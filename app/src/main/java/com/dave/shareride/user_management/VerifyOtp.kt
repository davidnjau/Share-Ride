package com.dave.shareride.user_management

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.dave.shareride.R
import com.dave.shareride.helper_classes.CustomDialogToast
import com.dave.shareride.helper_classes.SharedPreferenceStorage
import com.dave.shareride.network_requests.calls.RetrofitCallsAuthentication
import com.dave.shareride.network_requests.classes.OtpVerify
import com.dave.shareride.network_requests.classes.UserResendOtp
import com.dave.shareride.shared.SmsBroadcastReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
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
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        startSmsUserConsent()
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

                AutomaticVerifyOTP(otpPassword)

            }else{
                customDialogToast.CustomDialogToast(this, "The Otp cannot be empty.")

            }

        }

    }

    override fun onStart() {
        super.onStart()
        registerToSmsBroadcastReceiver()

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

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_USER_CONSENT -> {
                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
                    //That gives all message to us. We need to get the code from inside with regex
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val code = message?.let { fetchVerificationCode(it) }

                    firstPinView.setText(code)
                    AutomaticVerifyOTP(code.toString())

                }
            }
        }
    }

    private fun AutomaticVerifyOTP(otp: String) {

        val txtPin = firstPinView.text.toString()
        if (!TextUtils.isEmpty(txtPin) && txtPin.length == 6) {
            btnLogin.isEnabled = false
            val otpVerify = OtpVerify(phoneNumber, passWord, otp)
            retrofitCallsAuthentication.otpVerifyAccount(this, otpVerify)
        }else{
            btnLogin.isEnabled = true
            customDialogToast.CustomDialogToast(this, "The Otp cannot be empty.")

        }
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(this).also {
            //We can add user phone number or leave it blank
            it.startSmsUserConsent(null)
                .addOnSuccessListener {
                    Log.d(TAG, "LISTENING_SUCCESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "LISTENING_FAILURE")
                }
        }
    }

    private fun registerToSmsBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver().also {
            it.smsBroadcastReceiverListener = object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    intent?.let { context -> startActivityForResult(context, REQ_USER_CONSENT) }
                }

                override fun onFailure() {
                }
            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    private fun fetchVerificationCode(message: String): String {
        return Regex("(\\d{6})").find(message)?.value ?: ""
    }

    companion object {
        const val TAG = "SMS_USER_CONSENT"

        const val REQ_USER_CONSENT = 100
    }

}