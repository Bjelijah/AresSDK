package com.cbj.sdk.libui.mvp

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter




open abstract class BaseFragment(layoutRes:Int) : Fragment(layoutRes) {

    protected var mLayout: View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLayout = super.onCreateView(inflater, container, savedInstanceState)
        return mLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initView()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        try{
            deinitView()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }


//    @LayoutRes
//    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun deinitView()

    var myToast : Toast?=null
    protected fun showToast(msg:String?){
        if(msg.isNullOrEmpty())return
        activity?.runOnUiThread {
            myToast?.cancel()
            myToast = Toast.makeText(context,msg, Toast.LENGTH_SHORT)
            myToast?.setGravity(Gravity.CENTER,0,0)
            myToast?.show()
        }
    }
}