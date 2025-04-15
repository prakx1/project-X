/**
 * This file demonstrates SOLID principles in Java for technical interviews.
 * 
 * SOLID is an acronym for five design principles that help create maintainable
 * and scalable software:
 * 
 * S - Single Responsibility Principle
 * O - Open/Closed Principle
 * L - Liskov Substitution Principle
 * I - Interface Segregation Principle
 * D - Dependency Inversion Principle
 * 
 * Each principle is demonstrated with examples showing both violations and correct implementations.
 */
public class SOLIDPrinciples {

    public static void main(String[] args) {
        System.out.println("===== SOLID Principles Demonstration =====");
        
        System.out.println("\n1. Single Responsibility Principle");
        demonstrateSRP();
        
        System.out.println("\n2. Open/Closed Principle");
        demonstrateOCP();
        
        System.out.println("\n3. Liskov Substitution Principle");
        demonstrateLSP();
        
        System.out.println("\n4. Interface Segregation Principle");
        demonstrateISP();
        
        System.out.println("\n5. Dependency Inversion Principle");
        demonstrateDIP();
    }
    
    /**
     * Single Responsibility Principle (SRP)
     * 
     * "A class should have only one reason to change."
     * This means a class should have only one responsibility or job.
     */
    private static void demonstrateSRP() {
        System.out.println("Before SRP:");
        
        // Before: UserService handles multiple responsibilities
        UserServiceViolatesSRP badService = new UserServiceViolatesSRP();
        badService.registerUser("john@example.com", "password123");
        
        System.out.println("\nAfter SRP:");
        
        // After: Responsibilities are separated
        UserService userService = new UserService(new EmailService(), new UserRepository());
        userService.registerUser("john@example.com", "password123");
    }
    
    /**
     * Open/Closed Principle (OCP)
     * 
     * "Software entities should be open for extension, but closed for modification."
     * This means you should be able to extend a class's behavior without modifying it.
     */
    private static void demonstrateOCP() {
        System.out.println("Before OCP:");
        
        // Before: Need to modify AreaCalculator when adding new shapes
        AreaCalculatorViolatesOCP badCalculator = new AreaCalculatorViolatesOCP();
        CircleViolatesOCP circle = new CircleViolatesOCP(5);
        RectangleViolatesOCP rectangle = new RectangleViolatesOCP(4, 6);
        
        badCalculator.calculateArea(circle);
        badCalculator.calculateArea(rectangle);
        
        System.out.println("\nAfter OCP:");
        
        // After: AreaCalculator works with any Shape without modification
        AreaCalculator calculator = new AreaCalculator();
        Shape circle2 = new Circle(5);
        Shape rectangle2 = new Rectangle(4, 6);
        Shape triangle = new Triangle(3, 4, 5);
        
        calculator.calculateArea(circle2);
        calculator.calculateArea(rectangle2);
        calculator.calculateArea(triangle);
    }
    
    /**
     * Liskov Substitution Principle (LSP)
     * 
     * "Objects of a superclass should be replaceable with objects of subclasses
     * without affecting the correctness of the program."
     */
    private static void demonstrateLSP() {
        System.out.println("Before LSP:");
        
        // Before: Square violates LSP because it changes Rectangle's behavior
        RectangleViolatesLSP rectangle = new RectangleViolatesLSP();
        rectangle.setWidth(5);
        rectangle.setHeight(10);
        System.out.println("Rectangle area: " + rectangle.getArea());
        
        RectangleViolatesLSP square = new SquareViolatesLSP();
        square.setWidth(5); // This also sets height to 5
        square.setHeight(10); // This overrides width to 10 as well
        System.out.println("Square area: " + square.getArea()); // 100, not 50
        
        // This would fail
        System.out.println("Testing if rectangle has area 50: " + testRectangle(rectangle)); // true
        System.out.println("Testing if square has area 50: " + testRectangle(square)); // false, violates LSP
        
        System.out.println("\nAfter LSP:");
        
        // After: Using proper abstractions
        Shape2D rectangle2 = new ProperRectangle(5, 10);
        Shape2D square2 = new ProperSquare(5);
        
        System.out.println("Rectangle area: " + rectangle2.getArea());
        System.out.println("Square area: " + square2.getArea());
    }
    
