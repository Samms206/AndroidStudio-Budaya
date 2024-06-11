package com.example.projectakhirbudaya.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirbudaya.PopUpFragment
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.adapter.BudayaAdapterForUser
import com.example.projectakhirbudaya.adapter.BudayaAdapterRoom
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory
import com.google.android.material.textview.MaterialTextView
import kotlin.math.log

class UserViewActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"

    private lateinit var budayaViewModel: BudayaViewModel
    private lateinit var budayaAdapter : BudayaAdapterForUser
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INTENT DATA
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "testing@gmail.com") ?: "testing@gmail.com"

        val factory = BudayaViewModelFactory.getInstance(this)
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java]
        recyclerView = findViewById(R.id.rv_budayaUser)
        recyclerView.layoutManager = LinearLayoutManager(this)

        budayaViewModel.getAllBudaya().observe(this) { data ->
            if (data != null) {
                budayaAdapter = BudayaAdapterForUser(data, budayaViewModel) //ini
                recyclerView.adapter = budayaAdapter

                budayaAdapter.setOnItemClickCallback(object :
                    BudayaAdapterForUser.OnItemClickCallback{
                    override fun onClicked(data: BudayaEntity, position: Int) {
                        showSelectedData(data)
                    }
                })
            }
        }

        val iconHome = findViewById<ImageView>(R.id.icon_home)
        val iconBookmart = findViewById<ImageView>(R.id.icon_bookmart)
        iconBookmart.setOnClickListener{
            val intent = Intent(this, SavedBudayaActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("email", userEmail)
            startActivity(intent)
        }
        val iconProfile = findViewById<ImageView>(R.id.icon_profile)
        iconProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("email", userEmail)
            startActivity(intent)
        }

    }


    private fun showSelectedData(data: BudayaEntity) {
        // Membuat intent untuk berpindah ke DetailPlayerActivity
        val navigateToDetail = Intent(this, DetailUserViewActivity::class.java)

        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
        navigateToDetail.putExtra("id", userId)
        navigateToDetail.putExtra("email", userEmail)
        navigateToDetail.putExtra("namaBudaya", data.name)
        navigateToDetail.putExtra("lokasiBudaya", data.location)
        navigateToDetail.putExtra("descBudaya", data.description)
        navigateToDetail.putExtra("imgBudaya", data.image.toString())

        // Memulai activity baru
        startActivity(navigateToDetail)
    }
}