package com.berkhan.spacex.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.berkhan.spacex.R
import com.berkhan.spacex.databinding.FragmentLaunchDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailFragment : Fragment() {

    private val viewModel: LaunchDetailViewModel by viewModels()
    val args: LaunchDetailFragmentArgs by navArgs()

    private var _binding: FragmentLaunchDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaunchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLaunch()
        observeLaunchDetailUiState()
    }

    private fun getLaunch() {
        viewModel.getLaunch(args.launchId)
    }

    private fun observeLaunchDetailUiState() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { uiState ->
            handlePokeListScreenUiState(uiState)
        }
    }

    private fun handlePokeListScreenUiState(uiState: LaunchDetailUiState) {

        binding.pbLaunchLoading.visibility = uiState.getProgressBarVisibility()

        val isDataLoaded = uiState.getDataVisibility()

        with(binding) {

            if (isDataLoaded) {

                tvLaunchMissionName.text = uiState.data?.mission_name
                tvLaunchDetail.text = uiState.data?.details
                tvLaunchRocket.text = uiState.data?.rocket?.rocket_name

                tvLaunchMissionName.isVisible = isDataLoaded
                tvLaunchDetail.isVisible = isDataLoaded

                Glide.with(root)
                    .load(uiState.data?.links?.flickr_images?.firstOrNull())
                    .error(R.drawable.no_image)
                    .into(imLaunch)
            } else {
                tvLaunchError.isVisible = !isDataLoaded
                tvLaunchError.text = uiState.errorMessage
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}