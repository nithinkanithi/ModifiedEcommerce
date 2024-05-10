package Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DAL.Contract;
import DAO.DAOBridge;
import Models.Products;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String product_details = request.getParameter("product_id");
		JSONObject ob = new JSONObject(product_details);
		int pid = 0;
		int pincode = 0;
		boolean flag = false;
		Contract c = DAOBridge.getDalObj();
		for (String key : ob.keySet()) {
			pid = Integer.parseInt(key);
			pincode = ob.getInt(key);
		}
		ArrayList<Products> products = new ArrayList<>();
		try {
			products = c.getAvalaibleProd(pid, pincode);
			ArrayList<ArrayList<String>> all = new ArrayList<>();

			for (Products it : products) {
				flag = true;
				ArrayList<String> ls = new ArrayList<>();
				ls.add(it.getImgurl());
				ls.add(it.getProdname());
				ls.add(it.getPrice().toString());
				ls.add(String.valueOf(it.getPid()));
				all.add(ls);

			}
			if (!flag)
				ob.put("Products", "false");
			else
				ob.put("Products", all);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().write(ob.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
