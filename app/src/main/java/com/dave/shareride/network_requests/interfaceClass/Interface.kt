package com.uptech.easymeal.network_request.`interface`

import com.dave.shareride.network_requests.classes.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {

    @POST("/api/v1/accounts/auth/register-user")
    fun registerUser(@Body userRegister: UserRegister): Call<SuccessRegister>

    @POST("/api/v1/accounts/auth/verify-otp")
    fun otpVerify(@Body otpVerify: OtpVerify): Call<SuccessVerify>

    @POST("/api/v1/accounts/auth/login")
    fun loginUser(@Body user: UserLogin): Call<SuccessLogin>

    @POST("/api/v1/accounts/auth/resend-otp")
    fun resendOtp(@Body userResendOtp: UserResendOtp): Call<SuccessLogin>

//    @POST("/auth/otp-verify/")
//    fun otpVerify(@Body otpVerify: OtpVerify): Call<SuccessOtpVerify>



}