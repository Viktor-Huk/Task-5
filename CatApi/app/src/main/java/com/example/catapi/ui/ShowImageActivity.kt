package com.example.catapi.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.catapi.R
import com.example.catapi.databinding.ActivityShowImageBinding

class ShowImageActivity : AppCompatActivity() {

    private val viewModel by viewModels<ShowImageViewModel>()
    private lateinit var binding: ActivityShowImageBinding
    private lateinit var catId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.showActivityToolBar)

        val url = intent.extras?.get(MainActivity.CAT_URL).toString()
        catId = intent.extras?.get(MainActivity.CAT_ID).toString()

        viewModel.image.observe(this, Observer {
            binding.fullImageView.setImageBitmap(it)
        })
        viewModel.loadImage(url)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("tag", "saveOnRequest")
                    viewModel.saveImage(catId)
                } else {
                    Toast.makeText(this, FALSE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()
                    Log.i("tag", "We Need permission Storage")
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.show_image_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save_image -> {

                viewModel.isSave.observe(this, Observer {
                    when (it) {
                        true -> Toast.makeText(this, TRUE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()

                        false -> Toast.makeText(this, FALSE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()
                    }
                })

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.i("tag", "check")
                    checkPermissionWriteStorage()
                } else {
                    Log.i("tag", "save")
                    viewModel.saveImage(catId)

                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissionWriteStorage() {

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, NEED_PERMISSION_STORAGE, Toast.LENGTH_LONG).show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE
                )

                Log.i("tag", "request")
            }

        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1
        const val TRUE_RESPONSE_SAVE = "IMAGE SAVED"
        const val FALSE_RESPONSE_SAVE = "IMAGE NOD SAVED"
        const val NEED_PERMISSION_STORAGE = "WE NEED PERMISSION STORAGE"
    }
}
