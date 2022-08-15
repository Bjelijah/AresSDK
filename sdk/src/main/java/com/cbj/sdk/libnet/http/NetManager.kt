package com.cbj.sdk.libnet.http

import android.content.Context
import android.util.Log
import com.cbj.sdk.BuildConfig
import com.cbj.sdk.libnet.http.helper.*
import com.cbj.sdk.libnet.http.provider.INetProvider
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author : zhouzhou
 * @date   : 2019-11-02 14:53
 * @desc   :网络请求封装类 Retrofit+OkHttp
 */
object NetManager {

    @Throws
    @JvmOverloads
    fun getRetrofit(baseUrl: String, provider: INetProvider ): Retrofit =
        baseUrl.takeIf { it.isNullOrEmpty().not() }?.let {
            Retrofit.Builder()
                .baseUrl(it)
                .client(getClient(provider))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(buildGson())).build()
        }?:throw IllegalStateException("url should not be empty")



    private fun buildGson(): Gson =
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Integer::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
            .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
            .registerTypeAdapter(Float::class.java, FloatDefault0Adapter())
            .registerTypeAdapter(Number::class.java, NumberDefault0Adapter())
            .registerTypeAdapter(String::class.java, StringNullAdapter())
            .create()


    /**
     * 获取OkHttpClient实例
     */
    private fun getClient(provider: INetProvider): OkHttpClient {

        val builder = OkHttpClient.Builder()
        provider.configHttps(builder)

        builder.connectTimeout(provider.configConnectTimeoutSecs(), TimeUnit.SECONDS)
        builder.readTimeout(provider.configReadTimeoutSecs(), TimeUnit.SECONDS)
        builder.writeTimeout(provider.configWriteTimeoutSecs(), TimeUnit.SECONDS)

        provider.configCookie()?.let {
            builder.cookieJar(it)
        }


        builder.addInterceptor(NetInterceptor(provider.configHandler()))
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        provider.configInterceptors()?.forEach {
            builder.addInterceptor(it)
        }

        if (provider.configLogEnable()) {

            val loggingInterceptor = HttpLoggingInterceptor { msg ->
                var it = when {
                    msg.startsWith("{") -> JSONObject(msg).toString(4)
                    else -> msg
                }
                if (it.length > 4096) {
                    var i = 0
                    while (i < it.length) {
                        if (i + 4096 < it.length)
                            Log.i("http", it.substring(i, i + 4096))
                        else
                            Log.i("http", it.substring(i))
                        i += 4096
                    }
                } else {
                    Log.i("http", it)
                }
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }




}