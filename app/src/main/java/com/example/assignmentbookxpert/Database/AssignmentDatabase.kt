package com.example.assignmentbookxpert.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignmentbookxpert.dao.MasterDao
import com.example.assignmentbookxpert.entitty.UserCredentialTable

@Database(
    entities =[UserCredentialTable::class],
    version = 1,
    exportSchema = false
)
abstract class AssignmentDatabase : RoomDatabase(){
    abstract fun masterDao() : MasterDao
}