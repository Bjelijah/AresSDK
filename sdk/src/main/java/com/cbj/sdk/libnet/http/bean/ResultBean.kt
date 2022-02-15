package com.cbj.sdk.libnet.http.bean

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

/**
 * @date 2021/3/3
 * @author elijah
 * @Description
 */
data class ResultBean<T> (
    @SerializedName("code") @NonNull var code:Int,
    @SerializedName("msg")  @NonNull var msg:String,
    @SerializedName("data") @Nullable var data:T?
    ){
    val isSuccess
        get() = code == 200
}
