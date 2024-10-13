package com.tech.mymedicalshopuser.data.services


import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.data.response.response_status.ResponseStatus
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Date

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
    ) : Response<ResponseStatus>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ): Response<ResponseStatus>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("userId") userId : String,
    ): Response<ArrayList<GetAllUsersResponseItem>>

    @GET("getAllProduct")
    suspend fun getAllProduct() : Response<ArrayList<ProductModelItem>>

    @FormUrlEncoded
    @POST("order")
    suspend fun createOrder(
        @Field("user_id") userId : String,
        @Field("product_id") productId : String,
        @Field("product_name") productName : String,
        @Field("product_category") productCategory : String,
        @Field("user_name") userName : String,
        @Field("isApproved") isApproved : Int,
        @Field("product_quantity") productQuantity : Int,
        @Field("product_price") productPrice : Float,
        @Field("subtotal_price") subTotalPrice : Float,
        @Field("delivery_charge") deliveryCharge : Float,
        @Field("tax_charge") taxCharge : Float,
        @Field("total_price") totalPrice : Float,
        @Field("order_date") orderDate : Date,
        @Field("user_address") userAddress : String,
        @Field("user_pincode") userPinCode : String,
        @Field("user_mobile") userMobile : String,
        @Field("user_email") userEmail : String,
    ) : Response<ResponseStatus>

    @FormUrlEncoded
    @POST("getAllOrderThroughUser")
    suspend fun getAllOrders(
        @Field("user_id") userId : String,
    ) : Response<ArrayList<MedicalOrderResponseItem>>

}