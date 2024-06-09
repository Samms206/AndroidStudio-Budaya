package com.example.projectakhirbudaya

import android.annotation.SuppressLint
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
import java.io.File

class EditBudayaActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null

    private lateinit var budayaViewModel: BudayaViewModel
    private lateinit var vNamaKebudayaan: TextInputEditText
    private lateinit var vLokasi: TextInputEditText
    private lateinit var vDeskripsi: TextInputEditText
    private lateinit var vImage: ImageView
    private lateinit var vText_img: TextView

    private lateinit var getData: BudayaEntity

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
        setContentView(R.layout.activity_edit_budaya)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getData = intent.getParcelableExtra("dataBudaya")!!
        //
        val factory = BudayaViewModelFactory.getInstance(this) //ini
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java] //ini

        vNamaKebudayaan = findViewById(R.id.tf_nama_edit)
        vLokasi = findViewById(R.id.tf_lokasi_edit)
        vDeskripsi = findViewById(R.id.tf_desc_edit)
        vImage = findViewById(R.id.img_budaya_edit)
        vText_img = findViewById(R.id.text_img_edit)

        vNamaKebudayaan.setText(getData!!.name)
        vLokasi.setText(getData!!.location)
        vDeskripsi.setText(getData!!.description)
        vText_img.setText("ubah")

        oldPhoto = getData.image
        Glide.with(vImage)
            .load(getData.image)
            .into(vImage)
        onClick()
    }

    private fun onClick() {
        val openImagePicker = findViewById<ImageView>(R.id.img_budaya_edit)
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

        val btnSavedPlayer = findViewById<Button>(R.id.btn_edit)
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

        val updateData = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
            val namaText = vNamaKebudayaan.text.toString()
            val lokasiText = vLokasi.text.toString()
            val descText = vDeskripsi.text.toString()
            BudayaEntity(
                id = getData.id,
                name = namaText,
                location = lokasiText,
                description = descText,
                image = it
            )
        }

        if (updateData != null) budayaViewModel.updateBudaya(updateData)

        Toast.makeText(
            this@EditBudayaActivity,
            "Data Berhasil Diupdate",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

}