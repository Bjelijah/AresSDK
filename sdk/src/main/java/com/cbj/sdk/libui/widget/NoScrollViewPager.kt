package com.cbj.sdk.libui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.cbj.sdk.R

class NoScrollViewPager : ViewPager {

    private var isCanScroll = true//手动滑动
    private var isCanSmooth = true//滑动的效果

    constructor(c:Context):super(c)

    constructor(c:Context,attrs: AttributeSet):super(c,attrs){
        val a = c.obtainStyledAttributes(attrs, R.styleable.vp)
        isCanScroll = a.getBoolean(R.styleable.vp_vpCanScroll,true)
        isCanSmooth = a.getBoolean(R.styleable.vp_vpCanSmooth,true)
    }

    public fun setScroll(b:Boolean){
        isCanScroll = b
    }

    public fun setSmooth(b:Boolean){
        isCanSmooth = b
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isCanScroll && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isCanScroll&&super.onInterceptTouchEvent(ev)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,isCanSmooth)
    }
}