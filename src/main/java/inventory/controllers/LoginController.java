package inventory.controllers;

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

    public void Login(ActionEvent event) {
        try {
            if (status == null) {
                System.out.println("it was null");
//                label_status.setText("Login Successful");
            } else {
                status.setText("Please try again.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
