package com.example.catapi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catapi.R
import com.example.catapi.model.Cat


class CatAdapter : RecyclerView.Adapter<CatViewHolder>() {

    private val cats = mutableListOf<Cat>()

    private var isLoading = false
    private lateinit var onLoadMoreListener: OnLoadMoreListener

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun endLoading() {
        isLoading = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_cat, parent, false)

        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)

        if (!isLoading && holder.layoutPosition == itemCount - 5) {
            isLoading = true
            onLoadMoreListener.onLoadMore()
        }
    }

    override fun getItemCount() = cats.size

    fun refresh(listOfCats: List<Cat>) {
        cats.addAll(listOfCats)
        notifyDataSetChanged()
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

/*
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

 */
}
