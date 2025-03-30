package general

import "fmt"

func riskyFunction() {
	defer fmt.Println("Deferred: Closing resources")     // 3rd defer (executes 1st)
	defer fmt.Println("Deferred: Cleaning up variables") // 2nd defer (executes 2nd)

	defer func() {
		if err := recover(); err != nil {
			fmt.Println("Deferred: Caught panic -", err) // 1st defer (executes 3rd)
		}
	}()

	fmt.Println("Executing risky function")
	panic("Unexpected Error!")           // Triggers panic
	fmt.Println("This will not execute") // Unreachable code
}

func deferPanicRecoverExample() {
	fmt.Println("Calling riskyFunction()")
	riskyFunction()
	fmt.Println("Execution continues after recovery")
}

// Output :
// Calling riskyFunction()
// Executing risky function
// Deferred: Caught panic - Unexpected Error!
// Deferred: Cleaning up variables
// Deferred: Closing resources
// Execution continues after recovery
