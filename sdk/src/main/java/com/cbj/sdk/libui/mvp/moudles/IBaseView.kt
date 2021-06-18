package com.cbj.sdk.libui.mvp.moudles

interface IBaseView {
    fun bindPresenter()
    fun unbindPresenter()
    fun onMsg(msg:String)
}