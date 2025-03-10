package com.example.productlisting

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productlisting.R
import com.example.productlisting.adapter.ProductAdapter
import com.example.productlisting.api.RetrofitClient
import com.example.productlisting.model.Product
import com.example.productlisting.repository.ProductRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var repository: ProductRepository

    private lateinit var searchEditText: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var priceSeekBar: SeekBar
    private lateinit var priceTextView: TextView
    private lateinit var productCountTextView: TextView

    private var selectedType: String? = null
    private var maxPrice: Double = 10000.0
    private var products: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = ProductRepository()

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText)
        typeSpinner = findViewById(R.id.typeSpinner)
        priceSeekBar = findViewById(R.id.priceSeekBar)
        priceTextView = findViewById(R.id.priceTextView)
        productCountTextView = findViewById(R.id.productCountTextView)
        val addImageBtn: ImageButton = findViewById(R.id.addProductButton)

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.productsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(16))
        productAdapter = ProductAdapter{ product ->
            val intent = Intent(this, AddEditProductActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.ID)
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_TYPE", product.type)
            intent.putExtra("PRODUCT_PICTURE", product.picture)
            intent.putExtra("PRODUCT_PRICE", product.price)
            intent.putExtra("PRODUCT_DESCRIPTION", product.description)
            startActivity(intent)
        }
        recyclerView.adapter = productAdapter

        // Fetch products from API
        fetchProducts()

        // Setup Type Filter (Spinner)
        val types = listOf("All", "Electronics", "Utensils", "Footwears", "Food", "Fashion")
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = typeAdapter

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                selectedType = if (position == 0) null else types[position]
                filterProducts()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Setup Price Filter
        priceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxPrice = progress.toDouble()
                priceTextView.text = "Max Price: $maxPrice"
                filterProducts()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Setup Search Filter
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchProducts(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        addImageBtn.setOnClickListener{
            val intent = Intent(this, AddEditProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchProducts() {
        println("HELLO FETCH!!")
        repository.getProducts { fetchedProducts ->
            fetchedProducts?.let {
                products = it
                productCountTextView.text = "PRODUCT LISTING (${products.size})"

                productAdapter.setProducts(products)
            }
        }
    }

    private fun searchProducts(query: String) {
        if (query.isEmpty()) {
            filterProducts()
            repository.searchProducts(query) { searchResults ->
                searchResults?.let {
                    productAdapter.setProducts(it)
                    productCountTextView.text = "PRODUCT LISTING (${it.size})"
                }
            }
        } else {
            repository.searchProducts(query) { searchResults ->
                searchResults?.let {
                    productAdapter.setProducts(it)
                    productCountTextView.text = "PRODUCT LISTING (${it.size})"
                }
            }
        }
    }

    private fun filterProducts() {
        val filteredProducts = products.filter { product ->
            val matchesType = selectedType == null || product.type == selectedType
            val matchesPrice = product.price <= maxPrice
            matchesType && matchesPrice
        }
        productAdapter.setProducts(filteredProducts)
        productCountTextView.text = "PRODUCT LISTING (${filteredProducts.size})"
    }
}

// GridItemDecoration class to add spacing between items
class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val position = parent.getChildAdapterPosition(view) // Item position
        val column = position % spanCount // Column index

        // Apply padding around the grid items
        outRect.left = if (column == 0) 0 else spacing
        outRect.right = if (column == spanCount - 1) 0 else spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
}