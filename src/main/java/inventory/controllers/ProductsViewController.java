package inventory.controllers;

import inventory.models.Product;
import inventory.models.User;
import inventory.services.Authorizer;
import inventory.services.DBHandler;
import inventory.services.ParseNumbers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsViewController implements Initializable {

    @FXML
    private Button transactionsButton;

    @FXML
    private Button findProductButton;

    @FXML
    private TableColumn<?, ?> subCategoryColumn;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TableColumn<?, ?> upcColumn;

    @FXML
    private Label usernameDisplay;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private Label title;

    @FXML
    private Label buttonStatus;

    @FXML
    private TextField categoryTextField;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button signOutButton;

    @FXML
    private TableColumn<?, ?> manufacturerColumn;

    @FXML
    private Button productsButton;

    @FXML
    private Button manufacturersButton;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> inStockColumn;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button addProductButton;

    @FXML
    private TableColumn<?, ?> retailPriceColumn;

    @FXML
    private Button categoriesButton;

    @FXML
    private TextField upcTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField manufacturerTextField;

    private User currentUser;

    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    private DBHandler handler = new DBHandler();

    private Authorizer authorizer = new Authorizer();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();
    }


    public void initData(User user) {
        currentUser = user;

        usernameDisplay.setText("User: " + currentUser.GetUsername());
    }

    public void updateTable() {

        try {
            // UPC column
            upcColumn = new TableColumn<Product, String>("UPC");
            upcColumn.setMinWidth(200);


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
     * Filter table user input
     * @param event
     */
    public void addButtonClicked(ActionEvent event) {
        try {
            final String ACTION = "create";
            boolean allowed = authorizer.IsAuthorized(currentUser, ACTION);

            if (allowed) {

                String upc = upcTextField.getText();
                String productName = productNameTextField.getText();
                String priceString = priceTextField.getText();
//            double price = Double.parseDouble(priceTextField.getText());
                String quantityString = quantityTextField.getText();
//            int quantity = Integer.parseInt(quantityTextField.getText());
                String manufacturer = manufacturerTextField.getText();
                String category = categoryTextField.getText();

//            ArrayList<String> allFields = new ArrayList<>();
//            allFields.add(upc);
//            allFields.add(productName);
//            allFields.add(priceString);
//            allFields.add(quantityString);
//            allFields.add(manufacturer);
//            allFields.add(category);

//            for (int i = 0; i < allFields.size(); i++) {
//                System.out.println(allFields.get(i));
//            }
                if (ParseNumbers.isParsableInt(quantityString) && ParseNumbers.isParsableDouble(priceString)) {
                    int quantity = Integer.parseInt(quantityString);
                    double price = Double.parseDouble(priceString);
                    Product newProduct = new Product(upc, productName, quantity, price, manufacturer, category);

                    System.out.println(newProduct.getUpc() + " " + newProduct.getProductName() +
                            " " + newProduct.getQuantity() + " " + newProduct.getPrice());



                    buttonStatus.setText("Product successfully added");
                    buttonStatus.setTextFill(Paint.valueOf("green"));
                }
                else {
                    buttonStatus.setText("*Make sure price and quantity are in number format*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                }





            }
            else {
                buttonStatus.setText("You are not authorized for this action");
                buttonStatus.setTextFill(Paint.valueOf("red"));
            }


            /*
            add product:
            1 - XXXX enter upc, name, price, and stock, manufacturer, category
            2 - query manufacturer and subcategory tables for a match
            3 - window pops up, prints user input for confirmation
            4 - print success, or say try again
            5 - pop up window closes and products table updates
             */


        }catch(Exception e) {
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

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            Parent loginViewParent = loader.load();
            Scene homeScene = new Scene(loginViewParent);

            // access the controller of Products view to use controller to pass in user to initData()
            HomeController controller = loader.getController();
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