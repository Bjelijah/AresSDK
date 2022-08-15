package com.cbj.aresandroid.demo.api.bean

import com.google.gson.annotations.SerializedName

/**
 * @author : CBJ
 * @date   : 2022/8/12 16:48
 * @desc   :
 */
data class DeviceRes (
    @SerializedName("imei") var imei:String?,
    @SerializedName("device_name") var deviceName:String?,
    @SerializedName("status") var status:String?,
    @SerializedName("electricity") var electricity:Int?,
    @SerializedName("speed") var speed:Double?,
    @SerializedName("locate_type") var locateType:String?,
    @SerializedName("locate_time") var locateTime:String?,
    @SerializedName("receive_time") var receiveTime:String?,
    @SerializedName("latitude") var latitude:Double?,
    @SerializedName("longitude") var longitude:Double?,
    @SerializedName("direction") var direction:String?,
    @SerializedName("group_id") var groupId:String?,
    @SerializedName("group_name") var groupName:String?,
    @SerializedName("device_type") var deviceType:String?,
    @SerializedName("gps_status") var gpsStatus:String?,
    @SerializedName("gps_status_time") var gpsStatusTime:String?
)