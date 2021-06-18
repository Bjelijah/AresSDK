package com.cbj.sdk.libui.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndLessOnScrollListener: RecyclerView.OnScrollListener(){
    private var mLayoutManager: RecyclerView.LayoutManager?=null

    private var currentPage = 0
    private var totalItemCount: Int = 0
    private var previousTotal = 0
    private var visibleItemCount: Int = 0
    private var firstVisibleItem: Int = 0
    private var loading = true


    fun reset(){
        currentPage = 0
        previousTotal = 0
        totalItemCount = 0
        visibleItemCount = 0
        firstVisibleItem = 0
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        mLayoutManager = recyclerView.layoutManager
        visibleItemCount = recyclerView.childCount
        totalItemCount = mLayoutManager?.itemCount ?:0
        if(mLayoutManager is LinearLayoutManager){
            firstVisibleItem = (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                //说明数据已经加载结束
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }

    }
    abstract fun onLoadMore(currentPage:Int)
}