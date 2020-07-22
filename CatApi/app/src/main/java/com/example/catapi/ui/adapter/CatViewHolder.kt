package com.example.catapi.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.catapi.R
import com.example.catapi.model.Cat

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var catImageView: ImageView = view.findViewById(R.id.catImageView)

    fun bind(cat: Cat) {
        catImageView.load(cat.url)
    }
}
