package com.ardwiinoo.githubuserapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardwiinoo.githubuserapp.adapter.UserListAdapter
import com.ardwiinoo.githubuserapp.data.User
import com.ardwiinoo.githubuserapp.databinding.FragmentFollowersBinding
import com.ardwiinoo.githubuserapp.model.FollowersViewModel

class FollowersFragment : Fragment() {

    private val followersViewModel by viewModels<FollowersViewModel>()
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get username
        username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)

        followersViewModel.getFollowersUser(username)

        // pantau perubahan data
        followersViewModel.followersUser.observe(viewLifecycleOwner) { items ->
            binding.rvFollower.adapter = showFragmentRecycler(items)
        }

        followersViewModel.isLoadingFollower.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFragmentRecycler(items: List<User>?): UserListAdapter {
        val listUsers = ArrayList<User>()

        items?.let {
            listUsers.addAll(it)
        }

        return UserListAdapter(listUsers)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBarFollower.visibility = View.VISIBLE
        } else {
            binding.progressBarFollower.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
