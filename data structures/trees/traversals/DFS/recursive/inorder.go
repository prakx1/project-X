package trees

import (
	"fmt"
)

type Node struct {
	data  int
	left  *Node
	right *Node
}

// InOrder Traversal (Left, Root, Right)
func InOrder(root *Node) {
	if root == nil {
		return
	}
	InOrder(root.left)
	fmt.Print(root.data, " ")
	InOrder(root.right)
}

// PreOrder Traversal (Root, Left, Right)
func PreOrder(root *Node) {
	if root == nil {
		return
	}
	fmt.Print(root.data, " ")
	PreOrder(root.left)
	PreOrder(root.right)
}

// PostOrder Traversal (Left, Right, Root)
func PostOrder(root *Node) {
	if root == nil {
		return
	}
	PostOrder(root.left)
	PostOrder(root.right)
	fmt.Print(root.data, " ")
}

func DFSTraversals() {
	// Creating a sample binary tree
	root := &Node{data: 1}
	root.left = &Node{data: 2}
	root.right = &Node{data: 3}
	root.left.left = &Node{data: 4}
	root.left.right = &Node{data: 5}
	root.right.left = &Node{data: 6}
	root.right.right = &Node{data: 7}

	fmt.Println("InOrder Traversal:")
	InOrder(root)
	fmt.Println()

	fmt.Println("PreOrder Traversal:")
	PreOrder(root)
	fmt.Println()

	fmt.Println("PostOrder Traversal:")
	PostOrder(root)
	fmt.Println()
}
