package com.dave.shareride.user_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.dave.shareride.R
import com.dave.shareride.network_requests.calls.RetrofitCallsAuthentication
import com.dave.shareride.network_requests.classes.UserLogin
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvRegister.setOnClickListener {

            val intent = Intent(this, Registration::class.java)
            startActivity(intent)

        }
        tvForgotPassword.setOnClickListener {

            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)

        }

        btnLogin.setOnClickListener {

            val phoneNumber = etPhoneNumber.text.toString()
            val password = etLoginPassword.text.toString()

            if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password)){

                val userLogin = UserLogin(phoneNumber, password, resources.getString(R.string.public_user))
                retrofitCallsAuthentication.loginUser(this, userLogin)


            }else{

                if (TextUtils.isEmpty(phoneNumber))
                    etPhoneNumber.error = "This field cannot be empty."

                if (TextUtils.isEmpty(password))
                    etLoginPassword.error = "This field cannot be empty."

            }

        }

    }
}