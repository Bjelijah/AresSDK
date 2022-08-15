package com.cbj.aresandroid.demo.api.bean
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.cbj.sdk.libnet.http.helper.HeaderInterceptor
import com.google.gson.annotations.SerializedName


data class LoginReq(
    @SerializedName("account") @NonNull var account:String,
    @SerializedName("password") @NonNull var password:String,
    @SerializedName("type") @Nullable var type:String = "app"
)