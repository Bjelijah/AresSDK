package com.cbj.aresandroid.demo.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cbj.aresandroid.demo.api.DemoService
import com.cbj.aresandroid.demo.api.bean.LoginReq
import com.cbj.sdk.libbase.utils.LOG
import com.cbj.sdk.libui.BaseViewModel
import com.cbj.sdk.libui.isTrue
import com.cbj.sdk.libui.launch

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
class DemoViewModule @ViewModelInject constructor(private val apiService: DemoService )
    :BaseViewModel() {

    val res:LiveData<String>
        get() = _res

    val _res = MutableLiveData<String>()

    fun login(){
        launch {
            apiService.login(LoginReq(
                "13900001000", "111111","teacher"
            )).let {
                checkResult(it).isTrue {
                    _res.postValue(it.data!!.token)
                }
            }
        }
    }
}