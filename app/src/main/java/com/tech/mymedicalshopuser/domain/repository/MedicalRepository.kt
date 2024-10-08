package com.tech.mymedicalshopuser.domain.repository

import com.tech.mymedicalshopuser.data.response.signupLogin.SignupLoginResponse
import retrofit2.Response

interface MedicalRepository {

    suspend fun signupUser(
        name : String,
        password : String,
        email : String,
        phoneNumber : String,
        address : String,
        pinCode : String
    ) : Response<SignupLoginResponse>

    suspend fun loginUser(
        email : String,
        password : String
    ) : Response<SignupLoginResponse>


}