package com.example.catapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catapi.R
import com.example.catapi.model.Cat

class CatAdapter : RecyclerView.Adapter<CatViewHolder>() {

    private var items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_cat, parent, false)

        return CatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val url = items[position].imageUrl
        holder.bind(url)
    }

    fun addItems(list: List<Cat>) {
        items.addAll(list)
        notifyDataSetChanged()
    }
}