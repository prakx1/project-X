/**
 * This file demonstrates inheritance concepts in Java for technical interviews.
 * 
 * Inheritance allows a class to inherit properties and behaviors from another class.
 * Java supports single inheritance for classes, but multiple inheritance through interfaces.
 * 
 * Key concepts covered:
 * 1. Class inheritance with 'extends'
 * 2. Method overriding
 * 3. Super keyword usage
 * 4. Types of inheritance (single, multilevel, hierarchical)
 * 5. Final classes and methods
 * 6. Method hiding (static methods in inheritance)
 */
public class InheritanceExamples {

    public static void main(String[] args) {
        // Single Inheritance Example
        System.out.println("===== Single Inheritance =====");
        Dog dog = new Dog("Buddy", "Golden Retriever");
        dog.eat();
        dog.bark();
        dog.displayInfo();
        
        // Multilevel Inheritance Example
        System.out.println("\n===== Multilevel Inheritance =====");
        Puppy puppy = new Puppy("Max", "Labrador", 3);
        puppy.eat();
        puppy.bark();
        puppy.play();
        puppy.displayInfo();
        
        // Hierarchical Inheritance Example
        System.out.println("\n===== Hierarchical Inheritance =====");
        Cat cat = new Cat("Whiskers", "Siamese");
        cat.eat(); // Inherited from Animal
        cat.meow(); // Specific to Cat
        cat.displayInfo();
        
        // Method Overriding Example
        System.out.println("\n===== Method Overriding =====");
        Animal animal = new Animal("Generic Animal");
        animal.makeSound(); // Animal implementation
        
        Animal dogAsAnimal = new Dog("Rex", "German Shepherd");
        dogAsAnimal.makeSound(); // Dog's overridden implementation
        
        Animal catAsAnimal = new Cat("Mittens", "Persian");
        catAsAnimal.makeSound(); // Cat's overridden implementation
        
        // Method Hiding Example (static methods)
        System.out.println("\n===== Method Hiding (Static Methods) =====");
        Animal.staticMethod(); // Animal's static method
        Dog.staticMethod(); // Dog's static method (hides Animal's method)
        
        Animal dogRef = new Dog("Spot", "Dalmatian");
        dogRef.staticMethod(); // Calls Animal's method (based on reference type, not object type)
        
        // Final methods and classes
        System.out.println("\n===== Final Methods and Classes =====");
        Bird bird = new Bird("Tweety");
        bird.fly(); // final method
        
        Sparrow sparrow = new Sparrow("Jack");
        sparrow.chirp();
        sparrow.fly(); // Inherited final method, cannot be overridden
    }
}

// Base class
class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void makeSound() {
        System.out.println(name + " makes a generic sound");
    }
    
    public static void staticMethod() {
        System.out.println("Static method in Animal class");
    }
}

// Single inheritance
class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name); // Call to parent constructor
        this.breed = breed;
    }
    
    public void bark() {
        System.out.println(name + " is barking");
    }
    
    // Method overriding
    @Override
    public void makeSound() {
        System.out.println(name + " barks: Woof! Woof!");
    }
    
    // Method hiding (static method)
    public static void staticMethod() {
        System.out.println("Static method in Dog class");
    }
    
    public void displayInfo() {
        System.out.println("Dog: " + name + ", Breed: " + breed);
    }
}

// Multilevel inheritance
class Puppy extends Dog {
    private int age;
    
    public Puppy(String name, String breed, int age) {
        super(name, breed);
        this.age = age;
    }
    
    public void play() {
        System.out.println(name + " is playing");
    }
    
    // Overriding method from Dog class
    @Override
    public void displayInfo() {
        super.displayInfo(); // Calls parent method
        System.out.println("Age: " + age + " months");
    }
}

// Hierarchical inheritance (another class extending Animal)
class Cat extends Animal {
    private String breed;
    
    public Cat(String name, String breed) {
        super(name);
        this.breed = breed;
    }
    
    public void meow() {
        System.out.println(name + " is meowing");
    }
    
    // Method overriding
    @Override
    public void makeSound() {
        System.out.println(name + " meows: Meow! Meow!");
    }
    
    public void displayInfo() {
        System.out.println("Cat: " + name + ", Breed: " + breed);
    }
}

// Example of final keyword
final class Bird {
    protected String name;
    
    public Bird(String name) {
        this.name = name;
    }
    
    // Final method cannot be overridden in subclasses
    public final void fly() {
        System.out.println(name + " is flying");
    }
}

// Can extend a non-final class
class Sparrow extends Bird {
    public Sparrow(String name) {
        super(name);
    }
    
    public void chirp() {
        System.out.println(name + " is chirping");
    }
    
    // Cannot override fly() because it's final in the parent class
    // @Override
    // public void fly() { // This would cause a compilation error
    //     System.out.println(name + " flies differently");
    // }
}

// This would cause a compilation error as Bird class is final
// class Eagle extends Bird {
//     public Eagle(String name) {
//         super(name);
//     }
// }
