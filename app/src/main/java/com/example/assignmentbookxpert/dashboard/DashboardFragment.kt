package com.example.assignmentbookxpert.dashboard

import android.net.sip.SipErrorCode.TIME_OUT
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    lateinit var binding : FragmentDashboardBinding
    val viewList: MutableList<DashboardModel> = arrayListOf()
    private var doubleBackToExitPressedOnce = false
    private var userId:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewList.add(DashboardModel(R.mipmap.upload, 1000, "PDF View"))
        viewList.add(DashboardModel(R.mipmap.upload, 1001, "Open Camera"))
        viewList.add(DashboardModel(R.mipmap.upload, 1002, "API Call"))
        viewList.add(DashboardModel(R.mipmap.exit_logout, 99, "Exit"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater,container,false)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        findNavController().navigate(R.id.action_nav_dashboard_to_login)
                        return
                    }
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(requireContext(),"Please click BACK again to exit.", Toast.LENGTH_SHORT).show()

                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                }
            })

        val adapter = DashboardAdapter(viewList, object : DashboardClickListener {
            override fun onItemClicked(item: DashboardModel) {
                Handler().postDelayed({
                    when (item.order) {
                        1000 -> findNavController().navigate(R.id.action_nav_dashboard_to_pdf_view)
                        1001 -> findNavController().navigate(R.id.action_nav_dashboard_to_camera)
                        1002 -> findNavController().navigate(R.id.action_nav_dashboard_to_apiCall)
                        99 -> findNavController().navigate(R.id.action_nav_dashboard_to_login)
                    }
                }, TIME_OUT.toLong())
            }
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDashboard.layoutManager = gridLayoutManager
        binding.rvDashboard.adapter = adapter
        binding.rvDashboard.setHasFixedSize(true)

        return binding.root
    }

}