    private static boolean testRectangle(RectangleViolatesLSP r) {
        r.setWidth(5);
        r.setHeight(10);
        return r.getArea() == 50; // Should be true for proper rectangles
    }
    
    /**
     * Interface Segregation Principle (ISP)
     * 
     * "Clients should not be forced to depend on interfaces they do not use."
     * This means create focused interfaces rather than general-purpose ones.
     */
    private static void demonstrateISP() {
        System.out.println("Before ISP:");
        
        // Before: Worker interface has methods that not all workers can implement
        OfficeWorkerViolatesISP officeWorker = new OfficeWorkerViolatesISP();
        RobotWorkerViolatesISP robot = new RobotWorkerViolatesISP();
        
        officeWorker.work();
        officeWorker.eat();
        officeWorker.sleep();
        
        robot.work();
        robot.eat(); // This doesn't make sense for a robot
        robot.sleep(); // This doesn't make sense for a robot
        
        System.out.println("\nAfter ISP:");
        
        // After: Interfaces are segregated by responsibility
        Workable officeEmployee = new OfficeEmployee();
        Workable robotWorker = new Robot();
        
        officeEmployee.work();
        ((Eatable)officeEmployee).eat();
        ((Sleepable)officeEmployee).sleep();
        
        robotWorker.work();
        // Robot doesn't implement Eatable or Sleepable
    }
    
    /**
     * Dependency Inversion Principle (DIP)
     * 
     * "High-level modules should not depend on low-level modules. Both should depend on abstractions."
     * "Abstractions should not depend on details. Details should depend on abstractions."
     */
    private static void demonstrateDIP() {
        System.out.println("Before DIP:");
        
        // Before: NotificationService depends directly on EmailSender implementation
        NotificationServiceViolatesDIP badService = new NotificationServiceViolatesDIP();
        badService.sendNotification("Hello World");
        
        System.out.println("\nAfter DIP:");
        
        // After: NotificationService depends on MessageSender abstraction
        NotificationService emailService = new NotificationService(new EmailSender());
        emailService.sendNotification("Hello via Email");
        
        NotificationService smsService = new NotificationService(new SMSSender());
        smsService.sendNotification("Hello via SMS");
        
        NotificationService pushService = new NotificationService(new PushNotificationSender());
        pushService.sendNotification("Hello via Push Notification");
    }
}

/**
 * Single Responsibility Principle Examples
 */

// Violates SRP: Class has multiple responsibilities
class UserServiceViolatesSRP {
    public void registerUser(String email, String password) {
        // Responsibility 1: Validate user data
        if (!isValidEmail(email) || !isValidPassword(password)) {
            System.out.println("Invalid user data");
            return;
        }
        
        // Responsibility 2: Save user to database
        saveUser(email, password);
        
        // Responsibility 3: Send confirmation email
        sendConfirmationEmail(email);
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
    
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
    
    private void saveUser(String email, String password) {
        System.out.println("Saving user to database: " + email);
        // Database access code here
    }
    
    private void sendConfirmationEmail(String email) {
        System.out.println("Sending confirmation email to: " + email);
        // Email sending code here
    }
}

// Follows SRP: Each class has a single responsibility

// Responsibility 1: User registration and coordination
class UserService {
    private EmailService emailService;
    private UserRepository userRepository;
    
    public UserService(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }
    
    public void registerUser(String email, String password) {
        if (!isValidEmail(email) || !isValidPassword(password)) {
            System.out.println("Invalid user data");
            return;
        }
        
        userRepository.saveUser(email, password);
        emailService.sendConfirmationEmail(email);
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
    
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}

// Responsibility 2: Email related functionality
class EmailService {
    public void sendConfirmationEmail(String email) {
        System.out.println("Sending confirmation email to: " + email);
        // Email sending code here
    }
}

// Responsibility 3: Database access
class UserRepository {
    public void saveUser(String email, String password) {
        System.out.println("Saving user to database: " + email);
        // Database access code here
    }
}

/**
 * Open/Closed Principle Examples
 */

// Violates OCP: Need to modify class when adding new shapes

class CircleViolatesOCP {
    private double radius;
    
