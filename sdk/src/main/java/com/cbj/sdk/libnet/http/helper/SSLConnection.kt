package com.cbj.sdk.libnet.http.helper

import android.content.Context
import okhttp3.OkHttpClient
import java.io.InputStream
import java.lang.NullPointerException
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.*

object SSLConnection {
    private val TAG = SSLConnection::class.java.simpleName
    private var sTrustManagers:Array<TrustManager>?=null
    private var sSSLContext:SSLContext ?= null

    private var sClient:String?=null
    private var sKey:String?=null

    fun setClientKey(client:String?,key:String?):SSLConnection{
        sClient = client
        sKey = key
        return this
    }

    private fun contextSSL(context: Context):SSLContext{
        if (sClient==null || sKey == null){
            throw NullPointerException("call setClientKey method first")
        }
        if (sTrustManagers ==null) sTrustManagers = arrayOf(FakeX509TrustManager())
        var keyManagers:Array<KeyManager>?=null
        var ksIn :InputStream
        try{
            var keyStore = KeyStore.getInstance("BKS")

            ksIn = context.resources.assets.open(sClient!!)
            keyStore.load(ksIn, sKey!!.toCharArray())
            var kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            kmf.init(keyStore,sKey!!.toCharArray())
            keyManagers = kmf.keyManagers
        }catch (e:Exception){}
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagers, sTrustManagers, SecureRandom())
        return sslContext
    }

    fun getOKSSLBuild(context:Context):OkHttpClient.Builder{
        if (sSSLContext ==null) sSSLContext = contextSSL(context)
        return OkHttpClient.Builder()
                .sslSocketFactory(sSSLContext!!.socketFactory, FakeX509TrustManager())
                .hostnameVerifier { _, _ -> true }
    }

    fun allowSSL(context:Context){
        if (sSSLContext ==null) sSSLContext = contextSSL(context)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true}
        HttpsURLConnection.setDefaultSSLSocketFactory(sSSLContext!!.socketFactory)
    }
}