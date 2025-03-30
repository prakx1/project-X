package general

import "fmt"

type Person struct {
	Name string
	Age  int
}

func structLiteralExample() {
	// 1️⃣ Named Fields (Recommended)
	p1 := Person{Name: "Mehul", Age: 24}
	fmt.Println("Named Fields:", p1) // Output: {Mehul 24}

	// 2️⃣ Ordered Fields (Not Recommended)
	p2 := Person{"Mehul", 24}
	fmt.Println("Ordered Fields:", p2) // Output: {Mehul 24}

	// 3️⃣ Partial Initialization (Zero Value for Missing Fields)
	p3 := Person{Name: "Mehul"}                // Age defaults to 0
	fmt.Println("Partial Initialization:", p3) // Output: {Mehul 0}

	// 4️⃣ Struct Literal with Pointer
	p4 := &Person{Name: "Mehul", Age: 24}
	fmt.Println("Pointer to Struct:", p4) // Output: &{Mehul 24}

	// 5️⃣ Anonymous Struct
	p5 := struct {
		Name string
		Age  int
	}{"Mehul", 24}
	fmt.Println("Anonymous Struct:", p5) // Output: {Mehul 24}

	// 6️⃣ Struct Literals Inside a Slice
	people := []Person{
		{Name: "Alice", Age: 30},
		{Name: "Bob", Age: 28},
	}
	fmt.Println("Slice of Structs:", people) // Output: [{Alice 30} {Bob 28}]

	// 7️⃣ Struct Literals Inside a Map
	personMap := map[string]Person{
		"first":  {Name: "Charlie", Age: 35},
		"second": {Name: "David", Age: 29},
	}
	fmt.Println("Map of Structs:", personMap) // Output: map[first:{Charlie 35} second:{David 29}]
}
