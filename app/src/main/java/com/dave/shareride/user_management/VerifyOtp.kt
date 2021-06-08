package com.dave.shareride.user_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.dave.shareride.R
import com.dave.shareride.helper_classes.CustomDialogToast
import com.dave.shareride.helper_classes.SharedPreferenceStorage
import com.dave.shareride.network_requests.RetrofitCallsAuthentication
import com.dave.shareride.network_requests.classes.OtpVerify
import com.dave.shareride.network_requests.classes.UserResendOtp
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_verify_otp.*
import kotlinx.android.synthetic.main.activity_verify_otp.btnLogin
import kotlinx.android.synthetic.main.activity_verify_otp.tvForgotPassword
import kotlinx.android.synthetic.main.activity_verify_otp.tvRegister
import java.util.HashMap

class VerifyOtp : AppCompatActivity() {

    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    private var phoneNumber :String = ""
    private var passWord :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        tvRegister.setOnClickListener {

            val intent = Intent(this, Registration::class.java)
            startActivity(intent)

        }
        tvForgotPassword.setOnClickListener {

            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)

        }
        linearResendOtp.setOnClickListener {

            if (phoneNumber != ""){

                val userResendOtp = UserResendOtp(phoneNumber)
                retrofitCallsAuthentication.resendOtp(this, userResendOtp)

            }else{

                customDialogToast.CustomDialogToast(this, "There is something wrong please try again.")


            }

        }

        btnLogin.setOnClickListener {

            val otpPassword = firstPinView.text.toString()
            if (!TextUtils.isEmpty(otpPassword)) {

                val otpVerify = OtpVerify(phoneNumber, passWord, otpPassword)
                retrofitCallsAuthentication.otpVerifyAccount(this, otpVerify)

            }else{
                customDialogToast.CustomDialogToast(this, "The Otp cannot be empty.")

            }

        }


    }

    override fun onStart() {
        super.onStart()

        val preferences = SharedPreferenceStorage(this, resources.getString(R.string.app_name))
        val getVerify: HashMap<String, String> = preferences.getSavedData("profile")
        val phone_number = getVerify["phone_number"]
        val password = getVerify["password"]

        if (phone_number != null && password !=null){

            phoneNumber = phone_number
            passWord = password

        }else{

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        }


    }
}