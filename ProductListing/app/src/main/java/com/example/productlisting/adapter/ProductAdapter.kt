package com.example.productlisting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.productlisting.R
import com.example.productlisting.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter (private val onItemClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = listOf()

    fun setProducts(products: List<Product>) {
        this.productList = products
        println("ProductAdapter: $products")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productDescription: TextView = itemView.findViewById(R.id.productDescription)


        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = "$${product.price}"
            productDescription.text = product.description
            Picasso.get().load(product.picture).into(productImage)
//            Glide.with(itemView.context).load(product.picture).into(productImage)
        }
    }
}