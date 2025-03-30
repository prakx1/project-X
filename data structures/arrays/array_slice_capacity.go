package arrays

import "fmt"

func arraySliceCapacity() {
	// 📌 1️⃣ Array: Length and Capacity are always equal
	arr := [5]int{10, 20, 30, 40, 50}
	fmt.Println("Array:", arr)
	fmt.Println("Array Length:", len(arr))   // 5
	fmt.Println("Array Capacity:", cap(arr)) // 5

	fmt.Println("\n--- Slices ---\n")

	// 📌 2️⃣ Slice created from an array
	sliceFromArr := arr[1:4] // Elements: [20, 30, 40]
	fmt.Println("Slice from Array:", sliceFromArr)
	fmt.Println("Slice Length:", len(sliceFromArr))   // 3 (indices: 1 to 3)
	fmt.Println("Slice Capacity:", cap(sliceFromArr)) // 4 (from index 1 to end of array)

	// 📌 3️⃣ Slice created with make()
	s := make([]int, 3, 5) // len=3, cap=5
	fmt.Println("\nSlice from make():", s)
	fmt.Println("Slice Length:", len(s))   // 3
	fmt.Println("Slice Capacity:", cap(s)) // 5

	// 📌 4️⃣ Appending beyond capacity (triggers reallocation)
	s = append(s, 1, 2, 3) // Now exceeds cap=5
	fmt.Println("\nSlice after append:", s)
	fmt.Println("New Length:", len(s))   // 6
	fmt.Println("New Capacity:", cap(s)) // 10 (doubles when exceeded)

	// 📌 5️⃣ Subslice & its capacity calculation
	baseSlice := []int{1, 2, 3, 4, 5, 6, 7, 8, 9}
	subSlice := baseSlice[2:5] // Elements: [3, 4, 5]
	fmt.Println("\nSubslice:", subSlice)
	fmt.Println("Subslice Length:", len(subSlice))   // 3
	fmt.Println("Subslice Capacity:", cap(subSlice)) // 7 (from index 2 to end)

	// 📌 6️⃣ Checking capacity after re-slicing
	extendedSlice := subSlice[:cap(subSlice)] // Extends to full capacity
	fmt.Println("\nExtended Slice:", extendedSlice)
	fmt.Println("Extended Slice Length:", len(extendedSlice))   // 7
	fmt.Println("Extended Slice Capacity:", cap(extendedSlice)) // 7

	// 📌 7️⃣ Empty slice: Zero length & zero capacity
	var emptySlice []int
	fmt.Println("\nEmpty Slice Length:", len(emptySlice)) // 0
	fmt.Println("Empty Slice Capacity:", cap(emptySlice)) // 0

	// 📌 8️⃣ Nil vs. Empty Slice
	var nilSlice []int = nil
	nonNilSlice := []int{}
	fmt.Println("\nNil Slice:", nilSlice, "Len:", len(nilSlice), "Cap:", cap(nilSlice))
	fmt.Println("Empty Slice:", nonNilSlice, "Len:", len(nonNilSlice), "Cap:", cap(nonNilSlice))

	// 📌 9️⃣ Slices with zero capacity
	zeroCapSlice := make([]int, 0, 10) // Length = 0, Capacity = 10
	fmt.Println("\nZero-length Slice with Capacity:", cap(zeroCapSlice))

	// 📌 🔥 Edge Case: Slice grows exponentially
	dynamicSlice := []int{}
	for i := 0; i < 20; i++ {
		dynamicSlice = append(dynamicSlice, i)
		fmt.Printf("Iteration %d - Length: %d, Capacity: %d\n", i+1, len(dynamicSlice), cap(dynamicSlice))
	}
}
