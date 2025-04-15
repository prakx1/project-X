/**
 * Implementation of common design patterns in Java.
 * 
 * Design patterns are typical solutions to common problems in software design.
 * They are like pre-made blueprints that you can customize to solve recurring
 * design problems in your code.
 * 
 * This file demonstrates the implementation of several key design patterns
 * that are frequently asked about in technical interviews.
 */
public class DesignPatterns {

    public static void main(String[] args) {
        // Test Singleton pattern
        System.out.println("===== Singleton Pattern =====");
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println("Same instance? " + (singleton1 == singleton2));
        
        // Test Factory pattern
        System.out.println("\n===== Factory Pattern =====");
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.getShape("CIRCLE");
        circle.draw();
        Shape rectangle = shapeFactory.getShape("RECTANGLE");
        rectangle.draw();
        
        // Test Builder pattern
        System.out.println("\n===== Builder Pattern =====");
        Computer computer = new Computer.Builder()
                .setCPU("Intel i7")
                .setRAM("16GB")
                .setStorage("512GB SSD")
                .setGraphicsCard("NVIDIA RTX 3080")
                .build();
        System.out.println(computer);
        
        // Test Prototype pattern
        System.out.println("\n===== Prototype Pattern =====");
        PrototypeRegistry registry = new PrototypeRegistry();
        try {
            Shape circlePrototype = registry.getShape("circle");
            circlePrototype.draw();
            Shape rectanglePrototype = registry.getShape("rectangle");
            rectanglePrototype.draw();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        // Test Adapter pattern
        System.out.println("\n===== Adapter Pattern =====");
        MediaPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("mp3", "song.mp3");
        
        // Using adapter for different formats
        audioPlayer.play("mp4", "movie.mp4");
        audioPlayer.play("vlc", "video.vlc");
        
        // Test Decorator pattern
        System.out.println("\n===== Decorator Pattern =====");
        Beverage espresso = new Espresso();
        System.out.println(espresso.getDescription() + " $" + espresso.cost());
        
        Beverage espressoWithMilk = new MilkDecorator(espresso);
        System.out.println(espressoWithMilk.getDescription() + " $" + espressoWithMilk.cost());
        
        Beverage espressoWithMilkAndChocolate = new ChocolateDecorator(espressoWithMilk);
        System.out.println(espressoWithMilkAndChocolate.getDescription() + " $" + espressoWithMilkAndChocolate.cost());
        
        // Test Observer pattern
        System.out.println("\n===== Observer Pattern =====");
        WeatherStation weatherStation = new WeatherStation();
        WeatherDisplay phoneDisplay = new PhoneDisplay(weatherStation);
        WeatherDisplay tvDisplay = new TVDisplay(weatherStation);
        
        weatherStation.setMeasurements(25.5f, 65.0f, 1013.1f);
        weatherStation.setMeasurements(26.8f, 70.0f, 1010.2f);
        
        // Test Strategy pattern
        System.out.println("\n===== Strategy Pattern =====");
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new Item("Book", 50.0));
        cart.addItem(new Item("Phone", 500.0));
        
        cart.setPaymentStrategy(new CreditCardPayment("John Doe", "1234-5678-9012-3456", "123", "12/25"));
        cart.checkout();
        
        cart.setPaymentStrategy(new PayPalPayment("john.doe@example.com", "password123"));
        cart.checkout();
        
        // Test Command pattern
        System.out.println("\n===== Command Pattern =====");
        RemoteControl remote = new RemoteControl();
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        
        remote.setCommand(0, new LightOnCommand(livingRoomLight), new LightOffCommand(livingRoomLight));
        remote.setCommand(1, new LightOnCommand(kitchenLight), new LightOffCommand(kitchenLight));
        
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);
        remote.onButtonWasPushed(1);
        remote.offButtonWasPushed(1);
        remote.undoButtonWasPushed();
        
        // Test Chain of Responsibility pattern
        System.out.println("\n===== Chain of Responsibility Pattern =====");
        Logger consoleLogger = new ConsoleLogger(Logger.INFO);
        Logger fileLogger = new FileLogger(Logger.DEBUG);
        Logger errorLogger = new ErrorLogger(Logger.ERROR);
        
        consoleLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(errorLogger);
        
