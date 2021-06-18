package com.cbj.sdk.libui.mvp.moudles

interface IBasePresenter {
    fun bindView(v: IBaseView)
    fun unbindView()
}