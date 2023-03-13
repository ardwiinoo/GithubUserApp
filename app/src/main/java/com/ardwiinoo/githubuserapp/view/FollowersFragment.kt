package com.ardwiinoo.githubuserapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardwiinoo.githubuserapp.R
import com.ardwiinoo.githubuserapp.adapter.UserListAdapter
import com.ardwiinoo.githubuserapp.databinding.FragmentFollowersBinding
import com.ardwiinoo.githubuserapp.model.FollowersViewModel

class FollowersFragment : Fragment() {

    private lateinit var followerAdapter: UserListAdapter
    private val followersViewModel by viewModels<FollowersViewModel>()
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private var username: String = "username"

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            username = it.getString(ARG_USERNAME).toString()
        }

        val layoutManager = LinearLayoutManager(requireActivity())
    }
}