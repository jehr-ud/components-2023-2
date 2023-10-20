package com.ud.candicrushud

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ud.candicrushud.databinding.ActivityHomeBinding
import com.ud.candicrushud.utils.HandleMenu
import com.google.firebase.FirebaseApp;

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToGame()
        }

        initGoogleSignInClient()

        binding.btnPlayHome.setOnClickListener{
            signInWithGoogle()
        }

        binding.btnLoginEmail.setOnClickListener{
            saveUserData(binding.editTextEmail.text.toString(), "Nombre")
            goToGame()
        }
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("819328156754-1rcd3tng6qurk63irakjd65secbr302e.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e(TAG, "Google Sign In failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        saveUserData(it.uid, it.displayName)
                    }

                    goToGame()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun goToGame(){
        val intent = Intent(this, CreationGameActivity::class.java)
        startActivity(intent)
    }

    private fun saveUserData(userId: String, userName: String?) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 10
        private const val TAG = "LoginActivity"
        private const val PREFS_NAME = "Candy-crushUD"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USER_NAME = "userName"
    }
}