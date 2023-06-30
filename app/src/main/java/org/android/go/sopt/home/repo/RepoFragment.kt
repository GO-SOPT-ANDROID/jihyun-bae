package org.android.go.sopt.home.repo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentRepoBinding
import org.android.go.sopt.home.adapter.RepoAdapter
import org.android.go.sopt.home.data.Repo
import org.android.go.sopt.home.dialog.AddItemDialog
import org.android.go.sopt.util.binding.BindingFragment

class RepoFragment : BindingFragment<FragmentRepoBinding>(R.layout.fragment_repo) {
    private val viewModel by viewModels<RepoViewModel>()
    private lateinit var selectionTracker: SelectionTracker<Long>
    private lateinit var homeAdapter: RepoAdapter
    private lateinit var dialog: AddItemDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectAdapter()
        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        selectionTracker = binding.rvRepoRepos.let { recyclerView ->
            SelectionTracker.Builder(
                "mySelection",
                recyclerView,
                StableIdKeyProvider(recyclerView),
                RepoAdapter.RepoDetailLookUp(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }
        homeAdapter.selectionTracker = selectionTracker
    }

    private fun addListeners() {
        binding.fabRepoAdd.setOnClickListener {
            showAddItemDialog()
            addItem()
        }

        binding.fabRepoDelete.setOnClickListener {
            selectionTracker.selection.forEach { selectedItem ->
                val selectedItemViewHolder =
                    binding.rvRepoRepos.findViewHolderForItemId(selectedItem)

                if (selectedItemViewHolder is RepoAdapter.RepoViewHolder) {
                    homeAdapter.removeListItem(selectedItem.toInt())
                }
            }
            selectionTracker.clearSelection()
        }
    }

    private fun addObservers() {
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    setFabHomeDeleteListener()
                }
            }
        )
    }

    private fun connectAdapter() {
        homeAdapter = RepoAdapter(requireContext())
        homeAdapter.submitList(viewModel.getMockHomeList())

        binding.rvRepoRepos.adapter = homeAdapter
    }

    private fun setFabHomeDeleteListener() {
        binding.fabRepoDelete.setOnClickListener {
            selectionTracker.selection.forEach { selectedItem ->
                val selectedItemViewHolder =
                    binding.rvRepoRepos.findViewHolderForItemId(selectedItem)

                if (selectedItemViewHolder is RepoAdapter.RepoViewHolder) {
                    homeAdapter.removeListItem(selectedItem.toInt())
                }
            }
            selectionTracker.clearSelection()
        }
    }

    private fun showAddItemDialog() {
        dialog = AddItemDialog(requireContext())
        dialog.show()
    }

    private fun addItem() {
        dialog.setOnClickedListener(object : AddItemDialog.SaveEventListener {
            override fun sendInputData(inputRepoName: String, inputAuthor: String) {
                homeAdapter.addListItem(
                    Repo(
                        image = R.drawable.github,
                        name = inputRepoName,
                        author = inputAuthor
                    )
                )
            }
        })
    }
}