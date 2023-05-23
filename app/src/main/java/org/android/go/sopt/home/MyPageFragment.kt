package org.android.go.sopt.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.home.dialog.LogoutDialog


class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private lateinit var dialog: LogoutDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyPageData()
        setMyPageLogoutBtnClickListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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