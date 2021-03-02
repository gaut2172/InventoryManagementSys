package inventory.services;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import inventory.models.*;

/**
 * CRUD handler for database
 * @author gaut2172
 *
 */
public class DBHandler {

	/**
	 * Insert product object into database
	 * @param myProduct
	 * @return true if successful
	 */
	public boolean insertProduct(Product myProduct) {
		
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "INSERT INTO product(upc, productName, quantity, retailPrice, manufacturer, subcategory) VALUES(?, ?, ?, ?, ?, ?)";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, myProduct.getUpc());
			stmt.setString(2, myProduct.getProductName());
			stmt.setInt(3,  myProduct.getQuantity());
			stmt.setDouble(4, myProduct.getPrice());
			stmt.setInt(5, myProduct.getManufacturerInt());
			stmt.setInt(6, myProduct.getSubcategoryInt());
			
			// execute SQL command
			int inserted = stmt.executeUpdate();
			
			// were there any affected rows?
			result = inserted >= 1;
			
			// disconnect
			DBConnection.disconnect(conn);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	
	public boolean deleteProduct(String upc) {
		
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "DELETE FROM product WHERE upc = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, upc);
			
			// execute SQL command
			int deleted = stmt.executeUpdate();
			
			// were there any affected rows?
			result = deleted >= 1;
			
			// disconnect
			DBConnection.disconnect(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * Update an existing product record using a productId
	 * @param product user generated product with new values
	 * @return true if there were rows affected
	 */
	public boolean updateProduct(Product product) {
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "UPDATE product SET ProductName = ?, Quantity = ?, Price = ? WHERE ProductId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getQuantity());
			stmt.setDouble(3, product.getPrice());
			stmt.setInt(4, product.getProductID());
			
			// execute SQL command
			int rowsUpdated = stmt.executeUpdate();
			
			// were there any affected rows?
			result = rowsUpdated >= 1;
			
			// disconnect
			DBConnection.disconnect(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	public Product getProductByUpcAndName(String upc, String productName) {
		
		Product foundProduct = null;
		Manufacturer foundManufacturer = null;
		Subcategory foundSubcategory = null;
		
		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM view_products_1 WHERE upc = ? OR productName = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, upc);
			stmt.setString(2, productName);
			
			// execute SQL command and record results
			ResultSet results1 = stmt.executeQuery();
			
			// FIXME: check to see if the ResultSet has more than one result (potential bug)
			while (results1.next()) {
				System.out.println("FOUND PRODUCT");
				foundProduct = new Product(results1.getString(2), results1.getString(3), results1.getInt(4),
						results1.getDouble(5), results1.getString(6), results1.getString(7));
			}

			if (foundProduct == null) {
				return foundProduct;
			}

			// parameterize SQL statement to deter SQL injection attacks
			sql = "SELECT * FROM view_manufacturers_1 WHERE manufacturerName = ?";
			stmt = conn.prepareStatement(sql);

			// insert values into prepared statement
			stmt.setString(1, foundProduct.getManufacturer());

			// execute SQL command and record results
			ResultSet results2 = stmt.executeQuery();

			while (results2.next()) {
				foundManufacturer = new Manufacturer(results2.getInt(1), results2.getString(2));
			}

			if (foundManufacturer != null) {
				System.out.println(foundManufacturer.getManufacturerId());
				foundProduct.setManufacturerInt(foundManufacturer.getManufacturerId());
			}

			// parameterize SQL statement to deter SQL injection attacks
			sql = "SELECT * FROM view_subcategories_1 WHERE subcategoryName = ?";
			stmt = conn.prepareStatement(sql);

			// insert values into prepared statement
			stmt.setString(1, foundProduct.getSubcategory());

			// execute SQL command and record results
			ResultSet results3 = stmt.executeQuery();

			while (results3.next()) {
				foundSubcategory = new Subcategory(results3.getInt(1), results3.getString(2));
			}

			if (foundSubcategory != null) {
				foundProduct.setSubcategoryInt(foundSubcategory.getSubcategoryId());
			}

			DBConnection.disconnect(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return foundProduct;
	}


	public ArrayList<Product> getProductsWithLowStock() {
		ArrayList<Product> productsList = new ArrayList<>();

		Product currProduct = null;

		try {
			String sql = "SELECT * FROM view_products_1 WHERE quantity <= 5";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// iterate through ResultSet
			while (results.next()) {
				currProduct = new Product(results.getString(2), results.getString(3), results.getInt(4),
						results.getDouble(5), results.getString(6), results.getString(7));

				productsList.add(currProduct);
			}
			DBConnection.disconnect(conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return productsList;
	}


	public ArrayList<Row> getTotalSalesByProduct() {
		ArrayList<Row> totalSalesList = new ArrayList<>();

		try {
			String sql1 = "SELECT COUNT(*) FROM view_popular_products_1";
			Connection conn1 = DBConnection.getConnection();
			PreparedStatement stmt1 = conn1.prepareStatement(sql1);

			// execute SQL command and record results
			ResultSet results1 = stmt1.executeQuery();

			int numRows = 0;
			// iterate through ResultSet
			while (results1.next()) {
				numRows = results1.getInt(1);
			}

			DBConnection.disconnect(conn1);

			if (numRows == 0) {
				return null;
			}

			String sql2 = "SELECT * FROM view_popular_products_1";
			Connection conn2 = DBConnection.getConnection();
			PreparedStatement stmt2 = conn2.prepareStatement(sql2);

			// execute SQL command and record results
			ResultSet results2 = stmt2.executeQuery();

			// iterate through ResultSet
			while (results2.next()) {
				Row currRow = new Row(results2.getString(2), results2.getInt(3));
				totalSalesList.add(currRow);
			}
			DBConnection.disconnect(conn2);
			return totalSalesList;

		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getYTDTransactions() {
		int numOfTransactions = 0;

		try {
			String sql = "SELECT COUNT(*) FROM inventory.view_invoice_customer_1 WHERE orderTimestamp BETWEEN '2021-01-01' AND '2021-12-31'";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet results = stmt.executeQuery();

			results.next();
			numOfTransactions = results.getInt(1);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return numOfTransactions;
	}

	public double getYTDRevenue() {
		double ytdRevenue = 0.00;

		try {
			String sql = "SELECT SUM(totalPaid) FROM inventory.view_invoice_customer_1 WHERE orderTimestamp BETWEEN '2021-01-01' AND '2021-12-31'";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet results = stmt.executeQuery();

			results.next();
			ytdRevenue = results.getDouble(1);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return ytdRevenue;
	}


	public double getTodaysRevenue() {
		double todaysRevenue = 0.00;

		try {
			String sql = "SELECT SUM(totalPaid) FROM inventory.view_invoice_customer_1 WHERE orderTimestamp >= CURDATE() " +
					"AND orderTimestamp < CURDATE() + INTERVAL 1 DAY";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet results = stmt.executeQuery();

			results.next();
			todaysRevenue = results.getDouble(1);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return todaysRevenue;
	}


	/**
	 * Get list of all products in the database
	 * @return arrayList of all the products
	 */
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> productsList = new ArrayList<>();
		
		Product product = null;
		
		try {
			String sql = "SELECT * FROM view_products_1";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();
			
			// iterate through ResultSet
			while (results.next()) {
				product = new Product(results.getString(2), results.getString(3), results.getInt(4),
						results.getDouble(5), results.getString(6), results.getString(7));

				productsList.add(product);
			}
			
			DBConnection.disconnect(conn);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return productsList;
	}


	/**
	 * Get list of all subcategories from database
	 * @return list of subcategories containing ID and name
	 */
	public ArrayList<Subcategory> getAllSubcategories() {
		ArrayList<Subcategory> subcategoryList = new ArrayList<>();
		Subcategory currSubcategory = null;

		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM view_subcategories_1";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// iterate through ResultSet, adding each subcategory to list
			while (results.next()) {
				currSubcategory = new Subcategory(results.getInt(1), results.getString(2));
				subcategoryList.add(currSubcategory);
			}
			DBConnection.disconnect(conn);

		} catch(Exception e) {
			e.printStackTrace();
		}
		return subcategoryList;
	}

	/**
	 * Get list of all manufacturers from database
	 * @return list of manufacturers containing ID and name
	 */
	public ArrayList<Manufacturer> getAllManufacturers() {
		ArrayList<Manufacturer> manufacturers = new ArrayList<>();
		Manufacturer currManufacturer;

		try {
			// prepare SQL query
			String sql = "SELECT * FROM view_manufacturers_1";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// iterate through ResultSet, adding each manufacturer to list
			while (results.next()) {
				currManufacturer = new Manufacturer(results.getInt(1), results.getString(2));
				manufacturers.add(currManufacturer);
			}
			DBConnection.disconnect(conn);

		} catch(Exception e) {
			e.printStackTrace();
		}
		return manufacturers;
	}

	public ArrayList<Order> getAllInvoiceOrders() {
		ArrayList<Order> invoiceList = new ArrayList<>();
		Order currOrder;

		try {
			// prepare SQL query
			String sql = "SELECT * FROM inventory.view_invoice_customer_1";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// iterate through ResultSet, adding each manufacturer to list
			while (results.next()) {

			}
			DBConnection.disconnect(conn);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return invoiceList;
	}
}
