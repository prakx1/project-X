package oops

import "fmt"

type Speaker interface {
	Speak()
}

type Human struct{}

func (Human) Speak() {
	fmt.Println("Human speaks")
}

type Robot struct {
	Human
}

func (Robot) Speak() {
	fmt.Println("Robot speaks")
}

func overidingExample() {
	var s Speaker = Robot{}
	s.Speak() // Robot speaks (Overrides embedded method)
}

//  Trick: If Robot doesn’t override Speak(), it would use Human.Speak().
