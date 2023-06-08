package org.android.go.sopt.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.home.adapter.SearchAdapter
import org.android.go.sopt.home.adapter.SearchEmptyAdapter

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchCheckBtnClickListener()
        getKakaoSearchResultObserver()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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

    private fun getKakaoSearchResultObserver() {
        viewModel.getKakaoSearchResult.observe(viewLifecycleOwner) { kakaoSearchResult ->
            kakaoSearchResult.documents.let { documents ->
                if (documents.isEmpty()) connectSearchEmptyAdapter(binding.svSearch.query.toString())
                else connectSearchAdapter(documents)
            }
        }
    }

    private fun searchCheckBtnClickListener() {
        binding.btnSearchCheck.setOnClickListener {
            viewModel.getKakaoSearch(binding.svSearch.query.toString())
        }
    }
}