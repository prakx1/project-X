/**
 * This file demonstrates encapsulation concepts in Java for technical interviews.
 * 
 * Encapsulation is the bundling of data (attributes) and methods (behavior) that operate 
 * on the data into a single unit (class), and restricting direct access to some of its components.
 * 
 * Key concepts covered:
 * 1. Access modifiers (public, private, protected, default)
 * 2. Getter and setter methods
 * 3. Immutable classes
 * 4. Java Bean pattern
 * 5. Data validation in encapsulation
 */
public class EncapsulationExamples {

    public static void main(String[] args) {
        // Basic encapsulation example
        System.out.println("===== Basic Encapsulation =====");
        BankAccount account = new BankAccount("John Doe", 1000.0);
        
        // Cannot access private fields directly
        // account.balance = 2000.0; // Compilation error
        
        // Must use public methods to interact with the object
        System.out.println("Account holder: " + account.getAccountHolder());
        System.out.println("Current balance: $" + account.getBalance());
        
        // Data validation during deposit
        System.out.println("\nDepositing $500:");
        account.deposit(500.0);
        System.out.println("New balance: $" + account.getBalance());
        
        // Data validation during withdrawal
        System.out.println("\nWithdrawing $2000 (exceeds balance):");
        boolean success = account.withdraw(2000.0);
        System.out.println("Withdrawal successful? " + success);
        System.out.println("Balance after attempt: $" + account.getBalance());
        
        System.out.println("\nWithdrawing $200 (valid amount):");
        success = account.withdraw(200.0);
        System.out.println("Withdrawal successful? " + success);
        System.out.println("Balance after withdrawal: $" + account.getBalance());
        
        // Immutable class example
        System.out.println("\n===== Immutable Class =====");
        ImmutablePerson person = new ImmutablePerson("Alice Smith", 30, new String[]{"Reading", "Traveling"});
        System.out.println(person);
        
        // Try to modify the hobbies array
        String[] hobbies = person.getHobbies();
        hobbies[0] = "Swimming"; // This changes the array we got back
        
        // But the original object is not affected due to defensive copying
        System.out.println("After attempting to modify hobbies:");
        System.out.println(person);
        
        // Java Bean Pattern example
        System.out.println("\n===== Java Bean Pattern =====");
        Employee employee = new Employee();
        employee.setId(101);
        employee.setName("Bob Johnson");
        employee.setDepartment("Engineering");
        employee.setSalary(75000.0);
        
        System.out.println("Employee details: " + employee);
        
        // Package-private and protected access example
        System.out.println("\n===== Access Modifiers =====");
        AccessModifiersDemo demo = new AccessModifiersDemo();
        demo.demonstrateAccessModifiers();
    }
}

/**
 * Demonstrates basic encapsulation with private fields and public methods
 */
class BankAccount {
    private String accountHolder;
    private double balance;
    
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }
        
        balance -= amount;
        return true;
    }
}

/**
 * Demonstrates an immutable class
 * 
 * Immutable classes:
 * 1. Declare class as final (prevents subclassing)
 * 2. Make all fields private and final
 * 3. No setter methods
 * 4. Return copies of mutable fields in getter methods
 * 5. Initialize all fields in constructor and create defensive copies if needed
 */
final class ImmutablePerson {
    private final String name;
    private final int age;
    private final String[] hobbies; // Mutable object reference
    
    public ImmutablePerson(String name, int age, String[] hobbies) {
        this.name = name;
        this.age = age;
        
        // Defensive copy for mutable objects
        this.hobbies = (hobbies != null) ? hobbies.clone() : null;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String[] getHobbies() {
        // Return a defensive copy to protect internal state
        return (hobbies != null) ? hobbies.clone() : null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImmutablePerson{name='").append(name).append("', age=").append(age).append(", hobbies=[");
        
        if (hobbies != null) {
            for (int i = 0; i < hobbies.length; i++) {
                sb.append(hobbies[i]);
                if (i < hobbies.length - 1) {
                    sb.append(", ");
                }
            }
        }
        
        sb.append("]}");
        return sb.toString();
    }
}

/**
 * Demonstrates Java Bean pattern
 * 
 * Java Bean characteristics:
 * 1. Public no-arg constructor
 * 2. Private fields
 * 3. Public getter and setter methods
 * 4. Implements Serializable (optional)
 */
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    
    // No-arg constructor
    public Employee() {
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
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department + "', salary=" + salary + "}";
    }
}

/**
 * Demonstrates different access modifiers
 */
class AccessModifiersDemo {
    // Private: accessible only within the class
    private String privateVar = "Private variable";
    
    // Default (package-private): accessible within the same package
    String defaultVar = "Default variable";
    
    // Protected: accessible within the same package and subclasses
    protected String protectedVar = "Protected variable";
    
    // Public: accessible from anywhere
    public String publicVar = "Public variable";
    
    public void demonstrateAccessModifiers() {
        System.out.println("Inside AccessModifiersDemo class:");
        
        // All variables are accessible within the class itself
        System.out.println("- " + privateVar + " (accessible)");
        System.out.println("- " + defaultVar + " (accessible)");
        System.out.println("- " + protectedVar + " (accessible)");
        System.out.println("- " + publicVar + " (accessible)");
        
        // Create an instance of a class in the same package
        AccessModifiersSamePackage samePackage = new AccessModifiersSamePackage();
        samePackage.accessVariables(this);
        
        // Create a subclass instance
        AccessModifiersSubclass subclass = new AccessModifiersSubclass();
        subclass.accessVariables();
    }
    
    private void privateMethod() {
        System.out.println("This is a private method");
    }
    
    void defaultMethod() {
        System.out.println("This is a default (package-private) method");
    }
    
    protected void protectedMethod() {
        System.out.println("This is a protected method");
    }
    
    public void publicMethod() {
        System.out.println("This is a public method");
    }
}

/**
 * Class in the same package as AccessModifiersDemo
 */
class AccessModifiersSamePackage {
    public void accessVariables(AccessModifiersDemo demo) {
        System.out.println("\nFrom a class in the same package:");
        
        // Private variables are not accessible from another class
        // System.out.println("- " + demo.privateVar); // Compilation error
        
        // Default variables are accessible within the same package
        System.out.println("- " + demo.defaultVar + " (accessible)");
        
        // Protected variables are accessible within the same package
        System.out.println("- " + demo.protectedVar + " (accessible)");
        
        // Public variables are accessible from anywhere
        System.out.println("- " + demo.publicVar + " (accessible)");
        
        // Methods accessibility follows the same pattern
        // demo.privateMethod(); // Not accessible
        demo.defaultMethod(); // Accessible
        demo.protectedMethod(); // Accessible
        demo.publicMethod(); // Accessible
    }
}

/**
 * Subclass of AccessModifiersDemo
 */
class AccessModifiersSubclass extends AccessModifiersDemo {
    public void accessVariables() {
        System.out.println("\nFrom a subclass:");
        
        // Private variables are not accessible from a subclass
        // System.out.println("- " + privateVar); // Compilation error
        
        // Default variables are accessible if the subclass is in the same package
        System.out.println("- " + defaultVar + " (accessible within the same package)");
        
        // Protected variables are accessible to subclasses, even in different packages
        System.out.println("- " + protectedVar + " (accessible)");
        
        // Public variables are accessible from anywhere
        System.out.println("- " + publicVar + " (accessible)");
        
        // Methods accessibility follows the same pattern
        // privateMethod(); // Not accessible
        defaultMethod(); // Accessible if in same package
        protectedMethod(); // Accessible
        publicMethod(); // Accessible
    }
}
