package BLL;

import java.util.ArrayList;
import java.util.HashMap;

import Models.CartProductDetails;

public class CalShippmentCharges {
	public static HashMap<Double, HashMap<Integer, Double>> calShippment(double Shipment,
			ArrayList<CartProductDetails> cpd, double orderAmount) {
		double TotalShipmentCharges = 0.0;
		HashMap<Double, HashMap<Integer, Double>> CheckOutInfo = new HashMap<>();
		HashMap<Integer, Double> Info = new HashMap<>();
		;
		for (CartProductDetails cp : cpd) {
			int individualpercent = calIndPerc(cp.getPrice() * cp.getQuantity(), orderAmount);
			double IndividualShipment = (individualpercent / 100.0) * Shipment;
			double ShipmentWithGst = ((cp.getGst() / 100.0) * IndividualShipment) + IndividualShipment;
			TotalShipmentCharges += ShipmentWithGst;
			Info.put(cp.getPid(), ShipmentWithGst);
		}
		CheckOutInfo.put(TotalShipmentCharges, Info);
		return CheckOutInfo;
	}

	private static int calIndPerc(double price, double orderAmount) {

		int individualpercent = (int) Math.ceil((price / orderAmount) * 100);
		return individualpercent;
	}

}
