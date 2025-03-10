package main

import (
	"products/config"
	"products/models"
	"products/routes"
)

func main() {
	config.ConnectDB()
	config.DB.AutoMigrate(&models.Product{})

	r := routes.SetupRouter()
	r.Run(":8080")
}
