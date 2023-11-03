package com.ud.listusersapp.ui.usercreate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ud.listusersapp.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ud.listuserfront.models.User
import com.ud.listusersapp.databinding.FragmentUserCreateBinding
import com.ud.listusersapp.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserCreateFragment : Fragment() {

    private var _binding: FragmentUserCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(UserCreateViewModel::class.java)

        _binding = FragmentUserCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonCreateUser.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val password = binding.editTextPassword.text.toString()
            val username = binding.editTextUsername.text.toString()

            // Validate the fields
            if (name.isEmpty()) {
                binding.editTextName.error = getString(R.string.msg_required_name)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.editTextPassword.error = getString(R.string.msg_required_password)
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                binding.editTextUsername.error = getString(R.string.msg_required_user)
                return@setOnClickListener
            }

            createUser(name, password, username)
        }
        return root
    }

    private fun createUser(name: String, password: String, username: String){
        val user = User(null, name, password, username)
        val baseURL = getString(R.string.api_base_url)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)

        val call = userService.createUser(user)

        binding.textViewMessage.isVisible = false

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // User creation successful
                    val createdUser = response.body()

                    Log.d("CreateUser", "successes creation" + createdUser.toString())

                    binding.textViewMessage.text = getText(R.string.user_creation_ok)
                    goToListUsers()
                } else {
                    val errorBody = response.errorBody()?.string()
                    binding.textViewMessage.text = getText(R.string.user_creation_error).toString() + errorBody
                    binding.textViewMessage.isVisible = true
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                binding.textViewMessage.text = getText(R.string.user_creation_error)
                binding.textViewMessage.isVisible = true
            }
        })
    }

    fun goToListUsers(){
        findNavController().navigate(R.id.navigation_list_users)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}