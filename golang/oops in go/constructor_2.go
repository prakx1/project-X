package oops

// 3️⃣ Using a Struct with an Embedded Method
// Another trick is to use an unexported embedded struct that initializes itself when instantiated.
// How it Works?

// When Database{} is instantiated, it implicitly calls dbHelper.init().

// Avoids explicit constructor calls while keeping initialization logic inside the struct.

import "fmt"

// Internal helper struct (unexported)
type dbHelper struct{}

// Implicitly called constructor-like method
func (dbHelper) init() string {
	fmt.Println("Database Initialized")
	return "localhost:3306"
}

// Database struct
type Database1 struct {
	ConnectionString string
	help             dbHelper // Embedding to trigger initialization
}

func main() {
	db := Database1{} // No explicit constructor call needed
	db.ConnectionString = db.help.init()
	fmt.Println("Connected to:", db.ConnectionString)
}
