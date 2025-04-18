package com.example.assignmentbookxpert.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentbookxpert.entitty.UserCredentialTable

@Dao
interface MasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCredential(table: UserCredentialTable)

    @Query("Select * from user_credential_data_table")
    suspend fun fetchCredentialData(): List<UserCredentialTable>
}