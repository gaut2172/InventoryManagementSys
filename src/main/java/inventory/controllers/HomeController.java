package inventory.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    /**
     * Change scene to Products scene
     * @param event
     */
    public void changeToProductScene(ActionEvent event) {

        try {
            URL url = Paths.get("./src/main/java/inventory/views/Products.fxml").toUri().toURL();
            Parent loginViewParent = FXMLLoader.load(url);
            Scene homeScene = new Scene(loginViewParent);

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