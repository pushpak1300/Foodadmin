import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class trial{

	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/foodadmin";
	static final String USER = "root";
	static final String PASS = "";

	String sql;
	final List prodlist=new List(15,false);		//to display product list

	Connection conn=null;
	Statement stmt=null;

	public trial(){
		prepareGUI();
	}

	public static void main(String[] args){
		trial  awtControlDemo = new trial();
		awtControlDemo.showTextFieldDemo();
	}

	private void prepareGUI(){
		mainFrame = new Frame("Java AWT Examples");
		mainFrame.setSize(500,800);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {		//close, minimise and maxim
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    
		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();        
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350,100);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);  
	}

	private void showTextFieldDemo(){
		headerLabel.setText("Control in action: TextField"); 		//Header string

		Label  ssn= new Label("SSN", Label.RIGHT);
		Label  name = new Label("Name ", Label.CENTER);
		final TextField ssn1 = new TextField(6);
		final TextField name1 = new TextField(6);
		Label  contact= new Label("Contact number", Label.RIGHT);
		Label  product = new Label("Product Name", Label.CENTER);
		final TextField contact1 = new TextField(6);
		final TextField product1 = new TextField(6);
		Label  price= new Label("Quantity ", Label.RIGHT);
		final TextField price1 = new TextField(6);
		Label  quantity= new Label("Price ", Label.RIGHT);
		final TextField quantity1 = new TextField(6);

		Button insert = new Button("Insert");


		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);	//Connection Establish
			stmt = conn.createStatement();
		} catch (Exception e2) {			//to handle all types of exceptions
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}		//locate driver


		insert.addActionListener(new ActionListener() {		//add the delete button
			public void actionPerformed(ActionEvent e) {   
				try {
					sql = "INSERT INTO `customer`(`SSN`, `Name`, `Contact`) VALUES ("+ssn1.getText()+",'"+name1.getText()+"',"+contact1.getText()+")";
					stmt.executeUpdate(sql);
					sql="INSERT INTO `product`(`SSN`, `ProductName`, `Quantity`, `Price`) VALUES ("+ssn1.getText()+",'"+product1.getText()+"',"+quantity1.getText()+","+price1.getText()+")";
					stmt.executeUpdate(sql);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				statusLabel.setText("Data Inserted");        
			}
		}); 

		Button delete = new Button("Delete");
		delete.addActionListener(new ActionListener() {		//add the delete button
			public void actionPerformed(ActionEvent e) {   
				try {
					sql="DELETE FROM `product` WHERE ssn="+ssn1.getText();
					stmt.executeUpdate(sql);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				statusLabel.setText("Data Inserted");        
			}
		}); 
		Button fetch = new Button("fetch/refresh");

		fetch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
				String sql="SELECT * FROM product,customer where product.ssn=customer.ssn";
				ResultSet set;
				try {
					set=stmt.executeQuery(sql);
					while(set.next()){
						int ssnd=set.getInt("ssn");
						String prodd=set.getString("ProductName");
						int quantityd=set.getInt("quantity");
						int priced=set.getInt("price");
						String named=set.getString("name");
						int contactd=set.getInt("contact");

						prodlist.add(ssnd+"---"+prodd+"---"+quantityd+"---"+priced+"---"+named+"---"+contactd);
						System.out.println(ssnd+"---"+prodd+"---"+quantityd+"---"+priced+"---"+named+"---"+contactd);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				controlPanel.add(prodlist);    
			}
		}); 

		controlPanel.add(ssn);
		controlPanel.add(ssn1);
		controlPanel.add(name);
		controlPanel.add(name1);
		controlPanel.add(contact); 
		controlPanel.add(contact1);       
		controlPanel.add(product);
		controlPanel.add(product1);
		controlPanel.add(price);
		controlPanel.add(price1);
		controlPanel.add(quantity);
		controlPanel.add(quantity1);
		controlPanel.add(insert);
		controlPanel.add(fetch);
		controlPanel.add(delete);
		mainFrame.setVisible(true);  
	}
}