package banking.view;

import banking.Account;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AccountView {

    public VBox createView(Account account) {

        Label titleLabel = new Label("BANKING SYSTEM");
        Label accountLabel = new Label(
            "Account Number: " + account.getAccountNumber()
        );
        Label balanceLabel = new Label(
            "Balance: BWP " + String.format("%.2f", account.getBalance())
        );
        Label customerLabel = new Label(
            "Customer: " + account.getOwner().getFullName()
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
            titleLabel,
            accountLabel,
            balanceLabel,
            customerLabel
        );

        return layout;
    }
}