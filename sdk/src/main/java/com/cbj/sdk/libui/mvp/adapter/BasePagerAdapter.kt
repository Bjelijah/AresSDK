package com.cbj.sdk.libui.mvp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class BasePagerAdapter(fm:FragmentManager?, var list:List<Fragment>):FragmentPagerAdapter(fm!!) {
    override fun getItem(pos: Int): Fragment = list[pos]

    override fun getCount(): Int = list.size

}