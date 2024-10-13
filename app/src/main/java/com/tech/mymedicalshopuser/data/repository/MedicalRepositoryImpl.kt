package com.tech.mymedicalshopuser.data.repository

import android.util.Log
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.data.services.ApiServices
import com.tech.mymedicalshopuser.data.response.response_status.ResponseStatus
import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<MedicalResponseState<Response<ResponseStatus>>> = flow {
        emit(MedicalResponseState.Loading)
        try {
            val response =
                apiServices.signUpUser(name, password, email, phoneNumber, address, pinCode)
            emit(MedicalResponseState.Success(response))
        } catch (e: Exception) {
            emit(MedicalResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<MedicalResponseState<Response<ResponseStatus>>> = flow {

        emit(MedicalResponseState.Loading)
        try {
            val response = apiServices.loginUser(email, password)
            emit(MedicalResponseState.Success(response))
        } catch (e: Exception) {
            emit(MedicalResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getSpecificUser(userId: String): Flow<MedicalResponseState<Response<ArrayList<GetAllUsersResponseItem>>>> =
        flow {
            emit(MedicalResponseState.Loading)
            try {
                val response = apiServices.getSpecificUser(userId)
                emit(MedicalResponseState.Success(response))
            } catch (e: Exception) {
                emit(MedicalResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getAllProducts(): Flow<MedicalResponseState<Response<ArrayList<ProductModelItem>>>> =
        flow {
            emit(MedicalResponseState.Loading)
            try {
                val response = apiServices.getAllProduct()
                emit(MedicalResponseState.Success(response))
            } catch (e: Exception) {
                emit(MedicalResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun createOrder(
        orderList: List<MedicalOrderResponseItem>
    ): Flow<MedicalResponseState<Response<ResponseStatus>>> = flow {
        emit(MedicalResponseState.Loading)
        try {
            for (order in orderList) {
                val response = apiServices.createOrder(
                    userId = order.user_id,
                    productId = order.product_id,
                    productName = order.product_name,
                    productCategory = order.product_category,
                    userName = order.user_name,
                    isApproved = order.isApproved,
                    productQuantity = order.product_quantity,
                    productPrice = order.product_price.toFloat(),
                    subTotalPrice = order.subtotal_price.toFloat(),
                    deliveryCharge = order.delivery_charge.toFloat(),
                    taxCharge = order.tax_charge.toFloat(),
                    totalPrice = order.subtotal_price.toFloat() + order.delivery_charge.toFloat() + order.tax_charge.toFloat(),
                    orderDate = java.util.Date(),
                    userAddress = order.user_address,
                    userPinCode = order.user_pinCode,
                    userMobile = order.user_mobile,
                    userEmail = order.user_email
                )
                emit(MedicalResponseState.Success(response))
            }
        } catch (e: Exception) {
            emit(MedicalResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getAllUserOrders(
        userId: String
    ): Flow<MedicalResponseState<Response<ArrayList<MedicalOrderResponseItem>>>>  = flow{
        emit(MedicalResponseState.Loading)
        try {
            val response = apiServices.getAllOrders(userId)
            Log.d("@TAG", "getAllUserOrders: ${response.body()?.size}")
            emit(MedicalResponseState.Success(response))
        }catch (e : Exception){
            emit(MedicalResponseState.Error(e.message.toString()))
        }
    }


}