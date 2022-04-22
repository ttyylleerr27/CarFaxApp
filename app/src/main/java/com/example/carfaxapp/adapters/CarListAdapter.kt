package com.example.carfaxapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carfaxapp.R
import com.example.carfaxapp.databinding.LayoutCarListItemBinding
import com.example.carfaxapp.network.ListInfo

/**
 * Adapter responsible for loading and displaying a list of cars.
 */
class CarListAdapter(private val listener: Listener): RecyclerView.Adapter<CarListAdapter.MyViewHolder>(){

    var carListing = ArrayList<ListInfo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutCarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CarListAdapter.MyViewHolder, position: Int) {
        holder.bind(carListing[position])
    }

    override fun getItemCount(): Int {
        return carListing.size
    }

    inner class MyViewHolder(var binding: LayoutCarListItemBinding, private val listener: Listener): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private val make = binding.carMake
        private val mileage = binding.carMileage
        private val model = binding.carModel
        private val onePrice = binding.carPrice
        private val image = binding.carImageView
        private val location = binding.carLocation
        private val callButton = binding.callDealerButton

        fun bind(data : ListInfo){
            make.text = data.make
            mileage.text = "Mileage: ".plus(data.mileage.toString().plus(" miles"))
            model.text = data.model
            onePrice.text = "Price: ".plus("$".plus(data.listPrice.toString()))
            location.text = "Location: ".plus(data.dealer.city.plus(", " + data.dealer.state))
            callButton.setOnClickListener(this)

            val url = data.images.firstPhoto.small
            if(url.startsWith("https://carfax-img.vast.com")){
                image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.coming_soon_image))
            }else{
                Glide.with(image)
                    .load(url)
                    .centerCrop()
                    .into(image)
            }
        }

        override fun onClick(view: View?){
            if(view?.id == R.id.callDealerButton){
                listener.onCallButtonClicked(adapterPosition)
            } else {
                listener.onPostClicked(adapterPosition)
            }
        }
    }
}
