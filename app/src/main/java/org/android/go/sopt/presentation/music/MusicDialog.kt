package org.android.go.sopt.presentation.music

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.databinding.DialogUploadMusicBinding
import org.android.go.sopt.util.extension.ContentUriRequestBody

class MusicDialog(
    context: Context,
    private val id: String,
    private val viewModel: MusicViewModel,
    private val imagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
) : Dialog(context) {
    lateinit var binding: DialogUploadMusicBinding
    lateinit var image: ContentUriRequestBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogUploadMusicBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }

    private fun addListeners() {
        binding.btnDialogUploadMusicSave.setOnClickListener {
            viewModel.uploadMusic(id, image.toFormData())
            dismiss()
        }

        binding.btnDialogUploadMusicSelectImage.setOnClickListener {
            imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }
}