package com.example.assignmentbookxpert.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.adapter.ApiCallAdapter
import com.example.assignmentbookxpert.databinding.FragmentApiCallBinding
import com.example.assignmentbookxpert.viewModel.AssignmentViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class ApiCallFragment : Fragment() {
    private lateinit var binding: FragmentApiCallBinding
    private val assignmentViewModel: AssignmentViewModel by viewModels()
    private lateinit var apiCallAdapter: ApiCallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiCallBinding.inflate(layoutInflater)

        apiCallAdapter = ApiCallAdapter()
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = apiCallAdapter
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.apply {
            btnSignOut.setOnClickListener {
                googleSignInClient.signOut().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(),"Signed out successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_nav_api_to_login)
                    } else {
                        Toast.makeText(requireContext(),"Sign out failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnApi.setOnClickListener {
                lifecycleScope.launch {
                    val apiData = assignmentViewModel.callApi()
                    apiCallAdapter.submitList(apiData)
                }

            }
        }
        return binding.root


    }


}