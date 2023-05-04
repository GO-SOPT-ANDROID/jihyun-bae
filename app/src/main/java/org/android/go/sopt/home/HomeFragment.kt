package org.android.go.sopt.home

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
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.adapter.HomeAdapter
import org.android.go.sopt.home.data.Home
import org.android.go.sopt.home.dialog.AddItemDialog

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var selectionTracker: SelectionTracker<Long>
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var dialog: AddItemDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectAdapter()
        setUpSelectionTracker()
        setSelectedItemDeleteObserver()
        setFabHomeAddListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun connectAdapter() {
        homeAdapter = HomeAdapter(requireContext())
        homeAdapter.submitList(viewModel.mockHomeList)

        binding.rvHomeRepos.adapter = homeAdapter
    }

    private fun setUpSelectionTracker() {
        selectionTracker = binding.rvHomeRepos.let { recyclerView ->
            SelectionTracker.Builder(
                "mySelection",
                recyclerView,
                StableIdKeyProvider(recyclerView),
                HomeAdapter.HomeDetailLookUp(recyclerView),
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
        binding.fabHomeDelete.setOnClickListener {
            selectionTracker.selection.forEach { selectedItem ->
                val selectedItemViewHolder =
                    binding.rvHomeRepos.findViewHolderForItemId(selectedItem)

                if (selectedItemViewHolder is HomeAdapter.RepoViewHolder) {
                    homeAdapter.removeListItem(selectedItem.toInt())
                }
            }
            selectionTracker.clearSelection()
        }
    }

    private fun setFabHomeAddListener() {
        binding.fabHomeAdd.setOnClickListener {
            showDialog()
            addItem()
        }
    }

    private fun showDialog() {
        dialog = AddItemDialog(requireContext())
        dialog.show()
    }

    private fun addItem() {
        dialog.setOnClickedListener(object : AddItemDialog.SaveEventListener {
            override fun sendInputData(inputRepoName: String, inputAuthor: String) {
                homeAdapter.addListItem(
                    Home(
                        image = R.drawable.github,
                        name = inputRepoName,
                        author = inputAuthor
                    )
                )
            }
        })
    }
}