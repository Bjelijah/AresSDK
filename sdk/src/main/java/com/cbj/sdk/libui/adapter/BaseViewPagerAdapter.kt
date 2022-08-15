package com.cbj.sdk.libui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class BaseViewPagerAdapter constructor(
    private var fragments: MutableList<Fragment>,
    private var tabs: MutableList<String>,
    childFragmentManager: FragmentManager
) :
    FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getPageTitle(position: Int): CharSequence = tabs[position]
}
