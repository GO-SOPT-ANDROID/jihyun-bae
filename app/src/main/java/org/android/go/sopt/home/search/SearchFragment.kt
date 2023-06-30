package org.android.go.sopt.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.home.adapter.SearchAdapter
import org.android.go.sopt.home.adapter.SearchEmptyAdapter
import org.android.go.sopt.util.binding.BindingFragment

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
        addObservers()
    }

    private fun addObservers() {
        viewModel.getKakaoSearchResult.observe(viewLifecycleOwner) { kakaoSearchResult ->
            kakaoSearchResult.documents.let { documents ->
                if (documents.isEmpty()) connectSearchEmptyAdapter(binding.svSearch.query.toString())
                else connectSearchAdapter(documents)
            }
        }
    }

    private fun addListeners() {
        binding.btnSearchCheck.setOnClickListener {
            viewModel.getKakaoSearch(binding.svSearch.query.toString())
        }
    }

    private fun connectSearchAdapter(searchInfo: List<ResponseKakaoSearchDto.Document>) {
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