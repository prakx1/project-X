package arrays

import (
	"fmt"
	"sync"
)

type User struct {
	Name string
	Age  int
}

func mapsExample() {
	// 1️⃣ Basic Map Declaration & Initialization
	m1 := make(map[string]int)                           // Using make()
	m2 := map[string]int{"one": 1, "two": 2, "three": 3} // Using map literal
	var m3 map[string]int                                // Nil map (cannot add values)
	_ = m3

	// 2️⃣ Adding, Updating, and Deleting Elements
	m1["four"] = 4
	m1["one"] = 10 // Update existing key
	delete(m1, "one")

	// 3️⃣ Accessing Elements and Checking Existence
	val, exists := m2["two"]
	if exists {
		fmt.Println("Key found:", val)
	} else {
		fmt.Println("Key not found")
	}

	// 4️⃣ Iterating Over a Map (Order is Random)
	for key, value := range m2 {
		fmt.Println("Key:", key, "Value:", value)
	}

	// 5️⃣ Working with Structs in Maps
	userMap := make(map[string]User)
	userMap["Alice"] = User{Name: "Alice", Age: 25}
	fmt.Println("Struct in Map:", userMap["Alice"])

	// 6️⃣ Maps as Sets (Using `bool` Values)
	set := make(map[string]bool)
	set["item1"] = true
	if set["item1"] {
		fmt.Println("Item exists in set!")
	}

	// 7️⃣ Two-Dimensional (Nested) Map
	twoDMap := make(map[string]map[string]int)
	twoDMap["A"] = map[string]int{"x": 1, "y": 2}
	fmt.Println("2D Map Value:", twoDMap["A"]["x"])

	// 8️⃣ Three-Dimensional Map
	threeDMap := make(map[string]map[string]map[string]int)
	threeDMap["A"] = make(map[string]map[string]int)
	threeDMap["A"]["B"] = make(map[string]int)
	threeDMap["A"]["B"]["C"] = 100
	fmt.Println("3D Map Value:", threeDMap["A"]["B"]["C"])

	// 9️⃣ Safe Concurrent Map Access Using sync.Map
	var syncMap sync.Map
	syncMap.Store("key1", 123)
	syncMap.Store("key2", "Hello")

	value, exists := syncMap.Load("key1")
	if exists {
		fmt.Println("sync.Map Key1:", value)
	}

	//  🔟 Edge Cases
	var nilMap map[string]int // Nil map
	// nilMap["test"] = 10    // PANIC: assignment to entry in nil map

	// Checking if map is nil
	if nilMap == nil {
		fmt.Println("Map is nil")
	}
}
