package com.example.shopnow.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.shopnow.models.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter


 class SliderAdapter(context: Context) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    private val context: Context
    private var mSliderItems: MutableList<SliderItem> = ArrayList<SliderItem>()

    init {
        this.context = context
    }

    fun renewItems(sliderItems: MutableList<SliderItem>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(com.example.shopnow.R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderItem = mSliderItems[position]
        Glide.with(viewHolder.itemView)
            .load(sliderItem.image)
            .centerCrop()
            .into(viewHolder.imageViewBackground);


        viewHolder.itemView.setOnClickListener {
            fun onClick(v: View?) {
                Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {

        var imageViewBackground = itemView.findViewById<ImageView>(com.example.shopnow.R.id.iv_auto_image_slider)

    }
}