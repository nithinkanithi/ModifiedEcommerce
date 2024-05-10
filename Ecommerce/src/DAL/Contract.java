package DAL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Models.CartProductDetails;
import Models.CheckoutProductDetails;
import Models.OrderDetails;
import Models.Products;

public interface Contract {
	ArrayList<Products> getProd() throws SQLException;

	ArrayList<String> getCat() throws SQLException;

	ArrayList<Products> getCatProd(String catVal) throws SQLException;

	ArrayList<Products> getAvalaibleProd(int pid, int pincode) throws SQLException;

	ArrayList<CartProductDetails> getCartProd(HashMap<Integer, Integer> idqu) throws SQLException;

	OrderDetails createOrder(double totalPrice);

	void createOrderProducts(ArrayList<CartProductDetails> userProds, OrderDetails ord);

	double getShipmentCharges(double orderAmount);

	ArrayList<CheckoutProductDetails> getCheckOutProductDetails(HashMap<Integer, Integer> idqu,
			HashMap<Integer, Double> checkOutInfoIndividual);
}
