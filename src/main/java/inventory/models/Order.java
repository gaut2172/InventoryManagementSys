package inventory.models;

import javafx.beans.property.*;

public class Order {

    private IntegerProperty orderID;
    private IntegerProperty productID;
    private IntegerProperty orderQuantity;
    private DoubleProperty totalPaid;
    private StringProperty dateTimeString;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;

    // default constructor
    public Order() {
    }

    public Order(int orderID, int productID, int quantity, double totalPaid,
                 String dateTime, String firstName, String lastName, String email) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.productID = new SimpleIntegerProperty(productID);
        this.orderQuantity = new SimpleIntegerProperty(quantity);
        this.totalPaid = new SimpleDoubleProperty(totalPaid);
        this.dateTimeString = new SimpleStringProperty(dateTime);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
    }

    public int getOrderID() { return orderID.get(); }

    public int getProductID() { return productID.get(); }

    public int getOrderQuantity() { return orderQuantity.get(); }

    public double getTotalPaid() { return totalPaid.get(); }

    public String getDateTimeString() { return dateTimeString.get(); }

    public String getFirstName() { return firstName.get(); }

    public String getLastName() { return lastName.get(); }

    public String getEmail() { return email.get(); }
}
