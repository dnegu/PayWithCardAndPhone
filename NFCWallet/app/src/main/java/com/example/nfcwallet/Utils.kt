package com.example.nfcwallet

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {
    companion object {
        val BACKEND_URL = "https://stripe-node-lik1.onrender.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}