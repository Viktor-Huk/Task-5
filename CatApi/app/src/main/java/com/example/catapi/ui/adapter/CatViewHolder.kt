package com.example.catapi.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.catapi.R

class CatViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var catImageView = view.findViewById<ImageView>(R.id.catImageView)

    fun bind(url: String?) {
        catImageView.load(url)
    }
}