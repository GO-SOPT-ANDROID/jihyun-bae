package org.android.go.sopt.presentation.mypage

import android.app.Activity
import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.util.CustomSnackBar
import org.android.go.sopt.util.CustomToast
import org.android.go.sopt.util.binding.BindingFragment


class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private lateinit var dialog: LogoutDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val sharedPreference =
            requireActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        binding.tvMyPageName.text = "이름 : ${sharedPreference.getString("name", "")}"
        binding.tvMyPageSpecialty.text = "특기 : ${sharedPreference.getString("specialty", "")}"
    }

    private fun addListeners() {
        binding.btnMyPageLogout.setOnClickListener {
            showLogOutDialog()
            CustomToast.makeToast(requireContext(), "정말로 로그아웃 하시겠습니까?")?.show()
            view?.let { view -> CustomSnackBar.makeSnackBar(view, "정말로 로그아웃 하시겠습니까?") }
        }
    }

    private fun showLogOutDialog() {
        dialog = LogoutDialog(requireContext())
        dialog.show()
    }
}