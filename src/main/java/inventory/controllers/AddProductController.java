package inventory.controllers;

import inventory.models.Product;
import inventory.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

        public void initData(User user, Product product) {
                currentUser = user;
                productToAdd = product;
                usernameDisplay.setText("User: " + currentUser.GetUsername());

                upcLabel.setText(productToAdd.getUpc());
                productNameLabel.setText(productToAdd.getProductName());
                quantityLabel.setText(Integer.toString(productToAdd.getQuantity()));
                priceLabel.setText(Double.toString(productToAdd.getPrice()));
                manufacturerLabel.setText(productToAdd.getManufacturer());
                categoryLabel.setText(productToAdd.getSubcategory());
        }


}
