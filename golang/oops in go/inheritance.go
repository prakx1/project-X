package oops

// Go does not support traditional class-based inheritance like Java or C++, but it achieves inheritance-like behavior using struct embedding (composition). Below is a comprehensive Go program that demonstrates all possible aspects of inheritance in Go, including:

// ✅ Struct Embedding (Inheritance via Composition)
// ✅ Overriding Methods (Method Overriding)
// ✅ Calling Parent Methods (Base Method Access)
// ✅ Constructor Chaining (Parent Struct Initialization)
// ✅ Interface Implementation (Polymorphism with Inheritance)

import "fmt"

// ==========================
// 1️⃣ Base Struct (Parent)
// ==========================
type Person struct {
	Name string
	Age  int
}

// Constructor for Parent Struct
func NewPerson(name string, age int) *Person {
	return &Person{Name: name, Age: age}
}

// Base Method
func (p *Person) ShowDetails() {
	fmt.Printf("Name: %s, Age: %d\n", p.Name, p.Age)
}

// ==========================
// 2️⃣ Derived Struct (Child) - Inheriting via Embedding
// ==========================
type Employee struct {
	Person     // Inheriting Person
	EmployeeID string
	Department string
}

// Constructor for Employee (Derived Struct)
func NewEmployee(name string, age int, employeeID string, department string) *Employee {
	return &Employee{
		Person:     *NewPerson(name, age), // Constructor Chaining
		EmployeeID: employeeID,
		Department: department,
	}
}

// Overriding Parent Method
func (e *Employee) ShowDetails() {
	fmt.Printf("Employee: %s, Age: %d, ID: %s, Department: %s\n", e.Name, e.Age, e.EmployeeID, e.Department)
}

// Method to Call Parent Method
func (e *Employee) ShowPersonDetails() {
	e.Person.ShowDetails() // Explicitly calling parent method
}

// ==========================
// 3️⃣ Further Derived Struct (Multi-Level Inheritance)
// ==========================
type Manager struct {
	Employee // Inheriting Employee (Multi-level Inheritance)
	TeamSize int
}

// Constructor for Manager
func NewManager(name string, age int, employeeID string, department string, teamSize int) *Manager {
	return &Manager{
		Employee: *NewEmployee(name, age, employeeID, department),
		TeamSize: teamSize,
	}
}

// Overriding ShowDetails in Manager
func (m *Manager) ShowDetails() {
	fmt.Printf("Manager: %s, Age: %d, ID: %s, Department: %s, Team Size: %d\n",
		m.Name, m.Age, m.EmployeeID, m.Department, m.TeamSize)
}

// ==========================
// 4️⃣ Polymorphism with Inheritance
// ==========================
type Worker interface {
	ShowDetails()
}

func DisplayWorkerDetails(w Worker) {
	w.ShowDetails()
}

// ==========================
// 🔹 Main Function - Testing Inheritance
// ==========================
func inheritanceExample() {
	// Creating a Person
	p := NewPerson("Alice", 30)
	p.ShowDetails()

	// Creating an Employee
	e := NewEmployee("Bob", 35, "EMP123", "Engineering")
	e.ShowDetails()
	e.ShowPersonDetails() // Calling Parent Method

	// Creating a Manager (Multi-Level Inheritance)
	m := NewManager("Charlie", 40, "MGR456", "Sales", 10)
	m.ShowDetails()

	// Polymorphism - Different Structs Implementing the Same Method
	fmt.Println("\nPolymorphism Example:")
	DisplayWorkerDetails(e) // Employee implementing Worker interface
	DisplayWorkerDetails(m) // Manager implementing Worker interface
}

// 🔹 Key Inheritance Concepts Implemented
// 1️⃣ Struct Embedding (Inheritance via Composition) → Employee embeds Person, Manager embeds Employee.
// 2️⃣ Method Overriding → ShowDetails() is overridden in both Employee and Manager.
// 3️⃣ Calling Parent Methods → ShowPersonDetails() explicitly calls the parent method from Employee.
// 4️⃣ Constructor Chaining → Employee and Manager initialize their parent structs using constructors.
// 5️⃣ Multi-Level Inheritance → Manager inherits from Employee, which inherits from Person.
// 6️⃣ Polymorphism with Inheritance → Worker interface allows different struct types to be handled generically.
