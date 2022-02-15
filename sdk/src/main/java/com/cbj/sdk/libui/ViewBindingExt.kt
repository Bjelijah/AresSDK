package com.cbj.sdk.libui

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified VB : ViewBinding> Activity.bindView() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}


inline fun <reified VB : ViewBinding> Dialog.bindView() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}


inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
        VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB



inline fun <reified VB : ViewBinding> Fragment.bindView() =
        FragmentBindingDelegate(VB::class.java)



inline fun <reified T : ViewBinding> newBindingViewHolder(parent: ViewGroup): BindingViewHolder<T> {
    val method = T::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    val binding = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as T
    return BindingViewHolder(binding)
}

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

class FragmentBindingDelegate<VB:ViewBinding>(
        private val clazz: Class<VB>
) :ReadOnlyProperty<Fragment,VB>{

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
//                @LifecycleEventObserver(Lifecycle.Event.ON_DESTROY)
//                fun onDestroyView() {
//                    _binding = null
//                }

                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY){
                        _binding = null
                    }
                }
            })
            _binding = clazz.getMethod("bind", View::class.java)
                    .invoke(null, thisRef.requireView()) as VB
            isInitialized = true
        }
        return binding
    }
}