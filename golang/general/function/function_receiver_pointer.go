package general

import "fmt"

type User1 struct {
	name string
}

// Method with a pointer receiver
func (u *User1) SetName(newName string) {
	u.name = newName // Modifies the original struct
}

func (u User1) GetName1() string {
	return u.name
}

func pointerFunctionReceiver() {
	user := User1{name: "Mehul"}
	user.SetName("Lokhande")     // Modifies original struct
	fmt.Println(user.GetName1()) // Output: Lokhande
}

// 🔹 Key Concept:
// SetName modifies the actual instance because u *User1 is a pointer.
// Changes persist outside the method.
