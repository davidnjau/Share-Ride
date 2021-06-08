package com.dave.shareride.user_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dave.shareride.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        linearLogin.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
    }
}