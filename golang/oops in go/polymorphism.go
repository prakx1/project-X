package oops

// Polymorphism in Go
// Polymorphism in Go is achieved using interfaces. Unlike traditional OOP languages that support method overloading, Go focuses on interface-based polymorphism, where different types implement the same interface.

// 🔹 Polymorphism Aspects in Go
// ✅ Compile-time Polymorphism (Method Overloading) → Not Supported in Go
// ✅ Run-time Polymorphism (Interface-based Polymorphism) → Supported
// ✅ Dynamic Dispatch (Function accepting different types via interface)
// ✅ Multiple Implementations of an Interface
// ✅ Type Assertion (Determining Concrete Type at Runtime)

import "fmt"

// ==========================
// 1️⃣ Interface Definition (Polymorphism)
// ==========================
type Shape1 interface {
	Area() float64
	Perimeter() float64
}

// ==========================
// 2️⃣ Struct Implementations of the Interface
// ==========================

// Circle1 struct implementing Shape1 interface
type Circle1 struct {
	Radius float64
}

// Implementing the Area() method for Circle1
func (c Circle1) Area() float64 {
	return 3.14 * c.Radius * c.Radius
}

// Implementing the Perimeter() method for Circle1
func (c Circle1) Perimeter() float64 {
	return 2 * 3.14 * c.Radius
}

// Rectangle1 struct implementing Shape1 interface
type Rectangle1 struct {
	Width, Height float64
}

// Implementing the Area() method for Rectangle1
func (r Rectangle1) Area() float64 {
	return r.Width * r.Height
}

// Implementing the Perimeter() method for Rectangle1
func (r Rectangle1) Perimeter() float64 {
	return 2 * (r.Width + r.Height)
}

// ==========================
// 3️⃣ Function Utilizing Polymorphism
// ==========================

// This function can work with any Shape1
func PrintShapeDetails1(s Shape1) {
	fmt.Printf("Area: %.2f, Perimeter: %.2f\n", s.Area(), s.Perimeter())
}

// ==========================
// 4️⃣ Type Assertion (Checking Concrete Type)
// ==========================
func IdentifyShape(s Shape1) {
	switch v := s.(type) {
	case Circle1:
		fmt.Println("Shape1 is a Circle1 with radius:", v.Radius)
	case Rectangle1:
		fmt.Println("Shape1 is a Rectangle1 with width:", v.Width, "and height:", v.Height)
	default:
		fmt.Println("Unknown shape")
	}
}

// ==========================
// 🔹 Main Function - Testing Polymorphism
// ==========================
func polymorphismExample() {
	// Creating instances of Circle1 and Rectangle1
	circle := Circle1{Radius: 5}
	rectangle := Rectangle1{Width: 4, Height: 6}

	// Using polymorphism to call methods
	fmt.Println("Circle1 Details:")
	PrintShapeDetails1(circle)

	fmt.Println("\nRectangle Details:")
	PrintShapeDetails1(rectangle)

	// Type assertion example
	fmt.Println("\nIdentifying Shapes:")
	IdentifyShape(circle)
	IdentifyShape(rectangle)
}

// Key Polymorphism Concepts Implemented
// 1️⃣ Interface-Based Polymorphism → Shape1 interface allows different struct types (Circle1, Rectangle1) to implement the same methods.
// 2️⃣ Dynamic Dispatch → PrintShapeDetails1() can accept any shape without knowing the exact type.
// 3️⃣ Multiple Implementations → Both Circle1 and Rectangle1 implement Shape1 interface differently.
// 4️⃣ Type Assertion & Type Switch → IdentifyShape() detects the concrete type at runtime.
