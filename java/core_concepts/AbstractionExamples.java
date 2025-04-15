/**
 * This file demonstrates abstraction concepts in Java for technical interviews.
 * 
 * Abstraction is the process of hiding implementation details and showing only functionality.
 * In Java, abstraction can be achieved using:
 * 1. Abstract classes
 * 2. Interfaces
 * 
 * Key concepts covered:
 * - Abstract classes and methods
 * - Interface implementation
 * - Differences between abstract classes and interfaces
 * - Multiple levels of abstraction
 * - Real-world abstraction scenarios
 */
public class AbstractionExamples {

    public static void main(String[] args) {
        // Cannot instantiate abstract classes
        // Vehicle vehicle = new Vehicle(); // Compilation error
        
        // Abstract class example
        System.out.println("===== Abstract Class Examples =====");
        
        Vehicle car = new Car("Toyota", "Camry", 4);
        car.start();
        car.stop();
        car.displayInfo();
        
        Vehicle motorcycle = new Motorcycle("Honda", "CBR", 2);
        motorcycle.start();
        motorcycle.stop();
        motorcycle.displayInfo();
        
        // Interface example
        System.out.println("\n===== Interface Examples =====");
        
        DatabaseConnection mySqlConnection = new MySQLConnection();
        mySqlConnection.connect();
        mySqlConnection.executeQuery("SELECT * FROM users");
        mySqlConnection.disconnect();
        
        DatabaseConnection mongoDbConnection = new MongoDBConnection();
        mongoDbConnection.connect();
        mongoDbConnection.executeQuery("db.users.find()");
        mongoDbConnection.disconnect();
        
        // Multiple interfaces
        System.out.println("\n===== Multiple Interface Implementation =====");
        
        SmartDevice smartphone = new Smartphone();
        smartphone.turnOn();
        smartphone.connectToWifi();
        smartphone.browseInternet();
        smartphone.takePicture();
        smartphone.turnOff();
        
        // Layered abstraction
        System.out.println("\n===== Layered Abstraction Example =====");
        
        PaymentProcessor creditCardProcessor = new CreditCardProcessor();
        creditCardProcessor.processPayment(100.0);
        
        PaymentProcessor paypalProcessor = new PayPalProcessor();
        paypalProcessor.processPayment(150.0);
        
        // Real-world abstraction
        System.out.println("\n===== Real-world Abstraction Example =====");
        
        NotificationSender emailSender = new EmailNotificationSender();
        emailSender.sendNotification("Meeting reminder", "Your meeting starts in 15 minutes");
        
        NotificationSender smsSender = new SMSNotificationSender();
        smsSender.sendNotification("Login alert", "New login to your account detected");
    }
}

/**
 * Abstract class example - Vehicle
 */
abstract class Vehicle {
    protected String make;
    protected String model;
    protected int wheels;
    
    public Vehicle(String make, String model, int wheels) {
        this.make = make;
        this.model = model;
        this.wheels = wheels;
    }
    
    // Abstract method - must be implemented by concrete subclasses
    public abstract void start();
    
    // Concrete method with implementation
    public void stop() {
        System.out.println("Vehicle stopped");
    }
    
    public void displayInfo() {
        System.out.println("Vehicle: " + make + " " + model + ", " + wheels + " wheels");
    }
}

class Car extends Vehicle {
    public Car(String make, String model, int wheels) {
        super(make, model, wheels);
    }
    
    @Override
    public void start() {
        System.out.println("Car started. Engine running.");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Car: " + make + " " + model + ", " + wheels + " wheels");
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String make, String model, int wheels) {
        super(make, model, wheels);
    }
    
    @Override
    public void start() {
        System.out.println("Motorcycle started. Engine revving.");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Motorcycle: " + make + " " + model + ", " + wheels + " wheels");
    }
}

/**
 * Interface example - DatabaseConnection
 */
interface DatabaseConnection {
    // All methods in an interface are implicitly public and abstract
    void connect();
    void disconnect();
    void executeQuery(String query);
}

class MySQLConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connected to MySQL database");
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnected from MySQL database");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("Executing MySQL query: " + query);
    }
}

class MongoDBConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connected to MongoDB database");
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnected from MongoDB database");
    }
    
    @Override
    public void executeQuery(String query) {
        System.out.println("Executing MongoDB query: " + query);
    }
}

/**
 * Multiple interface implementation example
 */
interface Electronic {
    void turnOn();
    void turnOff();
}

