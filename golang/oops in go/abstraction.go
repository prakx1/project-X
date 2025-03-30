package oops

import "fmt"

// Shape is an abstract interface that defines the behavior of different shapes
type Shape interface {
	Area() float64
	Perimeter() float64
}

// Circle is a concrete struct implementing Shape interface
type Circle struct {
	Radius float64
}

// Rectangle is another concrete struct implementing Shape interface
type Rectangle struct {
	Length, Width float64
}

// Implementing Shape interface methods for Circle
func (c Circle) Area() float64 {
	return 3.14 * c.Radius * c.Radius
}

func (c Circle) Perimeter() float64 {
	return 2 * 3.14 * c.Radius
}

// Implementing Shape interface methods for Rectangle
func (r Rectangle) Area() float64 {
	return r.Length * r.Width
}

func (r Rectangle) Perimeter() float64 {
	return 2 * (r.Length + r.Width)
}

// PrintShapeDetails demonstrates abstraction by using the Shape interface
func PrintShapeDetails(s Shape) {
	fmt.Printf("Area: %.2f\n", s.Area())
	fmt.Printf("Perimeter: %.2f\n", s.Perimeter())
}

func abstractionExample() {
	c := Circle{Radius: 5}
	r := Rectangle{Length: 4, Width: 6}

	fmt.Println("Circle Details:")
	PrintShapeDetails(c)

	fmt.Println("\nRectangle Details:")
	PrintShapeDetails(r)
}
