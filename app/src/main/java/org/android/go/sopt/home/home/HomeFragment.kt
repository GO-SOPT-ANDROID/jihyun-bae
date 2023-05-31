package org.android.go.sopt.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.TitleViewModel
import org.android.go.sopt.home.adapter.TitleAdapter
import org.android.go.sopt.home.adapter.UserAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val titleViewModel by viewModels<TitleViewModel>()
    private val viewModel by viewModels<HomeViewModel>()

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
        viewModel.getListUsers()
        getListUserResultObserver()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun connectAdapter(listUsers: List<ResponseListUsersDto.Data>) {
        val userAdapter = UserAdapter(requireContext())
        val titleAdapter = TitleAdapter(requireContext())
        userAdapter.submitList(listUsers)
        titleAdapter.submitList(titleViewModel.getHomeTitleList())

        val concatAdapter = ConcatAdapter(titleAdapter, userAdapter)

        binding.rvHomeUsers.adapter = concatAdapter
    }

    private fun getListUserResultObserver() {
        viewModel.getListUserResult.observe(viewLifecycleOwner) { listUserResult ->
            connectAdapter(listUserResult.data)
        }
    }
}