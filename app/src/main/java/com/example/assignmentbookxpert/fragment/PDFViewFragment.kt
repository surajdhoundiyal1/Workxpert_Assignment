package com.example.assignmentbookxpert.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.databinding.FragmentPdfViewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PDFViewFragment : Fragment() {

    private lateinit var binding: FragmentPdfViewBinding
    private lateinit var pDialog: ProgressDialog
    private val pdfUrl = "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPdfViewBinding.inflate(layoutInflater)

        pDialog = ProgressDialog(requireContext(), R.style.MyThemePrimary)
        pDialog.setCancelable(false)
        pDialog.setProgressStyle(16973853)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.apply {
            btnSignOut.setOnClickListener {
                googleSignInClient.signOut().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(),"Signed out successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_nav_pdfView_to_login)
                    } else {
                        Toast.makeText(requireContext(),"Sign out failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            showProgress()
            loadPdfInWebView()
        }

        return binding.root
    }


    private fun loadPdfInWebView() {
        val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(googleDocsUrl)
        hideProgress()

    }

    private fun showProgress() {
        pDialog.show()
    }
    private fun hideProgress() {
        pDialog.hide()
    }

}