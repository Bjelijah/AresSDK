package com.cbj.sdk.libnet.http.helper

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @date 2021/4/8
 * @author elijah
 * @Description
 */
class StringNullAdapter : TypeAdapter<String>() {
    override fun write(writer: JsonWriter?, value: String?) {
        if (value == null){
            writer?.nullValue()
            return
        }
        writer?.value(value)
    }

    override fun read(read: JsonReader?): String {
        if (read?.peek() == JsonToken.NULL){
            read?.nextNull()
            return ""
        }
        var jsonStr = read?.nextString()
        return if (jsonStr == "null") "" else jsonStr?:""
    }
}