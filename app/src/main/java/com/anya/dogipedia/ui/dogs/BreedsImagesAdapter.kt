package com.anya.dogipedia.ui.dogs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anya.dogipedia.R
import com.anya.dogipedia.databinding.BreedImageListRowBinding
import com.anya.dogipedia.inject.module.GlideApp
import com.anya.dogipedia.ui.BaseViewHolder
import com.bumptech.glide.request.RequestOptions
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
        val itemBinding =
            BreedImageListRowBinding.inflate(LayoutInflater.from(context), parent, false)

        val holder = MainViewHolder(itemBinding)

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
//                            .placeholder(R.drawable.loading_animation)
//                            .error(R.drawable.ic_broken_image)
                        )
                        .into(imageView)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }
}