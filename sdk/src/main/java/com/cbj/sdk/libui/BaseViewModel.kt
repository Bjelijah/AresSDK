package com.cbj.sdk.libui

import androidx.lifecycle.ViewModel
import com.cbj.sdk.libbase.exception.ApiThrowable
import com.cbj.sdk.libnet.http.bean.ResultBean

/**
 * @author:CBJ
 * @date: 2022/2/14
 * @des:
 */
open class BaseViewModel:ViewModel() {

    protected fun <T> checkResult(b: ResultBean<T>):Boolean =
        if(b.isSuccess) true else throw ApiThrowable(b.code,b.msg)

    override fun onCleared() {
        super.onCleared()
    }
}