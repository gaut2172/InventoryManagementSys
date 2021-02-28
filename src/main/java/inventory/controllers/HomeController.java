package inventory.controllers;

import inventory.models.Product;
import inventory.models.User;
import inventory.services.DBHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
    private Label todayRevenue;

    @FXML
    private Label ytdRevenue;

    @FXML
    private Label ytdTransactions;

    @FXML
    private TableView<Product> lowStockTable;

    @FXML
    private TableColumn<?,?> lowStockNameCol;

    @FXML
    private TableColumn<?,?> lowStockQuantityCol;

    @FXML
    private TableView<String[]> popularProductsTable;

    @FXML
    private TableColumn<String[], String> popularProdNameCol;

    @FXML
    private TableColumn<String[], String> popularProdSalesCol;

    @FXML
    private Label usernameDisplay;

    private User currentUser;

    private ObservableList<Product> lowStockProdList = FXCollections.observableArrayList();

    private ObservableList<String> popularProdList = FXCollections.observableArrayList();

    private DBHandler handler = new DBHandler();

    public void initData(User user) {
        currentUser = user;

        usernameDisplay.setText("User: " + currentUser.GetUsername());

        // FIXME: display correct figures for dashboard
        updateLowStockTable();
        updatePopularProdTable();
    }

    public void updateLowStockTable() {

        try {
            // create the columns
            lowStockNameCol = new TableColumn<Product, String>("Product Name");
            lowStockQuantityCol = new TableColumn<>("Quantity in Stock");

            // load the data from database into an observableList
            ArrayList<Product> arrayList = handler.getProductsWithLowStock();
            lowStockProdList = FXCollections.observableArrayList(arrayList);

            // add the items to the JavaFX table
            lowStockTable.setItems(lowStockProdList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePopularProdTable() {

        try {
            // create the columns
            popularProdNameCol = new TableColumn<>("Product Name");
            popularProdSalesCol = new TableColumn<>("Quantity in Stock");

            // load the data from database into a String array
            String[][] stringArray = handler.getTotalSalesByProduct();

            for (int i = 0; i < stringArray.length; i++) {
                for (int j = 0; j < stringArray[i].length; j++) {
                    System.out.println(stringArray[i][j]);
                }
                System.out.println();
            }

            // change how columns get data from the data. Need this code for using String[][] data to update table
            popularProdNameCol.setCellValueFactory(p -> {
                String[] x = p.getValue();
                if (x != null && x.length>0) {
                    System.out.println("popularProdSalesCol x[1]= " + x[1]);
                    return new SimpleStringProperty(x[1]);
                } else {
                    System.out.println("x was NULL!!!!!");
                    return new SimpleStringProperty("<no name>");
                }
            });

            // change how columns get data from the data. Need this code for using String[][] data to update table
            popularProdSalesCol.setCellValueFactory(p -> {
                String[] x = p.getValue();
                if (x != null && x.length>0) {
                    System.out.println("popularProdSalesCol x[2]= " + x[2]);
                    return new SimpleStringProperty(x[2]);
                } else {
                    return new SimpleStringProperty("<no name>");
                }
            });

            // add the String array to the JavaFX table
            popularProductsTable.getItems().addAll(Arrays.asList(stringArray));
        }catch(Exception e) {
            e.printStackTrace();
        }
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

    public void signOutButtonPressed(ActionEvent event) {

        try {
            URL url = Paths.get("./src/main/java/inventory/views/Login.fxml").toUri().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            Parent productViewParent = loader.load();
            Scene loginScene = new Scene(productViewParent);

            // access the controller of Products view to use controller to pass in user to initData()
            LoginController controller = loader.getController();

            // get stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(loginScene);
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