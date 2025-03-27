package general

import (
	"fmt"
)

func main1() {
	// Boolean type
	var booleanVar bool = true

	// Integer types
	var intVar int = 42
	var int8Var int8 = 127
	var int16Var int16 = 32767
	var int32Var int32 = 2147483647
	var int64Var int64 = 9223372036854775807

	// Unsigned integer types
	var uintVar uint = 42
	var uint8Var uint8 = 255
	var uint16Var uint16 = 65535
	var uint32Var uint32 = 4294967295
	var uint64Var uint64 = 18446744073709551615

	// Floating-point types
	var float32Var float32 = 3.1415
	var float64Var float64 = 3.141592653589793

	// Complex types
	var complex64Var complex64 = complex(1, 2)
	var complex128Var complex128 = complex(3, 4)

	// String type
	var stringVar string = "Hello, Go!"

	// Byte and Rune types
	var byteVar byte = 'A' // Alias for uint8
	var runeVar rune = '界' // Alias for int32, used for Unicode characters

	// Printing values
	fmt.Println("Boolean:", booleanVar)
	fmt.Println("Integers:", intVar, int8Var, int16Var, int32Var, int64Var)
	fmt.Println("Unsigned Integers:", uintVar, uint8Var, uint16Var, uint32Var, uint64Var)
	fmt.Println("Floating Points:", float32Var, float64Var)
	fmt.Println("Complex Numbers:", complex64Var, complex128Var)
	fmt.Println("String:", stringVar)
	fmt.Println("Byte and Rune:", byteVar, runeVar)
}
