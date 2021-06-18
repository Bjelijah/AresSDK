package com.cbj.sdk.libui.mvp.moudles


/**
 * @date 2021/3/10
 * @author elijah
 * @Description
 */
open class BaseListPresenter: BasePresenter() {
    protected val mPageSize = 20
    protected var mPage = 1
    protected var mTotal = -1


    protected fun refreshPage():Boolean{
        mPage = 1
        return true
    }

    protected fun nextPage():Boolean{
        if (mPage*mPageSize  >=  mTotal)return false
        mPage++
        return true
    }


}