package arrays

func arrayExamples() {
	// ✅ Declare a 2D slice (dynamic size)
	var matrix [][]int

	// ✅ Append rows dynamically
	matrix = append(matrix, []int{1, 2, 3})
	matrix = append(matrix, []int{4, 5, 6})
	matrix = append(matrix, []int{7, 8, 9})

	// ✅ Declare a 3D slice (dynamic size)
	var cube [][][]int

	// ✅ Append layers dynamically
	cube = append(cube, [][]int{
		{1, 2, 3}, {4, 5, 6}, {7, 8, 9},
	})
	cube = append(cube, [][]int{
		{10, 11, 12}, {13, 14, 15}, {16, 17, 18},
	})
}
