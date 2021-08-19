package com.cbj.sdk.libnet.http.bean

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

/**
 * @date 2021/3/3
 * @author elijah
 * @Description
 */
data class ListBean<T>(
    @SerializedName("total")   @NonNull var total:Int?,
    @SerializedName("records") @NonNull var records:List<T>
)
