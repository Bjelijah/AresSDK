package com.cbj.sdk.libui.mvp

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.cbj.sdk.libui.mvp.adapter.BaseListAdapter
import com.cbj.sdk.libui.widget.EndLessOnScrollListener


open abstract class BaseListFragment<M,T:ViewBinding>(layoutRes:Int):BaseFragment(layoutRes), SwipeRefreshLayout.OnRefreshListener {

    var mListRv:RecyclerView?=null
    var mListSrl:SwipeRefreshLayout?=null
    protected var mScrollListener: RecyclerView.OnScrollListener?=null

//    override fun getLayout(): Int = R.layout.layout_base_list_mvp

    abstract fun getListRecyclerView(): RecyclerView
    abstract fun getListSwipeRefreshLayout():SwipeRefreshLayout

    override fun initView() {
        mListRv = getListRecyclerView()
        mListSrl = getListSwipeRefreshLayout()

//        LOG.E("123","v=$mLayout    rv=$mListRv   srl=$mListSrl")
        if (mListRv?.layoutManager==null) {
            mListRv?.layoutManager = getLayoutManager()
        }
        if (mListRv?.adapter==null) {
            mListRv?.adapter = getAdapter()
        }

        enableRefresh(isEnableRefresh())
        enableLoad(isEnableScroll())
    }

    override fun deinitView() {
        mListSrl?.isRefreshing = false
        removeRefreshListener()
        removeScrollListener()
    }

    protected fun enableRefresh(b:Boolean){
        if(b){
            addRefreshListener()
        }else{
            removeRefreshListener()
        }
    }

    protected fun enableLoad(b:Boolean){
        if(b){
            addScrollListener()
        }else{
            removeScrollListener()
        }
    }


    private fun addRefreshListener(){
        mListSrl?.setOnRefreshListener(this)
        mListSrl?.isEnabled = true
    }

    private fun removeRefreshListener(){
        mListSrl?.setOnRefreshListener(null)
        mListSrl?.isEnabled = false
    }

    private fun addScrollListener(){
        if(mScrollListener==null){
            mScrollListener = object : EndLessOnScrollListener() {

                override fun onLoadMore(currentPage: Int) {
                    onLoad(currentPage)
                }
            }
            mListRv?.addOnScrollListener(mScrollListener!!)
        }
    }

    private fun removeScrollListener(){
        if(mScrollListener!=null) {
            mListRv?.removeOnScrollListener(mScrollListener!!)
            mScrollListener = null
        }

    }

    protected fun refreshBegin(){
        mListSrl?.isRefreshing = true
    }

    protected fun refreshFinish(){
        mListSrl?.isRefreshing = false
    }


    override fun onRefresh() {
        if(mScrollListener is EndLessOnScrollListener){
            (mScrollListener as EndLessOnScrollListener).reset()
        }
    }

    protected fun onLoad(curPage:Int){

    }

    abstract fun getLayoutManager():RecyclerView.LayoutManager

    abstract fun getAdapter(): BaseListAdapter<M, T>

    abstract fun isEnableRefresh():Boolean

    abstract fun isEnableScroll():Boolean
}