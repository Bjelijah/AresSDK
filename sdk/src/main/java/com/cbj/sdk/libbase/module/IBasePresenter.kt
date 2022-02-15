package com.cbj.sdk.libbase.module

interface IBasePresenter {
    fun bindView(v: IBaseView)
    fun unbindView()
}