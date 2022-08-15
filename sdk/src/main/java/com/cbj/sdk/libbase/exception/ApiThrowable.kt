package com.cbj.sdk.libbase.exception

import java.io.IOException

/**
 * @author:CBJ
 * @date: 2022/2/14
 * @des:
 */
class ApiThrowable(val code:Int,msg:String): IOException(msg) {
}