        consoleLogger.logMessage(Logger.INFO, "This is an information message");
        consoleLogger.logMessage(Logger.DEBUG, "This is a debug message");
        consoleLogger.logMessage(Logger.ERROR, "This is an error message");
    }
}

// ==================== Singleton Pattern ====================
/**
 * Singleton Pattern ensures a class has only one instance and provides a global point of access to it.
 * This is useful when exactly one object is needed to coordinate actions across the system.
 */
class Singleton {
    // Private static instance - eager initialization
    private static final Singleton instance = new Singleton();
    
    // Private constructor to prevent instantiation
    private Singleton() {
        System.out.println("Singleton instance created");
    }
    
    // Public method to provide global access point
    public static Singleton getInstance() {
        return instance;
    }
    
    // Thread-safe lazy initialization alternative (commented out)
    /*
    private static volatile Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    */
}

// ==================== Factory Pattern ====================
/**
 * Factory Pattern provides an interface for creating objects in a superclass,
 * but allows subclasses to alter the type of objects that will be created.
 */
interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

class ShapeFactory {
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        
        return null;
    }
}

// ==================== Builder Pattern ====================
/**
 * Builder Pattern separates the construction of a complex object from its representation,
 * allowing the same construction process to create different representations.
 */
class Computer {
    // Required parameters
    private final String CPU;
    private final String RAM;
    
    // Optional parameters
    private final String storage;
    private final String graphicsCard;
    private final boolean hasWifi;
    private final boolean hasBluetooth;
    
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
    }
    
    @Override
    public String toString() {
        return "Computer [CPU=" + CPU + ", RAM=" + RAM + ", storage=" + storage +
               ", graphicsCard=" + graphicsCard + ", hasWifi=" + hasWifi +
               ", hasBluetooth=" + hasBluetooth + "]";
    }
    
    public static class Builder {
        // Required parameters
        private String CPU;
        private String RAM;
        
        // Optional parameters with default values
        private String storage = "256GB SSD";
        private String graphicsCard = "Integrated";
        private boolean hasWifi = true;
        private boolean hasBluetooth = true;
        
        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }
        
        public Builder setRAM(String RAM) {
            this.RAM = RAM;
            return this;
        }
        
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public Builder setWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }
        
        public Builder setBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }
        
        public Computer build() {
            if (CPU == null || RAM == null) {
                throw new IllegalStateException("CPU and RAM are required parameters");
            }
            return new Computer(this);
        }
    }
}

// ==================== Prototype Pattern ====================
/**
 * Prototype Pattern creates new objects by copying an existing object, known as the prototype.
 */
abstract class ShapePrototype implements Cloneable {
    private String id;
    protected String type;
    
    abstract void draw();
    
    public String getType() {
        return type;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class CirclePrototype extends ShapePrototype {
    public CirclePrototype() {
        type = "Circle";
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a Circle (Prototype)");
    }
}

class RectanglePrototype extends ShapePrototype {
    public RectanglePrototype() {
        type = "Rectangle";
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a Rectangle (Prototype)");
    }
}

class PrototypeRegistry {
    private final java.util.Map<String, ShapePrototype> shapeMap = new java.util.HashMap<>();
    
    public PrototypeRegistry() {
        shapeMap.put("circle", new CirclePrototype());
        shapeMap.put("rectangle", new RectanglePrototype());
    }
    
    public ShapePrototype getShape(String shapeType) throws CloneNotSupportedException {
        ShapePrototype cachedShape = shapeMap.get(shapeType);
        return (ShapePrototype) cachedShape.clone();
    }
}

// ==================== Adapter Pattern ====================
/**
 * Adapter Pattern allows incompatible interfaces to work together.
 * It acts as a bridge between two incompatible interfaces.
 */
interface MediaPlayer {
    void play(String audioType, String fileName);
}

interface AdvancedMediaPlayer {
    void playVlc(String fileName);
    void playMp4(String fileName);
}

class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
    
    @Override
    public void playMp4(String fileName) {
        // Do nothing
    }
}

class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        // Do nothing
    }
    
    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedMediaPlayer;
    
    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer = new Mp4Player();
        }
    }
    
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}

class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media type: " + audioType);
        }
    }
}

// ==================== Decorator Pattern ====================
/**
 * Decorator Pattern attaches additional responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to subclassing for extending functionality.
 */
