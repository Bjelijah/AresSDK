package com.cbj.sdk.libbase.utils

import java.util.regex.Pattern

/**
 * @date 2021/3/16
 * @author elijah
 * @Description
 */
object NumberUtil {
    var valueMap = mapOf(
        0 to "零", 1 to "一", 2 to "二", 3 to "三", 4 to "四", 5 to "五",
        6 to "六", 7 to "七", 8 to "八", 9 to "九"
    )

    var unitMap = listOf("", "十", "百", "千", "万")

    /**
     * @Description 数字转中文
     */
    fun num2Chinese(num: Int):String{
//        LOG.I("123","num=$num")
        var str = ""
        var numStr = num.toString()
        var len = numStr.length
        for (i in numStr.indices){
            var value = valueMap[numStr[i].toString().toInt()]
            var unit = unitMap[len - i - 1]
            if(value=="一" && unit == "十"){
                value = ""
            }
            if (value == "零" && unit == "" && str!=""){
                value = ""
            }
            str += "$value$unit"
        }
        return str
    }

    private const val regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)"
    fun isID(IDNumber: String?):Boolean{
        if (IDNumber.isNullOrEmpty())return false
        val matches: Boolean = IDNumber.matches(regularExpression.toRegex())
        if (matches){
            if (IDNumber.length === 18) {
                return try {
                    val charArray = IDNumber.toCharArray()
                    //前十七位加权因子
                    val idCardWi = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
                    //这是除以11后，可能产生的11位余数对应的验证码
                    val idCardY = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
                    var sum = 0
                    for (i in idCardWi.indices) {
                        val current = charArray[i].toString().toInt()
                        val count = current * idCardWi[i]
                        sum += count
                    }
                    val idCardLast = charArray[17]
                    val idCardMod = sum % 11
                    idCardY[idCardMod].equals(idCardLast.toString(), ignoreCase = true)
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }
        }
        return matches
    }





    const val PHONE_REGEX =  "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$"

    fun isPhone(phone: String?):Boolean{
        if (phone==null)return false
        var p = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE)
        var m = p.matcher(phone)
        return m.matches()
    }

    const val PWD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$"
    fun isPwd(pwd:String?):Boolean{
        if (pwd == null)return false
        var p = Pattern.compile(PWD_REGEX, Pattern.CASE_INSENSITIVE)
        var m = p.matcher(pwd)
        return m.matches()
    }

}