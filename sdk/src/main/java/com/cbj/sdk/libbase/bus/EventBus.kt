package com.cbj.sdk.libbase.bus

/**
 * @author : zhouzhou
 * @date   : 7/6/21 9:47 AM
 * @desc   :
 */
class EventBus<T>(val event: T, val tag: String? = null) {
    override fun toString(): String {
        return "event = $event,tag = $tag"
    }
}