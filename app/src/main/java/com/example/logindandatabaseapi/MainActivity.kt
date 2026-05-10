package com.example.logindandatabaseapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.logindandatabaseapi.model.LoginRequest
import com.example.logindandatabaseapi.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.real_layout)

        // Hubungkan variabel dengan view di layout real_layout.xml
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Email dan password wajib diisi")
            return
        }

        lifecycleScope.launch {
            showLoading(true)
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.apiService.login(request)

                if (response.isSuccessful) {
                    val loginData = response.body()?.data
                    val userName = loginData?.user?.name.orEmpty()
                    val token = loginData?.token.orEmpty()

                    if (token.isNotEmpty()) {
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().putString("token", token).apply()
                        
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.EXTRA_NAME, userName)
                        startActivity(intent)
                        finish()
                    } else {
                        showMessage("Login gagal: Token tidak ditemukan")
                    }
                } else {
                    showMessage("Email atau password salah")
                }
            } catch (e: Exception) {
                showMessage("Koneksi gagal: ${e.message}")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        btnLogin.isEnabled = !isLoading
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
