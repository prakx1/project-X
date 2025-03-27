package general

import (
	"bufio"
	"fmt"
	"os"
)

func inputExamples() {
	// Example 1: Reading a single string input
	var name string
	fmt.Print("Enter your name: ")
	fmt.Scanln(&name)
	fmt.Println("Hello,", name)

	// Example 2: Reading multiple values
	var age int
	var city string
	fmt.Print("Enter your age and city: ")
	fmt.Scanf("%d %s", &age, &city)
	fmt.Printf("You are %d years old and live in %s\n", age, city)

	// Example 3: Reading input using bufio (handles spaces)
	reader := bufio.NewReader(os.Stdin)
	fmt.Print("Enter your full address: ")
	address, _ := reader.ReadString('\n')
	fmt.Println("Your address is:", address)

	// Example 4: Reading input from a file
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	fmt.Println("Reading file content:")
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading file:", err)
	}
}
