package org.android.go.sopt.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.home.adapter.RepoAdapter
import org.android.go.sopt.home.data.Repo

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    private val mockRepoList = listOf<Repo>(
        Repo(
            image = R.drawable.github,
            name = "jihyunniiii",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Kream_Clone_App",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "DS_Project",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Android_Study",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "MobileSoftwareProject",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "Android",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "BOJ",
            author = "jihyunniiii"
        ),
        Repo(
            image = R.drawable.github,
            name = "git_exercise",
            author = "jihyunniiii"
        )
    )

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
        val adapter = RepoAdapter(requireContext())
        adapter.setRepoList(mockRepoList)
        binding.rvHomeRepos.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}