package org.android.go.sopt.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.domain.model.SearchDocument
import org.android.go.sopt.presentation.common.ViewModelFactory
import org.android.go.sopt.util.UiState
import org.android.go.sopt.util.binding.BindingFragment
import timber.log.Timber

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
        addObservers()
    }

    private fun addObservers() {
        viewModel.getKakaoSearchState.observe(viewLifecycleOwner) { searchDocuments ->
            when (searchDocuments) {
                is UiState.Success -> {
                    connectSearchAdapter(searchDocuments.data)
                }

                is UiState.Empty -> {
                    connectSearchEmptyAdapter(binding.svSearch.query.toString())
                }

                else -> {
                    Timber.e(getString(R.string.ui_state_false))
                }
            }
        }
    }

    private fun addListeners() {
        binding.btnSearchCheck.setOnClickListener {
            viewModel.getKakaoSearch(binding.svSearch.query.toString())
        }
    }

    private fun connectSearchAdapter(searchInfo: List<SearchDocument>) {
        val searchAdapter = SearchAdapter(requireContext())
        searchAdapter.submitList(searchInfo)
        binding.rvSearch.adapter = searchAdapter
    }

    private fun connectSearchEmptyAdapter(query: String) {
        val searchEmptyAdapter = SearchEmptyAdapter(requireContext())
        val queryList = mutableListOf<String>()
        queryList.add(query)
        searchEmptyAdapter.submitList(queryList)
        binding.rvSearch.adapter = searchEmptyAdapter
    }
}