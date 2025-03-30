package general

import "fmt"

func counter() func() int {
	count := 0
	return func() int {
		count++ // Retains the value of count
		return count
	}
}

func closureExample() {
	increment := counter()   // Creates a closure instance
	fmt.Println(increment()) // 1
	fmt.Println(increment()) // 2
	fmt.Println(increment()) // 3
}

// counter() returns an anonymous function that increments count.

// Each call to increment() retains and updates count.
