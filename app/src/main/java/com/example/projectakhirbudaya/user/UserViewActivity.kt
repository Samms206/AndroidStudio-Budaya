package com.example.projectakhirbudaya.user

import android.os.Bundle
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

class UserViewActivity : AppCompatActivity() {

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

        val factory = BudayaViewModelFactory.getInstance(this)
        budayaViewModel = ViewModelProvider(this, factory)[BudayaViewModel::class.java]
        recyclerView = findViewById(R.id.rv_budayaUser)
        recyclerView.layoutManager = LinearLayoutManager(this)

        budayaViewModel.getAllBudaya().observe(this) { data ->
            if (data != null) {
                budayaAdapter = BudayaAdapterForUser(data, budayaViewModel) //ini
                recyclerView.adapter = budayaAdapter

//                budayaAdapter.setOnItemClickCallback(object :
//                    BudayaAdapterRoom.OnItemClickCallback {
//                    override fun onMoreClicked(data: BudayaEntity, position: Int) {
//                        PopUpFragment(data, position).show(supportFragmentManager, PopUpFragment.TAG)
//                    }
//                })
            }
        }
    }
}