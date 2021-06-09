package com.dave.shareride.network_requests.calls

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dave.shareride.R
import com.dave.shareride.helper_classes.CustomDialogToast
import com.dave.shareride.helper_classes.SharedPreferenceStorage
import com.dave.shareride.helper_classes.UrlData
import com.dave.shareride.network_requests.classes.*
import com.dave.shareride.shared.MainActivity
import com.dave.shareride.user_management.Login
import com.dave.shareride.user_management.VerifyOtp
import com.uptech.easymeal.network_request.`interface`.Interface
import com.uptech.easymeal.network_request.builder.RetrofitBuilder
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RetrofitCallsAuthentication {

    var customDialogToast = CustomDialogToast()

    fun registerUser(context: Context, userRegister: UserRegister){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startRegistration(context, userRegister)
            }.join()
        }

    }
    private suspend fun startRegistration(context: Context, userRegister: UserRegister, ) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val sharedPreferenceStorage = SharedPreferenceStorage(
                context, context.resources.getString(
                    R.string.app_name
                )
            )

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Registration in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()


            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL_ACL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

                val apiInterface = apiService.registerUser(userRegister)
                apiInterface.enqueue(object : Callback<SuccessRegister> {
                    override fun onResponse(
                        call: Call<SuccessRegister>,
                        response: Response<SuccessRegister>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            val details = response.body()?.details.toString()

                            //Launch Otp Verifier

                            val hashMap1 = HashMap<String, String>()
                            hashMap1["phone_number"] = userRegister.phone_number
                            hashMap1["password"] = userRegister.password
                            sharedPreferenceStorage.saveData(hashMap1, "profile")

                            val intent = Intent(context, VerifyOtp::class.java)
                            context.startActivity(intent)

                            messageToast = details
                            customDialogToast.CustomDialogToast(context as Activity, messageToast)


                        } else {

                            val code = response.code()

                            if (code != 500){

                                val objectError = JSONObject(response.errorBody()!!.string())

                                CoroutineScope(Dispatchers.IO).launch {

                                    messageToast = objectError.getString("details")
                                    val phoneVerification = objectError.getString("PHONE_VERIFICATION").toBoolean()
                                    if (!phoneVerification){

                                        val intent = Intent(context, Login::class.java)
                                        context.startActivity(intent)

                                    }

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.CustomDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }


                                }

                            }else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }


                        }


                    }

                    override fun onFailure(call: Call<SuccessRegister>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })

            }.join()


        }

    }

    fun loginUser(context: Context, userLogin: UserLogin){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startLogin(context, userLogin)

            }.join()
        }

    }
    private suspend fun startLogin(context: Context, userLogin: UserLogin) {
        val sharedPreferenceStorage = SharedPreferenceStorage(
            context, context.resources.getString(
                R.string.app_name
            )
        )
        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Login in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL_ACL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

                val apiInterface = apiService.loginUser(userLogin)
                apiInterface.enqueue(object : Callback<SuccessLogin> {
                    override fun onResponse(
                        call: Call<SuccessLogin>,
                        response: Response<SuccessLogin>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            messageToast = "An OTP has been sent to your phone number."

                            val hashMap1 = HashMap<String, String>()
                            hashMap1["phone_number"] = userLogin.phone_number
                            hashMap1["password"] = userLogin.password
                            sharedPreferenceStorage.saveData(hashMap1, "profile")

                            val intent = Intent(context, VerifyOtp::class.java)
                            context.startActivity(intent)


                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    messageToast
                                )
                            }

                        } else {

                            val code = response.code()
                            val message = response.errorBody().toString()

                            if (code != 500) {

                                val objectError = JSONObject(response.errorBody()!!.string())

                                CoroutineScope(Dispatchers.IO).launch {

                                    messageToast = objectError.getString("details")

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.CustomDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }


                                }

                            } else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }

                        }


                    }

                    override fun onFailure(call: Call<SuccessLogin>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)

                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }

    fun otpVerifyAccount(context: Context, otpVerify: OtpVerify){
        CoroutineScope(Dispatchers.Main).launch {
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {
                verifyOtp(context, otpVerify)
            }.join()
        }
    }
    private suspend fun verifyOtp(context: Context, otpVerify: OtpVerify) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Otp Verification in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL_ACL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

                val apiInterface = apiService.otpVerify(otpVerify)
                apiInterface.enqueue(object : Callback<SuccessVerify> {
                    override fun onResponse(
                        call: Call<SuccessVerify>,
                        response: Response<SuccessVerify>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            val details = response.body()?.details

                            messageToast = "Otp code verified successfully. "

                            if (details != null) {



                                saveData(details, context)

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }

                            }


                        } else {

                            val code = response.code()
                            if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())

                                CoroutineScope(Dispatchers.IO).launch {

                                    messageToast = objectError.getString("details")

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.CustomDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }


                                }
                            }else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }


                        }


                    }

                    override fun onFailure(call: Call<SuccessVerify>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }
    private fun saveData(details: LoginDetails, context: Context) {

        val preferences = context.getSharedPreferences(context.resources.getString(R.string.app_name),
            AppCompatActivity.MODE_PRIVATE)

        val sharedPreferenceStorage = SharedPreferenceStorage(context,
            context.resources.getString(R.string.app_name))

        val hashMap1 = HashMap<String, String>()
        hashMap1["access_token"] = details.access_token
        hashMap1["expires_in"] = details.expires_in
        hashMap1["token_type"] = details.token_type
        hashMap1["refresh_token"] = details.refresh_token
        hashMap1["jwt"] = details.jwt
        sharedPreferenceStorage.saveData(hashMap1, "profile")

        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }

    fun resendOtp(context: Context, userResendOtp: UserResendOtp){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                requestNewOtp(context, userResendOtp)

            }.join()
        }

    }
    private suspend fun requestNewOtp(context: Context, userResendOtp: UserResendOtp) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Login in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL_ACL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

                val apiInterface = apiService.resendOtp(userResendOtp)
                apiInterface.enqueue(object : Callback<SuccessLogin> {
                    override fun onResponse(
                        call: Call<SuccessLogin>,
                        response: Response<SuccessLogin>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            messageToast = "An OTP has been sent to your phone number."

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    messageToast
                                )
                            }

                        } else {

                            val code = response.code()
                            val message = response.errorBody().toString()

                            if (code != 500) {

                                val objectError = JSONObject(response.errorBody()!!.string())

                                CoroutineScope(Dispatchers.IO).launch {

                                    messageToast = objectError.getString("details")

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.CustomDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }


                                }

                            } else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }

                        }


                    }

                    override fun onFailure(call: Call<SuccessLogin>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)

                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }


