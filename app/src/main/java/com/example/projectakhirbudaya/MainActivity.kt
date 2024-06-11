package com.example.projectakhirbudaya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirbudaya.adapter.BudayaAdapterRoom
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.user.ProfileActivity
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory

class MainActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"
    private var userPass: String = "123"
    private var userLevel: String = "user"

    private lateinit var budayaViewModel: BudayaViewModel
    private lateinit var budayaAdapter : BudayaAdapterRoom
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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

        val txtWelcome = findViewById<TextView>(R.id.txt_welcome_admin)
        txtWelcome.text = "Halo $userEmail"

        val factory = BudayaViewModelFactory.getInstance(this)
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java]
        recyclerView = findViewById(R.id.rv_budaya)
        recyclerView.layoutManager = LinearLayoutManager(this)

        budayaViewModel.getAllBudaya().observe(this) { data ->
            if (data != null) {
                budayaAdapter = BudayaAdapterRoom(data, budayaViewModel) //ini
                recyclerView.adapter = budayaAdapter

                budayaAdapter.setOnItemClickCallback(object :
                    BudayaAdapterRoom.OnItemClickCallback {
                    override fun onMoreClicked(data: BudayaEntity, position: Int) {
                        PopUpFragment(data, position).show(supportFragmentManager, PopUpFragment.TAG)
                    }
                })
            }
        }
    }

    fun toAddData(view: View) {
        val intent = Intent(this, AddBudayaActivity::class.java)
        startActivity(intent)
    }

    fun forProfileAdmin(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("id", userId)
        intent.putExtra("email", userEmail)
        intent.putExtra("password", userPass)
        intent.putExtra("level", userLevel)
        startActivity(intent)
    }

}