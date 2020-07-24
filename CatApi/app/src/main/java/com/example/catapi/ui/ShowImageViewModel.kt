package com.example.catapi.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.LoadRequest
import com.example.catapi.App
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ShowImageViewModel : ViewModel() {

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    private val _isSave = MutableLiveData<Boolean>(null)
    val isSave: LiveData<Boolean> = _isSave

    fun loadImage(url: String) {

        viewModelScope.launch {

            val loader = ImageLoader(App.INSTANCE)

            val request = LoadRequest.Builder(App.INSTANCE)
                .data(url)
                .target {
                    _image.value = (it as BitmapDrawable).bitmap
                }
                .build()

            loader.execute(request)
        }
    }

    fun saveImage(catId: String) {
        viewModelScope.launch {

            try {
                val rootPath =
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).absolutePath + "/PicturesCatApi/"

                val root = File(rootPath)

                if (!root.exists()) {
                    root.mkdirs()
                }

                val fileName = "JPEG_$catId.jpg"

                val file = File(root.absolutePath, fileName)

                if (file.exists()) {
                    file.delete()
                }

                file.createNewFile()

                val bitmap = image.value
                val outputStream = FileOutputStream(file)

                bitmap?.compress(Bitmap.CompressFormat.JPEG, QUALITY, outputStream)
                outputStream.close()

                MediaScannerConnection.scanFile(
                    App.INSTANCE,
                    arrayOf(file.absolutePath),
                    null,
                    null
                )
            } catch (exc: IOException) {
                exc.printStackTrace()
            }
            _isSave.value = true
            Log.i("tag", "saved")
        }
    }

    companion object {
        private const val QUALITY = 100
    }
}
