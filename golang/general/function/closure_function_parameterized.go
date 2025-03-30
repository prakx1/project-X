package general

import "fmt"

func multiplyBy(factor int) func(int) int {
	return func(num int) int {
		return num * factor
	}
}

func main() {
	double := multiplyBy(2)
	triple := multiplyBy(3)

	fmt.Println(double(5)) // 10
	fmt.Println(triple(5)) // 15
}
