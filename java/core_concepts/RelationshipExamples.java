/**
 * This file demonstrates various class relationships in Java for technical interviews.
 * 
 * Object-oriented programming relies on well-defined relationships between classes.
 * This file covers:
 * 
 * 1. Association - "uses-a" relationship
 * 2. Aggregation - "has-a" relationship (weak ownership)
 * 3. Composition - "contains-a" relationship (strong ownership)
 * 4. Dependency - "depends-on" relationship
 * 
 * Understanding these relationships is crucial for good object-oriented design.
 */
public class RelationshipExamples {

    public static void main(String[] args) {
        System.out.println("===== Association Example =====");
        demonstrateAssociation();
        
        System.out.println("\n===== Aggregation Example =====");
        demonstrateAggregation();
        
        System.out.println("\n===== Composition Example =====");
        demonstrateComposition();
        
        System.out.println("\n===== Dependency Example =====");
        demonstrateDependency();
    }
    
    /**
     * Association: a relationship where objects have their own lifecycle and
     * no ownership between the associated objects.
     * 
     * In this example, Teacher and Student have an association - they exist
     * independently, but can be related to each other.
     */
    private static void demonstrateAssociation() {
        // Create teacher and students independently
        Teacher teacher = new Teacher("Mrs. Smith");
        Student student1 = new Student("John");
        Student student2 = new Student("Alice");
        
        // Associate students with teacher (bi-directional association)
        teacher.addStudent(student1);
        teacher.addStudent(student2);
        student1.setTeacher(teacher);
        student2.setTeacher(teacher);
        
        // Display associations
        teacher.displayStudents();
        student1.displayTeacher();
        student2.displayTeacher();
        
        // The teacher and students can exist independently
        System.out.println("\nRemoving association...");
        teacher.removeStudent(student1);
        student1.setTeacher(null);
        
        teacher.displayStudents();
        student1.displayTeacher();
    }
    
    /**
     * Aggregation: a specialized form of association where objects have their
     * own lifecycle, but there is ownership. This is a "has-a" relationship.
     * 
     * In this example, a Department has Employees, but Employees can exist
     * without the Department.
     */
    private static void demonstrateAggregation() {
        // Create employees independently
        Employee emp1 = new Employee(101, "Bob Johnson", "Developer");
        Employee emp2 = new Employee(102, "Jane Smith", "Designer");
        Employee emp3 = new Employee(103, "Mike Wilson", "Manager");
        
        // Create department and add employees (aggregation)
        Department engineeringDept = new Department("Engineering");
        engineeringDept.addEmployee(emp1);
        engineeringDept.addEmployee(emp2);
        
        Department marketingDept = new Department("Marketing");
        marketingDept.addEmployee(emp3);
        
        // Display departments and their employees
        engineeringDept.displayEmployees();
        marketingDept.displayEmployees();
        
        // Employee can move to a different department
        System.out.println("\nMoving employee to different department...");
        engineeringDept.removeEmployee(emp2);
        marketingDept.addEmployee(emp2);
        
        engineeringDept.displayEmployees();
        marketingDept.displayEmployees();
        
        // If department is disbanded, employees still exist
        System.out.println("\nDisbanding Marketing department...");
        marketingDept = null; // Department reference is gone
        System.out.println("But employees still exist:");
        System.out.println(emp2); // Employee still exists
        System.out.println(emp3); // Employee still exists
    }
    
    /**
     * Composition: a stronger form of aggregation where the contained objects
     * cannot exist without the container. This is a "contains-a" relationship.
     * 
     * In this example, a Car contains an Engine, and the Engine cannot exist
     * independently of the Car.
     */
    private static void demonstrateComposition() {
        // Create a car which automatically creates its engine (composition)
        Car car = new Car("Toyota", "Camry");
        car.start();
        car.accelerate();
        car.stop();
        
        // Cannot access or create engine independently
        // Engine engine = new Engine(); // Not accessible outside Car
        
        System.out.println("\nWhen car is destroyed, its engine is also destroyed");
        car = null; // Car reference is removed, engine is also garbage collected
    }
    
    /**
     * Dependency: a relationship where one class uses another class, typically
     * as a method parameter, local variable, or return type.
     * 
     * In this example, OrderProcessor depends on the Payment class for processing
     * orders, but doesn't contain or inherit from it.
     */
    private static void demonstrateDependency() {
        OrderProcessor processor = new OrderProcessor();
        
        // Order processor depends on Payment objects
        CreditCardPayment creditCard = new CreditCardPayment("1234-5678-9012-3456", "John Doe");
        processor.processOrder("Order-123", 99.99, creditCard);
        
        PayPalPayment paypal = new PayPalPayment("john.doe@example.com");
        processor.processOrder("Order-456", 149.50, paypal);
    }
}

