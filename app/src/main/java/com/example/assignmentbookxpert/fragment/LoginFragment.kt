package com.example.assignmentbookxpert.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.databinding.FragmentLoginBinding
import com.example.assignmentbookxpert.entitty.UserCredentialTable
import com.example.assignmentbookxpert.viewModel.AssignmentViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment()  {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userCredentialDataDb : List<UserCredentialTable>
    private val assignmentViewModel: AssignmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        userCredentialDataDb = ArrayList()

        lifecycleScope.launch {
            userCredentialDataDb = assignmentViewModel.fetchUserDataFromDatabase()
        }

        val googleSignInClient = GoogleSignIn.getClient(requireContext(),
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )

        binding.apply {
            btnGoogle.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, 1001)
            }

            loginButton.setOnClickListener {
                if (etUsername.text.toString().isNullOrEmpty()) {
                    etUsername.setError("userName should not be Empty")
                }else if(etPassword.text.toString().isNullOrEmpty()){
                    etPassword.setError("Password should not be empty")
                }else{
                    val data = userCredentialDataDb.find { it-> it.userid.equals(etUsername.text.toString().trim(),ignoreCase = true)  }
                    if(data!=null){
                        if(data.password.equals(etPassword.text.toString().trim(),false)){
                            findNavController().navigate(R.id.action_nav_login_to_dashboard)
                            Toast.makeText(requireContext(),"Successfully login", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(),"Incorrect Password", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(),"User Id Not in the Database", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account.email ?: ""
                val dummyPassword = "Suraj123"
                val isCheckUserAlreadyExist = userCredentialDataDb.any { it-> it.userid.equals(email,true)  }
                if(!isCheckUserAlreadyExist){
                    assignmentViewModel.insertUser(email, dummyPassword)
                }
                findNavController().navigate(R.id.action_nav_login_to_dashboard)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(),"Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


}