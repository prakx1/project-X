package general

import (
	"fmt"
	"log"
	"os"
)

func demonstrateOutput() {
	// Standard output using fmt
	fmt.Println("Hello, World!")           // Println adds a newline
	fmt.Print("Hello, ")                   // Print does not add a newline
	fmt.Printf("%s\n", "Formatted Output") // Printf for formatted output

	// Using log package
	log.Println("This is a log message")
	log.Printf("Logging formatted value: %d", 42)

	// Writing to a file
	file, err := os.Create("output.txt")
	if err != nil {
		log.Fatalf("Failed to create file: %v", err)
	}
	defer file.Close()

	// Writing output to file
	file.WriteString("Hello, File!\n")
	fmt.Fprintln(file, "Writing using fmt.Fprintln")
	fmt.Fprintf(file, "Formatted Output: %d\n", 100)
}
