package org.android.go.sopt.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.api.KakaoSearchServicePool
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.home.adapter.SearchAdapter
import org.android.go.sopt.home.adapter.SearchEmptyAdapter
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val kakaoSearchService = KakaoSearchServicePool.KakaoSearchService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchCheckBtnClickListener()
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

    private fun searchCheckBtnClickListener() {
        binding.btnSearchCheck.setOnClickListener {
            kakaoSearchService.getKakaoSearch(searchWord = binding.svSearch.query.toString())
                .enqueue(object : retrofit2.Callback<ResponseKakaoSearchDto> {
                    override fun onResponse(
                        call: Call<ResponseKakaoSearchDto>,
                        response: Response<ResponseKakaoSearchDto>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.documents?.let { documents ->
                                if (documents.isEmpty()) {
                                    connectSearchEmptyAdapter(binding.svSearch.query.toString())
                                } else {
                                    connectSearchAdapter(documents)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseKakaoSearchDto>, t: Throwable) {
                        t.message?.let { requireContext().showToast(it) } ?: run {
                            requireContext().showToast(getString(R.string.server_communication_on_failure))
                        }
                    }

                })
        }
    }
}