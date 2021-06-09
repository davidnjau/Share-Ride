package com.dave.shareride.shared

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.dave.shareride.R
import com.dave.shareride.user_management.Registration

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({

            val preferences = this.getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
            val boolValue: Boolean = preferences.getBoolean("isLoggedIn", false)
            if (boolValue){

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, Registration::class.java)
                startActivity(intent)
                finish()
            }


        }, 3000)

    }


}