package com.example.assignmentbookxpert.model

data class ApiResponseData(
    val id: String,
    val name: String,
    val data: Map<String, Any>? = null
)
