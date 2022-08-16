package com.cbj.sdk.libui.adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cbj.sdk.libui.BindingViewHolder


abstract class BaseListAdapter<T,VB: ViewBinding>: RecyclerView.Adapter<BindingViewHolder<VB>>(){

    protected var mList:ArrayList<T>?=null

    var mItemCb:((T)->Unit)?=null


    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        init(holder.binding,mList!![position],position)
    }

    override fun getItemCount(): Int  = mList?.size?:0

    abstract fun init(bind: VB,bean:T,pos:Int)

    fun clear(){
        mList?.clear()
        notifyDataSetChanged()
    }

    fun setData(list:List<T>){
        if (mList==null)mList = ArrayList()
        mList?.clear()
        mList?.addAll(list)
        notifyDataSetChanged()
    }
}

