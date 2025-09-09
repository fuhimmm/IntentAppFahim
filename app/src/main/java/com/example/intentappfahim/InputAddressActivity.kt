package com.example.intentapp_fahim

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentapp_fahim.databinding.ActivityInputAddressBinding

class InputAddressActivity : AppCompatActivity() {

    lateinit var binding: ActivityInputAddressBinding

    private var currentAddress: String = ""
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityInputAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retrieveDataFromIntent()
        setupUI()
        setupClickListeners()
        setupValidation()
    }

    private fun retrieveDataFromIntent() {
        // Mengambil data yang dikirim dari SecondActivity
        currentAddress = intent.getStringExtra("current_address") ?: "Belum ada alamat"
        userName = intent.getStringExtra("user_name") ?: "User"
    }

    private fun setupUI() {
        with(binding) {
            // Update welcome message dengan nama user
            tvWelcomeUser.text = "Halo $userName, masukkan alamat lengkap Anda"

            // Tampilkan alamat saat ini
            tvCurrentAddress.text = currentAddress

            // Pre-fill EditText jika alamat sudah ada dan bukan default message
            if (currentAddress != "Belum ada alamat" &&
                currentAddress != "Alamat belum dilengkapi" &&
                currentAddress.isNotEmpty()) {
                edtAddress.setText(currentAddress)
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            btnSubmit.setOnClickListener {
                submitAddress()
            }

            btnCancel.setOnClickListener {
                cancelInput()
            }
        }
    }

    private fun setupValidation() {
        binding.edtAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInput(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateInput(input: String) {
        with(binding) {
            when {
                input.isEmpty() -> {
                    showValidationMessage("Alamat tidak boleh kosong")
                    btnSubmit.isEnabled = false
                }
                input.length < 10 -> {
                    showValidationMessage("Alamat terlalu pendek, minimal 10 karakter")
                    btnSubmit.isEnabled = false
                }
                else -> {
                    hideValidationMessage()
                    btnSubmit.isEnabled = true
                }
            }
        }
    }

    private fun showValidationMessage(message: String) {
        with(binding) {
            tvValidationMessage.text = message
            tvValidationMessage.visibility = View.VISIBLE
        }
    }

    private fun hideValidationMessage() {
        binding.tvValidationMessage.visibility = View.GONE
    }

    private fun submitAddress() {
        val addressInput = binding.edtAddress.text.toString().trim()

        if (addressInput.isEmpty()) {
            showValidationMessage("Alamat tidak boleh kosong")
            return
        }

        if (addressInput.length < 10) {
            showValidationMessage("Alamat terlalu pendek, minimal 10 karakter")
            return
        }

        // Kirim data kembali ke SecondActivity
        val resultIntent = Intent().apply {
            putExtra(MainActivity.EXTRA_ADDRESS, addressInput)
        }

        setResult(Activity.RESULT_OK, resultIntent)
        Toast.makeText(this, "Alamat berhasil disimpan!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun cancelInput() {
        Toast.makeText(this, "Input alamat dibatalkan", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}