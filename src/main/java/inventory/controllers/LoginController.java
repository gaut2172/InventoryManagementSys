package inventory.controllers;

import inventory.models.User;
import inventory.services.MiddleLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label status;

    @FXML
    private TextField textField_username;

    @FXML
    private PasswordField passwordField;

    private MiddleLogin authenticator = new MiddleLogin();

    public void Login(ActionEvent event) {
        try {
            User user = null;
            String username = textField_username.getText();
            String pwd = passwordField.getText();
            user = authenticator.authenticate(username, pwd);

            if (user != null) {
                status.setText("Login Successful");
            } else {
                status.setText("Please try again.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
