package com.cbj.sdk.libui.mvp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cbj.sdk.libui.mvp.BindingViewHolder


open abstract class BaseListAdapter<M,T: ViewBinding>: RecyclerView.Adapter<BindingViewHolder<T>>(){

    protected var mList:ArrayList<M>?=null




    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        init(holder.binding,mList!![position],position)
    }




    override fun getItemCount(): Int  = mList?.size?:0

    abstract fun init(bind: T,bean:M,pos:Int)

    fun clear(){
        mList?.clear()
        notifyDataSetChanged()
    }
}

