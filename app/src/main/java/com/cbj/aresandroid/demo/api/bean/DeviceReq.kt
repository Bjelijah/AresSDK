package com.cbj.aresandroid.demo.api.bean

import com.cbj.sdk.libbase.ext.preference
import com.google.gson.annotations.SerializedName

/**
 * @author : CBJ
 * @date   : 2022/8/12 16:42
 * @desc   :
 */
data class DeviceReq(
    @SerializedName("pagenum") var page:Int,
    @SerializedName("pagesize") var size:Int,
    @SerializedName("install_status") var installStatus:Int = 1,
    @SerializedName("status") var status:Int = 0,
    @SerializedName("dept_id") var deptId:Int?=null,
    @SerializedName("keyword") var keyword:String?=null,
    @SerializedName("token") var token:String = preference.token?:"",

)
