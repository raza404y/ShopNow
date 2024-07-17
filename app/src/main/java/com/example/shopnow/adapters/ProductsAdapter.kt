import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopnow.DiffUtilsCallback
import com.example.shopnow.activities.ProductDetails
import com.example.shopnow.databinding.ProductsDesignRvBinding
import com.example.shopnow.models.Products

class ProductsAdapter(private val context: Context) : ListAdapter<Products,ProductsAdapter.ProductsViewHolder>(DiffUtilsCallback){

    class ProductsViewHolder(var binding: ProductsDesignRvBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(products: Products){
            binding.apply {
                productName.text = products.productName
                productPrice.text = products.price.toString()
                Glide.with(itemView.context)
                    .load(products.productImgUrl)
                    .placeholder(com.example.shopnow.R.drawable.dresss)
                    .into(productImage)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ProductsDesignRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currItem = getItem(position)
        holder.setData(currItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetails::class.java).apply {
                putExtra("key_id",currItem.id)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}