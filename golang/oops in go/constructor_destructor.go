package oops

import "fmt"

// Database represents a simple database connection
type Database struct {
	ConnectionString string
}

// NewDatabase constructor
func NewDatabase(connectionString string) *Database {
	fmt.Println("Database connection established")
	return &Database{ConnectionString: connectionString}
}

// Close acts as a destructor to clean up resources
func (db *Database) Close() {
	fmt.Println("Database connection closed")
}

func constructorDestructor() {
	// Creating a new database connection
	db := NewDatabase("localhost:3306")

	// Using defer to ensure cleanup
	defer db.Close()

	fmt.Println("Performing database operations...")
}
