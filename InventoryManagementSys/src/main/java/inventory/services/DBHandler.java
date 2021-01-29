package inventory.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import inventory.models.Product;
import inventory.models.Customer;

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
			String sql = "INSERT INTO product(ProductName, Quantity, Price) VALUES(?, ?, ?)";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, myProduct.GetProductName());
			stmt.setInt(2,  myProduct.GetQuantity());
			stmt.setDouble(3, myProduct.GetPrice());
			
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
	
	
	/**
	 * Delete product by productId
	 * @param productId
	 * @return true if successful
	 */
	public boolean deleteProduct(int productId) {
		
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "DELETE FROM product WHERE ProductId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setInt(1, productId);
			
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
			stmt.setString(1, product.GetProductName());
			stmt.setInt(2, product.GetQuantity());
			stmt.setDouble(3, product.GetPrice());
			stmt.setInt(4, product.GetProductId());
			
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
	
	/**
	 * Retrieve Product from database by productId
	 * @param productId
	 */
	public Product getProduct(int productId) {
		
		Product foundProduct = null;
		
		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM product WHERE ProductId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statment
			stmt.setInt(1, productId);
			
			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();
			
			// FIXME: check to see if the ResultSet has more than one result (potential bug)
			while (results.next()) {
				foundProduct = new Product(results.getInt(1), results.getString(2), results.getInt(3), results.getDouble(4));
			}
			
			DBConnection.disconnect(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return foundProduct;
	}
	
	/**
	 * Get list of all products in the database
	 * @return arrayList of all the products
	 */
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> productsList = new ArrayList<Product>();
		
		Product product = null;
		
		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM product";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();
			
			// iterate through ResultSet
			while (results.next()) {
				product = new Product(results.getInt(1), results.getString(2), results.getInt(3), results.getDouble(4));
				
				productsList.add(product);
			}
			
			DBConnection.disconnect(conn);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return productsList;
	}
	
	
	
	/**
	 * Insert customer into the customers table
	 * @param myCustomer
	 * @return true if 1 or more rows affected
	 */
	public boolean insertCustomer(Customer myCustomer) {
		
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "INSERT INTO customer(customerFirstName, customerLastName, customerPhone) VALUES(?, ?, ?)";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, myCustomer.GetCustomerFirstName());
			stmt.setString(2,  myCustomer.GetCustomerLastName());
			stmt.setString(3, myCustomer.GetCustomerPhone());
			
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

	/**
	 * Delete a customer from the database
	 * @param customerId
	 * @return true if 1 or more rows affected
	 */
	public boolean deleteCustomer(int customerId) {
		
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "DELETE FROM customer WHERE customerId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setInt(1, customerId);
			
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

	/**
	 * Update a customer in the database
	 * @param updatedInfo - new info
	 * @return true if 1 or more rows affected
	 */
	public boolean updateCustomer(Customer updatedInfo) {
		boolean result = false;
		
		try {
			// parameterize SQL statement to stop SQL injections
			String sql = "UPDATE customer SET CustomerFirstName = ?, CustomerLastName = ?, CustomerPhone = ? WHERE CustomerId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// insert values into prepared statement
			stmt.setString(1, updatedInfo.GetCustomerFirstName());
			stmt.setString(2, updatedInfo.GetCustomerLastName());
			stmt.setString(3, updatedInfo.GetCustomerPhone());
			stmt.setInt(4, updatedInfo.GetCustomerId());
			
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


	/**
	 * Retrieve customer from database by customerId
	 * @param customerId
	 */
	public Customer getCustomer(int customerId) {

		Customer foundCustomer = null;

		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM customer WHERE customerId = ?";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// insert values into prepared statment
			stmt.setInt(1, customerId);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// FIXME: check to see if the ResultSet has more than one result (potential bug)
			while (results.next()) {
				foundCustomer = new Customer(results.getInt(1), results.getString(2), results.getString(3), results.getString(4));
			}

			DBConnection.disconnect(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return foundCustomer;
	}


	/**
	 * Get list of all customers in the database
	 * @return arrayList of all the customers
	 */
	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customerArrayList = new ArrayList<Customer>();

		Customer customer = null;

		try {
			// parameterize SQL statement to deter SQL injection attacks
			String sql = "SELECT * FROM customer";
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			// execute SQL command and record results
			ResultSet results = stmt.executeQuery();

			// iterate through ResultSet
			while (results.next()) {
				customer = new Customer(results.getInt(1), results.getString(2), results.getString(3), results.getString(4));

				customerArrayList.add(customer);
			}

			DBConnection.disconnect(conn);


		} catch(Exception e) {
			e.printStackTrace();
		}

		return customerArrayList;
	}


}
