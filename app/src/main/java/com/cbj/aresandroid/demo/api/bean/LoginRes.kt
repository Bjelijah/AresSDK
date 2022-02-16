package xyz.mercs.libnet.http.bean.edrum

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data  class LoginRes(
    @SerializedName("token") @NonNull var token:String,
    @SerializedName("roleId") @NonNull var roleId:Int,
    @SerializedName("roleName") @NonNull var roleName:String,
    @SerializedName("userId") @NonNull var userId:Int,
    @SerializedName("expires") @NonNull var expires:String
)