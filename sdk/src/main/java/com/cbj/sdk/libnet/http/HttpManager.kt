package com.cbj.sdk.libnet.http

import android.content.Context
import android.util.Log
import com.cbj.sdk.libnet.http.helper.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.cbj.sdk.libnet.http.helper.SSLConnection
import java.io.IOException
import java.lang.reflect.Type
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit

class HttpManager private constructor(){
    companion object {
        val instance: HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            HttpManager()
        }
    }



    var mHost = ""
    var mClient:OkHttpClient?=null
    var mUploadClient:OkHttpClient?=null

    var mUrl:String?=null
    var mHttpApi: BaseHttpApi?=null
    var mUploadApi: BaseHttpApi?=null
    private val mInterceptors = ArrayList<Interceptor>()
    private var mErrorCb: IErrorCallback?= null

    fun addInterceptor(interceptor:Interceptor) : HttpManager {
        mInterceptors.add(interceptor)
        return this
    }

    fun error(e:()->Unit): HttpManager {
        mErrorCb = object : IErrorCallback {
            override fun onError() {
                e()
            }
        }
        return this
    }

    fun error(cb: IErrorCallback): HttpManager {
        mErrorCb = cb
        return this
    }

    var sslKey:String?=null
    var sslClient:String?=null

    fun initSSL_KEY(client:String,key:String):HttpManager{
        sslClient = client
        sslKey = key
        return this
    }

    fun initHttpClient(context: Context, url:String,isSSL:Boolean): HttpManager {
        try {
            if (mClient==null){
                val logInterceptor = HttpLoggingInterceptor{
                    if (it.length > 4096) {
                        var i = 0
                        while (i < it.length) {
                            if (i + 4096 < it.length)
                                Log.i("http",  it.substring(i, i + 4096))
                            else
                                Log.i("http",  it.substring(i))
                            i += 4096
                        }
                    } else {
                        Log.i("http",  it)
                    }
                }
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val uploadInterceptor = HttpLoggingInterceptor{
                    Log.i("http",it)
                }
                uploadInterceptor.level = HttpLoggingInterceptor.Level.HEADERS


                val builder = if(isSSL){
                    SSLConnection
                        .setClientKey(sslClient,sslKey)
                        .getOKSSLBuild(context)
                }else{
                    OkHttpClient.Builder()
                }
                val uploadBuilder = if(isSSL){
                    SSLConnection
                        .setClientKey(sslClient,sslKey)
                        .getOKSSLBuild(context)
                }else{
                    OkHttpClient.Builder()
                }
                for (i in mInterceptors){
                    builder.addInterceptor(i)
                    uploadBuilder.addInterceptor(i)
                }


                mClient = builder
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(logInterceptor)
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .build()

                mUploadClient = uploadBuilder
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(uploadInterceptor)
                    .connectTimeout(1800,TimeUnit.SECONDS)
                    .readTimeout(1800,TimeUnit.SECONDS)
                    .writeTimeout(1800,TimeUnit.SECONDS)
                    .build()

                RxJavaPlugins.setErrorHandler{ t->
                    Log.e("123","HttpManager error  ")
                    t.printStackTrace()
                    mErrorCb?.onError()
                }
                mUrl = url
            }
        }catch (e:KeyStoreException){
            e.printStackTrace()
        }catch (e:IOException){
            e.printStackTrace()
        }catch (e:CertificateException){
            e.printStackTrace()
        }catch (e:NoSuchAlgorithmException){
            e.printStackTrace()
        }catch (e:UnrecoverableKeyException){
            e.printStackTrace()
        }catch (e:KeyManagementException){
            e.printStackTrace()
        }
        mUrl = url
        return this
    }




    inline fun <reified T:BaseHttpApi> getHttpService(): T {
        if (mHttpApi==null){
            val retrofit = Retrofit.Builder()
                .client(mClient!!)
                .baseUrl(mUrl!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                    buildGson()  // 序列化 字段null "null" ""
                ))
                .build()
            mHttpApi = retrofit.create(T::class.java)
        }
        return mHttpApi as T
    }



    //    val listType: Type = object : TypeToken<List<String>>() {}.type
    val listType: Type = object : TypeToken<MutableList<String>>() {}.type
    fun buildGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Integer::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
            .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
            .registerTypeAdapter(Float::class.java, FloatDefault0Adapter())
            .registerTypeAdapter(Number::class.java, NumberDefault0Adapter())
            .registerTypeAdapter(String::class.java, StringNullAdapter())
            .registerTypeAdapter(listType, ListDefault0Adapter())
//                    .serializeNulls()
            .create()



    inline fun <reified T:BaseHttpApi> getUploadService(): T {
        if (mUploadApi==null){
            val retrofit = Retrofit.Builder()
                .client(mUploadClient!!)
                .baseUrl(mUrl!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mUploadApi = retrofit.create(T::class.java)
        }
        return mUploadApi as T
    }

    interface IErrorCallback{
        fun onError()
    }
}