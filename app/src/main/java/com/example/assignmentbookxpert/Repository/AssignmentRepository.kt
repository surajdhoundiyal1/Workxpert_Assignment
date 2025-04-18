package com.example.assignmentbookxpert.Repository

import com.example.assignmentbookxpert.dao.MasterDao
import com.example.assignmentbookxpert.entitty.UserCredentialTable
import javax.inject.Inject

class AssignmentRepository @Inject constructor(private val masterDao: MasterDao) {

    suspend fun insertCredential(user: UserCredentialTable) = masterDao.insertUserCredential(user)

    suspend fun fetchAllUserCredentialData(): List<UserCredentialTable> = masterDao.fetchCredentialData()

}