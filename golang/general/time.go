package general

import (
	"fmt"
	"time"
)

func timeExamples() {
	// Example 1: Get current time
	currentTime := time.Now()
	fmt.Println("Current Time:", currentTime)

	// Example 2: Formatting time
	formattedTime := currentTime.Format("2006-01-02 15:04:05")
	fmt.Println("Formatted Time:", formattedTime)

	// Example 3: Parsing a time string
	timeString := "2025-03-27 14:00:00"
	parsedTime, err := time.Parse("2006-01-02 15:04:05", timeString)
	if err != nil {
		fmt.Println("Error parsing time:", err)
	} else {
		fmt.Println("Parsed Time:", parsedTime)
	}

	// Example 4: Adding and subtracting time
	futureTime := currentTime.Add(24 * time.Hour)
	pastTime := currentTime.Add(-24 * time.Hour)
	fmt.Println("24 hours later:", futureTime)
	fmt.Println("24 hours ago:", pastTime)

	// Example 5: Measuring execution time
	start := time.Now()
	time.Sleep(2 * time.Second) // Simulating a process
	elapsed := time.Since(start)
	fmt.Println("Execution Time:", elapsed)

	// Example 6: Using time.Ticker for periodic tasks
	ticker := time.NewTicker(1 * time.Second)
	go func() {
		for t := range ticker.C {
			fmt.Println("Tick at:", t)
		}
	}()

	time.Sleep(5 * time.Second) // Let the ticker run for 5 seconds
	ticker.Stop()
	fmt.Println("Ticker stopped")
}
