package com.cbj.sdk.libbase.service

import android.text.TextUtils
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

object RouterServiceManager {
    fun <T : IProvider> provide(path: String): T? {
        if (TextUtils.isEmpty(path)) {
            return null
        }
        var provider: IProvider? = null
        try {
            provider = ARouter.getInstance()
                .build(path)
                .navigation() as IProvider
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


        return provider as T?
    }

    fun <T : IProvider> provide(clz: Class<T>): T? {

        var provider: IProvider? = null
        try {
            provider = ARouter.getInstance().navigation(clz)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return provider as T?
    }
}