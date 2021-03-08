package com.anya.dogipedia.ui.dogs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.BreedImageListRowBinding
import com.anya.dogipedia.inject.module.GlideApp
import com.anya.dogipedia.ui.BaseViewHolder
import com.anya.dogipedia.ui.dogs.dogImageView.PageViewActivity
import com.bumptech.glide.request.RequestOptions
import java.io.Serializable
import java.lang.Exception

class BreedsImagesAdapter(
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var breedsImageList = listOf<String>()

    fun setDogsImageList(dogsList: ArrayList<String>) {
        this.breedsImageList = dogsList
        notifyDataSetChanged()
    }

    //create row view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        // bind row view
        val itemBinding = BreedImageListRowBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = MainViewHolder(itemBinding)

        // onClick on Image item, opens new activity with view pager
        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            val intent = Intent(it.context, PageViewActivity::class.java).apply {
                putExtra("currentImage", position)
                putExtra("data", breedsImageList as Serializable)
            }
            it.context.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount(): Int = breedsImageList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is BreedsImagesAdapter.MainViewHolder -> holder.bind(breedsImageList[position])
        }
    }

    inner class MainViewHolder(
        val binding: BreedImageListRowBinding,
    ) : BaseViewHolder<String>(binding.root) {

        override fun bind(item: String): Unit = with(binding) {
            // attach data to bind row view
            item?.let {
                try {
                    GlideApp.with(context)
                        .load(it)
                        .apply(
                            RequestOptions()
                            .placeholder(R.drawable.ic_loading_animation)
                            .error(R.drawable.ic_error)
                        )
                        .into(imageView)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }
}