package com.example.intentappfahim

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentappfahim.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    // Launcher untuk menerima hasil dari InputAddressActivity
    private val addressInputLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val address = data?.getStringExtra(MainActivity.EXTRA_ADDRESS)
                if (address != null) {
                    binding.tvCurrentAddress.text = address
                    Toast.makeText(this, "Alamat berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal mendapatkan alamat", Toast.LENGTH_SHORT).show()
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Input alamat dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data dari Intent
        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)
        val address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS)
        val profession = intent.getStringExtra(MainActivity.EXTRA_PROFESSION)
        val email = intent.getStringExtra(MainActivity.EXTRA_EMAIL)

        // Tampilkan data
        with(binding) {
            tvName.text = name ?: "Nama tidak tersedia"
            tvCurrentAddress.text = address ?: "Belum ada alamat"
            tvProfession.text = profession ?: "Profesi tidak tersedia"
            tvEmail.text = email ?: "Email tidak tersedia"

            btnEditAddress.setOnClickListener {
                val intentToInputAddress = Intent(this@SecondActivity, InputAddressActivity::class.java)
                // Kirim alamat saat ini dan nama user ke InputAddressActivity
                intentToInputAddress.putExtra("current_address", tvCurrentAddress.text.toString())
                intentToInputAddress.putExtra("user_name", name)
                addressInputLauncher.launch(intentToInputAddress)
            }

            btnBack.setOnClickListener {
                finish() // Kembali ke MainActivity
            }
        }
    }
}
