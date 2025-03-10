package com.example.productlisting.api

import com.example.productlisting.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {
    @GET("/products")
    fun getProducts(): Call<List<Product>>

    @POST("/products")
    fun createProduct(@Body product: Product): Call<Product>

    @PUT("/products/{id}")
    fun updateProduct(@Path("id") id: Int, @Body product: Product): Call<Product>

    @GET("/products/search")
    fun searchProducts(@Query("name") name: String): Call<List<Product>>
}