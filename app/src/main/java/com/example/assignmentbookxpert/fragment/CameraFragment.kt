package com.example.assignmentbookxpert.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.databinding.FragmentCameraBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private var imageUri: Uri? = null

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            binding.imageView.setImageURI(imageUri)
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.imageView.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.apply {
            btnSignOut.setOnClickListener {
                googleSignInClient.signOut().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(),"Signed out successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_nav_camera_to_login)
                    } else {
                        Toast.makeText(requireContext(),"Sign out failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnCamera.setOnClickListener {
//            val file = File(requireContext().cacheDir, "${System.currentTimeMillis()}.jpg")
//            imageUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
//            cameraLauncher.launch(imageUri)
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        return binding.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                launchCamera()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun launchCamera() {
        val file = File(requireContext().cacheDir, "${System.currentTimeMillis()}.jpg")
        imageUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
        cameraLauncher.launch(imageUri)
    }

}