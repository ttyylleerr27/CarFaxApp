package com.example.carfaxapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carfaxapp.databinding.LayoutCarListItemBinding
import com.example.carfaxapp.network.CarInfoDetailed

/**
 * Adapter responsible for loading and displaying a list of cars.
 */
class CarListAdapter: RecyclerView.Adapter<CarListAdapter.MyViewHolder>(){

    var carListData = ArrayList<CarInfoDetailed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutCarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarListAdapter.MyViewHolder, position: Int) {
        holder.bind(carListData[position])
    }

    override fun getItemCount(): Int {
        return carListData.size
    }

    inner class MyViewHolder(val binding: LayoutCarListItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        val photo = binding.carImageView
        val info = binding.carInfo
        val location = binding.carLocation
        val mileage = binding.carMileage
        val price = binding.carPrice

        fun bind(data: CarInfoDetailed){
            info.text = data.info
            location.text = data.location
            mileage.text = data.mileage
            price.text = data.price

            val url = data.photo.carImage
            Glide.with(photo)
                .load(url)
                .into(photo)
        }
    }




}
