package general

import (
	"encoding/json"
	"fmt"
	"reflect"
)

// Define a struct with JSON tags
type Person1 struct {
	Name    string   `json:"name"`
	Age     int      `json:"age"`
	Email   string   `json:"email,omitempty"` // `omitempty` excludes empty values
	Hobbies []string `json:"hobbies"`
	Secret  string   `json:"-"` // `-` prevents this field from being included in JSON
}

func jsonExample() {
	// Sample struct instance
	p1 := Person1{Name: "Alice", Age: 25, Email: "alice@example.com", Hobbies: []string{"Reading", "Swimming"}, Secret: "HiddenInfo"}

	// ✅ 1. JSON Marshalling (Convert struct to JSON)
	jsonData, err := json.Marshal(p1)
	if err != nil {
		fmt.Println("Error marshalling JSON:", err)
		return
	}
	fmt.Println("JSON Output:", string(jsonData))

	// ✅ 2. JSON Unmarshalling (Convert JSON to struct)
	var p2 Person1
	err = json.Unmarshal(jsonData, &p2)
	if err != nil {
		fmt.Println("Error unmarshalling JSON:", err)
		return
	}
	fmt.Println("Unmarshalled Struct:", p2)

	// ✅ 3. Compare Two Structs (Deep Equal)
	p3 := Person1{Name: "Alice", Age: 25, Email: "alice@example.com", Hobbies: []string{"Reading", "Swimming"}}
	fmt.Println("Are p1 and p3 identical?", reflect.DeepEqual(p1, p3)) // false (Secret field differs)
	fmt.Println("Are p2 and p3 identical?", reflect.DeepEqual(p2, p3)) // true (Secret field is omitted in JSON)

	// ✅ 4. JSON Handling Missing Fields
	jsonString := `{"name":"Bob","age":30}` // Email and Hobbies are missing
	var p4 Person1
	json.Unmarshal([]byte(jsonString), &p4)
	fmt.Println("Struct with missing fields:", p4) // Email: "" and Hobbies: nil

	// ✅ 5. Handling JSON with Extra Fields (Ignored)
	extraFieldJSON := `{"name":"Eve","age":28,"email":"eve@example.com","extra":"ignored"}`
	var p5 Person1
	json.Unmarshal([]byte(extraFieldJSON), &p5)
	fmt.Println("Struct ignoring extra fields:", p5)
}
