package com.autel.sdksample.visual.utils

/**
 * Created by A13087 on 2017/7/26.
 */
object TrackingUtils {

    fun checkValidValue(value: Float): Boolean {
        return value > 0 && value < 1
    }


}