interface WifiEnabled {
    void connectToWifi();
    void browseInternet();
}

interface Camera {
    void takePicture();
}

// Class implementing multiple interfaces
class SmartDevice implements Electronic, WifiEnabled, Camera {
    @Override
    public void turnOn() {
        System.out.println("Smart device turned on");
    }
    
    @Override
    public void turnOff() {
        System.out.println("Smart device turned off");
    }
    
    @Override
    public void connectToWifi() {
        System.out.println("Connected to WiFi");
    }
    
    @Override
    public void browseInternet() {
        System.out.println("Browsing the internet");
    }
    
    @Override
    public void takePicture() {
        System.out.println("Taking a picture");
    }
}

class Smartphone extends SmartDevice {
    @Override
    public void turnOn() {
        System.out.println("Smartphone turned on");
    }
    
    @Override
    public void turnOff() {
        System.out.println("Smartphone turned off");
    }
}

/**
 * Layered abstraction example - Payment processing
 */
abstract class PaymentProcessor {
    // Template method pattern - defines the algorithm structure
    public final void processPayment(double amount) {
        if (validatePayment(amount)) {
            double fee = calculateFee(amount);
            double totalAmount = amount + fee;
            System.out.println("Processing payment of $" + amount + " with fee $" + fee);
            System.out.println("Total amount: $" + totalAmount);
            performPayment(totalAmount);
            sendReceipt(amount);
        } else {
            System.out.println("Payment validation failed for amount: $" + amount);
        }
    }
    
    // Common method with default implementation
    protected boolean validatePayment(double amount) {
        return amount > 0;
    }
    
    // Abstract methods to be implemented by subclasses
    protected abstract double calculateFee(double amount);
    protected abstract void performPayment(double totalAmount);
    
    // Hook method with default implementation
    protected void sendReceipt(double amount) {
        System.out.println("Receipt sent for payment amount: $" + amount);
    }
}

class CreditCardProcessor extends PaymentProcessor {
    @Override
    protected double calculateFee(double amount) {
        // Credit card processing fee: 2.5%
        return amount * 0.025;
    }
    
    @Override
    protected void performPayment(double totalAmount) {
        System.out.println("Processing credit card payment: $" + totalAmount);
    }
}

class PayPalProcessor extends PaymentProcessor {
    @Override
    protected double calculateFee(double amount) {
        // PayPal processing fee: 3.0% + $0.30
        return (amount * 0.03) + 0.30;
    }
    
    @Override
    protected void performPayment(double totalAmount) {
        System.out.println("Processing PayPal payment: $" + totalAmount);
    }
    
    @Override
    protected void sendReceipt(double amount) {
        System.out.println("Sending PayPal receipt via email for payment: $" + amount);
    }
}

/**
 * Real-world abstraction example - Notification system
 */
abstract class NotificationSender {
    // Template for sending notifications
    public final void sendNotification(String title, String message) {
        connectToService();
        validateMessage(title, message);
        String formattedMessage = formatMessage(title, message);
        deliver(formattedMessage);
        logNotification(title);
        closeConnection();
    }
    
    protected abstract void connectToService();
    protected abstract void deliver(String formattedMessage);
    protected abstract void closeConnection();
    
    // Common implementations
    protected void validateMessage(String title, String message) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Notification title cannot be empty");
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be empty");
        }
        System.out.println("Message validation successful");
    }
    
    protected String formatMessage(String title, String message) {
        return "[" + title + "] " + message;
    }
    
    protected void logNotification(String title) {
        System.out.println("Notification logged: " + title);
    }
}

class EmailNotificationSender extends NotificationSender {
    @Override
    protected void connectToService() {
        System.out.println("Connecting to email service");
    }
    
    @Override
    protected void deliver(String formattedMessage) {
        System.out.println("Sending email: " + formattedMessage);
    }
    
    @Override
    protected void closeConnection() {
        System.out.println("Email service connection closed");
    }
    
    @Override
    protected String formatMessage(String title, String message) {
        return "Subject: " + title + "\nBody: " + message;
    }
}

class SMSNotificationSender extends NotificationSender {
    @Override
    protected void connectToService() {
        System.out.println("Connecting to SMS gateway");
    }
    
    @Override
    protected void deliver(String formattedMessage) {
        System.out.println("Sending SMS: " + formattedMessage);
    }
    
    @Override
    protected void closeConnection() {
        System.out.println("SMS gateway connection closed");
    }
}
