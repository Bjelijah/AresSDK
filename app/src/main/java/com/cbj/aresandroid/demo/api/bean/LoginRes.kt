package com.cbj.aresandroid.demo.api.bean
import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data  class LoginRes(
    @SerializedName("token")  var token:String,
    @SerializedName("depts")  var depts: List<Dept>?
){
    data class Dept(
        @SerializedName("id") var id:Int,
        @SerializedName("name") var name:String,
        @SerializedName("children") var children:List<Dept>?
    )
}