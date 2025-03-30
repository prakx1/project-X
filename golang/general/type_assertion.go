package general

import "fmt"

func checkType(i interface{}) {
	switch v := i.(type) {
	case string:
		fmt.Println("It's a string:", v)
	case int:
		fmt.Println("It's an int:", v)
	default:
		fmt.Println("Unknown type")
	}
}

func typeAssertionExample() {
	checkType(42)
	checkType("golang")
	checkType(3.14)
}

// Edge Cases for Interviews
// What if the assertion fails?

// Without ok, it panics.

// With ok, it safely handles failure.

// How does type assertion work with nil?

// var i interface{}
// s, ok := i.(string) // ok is false, s is empty string
// fmt.Println(s, ok)  // Output: "" false

// Can you assert custom types?

// type MyType struct{}
// var i interface{} = MyType{}
// _, ok := i.(MyType) // Works fine
