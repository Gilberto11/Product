import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductDisplay extends JFrame{
	
	//navigation panel objects
	private JPanel navigationPanel;
	
	private JButton previousButton;
	private JTextField indexTextField;
	private JLabel ofLabel;
	private JTextField maxTextField;
	private JButton nextButton;
	
	// details panel objects
	private JPanel detailsPanel;
	
	private JLabel productIDLabel;
	private JTextField productIDTextField;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel priceLabel;
	private JTextField priceTextField;
	private JLabel descriptionLabel;
	private JTextField descriptionTextField;
	private JLabel quantityLabel;
	private JTextField quantityTextField;
	
	// search panel objects
	private JPanel searchPanel;
	
	//private JLabel searchPanelLabel;
	private JLabel searchProductNameLabel;
	private JTextField searchProductNameTextField;
	private JButton searchButton;
	
	//last two buttons
	private JButton browseAllButton;
	private JButton insertNewRecordButton;
	
	//stuff needed for database
	private ProductQueries products;
	private Product currentProduct; // used to hold data for the product currently on display
	private List<Product> results;
	private int numberOfEntries;
	private int currentEntryIndex;
	
	public ProductDisplay(){
		super("Display List");
		
		//params for main window
		//these methods are not prefixed with onject. - indicates they are for the main window
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setSize(400,300);
		setResizable(false);		
		
		products = new ProductQueries(); // connects to db and creates prepared statements
		
		//navigation objects for navigation panel
		navigationPanel = new JPanel();
		previousButton = new JButton("Previous");
		indexTextField = new JTextField(2);
		ofLabel = new JLabel("Of");
		maxTextField = new JTextField(2);
		nextButton = new JButton("Next");
		
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		indexTextField.setHorizontalAlignment(JTextField.CENTER);
		maxTextField.setHorizontalAlignment(JTextField.CENTER);
		maxTextField.setEditable(false);
		
		navigationPanel.add(previousButton);
		navigationPanel.add(Box.createHorizontalStrut(10)); // makes a gap between the objects on the panel
		navigationPanel.add(indexTextField);
		navigationPanel.add(Box.createHorizontalStrut(10));
		navigationPanel.add(ofLabel);
		navigationPanel.add(Box.createHorizontalStrut(10));
		navigationPanel.add(maxTextField);
		navigationPanel.add(Box.createHorizontalStrut(10));
		navigationPanel.add(nextButton);
		navigationPanel.add(Box.createHorizontalStrut(10));
		
		add(navigationPanel); // adds navigationPanel to main JFrame
		
		//initialise the objects for the details
		detailsPanel = new JPanel();
		// GridLayout settings : 5 rows, 4 columns, vertical & horizontal gap of 4
		detailsPanel.setLayout(new GridLayout(5,2,4,4));
		
		productIDLabel = new JLabel("Product ID:");
		productIDTextField = new JTextField(10);
		nameLabel = new JLabel("Product Name:");
		nameTextField = new JTextField(20);
		priceLabel = new JLabel("Price:");
		priceTextField = new JTextField(10);
		descriptionLabel = new JLabel("Description:");
		descriptionTextField = new JTextField(10);
		quantityLabel = new JLabel("Quantity:");
		quantityTextField = new JTextField(10);
		
		detailsPanel.add(productIDLabel);
		detailsPanel.add(productIDTextField);
		detailsPanel.add(nameLabel);
		detailsPanel.add(nameTextField);
		detailsPanel.add(priceLabel);
		detailsPanel.add(priceTextField);
		detailsPanel.add(descriptionLabel);
		detailsPanel.add(descriptionTextField);
		detailsPanel.add(quantityLabel);
		detailsPanel.add(quantityTextField);
		
		add(detailsPanel); // adds detailsPanel to main JFrame
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.X_AXIS));
		
		//add titled border to searchPanel
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search for Product by Name"));
		
		//searchPanelLabel = new JLabel("Product Name:");
		searchProductNameLabel = new JLabel("Product Name:");
		searchProductNameTextField = new JTextField(10);
		searchButton = new JButton("Search");
		
		searchPanel.add(Box.createHorizontalStrut(5));
		searchPanel.add(searchProductNameLabel);
		searchPanel.add(Box.createHorizontalStrut(10));
		searchPanel.add(searchProductNameTextField);
		searchPanel.add(Box.createHorizontalStrut(10));
		searchPanel.add(searchButton);
		searchPanel.add(Box.createHorizontalStrut(5));
		
		add(searchPanel);
		
		browseAllButton = new JButton("Browse All");
		insertNewRecordButton = new JButton("Insert Record");
		
		add(browseAllButton);
		add(insertNewRecordButton);
		
		//add the action listeners to buttons etc
		previousButton.addActionListener(
					new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run if someone presses previous button
							previousButtonPressed(e);
							
						}
						
					}
				);
		
		nextButton.addActionListener(
					new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run when nextBUtton pressed
							nextButtonPressed(e);
							
						}
						
					}
				);
		
		indexTextField.addActionListener(
					new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run when indexTextField is changed
							indexTextFieldChanged(e);
							
						}
						
					}
				);
		
		
		searchButton.addActionListener(
					new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run when searchButton is pressed
							searchButtonPressed(e);
						}
						
					}
				);
		
		browseAllButton.addActionListener(
					new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run when browseAllButton is pressed
							browseAllButtonPressed(e);
							
						}
						
					}
				);
		
		insertNewRecordButton.addActionListener(
					new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// code to run when someone presses insertNewRecordButton
							insertNewRecordButtonPressed(e);
							
						}
						
					}
				);
		
		// window listener - will listen for the window close event
		// use adaptor class
		addWindowListener(
					new WindowAdapter(){
						
						public void windowClosed(WindowEvent e){
							products.close();
							System.exit(0); //exit normally - no errors
						}
						
					}
				);
		
		setVisible(true); // really important to do this last in this constructor !!!!
	}
	
	public void indexTextFieldChanged(ActionEvent e){
		
		try{
			currentEntryIndex = (Integer.parseInt(indexTextField.getText())) - 1; // surrounded with brackets to ensure that conversion happens before subtraction
			
			if (numberOfEntries != 0 && currentEntryIndex < numberOfEntries && currentEntryIndex >=0){
				
				//load the requested product into the GUI				
				currentProduct = results.get(currentEntryIndex);
				
				//set text of fields
				productIDTextField.setText(currentProduct.getProductid()+"");
				nameTextField.setText(currentProduct.getProductname());
				priceTextField.setText(currentProduct.getPrice()+"");
				descriptionTextField.setText(currentProduct.getDescription());
				quantityTextField.setText(currentProduct.getQuantity()+"");
				
				maxTextField.setText(numberOfEntries+"");
				indexTextField.setText((currentEntryIndex+1)+"");								
			}
		} catch (Exception ex){
			System.out.println("Error accessing requested record");
			ex.printStackTrace();
			
		}
	}
	
	public void browseAllButtonPressed(ActionEvent e){
		
		try{
			
			results = products.getAllProducts();
			numberOfEntries = results.size();
			
			if (numberOfEntries != 0){
				//load the first product into the GUI
				currentEntryIndex = 0;// set to 0 to access first product in the list
				currentProduct = results.get(currentEntryIndex);
				
				//set text of fields
				productIDTextField.setText(currentProduct.getProductid()+"");
				nameTextField.setText(currentProduct.getProductname());
				priceTextField.setText(currentProduct.getPrice()+"");
				descriptionTextField.setText(currentProduct.getDescription());
				quantityTextField.setText(currentProduct.getQuantity()+"");
				
				maxTextField.setText(numberOfEntries+"");
				indexTextField.setText((currentEntryIndex+1)+"");
				
				previousButton.setEnabled(true);
				nextButton.setEnabled(true);				
				
			}
		} catch (Exception ex){
			System.out.println("Error accessing db from browse all");
			ex.printStackTrace();
			
		}
	}
	
	public void previousButtonPressed(ActionEvent e){
		currentEntryIndex--;
		
		// if we go back past the first record move to the last record
		if(currentEntryIndex < 0){
			currentEntryIndex = numberOfEntries-1;
		}
		indexTextField.setText((currentEntryIndex+1)+""); // enclose calculation in brackets before adding the string
		indexTextFieldChanged(e);		
	}
	
	public void nextButtonPressed(ActionEvent e){
		currentEntryIndex++;
		
		// if we go back past the first record move to the last record
		if(currentEntryIndex >= numberOfEntries){			
			currentEntryIndex = 0;		
		}
		indexTextField.setText((currentEntryIndex+1)+""); // enclose calculation in brackets before adding the string
		indexTextFieldChanged(e);		
	}
	
	public void searchButtonPressed(ActionEvent e){
		
		results = products.searchProduct(searchProductNameTextField.getText());
		
		numberOfEntries = results.size();
		
		if (numberOfEntries != 0){
			//load the first product into the GUI
			currentEntryIndex = 0;// set to 0 to access first product in the list
			currentProduct = results.get(currentEntryIndex);
			
			//set text of fields
			productIDTextField.setText(currentProduct.getProductid()+"");
			nameTextField.setText(currentProduct.getProductname());
			priceTextField.setText(currentProduct.getPrice()+"");
			descriptionTextField.setText(currentProduct.getDescription());
			quantityTextField.setText(currentProduct.getQuantity()+"");
			
			maxTextField.setText(numberOfEntries+"");
			indexTextField.setText((currentEntryIndex+1)+"");
			
			//previousButton.setEnabled(true);
			//nextButton.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(this, "No records returned.  Now displaying all records");
			browseAllButtonPressed(e);
		}
	}
	
	public void insertNewRecordButtonPressed(ActionEvent e){
		int result = products.addProduct(
						nameTextField.getText(), 
						Double.parseDouble(priceTextField.getText()), 
						descriptionTextField.getText(), 
						Double.parseDouble(quantityTextField.getText())
					);
		
		if(result == 1){
			JOptionPane.showMessageDialog(this, "Product saved successfully");
		} else {
			JOptionPane.showMessageDialog(this, "Error occurred.  Product not saved");
		}
		browseAllButtonPressed(e);
	}
	
	/*public static void main(String[] args){
		new ProductDisplay();
	}*/
	//setVisible(true);

}
