package inventory.controllers;

import inventory.models.Order;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {

    @FXML
    private Button transactionsButton;

    @FXML
    private TableColumn<?, ?> orderIdColumn;

    @FXML
    private TableColumn<?, ?> orderQuantityColumn;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private TextField orderIdTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField dateTimeTextField;

    @FXML
    private TableView<Order> invoiceTable;

    @FXML
    private Label title;

    @FXML
    private TextField firstNameField;

    @FXML
    private TableColumn<?, ?> productIdColumn;

    @FXML
    private Button homeButton;

    @FXML
    private Button signOutButton;

    @FXML
    private TextField productIdTextField;

    @FXML
    private TableColumn<?, ?> dateTimeColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Label buttonStatus;

    @FXML
    private Button manufacturersButton;

    @FXML
    private Button categoriesButton;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private RadioButton findRadioBtn;

    @FXML
    private Button submitButton;

    @FXML
    private RadioButton deleteRadioBtn;

    @FXML
    private Label usernameDisplay;

    @FXML
    private RadioButton addRadioBtn;

    @FXML
    private TextField orderQuantityTextField;

    @FXML
    private TableColumn<?, ?> totalPaidColumn;

    @FXML
    private Button productsButton;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private TextField totalPaidTextField;

    private User currentUser;

    private ObservableList<Order> orderList;

    private DBHandler handler = new DBHandler();

    private Authorizer authorizer = new Authorizer();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTransactionsTable();
        orderIdTextField.setVisible(false);
        orderIdTextField.setManaged(false);
    }

    public void initData(User user) {
        currentUser = user;
        usernameDisplay.setText("User: " + currentUser.GetUsername());
    }


    /**
     * Change scene to home scene
     * @param event
     */
    @FXML
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
     * Change scene to Products scene
     * @param event
     */
    @FXML
    public void changeToProductScene(ActionEvent event) {

        try {
            URL url = Paths.get("./src/main/java/inventory/views/Products.fxml").toUri().toURL();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            Parent productsViewParent = loader.load();
            Scene productsScene = new Scene(productsViewParent);

            // access the controller of Products view to use controller to pass in user to initData()
            ProductsViewController controller = loader.getController();
            controller.initData(currentUser);

            // get stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(productsScene);
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
     * Go back to login screen when user signs out
     */
    @FXML
    void signOutButtonPressed(ActionEvent event) {

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

    /**
     * Refresh the table
     */
    @FXML
    void updateTransactionsTable() {

        try {
            orderIdColumn = new TableColumn<>("Order ID");
            productIdColumn = new TableColumn<>("Product ID");
            orderQuantityColumn = new TableColumn<>("Order Quantity");
            totalPaidColumn = new TableColumn<>("Total Paid");
            dateTimeColumn = new TableColumn<>("Transaction Time");
            firstNameColumn = new TableColumn<>("First Name");
            lastNameColumn = new TableColumn<>("Last Name");
            emailColumn = new TableColumn<>("Email");

            ArrayList<Order> arrayList = handler.getAllInvoiceOrders();
            orderList = FXCollections.observableArrayList(arrayList);

            invoiceTable.setItems(orderList);

            //FIXME: delete this print statement
            System.out.println("arrayList size: " + arrayList.size());

        }catch(Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Change options based on radio button selection
     */
    @FXML
    void radioButtonChanged() {
        try {
            if (this.toggleGroup.getSelectedToggle() == null) {
                buttonStatus.setText("*Please select either Find, Add, or Delete*");
                buttonStatus.setTextFill(Paint.valueOf("red"));
                return;
            }
            if (this.toggleGroup.getSelectedToggle().equals(this.findRadioBtn)) {
                orderIdTextField.setVisible(true);
                orderIdTextField.setManaged(true);
                productIdTextField.setVisible(true);
                productIdTextField.setManaged(true);
                orderQuantityTextField.setVisible(false);
                orderQuantityTextField.setManaged(false);
                totalPaidTextField.setVisible(false);
                totalPaidTextField.setManaged(false);
                dateTimeTextField.setVisible(false);
                dateTimeTextField.setManaged(false);
                firstNameField.setVisible(true);
                firstNameField.setManaged(true);
                lastNameTextField.setVisible(true);
                lastNameTextField.setManaged(true);
                emailTextField.setVisible(true);
                emailTextField.setManaged(true);

                submitButton.setText("Find Product");
            }
            else if (this.toggleGroup.getSelectedToggle().equals(this.addRadioBtn)) {
                orderIdTextField.setVisible(false);
                orderIdTextField.setManaged(false);
                productIdTextField.setVisible(true);
                productIdTextField.setManaged(true);
                orderQuantityTextField.setVisible(true);
                orderQuantityTextField.setManaged(true);
                totalPaidTextField.setVisible(true);
                totalPaidTextField.setManaged(true);
                dateTimeTextField.setVisible(true);
                dateTimeTextField.setManaged(true);
                firstNameField.setVisible(true);
                firstNameField.setManaged(true);
                lastNameTextField.setVisible(true);
                lastNameTextField.setManaged(true);
                emailTextField.setVisible(true);
                emailTextField.setManaged(true);

                submitButton.setText("Add Product");
            }
            else if (this.toggleGroup.getSelectedToggle().equals(this.deleteRadioBtn)) {
                orderIdTextField.setVisible(true);
                orderIdTextField.setManaged(true);
                productIdTextField.setVisible(false);
                productIdTextField.setManaged(false);
                orderQuantityTextField.setVisible(false);
                orderQuantityTextField.setManaged(false);
                totalPaidTextField.setVisible(false);
                totalPaidTextField.setManaged(false);
                dateTimeTextField.setVisible(false);
                dateTimeTextField.setManaged(false);
                firstNameField.setVisible(false);
                firstNameField.setManaged(false);
                lastNameTextField.setVisible(false);
                lastNameTextField.setManaged(false);
                emailTextField.setVisible(false);
                emailTextField.setManaged(false);

                submitButton.setText("Delete Product");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submitButtonClicked(ActionEvent event) {
        try {
            if (this.toggleGroup.getSelectedToggle() == null) {
                buttonStatus.setText("*Please select either Find, Add, or Delete*");
                buttonStatus.setTextFill(Paint.valueOf("red"));
                return;
            }
            else if (this.toggleGroup.getSelectedToggle().equals(this.addRadioBtn)) {
                // FIXME: finish
//                addTransaction();
//            } else if (this.toggleGroup.getSelectedToggle().equals(this.deleteRadioBtn)) {
//                deleteTransaction();
//            } else if (this.toggleGroup.getSelectedToggle().equals(this.findRadioBtn)) {
//                findTransaction();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTransaction() {
        try {
            final String ACTION = "create";
            boolean allowed = authorizer.IsAuthorized(currentUser, ACTION);

            // if user is authorized for this action, let them do it, else return and notify user
            if (allowed) {
                // get string versions of the input
                String productId = productIdTextField.getText();
                String quantity = orderQuantityTextField.getText();
                String totalPaid = totalPaidTextField.getText();
                String dateTime = dateTimeTextField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameTextField.getText();
                String email = emailTextField.getText();


                // if user input for quantity and price cannot be parsed to int and double, notify user
                if (!ParseNumbers.isParsableInt(quantity) || !ParseNumbers.isParsableDouble(totalPaid)) {
                    buttonStatus.setText("*Make sure total price and quantity are in number format*");
                    buttonStatus.setTextFill(Paint.valueOf("red"));
                    return;
                }

                // if user input for dateTime is not in valid format, notify user
                //FIXME: finish datetime check
//
//                // find corresponding manufacturer ID for the name input if exists
//                int manufacturerInt = assignManufacturerInt(manufacturer);
//
//                // if user input for manufacturer is not in database, notify user
//                if (manufacturerInt == 0) {
//                    buttonStatus.setText("*Manufacturer name entered must already be in our database*");
//                    buttonStatus.setTextFill(Paint.valueOf("red"));
//                    return;
//                }
//
//                // find corresponding subcategory ID for the name input if exists
//                int subcategoryInt = assignSubcategoryInt(category);
//
//                //  if user input for subcategory is not in database, notify user
//                if (subcategoryInt == 0) {
//                    buttonStatus.setText("*Category name entered must already be in our database*");
//                    buttonStatus.setTextFill(Paint.valueOf("red"));
//                    return;
//                }
//
//                // parse user input to int and double, then create new product instance from user input
//                int quantity = Integer.parseInt(quantityString);
//                double price = Double.parseDouble(priceString);
//
//                Product newProduct = new Product(upc, productName, quantity, price, manufacturerInt, subcategoryInt);
//
//                System.out.println(newProduct.getManufacturerInt() + " / " + newProduct.getSubcategoryInt());
//
//                // show confirmation window for user to verify that they want to add the new product to the database
//                boolean confirmed = showPopup(currentUser, newProduct, "add");
//
//                // if user says they don't want to add the product to the database, don't add it
//                if (!confirmed) {
//                    buttonStatus.setText("Product was not saved to the database");
//                    buttonStatus.setTextFill(Paint.valueOf("red"));
//                    return;
//                }
//
//                boolean added = handler.insertProduct(newProduct);
//
//                // if product was succesffully inserted into database, notify user
//                if (added) {
//                    upcTextField.clear();
//                    productNameTextField.clear();
//                    priceTextField.clear();
//                    quantityTextField.clear();
//                    manufacturerTextField.clear();
//                    categoryTextField.clear();
//                    buttonStatus.setText("Product successfully added");
//                    buttonStatus.setTextFill(Paint.valueOf("green"));
//                }
//
//                // update table so user can see new product
//                updateTable();
//            }
//            // else user is not authorized for adding a product
//            else{
//                buttonStatus.setText("You are not authorized for this action");
//                buttonStatus.setTextFill(Paint.valueOf("red"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
