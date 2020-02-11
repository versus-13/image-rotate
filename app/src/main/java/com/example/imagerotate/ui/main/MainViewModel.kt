package com.example.imagerotate.ui.main

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.imagerotate.models.MainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(), CoroutineScope by MainScope() {
    var model: MainModel = MainModel()
    val image = ObservableField<Drawable>()
    var inited = false // это костыль, если надо, могу потом убрать, сорри.

    private val seekBarData = ObservableField<Int>()

    fun setOriginalImage(image: Drawable) {
        if (!inited) {
            model.originalImage = image
            this.image.set(image)
            inited = true
        }
    }

    fun onProgress(progress: Int) {
        seekBarData.set(progress)
        launch {
            image.set(model.rotate(progress))
        }
    }
}
