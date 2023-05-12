package org.android.go.sopt.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.data.remote.api.ListUsersServicePool
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.adapter.UserAdapter
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val listUsersService = ListUsersServicePool.listUsersService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun connectAdapter(listUsers: List<ResponseListUsersDto.Data>) {
        val userAdapter = UserAdapter(requireContext())
        userAdapter.submitList(listUsers)

        binding.rvHomeUsers.adapter = userAdapter
    }

    private fun setListUsers() {
        listUsersService.getListUsers().enqueue(object : retrofit2.Callback<ResponseListUsersDto> {
            override fun onResponse(
                call: Call<ResponseListUsersDto>,
                response: Response<ResponseListUsersDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { connectAdapter(it) }
                }
            }

            override fun onFailure(call: Call<ResponseListUsersDto>, t: Throwable) {
                t.message?.let { requireContext().showToast(it) } ?: "서버통신 실패(응답값 X)"
            }
        })
    }
}