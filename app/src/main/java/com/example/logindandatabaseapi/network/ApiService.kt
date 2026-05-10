package com.example.logindandatabaseapi.network

import com.example.logindandatabaseapi.model.LoginRequest
import com.example.logindandatabaseapi.model.LoginResponse
import com.example.logindandatabaseapi.model.PasienResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<PasienResponse>
}
