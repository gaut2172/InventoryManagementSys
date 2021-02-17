package inventory.controllers;

import inventory.models.Product;
import inventory.models.User;
import inventory.services.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class AddProductController {

        @FXML
        private Label manufacturerLabel;

        @FXML
        private Label upcLabel;

        @FXML
        private Label categoryLabel;

        @FXML
        private Label usernameDisplay;

        @FXML
        private Label productNameLabel;

        @FXML
        private Button yesButton;

        @FXML
        private Label quantityLabel;

        @FXML
        private Label priceLabel;

        @FXML
        private Label messageLabel;

        @FXML
        private Button noButton;

        private User currentUser;

        private Product productToAdd;

        private boolean yesOrNo;

        private DBHandler dbHandler = new DBHandler();

        public void initData(User user, Product product) {
                currentUser = user;
                productToAdd = product;
                usernameDisplay.setText("User: " + currentUser.GetUsername());
                yesOrNo = false;

                upcLabel.setText(productToAdd.getUpc());
                productNameLabel.setText(productToAdd.getProductName());
                quantityLabel.setText(Integer.toString(productToAdd.getQuantity()));
                priceLabel.setText("$ " + Double.toString(productToAdd.getPrice()));
                manufacturerLabel.setText(productToAdd.getManufacturer());
                categoryLabel.setText(productToAdd.getSubcategory());
        }

        public boolean getResult() {
                return yesOrNo;
        }

        public void noButtonPressed(ActionEvent event) {
                yesOrNo = false;
                closeWindow(event);
        }

        public void yesButtonPressed(ActionEvent event) {
                yesOrNo = true;
                closeWindow(event);
        }



        public void closeWindow(ActionEvent event) {
                Stage stage = (Stage)noButton.getScene().getWindow();
                stage.close();
        }





}
