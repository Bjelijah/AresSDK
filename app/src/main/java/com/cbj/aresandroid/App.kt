package com.cbj.aresandroid

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        Utils.init(this)
    }
}