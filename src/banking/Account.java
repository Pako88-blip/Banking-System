
package banking;

public abstract class Account {

    private String accountNumber;
    protected double balance;
    private Customer owner;

    public Account(String accountNumber, double balance, Customer owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }

    //Deposit Method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
        System.out.println("Deposit amount must be greater than zero.");
        }
    }
    
    //Withdraw Method
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        }
    }
    //Abstract Method
    public abstract void applyMonthlyInterest();
}
