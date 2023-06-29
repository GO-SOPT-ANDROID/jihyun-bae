package org.android.go.sopt.home.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.home.adapter.GalleryAdapter
import org.android.go.sopt.util.extension.ContentUriRequestBody

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectAdapter()
        btnGallerySelectImageClickListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun connectAdapter() {
        galleryAdapter = GalleryAdapter(requireContext())

        binding.vpGallery.adapter = galleryAdapter
    }

    private fun btnGallerySelectImageClickListener() {
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
}