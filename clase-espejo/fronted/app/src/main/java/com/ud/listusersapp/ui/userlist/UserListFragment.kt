package com.ud.listusersapp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ud.listuserfront.models.User
import com.ud.listusersapp.R
import com.ud.listusersapp.adapters.UserListAdapter
import com.ud.listusersapp.databinding.FragmentUserListBinding
import com.ud.listusersapp.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserListFragment : Fragment(),  UserListAdapter.OnUserItemClickListener {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(UserListViewModel::class.java)

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userRecyclerView = binding.userRecyclerView
        userListAdapter = UserListAdapter(requireContext(), mutableListOf())
        userListAdapter.setOnUserItemClickListener(this)

        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerView.adapter = userListAdapter

        fetchUserList()

        return root
    }


    override fun onUserItemClick(user: User) {
        if (user.id != null){
            val bundle = Bundle()
            bundle.putString("user_id", user.id)
            findNavController().navigate(R.id.navigation_detail_user, bundle)
        }
    }

    private fun fetchUserList() {
        val baseUrl = getString(R.string.api_base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)
        val call = userService.getUsers()

        binding.textViewFailed.isVisible = false

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null){
                        userListAdapter.updateUsers(users)
                    }
                } else {
                    binding.textViewFailed.text = getString(R.string.msg_failed_fetch_users)
                    binding.textViewFailed.isVisible = true
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                binding.textViewFailed.text = getString(R.string.msg_failed_fetch_users)
                binding.textViewFailed.isVisible = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}