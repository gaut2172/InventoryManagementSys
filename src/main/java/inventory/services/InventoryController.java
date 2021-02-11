package inventory.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import inventory.models.Customer;
import inventory.models.Product;
import inventory.models.User;

/**
 * Controller for inventory system FIXME: change this comment to more accurate description once done
 * @author gaut2172
 *
 */
public class InventoryController {
	
	private Scanner myObj = new Scanner(System.in);
	private DBHandler handler = new DBHandler();
	private Authorizer authorizer = new Authorizer();
	
	/**
	 * Add a given product to the database
	 */
//	public void addProduct(User user) {
//		//FIXME: add UI so I'm not using console
//
//		final String ACTION = "create";
//
//
////		System.out.println("user: " + user.GetUsername() + "...ACTION: " + ACTION);
//		// confirm authorization
//		if (authorizer.IsAuthorized(user, ACTION)) {
//
//			System.out.println("Enter Product Name: ");
//			String productName = myObj.nextLine();
//			System.out.println("Enter Product Quantity: ");
//			int quantity = 0;
//			try {
//				quantity = Integer.parseInt(myObj.nextLine());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("Enter Product Price");
//			double price = 0;
//			try {
//				price = Double.parseDouble(myObj.nextLine());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// create Product object
//			Product newProduct = new Product(productName, quantity, price);
//
//			// Add Product object to the database
//			if (handler.insertProduct(newProduct)) {
//				System.out.println("Product successfully saved to database!");
//			}
//			else {
//				System.out.println("Failed to save product to database");
//			}
//		}
//	}
	
