package edge_case

import "fmt"

type Reader interface {
	Read()
}

type Writer interface {
	Write()
}

type ReadWriter interface {
	Reader
	Writer
}

type File struct{}

func (File) Read()  { fmt.Println("Reading") }
func (File) Write() { fmt.Println("Writing") }

func embeddedInterface() {
	var rw ReadWriter = File{}
	rw.Read()
	rw.Write()
}
