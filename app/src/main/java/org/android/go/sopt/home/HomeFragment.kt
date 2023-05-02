package org.android.go.sopt.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.adapter.HomeAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var selectionTracker: SelectionTracker<Long>
    private lateinit var homeAdapter: HomeAdapter

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
        homeAdapter.selectionTracker = selectionTracker
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
                HomeAdapter.RepoKeyProvider(homeAdapter),
                HomeAdapter.RepoDetailLookUp(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }
    }
}