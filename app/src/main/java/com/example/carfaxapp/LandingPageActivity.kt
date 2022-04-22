package com.example.carfaxapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfaxapp.adapters.CarListAdapter
import com.example.carfaxapp.adapters.Listener
import com.example.carfaxapp.databinding.LandingPageActivityBinding
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.viewmodels.LandingPageViewModel

class LandingPageActivity : AppCompatActivity(), Listener {
    lateinit var viewModel: LandingPageViewModel
    private lateinit var carListAdapter: CarListAdapter
    private lateinit var binding: LandingPageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LandingPageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadApi()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.carListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@LandingPageActivity)
            carListAdapter = CarListAdapter(listener = this@LandingPageActivity)
            adapter = carListAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadApi(){
        viewModel = ViewModelProvider(this).get(LandingPageViewModel::class.java)
        viewModel.getCarListObserver().observe(this, Observer<CarListModel>{
            if(it != null){
                carListAdapter.carListing = it.listings
                carListAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCalls()
    }

    override fun onPostClicked(position: Int) {
        Toast.makeText(this, "Post was clicked", Toast.LENGTH_SHORT).show()
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
