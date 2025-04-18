package com.example.assignmentbookxpert.entitty

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_credential_data_table")
data class UserCredentialTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    @ColumnInfo(name = "UserId")
    val userid: String,
    @ColumnInfo(name = "Password")
    val password :String
)
