package com.example.productlisting

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productlisting.model.Product
import com.example.productlisting.model.ProductViewModel
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class AddEditProductActivity: AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()

    private lateinit var nameEditText: EditText
    private lateinit var typeEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageUrlEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: MaterialButton

    private var isEditing: Boolean = false
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_product)

        nameEditText = findViewById(R.id.productNameEditText)
        typeEditText = findViewById(R.id.productTypeEditText)
        priceEditText = findViewById(R.id.productPriceEditText)
        descriptionEditText = findViewById(R.id.productDescriptionEditText)
        imageUrlEditText = findViewById(R.id.productImageUrlEditText)
        imageView = findViewById(R.id.productImageView)
        saveButton = findViewById(R.id.saveButton)

        // check if the product exist and then edit
        val product = intent.getParcelableExtra<Product>("product")
        productId = intent?.getIntExtra("PRODUCT_ID", -1)?.takeIf { it != -1 }

        if(productId != null) {

            val productName = intent.getStringExtra("PRODUCT_NAME")!!
            val productDesc = intent.getStringExtra("PRODUCT_DESCRIPTION")!!
            val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
            val productImage = intent.getStringExtra("PRODUCT_PICTURE")!!
            val productType = intent.getStringExtra("PRODUCT_TYPE")!!
            val product = Product(
                ID = productId!!,
                name =  productName,
                type = productType,
                picture = productImage,
                price = productPrice,
                description = productDesc
            )
            isEditing = true
            populateFields(product)
        }
//        if(product != null) {
//            isEditing = true
//            productId = product.id
//            populateFields(product)
//        }

        saveButton.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = nameEditText.text.toString()
        val type = typeEditText.text.toString()
        val price = priceEditText.text.toString().toDoubleOrNull()
        val description = descriptionEditText.text.toString()
        val imageUrl = imageUrlEditText.text.toString()

        if (name.isEmpty() || type.isEmpty() || price == null || description.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }
        val product = Product(
            ID = if (isEditing) productId!! else (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
            name = name,
            type = type,
            picture = imageUrl,
            price = price,
            description = description
        )
        if (isEditing) {
            productViewModel.updateProduct(productId!!, product)
            Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show()
        } else {
            productViewModel.createProduct(product)
            Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show()
        }

        finish()
    }


    private fun populateFields(product: Product) {
        nameEditText.setText(product.name)
        typeEditText.setText(product.type)
        priceEditText.setText(product.price.toString())
        descriptionEditText.setText(product.description)
        imageUrlEditText.setText(product.picture)
        Picasso.get().load(product.picture).into(imageView)
    }
}