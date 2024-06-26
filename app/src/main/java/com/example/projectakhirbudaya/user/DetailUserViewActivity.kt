package com.example.projectakhirbudaya.user

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projectakhirbudaya.MainActivity
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.room.BudayaSavedEntity
import com.example.projectakhirbudaya.utils.reduceFileImage
import com.example.projectakhirbudaya.utils.uriToFile
import com.example.projectakhirbudaya.viewmodel.BudayaSavedViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaSavedViewModelFactory
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory
import com.google.android.material.imageview.ShapeableImageView
import java.io.File

class DetailUserViewActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"
    private var userPass: String = "123"
    private var userLevel: String = "user"

    private lateinit var budayaViewModel: BudayaSavedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_user_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = BudayaSavedViewModelFactory.getInstance(this) //ini
        budayaViewModel = ViewModelProvider(this, factory)[BudayaSavedViewModel::class.java] //ini

        //INTENT DATA
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "testing@gmail.com") ?: "testing@gmail.com"
        userPass = extras?.getString("password", "123") ?: "123"
        userLevel = extras?.getString("level", "user") ?: "user"

        val getDataNama = intent.getStringExtra("namaBudaya")
        val getDataLokasi= intent.getStringExtra("lokasiBudaya")
        val getDataDescription = intent.getStringExtra("descBudaya")
        val getDataImage = intent.getStringExtra("imgBudaya")

        val title = findViewById<TextView>(R.id.txt_nama_dtl)
        val location = findViewById<TextView>(R.id.txt_lokasi_dtl)
        val desc = findViewById<TextView>(R.id.txt_desc_dtl)
        val image = findViewById<ShapeableImageView>(R.id.img_dtl)
        val btnSave = findViewById<Button>(R.id.btn_savePin)
        val btnShare = findViewById<Button>(R.id.btn_share)

        title.text = getDataNama
        location.text = getDataLokasi
        desc.text = getDataDescription
        Glide.with(this)
            .load(getDataImage)
            .into(image)

        //Button Save
        btnSave.setOnClickListener {
            val imagePath = getDataImage ?: return@setOnClickListener
            val imageFile = File(imagePath).reduceFileImage()
            val newData =
                BudayaSavedEntity(
                    id = 0,
                    idUser = userId.toString(),
                    name = title.text.toString(),
                    location = location.text.toString(),
                    description = desc.text.toString(),
                    image = imageFile
                )

            if (newData != null) budayaViewModel.insertBudayaSaved(newData)

            Toast.makeText(
                this@DetailUserViewActivity,
                "Data Berhasil Ditambahkan",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }

        //Button Share
        btnShare.setOnClickListener {
            val namaBudaya = title.text.toString()
            val descBudaya = desc.text.toString()
            val imgBudayaPath = getDataImage ?: return@setOnClickListener

            // Membuat URI dari path gambar
            val imgUri: Uri = File(imgBudayaPath).toUri()

            // Membuat Intent untuk berbagi ke WhatsApp
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, imgUri)
                putExtra(Intent.EXTRA_TEXT, "Nama Budaya: $namaBudaya\nDeskripsi: $descBudaya")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage("com.whatsapp")
            }

            val whatsappInstalled = isPackageInstalled("com.whatsapp") || isPackageInstalled("com.whatsapp.w4b")
            if (whatsappInstalled) {
                shareIntent.setPackage("com.whatsapp")
                startActivity(shareIntent)
            } else {
                Toast.makeText(this, "WhatsApp tidak terinstal.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun toMainUser(view: View) {
        val intent = Intent(this, UserViewActivity::class.java)
        intent.putExtra("id", userId)
        intent.putExtra("email", userEmail)
        intent.putExtra("password", userPass)
        intent.putExtra("level", userLevel)
        startActivity(intent)
    }
}