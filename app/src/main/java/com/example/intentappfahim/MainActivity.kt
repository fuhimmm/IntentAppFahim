package com.example.intentappfahim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentappfahim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Demo aplikasi Android Activity Intent
    // Fitur:
    // 1. Navigasi antar Activity dengan Intent
    // 2. Mengirim data ke Activity lain melalui Intent extras
    // 3. Menerima data kembali dari Activity dengan ActivityResult

    lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_NAME = "data_name"
        const val EXTRA_ADDRESS = "data_address"
        const val EXTRA_PROFESSION = "data_profession"
        const val EXTRA_EMAIL = "data_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MAINACTIVITY", "onCreate dipanggil")

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Setup any initial UI state if needed
        Log.d("MAINACTIVITY", "UI setup completed")
    }

    private fun setupClickListeners() {
        with(binding) {
            btnToSecondActivity.setOnClickListener {
                navigateToSecondActivity()
            }
        }
    }

    private fun navigateToSecondActivity() {
        Toast.makeText(this, "Navigasi ke Second Activity...", Toast.LENGTH_SHORT).show()

        val intentToSecondActivity = Intent(this@MainActivity, SecondActivity::class.java).apply {
            putExtra(EXTRA_NAME, "Ahmad Fahim")
            putExtra(EXTRA_PROFESSION, "Android Developer")
            putExtra(EXTRA_EMAIL, "ahmad.fahim@example.com")
            putExtra(EXTRA_ADDRESS, "Palembang, Indonesia")
        }

        startActivity(intentToSecondActivity)
        Log.d("MAINACTIVITY", "Intent sent to SecondActivity with user data")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MAINACTIVITY", "onStart dipanggil")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MAINACTIVITY", "onResume dipanggil")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MAINACTIVITY", "onPause dipanggil")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MAINACTIVITY", "onStop dipanggil")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MAINACTIVITY", "onDestroy dipanggil")
    }
}
