package banking;

import banking.view.LoginView;
import banking.controller.LoginController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Create the login screen
        LoginView loginView = new LoginView();

        // Build the screen and initialize its buttons
        VBox layout = loginView.createView();

        // Connect the login button to the controller
        new LoginController(loginView);

        // Display the window
        Scene scene = new Scene(layout, 500, 400);

        stage.setTitle("Banking System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
