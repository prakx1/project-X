package edge_case

import "fmt"

type A struct{}

func (A) Show() {
	fmt.Println("A's Show")
}

type B struct{}

func (B) Show() {
	fmt.Println("B's Show")
}

type C struct {
	A
	B
}

func overidingEdgeCase() {
	var c C
	// If there were only A, than c.show() would work
	// c.Show()  // Compilation error: ambiguous method
	c.A.Show() // Works
	c.B.Show() // Works
}
