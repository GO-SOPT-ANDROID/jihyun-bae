package org.android.go.sopt.presentation.gallery

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.util.extension.ContentUriRequestBody

class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        initLayout()
    }

    private fun addListeners() {
        val launcher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
                imageUri?.let {
                    viewModel.setRequestBody(ContentUriRequestBody(requireContext(), it))
                    viewModel.uploadImage()
                    galleryAdapter.addListItem(it)
                }
            }

        binding.btnGallerySelectImage.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    private fun initLayout() {
        galleryAdapter = GalleryAdapter(requireContext())

        binding.vpGallery.adapter = galleryAdapter
    }
}