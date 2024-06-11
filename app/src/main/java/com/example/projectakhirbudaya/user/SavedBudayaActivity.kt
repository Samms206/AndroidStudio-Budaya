package com.example.projectakhirbudaya.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirbudaya.PopUpFragment
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.adapter.BudayaAdapterForSaved
import com.example.projectakhirbudaya.adapter.BudayaAdapterForUser
import com.example.projectakhirbudaya.adapter.BudayaAdapterRoom
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.room.BudayaSavedEntity
import com.example.projectakhirbudaya.viewmodel.BudayaSavedViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaSavedViewModelFactory
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModelFactory

class SavedBudayaActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"

    private lateinit var budayaViewModel: BudayaSavedViewModel
    private lateinit var budayaAdapter : BudayaAdapterForSaved
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saved_budaya)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INTENT DATA
        val extras = intent.extras
        userId = extras?.getInt("id", -1) ?: -1
        userEmail = extras?.getString("email", "testing@gmail.com") ?: "testing@gmail.com"

        //
        val factory = BudayaSavedViewModelFactory.getInstance(this)
        budayaViewModel = ViewModelProvider(this, factory)[BudayaSavedViewModel::class.java]
        recyclerView = findViewById(R.id.rv_budayaSaved)
        recyclerView.layoutManager = LinearLayoutManager(this)

        budayaViewModel.getAllBudayaByUserId(userId.toString()).observe(this) { data ->
            if (data != null) {
                budayaAdapter = BudayaAdapterForSaved(data, budayaViewModel) //ini
                recyclerView.adapter = budayaAdapter

                budayaAdapter.setOnItemClickCallback(object :
                    BudayaAdapterForSaved.OnItemClickCallback {
                    override fun onDeleteClicked(data: BudayaSavedEntity, position: Int) {
                       budayaViewModel.deleteBudayaSaved(data)
                    }

                })
            }
        }
    }

    fun toMainUser(view: View) {
        val intent = Intent(this, UserViewActivity::class.java)
        intent.putExtra("id", userId)
        intent.putExtra("email", userEmail)
        startActivity(intent)
    }
}