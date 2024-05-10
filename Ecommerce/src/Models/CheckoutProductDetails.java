package Models;

public class CheckoutProductDetails {
	private int pid;
	private String pname;
	private int quantity;
	private double GST;
	private double Discount;
	private double ShipmentCharges;
	private double FinalPrice;

	public CheckoutProductDetails(int pid, String name, int quantity, Double GST, double Discount,
			double ShipmentCharges, double price) {
		this.setPid(pid);
		setPname(name);
		this.setQuantity(quantity);
		this.setGST(GST);
		this.setDiscount(Discount);
		this.setShipmentCharges(ShipmentCharges);
		setFinalPrice((price * quantity) + ShipmentCharges - Discount);
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getGST() {
		return GST;
	}

	public void setGST(Double gST) {
		GST = gST;
	}

	public double getDiscount() {
		return Discount;
	}

	public void setDiscount(double discount) {
		Discount = discount;
	}

	public double getShipmentCharges() {
		return ShipmentCharges;
	}

	public void setShipmentCharges(double shipmentCharges) {
		ShipmentCharges = shipmentCharges;
	}

	public double getFinalPrice() {
		return FinalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		FinalPrice = finalPrice;
	}
}
