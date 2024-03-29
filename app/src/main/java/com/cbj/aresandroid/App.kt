package com.cbj.aresandroid

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
@HiltAndroidApp
class App:Application() ,LifecycleOwner{
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this)
        Utils.init(this)
    }

    override fun getLifecycle(): Lifecycle =  LifecycleRegistry(this)
}