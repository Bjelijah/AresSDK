package com.cbj.sdk.libbase.module

interface IBaseView {
    fun bindPresenter()
    fun unbindPresenter()
    fun onMsg(msg:String)
}