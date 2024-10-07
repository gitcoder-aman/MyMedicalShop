package com.tech.mymedicalshopuser.data

import com.tech.mymedicalshopuser.data.model.SignUpUserModel
import com.tech.mymedicalshopuser.data.response.GetAllUsersResponse
import com.tech.mymedicalshopuser.data.response.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.data.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<ArrayList<GetAllUsersResponseItem>>

    @POST("signup")
    suspend fun signUpUser(@Body signUpUserModel: SignUpUserModel) : Response<SignupResponse>
}