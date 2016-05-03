
public class Product {
	//class matches Product table on db
	private int productid;
	private String productname;
	private double price;
	private String description;
	private double quantity;

	public Product(){
		
	}
	
	public Product(int productid, String productname, double price, String description, double quantity){
		setProductid(productid);
		setProductname(productname);
		setPrice(price);
		setDescription(description);
		setQuantity(quantity);
	}
	
	public int getProductid(){
		return productid;
	}
	
	public void setProductid(int productid){
		this.productid = productid;
	}
	
	public String getProductname(){
		return productname;
	}
	
	public void setProductname(String productname){
		this.productname = productname;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public double getQuantity(){
		return quantity;
	}
	
	public void setQuantity(double quantity){
		this.quantity = quantity;
	}
	
	@Override
	public String toString(){
		return String.format(
				"Product Id: %f\n"
				+ "Product Name: %-30s"
				+ "Price: %f"
				+ "Description: %-80s"
				+ "Quantity: %f",
				getProductid(),
				getProductname(),
				getPrice(),
				getDescription(),
				getQuantity());
	}
	
}
