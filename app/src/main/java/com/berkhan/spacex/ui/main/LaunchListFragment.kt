package com.berkhan.spacex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.berkhan.spacex.common.FooterLoadStateAdapter
import com.berkhan.spacex.databinding.FragmentLaunchListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchListFragment : Fragment() {

    private val viewModel: LaunchListViewModel by viewModels()
    private val launchAdapter = LaunchAdapter()

    private var _binding: FragmentLaunchListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaunchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPagingationAdapter()
        initListener()
        requestData()
        collectData()
        observeLoadState()
    }

    private fun initPagingationAdapter() {
        binding.launchList.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.launchList.adapter = // ERROR LOADİNG, EMPTY LIST CODELABDAN BAK
            launchAdapter.withLoadStateFooter(footer = FooterLoadStateAdapter { launchAdapter.retry() })
    }

    private fun initListener() {
        launchAdapter.setOnItemClickListener(::navigateLaunchDetailPage)
        binding.btnRetry.setOnClickListener { launchAdapter.retry() }
    }

    private fun navigateLaunchDetailPage(launchId: String) {
        val action =
            LaunchListFragmentDirections.actionLaunchListFragmentToLaunchDetailFragment(launchId)
        findNavController().navigate(action)
    }

    private fun requestData() {
        viewModel.getLaunches()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.launchesFlow.collectLatest {
                launchAdapter.submitData(it)
            }
        }
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            launchAdapter.loadStateFlow.collect {
                initView(it)
            }
        }
    }

    private fun initView(loadState: CombinedLoadStates) {
        val isListEmpty =
            (loadState.refresh is LoadState.NotLoading) && launchAdapter.itemCount == 0
        val isThereError = loadState.source.refresh is LoadState.Error
        val listVisibility =
            (loadState.source.refresh is LoadState.Loading) || (!isListEmpty && !isThereError)

        with(binding) {
            pbLaunchList.isVisible = loadState.source.refresh is LoadState.Loading
            btnRetry.isVisible = isThereError
            tvLaunchListError.isVisible = isThereError
            launchList.isVisible = listVisibility
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}