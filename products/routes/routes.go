package routes

import (
	handlers "products/handler"

	"github.com/gin-gonic/gin"
)

func SetupRouter() *gin.Engine {
	r := gin.Default()

	r.POST("/products", handlers.CreateProduct)
	r.PUT("/products/:id", handlers.EditProduct)
	r.GET("/products", handlers.GetProducts)
	r.GET("/products/search", handlers.SearchProduct)

	return r
}
