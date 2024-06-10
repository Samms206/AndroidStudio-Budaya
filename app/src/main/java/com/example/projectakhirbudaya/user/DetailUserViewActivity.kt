package com.example.projectakhirbudaya.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.projectakhirbudaya.MainActivity
import com.example.projectakhirbudaya.R
import com.google.android.material.imageview.ShapeableImageView

class DetailUserViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_user_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val getDataNama = intent.getStringExtra("namaBudaya")
        val getDataLokasi= intent.getStringExtra("lokasiBudaya")
        val getDataDescription = intent.getStringExtra("descBudaya")
        val getDataImage = intent.getStringExtra("imgBudaya")

        val title = findViewById<TextView>(R.id.txt_nama_dtl)
        val location = findViewById<TextView>(R.id.txt_lokasi_dtl)
        val desc = findViewById<TextView>(R.id.txt_desc_dtl)
        val image = findViewById<ShapeableImageView>(R.id.img_dtl)

        Log.d(
            "DetailData",
            "Nama: ${getDataNama}, Image: ${getDataImage}"
        )

        title.text = getDataNama
        location.text = getDataLokasi
        desc.text = getDataDescription
//        image.setImageResource(getDataImage)
        Glide.with(this)
            .load(getDataImage)
            .into(image)
    }

    fun toMainUser(view: View) {
        val intent = Intent(this, UserViewActivity::class.java)
        startActivity(intent)
    }
}