package org.android.go.sopt.home

import android.app.Activity
import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.home.dialog.LogoutDialog
import org.android.go.sopt.util.binding.BindingFragment


class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private lateinit var dialog: LogoutDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyPageData()
        setMyPageLogoutBtnClickListener()
    }

    private fun setMyPageLogoutBtnClickListener() {
        binding.btnMyPageLogout.setOnClickListener {
            showLogOutDialog()
        }
    }

    private fun showLogOutDialog() {
        dialog = LogoutDialog(requireContext())
        dialog.show()
    }

    private fun setMyPageData() {
        val sharedPreference =
            requireActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        binding.tvMyPageName.text = "이름 : ${sharedPreference.getString("name", "")}"
        binding.tvMyPageSpecialty.text = "특기 : ${sharedPreference.getString("specialty", "")}"
    }
}