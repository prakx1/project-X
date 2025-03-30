package oops

// 🔹 How This Implements Encapsulation?
// 1️⃣ Private Fields (accountNumber, balance) → Direct access is restricted.
// 2️⃣ Getter Methods (GetBalance(), GetAccountNumber()) → Allows controlled read access.
// 3️⃣ Setter Methods (Deposit(), Withdraw()) → Allows controlled write access with validation.
// 4️⃣ Encapsulated Business Logic (TransferFunds()) → Handles operations securely.
// 5️⃣ Constructor (NewBankAccount()) → Ensures safe object creation and initialization.

import "fmt"

// BankAccount struct with encapsulated fields
type BankAccount struct {
	accountNumber string  // Private field (unexported)
	balance       float64 // Private field (unexported)
}

// Constructor Function (Encapsulation)
func NewBankAccount(accountNumber string, initialBalance float64) *BankAccount {
	if initialBalance < 0 {
		fmt.Println("Initial balance cannot be negative. Setting to 0.")
		initialBalance = 0
	}
	return &BankAccount{accountNumber: accountNumber, balance: initialBalance}
}

// Getter for Account Number (Encapsulation)
func (ba *BankAccount) GetAccountNumber() string {
	return ba.accountNumber
}

// Getter for Balance (Encapsulation)
func (ba *BankAccount) GetBalance() float64 {
	return ba.balance
}

// Setter for Balance (Encapsulation with validation)
func (ba *BankAccount) Deposit(amount float64) {
	if amount > 0 {
		ba.balance += amount
		fmt.Println("Deposited:", amount)
	} else {
		fmt.Println("Invalid deposit amount")
	}
}

// Withdraw method (Encapsulation with validation)
func (ba *BankAccount) Withdraw(amount float64) {
	if amount > 0 && amount <= ba.balance {
		ba.balance -= amount
		fmt.Println("Withdrawn:", amount)
	} else {
		fmt.Println("Insufficient balance or invalid amount")
	}
}

// Business Logic: Transfer Money (Encapsulation)
func (ba *BankAccount) TransferFunds(target *BankAccount, amount float64) {
	if amount > 0 && amount <= ba.balance {
		ba.balance -= amount
		target.balance += amount
		fmt.Println("Transferred:", amount, "to", target.GetAccountNumber())
	} else {
		fmt.Println("Transfer failed: Insufficient funds or invalid amount")
	}
}

func encapsulationExample() {
	// Creating two accounts (Encapsulation via constructor)
	account1 := NewBankAccount("1234567890", 1000)
	account2 := NewBankAccount("9876543210", 500)

	// Accessing encapsulated data using getters
	fmt.Println("Account 1 Balance:", account1.GetBalance())
	fmt.Println("Account 2 Balance:", account2.GetBalance())

	// Performing operations using setters and business logic
	account1.Deposit(500)
	account1.Withdraw(300)
	account1.TransferFunds(account2, 700)

	// Checking updated balances
	fmt.Println("Final Account 1 Balance:", account1.GetBalance())
	fmt.Println("Final Account 2 Balance:", account2.GetBalance())
}
