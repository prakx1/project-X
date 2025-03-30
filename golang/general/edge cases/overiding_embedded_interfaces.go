package edge_case

import "fmt"

type Base struct{}

func (Base) Show() {
	fmt.Println("Base Show")
}

type Derived struct {
	Base
}

func (d Derived) Show() { // Overriding Base.Show()
	fmt.Println("Derived Show")
}

func overidingEmbeddedInterfaceExample() {
	var d Derived
	d.Show()      // Derived Show
	d.Base.Show() // Base Show
}
