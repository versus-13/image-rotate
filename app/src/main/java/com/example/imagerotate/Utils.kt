package com.example.imagerotate

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import java.nio.ByteBuffer
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

object Utils {
    fun rotateImage(image: Drawable, angle: Double): Drawable {

        var d = image.toBitmap()

        var width = d.getWidth()
        var height = d.getHeight()

        val size: Int = d.getRowBytes() * d.getHeight()
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(size)
        d.copyPixelsToBuffer(byteBuffer)
        var byteArray = byteBuffer.array()

        val h = 400
        var OfsZ = 400

        val alpha: Double = 3.1415 * angle / 180.0
        val cos_alpha = cos(alpha)
        val sin_alpha = sin(alpha)

        OfsZ += (d.width * abs(sin_alpha) / 2).toInt() // для приближения проекции

        val mx = d.width / 2
        val my = d.height / 2
        val newByteArray = ByteArray(size)
        for (x in -mx until mx) {
            for (y in -my until my) {

                val px: Double = x * cos_alpha
                val py: Double = y.toDouble()
                val pz: Double = - x * sin_alpha

                val k: Double = h / (pz + OfsZ)

                val x1: Int = round(px * k).toInt() + mx
                val y1: Int = round(py * k).toInt() + my


                if (x1 >= 0 && x1 < d.width && y1 >= 0 && y1 < d.height) {
                    val i = 4 * ((y + my) * d.width + x + mx)
                    val j = 4 * (y1 * d.width + x1)
                    newByteArray[i + 0] = byteArray[j + 0]
                    newByteArray[i + 1] = byteArray[j + 1]
                    newByteArray[i + 2] = byteArray[j + 2]
                    newByteArray[i + 3] = byteArray[j + 3]
                }
            }
        }

        val configBmp = d.getConfig()
        val bitmap_tmp = Bitmap.createBitmap(width, height, configBmp)
        val buffer = ByteBuffer.wrap(newByteArray)
        bitmap_tmp.copyPixelsFromBuffer(buffer)

        return BitmapDrawable(bitmap_tmp)
    }
}