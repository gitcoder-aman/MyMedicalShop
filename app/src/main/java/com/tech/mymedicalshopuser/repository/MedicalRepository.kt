package com.tech.mymedicalshopuser.repository

import com.tech.mymedicalshopuser.data.ApiProvider
import com.tech.mymedicalshopuser.data.response.GetAllUsersResponse
import retrofit2.Response

class MedicalRepository {

   suspend fun getAllUsers() {
        val apiService = ApiProvider.api()
        val response = apiService.getAllUsers()
    }


}