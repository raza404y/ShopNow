package com.example.shopnow.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopnow.activities.CategoriesProducts
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

        val list = list[position]
        holder.binding.categoryImage.setImageResource(list.categoryImage)
        holder.binding.categoryName.text = list.category

        holder.itemView.setOnClickListener {
            val intent = Intent(context,CategoriesProducts::class.java)
            intent.putExtra("key_name",list.category)
            context.startActivity(intent)
        }

    }

}