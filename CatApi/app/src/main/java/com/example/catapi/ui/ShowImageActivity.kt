package com.example.catapi.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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

        val url = intent.extras?.get(CAT_URL).toString()
        catId = intent.extras?.get(CAT_ID).toString()

        viewModel.image.observe(this, Observer {
            binding.fullImageView.setImageBitmap(it)
        })
        viewModel.loadImage(url)

        viewModel.isSave.observe(this, Observer {
            when (it) {
                true -> Toast.makeText(this, TRUE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()

                false -> Toast.makeText(this, FALSE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()
            }
        })
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
                    viewModel.saveImage(catId)
                } else {
                    Toast.makeText(this, FALSE_RESPONSE_SAVE, Toast.LENGTH_LONG).show()
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

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE
                    )
                } else {
                    viewModel.saveImage(catId)
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1
        const val TRUE_RESPONSE_SAVE = "IMAGE SAVED"
        const val FALSE_RESPONSE_SAVE = "IMAGE NOT SAVED"
        const val NEED_PERMISSION_STORAGE = "WE NEED PERMISSION STORAGE"

        const val CAT_URL = "cat_url"
        const val CAT_ID = "cat_id"
    }
}
