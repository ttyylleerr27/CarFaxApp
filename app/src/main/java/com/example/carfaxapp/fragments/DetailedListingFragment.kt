package com.example.carfaxapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.carfaxapp.R
import com.example.carfaxapp.databinding.DetailedListingFragmentBinding
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.network.ListInfo
import com.example.carfaxapp.viewmodels.LandingPageViewModel
import java.text.DecimalFormat
import java.util.*

class DetailedListingFragment : Fragment(R.layout.detailed_listing_fragment) {

    private var _binding: DetailedListingFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedListingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailedListingFragmentBinding.inflate(inflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI(){
        val decimalFormat = DecimalFormat("$##,###")
        args.listing.let{
            val url = it.images.firstPhoto.large
            if(url.startsWith("https://carfax-img.vast.com")){
                binding.carImageView2.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.coming_soon_image))
            }else{
                Glide.with(binding.carImageView2)
                    .load(url)
                    .into(binding.carImageView2)
            }
            binding.year.text = it.year.toString()
            binding.make.text = it.make
            binding.model.text = it.model
            binding.price.text = decimalFormat.format(it.currentPrice)
            binding.mileage.text = it.mileage.toString().plus("k mi")
            binding.location.text = it.dealer.city.plus(", " + it.dealer.state)
            binding.extColor.text = it.exteriorColor
            binding.intColor.text = it.interiorColor
            binding.driveType.text = it.drivetype
            binding.transmission.text = it.transmission
            binding.bodyType.text = it.bodytype
            binding.engine.text = it.engine
            binding.fuel.text = it.fuel

            callDealer(it.dealer.phone)
        }
    }

    private fun callDealer(phoneNumber: String){
        binding.callDealerButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }
    }
}