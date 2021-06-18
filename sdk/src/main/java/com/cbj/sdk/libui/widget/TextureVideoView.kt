package com.cbj.sdk.libui.widget

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.TextureView


class TextureVideoView:TextureView{
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var mShowMask = false

    /**
     * 重新计算video的显示位置，裁剪后全屏显示
     */
    fun updateTextureViewSizeCenterCrop(videoWidth:Int,videoHeight:Int){
        if(height ==0 || width ==0)return
        var sx = width.toFloat() / videoWidth.toFloat()
        var sy = height.toFloat() / videoHeight.toFloat()
        var maxScale = sx.coerceAtLeast(sy)

        var matrix = Matrix()
        matrix.reset()

        matrix.preTranslate((width - videoWidth)/2f, (height -videoHeight)/2f)

        matrix.preScale(videoWidth / width.toFloat(),videoHeight / height.toFloat())

        matrix.postScale(maxScale,maxScale, width/2f, height/2f)

        setTransform(matrix)

        postInvalidate()
    }

    /**
     * 重新计算video的显示位置，让其全部显示并据中
     */
    fun updateTextureViewSizeCenter(videoWidth:Int,videoHeight:Int){
        if(height ==0 || width ==0)return
        var sx = width.toFloat() / videoWidth.toFloat()
        var sy = height.toFloat() / videoHeight.toFloat()
        var minScale = sx.coerceAtMost(sy)

        var matrix = Matrix()
        matrix.reset()

        matrix.preTranslate((width - videoWidth)/2f, (height -videoHeight)/2f)

        matrix.preScale(videoWidth / width.toFloat(),videoHeight / height.toFloat())

        matrix.postScale(minScale, minScale, width / 2f, height/ 2f);

        setTransform(matrix)

        postInvalidate()
    }

    fun clearDraw(b:Boolean){
        if (b){
            val canvas = lockCanvas()
            canvas?.drawARGB(0,161,161,161)
            unlockCanvasAndPost(canvas!!)
        }

    }



}