package com.cbj.sdk.libbase.ext

import android.content.res.Resources
import android.util.TypedValue
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.cbj.sdk.libbase.utils.NumberUtil
import com.cbj.sdk.libbase.utils.SharedPreferencesUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * @author:cbj
 * @Date: 2022/2/15
 * @Description:
 */
fun LocalDateTime.format(): String {
    return String.format(
        "%04d-%02d-%02d %02d:%02d:%02d",
        year,
        monthValue,
        dayOfMonth,
        hour,
        minute,
        second
    )
}

fun Number.asChinese() = NumberUtil.num2Chinese(this.toInt())


/**
 * @author cbj
 * @Description this: @DimRes
 */
val Int.sdp
    get() = Utils.getApp().resources.getDimensionPixelSize(this)

val Int.color
    get() = Utils.getApp().resources.getColor(this)

val Int.textSize
    get() = Utils.getApp().resources.getDimension(this).div(
        Utils.getApp().resources.displayMetrics.scaledDensity
    )

val Float.dp
    get() = this * Utils.getApp().resources.displayMetrics.density


val Int.dp
    get() = toFloat().dp.toInt()

val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
val Int.sp
    get() = toFloat().sp.toInt()

val Float.pt
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PT,
        this,
        Utils.getApp().resources.displayMetrics
    )
val Int.pt
    get() = toFloat().pt.toInt()

fun Double.format(scale: Int): String
    = BigDecimal(this).setScale(scale, BigDecimal.ROUND_FLOOR).toString()


fun Float.format(n: Int) = String.format("%.${n}f", this)

fun <T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, object: TypeToken<T>(){}.type)
}

fun <T> T.toMap(): Map<String, String> = Gson().fromJson(this.toJson(),object:TypeToken<Map<String,String>>(){}.type)

fun Boolean.isFalse(block: () -> Unit) {
    when (this) {
        false -> {
            block.invoke()
        }
        else -> {}
    }
}

fun Boolean.isTrue(block: () -> Unit) {
    when (this) {
        true -> {
            block.invoke()
        }
        else -> {}
    }
}

fun <T> T?.isNotNull(block: (any: T) -> Unit) {
    if (this != null) {
        block.invoke(this)
    }
}

fun <T> T?.isNull(block: () -> Unit) {
    if (this == null) {
        block.invoke()
    }
}

val preference by lazy {
    SharedPreferencesUtil()
}

val appVersion by lazy {
    AppUtils.getAppVersionName()
}