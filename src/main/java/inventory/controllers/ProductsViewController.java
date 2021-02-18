package inventory.controllers;

import inventory.models.Manufacturer;
import inventory.models.Product;
import inventory.models.Subcategory;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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
     * Add button clicked, lets user add a new product to the database
     */
    public void addButtonClicked() {
        try {
            final String ACTION = "create";
            boolean allowed = authorizer.IsAuthorized(currentUser, ACTION);

            // if user is authorized for this action, let them do it, else return and notify user
            if (allowed) {
                // get string versions of the input
                String upc = upcTextField.getText();
                String productName = productNameTextField.getText();
                String priceString = priceTextField.getText();
                String quantityString = quantityTextField.getText();
                String manufacturer = manufacturerTextField.getText();
                String category = categoryTextField.getText();

                // make sure UPC entered is not already taken
                boolean upcFoundMatch = checkUpc(upc);

                // if UPC already taken, notify user
                if (upcFoundMatch) {
                    buttonStatus.setText("*UPC entered is already being used in database*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                // if user input for quantity and price cannot be parsed to int and double, notify user
                if (!ParseNumbers.isParsableInt(quantityString) || !ParseNumbers.isParsableDouble(priceString)) {
                    buttonStatus.setText("*Make sure price and quantity are in number format*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                // find corresponding manufacturer ID for the name input if exists
                int manufacturerInt = assignManufacturerInt(manufacturer);

                // if user input for manufacturer is not in database, notify user
                if (manufacturerInt == 0) {
                    buttonStatus.setText("*Manufacturer name entered must already be in our database*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                // find corresponding subcategory ID for the name input if exists
                int subcategoryInt = assignSubcategoryInt(category);

                //  if user input for subcategory is not in database, notify user
                if (subcategoryInt == 0) {
                    buttonStatus.setText("*Category name entered must already be in our database*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                // parse user input to int and double, then create new product instance from user input
                int quantity = Integer.parseInt(quantityString);
                double price = Double.parseDouble(priceString);

                Product newProduct = new Product(upc, productName, quantity, price, manufacturerInt, subcategoryInt);

                System.out.println(newProduct.getManufacturerInt() + " / " + newProduct.getSubcategoryInt());

                // show confirmation window for user to verify that they want to add the new product to the database
                boolean confirmed = showPopup(currentUser, newProduct);

                // if user says they don't want to add the product to the database, don't add it
                if (!confirmed) {
                    buttonStatus.setText("Product was not saved to the database");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                boolean added = handler.insertProduct(newProduct);

                // if product was succesffully inserted into database, notify user
                if (added) {
                    buttonStatus.setText("Product successfully added");
                    buttonStatus.setTextFill(Paint.valueOf("green"));
                }

                // update table so user can see new product
                updateTable();
            }
            // else user is not authorized for adding a product
            else{
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

    private boolean showPopup(User currentUser, Product newProduct) {
        boolean confirmed = false;

        try {
            URL url = Paths.get("./src/main/java/inventory/views/AddProduct.fxml").toUri().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            Parent productsViewParent = loader.load();
            Scene addProductScene = new Scene(productsViewParent);

            // access the controller of AddProduct view to pass in user and newProduct to initData()
            AddProductController controller = loader.getController();
            controller.initData(currentUser, newProduct);

            // get stage info
            Stage window = new Stage();
            window.setScene(addProductScene);
            window.initModality(Modality.APPLICATION_MODAL);
            window.initOwner(addProductButton.getScene().getWindow());
            window.showAndWait();
            confirmed = controller.getResult();

        }catch(Exception e) {
            e.printStackTrace();
        }
        return confirmed;
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

    /**
     * Check if manufacturer to add is a valid manufacturer name in the database
     * @param userInput the manufacturer name to check
     * @return true if it is in the database as a valid manufacturer name
     */
    public int assignManufacturerInt(String userInput) {
        int manufacturerId = 0;
        ArrayList<Manufacturer> manufacturers = handler.getAllManufacturers();

        for (int i = 0; i < manufacturers.size(); i++) {
            Manufacturer currManufacturer = manufacturers.get(i);
            if (currManufacturer.getManufacturerName().equals(userInput)) {
                manufacturerId = currManufacturer.getManufacturerId();
            }
        }
        return manufacturerId;
    }

    /**
     * Check if subcategory to add is a valid subcategory name in the database
     * @param userInput the subcategory name to check
     * @return true if it is in the database as a valid subcategory name
     */
    public int assignSubcategoryInt(String userInput) {
        int subcategoryId = 0;
        ArrayList<Subcategory> subcategories = handler.getAllSubcategories();

        for (int i = 0; i < subcategories.size(); i++) {
            Subcategory currSubcategory = subcategories.get(i);
            if (currSubcategory.getSubcategoryName().equals(userInput)) {
                subcategoryId = currSubcategory.getSubcategoryId();
            }
        }
        return subcategoryId;
    }

    public boolean checkUpc(String userInput) {
        boolean isThere = false;
        if (productsList.contains(userInput)) {
            isThere = true;
        }
        return isThere;
    }
}