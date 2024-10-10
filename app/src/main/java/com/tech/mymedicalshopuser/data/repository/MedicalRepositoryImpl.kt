package com.tech.mymedicalshopuser.data.repository

import com.tech.mymedicalshopuser.data.services.ApiServices
import com.tech.mymedicalshopuser.data.response.signupLogin.SignupLoginResponse
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import retrofit2.Response
import javax.inject.Inject

class MedicalRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : MedicalRepository {

    override suspend fun signupUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ): Response<SignupLoginResponse> {
        return apiServices.signUpUser(name, password, email, phoneNumber, address, pinCode)
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Response<SignupLoginResponse> {
        return apiServices.loginUser(email = email,password = password)
    }

}