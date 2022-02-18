package com.cbj.sdk.libui

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils

/**
 * @author:cbj
 * @Date: 2022/2/15
 * @Description: ui 扩展
 */
fun toast(msg:String?){
    if (msg.isNullOrEmpty())return
    ToastUtils.showShort(msg)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> observer(t) } })
}


var lastClickTime: Long = 0
var SPACE_TIME: Long = 1000
var hash: Int = 0
infix fun View.singleClick(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

val Int.textSize
    get() = Utils.getApp().resources.getDimension(this)

infix fun TextView.size(@DimenRes resId:Int){
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX,resId.textSize)
}
