package edge_case

import "fmt"

type Data struct {
	Value int
}

func (d Data) Modify() {
	d.Value = 100 // Modifies copy, not original
}

func main() {
	d := Data{10}
	d.Modify()
	fmt.Println(d.Value) // 10 (not 100)
}

// Trick: Always use a pointer receiver (*Data) to modify the original.
