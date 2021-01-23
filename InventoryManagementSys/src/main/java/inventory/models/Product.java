package inventory.models;

public class Product {

	
	private int productId;
	private String productName;
	private int quantity;
	private double price;
	
	// default constructor
	public Product() {
	}
	
	/*
	 * constructor with productId
	 */
	public Product(int productId, String productName, int quantity, double price) {
		try {
			if (productId >= 0 && quantity >= 0 && price >= 0.00) {
				this.productId = productId;
				this.productName = productName;
				this.quantity = quantity;
				this.price = price;
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
	public Product(String productName, int quantity, double price) {
		this.productName = productName;
		this.quantity = quantity;  //FIXME: make sure quantity is positive integer
		this.price = price;
	}
	
	
	public int GetProductId() {
		return this.productId;
	}
	
	public String GetProductName() {
		return this.productName;
	}
	
	public int GetQuantity() {
		return this.quantity;
	}
	
	public double GetPrice() {
		return this.price;
		}
	
	public void SetProductId(int productId) {
		this.productId = productId;
	}
	
	public void SetProductName(String productName) {
		this.productName = productName;
	}
	
	public void SetQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void SetPrice(double price) {
		this.price = price;
	}
	
	
	@Override
	/**
	 * Get String representation
	 */
	public String toString() {
		return String.format("%-12d %-30s %10d %20.2f", productId, productName, quantity, price);
		
	}
}
