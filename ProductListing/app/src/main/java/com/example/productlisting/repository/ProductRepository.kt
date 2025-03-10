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
        println("HELLO PRODUCT!!")
        api.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                println("HELLO PRODUCT!! : ${response.body()}")
                if( response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println("HELLO PRODUCT!! FAILED $t")
                onResult(null)
            }

        })

    }

    fun createProduct(product: Product, onResult: (Product?) -> Unit) {
        println("HELLO PRODUCT!!")
        api.createProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println("HELLO PRODUCT!! : $response")
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
        println("UPDATE PRODUCT:: ${product}")
        api.updateProduct(id,product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println("UPDATE PRODUCT:: $response")
                println("UPDATE PRODUCT:: ${response.body()}")
                if(response.isSuccessful) {
                    onResult(response.body())
                }else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println("UPDATE PRODUCT:: $t")
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