/**
 * Classes for Association example
 */
class Teacher {
    private String name;
    private java.util.List<Student> students;
    
    public Teacher(String name) {
        this.name = name;
        this.students = new java.util.ArrayList<>();
    }
    
    public void addStudent(Student student) {
        this.students.add(student);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
    }
    
    public void displayStudents() {
        System.out.println("Teacher " + name + " has students:");
        if (students.isEmpty()) {
            System.out.println("  No students");
        } else {
            for (Student student : students) {
                System.out.println("  - " + student.getName());
            }
        }
    }
    
    public String getName() {
        return name;
    }
}

class Student {
    private String name;
    private Teacher teacher; // Association with Teacher
    
    public Student(String name) {
        this.name = name;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public void displayTeacher() {
        if (teacher != null) {
            System.out.println("Student " + name + " has teacher: " + teacher.getName());
        } else {
            System.out.println("Student " + name + " has no teacher assigned");
        }
    }
    
    public String getName() {
        return name;
    }
}

/**
 * Classes for Aggregation example
 */
class Department {
    private String name;
    private java.util.List<Employee> employees;
    
    public Department(String name) {
        this.name = name;
        this.employees = new java.util.ArrayList<>();
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    public void displayEmployees() {
        System.out.println("Department: " + name);
        System.out.println("Employees:");
        if (employees.isEmpty()) {
            System.out.println("  No employees");
        } else {
            for (Employee emp : employees) {
                System.out.println("  - " + emp);
            }
        }
    }
}

class Employee {
    private int id;
    private String name;
    private String position;
    
    public Employee(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
    
    @Override
    public String toString() {
        return "Employee [ID=" + id + ", Name=" + name + ", Position=" + position + "]";
    }
}

/**
 * Classes for Composition example
 */
class Car {
    private String make;
    private String model;
    private Engine engine; // Composition: Engine is part of Car
    
    public Car(String make, String model) {
        this.make = make;
        this.model = model;
        // Engine is created as part of Car instantiation
        this.engine = new Engine(model.equals("Camry") ? 2.5 : 3.0);
    }
    
    public void start() {
        System.out.println(make + " " + model + " is starting...");
        engine.start();
    }
    
    public void accelerate() {
        System.out.println(make + " " + model + " is accelerating...");
        engine.increaseRPM(2000);
    }
    
    public void stop() {
        System.out.println(make + " " + model + " is stopping...");
        engine.stop();
    }
    
    // Inner class - represents composition
    // Engine cannot exist without a Car
    private class Engine {
        private double liters;
        private boolean running;
        private int rpm;
        
        public Engine(double liters) {
            this.liters = liters;
            this.running = false;
            this.rpm = 0;
        }
        
        public void start() {
            running = true;
            rpm = 800; // idle speed
            System.out.println("  Engine started: " + liters + "L engine idling at " + rpm + " RPM");
        }
        
        public void increaseRPM(int amount) {
            if (running) {
                rpm += amount;
                System.out.println("  Engine RPM increased to " + rpm);
            }
        }
        
        public void stop() {
            if (running) {
                rpm = 0;
                running = false;
                System.out.println("  Engine stopped");
            }
        }
    }
}

/**
 * Classes for Dependency example
 */
class OrderProcessor {
    public void processOrder(String orderId, double amount, Payment paymentMethod) {
        System.out.println("Processing order: " + orderId + " for $" + amount);
        boolean success = paymentMethod.processPayment(amount);
        
        if (success) {
            System.out.println("Order " + orderId + " payment successful");
            generateInvoice(orderId, amount);
        } else {
            System.out.println("Order " + orderId + " payment failed");
        }
    }
    
    private void generateInvoice(String orderId, double amount) {
        System.out.println("Invoice generated for order: " + orderId);
    }
}

interface Payment {
    boolean processPayment(double amount);
}

class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cardholderName;
    
    public CreditCardPayment(String cardNumber, String cardholderName) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
    }
    
    @Override
    public boolean processPayment(double amount) {
        // In a real system, this would connect to a payment gateway
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card: " + maskCardNumber(cardNumber) + ", Cardholder: " + cardholderName);
        return true; // Simulating successful payment
    }
    
    private String maskCardNumber(String number) {
        return "XXXX-XXXX-XXXX-" + number.substring(number.length() - 4);
    }
}

class PayPalPayment implements Payment {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public boolean processPayment(double amount) {
        // In a real system, this would connect to PayPal API
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("PayPal account: " + email);
        return true; // Simulating successful payment
    }
}
