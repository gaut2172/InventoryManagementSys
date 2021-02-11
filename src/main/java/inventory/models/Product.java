package inventory.models;

import java.math.BigDecimal;

public class Product {

	
	private int productId;
	private String upc;
	private String productName;
	private int quantity;
	// FIXME: change this to BigDecimal
	private BigDecimal price;
	private String manufacturer;
	private String subcategory;
	
	// default constructor
	public Product() {
	}
	
	/*
	 * constructor with productId
	 */
	public Product(int productId, String upc, String productName, int quantity, BigDecimal price, String manufacturer, String subcategory) {
		try {
			if (productId >= 0 && quantity >= 0 && price.compareTo(new BigDecimal(0)) >= 0) {
				this.productId = productId;
				this.upc = upc;
				this.productName = productName;
				this.quantity = quantity;
				this.price = price;
				this.manufacturer = manufacturer;
				this.subcategory = subcategory;
			}
			else {
				throw new ArithmeticException("Failed to create new instance of Product. productId, quantity, or price is a negative value");
			}
		}catch (ArithmeticException e) {
			System.out.println(e);
		}
	}
	
	/*
	 * constructor with no productId
	 */
	public Product(String upc, String productName, int quantity, BigDecimal price, String manufacturer, String subcategory) {
		this.upc = upc;
		this.productName = productName;
		this.quantity = quantity;  //FIXME: make sure quantity is positive integer
		this.price = price;
		this.manufacturer = manufacturer;
		this.subcategory = subcategory;
	}
	
	
	public int GetProductId() {
		return this.productId;
	}

	public String GetUpc() {
		return this.upc;
	}
	
	public String GetProductName() {
		return this.productName;
	}
	
	public int GetQuantity() {
		return this.quantity;
	}
	
	public BigDecimal GetPrice() {
		return this.price;
		}

	public String GetManufacturer() {
		return this.manufacturer;
	}

	public String GetSubcategory () { return this.subcategory; }
	
	public void SetProductId(int productId) {
		this.productId = productId;
	}
	
	public void SetProductName(String productName) {
		this.productName = productName;
	}
	
	public void SetQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void SetPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	@Override
	/**
	 * Get String representation
	 */
	public String toString() {
		return String.format("%-12d %-30s %-30s %10d %20.2f %30s %40s", productId, upc, productName, quantity, price, manufacturer, subcategory);
		
	}
}
