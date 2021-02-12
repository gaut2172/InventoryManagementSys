package inventory.controllers;

import inventory.models.Product;
import inventory.services.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsViewController implements Initializable {

    @FXML
    private Button transactionsButton;

    @FXML
    private Button productsButton;

    @FXML
    private Button manufacturersButton;

    @FXML
    private Label usernameDisplay;

    @FXML
    private Label title;

    @FXML
    private Button categoriesButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button signOutButton;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> inStockColumn;

    //FIXME: change this to BigDecimal, not double
    @FXML
    private TableColumn<Product, Double> retailPriceColumn;

    // FIXME: change DBController to handle manufacturer and category
    @FXML
    private TableColumn<Product, String> manufacturerColumn;

    @FXML
    private TableColumn<Product, String> subCategoryColumn;

    @FXML
    private TableColumn<Product, String> upcColumn;

    @FXML
    private TableView<Product> productsTable;

    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    private DBHandler handler = new DBHandler();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();
    }

    public void updateTable() {

        try {
            // UPC column
            upcColumn = new TableColumn<Product, String>("UPC");
            upcColumn.setMinWidth(200);
            upcColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("upc"));


            // product name column
            productNameColumn = new TableColumn<>("Product Name");
            productNameColumn.setMinWidth(200);

            // in stock quantity column
            inStockColumn = new TableColumn<>("Stock Quantity");
            inStockColumn.setMinWidth(100);

            // retail price column
            retailPriceColumn = new TableColumn<>("Retail Price");
            retailPriceColumn.setMinWidth(150);

            // manufacturer column
            manufacturerColumn = new TableColumn<>("Manufacturer");
            manufacturerColumn.setMinWidth(200);

            // subcategory column
            subCategoryColumn = new TableColumn<>("Subcategory");
            subCategoryColumn.setMinWidth(200);

            // load the data from database into an observableList
            ArrayList<Product> arrayList = handler.getAllProducts();
            productsList = FXCollections.observableArrayList(arrayList);

            // add the items to the JavaFX table
            productsTable.setItems(productsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Change scene to home scene
     * @param event
     */
    public void changeToHomeScreen(ActionEvent event) {

        try {
            URL url = Paths.get("./src/main/java/inventory/views/Home.fxml").toUri().toURL();
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