package com.example.assignmentbookxpert.network

import com.example.assignmentbookxpert.entitty.ApiEntity
import com.example.assignmentbookxpert.model.ApiResponseData
import retrofit2.http.GET

interface ApiInterface {
    @GET("objects")
    suspend fun getObjects(): List<ApiResponseData>
}