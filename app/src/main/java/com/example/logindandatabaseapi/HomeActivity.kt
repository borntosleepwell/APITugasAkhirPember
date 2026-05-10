package com.example.logindandatabaseapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logindandatabaseapi.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var rvPasien: RecyclerView
    private lateinit var pbHome: ProgressBar
    private lateinit var tvName: TextView
    private lateinit var btnLogout: ImageButton

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inisialisasi View
        rvPasien = findViewById(R.id.rvPasien)
        pbHome = findViewById(R.id.pbHome)
        tvName = findViewById(R.id.tvName)
        btnLogout = findViewById(R.id.btnLogout)

        // Setup RecyclerView
        rvPasien.layoutManager = LinearLayoutManager(this)

        // Tampilkan nama user dari intent
        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()
        tvName.text = name

        // Setup tombol logout
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        // Ambil data pasien
        fetchDataPasien()
    }

    private fun fetchDataPasien() {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "").orEmpty()

        if (token.isEmpty()) {
            logout()
            return
        }

        lifecycleScope.launch {
            pbHome.visibility = View.VISIBLE
            try {
                // Menambahkan prefix Bearer sesuai ketentuan
                val response = RetrofitClient.apiService.getPasien("Bearer $token")
                
                if (response.isSuccessful) {
                    val listPasien = response.body()?.data ?: emptyList()
                    rvPasien.adapter = PasienAdapter(listPasien)
                    
                    if (listPasien.isEmpty()) {
                        Toast.makeText(this@HomeActivity, "Data pasien kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal mengambil data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                pbHome.visibility = View.GONE
            }
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Logout") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        // Hapus token dari SharedPreferences
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        prefs.edit().remove("token").apply()

        // Kembali ke MainActivity dan tutup semua Activity di atasnya
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
