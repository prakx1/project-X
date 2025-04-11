package trees

// We use recursion with backtracking:
// At each node, we append it to the path.
// If it’s not the target, we recursively call left and right child.
// If the target is not found down that path, we pop (backtrack) that node from the path.
