package banking.controller;

import banking.view.LoginView;
import javafx.scene.control.Alert;

public class LoginController {

    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        loginView.getLoginButton().setOnAction(event -> handleLogin());
    }

    private void handleLogin() {

        String customerId = loginView.getCustomerId();
        String password = loginView.getPassword();

        if (customerId.isBlank() || password.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your Customer ID and password.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText(
                "Input received. Authentication is not implemented yet."
            );
            alert.showAndWait();
        }
    }
}
