package inventory.application;

import inventory.services.InventoryController;

public class Application {

	public static void main(String[] args) throws Exception {
		InventoryController controller = new InventoryController();
		controller.runApp();
	}
}
