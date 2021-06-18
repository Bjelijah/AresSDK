package com.cbj.sdk.libnet.http.helper

import android.util.Log
import com.google.gson.*
import java.lang.NumberFormatException
import java.lang.reflect.Type

class DoubleDefault0Adapter: JsonSerializer<Double>, JsonDeserializer<Double> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
        try{
            if (json?.asString=="" || json?.asString == "null"){
                Log.e("jsonErr","name=${typeOfT?.toString()}   json=${json?.asString}")
                return 0.0
            }
        }catch (ignore : Exception){}
        try{
            return json!!.asDouble
        }catch (e:NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Double?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement = JsonPrimitive(src)
}