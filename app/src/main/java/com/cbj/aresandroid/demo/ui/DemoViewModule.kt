package com.cbj.aresandroid.demo.ui

import androidx.lifecycle.MutableLiveData
import com.cbj.aresandroid.demo.api.ApiService
import com.cbj.aresandroid.demo.api.bean.LoginReq
import com.cbj.sdk.libbase.ext.preference
import com.cbj.sdk.libbase.ext.toMap
import com.cbj.sdk.libui.BaseViewModel
import com.cbj.sdk.libui.asLiveData
import com.cbj.sdk.libui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
@HiltViewModel
class DemoViewModule @Inject constructor(
     private val apiService: ApiService
    ) :BaseViewModel() {
    private val _res = MutableLiveData<String>()
    val res get() = _res.asLiveData()



    fun login(){
        launch {
            apiService.login(LoginReq(
                "develop", "Hx201910"
            ).toMap()).let {
                preference.token = it.data!!.token
                _res.postValue(it.data!!.token)
            }
        }
    }


}