	/**
	 * Delete given product from database
	 */
	public void deleteProduct(User user) throws Exception {
		
		final String ACTION = "delete";
		
		// confirm authorization
		if (authorizer.IsAuthorized(user, ACTION)) {
			
		
			System.out.print("Enter product ID you wish to delete: ");
			int id = 0;
			try {
				id = Integer.parseInt(myObj.nextLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println("Are you sure you wish to delete Product with ID of " + id + "? Answer type YES to confirm.");
			String answer = myObj.nextLine();
			
			if (answer.equalsIgnoreCase("yes")) {
				// search database for productId
				if (handler.deleteProduct(id)) {
					System.out.println("Product with ID of " + id + " has been deleted.");
				}
				else {
					System.out.println("Something went wrong, product with ID of " + id + " was not deleted");
				}
			}
			else {
				//FIXME print this to the user after GUI
				System.out.println("answer was: " + answer);
				System.out.println("Returning to main menu...");
				return;
			}
		}
	}
	
	/**
	 * Update a product in the database using productId
	 */
//	public void updateProduct(User user) throws Exception {
//
//		final String ACTION = "update";
//
//		// confirm authorization
//		if (authorizer.IsAuthorized(user, ACTION)) {
//
//
//
//			Product foundProduct = null;
//
//			try {
//				System.out.println("Enter product ID you wish to update: ");
//				int id = 0;
//				try {
//					id = Integer.parseInt(myObj.nextLine());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println();
//
//				// find product in the database
//				foundProduct = handler.getProduct(id);
//
//				if (foundProduct == null) {
//					System.out.println("Product ID was not found in the database.");
//					return;
//				}
//
//				System.out.println("\nProduct ID: " + foundProduct.GetProductId());
//				System.out.println("Product name: " + foundProduct.GetProductName());
//				System.out.println("Product quantity: " + foundProduct.GetQuantity());
//				// FIXME: format as currency
//				System.out.println("Product price: " + foundProduct.GetPrice());
//
//				// get new values
//				myObj.nextLine();
//				System.out.println();
//				System.out.print("Enter new product name: ");
//				String newName = myObj.nextLine();
//				System.out.println();
//
//				System.out.print("Enter new product quantity: ");
//				int newQuantity = 0;
//				try {
//					newQuantity = Integer.parseInt(myObj.nextLine());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println();
//
//				System.out.print("Enter new product price: ");
//				BigDecimal newPrice = 0;
//				try {
////					newPrice = BigDecimal.parse(myObj.nextLine());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println();
//
//				foundProduct.SetProductName(newName);
//				foundProduct.SetQuantity(newQuantity);
//				foundProduct.SetPrice(newPrice);
//
//				// update database with new product values
//				if (handler.updateProduct(foundProduct)) {
//					System.out.println("Product was successfully updated in the database.");
//				}
//				else {
//					System.out.println("Product has not been updated");
//				}
//
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	/**
	 * Display product table from database
	 */
	public void displayInventory(User user) {
		
		final String ACTION = "read";
		
		// confirm authorization
		
		if (authorizer.IsAuthorized(user, ACTION)) {
			
			// retrieve list
			ArrayList<Product> productList = handler.getAllProducts();
			
			System.out.println("\n                   AVAILABLE PRODUCTS:\n");
			System.out.println(String.format("%-12s %-32s %-18s %10s", "Product ID", "Product Name", "Quantity", "Price"));
			
			// print out list
			for(Product p: productList) {
				System.out.println(p);
			}
		}
	}
	
	/**
	 * Add a customer to database
	 */
	public void addCustomer(User user) {
		
		final String ACTION = "create";
		
		// confirm authorization
		if (authorizer.IsAuthorized(user, ACTION)) {
			System.out.println("Enter customer's first name: ");
			String firstName = myObj.nextLine();
			System.out.println("Enter customer's last name: ");
			String lastName = myObj.nextLine();
			System.out.println("Enter customer's phone number: ");
			// FIXME: parse into valid phone number format
			String phoneNum = myObj.nextLine();
			
			// create Customer object
			Customer newCustomer = new Customer(firstName, lastName, phoneNum);
			
			// Add Customer object to the database
			if (handler.insertCustomer(newCustomer)) {
				System.out.println("Customer successfully saved to database!");
			}
			else {
				System.out.println("Failed to save customer to database.");
			}
		}
	}
	
	/**
	 * Delete customer from database
	 * @param user
	 */
	public void deleteCustomer(User user) {
		
		final String ACTION = "delete";
		
		// confirm authorization
		if (authorizer.IsAuthorized(user, ACTION)) {
			
			System.out.print("Enter the ID of the customer you wish to delete: ");
			int id = 0;
			try {
				id = Integer.parseInt(myObj.nextLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println("Are you sure you wish to delete customer with ID of " + id + "? Type yes or no.");
			String answer = myObj.nextLine();
			System.out.println("myObj: " + answer);
			if (answer.equalsIgnoreCase("yes")) {
				// search database for customerId
				if (handler.deleteCustomer(id)) {
					System.out.println("Customer with ID of " + id + " has been deleted.");
				}
				else {
					System.out.println("Something went wrong, customer with ID of " + id + " was not deleted.");
				}
			}
			else {
				//FIXME print this to the user after GUI
				System.out.println("Returning to main menu...");
				return;
			}
		}
	}


	/**
	 * Update a customer in the database using customerId
	 */
	public void updateCustomer(User user) throws Exception {

		final String ACTION = "update";

		// confirm authorization
		if (authorizer.IsAuthorized(user, ACTION)) {

			Customer foundCustomer = null;

			try {
				System.out.println("Enter Customer ID you wish to update: ");
				int id = 0;
				try {
					id = Integer.parseInt(myObj.nextLine());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println();

				// find customer in the database
				foundCustomer = handler.getCustomer(id);

				if (foundCustomer == null) {
					System.out.println("Customer ID was not found in the database.");
					return;
				}
				else {
					System.out.println("Customer found...");
				}

				System.out.println("\nCustomer ID: " + foundCustomer.GetCustomerId());
				System.out.println("Customer name: " + foundCustomer.GetCustomerFullName());
				System.out.println("Customer phone number: " + foundCustomer.GetCustomerPhone());

				// get new values
				System.out.println();
				System.out.print("Enter new first name for customer: ");
				String newFirstName = myObj.nextLine();
				System.out.println();

				System.out.print("Enter new last name for customer: ");
				String newLastName = myObj.nextLine();
				System.out.println();

				System.out.print("Enter new phone number for customer: ");
				String newPhoneNum = myObj.nextLine();
				System.out.println();

				foundCustomer.SetCustomerFirstName(newFirstName);
				foundCustomer.SetCustomerLastName(newLastName);
				foundCustomer.SetCustomerPhone(newPhoneNum);

				// update database with new product values
				if (handler.updateCustomer(foundCustomer)) {
					System.out.println("Customer was successfully updated in the database.");
				}
				else {
					System.out.println("Customer has not been updated");
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Display customer table from database
	 */
	public void displayCustomers(User user) {

		final String ACTION = "read";

		// confirm authorization

		if (authorizer.IsAuthorized(user, ACTION)) {

			// retrieve list
			ArrayList<Customer> customerList = handler.getAllCustomers();

			System.out.println("\n                   CUSTOMER LIST:\n");
			System.out.println(String.format("%-30s %-30s %-30s %-30s", "Customer ID", "First Name", "Last Name", "Phone Number"));

			// print out list
			for(Customer c: customerList) {
				System.out.println(c);
			}
		}
	}
	
	
	
	/**
	 * Print console menu and get user input
	 * @return user's choice input
	 * @throws Exception
	 */
	private int menu() throws Exception {
		
		System.out.println("\n");
		System.out.println("Inventory Management System");
		System.out.println("1. Add new Product");
		System.out.println("2. Delete a Product");
		System.out.println("3. Update a Product");
		System.out.println("4. Display all the Products");
		System.out.println("5. Add new Customer");
		System.out.println("6. Delete a Customer");
		System.out.println("7. Update a Customer");
		System.out.println("8. Display all the Customers");
		
		System.out.println("9. Quit");
		
		int choice = 0;
		do {
			System.out.print("Enter an option: ");
			try {
				choice = Integer.parseInt(myObj.nextLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (choice < 1 || choice > 9);
		
		System.out.println();
		return choice;
	}
	
	/**
	 * Prompt user to login
	 * @return
	 */
	private User login() {
		User authenticatedUser = null;
		
		try {
			System.out.println("Welcome to the inventory management system.");
			System.out.println("\nPlease enter your username: ");
			String username = myObj.nextLine();
			System.out.println("\nPlease enter your password: ");
			String pwd = myObj.nextLine();
			
			MiddleLogin middleLogin = new MiddleLogin();
			User checkIfAuthenticated = middleLogin.authenticate(username, pwd);
			
			if (checkIfAuthenticated != null) {
				authenticatedUser = checkIfAuthenticated;
			}
			}catch (Exception e) {
				e.printStackTrace();
		}
		return authenticatedUser;
	}
	
	/**
	 * Method to run the application
	 * @throws Exception
	 */
	public void runApp() throws Exception{
		int choice;
		int attempts = 5;
		User user = null;
		
		for (int i = 0; i < 5; i++) {
			user = login();
			if (user != null) {
				break;
			}
			attempts--;
			System.out.println("\nLogin unsuccessful, please try again. (" + attempts + ") attempt(s) left.\n");
		}
		
		if (user != null) {
		
			do {
				// FIXME: change text based menu to GUI
				// show user menu
				choice = menu();
				System.out.println();
				
	
				switch (choice) {
				case 1:
					// add new product
//					addProduct(user);
					break;
				case 2:
					// delete product
					deleteProduct(user);
					break;
				case 3:
//					// update a product
//					updateProduct(user);
					break;
				case 4:
					// display all products
					displayInventory(user);
					break;
				case 5:
//					// add new customer
					addCustomer(user);
					break;
				case 6:
//					// delete customer
					deleteCustomer(user);
					break;
				case 7:
//					// update a customer
					updateCustomer(user);
					break;
				case 8:
//					// display all customers
					displayCustomers(user);
					break;
				case 9:
					System.out.println("Goodbye");
					break;
				}
				
			} while (choice != 9);
		}
		else {
			System.out.println("Something went wrong...");
		}
	}
}
