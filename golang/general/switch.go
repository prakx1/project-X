package general

import "fmt"

// Basic switch Example
func switchExample1() {
	day := "Monday"

	switch day {
	case "Monday":
		fmt.Println("Start of the workweek!")
	case "Friday":
		fmt.Println("Almost the weekend!")
	case "Saturday", "Sunday":
		fmt.Println("It's the weekend!")
	default:
		fmt.Println("Just another weekday.")
	}
}

// Switch with fallthrough
func switchExample2() {
	num := 1

	switch num {
	case 1:
		fmt.Println("One")
		fallthrough
	case 2:
		fmt.Println("Two (fallthrough executed)")
	default:
		fmt.Println("Other number")
	}
}

// Switch Without an Expression (Like an if-else Chain)
func switchExample3() {
	age := 25

	switch {
	case age < 18:
		fmt.Println("You're a minor.")
	case age >= 18 && age < 65:
		fmt.Println("You're an adult.")
	default:
		fmt.Println("You're a senior citizen.")
	}
}

// Switch with Type Assertions (type switch)
func switchExample4(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Println("This is an int:", v)
	case string:
		fmt.Println("This is a string:", v)
	case bool:
		fmt.Println("This is a bool:", v)
	default:
		fmt.Println("Unknown type")
	}
}
