package banking;

public class ChequeAccount extends Account {

    private String employer;
    private String companyAddress;

    public ChequeAccount(String accountNumber, double balance,
                         Customer owner, boolean employed,
                         String employer, String companyAddress) {

        super(accountNumber, balance, owner);

        if (!employed) {
            throw new IllegalArgumentException(
                "Cheque accounts are only available to employed customers."
            );
        }

        this.employer = employer;
        this.companyAddress = companyAddress;
    }

    public String getEmployer() {
        return employer;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    @Override
    public void applyMonthlyInterest() {
        // No cheque-account interest rate is specified in the assignment.
    }
}
