package banking;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, double balance,
                          Customer owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals are not allowed from savings accounts.");
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * 0.0005;
        balance += interest;
    }

    
}

