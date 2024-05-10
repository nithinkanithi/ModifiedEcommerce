package DAL;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import Models.CartProductDetails;
import Models.CheckoutProductDetails;
import Models.OrderDetails;
import Models.Products;

public class DalLayer implements Contract {
	Connection con = JDBCConn.getJDBCConn();

	public ArrayList<Products> getProd() throws SQLException {
		ArrayList<Products> al = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call getAllProd()}");
		ResultSet rs = cs.executeQuery();

		while (rs.next()) {
			Products p = new Products(rs.getString("pname"), rs.getString("image"), rs.getDouble("price"),
					rs.getInt("pid"));
			al.add(p);
		}

		rs.close();
		cs.close();
		return al;
	}

	public ArrayList<String> getCat() throws SQLException {
		ArrayList<String> al = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call getAllCat()}");
		ResultSet rs = cs.executeQuery();

		while (rs.next()) {
			al.add(rs.getString("catigory"));
		}

		rs.close();
		cs.close();
		return al;
	}

	public ArrayList<Products> getCatProd(String catVal) throws SQLException {
		int val = Integer.parseInt(catVal);
		ArrayList<Products> al = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call getAllProd(?)}");
		cs.setInt(1, val);
		ResultSet rs = cs.executeQuery();

		while (rs.next()) {
			Products p = new Products(rs.getString("pname"), rs.getString("image"), rs.getDouble("price"),
					rs.getInt("pid"));
			al.add(p);
		}

		rs.close();
		cs.close();
		return al;
	}

	@Override
	public ArrayList<Products> getAvalaibleProd(int pid, int pincode) throws SQLException {
		ArrayList<Products> al = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{ ? = call CheckProductServiceable(?, ?) }");
		cs.registerOutParameter(1, Types.BOOLEAN);
		cs.setInt(2, pid);
		cs.setInt(3, pincode);
		cs.execute();
		boolean isServiceable = cs.getBoolean(1);
		if (isServiceable) {
			CallableStatement cs1 = con.prepareCall("{call getAvailableProd(?)}");
			cs1.setInt(1, pid);
			ResultSet rs = cs1.executeQuery();

			while (rs.next()) {
				Products p = new Products(rs.getString("pname"), rs.getString("image"), rs.getDouble("price"),
						rs.getInt("pid"));
				al.add(p);
			}
			return al;
		} else
			return null;
	}

	public ArrayList<CartProductDetails> getCartProd(HashMap<Integer, Integer> idqu) throws SQLException {
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> qu = new ArrayList<>();
		for (int i : idqu.keySet()) {
			ids.add(i);
			qu.add(idqu.get(i));
		}
		ArrayList<CartProductDetails> al = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call getCartProd(?)}");
		Array array = con.createArrayOf("Integer", ids.toArray(new Integer[0]));

		cs.setArray(1, array);

		ResultSet rs = cs.executeQuery();
		while (rs.next()) {
			int productId = rs.getInt("pid");
			int quantity = qu.get(ids.indexOf(productId)); // Retrieve quantity using productId
			CartProductDetails p = new CartProductDetails(productId, rs.getDouble("price"), rs.getInt("gst"), quantity);
			al.add(p);
		}
		return al;
	}

	public OrderDetails createOrder(double totalPrice) {

		OrderDetails ord = null;
		try {
			CallableStatement cs = con.prepareCall("{call createorder(?,?,?)}");
			cs.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			cs.setDouble(2, totalPrice);
			cs.setInt(3, 1234);
			ResultSet rst = cs.executeQuery();
			rst.next();
			ord = new OrderDetails(rst.getInt(1), rst.getDate(2), rst.getDouble(3), rst.getInt(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ord;
	}

	public void createOrderProducts(ArrayList<CartProductDetails> userProds, OrderDetails ord) {
		try {
			CallableStatement cs = con.prepareCall("{call createOrderProduct(?,?,?,?)}");
			for (CartProductDetails ordprod : userProds) {
				cs.setInt(1, ord.getOrderid());
				cs.setInt(2, ordprod.getPid());
				cs.setInt(3, ordprod.getQuantity());
				cs.setDouble(4, ((ordprod.getGst() / 100.0) * ordprod.getPrice())
						+ (ordprod.getPrice() * ordprod.getQuantity()));
				cs.addBatch();
			}
			cs.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getShipmentCharges(double orderAmount) {
		double shippingAmount = 0;
		try {
			String sql = "SELECT orvl_shippingamount FROM ShippmentCharges_192 WHERE ? BETWEEN orvl_from AND orvl_to";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setDouble(1, orderAmount);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			shippingAmount = rs.getDouble("orvl_shippingamount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shippingAmount;
	}

	@Override
	public ArrayList<CheckoutProductDetails> getCheckOutProductDetails(HashMap<Integer, Integer> idqu,
			HashMap<Integer, Double> checkOutInfoIndividual) {
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Integer> qu = new ArrayList<>();
		ArrayList<Double> ShipmentCharges = new ArrayList<>();
		for (int i : idqu.keySet()) {
			ids.add(i);
			qu.add(idqu.get(i));
		}
		for (int i : checkOutInfoIndividual.keySet()) {
			ShipmentCharges.add(checkOutInfoIndividual.get(i));
		}
		ArrayList<CheckoutProductDetails> al = new ArrayList<>();
		CallableStatement cs;
		try {
			cs = con.prepareCall("{call getCheckOutProd(?)}");
			Array array = con.createArrayOf("Integer", ids.toArray(new Integer[0]));

			cs.setArray(1, array);

			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				int productId = rs.getInt("pid");
				int quantity = qu.get(ids.indexOf(productId));
				Double GST = ((rs.getInt("gst") / 100.0) * (rs.getDouble("price") * quantity));
				Double discount = (rs.getInt("Discount_percentage") / 100.0) * (rs.getDouble("price") * quantity);
				double Charges = ShipmentCharges.get(ids.indexOf(productId));
				CheckoutProductDetails p = new CheckoutProductDetails(productId, rs.getString("pname"), quantity, GST,
						discount, Charges, rs.getDouble("price"));
				al.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return al;
	}

}
