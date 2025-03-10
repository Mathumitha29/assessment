package com.example.productlisting.api

import com.example.productlisting.model.Product

object MockProductApi {
    private val products = mutableListOf(
        Product(1, "Laptop", "Electronics", "https://fastly.picsum.photos/id/9/5000/3269.jpg?hmac=cZKbaLeduq7rNB8X-bigYO8bvPIWtT-mh8GRXtU3vPc", 999.99, "High-performance laptop"),
        Product(2, "Phone", "Electronics", "https://fastly.picsum.photos/id/30/1280/901.jpg?hmac=A_hpFyEavMBB7Dsmmp53kPXKmatwM05MUDatlWSgATE", 599.99, "Latest smartphone"),
        Product(3, "Shoes", "Fashion", "https://fastly.picsum.photos/id/21/3008/2008.jpg?hmac=T8DSVNvP-QldCew7WD4jj_S3mWwxZPqdF0CNPksSko4", 49.99, "Comfortable running shoes")
    )

    fun getProducts(): List<Product> = products

    fun addProduct(product: Product){
        products.add(product)
    }

    fun updateProduct(id: Int, updateProduct: Product) {
        products.replaceAll{
            if(it.ID == id)
                updateProduct
            else
                it
        }
    }
}