    public CircleViolatesOCP(double radius) {
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
}

class RectangleViolatesOCP {
    private double width;
    private double height;
    
    public RectangleViolatesOCP(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}

class AreaCalculatorViolatesOCP {
    public void calculateArea(Object shape) {
        if (shape instanceof CircleViolatesOCP) {
            CircleViolatesOCP circle = (CircleViolatesOCP) shape;
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            System.out.println("Circle area: " + area);
        } else if (shape instanceof RectangleViolatesOCP) {
            RectangleViolatesOCP rectangle = (RectangleViolatesOCP) shape;
            double area = rectangle.getWidth() * rectangle.getHeight();
            System.out.println("Rectangle area: " + area);
        }
        // Need to add more conditions for new shapes
    }
}

// Follows OCP: Can add new shapes without modifying AreaCalculator

interface Shape {
    double area();
    String getShapeName();
}

class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String getShapeName() {
        return "Circle";
    }
}

class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double area() {
        return width * height;
    }
    
    @Override
    public String getShapeName() {
        return "Rectangle";
    }
}

class Triangle implements Shape {
    private double a, b, c; // Sides
    
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public double area() {
        // Heron's formula
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
    
    @Override
    public String getShapeName() {
        return "Triangle";
    }
}

class AreaCalculator {
    public void calculateArea(Shape shape) {
        System.out.println(shape.getShapeName() + " area: " + shape.area());
    }
}

/**
 * Liskov Substitution Principle Examples
 */

// Violates LSP: Square is not a proper substitute for Rectangle

class RectangleViolatesLSP {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getArea() {
        return width * height;
    }
}

class SquareViolatesLSP extends RectangleViolatesLSP {
    // Square changes behavior of the base class
    @Override
    public void setWidth(int width) {
        super.width = width;
        super.height = width; // Square must have equal sides
    }
    
    @Override
    public void setHeight(int height) {
        super.height = height;
        super.width = height; // Square must have equal sides
    }
}

// Follows LSP: Using proper abstractions

interface Shape2D {
    double getArea();
}

class ProperRectangle implements Shape2D {
    private double width;
    private double height;
    
    public ProperRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
}

class ProperSquare implements Shape2D {
    private double side;
    
    public ProperSquare(double side) {
        this.side = side;
    }
    
    @Override
    public double getArea() {
        return side * side;
    }
}

/**
 * Interface Segregation Principle Examples
 */

// Violates ISP: Interface has methods that not all implementers need

interface WorkerViolatesISP {
    void work();
    void eat();
    void sleep();
}

class OfficeWorkerViolatesISP implements WorkerViolatesISP {
    @Override
    public void work() {
        System.out.println("Office worker is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Office worker is eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Office worker is sleeping");
    }
}

class RobotWorkerViolatesISP implements WorkerViolatesISP {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Robot cannot eat"); // Doesn't make sense
    }
    
    @Override
    public void sleep() {
        System.out.println("Robot cannot sleep"); // Doesn't make sense
    }
}

// Follows ISP: Interfaces are segregated by responsibility

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

class OfficeEmployee implements Workable, Eatable, Sleepable {
    @Override
    public void work() {
        System.out.println("Office employee is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Office employee is eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Office employee is sleeping");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
    // No need to implement eat() or sleep()
}

/**
 * Dependency Inversion Principle Examples
 */

// Violates DIP: High-level module depends on low-level module

class EmailSenderViolatesDIP {
    public void sendEmail(String message) {
        System.out.println("Sending email: " + message);
    }
}

class NotificationServiceViolatesDIP {
    private EmailSenderViolatesDIP emailSender;
    
    public NotificationServiceViolatesDIP() {
        this.emailSender = new EmailSenderViolatesDIP();
    }
    
    public void sendNotification(String message) {
        emailSender.sendEmail(message);
    }
}

// Follows DIP: Both high and low level modules depend on abstraction

interface MessageSender {
    void sendMessage(String message);
}

class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending email: " + message);
    }
}

class SMSSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class PushNotificationSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending push notification: " + message);
    }
}

class NotificationService {
    private MessageSender messageSender;
    
    // Dependency is injected through constructor
    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
    public void sendNotification(String message) {
        messageSender.sendMessage(message);
    }
}
