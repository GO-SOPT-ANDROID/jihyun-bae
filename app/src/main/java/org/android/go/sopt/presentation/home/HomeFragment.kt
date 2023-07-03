package org.android.go.sopt.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.domain.model.ListUser
import org.android.go.sopt.presentation.common.ViewModelFactory
import org.android.go.sopt.util.UiState
import org.android.go.sopt.util.binding.BindingFragment
import timber.log.Timber

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
        viewModel.getListUserState.observe(viewLifecycleOwner) { listUserState ->
            when (listUserState) {
                is UiState.Success -> {
                    connectAdapter(listUserState.data)
                }

                else -> {
                    Timber.e(getString(R.string.ui_state_false))
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoadingDialog() else dialog.dismiss()
        }
    }

    private fun connectAdapter(listUsers: List<ListUser>) {
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