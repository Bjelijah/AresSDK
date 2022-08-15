package com.cbj.aresandroid.demo.ui.adapter

import android.view.ViewGroup
import com.cbj.aresandroid.databinding.ItemDevicesBinding
import com.cbj.aresandroid.demo.api.bean.DeviceRes
import com.cbj.sdk.libui.BindingViewHolder
import com.cbj.sdk.libui.adapter.BasePagingAdapter
import com.cbj.sdk.libui.newBindingViewHolder

/**
 * @author : CBJ
 * @date   : 2022/8/12 14:20
 * @desc   :
 */
class DeviceListAdapter:BasePagingAdapter<DeviceRes,ItemDevicesBinding>(
    { old,new-> old.imei == new.imei },
    { old,new-> old.deviceName == new.deviceName }
) {
    override fun init(bind: ItemDevicesBinding, bean: DeviceRes, pos: Int) {
        bind.title.text = bean.deviceName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemDevicesBinding> = newBindingViewHolder(parent)
}