import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductQueries {
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "student";
	private static final String PASSWORD = "Password1";
	
	private Connection con;
	
	private PreparedStatement browseAll;
	private PreparedStatement search;
	private PreparedStatement insert;
	
	public ProductQueries(){
		
		try{
			//load the Oracle drivers
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (Exception e){
			System.out.println("Problem loading database driver");
			e.printStackTrace();
			System.exit(1); // 0 = all ok, any other number indicates an error
		}	
		
		try{
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (Exception e){
			System.out.println("Problem creating database connection");
			e.printStackTrace();
			System.exit(2); // 0 = all ok, any other number indicates an error
		}
		
		try{
			String sql1 = "SELECT * FROM product ORDER BY productid";
			browseAll = con.prepareStatement(sql1);
			
			String sql2 = "SELECT * FROM product WHERE productname = ?";
			search = con.prepareStatement(sql2);
			
			String sql3 = "insert into product(ProductID,ProductName,Price,Description,Quantity) values "
					+ "(productsequence.nextval,?,?,?,?)";
			insert = con.prepareStatement(sql3);
		} catch (Exception e){
			System.out.println("Problem creating database queries");
			e.printStackTrace();
			System.exit(3); // 0 = all ok, any other number indicates an error
		}
			
		
	}
	
	public List<Product> getAllProducts(){
		
		List<Product> results = null;
		ResultSet rs = null;
		
		try{
			rs = browseAll.executeQuery();
			results = new ArrayList<Product>();
			
			while (rs.next()){
				results.add(new Product(
						rs.getInt("productid"),
						rs.getString("productname"),
						rs.getDouble("price"),
						rs.getString("description"),
						rs.getDouble("quantity")
						));
			}
			
		} catch(SQLException e){
			System.out.println("Problem executing sql query - browseAll");
			e.printStackTrace();
		} finally {
			try{
				rs.close();
			} catch (SQLException e){
				System.out.println("Error closing database connection - resultset");
				e.printStackTrace();
				close();
			}
		}
		
		return results;
		
	}
	
	public List<Product> searchProduct(String n){
		
		List<Product> results = null;		
		ResultSet rs = null;
		
		try{
			search.setString(1,n);
			rs = search.executeQuery();
			results = new ArrayList<Product>();
			
			while (rs.next()){
				results.add(new Product(
						rs.getInt("productid"),
						rs.getString("productname"),
						rs.getDouble("price"),
						rs.getString("description"),
						rs.getDouble("quantity")
						));
			}
			
		} catch(SQLException e){
			System.out.println("Problem executing sql query - browseAll");
			e.printStackTrace();
		} finally {
			try{
				rs.close();
			} catch (SQLException e){
				System.out.println("Error closing database connection - resultset");
				e.printStackTrace();
				close();
			}
		}
		
		return results;
		
	}
	
	public int addProduct(String n, double p, String d, double q){
		
		int results = 0;
		
		try{
			
			insert.setString(1,n);
			insert.setDouble(2,p);
			insert.setString(3,d);
			insert.setDouble(4,q);
			
			results = insert.executeUpdate();
						
		} catch(SQLException e){
			System.out.println("Error closing database connection - resultset");
			e.printStackTrace();
			close();
		} 
		
		return results;
		
	}
	
	public void close(){
		try{
			con.close();
		} catch (Exception e){
			System.out.println("Problem closing database connection");
			e.printStackTrace(); //debug only
		}
	}

}
