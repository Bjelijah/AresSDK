package com.cbj.sdk.libnet.http.helper

import android.util.Log
import com.google.gson.*
import java.lang.NumberFormatException
import java.lang.reflect.Type

class NumberDefault0Adapter: JsonSerializer<Number>, JsonDeserializer<Number> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Number {
        try{
            if (json?.asString=="" || json?.asString == "null"){
                Log.e("jsonErr","name=${typeOfT?.toString()}   json=${json?.asString}")
                return 0F
            }
        }catch (ignore : Exception){}
        try{
            return json!!.asFloat
        }catch (e:NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Number?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement  = JsonPrimitive(src)
}