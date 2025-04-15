# Object-Oriented Programming in Java

This guide covers core OOP concepts in Java, with examples and explanations targeted for technical interviews.

## Table of Contents
1. [Core OOP Principles](#core-oop-principles)
2. [Classes and Objects](#classes-and-objects)
3. [Inheritance](#inheritance)
4. [Polymorphism](#polymorphism)
5. [Encapsulation](#encapsulation)
6. [Abstraction](#abstraction)
7. [Interfaces and Abstract Classes](#interfaces-and-abstract-classes)
8. [Association, Aggregation, and Composition](#association-aggregation-and-composition)
9. [Common OOP Design Patterns](#common-oop-design-patterns)
10. [SOLID Principles](#solid-principles)
11. [Interview Questions and Answers](#interview-questions-and-answers)

## Core OOP Principles

Object-Oriented Programming is built on four main principles:

1. **Inheritance**: The ability of a class to derive properties and behaviors from another class
2. **Polymorphism**: The ability of an object to take many forms
3. **Encapsulation**: Bundling data and methods that operate on that data within a single unit
4. **Abstraction**: Hiding implementation details and showing only functionality

Java is a pure object-oriented language (except for primitive types) that strictly follows these principles.

## Classes and Objects

### What is a Class?
A class is a blueprint or template that defines the variables and methods common to all objects of a certain kind.

### What is an Object?
An object is an instance of a class, which has state (fields) and behavior (methods).

See examples in: [ClassesAndObjects.java](./ClassesAndObjects.java)

## Inheritance

Inheritance is a mechanism where a new class (subclass/derived class) inherits properties and behaviors from an existing class (superclass/base class).

### Types of Inheritance in Java
- **Single Inheritance**: A subclass inherits from only one superclass
- **Multilevel Inheritance**: A class inherits from a subclass, forming a chain
- **Hierarchical Inheritance**: Multiple classes inherit from a single superclass
- **Multiple Inheritance**: Java doesn't support multiple inheritance for classes, but it can be achieved through interfaces

### Key Features
- The `extends` keyword is used to inherit from a class
- The `super` keyword is used to call the superclass constructor or methods
- Java supports method overriding, where a subclass provides a specific implementation of a method defined in its superclass

See examples in: [InheritanceExamples.java](./InheritanceExamples.java)

## Polymorphism

Polymorphism allows objects to be treated as instances of their parent class rather than their actual class. It enables one interface to be used for a general class of actions.

### Types of Polymorphism
- **Compile-time Polymorphism (Method Overloading)**: Multiple methods with the same name but different parameters
- **Runtime Polymorphism (Method Overriding)**: Subclass provides a specific implementation of a method defined in its superclass

See examples in: [PolymorphismExamples.java](./PolymorphismExamples.java)

## Encapsulation

Encapsulation is the bundling of data and methods that operate on that data within a single unit, and restricting access to some of the object's components.

### Key Features
- Access modifiers (public, private, protected, default) control the visibility of fields and methods
- Getters and setters provide controlled access to private fields

See examples in: [EncapsulationExamples.java](./EncapsulationExamples.java)

## Abstraction

Abstraction is the concept of hiding complex implementation details and showing only the necessary features of an object.

### Key Features
- Abstract classes and methods using the `abstract` keyword
- Interfaces to define behavior without implementation

See examples in: [AbstractionExamples.java](./AbstractionExamples.java)

## Interfaces and Abstract Classes

### Abstract Classes
- Cannot be instantiated
- May contain abstract methods and concrete methods
- A class can extend only one abstract class
- Can have constructors, instance variables, and static methods

### Interfaces
- Cannot be instantiated
- All methods are implicitly public and abstract (before Java 8)
- Java 8 introduced default and static methods in interfaces
- Java 9 introduced private methods in interfaces
- A class can implement multiple interfaces
- Cannot have constructors or instance variables (except constants)

See examples in: [InterfacesAndAbstractClasses.java](./InterfacesAndAbstractClasses.java)

## Association, Aggregation, and Composition

These are ways to represent relationships between classes:

### Association
- Represents a relationship between two classes
- Can be one-to-one, one-to-many, many-to-one, or many-to-many
- Example: Teacher and Student have an association

### Aggregation
- A special form of association representing a "has-a" relationship
- Represents a whole-part relationship where parts can exist independently
- Example: Department has Professors, but Professors can exist without a Department

### Composition
- A stronger form of aggregation representing a "contains-a" relationship
- The contained objects cannot exist independently of the container
- Example: House contains Rooms, Rooms cannot exist without a House

See examples in: [RelationshipExamples.java](./RelationshipExamples.java)

## Common OOP Design Patterns

Design patterns are typical solutions to common problems in software design. They are categorized into three groups:

### Creational Patterns
- **Singleton**: Ensures a class has only one instance
- **Factory Method**: Creates objects without specifying the exact class
- **Abstract Factory**: Creates families of related objects
- **Builder**: Constructs complex objects step by step
- **Prototype**: Creates new objects by copying existing ones

### Structural Patterns
- **Adapter**: Allows incompatible interfaces to work together
- **Bridge**: Separates an abstraction from its implementation
- **Composite**: Composes objects into tree structures
- **Decorator**: Adds responsibilities to objects dynamically
- **Facade**: Provides a simplified interface to a complex subsystem
- **Flyweight**: Minimizes memory usage by sharing common parts of objects
- **Proxy**: Provides a surrogate for another object

### Behavioral Patterns
- **Chain of Responsibility**: Passes a request along a chain of handlers
- **Command**: Encapsulates a request as an object
- **Iterator**: Accesses elements of an aggregate object sequentially
- **Mediator**: Reduces chaotic dependencies between objects
- **Memento**: Captures and restores an object's internal state
- **Observer**: Notifies dependent objects about changes
- **State**: Alters an object's behavior when its state changes
- **Strategy**: Defines a family of interchangeable algorithms
- **Template Method**: Defines the skeleton of an algorithm, deferring steps to subclasses
- **Visitor**: Separates an algorithm from the object structure it operates on

See examples in the [design_patterns](../design_patterns/) directory.

## SOLID Principles

SOLID is a set of five design principles for writing maintainable and scalable software:

### Single Responsibility Principle (SRP)
- A class should have only one reason to change
- Each class should have only one responsibility

### Open/Closed Principle (OCP)
- Software entities should be open for extension but closed for modification
- Extend functionality by adding new code, not by modifying existing code

### Liskov Substitution Principle (LSP)
- Objects of a superclass should be replaceable with objects of a subclass without affecting program correctness
- Subtypes must be substitutable for their base types

### Interface Segregation Principle (ISP)
- No client should be forced to depend on methods it does not use
- Many client-specific interfaces are better than one general-purpose interface

### Dependency Inversion Principle (DIP)
- High-level modules should not depend on low-level modules; both should depend on abstractions
- Abstractions should not depend on details; details should depend on abstractions

See examples in: [SOLIDPrinciples.java](./SOLIDPrinciples.java)

## Interview Questions and Answers

See the detailed list of OOP interview questions and sample answers in: [OOPInterviewQuestions.md](./OOPInterviewQuestions.md)
