package com.varun.pixabayapp.presentation.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.varun.pixabayapp.R
import com.varun.pixabayapp.databinding.FragmentDetailBinding
import com.varun.pixabayapp.presentation.extensions.getDataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getDataBinding(R.layout.fragment_detail, container)
        return binding.apply {
            image = args.image
            imageView.setOnClickListener(onImageClicked)
            backButton.setOnClickListener(onBackClicked)
        }.root
    }

    private val onImageClicked = View.OnClickListener {
        binding.infoLayout.isVisible = !binding.infoLayout.isVisible
    }

    private val onBackClicked = View.OnClickListener {
        findNavController().navigateUp()
    }
}