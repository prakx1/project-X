package general

import "fmt"

type User struct {
	name string
}

// Method with a value receiver
func (u User) SetName(newName string) {
	u.name = newName // Modifies a copy, not the original
}

// Method to display the name
func (u User) GetName() string {
	return u.name
}

func valueReceiverExample() {
	user := User{name: "Mehul"}
	user.SetName("Lokhande")    // Does NOT modify original struct
	fmt.Println(user.GetName()) // Output: Mehul
}

// SetName modifies a copy of User, so the original user remains unchanged.
