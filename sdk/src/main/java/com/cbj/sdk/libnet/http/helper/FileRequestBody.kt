package com.cbj.sdk.libnet.http.helper

import android.util.Log
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*

class FileRequestBody<T>(var requestBody: RequestBody,var callback: RetrofitCallback<T>): RequestBody() {
    private var bufferedSink: BufferedSink? = null

    override fun contentLength(): Long = requestBody.contentLength()

    override fun contentType(): MediaType? = requestBody.contentType()

    override fun writeTo(sink: BufferedSink) {
        if(sink is Buffer){
            Log.e("123","sink is Buffer")
            requestBody.writeTo(sink)
            return
        }
        Log.i("123","sink=${sink.javaClass.simpleName}")
        if (bufferedSink==null){
            bufferedSink = sink(sink).buffer()
        }

        requestBody.writeTo(bufferedSink!!)
        bufferedSink!!.flush()
    }

    private fun sink(sink:Sink):Sink = object :ForwardingSink(sink){
        var bytesWritten = 0L
        var contentLength = 0L
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            if (contentLength == 0L){
                contentLength = contentLength()
            }
            bytesWritten += byteCount

            callback.onLoading(contentLength,bytesWritten)
        }

    }
}