abstract class Beverage {
    protected String description = "Unknown Beverage";
    
    public String getDescription() {
        return description;
    }
    
    public abstract double cost();
}

class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }
    
    @Override
    public double cost() {
        return 1.99;
    }
}

abstract class BeverageDecorator extends Beverage {
    protected Beverage beverage;
    
    public abstract String getDescription();
}

class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}

class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Chocolate";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.75;
    }
}

// ==================== Observer Pattern ====================
/**
 * Observer Pattern defines a one-to-many dependency between objects so that when one
 * object changes state, all its dependents are notified and updated automatically.
 */
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

interface Observer {
    void update(float temperature, float humidity, float pressure);
}

interface WeatherDisplay {
    void display();
}

class WeatherStation implements Subject {
    private final java.util.List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherStation() {
        this.observers = new java.util.ArrayList<>();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
    private void measurementsChanged() {
        notifyObservers();
    }
}

class PhoneDisplay implements Observer, WeatherDisplay {
    private float temperature;
    private float humidity;
    private float pressure;
    private final Subject weatherStation;
    
    public PhoneDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Phone Display: " + temperature + "°C, " + humidity + "% humidity, " + pressure + " hPa");
    }
}

class TVDisplay implements Observer, WeatherDisplay {
    private float temperature;
    private float humidity;
    private float pressure;
    private final Subject weatherStation;
    
    public TVDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
    
    @Override
    public void display() {
        System.out.println("TV Display: " + temperature + "°C, " + humidity + "% humidity, " + pressure + " hPa");
    }
}

// ==================== Strategy Pattern ====================
/**
 * Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them
 * interchangeable. Strategy lets the algorithm vary independently from clients that use it.
 */
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private final String name;
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;
    
    public CreditCardPayment(String name, String cardNumber, String cvv, String expiryDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with credit card ending in " +
                           cardNumber.substring(cardNumber.length() - 4));
    }
}

class PayPalPayment implements PaymentStrategy {
    private final String email;
    private final String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal account: " + email);
    }
}

class Item {
    private final String name;
    private final double price;
    
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
}

class ShoppingCart {
    private final java.util.List<Item> items;
    private PaymentStrategy paymentStrategy;
    
    public ShoppingCart() {
        this.items = new java.util.ArrayList<>();
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public double calculateTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public void checkout() {
        double total = calculateTotal();
        System.out.println("Cart total: $" + total);
        paymentStrategy.pay(total);
    }
}

// ==================== Command Pattern ====================
/**
 * Command Pattern encapsulates a request as an object, thereby letting you parameterize clients
 * with different requests, queue or log requests, and support undoable operations.
 */
interface Command {
    void execute();
    void undo();
}

class Light {
    private final String location;
    
    public Light(String location) {
        this.location = location;
    }
    
    public void on() {
        System.out.println(location + " light is ON");
    }
    
    public void off() {
        System.out.println(location + " light is OFF");
    }
}

class LightOnCommand implements Command {
    private final Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    private final Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
}

class RemoteControl {
    private final Command[] onCommands;
    private final Command[] offCommands;
    private Command undoCommand;
    
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    
    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }
    
    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    public void undoButtonWasPushed() {
        System.out.println("Undo operation:");
        undoCommand.undo();
    }
}

class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }
    
    @Override
    public void undo() {
        // Do nothing
    }
}

// ==================== Chain of Responsibility Pattern ====================
/**
 * Chain of Responsibility Pattern passes a request along a chain of handlers. Upon receiving
 * a request, each handler decides either to process the request or to pass it to the next handler in the chain.
 */
abstract class Logger {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;
    
    protected int level;
    protected Logger nextLogger;
    
    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }
    
    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }
    
    protected abstract void write(String message);
}

class ConsoleLogger extends Logger {
    public ConsoleLogger(int level) {
        this.level = level;
    }
    
    @Override
    protected void write(String message) {
        System.out.println("Console Logger: " + message);
    }
}

class FileLogger extends Logger {
    public FileLogger(int level) {
        this.level = level;
    }
    
    @Override
    protected void write(String message) {
        System.out.println("File Logger: " + message);
    }
}

class ErrorLogger extends Logger {
    public ErrorLogger(int level) {
        this.level = level;
    }
    
    @Override
    protected void write(String message) {
        System.out.println("Error Logger: " + message);
    }
}
