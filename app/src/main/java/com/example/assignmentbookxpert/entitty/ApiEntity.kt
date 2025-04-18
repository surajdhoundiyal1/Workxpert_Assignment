package com.example.assignmentbookxpert.entitty

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.GET

@Entity(tableName = "api_table")
data class ApiEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val data: String
)
