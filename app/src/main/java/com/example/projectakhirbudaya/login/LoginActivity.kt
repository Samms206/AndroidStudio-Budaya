package com.example.projectakhirbudaya.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirbudaya.MainActivity
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.user.UserViewActivity
import com.example.projectakhirbudaya.utils.DependencyInjection
import com.example.projectakhirbudaya.viewmodel.UserViewModel
import com.example.projectakhirbudaya.viewmodel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showDataUser()

        val repository = DependencyInjection.provideUserRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.tf_email)
        val passwordEditText = findViewById<EditText>(R.id.tf_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            userViewModel.login(email, password) { user ->
                if (user != null) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    if (user.level == "admin") {
                        Intent(this, MainActivity::class.java).also {
                            it.putExtra("id", user.id)
                            it.putExtra("email", user.email)
                            startActivity(it)
                        }
                    } else if (user.level == "user") {
                        Intent(this, UserViewActivity::class.java).also {
                            it.putExtra("id", user.id)
                            it.putExtra("email", user.email)
                            startActivity(it)
                        }
                    }
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun toRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun showDataUser(){
        //Menampilkan data di tabel user
        val repository = DependencyInjection.provideUserRepository(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]
        userViewModel.getAllUser().observe(this) { userList ->
            for (user in userList) {
                Log.d(
                    "DatabaseData",
                    "ID: ${user.id}, Email: ${user.email}, Password: ${user.password}, Level: ${user.level}"
                )
            }
        }
    }
}