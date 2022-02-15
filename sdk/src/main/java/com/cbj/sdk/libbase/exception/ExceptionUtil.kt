package com.cbj.sdk.libbase.exception

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.cbj.sdk.BuildConfig
import com.cbj.sdk.libnet.http.helper.HeaderInterceptor
import com.cbj.sdk.libui.ARouterPath
import com.cbj.sdk.libui.toast
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.text.ParseException
import javax.net.ssl.SSLException

/**
 * @author:CBJ
 * @date: 2022/2/14
 * @des:
 */
object ExceptionUtil {
    fun catchException(error: Throwable){
        if (BuildConfig.DEBUG) {
            error.printStackTrace()
        }
        when(error){
            is HttpException -> {
                 when (error.code()) {
                    403 -> toast("请求被服务器拒绝")
                    404 -> toast("请求地址不存在")
                    500 -> toast("服务器发生错误")
                    else -> toast(error.message())
                }
            }
            is MsgThrowable -> {
                error.message?.let {
                    toast(it)
                }
            }
            is ApiThrowable -> {
                when(error.code){
                    305->{ //客户端请求的API版本与服务端不兼容，客户端应升级到最新版

                    }
                    400->{ // 请求参数错误

                    }
                    402,403->{ // token已失效,未携带token
                        HeaderInterceptor.TOKEN = null
                        ARouter.getInstance().build(ARouterPath.LOGIN_ATY)
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            .navigation()
                    }
                    500->{ //服务器错误

                    }
                    501->{ // 未知错误

                    }
                }
                error.message?.let {
                    toast(it)
                }
            }
            is SSLException -> {
                error.message?.let {
                    toast(it)
                }
            }
            is SocketTimeoutException -> {
                toast("连接超时")
            }
            is JsonParseException, is ParseException, is JSONException, is JsonIOException -> {
                toast("解析错误")
            }
        }
    }
}