package com.grupo3.historyar.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivityLoginBinding
import com.grupo3.historyar.models.toDomain
import com.grupo3.historyar.ui.view_models.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth : FirebaseAuth
    private val userViewModel: UserViewModel by viewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignIn(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        initUI()
        initGoogleOptions()
        userViewModel.userIsSaved.observe(this, Observer {
            if (it)
                navigateToAppActivity()
            else {
                auth.signOut()
                Toast.makeText(this, "Esta cuenta esta inactiva. Por favor, comunicate con el soporte para reactivarla", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

    private fun initUI() {
        binding.btnSignIn.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            launcher.launch(intent)
            startActivity(intent)
        }
    }

    private fun initGoogleOptions() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, options)
    }

    private fun handleSignIn(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) setFirebaseUser(account)
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFirebaseUser(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                userViewModel.saveUser(account.toDomain())
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToAppActivity() {
        val intent = Intent(this, AppActivity::class.java)
        startActivity(intent)
    }
}