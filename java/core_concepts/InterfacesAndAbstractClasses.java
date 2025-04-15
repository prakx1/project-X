/**
 * This file demonstrates interfaces and abstract classes in Java for technical interviews.
 * 
 * It covers the key differences between interfaces and abstract classes,
 * when to use each, and the latest features in Java 8, 9, and beyond.
 * 
 * Key concepts covered:
 * - Abstract class fundamentals
 * - Interface fundamentals and evolution
 * - Default methods in interfaces (Java 8+)
 * - Static methods in interfaces (Java 8+)
 * - Private methods in interfaces (Java 9+)
 * - Multiple inheritance with interfaces
 * - Abstract classes vs interfaces comparison
 */
public class InterfacesAndAbstractClasses {

    public static void main(String[] args) {
        // Abstract class usage
        System.out.println("===== Abstract Class Usage =====");
        
        AbstractDataProcessor csvProcessor = new CSVDataProcessor();
        csvProcessor.processData();
        csvProcessor.displayMetrics();
        
        AbstractDataProcessor jsonProcessor = new JSONDataProcessor();
        jsonProcessor.processData();
        jsonProcessor.displayMetrics();
        
        // Basic interface implementation
        System.out.println("\n===== Basic Interface Implementation =====");
        
        Drawable circle = new DrawableCircle(5.0);
        circle.draw();
        System.out.println("Area: " + ((DrawableCircle) circle).calculateArea());
        
        Drawable rectangle = new DrawableRectangle(4.0, 6.0);
        rectangle.draw();
        
        // Interface with default methods (Java 8+)
        System.out.println("\n===== Interface with Default Methods =====");
        
        Logger consoleLogger = new ConsoleLogger();
        consoleLogger.log("This is an info message");
        consoleLogger.logError("This is an error message"); // Using default method
        
        Logger fileLogger = new FileLogger();
        fileLogger.log("This is an info message for file");
        fileLogger.logError("This is an error message for file"); // Overriding default method
        
        // Interface with static methods (Java 8+)
        System.out.println("\n===== Interface with Static Methods =====");
        System.out.println("Current version: " + Logger.getVersion());
        
        // Multiple interface implementation
        System.out.println("\n===== Multiple Interface Implementation =====");
        
        AudioPlayer player = new MediaPlayer();
        player.play("song.mp3");
        player.pause();
        player.stop();
        
        VideoPlayer videoCapability = (VideoPlayer) player;
        videoCapability.playVideo("movie.mp4");
        videoCapability.stopVideo();
        
        // Functional interface with lambda (Java 8+)
        System.out.println("\n===== Functional Interface with Lambda =====");
        
        // Using a lambda expression to implement the functional interface
        MathOperation addition = (a, b) -> a + b;
        System.out.println("10 + 5 = " + addition.operate(10, 5));
        
        MathOperation multiplication = (a, b) -> a * b;
        System.out.println("10 * 5 = " + multiplication.operate(10, 5));
        
        // Abstract class vs interface comparison
        System.out.println("\n===== Abstract Class vs Interface Comparison =====");
        
        System.out.println("Abstract Class:");
        System.out.println("- Can have instance variables");
        System.out.println("- Can have constructors");
        System.out.println("- Can have concrete methods");
        System.out.println("- Supports single inheritance");
        System.out.println("- Use when you want to share code among related classes");
        
        System.out.println("\nInterface:");
        System.out.println("- Can only have constants (public static final)");
        System.out.println("- Cannot have constructors");
        System.out.println("- Can have default and static methods (Java 8+)");
        System.out.println("- Supports multiple inheritance");
        System.out.println("- Use when you want to define a contract for unrelated classes");
    }
}

/**
 * Abstract class example
 */
abstract class AbstractDataProcessor {
    // Instance variables
    protected String fileName;
    protected long processedRecords;
    protected long startTime;
    
    // Constructor
    public AbstractDataProcessor() {
        this.processedRecords = 0;
    }
    
    // Concrete method
    public void displayMetrics() {
        long endTime = System.currentTimeMillis();
        System.out.println("Processed " + processedRecords + " records");
        System.out.println("Processing time: " + (endTime - startTime) + " ms");
    }
    
