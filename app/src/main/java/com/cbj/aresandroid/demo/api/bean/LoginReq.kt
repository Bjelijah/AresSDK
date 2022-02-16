package com.cbj.aresandroid.demo.api.bean
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.cbj.sdk.libnet.http.helper.HeaderInterceptor
import com.google.gson.annotations.SerializedName


data class LoginReq(
    @SerializedName("userName") @NonNull var userName:String,
    @SerializedName("password") @NonNull var password:String,
    @SerializedName("platform") @Nullable var platform:String?
){
    @SerializedName("version") var version:Int = HeaderInterceptor.VERSION_CODE
}