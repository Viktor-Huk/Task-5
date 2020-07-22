package com.example.catapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catapi.R
import com.example.catapi.model.Cat


class CatAdapter(private val itemClickListener: ItemClickListener) : ListAdapter<Cat, CatViewHolder>(catDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_cat, parent, false)

        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)

        holder.catImageView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    companion object {

        private val catDiffUtilCallback = object : DiffUtil.ItemCallback<Cat>() {

            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}


