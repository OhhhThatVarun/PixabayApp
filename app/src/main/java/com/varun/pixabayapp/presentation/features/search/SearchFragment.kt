package com.varun.pixabayapp.presentation.features.search

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.varun.pixabayapp.R
import com.varun.pixabayapp.databinding.FragmentSearchBinding
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.presentation.extensions.getDataBinding
import com.varun.pixabayapp.presentation.extensions.showSnackBar
import com.varun.pixabayapp.presentation.extensions.showSnackBarWithRetry
import com.varun.pixabayapp.presentation.features.adapters.LoadingStateAdapter
import com.varun.pixabayapp.presentation.features.adapters.PixabayImageAdapter
import com.varun.pixabayapp.presentation.features.adapters.PixabayImageAdapter.Companion.IMAGE_VIEW_TYPE
import com.varun.pixabayapp.presentation.utils.ItemOffsetDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter = PixabayImageAdapter(::onImageClicked)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getDataBinding(R.layout.fragment_search, container)
        return binding.apply {
            viewModel = this@SearchFragment.viewModel
            setUpRecyclerView()
            adapter = this@SearchFragment.adapter
            adapter?.withLoadStateFooter(LoadingStateAdapter(::onRetryClicked))
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.images.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {

                }
                is LoadState.NotLoading -> {
                    if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                        showSnackBar(R.string.no_results_found)
                    }
                }
                is LoadState.Error -> {
                    showSnackBarWithRetry(R.string.some_error_occurred) {
                        viewModel.retrySearch()
                    }
                    logcat(LogPriority.ERROR) {
                        (loadState.refresh as LoadState.Error).error.localizedMessage ?: "Unknown error"
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        val itemDecoration = ItemOffsetDecoration(requireContext(), R.dimen.item_margin)
        binding.images.addItemDecoration(itemDecoration)
        val currentOrientation = resources.configuration.orientation
        val gridLayoutSpan = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
        val gridLayoutManager = GridLayoutManager(requireContext(), gridLayoutSpan)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if (viewType == IMAGE_VIEW_TYPE) LoadingStateAdapter.NETWORK_VIEW_TYPE else gridLayoutSpan
            }
        }
        binding.images.layoutManager = gridLayoutManager
    }

    private fun onImageClicked(image: Image) {
        showConfirmationDialog {
            openDetail(image)
        }
    }

    private fun onRetryClicked() {
        viewModel.retrySearch()
    }

    private fun showConfirmationDialog(onPositiveButtonClicked: () -> Unit) {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(true)
            setTitle(R.string.confirmation)
            setMessage(R.string.really_want_to_see_detail)
            setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.cancel()
                onPositiveButtonClicked()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        }.show()
    }

    private fun openDetail(image: Image) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(image))
    }
}