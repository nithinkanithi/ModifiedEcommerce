package Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import BLL.CalFare;
import BLL.CalShippmentCharges;
import DAL.Contract;
import DAO.DAOBridge;
import Models.CartProductDetails;
import Models.CheckoutProductDetails;
import Models.OrderDetails;

@WebServlet("/CheckOutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String al = request.getParameter("product_details");
		JSONObject ob = new JSONObject(al);
		Contract c = DAOBridge.getDalObj();
		HashMap<Integer, Integer> idqu = new HashMap<>();
		for (String key : ob.keySet()) {
			idqu.put(Integer.parseInt(key), ob.getInt(key));
		}
		ArrayList<CartProductDetails> cpd = null;

		try {
			cpd = c.getCartProd(idqu);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		double orderAmount = new CalFare().caluclateAmount(cpd);
		OrderDetails o = c.createOrder(orderAmount);
		c.createOrderProducts(cpd, o);

		double shipment = c.getShipmentCharges(orderAmount);
		double shipmentCharges = 0.0;
		HashMap<Integer, Double> CheckOutInfoIndividual = null;
		HashMap<Double, HashMap<Integer, Double>> CheckOutInfo = CalShippmentCharges.calShippment(shipment, cpd,
				orderAmount);
		for (Double key : CheckOutInfo.keySet()) {
			shipmentCharges = key;
			CheckOutInfoIndividual = CheckOutInfo.get(key);
		}
		ArrayList<CheckoutProductDetails> CheckOutProducts = c.getCheckOutProductDetails(idqu, CheckOutInfoIndividual);
		ArrayList<ArrayList<String>> all = new ArrayList<>();
		for (CheckoutProductDetails cp : CheckOutProducts) {
			ArrayList<String> ls = new ArrayList<>();
			ls.add(String.valueOf(cp.getPid()));
			ls.add(String.valueOf(cp.getPname()));
			ls.add(String.valueOf(cp.getQuantity()));
			ls.add(String.valueOf(cp.getGST()));
			ls.add(String.valueOf(cp.getDiscount()));
			ls.add(String.valueOf(cp.getShipmentCharges()));
			ls.add(String.valueOf(cp.getFinalPrice()));
			all.add(ls);
		}

		ob.put("products", all);
		ob.put("shipment", shipmentCharges);
		ob.put("order", o.getOtotal());
		response.getWriter().write(ob.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
