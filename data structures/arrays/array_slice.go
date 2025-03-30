package arrays

import "fmt"

func arrayExample() {
	// Declare Without Initialization (Zero Values)
	var arr [3]int // Default values: [0, 0, 0]

	// Declare and Initialize
	arr1 := [3]int{1, 2, 3} // Fixed size

	// Let Compiler Determine Size
	arr2 := [...]int{1, 2, 3, 4, 5} // Compiler sets length to 5

	// 4️⃣ Initialize Specific Indices
	arr3 := [5]int{0: 10, 3: 40} // [10, 0, 0, 40, 0]

	fmt.Println(arr, arr1, arr2, arr3)
}

func sliceExample() {

	// Using var (Zero Value is nil)

	var s []int           // nil slice (doesn't point to any array)
	fmt.Println(s == nil) // true
	// Using a Slice Literal

	s1 := []int{1, 2, 3, 4, 5} // Length: 5, Capacity: 5
	// Creating a Slice from an Array

	arr := [5]int{10, 20, 30, 40, 50}
	s2 := arr[1:4] // Creates slice [20, 30, 40]
	// Why? s points to arr and includes elements from index 1 (inclusive) to 4 (exclusive).

	// Using make() to Create a Slice

	s3 := make([]int, 5, 10)    // len=5, cap=10
	fmt.Println(len(s), cap(s)) // 5 10
	// Why? make() preallocates memory for efficiency.

	fmt.Println(s, s1, s2, s3)

	s4 := []int{1, 2, 3}
	s4 = append(s4, 4, 5)
	fmt.Println(s4) // Output: [1, 2, 3, 4, 5]

	// When the slice exceeds its capacity, Go allocates a new array with doubled capacity.
	s5 := make([]int, 2, 2) // len=2, cap=2
	s5 = append(s5, 100)    // Capacity doubles to accommodate new elements

	// nil slice has no memory allocation, while an empty slice has allocated space.
	var s11 []int  // nil slice
	s21 := []int{} // empty slice

	fmt.Println(s11 == nil) // true
	fmt.Println(s21 == nil) // false

	// append() Might Create a New Array

	s12 := []int{1, 2, 3}
	s22 := s12[:2] // [1, 2]
	s22 = append(s22, 99)

	fmt.Println(s12) // Might be [1, 2, 99] OR [1, 2, 3] (depends on capacity)
	// If s2's capacity allows, it modifies s1; otherwise, it creates a new array.

	// Copying vs Assigning - s1 and s2 share the same memory.
	s13 := []int{1, 2, 3}
	s23 := s13 // Points to same array!
	s23[0] = 100

	fmt.Println(s13) // [100, 2, 3]

	fmt.Println("Length:", len(s))
	fmt.Println("Capacity:", cap(s))

}
