package com.anya.dogipedia.ui.breeds

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anya.dogipedia.databinding.DogListRowBinding
import com.anya.dogipedia.ui.BaseViewHolder


class BreedsListAdapter (
    private val context: Context,
    private val itemClickListener: OnRowClickListener,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var breedsList = listOf<String>()

    // recycler row listener
    interface OnRowClickListener {
        fun onRowClick(dogDetail: String, position: Int)
    }

    fun setDogsList(dogsList: Map<String, List<String>>) {
        this.breedsList = dogsList.keys.toList()
        notifyDataSetChanged()
    }

    fun updateDogsList(dogsList: List<String>?) {
        this.breedsList = dogsList!!
        notifyDataSetChanged()
    }

    //create row view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        // bind row view
        val itemBinding = DogListRowBinding.inflate(LayoutInflater.from(context), parent, false)

        val holder = MainViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onRowClick(breedsList[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int = breedsList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(breedsList[position])
        }
    }


    inner class MainViewHolder(
        val binding: DogListRowBinding,
    ) : BaseViewHolder<String>(binding.root) {

        override fun bind(item: String): Unit = with(binding) {
            // attach data to bind row view
            item?.let {
                textViewName.text = item.capitalize()
            }

        }
    }
}

