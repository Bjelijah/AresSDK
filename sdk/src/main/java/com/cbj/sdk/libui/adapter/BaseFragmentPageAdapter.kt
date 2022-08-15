package com.cbj.sdk.libui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author : CBJ
 * @date   : 2022/8/11 15:55
 * @desc   :
 */
open class BaseFragmentPageAdapter:FragmentStateAdapter {

    constructor(activity: FragmentActivity, list:List<Fragment>):super(activity){
        mFragments = list
    }

    constructor(fm:FragmentManager,lifecycle: Lifecycle,list: List<Fragment>):super(fm,lifecycle){
        mFragments = list
    }

    constructor(fragment:Fragment,list: List<Fragment>):super(fragment){
        mFragments = list
    }

    lateinit var mFragments:List<Fragment>


    override fun getItemCount(): Int = mFragments.size

    override fun createFragment(position: Int): Fragment = mFragments[position]
}