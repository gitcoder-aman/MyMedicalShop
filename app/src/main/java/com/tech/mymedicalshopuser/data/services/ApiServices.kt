package com.tech.mymedicalshopuser.data.services


import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.data.response.signupLogin.SignupLoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<ArrayList<GetAllUsersResponseItem>>

    @FormUrlEncoded
    @POST("signup")
    suspend fun signUpUser(
        @Field("userName") name : String,
        @Field("password") password : String,
        @Field("email") email : String,
        @Field("phoneNumber") phoneNumber : String,
        @Field("address") address : String,
        @Field("pinCode") pinCode : String
    ) : Response<SignupLoginResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ): Response<SignupLoginResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("userId") userId : String,
    ): Response<ArrayList<GetAllUsersResponseItem>>
}