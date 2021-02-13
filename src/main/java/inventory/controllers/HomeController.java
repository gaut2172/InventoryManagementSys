package inventory.controllers;

import inventory.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class HomeController {

    @FXML
    private Button transactionsButton;

    @FXML
    private Button productsButton;

    @FXML
    private Button manufacturersButton;

    @FXML
    private Button categoriesButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Label usernameDisplay;

    private User currentUser;

    public void initData(User user) {
        currentUser = user;

        usernameDisplay.setText("User: " + currentUser.GetUsername());
    }

    /**
     * Change scene to Products scene
     * @param event
     */
    public void changeToProductScene(ActionEvent event) {

        try {
            URL url = Paths.get("./src/main/java/inventory/views/Products.fxml").toUri().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            Parent ProductsViewParent = loader.load();
            Scene homeScene = new Scene(ProductsViewParent);

            // access the controller of Products view to use controller to pass in user to initData()
            ProductsViewController controller = loader.getController();
            controller.initData(currentUser);

            // get stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(homeScene);
            window.show();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




}