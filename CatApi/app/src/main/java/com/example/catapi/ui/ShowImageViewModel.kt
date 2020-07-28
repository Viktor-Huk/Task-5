package com.example.catapi.ui

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.LoadRequest
import com.example.catapi.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.io.FileOutputStream as FileOutputStream1

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

                val bitmap = image.value

                val fileName = "JPEG_$catId.jpg"

                val write: (OutputStream) -> Boolean? = {
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, QUALITY, it)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(
                            MediaStore.MediaColumns.RELATIVE_PATH,
                            "${Environment.DIRECTORY_DCIM}$ALBUM_NAME"
                        )
                    }

                    App.INSTANCE.contentResolver.let {
                        it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                            ?.let { uri ->
                                it.openOutputStream(uri)?.let(write)
                            }
                    }
                } else {

                    val imagesDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .toString() + ALBUM_NAME
                    val file = File(imagesDir)
                    if (!file.exists()) {
                        file.mkdir()
                    }
                    val image = File(imagesDir, fileName)
                    write(FileOutputStream1(image))
                }
            } catch (exc: IOException) {
                exc.printStackTrace()
            }
            _isSave.value = true
        }
    }

    companion object {
        private const val QUALITY = 100
        private const val ALBUM_NAME = "/cats"
    }
}
