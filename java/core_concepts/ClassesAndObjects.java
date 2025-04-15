/**
 * This file demonstrates the fundamental concepts of classes and objects in Java.
 * 
 * Classes are blueprints for objects, and objects are instances of classes.
 * This file shows how to:
 * 1. Define a class with fields and methods
 * 2. Create objects/instances
 * 3. Work with constructors
 * 4. Access modifiers and their impact
 * 5. Static vs instance members
 */
public class ClassesAndObjects {

    public static void main(String[] args) {
        // Creating objects using different constructors
        Student student1 = new Student(); // Using default constructor
        student1.displayInfo(); // Output will show default values
        
        Student student2 = new Student(101, "John Doe", 22);
        student2.displayInfo();
        
        // Accessing and modifying object states
        student2.setAge(23);
        System.out.println("Updated age: " + student2.getAge());
        
        // Demonstrating static variable
        System.out.println("School name for student1: " + student1.getSchoolName());
        System.out.println("School name for student2: " + student2.getSchoolName());
        
        // Change static variable - affects all instances
        Student.setSchoolName("Harvard University");
        System.out.println("After change - School name for student1: " + student1.getSchoolName());
        System.out.println("After change - School name for student2: " + student2.getSchoolName());
        
        // Demonstrating static method
        System.out.println("Total students: " + Student.getStudentCount());
        
        // More examples for technical interviews
        
        // Inner class example
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();
        
        // Static nested class
        OuterClass.StaticNestedClass staticNested = new OuterClass.StaticNestedClass();
        staticNested.display();
        
        // Anonymous class
        Greeting anonymousGreeting = new Greeting() {
            @Override
            public void greet() {
                System.out.println("Hello from anonymous class!");
            }
        };
        anonymousGreeting.greet();
    }
}

/**
 * Example class representing a Student
 */
class Student {
    // Instance variables (fields)
    private int id;
    private String name;
    private int age;
    
    // Static variable (shared across all instances)
    private static String schoolName = "MIT";
    
    // Static counter to keep track of student objects
    private static int studentCount = 0;
    
    // Default constructor
    public Student() {
        this.id = 0;
        this.name = "Unknown";
        this.age = 0;
        studentCount++;
    }
    
    // Parameterized constructor
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        studentCount++;
    }
    
    // Copy constructor
    public Student(Student other) {
        this.id = other.id;
        this.name = other.name;
        this.age = other.age;
        studentCount++;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getSchoolName() {
        return schoolName;
    }
    
    public static void setSchoolName(String newSchoolName) {
        schoolName = newSchoolName;
    }
    
    public static int getStudentCount() {
        return studentCount;
    }
    
    // Instance method
    public void displayInfo() {
        System.out.println("Student ID: " + id + ", Name: " + name + ", Age: " + age);
    }
    
    // Static method
    public static void displaySchool() {
        System.out.println("School Name: " + schoolName);
        // Cannot access instance variables directly
        // System.out.println("Student name: " + name); // This would cause a compilation error
    }
    
    // Method to simulate finalize method (deprecated but still asked in interviews)
    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("Object of Student with ID: " + id + " is being garbage collected");
            studentCount--;
        } finally {
            super.finalize();
        }
    }
}

/**
 * Examples of outer and inner classes
 */
class OuterClass {
    private int outerVar = 10;
    
    // Inner class (non-static nested class)
    class InnerClass {
        public void display() {
            // Inner class can access outer class variables
            System.out.println("InnerClass - Access to outerVar: " + outerVar);
        }
    }
    
    // Static nested class
    static class StaticNestedClass {
        public void display() {
            // Static nested class cannot access outer class instance variables
            // System.out.println(outerVar); // This would cause a compilation error
            System.out.println("StaticNestedClass - Cannot access outerVar directly");
        }
    }
}

/**
 * Interface for demonstrating anonymous classes
 */
interface Greeting {
    void greet();
}
