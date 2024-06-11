package com.example.projectakhirbudaya.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectakhirbudaya.MainActivity
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private var userId: Int = -1
    private var userEmail: String = "testing@gmail.com"
    private var userPass: String = "123"
    private var userLevel: String = "user"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
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

        val txtEmailHeading = findViewById<TextView>(R.id.txt_name_heading)
        val txtEmail = findViewById<TextView>(R.id.txt_email)
        val txtPassword = findViewById<TextView>(R.id.txt_password)
        val txtLoginAs = findViewById<TextView>(R.id.txt_loginSebagai)

        txtEmailHeading.text = userEmail
        txtEmail.text = userEmail
        txtPassword.text = userPass
        txtLoginAs.text = userLevel

    }

    fun forLogout(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout Confirmation")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun backToMain(view: View) {
        if (userLevel == "admin"){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("email", userEmail)
            intent.putExtra("password", userPass)
            intent.putExtra("level", userLevel)
            startActivity(intent)
        }else{
            val intent = Intent(this, UserViewActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("email", userEmail)
            intent.putExtra("password", userPass)
            intent.putExtra("level", userLevel)
            startActivity(intent)
        }
    }
}