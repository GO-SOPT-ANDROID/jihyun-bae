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
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.adapter.RepoAdapter
import org.android.go.sopt.home.adapter.TitleAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var selectionTracker: SelectionTracker<Long>
    private lateinit var repoAdapter: RepoAdapter

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

        val titleAdapter = TitleAdapter(requireContext())
        repoAdapter = RepoAdapter(requireContext())
        titleAdapter.submitList(viewModel.mockTitleList)
        repoAdapter.submitList(viewModel.mockRepoList)

        val concatAdapter = ConcatAdapter(titleAdapter, repoAdapter)
        binding.rvHomeRepos.adapter = concatAdapter

        setUpSelectionTracker()
        setSelectionTracker(selectionTracker, repoAdapter, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    private fun setUpSelectionTracker() {
        selectionTracker = binding.rvHomeRepos.let {recyclerView ->
            SelectionTracker.Builder(
                "mySelection",
                recyclerView,
                RepoAdapter.RepoKeyProvider(repoAdapter),
                RepoAdapter.SelectionDetailLookUp(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }
    }

    private fun setSelectionTracker(
        selectionTracker: SelectionTracker<Long>,
        adapter: RepoAdapter,
        savedInstanceState: Bundle?
    ) {
        savedInstanceState.let {
            selectionTracker.onRestoreInstanceState(it)
        }
        adapter.selectionTracker = selectionTracker
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                selectionTracker.selection
            }
        })
    }
}