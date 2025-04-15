/**
 * This file demonstrates polymorphism concepts in Java for technical interviews.
 * 
 * Polymorphism means "many forms" and occurs when we have many classes related 
 * by inheritance. Java supports two types of polymorphism:
 * 
 * 1. Compile-time Polymorphism (Method Overloading)
 * 2. Runtime Polymorphism (Method Overriding)
 * 
 * Key concepts covered:
 * - Method overloading (same method name, different parameters)
 * - Method overriding (same method signature in parent and child classes)
 * - Dynamic method dispatch (runtime binding)
 * - Upcasting and downcasting
 * - The instanceof operator
 */
public class PolymorphismExamples {

    public static void main(String[] args) {
        // Method Overloading (Compile-time Polymorphism)
        System.out.println("===== Method Overloading =====");
        Calculator calculator = new Calculator();
        System.out.println("Sum of two integers: " + calculator.add(10, 20));
        System.out.println("Sum of three integers: " + calculator.add(10, 20, 30));
        System.out.println("Sum of two doubles: " + calculator.add(10.5, 20.5));
        
        // Method Overriding (Runtime Polymorphism)
        System.out.println("\n===== Method Overriding =====");
        Shape shape = new Shape();
        Shape circle = new Circle();
        Shape rectangle = new Rectangle();
        
        // Method call is resolved at runtime based on the actual object type
        shape.draw(); // Shape's draw method
        circle.draw(); // Circle's draw method
        rectangle.draw(); // Rectangle's draw method
        
        // Dynamic Method Dispatch
        System.out.println("\n===== Dynamic Method Dispatch =====");
        displayInfo(shape);
        displayInfo(circle);
        displayInfo(rectangle);
        
        // Upcasting and Downcasting
        System.out.println("\n===== Upcasting and Downcasting =====");
        
        // Upcasting (implicit) - treating a subclass as superclass
        Shape shapeRef = new Circle(); // Circle object is upcast to Shape reference
        shapeRef.draw(); // Calls Circle's draw method
        
        // Attempting to access subclass-specific method with superclass reference
        // shapeRef.getRadius(); // Compilation error - Shape doesn't have getRadius method
        
        // Downcasting (explicit) - treating a superclass as subclass
        if (shapeRef instanceof Circle) {
            Circle circleRef = (Circle) shapeRef; // Explicit downcasting
            System.out.println("Circle radius: " + circleRef.getRadius());
        }
        
        // Incorrect downcasting leads to ClassCastException at runtime
        try {
            Shape rect = new Rectangle();
            Circle wrongCast = (Circle) rect; // Will throw ClassCastException
        } catch (ClassCastException e) {
            System.out.println("Cannot cast Rectangle to Circle: " + e.getMessage());
        }
        
        // Polymorphic collections
        System.out.println("\n===== Polymorphic Collections =====");
        Shape[] shapes = new Shape[3];
        shapes[0] = new Shape();
        shapes[1] = new Circle();
        shapes[2] = new Rectangle();
        
        for (Shape s : shapes) {
            s.draw(); // Polymorphic invocation
            
            // Using instanceof to safely work with specific types
            if (s instanceof Circle) {
                Circle c = (Circle) s;
                System.out.println("  This is a circle with radius: " + c.getRadius());
            } else if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;
                System.out.println("  This is a rectangle with area: " + r.getArea());
            }
        }
        
        // Pattern matching for instanceof (Java 16+)
        System.out.println("\n===== Pattern Matching for instanceof (Java 16+) =====");
        for (Shape s : shapes) {
            // Uncomment if using Java 16 or higher
            /*
            if (s instanceof Circle c) {
                System.out.println("Circle with radius: " + c.getRadius());
            } else if (s instanceof Rectangle r) {
                System.out.println("Rectangle with area: " + r.getArea());
            } else {
                System.out.println("Generic shape");
            }
            */
            
            // For Java versions below 16, use traditional instanceof
            if (s instanceof Circle) {
                Circle c = (Circle) s;
                System.out.println("Circle with radius: " + c.getRadius());
            } else if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;
                System.out.println("Rectangle with area: " + r.getArea());
            } else {
                System.out.println("Generic shape");
            }
        }
    }
    
    // Method that demonstrates polymorphic behavior
    public static void displayInfo(Shape shape) {
        System.out.println("Working with shape: " + shape.getClass().getSimpleName());
        shape.draw(); // Polymorphic call
    }
}

// Method Overloading Example
class Calculator {
    // Method with two int parameters
    public int add(int a, int b) {
        return a + b;
    }
    
    // Method with three int parameters - overloaded based on number of parameters
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    // Method with two double parameters - overloaded based on parameter types
    public double add(double a, double b) {
        return a + b;
    }
    
    // Method with different parameter order - overloaded based on parameter order
    public double add(int a, double b) {
        return a + b;
    }
    
    public double add(double a, int b) {
        return a + b;
    }
    
    // NOTE: Cannot overload by return type alone
    // public double add(int a, int b) { // This would cause a compilation error
    //     return (double)(a + b);
    // }
}

// Method Overriding Example
class Shape {
    public void draw() {
        System.out.println("Drawing a shape");
    }
    
    public double getArea() {
        return 0.0;
    }
}

class Circle extends Shape {
    private double radius = 5.0;
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    // Specific method for Circle
    public double getRadius() {
        return radius;
    }
}

class Rectangle extends Shape {
    private double width = 4.0;
    private double height = 6.0;
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    // Specific methods for Rectangle
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}
