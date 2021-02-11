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
    private TableColumn<Product, BigDecimal> retailPriceColumn;

    // FIXME: change DBController to handle manufacturer and category
    @FXML
    private TableColumn<Product, String> manufacturerColumn;

    @FXML
    private TableColumn<Product, String> subCategoryColumn;

    @FXML
    private TableColumn<Product, String> upcColumn;

    @FXML
    private TableView<Product> productsTable;

    private DBHandler handler = new DBHandler();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();
    }

    public void updateTable() {
        ObservableList<Product> productsList = null;

        try {
            // UPC column
            upcColumn = new TableColumn<>("UPC");
            upcColumn.setMinWidth(200);
            upcColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("upc"));


            // product name column
            productNameColumn = new TableColumn<>("Product Name");
            productNameColumn.setMinWidth(200);
            productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));

            // in stock quantity column
            inStockColumn = new TableColumn<>("Stock Quantity");
            inStockColumn.setMinWidth(100);
            inStockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

            // retail price column
            retailPriceColumn = new TableColumn<>("Retail Price");
            retailPriceColumn.setMinWidth(150);
            retailPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));

            // manufacturer column
            manufacturerColumn = new TableColumn<>("Manufacturer");
            manufacturerColumn.setMinWidth(200);
            manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));

            // subcategory column
            subCategoryColumn = new TableColumn<>("Subcategory");
            subCategoryColumn.setMinWidth(200);
            subCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("subcategory"));

            // load the data from an observableList
            ArrayList<Product> arrayList = handler.getAllProducts();
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println(arrayList.get(i));
            }
            System.out.println("\n");

            productsList = FXCollections.observableArrayList(arrayList);
            for (int i = 0; i < productsList.size(); i++) {
                System.out.println(productsList.get(i));
            }
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