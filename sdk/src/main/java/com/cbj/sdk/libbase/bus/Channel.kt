@file:Suppress("ObjectPropertyName", "EXPERIMENTAL_API_USAGE")

package com.cbj.sdk.libbase.bus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

/**
 * @author : zhouzhou
 * @date   : 7/6/21 9:57 AM
 * @desc   : 事件总线
 */

var _channel = BroadcastChannel<EventBus<Any>>(102400)

/**
 * 发送事件
 * @param event 事件
 * @param tag 标签
 * @return Job
 */
fun sendEvent(event: Any, tag: String? = null) = ChannelScope().launch {
    _channel.send(EventBus(event, tag))
}


/**
 * 接收事件
 * @receiver LifecycleOwner
 * @param tags 标签
 * @param lifeEvent Event
 * @param block 接收到事件后执行函数
 */
inline fun <reified T> LifecycleOwner.receiveEvent(
    vararg tags: String? = emptyArray(),
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    noinline block: suspend CoroutineScope.(event: T) -> Unit
) {
    val coroutineScope = ChannelScope(this, lifeEvent)
    coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is T && (tags.isEmpty() || tags.contains(bus.tag))) {
                block(bus.event)
            }
        }
    }
}


