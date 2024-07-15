import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.R
import com.example.shopnow.DiffUtilsCallback
import com.example.shopnow.OnItemClick
import com.example.shopnow.databinding.ForsaleDesignRvBinding
import com.example.shopnow.models.Products

class ProductsAdapter(private val itemClick: OnItemClick) : ListAdapter<Products,ProductsAdapter.ProductsViewHolder>(DiffUtilsCallback){

    class ProductsViewHolder(private var binding: ForsaleDesignRvBinding,itemClick: OnItemClick):RecyclerView.ViewHolder(binding.root){
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
        val binding = ForsaleDesignRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currItem = getItem(position)
        holder.setData(currItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}