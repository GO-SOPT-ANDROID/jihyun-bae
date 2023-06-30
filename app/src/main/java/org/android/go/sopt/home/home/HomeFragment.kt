package org.android.go.sopt.home.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.model.ResponseListUsersDto
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.ViewModelFactory
import org.android.go.sopt.home.adapter.TitleAdapter
import org.android.go.sopt.home.adapter.UserAdapter
import org.android.go.sopt.home.dialog.LoadingDialog
import org.android.go.sopt.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val titleViewModel by viewModels<TitleViewModel>()
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var dialog: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObservers()
        viewModel.getListUsers()
    }

    private fun addObservers() {
        viewModel.getListUserResult.observe(viewLifecycleOwner) { listUserResult ->
            connectAdapter(listUserResult.data)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoadingDialog() else dialog.dismiss()
        }
    }

    private fun connectAdapter(listUsers: List<ResponseListUsersDto.Data>) {
        val userAdapter = UserAdapter(requireContext())
        val titleAdapter = TitleAdapter(requireContext())
        userAdapter.submitList(listUsers)
        titleAdapter.submitList(titleViewModel.getHomeTitleList())

        val concatAdapter = ConcatAdapter(titleAdapter, userAdapter)

        binding.rvHomeUsers.adapter = concatAdapter
    }

    private fun showLoadingDialog() {
        dialog = LoadingDialog(requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}