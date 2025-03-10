package handlers

import (
	"net/http"

	"products/config"
	"products/models"

	"github.com/gin-gonic/gin"
)

// Create Product
func CreateProduct(c *gin.Context) {
	var product models.Product
	if err := c.ShouldBindJSON(&product); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	config.DB.Create(&product)
	c.JSON(http.StatusCreated, product)
}

// Edit Product
func EditProduct(c *gin.Context) {
	id := c.Param("id")
	var product models.Product
	if err := config.DB.First(&product, id).Error; err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Product not found"})
		return
	}

	if err := c.ShouldBindJSON(&product); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	config.DB.Save(&product)
	c.JSON(http.StatusOK, product)
}

// Get Product List with Sorting
func GetProducts(c *gin.Context) {
	var products []models.Product
	sortBy := c.Query("sort_by")
	order := c.Query("order")

	query := config.DB

	if sortBy == "price" {
		query = query.Order("price " + order)
	} else if sortBy == "type" {
		query = query.Order("type " + order)
	}

	query.Find(&products)
	c.JSON(http.StatusOK, products)
}

// Search Product by Name
func SearchProduct(c *gin.Context) {
	var products []models.Product
	name := c.Query("name")

	config.DB.Where("name ILIKE ?", "%"+name+"%").Find(&products)
	c.JSON(http.StatusOK, products)
}
