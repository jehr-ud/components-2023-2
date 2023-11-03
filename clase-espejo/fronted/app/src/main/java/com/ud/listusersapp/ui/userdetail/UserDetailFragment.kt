package com.ud.listusersapp.ui.userdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.ud.listuserfront.models.User
import com.ud.listusersapp.R
import com.ud.listusersapp.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val USER_ID = "user_id"
class UserDetailFragment : Fragment() {
    private var userID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userID = it.getString(USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)

        val baseURL = getString(R.string.api_base_url)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewPassword = view.findViewById<TextView>(R.id.textViewPassword)
        val textViewId = view.findViewById<TextView>(R.id.textViewId)
        val textViewUsername = view.findViewById<TextView>(R.id.textViewUsername)

        // Call the service to get user details by ID
        val call = userID?.let { userService.getUserById(it) }

        Log.d("UserDetail", userID.toString())

        call?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("UserDetail", "response")

                if (response.isSuccessful) {
                    Log.d("UserDetail", "isok")
                    val user = response.body()
                    if (user != null) {
                        textViewName.text = "Name: " + user.name
                        textViewPassword.text = "Password: " + user.password
                        textViewId.text = "ID: " + user.id
                        textViewUsername.text = "Username: " + user.username
                    }
                } else{
                    Log.d("UserDetail", "error1")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("UserDetail", "error2")
            }
        })

        val btnDeleteUser = view.findViewById<Button>(R.id.btnDeleteUser)

        if (userID != null){
            btnDeleteUser.setOnClickListener {
                deleteUser()
            }
        }

        return view
    }

    private fun deleteUser() {
        val baseURL = getString(R.string.api_base_url)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)
        val call = userService.deleteUserById(userID)

        val textViewMessage = view?.findViewById<TextView>(R.id.textViewMessage)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    goToListUsers()
                } else {
                    val errorBody = response.errorBody()?.string()
                    textViewMessage?.text = "Error eliminando al usuario" + errorBody
                    textViewMessage?.isVisible = true
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                textViewMessage?.text = "Error eliminando al usuario"
                textViewMessage?.isVisible = true
            }
        })
    }

    fun goToListUsers(){
        findNavController().navigate(R.id.navigation_list_users)
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
    }
}