package com.example.assignmentbookxpert.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentbookxpert.Repository.AssignmentRepository
import com.example.assignmentbookxpert.entitty.ApiEntity
import com.example.assignmentbookxpert.entitty.UserCredentialTable
import com.example.assignmentbookxpert.model.ApiResponseData
import com.example.assignmentbookxpert.network.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentViewModel @Inject constructor(private val assignmentRepository: AssignmentRepository,
    private val apiService: ApiInterface) : ViewModel() {

    fun insertUser(email:String, password:String){
        viewModelScope.launch {
            assignmentRepository.insertCredential(UserCredentialTable(userid = email, password = password))
        }
    }

    suspend fun fetchUserDataFromDatabase(): List<UserCredentialTable> {
        return assignmentRepository.fetchAllUserCredentialData()
    }

    suspend fun callApi() : List<ApiResponseData>  = apiService.getObjects()
}