package org.android.go.sopt.home.music

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.data.remote.model.ResponseMusicDto
import org.android.go.sopt.databinding.FragmentMusicBinding
import org.android.go.sopt.home.adapter.MusicAdapter
import org.android.go.sopt.home.dialog.LoadingDialog

class MusicFragment : Fragment() {
    private var _binding: FragmentMusicBinding? = null
    private val binding: FragmentMusicBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<MusicViewModel>()
    private lateinit var id: String
    private lateinit var dialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setId()
        Log.e("music", id)
        viewModel.getMusicList(id)
        getListMusicResultObserver()
        isLoadingObserver()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setId() {
        val sharedPreference =
            requireActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        id = sharedPreference.getString("id", "").toString()
    }

    private fun connectAdapter(musicList: List<ResponseMusicDto.MusicData>) {
        val musicAdapter = MusicAdapter(requireContext())
        musicAdapter.submitList(musicList)
        binding.rvMusicMusics.adapter = musicAdapter
    }

    private fun getListMusicResultObserver() {
        viewModel.getListMusicResult.observe(viewLifecycleOwner) { listMusicResult ->
            connectAdapter(listMusicResult.data.musicList)
            Log.e("music", "nn")
        }
    }

    private fun isLoadingObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoadingDialog() else dialog.dismiss()
        }
    }

    private fun showLoadingDialog() {
        dialog = LoadingDialog(requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}

