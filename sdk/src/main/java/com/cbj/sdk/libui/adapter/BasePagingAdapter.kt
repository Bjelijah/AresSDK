package com.cbj.sdk.libui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.cbj.sdk.libui.BindingViewHolder

/**
 * @author : CBJ
 * @date   : 2022/8/11 16:50
 * @desc   :
 */
abstract class BasePagingAdapter<T:Any,VB: ViewBinding>(
    onItemSame:(oldItem: T, newItem: T)->Boolean, onContentSame:(oldItem: T, newItem: T)->Boolean
): PagingDataAdapter<T,BindingViewHolder<VB>>(object :DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =  onItemSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = onContentSame(oldItem, newItem)
}){

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        getItem(position)?.let {
            init(holder.binding, it,position)
        }
    }

    abstract fun init(bind: VB,bean:T,pos:Int)

    override fun getItemCount(): Int {
        return super.getItemCount()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<VB> {
        TODO("Not yet implemented")
    }
}