package com.example.carfaxapp.fragments

import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfaxapp.adapters.CarListAdapter
import com.example.carfaxapp.databinding.LandingPageFragmentBinding
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.viewmodels.LandingPageViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LandingPageFragment : Fragment() {

    private var _binding: LandingPageFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var carListAdapter: CarListAdapter
    lateinit var viewModel: LandingPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LandingPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadApiData()
    }

    fun initRecyclerView(){
        binding.carListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val decoration = DividerItemDecoration(context, VERTICAL)
            addItemDecoration(decoration)
            carListAdapter = CarListAdapter()
            adapter = carListAdapter
        }
    }

    fun loadApiData(){
        viewModel = ViewModelProvider(this).get(LandingPageViewModel::class.java)
        viewModel.getCarListObserver().observe(this, {
            if(it != null){
                carListAdapter.carListData = it.items
                carListAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCalls()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}