package com.example.productlisting.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productlisting.api.RetrofitClient
import com.example.productlisting.repository.ProductRepository

class ProductViewModel: ViewModel() {
//    private val repository = ProductRepository()
//    private val _productsLiveData = MutableLiveData<List<Product>>()
//
//    val productsLiveData: LiveData<List<Product>> get()  = _productsLiveData
//
//    init {
//        _productsLiveData.value = repository.getProducts()
//    }
//
//    fun addProduct(product: Product) {
//        repository.addProduct(product)
//        _productsLiveData.value = repository.getProducts()
//    }
//
//    fun updateProduct(id: Int, product: Product) {
//        repository.updateProduct(id, product)
//        _productsLiveData.value = repository.getProducts()
//    }
//
//    fun searchProducts(query: String) {
//        _productsLiveData.value = repository.getProducts().filter {
//            it.name.contains(query, ignoreCase = true)
//        }
//    }

    private val repository = ProductRepository() // ✅ Inject API

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productCreationStatus = MutableLiveData<Boolean>()
    val productCreationStatus: LiveData<Boolean> = _productCreationStatus

    private val _productUpdateStatus = MutableLiveData<Boolean>()
    val productUpdateStatus: LiveData<Boolean> = _productUpdateStatus

    private val _searchResults = MutableLiveData<List<Product>>()
    val searchResults: LiveData<List<Product>> = _searchResults

    fun fetchProducts() {
        repository.getProducts { fetchedProducts ->
            _products.postValue(fetchedProducts)
        }
    }

    fun createProduct(product: Product) {
        repository.createProduct(product) { newProduct ->
            _productCreationStatus.postValue(newProduct != null)
            fetchProducts() // Refresh list after adding
        }
    }

    fun updateProduct(id: Int, product: Product) {
        repository.updateProduct(id, product) { updatedProduct ->
            _productUpdateStatus.postValue(updatedProduct != null)
            fetchProducts() // Refresh list after updating
        }
    }

    fun searchProducts(name: String) {
        repository.searchProducts(name) { result ->
            _searchResults.postValue(result)
        }
    }
}