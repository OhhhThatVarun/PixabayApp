package com.varun.pixabayapp.presentation.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.varun.pixabayapp.databinding.ItemImageBinding
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.presentation.features.adapters.LoadingStateAdapter.Companion.NETWORK_VIEW_TYPE


class PixabayImageAdapter(private val onImageClicked: (Image) -> Unit) : PagingDataAdapter<Image, PixabayImageAdapter.ViewHolder>(ImageComparator) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { image -> holder.bind(image) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onImageClicked)
    }

    object ImageComparator : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) NETWORK_VIEW_TYPE else IMAGE_VIEW_TYPE
    }

    class ViewHolder(private val binding: ItemImageBinding, private val onImageClicked: (Image) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.image = image
            binding.root.setOnClickListener {
                onImageClicked(image)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        const val IMAGE_VIEW_TYPE = 2
    }
}