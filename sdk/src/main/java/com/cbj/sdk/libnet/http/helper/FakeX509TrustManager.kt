package com.cbj.sdk.libnet.http.helper

import android.util.Log
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class FakeX509TrustManager  : X509TrustManager {
    val TAG = FakeX509TrustManager::class.java.simpleName
    private val mAcceptedIssuers = arrayOf<X509Certificate>()
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        chain?.forEach { printCertificate(it) }
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        chain?.forEach { printCertificate(it) }
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> = mAcceptedIssuers

    private fun printCertificate(cer: X509Certificate){
        Log.i(TAG,"[print]:version=${cer.version}, " +
                "sinName=${cer.sigAlgName}, "+
                "type=${cer.type}, "+
                "algorName=${cer.publicKey.algorithm}, "+
                "serialNum=${cer.serialNumber}, "+
                "dn=${cer.issuerDN}, "+
                "principalName=${cer.issuerDN.name}")
    }
}