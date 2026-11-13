package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {

    private TextField customerIdField;
    private PasswordField passwordField;
    private Button loginButton;

    public VBox createView() {

        Label titleLabel = new Label("BANKING SYSTEM");
        Label instructionLabel = new Label("Please log in to continue");

        customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");

        VBox layout = new VBox(
            15,
            titleLabel,
            instructionLabel,
            customerIdField,
            passwordField,
            loginButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setMaxWidth(350);

        return layout;
    }

    public String getCustomerId() {
        return customerIdField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }
}
