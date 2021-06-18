package com.cbj.sdk.libnet.http.helper

import android.util.Log
import com.google.gson.*
import kotlinx.serialization.json.buildJsonArray
import java.lang.NumberFormatException
import java.lang.reflect.Type

class ListDefault0Adapter: JsonSerializer<List<String>>, JsonDeserializer<List<String>> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<String> {
        try{
            if (json?.asString=="" || json?.asString == "null"){
                Log.e("jsonErr","name=${typeOfT?.toString()}   json=${json?.asString}")
                return emptyList()
            }
        }catch (ignore : Exception){}
        try{
            return Gson().fromJson(json,typeOfT)
        }catch (e:NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(
        src: List<String>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonParser().parse(Gson().toJson(src))

}