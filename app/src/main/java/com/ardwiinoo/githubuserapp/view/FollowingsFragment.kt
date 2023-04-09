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
import com.ardwiinoo.githubuserapp.databinding.FragmentFollowingsBinding
import com.ardwiinoo.githubuserapp.model.FollowingsViewModel

class FollowingsFragment : Fragment() {

    private val followingsViewModel by viewModels<FollowingsViewModel>()
    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingsBinding.inflate(
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
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        followingsViewModel.getFollowingsUser(username)

        // pantau perubahan data
        followingsViewModel.followingsUser.observe(viewLifecycleOwner) { items ->
            binding.rvFollowing.adapter = showFragmentRecycler(items)
        }

        followingsViewModel.isLoadingFollowing.observe(viewLifecycleOwner) {
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
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}