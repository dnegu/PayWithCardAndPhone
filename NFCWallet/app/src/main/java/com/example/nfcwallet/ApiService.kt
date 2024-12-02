package com.example.nfcwallet

import com.example.nfcwallet.data.PaymentMethod
import com.example.nfcwallet.data.PaymentMethodList
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    data class CreatePaymentMethodRequest(val customerId: String,val encryptedData: String)
    @FormUrlEncoded
    @POST("/get-payment-method-list") // Replace with actual endpoint path
    suspend fun listPaymentMethods(@Field("customerId") id: String): PaymentMethodList

    @POST("/create-payment-method") // Replace with actual endpoint path
    suspend fun createPaymentMethod(@Body createPaymentMethodRequest: CreatePaymentMethodRequest): PaymentMethod

}