//    private fun getProfileDetails(context: Context, accessToken: String, rolesList: List<String>?) {
//
//        var stringStringMap = HashMap<String, String>()
//        stringStringMap["Authorization"] = " Bearer $accessToken"
//
//        val sharedPreferenceStorage = SharedPreferenceStorage(
//            context,
//            context.resources.getString(R.string.app_name)
//        )
//
//        val baseUrl = context.getString(UrlData.BASE_URL.message)
//        val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
//        val apiInterface = apiService.gerProfileDetails(stringStringMap)
//        apiInterface.enqueue(object : Callback<SuccessProfileDetails> {
//            override fun onResponse(
//                call: Call<SuccessProfileDetails>,
//                response: Response<SuccessProfileDetails>
//            ) {
//
//                if (response.isSuccessful) {
//
//                    val responseData = response.body()?.user_details
//                    if (responseData != null) {
//
//                        val id = responseData.id
//                        val name = responseData.name
//                        val email = responseData.email
//                        val phone_number = responseData.phone_number
//                        val avatar = responseData.avatar
//
//                        val hashMap1 = HashMap<String, String>()
//                        hashMap1["email"] = email
//                        hashMap1["id"] = id
//                        hashMap1["name"] = name
//                        hashMap1["phone_number"] = phone_number
//
//                        if (rolesList != null) {
//                            if (rolesList.contains("WAITER")) {
//                                hashMap1["role"] = "WAITER"
//                            } else {
//                                hashMap1["role"] = "USER"
//                            }
//                        }
//
//                        hashMap1["avatar"] = avatar.toString()
//                        sharedPreferenceStorage.saveData(hashMap1, "profileDetails")
//
//
//                    }
//
//
//                } else {
//                    val code = response.code()
//
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<SuccessProfileDetails>, t: Throwable) {
//                Log.e("-*-*error ", t.localizedMessage)
//
//
//            }
//        })
//
//    }

}

