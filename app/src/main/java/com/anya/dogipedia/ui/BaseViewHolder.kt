package com.anya.dogipedia.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

//  base view holder for list recycler
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}