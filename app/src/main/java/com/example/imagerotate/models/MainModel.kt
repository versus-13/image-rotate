package com.example.imagerotate.models

import android.graphics.drawable.Drawable
import com.example.imagerotate.Utils

class MainModel {

    lateinit var originalImage: Drawable

    fun rotate(progress: Int): Drawable {
        return Utils.rotateImage(originalImage, progress.toDouble())
    }
}
