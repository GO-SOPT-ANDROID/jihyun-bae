package org.android.go.sopt.home.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import org.android.go.sopt.databinding.DialogRepoAddBinding

class AddItemDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: DialogRepoAddBinding
    private lateinit var saveEventListener: SaveEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRepoAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        saveBtnClickListener()
    }

    private fun initViews() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }

    private fun saveBtnClickListener() {
        binding.btnDialogRepoAddSave.setOnClickListener {
            val repoName = binding.etDialogRepoAddRepoName.text.toString()
            val author = binding.etDialogRepoAddAuthor.text.toString()

            saveEventListener.sendInputData(repoName, author)
            dismiss()
        }
    }

    interface SaveEventListener {
        fun sendInputData(inputRepoName: String, inputAuthor: String)
    }

    fun setOnClickedListener(listener: SaveEventListener) {
        saveEventListener = listener
    }
}