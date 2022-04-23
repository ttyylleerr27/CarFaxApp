package com.example.carfaxapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carfaxapp.R
import com.example.carfaxapp.databinding.LayoutCarListItemBinding
import com.example.carfaxapp.network.ListInfo
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

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

    fun getPost(position: Int): ListInfo{
        return carListing[position]
    }

    inner class MyViewHolder(var binding: LayoutCarListItemBinding, private val listener: Listener): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private val year = binding.carYear
        private val make = binding.carMake
        private val mileage = binding.carMileage
        private val model = binding.carModel
        private val price = binding.carPrice
        private val image = binding.carImageView
        private val location = binding.carLocation
        private val callButton = binding.callDealerButton
        private val priceFormat = DecimalFormat("$ ##,###")

        fun bind(data : ListInfo){
            year.text = data.year.toString()
            make.text = data.make
            mileage.text = data.mileage.toString().plus(" mi")
            model.text = data.model
            price.text = priceFormat.format(data.currentPrice)
            location.text = data.dealer.city.plus(", " + data.dealer.state)
            callButton.setOnClickListener(this)
            itemView.setOnClickListener(this)

            val url = data.images.firstPhoto.large
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
            if(view?.id == R.id.callDealerButton) {
                listener.onCallButtonClicked(adapterPosition)
                //Toast.makeText(binding.root.context, "Adapter Position: ".plus(adapterPosition), Toast.LENGTH_SHORT).show()
            }else {
                listener.onPostClicked(adapterPosition)
                //Toast.makeText(binding.root.context, "Adapter Position: ".plus(adapterPosition), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
