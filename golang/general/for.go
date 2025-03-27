package general

import "fmt"

func main2() {
	// Basic for loop (like C-style loop)
	fmt.Println("Basic for loop:")
	for i := 0; i < 5; i++ {
		fmt.Println(i)
	}

	// For loop as a while loop
	fmt.Println("For loop as a while loop:")
	n := 0
	for n < 5 {
		fmt.Println(n)
		n++
	}

	// Infinite loop with break
	fmt.Println("Infinite loop with break:")
	count := 0
	for {
		fmt.Println("Looping...")
		count++
		if count == 3 {
			break
		}
	}

	// Looping over a slice using range
	fmt.Println("Looping over a slice:")
	numbers := []int{10, 20, 30, 40, 50}
	for index, value := range numbers {
		fmt.Printf("Index: %d, Value: %d\n", index, value)
	}

	// Looping over a string (runes)
	fmt.Println("Looping over a string:")
	str := "GoLang"
	for index, char := range str {
		fmt.Printf("Index: %d, Character: %c\n", index, char)
	}
}
