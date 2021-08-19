package com.cbj.sdk.libui.mvp.moudles

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.cbj.sdk.libbase.exception.MsgThrowable
import com.cbj.sdk.libnet.http.bean.ResultBean
import com.cbj.sdk.libnet.http.helper.HeaderInterceptor
import com.cbj.sdk.libui.ARouterPath
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BasePresenter {
    companion object{
        val ERROR_HTTP = "服务端错误"
        val ERROR_DOWNLOAD = "下载失败"
    }



    private var mCompositeDisposable: CompositeDisposable?=null

    protected fun addDisposable(subscription: Disposable){
        if (mCompositeDisposable?.isDisposed != false){
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    protected fun dispose() = mCompositeDisposable?.dispose()


    protected fun checkCode(code:Int):Boolean{
        return when(code){
            200->true
            305->{ //客户端请求的API版本与服务端不兼容，客户端应升级到最新版
                false
            }
            400->{ // 请求参数错误
                false
            }
            402,403->{ // token已失效,未携带token
                onTokenExpired()
                false
            }
            500->{ //服务器错误
                false
            }
            501->{ // 未知错误
                false
            }
            else->true//FIXME by cbj test
        }
    }

    protected fun <T> checkResult(b:ResultBean<T>):Boolean{
        var res = checkCode(b.code)
        if (!res) throw MsgThrowable(b.msg)
        return res
    }


    protected fun checkError(e:Throwable):String{
        e.printStackTrace()
        return if (e is MsgThrowable){
            e.message?:""
        }else{
            ERROR_HTTP
        }
    }

    open fun onTokenExpired(){
        HeaderInterceptor.TOKEN = null
        ARouter.getInstance().build(ARouterPath.LOGIN_ATY)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

}