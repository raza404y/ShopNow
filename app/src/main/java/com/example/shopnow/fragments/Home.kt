package com.example.shopnow.fragments

import ProductsAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopnow.Constants
import com.example.shopnow.R
import com.example.shopnow.activities.SeeMoreProducts
import com.example.shopnow.adapters.CategoryAdapter
import com.example.shopnow.adapters.SliderAdapter
import com.example.shopnow.databinding.FragmentHomeBinding
import com.example.shopnow.models.Categories
import com.example.shopnow.models.Products
import com.example.shopnow.models.SliderItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var adapterSlider: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllProducts()


        binding.seemoreBtn.setOnClickListener {
            startActivity(Intent(requireContext(),SeeMoreProducts::class.java))
        }

        val categoriesList = ArrayList<Categories>()


        categoriesList.add(Categories(R.drawable.dresss, "Casual"))
        categoriesList.add(Categories(R.drawable.dresss, "Formal"))
        categoriesList.add(Categories(R.drawable.dresss, "Party"))
        categoriesList.add(Categories(R.drawable.dresss, "Summer"))
        categoriesList.add(Categories(R.drawable.dresss, "Winter"))
        categoriesList.add(Categories(R.drawable.dresss, "Maxi"))



        val adapter = CategoryAdapter(categoriesList, requireContext())
        binding.categoriesRv.adapter = adapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRv.layoutManager = layoutManager


        val sliderView = view.findViewById<SliderView>(R.id.imageSlider)

        adapterSlider = SliderAdapter(requireContext());
        sliderView.setSliderAdapter(adapterSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH;
        sliderView.indicatorSelectedColor = resources.getColor(R.color.pink)
        sliderView.indicatorUnselectedColor = Color.WHITE;
        sliderView.scrollTimeInSec = 3;
        sliderView.isAutoCycle = true;
        sliderView.startAutoCycle();

        // Initialize slider items
        renewItems(view)

        // Set button listeners
//        view.findViewById<Button>(R.id.button_renew_items).setOnClickListener {
//            renewItems(view)
//        }
//        view.findViewById<Button>(R.id.button_remove_last_item).setOnClickListener {
//            removeLastItem(view)
//        }
//        view.findViewById<Button>(R.id.button_add_new_item).setOnClickListener {
//            addNewItem(view)
//        }


    }

    private fun renewItems(view: View?) {
        val sliderItemList: MutableList<SliderItem> = ArrayList()
        //dummy data
        for (i in 0..3) {
            val sliderItem = SliderItem()
            sliderItem.image = when (i) {
                0 -> "https://img.freepik.com/free-photo/clothing-rack-with-hawaiian-shirts-with-floral-print_23-2149366012.jpg?semt=sph"
                1 -> "https://img.freepik.com/free-photo/men39s-clothes-hanger-generative-ai_169016-29035.jpg?semt=sph"
                2 -> "https://img.freepik.com/free-photo/cute-woman-bright-hat-purple-blouse-is-leaning-stand-with-dresses-posing-with-package-isolated-background_197531-17610.jpg?semt=sph"
                else -> "https://img.freepik.com/free-photo/young-stylish-woman-with-gift-box-celebrating-wearing-black-dress-black-crown-happy-birthday-party-sparkling-gold-confetti-having-fun_197531-2632.jpg?semt=sph"
            }
            sliderItemList.add(sliderItem)
        }
        adapterSlider.renewItems(sliderItemList)
    }


    fun removeLastItem(view: View?) {
        if (adapterSlider.getCount() - 1 >= 0) adapterSlider.deleteItem(adapterSlider.getCount() - 1)
    }

    fun addNewItem(view: View?) {
        val sliderItem = SliderItem()
        sliderItem.image =
            "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        adapterSlider.addItem(sliderItem)
    }

    private fun getAllProducts() {
        val productList = ArrayList<Products>()
        val adapter = ProductsAdapter(requireContext())
        binding.forsaleRecyclerView.adapter = adapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.forsaleRecyclerView.layoutManager = layoutManager


        Firebase.firestore.collection(Constants.products).limit(5).get().addOnSuccessListener {
            productList.clear()
            for (i in it) {
                val products = i.toObject(Products::class.java)
                products.id = i.id
                productList.add(products)
            }
            productList.shuffle()
            adapter.submitList(productList)
        }

    }
}