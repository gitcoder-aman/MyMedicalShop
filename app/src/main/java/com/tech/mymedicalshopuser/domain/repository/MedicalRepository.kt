package com.tech.mymedicalshopuser.domain.repository

import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.data.response.response_status.ResponseStatus
import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.state.MedicalResponseState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MedicalRepository {

    suspend fun signupUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ): Flow<MedicalResponseState<Response<ResponseStatus>>>

    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<MedicalResponseState<Response<ResponseStatus>>>

    suspend fun getSpecificUser(
        userId: String
    ): Flow<MedicalResponseState<Response<ArrayList<GetAllUsersResponseItem>>>>

    suspend fun getAllProducts(): Flow<MedicalResponseState<Response<ArrayList<ProductModelItem>>>>

    suspend fun createOrder(
       orderList : List<MedicalOrderResponseItem>
    ): Flow<MedicalResponseState<Response<ResponseStatus>>>

    suspend fun getAllUserOrders(
        userId: String
    ): Flow<MedicalResponseState<Response<ArrayList<MedicalOrderResponseItem>>>>



}