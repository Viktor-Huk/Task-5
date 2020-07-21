package com.example.catapi.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.catapi.R
import com.example.catapi.model.Cat
import com.example.catapi.ui.ShowImageActivity

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var catImageView: ImageView = view.findViewById(R.id.catImageView)

    fun bind(cat: Cat) {
        catImageView.load(cat.url)
    }
}