    // Template method pattern
    public final void processData() {
        startTime = System.currentTimeMillis();
        openFile();
        readData();
        parseData();
        validateData();
        closeFile();
    }
    
    // Abstract methods to be implemented by subclasses
    protected abstract void openFile();
    protected abstract void readData();
    protected abstract void parseData();
    
    // Method with default implementation that can be overridden
    protected void validateData() {
        System.out.println("Performing basic data validation");
    }
    
    // Final method that cannot be overridden
    protected final void closeFile() {
        System.out.println("Closing data file");
    }
}

class CSVDataProcessor extends AbstractDataProcessor {
    public CSVDataProcessor() {
        super();
        this.fileName = "data.csv";
    }
    
    @Override
    protected void openFile() {
        System.out.println("Opening CSV file: " + fileName);
    }
    
    @Override
    protected void readData() {
        System.out.println("Reading CSV data");
        this.processedRecords = 100; // Simulated value
    }
    
    @Override
    protected void parseData() {
        System.out.println("Parsing CSV data by splitting on commas");
    }
    
    @Override
    protected void validateData() {
        super.validateData(); // Call parent implementation
        System.out.println("Performing CSV-specific validation");
    }
}

class JSONDataProcessor extends AbstractDataProcessor {
    public JSONDataProcessor() {
        super();
        this.fileName = "data.json";
    }
    
    @Override
    protected void openFile() {
        System.out.println("Opening JSON file: " + fileName);
    }
    
    @Override
    protected void readData() {
        System.out.println("Reading JSON data");
        this.processedRecords = 200; // Simulated value
    }
    
    @Override
    protected void parseData() {
        System.out.println("Parsing JSON data into objects");
    }
}

/**
 * Basic interface example
 */
interface Drawable {
    // Constants (implicitly public, static, final)
    String LABEL = "Shape";
    
    // Abstract method (implicitly public and abstract)
    void draw();
    
    // Static method (Java 8+)
    static void printInfo() {
        System.out.println("Drawable interface is used for objects that can be drawn");
    }
}

class DrawableCircle implements Drawable {
    private double radius;
    
    public DrawableCircle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius: " + radius);
    }
    
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class DrawableRectangle implements Drawable {
    private double width;
    private double height;
    
    public DrawableRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle with width: " + width + " and height: " + height);
    }
}

/**
 * Interface with default and static methods (Java 8+)
 */
interface Logger {
    // Abstract method
    void log(String message);
    
    // Default method (Java 8+)
    default void logError(String message) {
        System.out.println("ERROR: " + message);
    }
    
    // Static method (Java 8+)
    static String getVersion() {
        return "Logger Interface v1.0";
    }
    
    // Private method (Java 9+) - uncomment if using Java 9 or higher
    /*
    private String formatMessage(String message) {
        return "[" + System.currentTimeMillis() + "] " + message;
    }
    
    private static String getPrefix() {
        return "LOG: ";
    }
    */
}

class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("CONSOLE: " + message);
    }
}

class FileLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("FILE: " + message);
    }
    
    // Override default method
    @Override
    public void logError(String message) {
        System.out.println("FILE ERROR: " + message + " (writing to error log file)");
    }
}

/**
 * Multiple interface implementation example
 */
interface AudioPlayer {
    void play(String audioFile);
    void pause();
    void stop();
}

interface VideoPlayer {
    void playVideo(String videoFile);
    void stopVideo();
}

// Class implementing multiple interfaces
class MediaPlayer implements AudioPlayer, VideoPlayer {
    @Override
    public void play(String audioFile) {
        System.out.println("Playing audio: " + audioFile);
    }
    
    @Override
    public void pause() {
        System.out.println("Audio paused");
    }
    
    @Override
    public void stop() {
        System.out.println("Audio stopped");
    }
    
    @Override
    public void playVideo(String videoFile) {
        System.out.println("Playing video: " + videoFile);
    }
    
    @Override
    public void stopVideo() {
        System.out.println("Video stopped");
    }
}

/**
 * Functional interface example (Java 8+)
 */
@FunctionalInterface
interface MathOperation {
    // Single abstract method (SAM)
    int operate(int a, int b);
    
    // A functional interface can have any number of default or static methods
    default MathOperation combine(MathOperation other) {
        return (a, b) -> other.operate(this.operate(a, b), b);
    }
}
