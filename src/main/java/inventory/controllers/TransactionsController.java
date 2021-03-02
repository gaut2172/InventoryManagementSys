package inventory.controllers;

import inventory.models.Order;
import inventory.models.Product;
import inventory.models.User;
import inventory.services.Authorizer;
import inventory.services.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
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
        updateTable();
    }

    @FXML
    void changeToHomeScreen(ActionEvent event) {

    }

    @FXML
    void signOutButtonPressed(ActionEvent event) {

    }

    @FXML
    void updateTable() {

        try {
//            orderIdColumn = new TableColumn<>("Order ID");
            ArrayList<Order> arrayList = handler.getAllInvoiceOrders();
            orderList = FXCollections.observableArrayList(arrayList);

            invoiceTable.setItems(orderList);

        }catch(Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void radioButtonChanged(ActionEvent event) {

    }

    @FXML
    void submitButtonClicked(ActionEvent event) {

    }

}
