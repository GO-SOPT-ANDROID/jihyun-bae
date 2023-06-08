package org.android.go.sopt.home.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class RepoFragment : Fragment() {
    private var _binding: FragmentRepoBinding? = null
    private val binding: FragmentRepoBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<RepoViewModel>()
    private lateinit var selectionTracker: SelectionTracker<Long>
    private lateinit var homeAdapter: RepoAdapter
    private lateinit var dialog: AddItemDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectAdapter()
        setUpSelectionTracker()
        setSelectedItemDeleteObserver()
        setFabHomeAddClickListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun connectAdapter() {
        homeAdapter = RepoAdapter(requireContext())
        homeAdapter.submitList(viewModel.getMockHomeList())

        binding.rvRepoRepos.adapter = homeAdapter
    }

    private fun setUpSelectionTracker() {
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

    private fun setSelectedItemDeleteObserver() {
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    setFabHomeDeleteListener()
                }
            }
        )
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

    private fun setFabHomeAddClickListener() {
        binding.fabRepoAdd.setOnClickListener {
            showAddItemDialog()
            addItem()
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