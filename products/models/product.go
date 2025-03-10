package models

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Name        string  `json:"name"`
	Type        string  `json:"type"`
	Picture     string  `json:"picture"`
	Price       float64 `json:"price"`
	Description string  `json:"description"`
}
