package com.example.productlisting.repository

import com.example.productlisting.api.MockProductApi
import com.example.productlisting.api.ProductApi
import com.example.productlisting.api.RetrofitClient
import com.example.productlisting.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    //Mock Api
//    fun getProducts(): List<Product> = MockProductApi.getProducts()
//    fun addProduct(product: Product) = MockProductApi.addProduct(product)
//    fun updateProduct(id: Int, product: Product) = MockProductApi.updateProduct(id, product)
    private val api = RetrofitClient.instance

    fun getProducts(onResult: (List<Product>?) -> Unit) {
        api.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if( response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                onResult(null)
            }

        })

    }

    fun createProduct(product: Product, onResult: (Product?) -> Unit) {
        api.createProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println("HELLO PRODUCT!! FAILED")
                onResult(null)
            }

        })
    }

    fun updateProduct(id: Int, product: Product, onResult: (Product?) -> Unit) {
        api.updateProduct(id,product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun searchProducts(name: String, onResult: (List<Product>?) -> Unit) {
        api.searchProducts(name).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                onResult(null)
            }

        })
    }
}