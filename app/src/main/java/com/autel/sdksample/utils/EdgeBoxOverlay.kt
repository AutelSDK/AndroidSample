package com.autel.sdksample.visual.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.autel.sdksample.evo.visual.view.DynamicTrackView
import com.autel.sdksample.evo.visual.view.IntelTrackView
import com.autel.starlink.aircraft.camera.edgebox.Detection
import java.util.concurrent.Executors

/**
 * Created by A13087 on 2017/7/27.
 */
class EdgeBoxOverlay {

    val workThread = Executors.newCachedThreadPool()
    val mHandler = Handler(Looper.getMainLooper())
    var trackView: IntelTrackView? = null

    constructor(trackView: IntelTrackView) {
        this.trackView = trackView
    }

    fun clickPointForTouch(pix: IntArray, width: Int, height: Int, x: Float, y: Float) {
        if(null != mIdentifyCallback){
            mIdentifyCallback?.startIdentify()
        }

        workThread.execute {
            val result: IntArray = intArrayOf(0, -1, 0, 0, 0, 0)
            val thresh = 0.2f
            var starX: Float = 0f
            var starY: Float = 0f
            var starWidth: Float = 0f
            var starHeight: Float = 0f
            Log.d("fyh","bitmap:"+pix+" width:"+width+" height:"+height+" x:"+x+" y:"+y)
            Detection.Detect(pix, width, height, x, y, thresh, result)
            Log.d("fyh","Detection.Detect-------------")
            if (result[0] == 1 || result[0] == 2) {
                val corrds1: FloatArray = floatArrayOf(result[2].toFloat(), result[4].toFloat())
                val corrds2: FloatArray = floatArrayOf(result[3].toFloat(), result[5].toFloat())
                trackView?.getPaint()?.color = Color.GREEN
                starX = corrds1[0]
                starY = corrds1[1]
                starWidth = corrds2[0]
                starHeight = corrds2[1]

            } else {
                trackView?.getPaint()?.color = Color.RED
            }

            mHandler.post {
                //update ui
                if(null != mIdentifyCallback){
                    mIdentifyCallback?.finishPointIdentify(result[1])
                }
                trackView?.setEdgeBox(starX, starY, starWidth, starHeight)
            }
        }
    }

    fun classification(mBitmap: Bitmap, regLeft: Float, regTop: Float, regRight: Float, regBottom: Float) {
        if(null != mIdentifyCallback){
            mIdentifyCallback?.startIdentify()
        }
        workThread.execute {
            val mCurmap = Bitmap.createBitmap(mBitmap, regLeft.toInt(), regTop.toInt(), (regRight - regLeft).toInt(), (regBottom - regTop).toInt())
            val imageWidth = mCurmap.width
            val imageHeight = mCurmap.height
            val pix = IntArray(imageHeight * imageWidth)
            mCurmap.getPixels(pix, 0, imageWidth, 0, 0, imageWidth, imageHeight)
            val thresh = 0.2f
            val classId = Detection.Classify(pix, imageWidth, imageHeight, thresh)
            mHandler.post {
                //update ui
                trackView?.setCanReceiveRegData(true)
                trackView?.setEdgeBox(regLeft, regTop, regRight, regBottom)
                if(null != mIdentifyCallback){
                    mIdentifyCallback?.finishDrawIdentify(classId)
                }
            }
        }
    }
    var mIdentifyCallback : IntelTrackView.IdentifyCallback? = null
    fun setIdentifyCallback(callback: IntelTrackView.IdentifyCallback){
        this.mIdentifyCallback = callback
    }
}