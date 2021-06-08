package com.dave.shareride.user_management

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dave.shareride.R
import com.dave.shareride.helper_classes.CustomDialogToast
import com.dave.shareride.helper_classes.Formatter
import com.dave.shareride.helper_classes.TextValidator
import com.dave.shareride.network_requests.RetrofitCallsAuthentication
import com.dave.shareride.network_requests.classes.UserRegister
import kotlinx.android.synthetic.main.activity_registration.*

class Registration : AppCompatActivity() {

    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()
    private var textValidator : TextValidator = TextValidator()

    lateinit var radioButton: RadioButton
    var customDialogToast = CustomDialogToast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etEmailAddress.addTextChangedListener(emailTextWatcher)
        etMobileNumber.addTextChangedListener(phoneNumberTextWatcher)
        etPassword.addTextChangedListener(passwordTextWatcher)
        etConfirmPassword.addTextChangedListener(confirmPasswordTextWatcher)

        linearLogin.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }

        btnRegister.setOnClickListener {

            val emailAddress = etEmailAddress.text.toString()
            val phoneNumber = etMobileNumber.text.toString()
            val passWord = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val userName = etUserName.text.toString()

            val userValuePair = textValidator.textNotNull(userName)
            val isText = userValuePair.second
            val textValue = userValuePair.first

            val isEmailValid = textValidator.isEmailValid(emailAddress)

            val phoneStatus = textValidator.isPhoneNumberValid(phoneNumber)
            val isPhoneValid = phoneStatus.first

            val passwordStatus = textValidator.isPasswordStrong(passWord)
            val isPasswordStrong = passwordStatus.first

            val isPasswordSame = textValidator.isPasswordSame(passWord, confirmPassword)

            val selectedOption = radioSexGroup.checkedRadioButtonId

            var gender : String? = null
            if (selectedOption > 0){
                radioButton = findViewById(selectedOption)
                gender = radioButton.text.toString()
            }else{
                gender = null
            }


            if (isEmailValid && isPhoneValid  && isPasswordSame && isText && gender != null){

                var userRegister = UserRegister(phoneNumber, emailAddress, userName, gender, passWord, confirmPassword)
                retrofitCallsAuthentication.registerUser(this, userRegister)


            }else{

                if (!isEmailValid)
                    etEmailAddress.error = "Email is invalid"
                else
                    etEmailAddress.error = null

                val phoneCount = phoneStatus.second

                if (!isPhoneValid)
                    etMobileNumber.error = "Phone is invalid. $phoneCount/10"
                else
                    etEmailAddress.error = null

                val passwordCount = passwordStatus.second
                val strength = passwordStatus.third

                if (!isPasswordStrong) {
                    etPassword.error = "Password is $strength. $passwordCount/6 "
                    etConfirmPassword.isEnabled = false
                    etConfirmPassword.setText("")
                }else {
                    etConfirmPassword.isEnabled = true
                    etPassword.error = null
                }

                if (!isPasswordSame)
                    etConfirmPassword.error = "Passwords don't match."
                else
                    etConfirmPassword.error = null

                if (!isText)
                    etUserName.error = textValue
                else
                    etUserName.error = null

                if (gender == null)
                    customDialogToast.CustomDialogToast(this, "You have to select a gender.")


            }


        }
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val isEmailValid = textValidator.isEmailValid(s.toString())
            if (!isEmailValid)
                etEmailAddress.error = "Email is invalid"
            else
                etEmailAddress.error = null

        }

    }
    private val phoneNumberTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val phoneStatus = textValidator.isPhoneNumberValid(s.toString())
            val isPhoneValid = phoneStatus.first
            val phoneCount = phoneStatus.second

            if (!isPhoneValid)
                etMobileNumber.error = "Phone is invalid. $phoneCount/10"
            else
                etEmailAddress.error = null

        }

    }
    private val passwordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val passwordStatus = textValidator.isPasswordStrong(s.toString())
            val isPasswordStrong = passwordStatus.first
            val passwordCount = passwordStatus.second
            val strength = passwordStatus.third

            if (!isPasswordStrong) {
                etPassword.error = "Password is $strength. $passwordCount/8 "
                etConfirmPassword.isEnabled = true
                etConfirmPassword.setText("")
            }else {
                etConfirmPassword.isEnabled = true
                etPassword.error = null
            }

        }

    }
    private val confirmPasswordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val password = etPassword.text.toString()
            val isPasswordSame = textValidator.isPasswordSame(password, s.toString())
            if (!isPasswordSame)
                etConfirmPassword.error = "Passwords don't match."
            else
                etConfirmPassword.error = null

        }

    }

}