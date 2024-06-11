package com.example.projectakhirbudaya

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.utils.reduceFileImage
import com.example.projectakhirbudaya.utils.uriToFile
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class AddBudayaActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"
    private var userPass: String = "123"
    private var userLevel: String = "user"

    private var currentImageUri: Uri? = null
    private lateinit var budayaViewModel: BudayaViewModel
    private lateinit var vNamaKebudayaan: TextInputEditText
    private lateinit var vLokasi: TextInputEditText
    private lateinit var vDeskripsi: TextInputEditText
    private lateinit var vImage: ImageView
    private lateinit var vText_img: TextView

    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            vImage.visibility = View.VISIBLE
            currentImageUri = firstImage.uri
            vText_img.setText("ubah")
            Glide.with(vImage)
                .load(firstImage.uri)
                .into(vImage)
        } else {
            View.GONE
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_budaya)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INTENT DATA
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "testing@gmail.com") ?: "testing@gmail.com"
        userPass = extras?.getString("password", "123") ?: "123"
        userLevel = extras?.getString("level", "user") ?: "user"

        val factory = BudayaViewModelFactory.getInstance(this) //ini
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java] //ini

        vNamaKebudayaan = findViewById(R.id.tf_nama)
        vLokasi = findViewById(R.id.tf_lokasi)
        vDeskripsi = findViewById(R.id.tf_desc)
        vImage = findViewById(R.id.img_budaya)
        vText_img = findViewById(R.id.text_img)
        onClick()
    }

    private fun onClick() {
        val openImagePicker = findViewById<ImageView>(R.id.img_budaya)
        openImagePicker.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Galeri"
                    isShowCamera = false
                    imageTitle = "Click to choice the image"
                    doneButtonText = "Done"
                }
            )
        }

        val btnSavedPlayer = findViewById<Button>(R.id.btn_tambah)
        btnSavedPlayer.setOnClickListener {
            if (validateInput()) {
                savedData()
            }
        }
    }

    private fun validateInput(): Boolean {
        var error = 0

        if (vNamaKebudayaan.text.toString().isEmpty()) {
            error++
            vNamaKebudayaan.error = "Nama tidak boleh kosong!"
        }
        if (vLokasi.text.toString().isEmpty()) {
            error++
            vLokasi.error = "Lokasi tidak boleh kosong!"
        }

        if (vDeskripsi.text.toString().isEmpty()) {
            error++
            vDeskripsi.error = "Deskripsi tidak boleh kosong!"
        }
        if (vText_img.text.toString() == "upload") {
            error++
            vText_img.error = "Gambar tidak boleh kosong!"
        }

        return error == 0
    }

    private fun savedData() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val namaText = vNamaKebudayaan.text.toString()
        val lokasiText = vLokasi.text.toString()
        val descText = vDeskripsi.text.toString()
        val newData = imageFile?.let {
            BudayaEntity(
                id = 0,
                name = namaText,
                location = lokasiText,
                description = descText,
                image = imageFile
            )
        }

        if (newData != null) budayaViewModel.insertBudaya(newData)

        Toast.makeText(
            this@AddBudayaActivity,
            "Data Berhasil Ditambahkan",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    fun toMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", userId)
        intent.putExtra("email", userEmail)
        intent.putExtra("password", userPass)
        intent.putExtra("level", userLevel)
        startActivity(intent)
    }
}