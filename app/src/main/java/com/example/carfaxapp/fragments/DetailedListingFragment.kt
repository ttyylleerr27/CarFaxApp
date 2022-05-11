package com.example.carfaxapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.carfaxapp.R
import com.example.carfaxapp.databinding.DetailedListingFragmentBinding
import java.text.DecimalFormat

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
        //onBackPressed()
        return binding.root
    }

    private fun initUI(){
        //Format the price of the car
        val decimalFormat = DecimalFormat("$##,###")

        args.listing.let{
            val url = it.images.firstPhoto.large

            /*
            If statement for looking to see if the url starts with carfax. If it does load in a coming soon picture.
            I had noticed all urls starting with this had no visible picture linked to it, so I came up with this solution.
             */
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
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(callIntent)
        }
    }

//    /**
//     * Custom onBackPressed to always go back to the landing page fragment.
//     * Otherwise, when a dial intent was clicked on in the landing page, the back button would always go back to that dial intent.
//     * This way creates a new landing page fragment instance which loses its state of location on the recycler view; however, this
//     * was the only viable solution I could come up with in the time I have.
//     */
//    private fun onBackPressed(){
//        requireActivity()
//            .onBackPressedDispatcher
//            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    findNavController().navigate(R.id.action_detailedListingFragment_to_landingPageFragment2)
//                }
//            })
//    }
}