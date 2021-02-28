package inventory.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Row {

    private StringProperty name;
    private StringProperty totalSales;

    Row(String name, String totalSales){
        this.name = new SimpleStringProperty(name);
        this.totalSales = new SimpleStringProperty(totalSales);
    }

    public String getName() { return name.get(); }

    public String getTotalSales() { return totalSales.get(); }
}
