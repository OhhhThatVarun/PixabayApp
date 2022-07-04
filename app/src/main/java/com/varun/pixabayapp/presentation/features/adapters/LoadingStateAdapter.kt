package com.varun.pixabayapp.presentation.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.varun.pixabayapp.databinding.ItemLoadingBinding


class LoadingStateAdapter(private val onRetryClicked: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onRetryClicked)
    }

    class ViewHolder(private val binding: ItemLoadingBinding, private val onRetryClicked: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBarItem.isVisible = loadState is LoadState.Loading
            binding.errorMsgItem.isVisible = loadState is LoadState.Error
            binding.retryBtn.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) binding.errorMsgItem.text = loadState.error.localizedMessage
            binding.retryBtn.setOnClickListener {
                onRetryClicked()
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        const val NETWORK_VIEW_TYPE = 1
    }
}