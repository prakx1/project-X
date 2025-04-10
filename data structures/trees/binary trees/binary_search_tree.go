package trees

import (
	"fmt"
)

// TreeNode represents a node in the binary tree
type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// Insert inserts a value into the BST
func Insert(root *TreeNode, val int) *TreeNode {
	if root == nil {
		return &TreeNode{Val: val}
	}

	if val < root.Val {
		root.Left = Insert(root.Left, val)
	} else {
		root.Right = Insert(root.Right, val)
	}

	return root
}

// InOrder prints the tree in in-order traversal
func InOrder(root *TreeNode) {
	if root == nil {
		return
	}

	InOrder(root.Left)
	fmt.Print(root.Val, " ")
	InOrder(root.Right)
}

func main() {
	var root *TreeNode

	// Insert values into the tree
	values := []int{5, 3, 7, 2, 4, 6, 8}
	for _, val := range values {
		root = Insert(root, val)
	}

	// Print the tree in-order (should be sorted)
	fmt.Print("Inorder Traversal: ")
	InOrder(root)
	fmt.Println()
}
