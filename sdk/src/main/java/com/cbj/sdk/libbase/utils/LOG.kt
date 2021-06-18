package com.cbj.sdk.libbase.utils

import android.util.Log

object LOG {
    var isDebug:Boolean = true

    fun I(tag:String,msg:String){
        if (!isDebug) return
        if (msg.length > 4096) {
            var i = 0
            while (i < msg.length) {
                if (i + 4096 < msg.length)
                    Log.i(tag, getFileLine() + msg.substring(i, i + 4096))
                else
                    Log.i(tag, getFileLine() + msg.substring(i))
                i += 4096
            }
        } else {
            Log.i(tag, getFileLine() + msg)
        }
    }
    fun E(tag:String,msg:String){
        if (!isDebug) return
        if (msg.length > 4096) {
            var i = 0
            while (i < msg.length) {
                if (i + 4096 < msg.length)
                    Log.e(tag, getFileLine() + msg.substring(i, i + 4096))
                else
                    Log.e(tag, getFileLine() + msg.substring(i))
                i += 4096
            }
        } else {
            Log.e(tag, getFileLine() + msg)
        }
    }
    fun W(tag:String,msg:String){
        if (!isDebug) return
        if (msg.length > 4096) {
            var i = 0
            while (i < msg.length) {
                if (i + 4096 < msg.length)
                    Log.w(tag, getFileLine() + msg.substring(i, i + 4096))
                else
                    Log.w(tag, getFileLine() + msg.substring(i))
                i += 4096
            }
        } else {
            Log.w(tag, getFileLine() + msg)
        }
    }
    fun D(tag:String,msg:String){
        if (!isDebug) return
        if (msg.length > 4096) {
            var i = 0
            while (i < msg.length) {
                if (i + 4096 < msg.length)
                    Log.d(tag, getFileLine() + msg.substring(i, i + 4096))
                else
                    Log.d(tag, getFileLine() + msg.substring(i))
                i += 4096
            }
        } else {
            Log.d(tag, getFileLine() + msg)
        }
    }
    private fun getFileLine(): String {
        val traceElement = Exception().stackTrace[2]
        val toStringBuffer = StringBuffer("[")
            .append(traceElement.fileName).append(" | ")
            .append(traceElement.lineNumber).append(" ] ")
        return toStringBuffer.toString()
    }
}