package trees

// Depth of a node = Number of edges from the root to that node.
// Maximum depth of the tree = Depth of the deepest node (a leaf usually).
// 📌 In most contexts, "maximum depth of the tree" = height of the tree (if root is at depth 0 and leaf is the deepest).

// TreeNode represents a node in the binary tree
type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}
