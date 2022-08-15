package com.cbj.sdk.libui.adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cbj.sdk.libui.BindingViewHolder


abstract class BaseListAdapter<M,T: ViewBinding>: RecyclerView.Adapter<BindingViewHolder<T>>(){

    protected var mList:ArrayList<M>?=null

    var mItemCb:((M)->Unit)?=null


    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        init(holder.binding,mList!![position],position)
    }

    override fun getItemCount(): Int  = mList?.size?:0

    abstract fun init(bind: T,bean:M,pos:Int)

    fun clear(){
        mList?.clear()
        notifyDataSetChanged()
    }

    fun setData(list:List<M>){
        if (mList==null)mList = ArrayList()
        mList?.clear()
        mList?.addAll(list)
        notifyDataSetChanged()
    }
}

