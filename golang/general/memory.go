package general

import "fmt"

func dynamicMemoryAllocationExamples() {
	// Example 1: Allocating memory for an integer using new
	intPointer := new(int)
	*intPointer = 42
	fmt.Println("Value of dynamically allocated integer:", *intPointer)

	// Example 2: Allocating memory for a struct using new
	type Person struct {
		Name string
		Age  int
	}
	personPointer := new(Person)
	personPointer.Name = "Alice"
	personPointer.Age = 30
	fmt.Println("Dynamically allocated struct:", *personPointer)

	// Example 3: Allocating memory for a slice using make
	slice := make([]int, 5) // Creates a slice of length 5
	slice[0] = 10
	slice[1] = 20
	fmt.Println("Dynamically allocated slice:", slice)

	// Example 4: Allocating memory for a map using make
	personMap := make(map[string]int)
	personMap["Alice"] = 30
	personMap["Bob"] = 25
	fmt.Println("Dynamically allocated map:", personMap)

	// Example 5: Allocating memory for a channel using make
	ch := make(chan int, 2)
	ch <- 100
	ch <- 200
	fmt.Println("Dynamically allocated channel values:", <-ch, <-ch)
}
