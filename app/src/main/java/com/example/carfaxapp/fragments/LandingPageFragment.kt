package com.example.carfaxapp.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfaxapp.adapters.CarListAdapter
import com.example.carfaxapp.adapters.Listener
import com.example.carfaxapp.database.CarListDatabase
import com.example.carfaxapp.databinding.LandingPageFragmentBinding
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.network.ListInfo
import com.example.carfaxapp.viewmodels.LandingPageViewModel

class LandingPageFragment : Fragment(), Listener {
    lateinit var viewModel: LandingPageViewModel
    private lateinit var carListAdapter: CarListAdapter
    private var _binding: LandingPageFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LandingPageFragmentBinding.inflate(inflater, container, false)

        loadApi()
        val database = CarListDatabase.getInstance(binding.root.context)
        viewModel.setInstanceOfDatabase(database)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView(){
        binding.carListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            carListAdapter = CarListAdapter(listener = this@LandingPageFragment)
            adapter = carListAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadApi(){
        viewModel = ViewModelProvider(this).get(LandingPageViewModel::class.java)
        viewModel.getCarListObserver().observe(this, Observer<CarListModel>{
            if(it != null){
                carListAdapter.carListing = it.listings as ArrayList<ListInfo>
                viewModel.saveDataIntoDb(it)
                carListAdapter.notifyDataSetChanged()
            }else{
                viewModel.getDataFromDb()
            }
        })
        viewModel.makeApiCalls()
    }

    override fun onPostClicked(position: Int) {
        val post = carListAdapter.getPost(position)
        val action = LandingPageFragmentDirections.actionLandingPageFragmentToDetailedListingFragment(post)
        findNavController().navigate(action)
    }

    override fun onCallButtonClicked(position: Int) {
        viewModel.getCarListObserver().observe(this, Observer<CarListModel> {
            if (it != null) {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:" + it.listings[position].dealer.phone)
                startActivity(callIntent)
            }
        })
    }
}
