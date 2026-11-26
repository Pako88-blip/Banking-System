
package banking;

public class InvestmentAccount extends Account {

    public InvestmentAccount(String accountNumber, double balance,
                             Customer owner) {
        super(accountNumber, balance, owner);

        if (balance < 500) {
        throw new IllegalArgumentException(
            "Initial deposit must be at least BWP 500."
            );
        }
    }

    // Used when restoring an existing account from the database.
    public InvestmentAccount(String accountNumber, double balance,
                             Customer owner, boolean restoring) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * 0.05;
        balance += interest;
    }
}
