package org.android.go.sopt.presentation.music

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.api.load
import org.android.go.sopt.R
import org.android.go.sopt.data.model.response.ResponseMusicDto
import org.android.go.sopt.databinding.FragmentMusicBinding
import org.android.go.sopt.presentation.ViewModelFactory
import org.android.go.sopt.presentation.home.LoadingDialog
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.util.extension.ContentUriRequestBody

class MusicFragment : BindingFragment<FragmentMusicBinding>(R.layout.fragment_music) {
    private val viewModel: MusicViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var id: String
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var musicDialog: MusicDialog
    private lateinit var imagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        addObservers()
        setId()
        viewModel.getMusicList(id)
        initImagePickerLauncher()
    }

    private fun addListeners() {
        binding.fabMusicMain.setOnClickListener {
            showMusicDialog()
        }
    }

    private fun addObservers() {
        viewModel.getListMusicResult.observe(viewLifecycleOwner) { listMusicResult ->
            connectAdapter(listMusicResult)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoadingDialog() else loadingDialog.dismiss()
        }
    }

    private fun setId() {
        val sharedPreference =
            requireActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        id = sharedPreference.getString("id", "").toString()
    }

    private fun connectAdapter(musicList: ResponseMusicDto.MusicList) {
        val musicAdapter = MusicAdapter(requireContext())
        musicAdapter.submitList(musicList.musicList)
        binding.rvMusicMusics.adapter = musicAdapter
    }

    private fun showLoadingDialog() {
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.show()
    }

    private fun showMusicDialog() {
        musicDialog = MusicDialog(requireContext(), id, viewModel, imagePickerLauncher)
        musicDialog.show()
    }

    private fun initImagePickerLauncher() {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
                imageUri?.let {
                    musicDialog.binding.ivDialogUploadMusicImage.load(imageUri)
                    musicDialog.image = ContentUriRequestBody(requireContext(), it)
                }
            }
    }
}

