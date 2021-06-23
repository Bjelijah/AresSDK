package com.cbj.sdk.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * @date 2021/3/10
 * @author elijah
 * @Description
 */
object TimeUtil {

    /**
     * @Description 毫秒数-> "mm:ss"
     * @param ms
     */
    fun ms2String(ms:Long,pattern:String):String = SimpleDateFormat(pattern).format(ms - TimeZone.getDefault().rawOffset)


    /**
     * @Description 判断时间是否早于现在
     */
    fun isBefore(time:String):Boolean{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = Date()
        val parseDate = sdf.parse(time)
        return parseDate.before(now)
    }


    /**
     * @Description 计算 现在到输入时间还差多少时间
     * @param time 晚于现在的时间
     * @return {"HH","mm","ss"}
     */
    fun calcNow2Date(time:String):List<String>{
        val now = Date()
        val after =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)?:return emptyList()
        val ms = after.time - now.time
        if (ms<=0)return listOf("00","00","00")//比现在早
        val day = ms / (3600*24*1000L)
        val hhmmss =   ms2String(ms,"HH:mm:ss")
        return if (day>0){
            val arr = hhmmss.split(":")
            val hh = arr[0].toInt()+day*24
            listOf(hh.toString(),arr[1],arr[2])
        }else{
            hhmmss.split(":")
        }
    }

    /**
     * @Description 计算输入时间到现在
     * @param time 早于现在的时间
     * @return x天前， x小时前
     */
    fun calcDate2Now(time:String):String{
        if (time.isNullOrEmpty())return ""
        val now = Date()
        val before =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)?:return ""
        val ms = now.time - before.time
        if (ms<0)return ""
        val day = ms / (3600*24*1000L)
        val hour = ms / (3600*1000)
        val min = ms / (60*1000)
        val sec = ms / 1000
        if (day >= 1) return "${day}天前"
        if (hour>= 1) return "${hour}小时前"
        if (min >= 1) return "${min}分钟前"
        if (sec >= 1) return "${sec}秒前"
        return "刚刚"
    }





    /**
     * @Description   "yyyy-MM-dd HH:mm:ss"  -> "yyyy-MM-dd"
     */
    fun fmtYMD(time:String):String = time.split(" ")[0]


    fun now():String{
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }

    fun today():String{
        return SimpleDateFormat("yyyy-MM-dd 00:00:00").format(Date())
    }

    fun yesterday():String{
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,-1)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
    }


    fun nearWeek():String{
        var calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-7,0,0,0)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
    }

    fun nearMonth():String{
        var calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-30,0,0,0);
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
    }

    fun nearHalfYear():String{
        var calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)-6,calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
    }

    fun isSameDay(t1:String,t2:String):Boolean = fmtYMD(t1) == fmtYMD(t2)

}