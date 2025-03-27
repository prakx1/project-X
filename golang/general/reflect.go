package general

import (
	"fmt"
	"reflect"
)

func reflectExamples() {
	// Example 1: Get type of a variable
	var num int = 42
	fmt.Println("Type of num:", reflect.TypeOf(num))

	// Example 2: Get value of a variable
	fmt.Println("Value of num:", reflect.ValueOf(num))

	// Example 3: Check if a variable is of a certain type
	var str string = "Hello"
	if reflect.TypeOf(str).Kind() == reflect.String {
		fmt.Println("str is of type string")
	}

	// Example 4: Reflect on a struct
	type Person struct {
		Name string
		Age  int
	}

	person := Person{Name: "Alice", Age: 30}

	typeOfPerson := reflect.TypeOf(person)
	valueOfPerson := reflect.ValueOf(person)

	fmt.Println("Struct Type:", typeOfPerson.Name())
	for i := 0; i < typeOfPerson.NumField(); i++ {
		field := typeOfPerson.Field(i)
		value := valueOfPerson.Field(i)
		fmt.Printf("Field Name: %s, Field Type: %s, Field Value: %v\n", field.Name, field.Type, value)
	}

	// Example 5: Modify a value using reflection
	var x int = 10
	p := reflect.ValueOf(&x).Elem()
	if p.CanSet() {
		p.SetInt(20)
	}
	fmt.Println("Modified x:", x)
}
