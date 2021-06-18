package com.cbj.sdk.libnet.http.helper

import android.util.Log
import com.google.gson.*
import java.lang.NumberFormatException
import java.lang.reflect.Type

class IntegerDefault0Adapter: JsonSerializer<Int>, JsonDeserializer<Int> {
    override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement = JsonPrimitive(src)

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        try{
            if (json?.asString=="" || json?.asString == "null"){
                Log.e("jsonErr","name=${typeOfT?.toString()}   json=${json?.asString}")
                return 0
            }
        }catch (ignore : Exception){}
        try{
            return json!!.asInt
        }catch (e:NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }
}