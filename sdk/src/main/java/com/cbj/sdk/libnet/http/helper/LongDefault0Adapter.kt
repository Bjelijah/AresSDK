package com.cbj.sdk.libnet.http.helper

import android.util.Log
import com.google.gson.*
import java.lang.NumberFormatException
import java.lang.reflect.Type

class LongDefault0Adapter: JsonSerializer<Long>, JsonDeserializer<Long> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        try{
            if (json?.asString=="" || json?.asString == "null"){
                Log.e("jsonErr","name=${typeOfT?.toString()}   json=${json?.asString}")
                return 0L
            }
        }catch (ignore : Exception){}
        try{
            return json!!.asLong
        }catch (e:NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Long?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement = JsonPrimitive(src)
}