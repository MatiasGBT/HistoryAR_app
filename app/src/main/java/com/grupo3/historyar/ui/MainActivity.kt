package com.grupo3.historyar.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.*
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        if (isUserLoggedIn())
            navigateToAppActivity()
        else
            navigateToLoginActivity()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (!isUserLoggedIn())
            navigateToLoginActivity()
        /*if (intent.action == Intent.ACTION_VIEW) {
            val data: Uri? = intent.data
            data?.let {
                val tourId = it.lastPathSegment
                if (tourId != null) {
                    navigateToAppActivity(tourId)
                }
            }
        }*/
    }

    private fun isUserLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    private fun navigateToAppActivity(tourId: String? = null) {
        val intent = Intent(this, AppActivity::class.java)
        if (tourId != null)
            intent.putExtra("tour_id", tourId)
        startActivity(intent)
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}