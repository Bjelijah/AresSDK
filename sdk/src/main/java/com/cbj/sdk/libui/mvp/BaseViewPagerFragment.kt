package com.cbj.sdk.libui.mvp

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cbj.sdk.R
import com.cbj.sdk.libui.mvp.adapter.BasePagerAdapter
import com.cbj.sdk.libui.widget.NoScrollViewPager


abstract class BaseViewPagerFragment :BaseFragment(R.layout.layout_base_vp_mvp),ViewPager.OnPageChangeListener {

    protected var mFragments:List<BaseFragment> ?= null
    protected var mAdapter:PagerAdapter?=null
    protected var mPageIndex = 0

    var vp :NoScrollViewPager?=null

    override fun initView() {
        if (mFragments==null){
            mFragments = getFragments()
        }
        vp = mLayout?.findViewById(R.id.vp)

        vp?.setScroll(getScrollable())
        vp?.setSmooth(getSmoothable())
        if (mAdapter==null){
            mAdapter = BasePagerAdapter(childFragmentManager,mFragments!!)
            vp?.adapter = mAdapter
        }

        vp?.addOnPageChangeListener(this)
        vp?.currentItem = mPageIndex
    }

    override fun deinitView() {
        vp?.removeOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mPageIndex = position
    }

    override fun onPageSelected(position: Int) {
        mPageIndex = position
    }

    abstract fun getFragments():List<BaseFragment>

    abstract fun getScrollable():Boolean

    abstract fun getSmoothable():Boolean

    fun getViewPage(): NoScrollViewPager {
        return vp!!
    }


}