package com.example.shopnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopnow.databinding.CategoriesDesignRvBinding
import com.example.shopnow.models.Categories

class CategoryAdapter(private val list: ArrayList<Categories>,private val context: Context):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding:CategoriesDesignRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CategoriesDesignRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.categoryImage.setImageResource(list[position].categoryImage)
        holder.binding.categoryName.text = list[position].categoryName

    }

}