package dataAccess;

import helpers.Customer;
import helpers.DataParser;
import helpers.TimeShare;
import helpers.Unit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUIAccess extends JFrame{

	private static final long serialVersionUID = 1L;
	private static DataAccess dataAccess;
	private static JPanel resultsPanel = null;
	private static JTextArea resultTextArea = null;

	public GUIAccess() {
		// Get the data access instance
		try {
			dataAccess = DataAccess.getInstance();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		final JFrame myFrame = new JFrame("CondoShare");
		myFrame.setSize(1500,1000);
		myFrame.setLayout(new BorderLayout());
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Create the ImageIcon picture
		ImageIcon icon = new ImageIcon("beachCondo.jpg");
		final JLabel image = new JLabel(icon);
		myFrame.add(image, BorderLayout.CENTER);

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Import");
		menuBar.add(menu);

		
		// IMPORTING
		menuItem = new JMenuItem("Select File...");
		menuItem.setToolTipText("Choose the file in which you would like to read data from.");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				File file;
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));
				int returnVal = chooser.showOpenDialog(null);

				JTextArea text = new JTextArea();
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					//Parses the file for each unit, customer, and timeshare
					List<Unit> units = DataParser.parseUnit(file);
					List<Customer> customers = DataParser.parseCustomer(file);
					List<TimeShare> timeshares = DataParser.parseShares(file);

					//Adds each unit to the database and the text field
					text.setText(text.getText()+"==Unit==\n");
					try {
						for(Unit u : units){
							DataAccess.getInstance().createUnit(u);
							text.setText(text.getText()+"Parsed: "+u.toString()+'\n');
						}
					} catch (Exception e) {
						text.setText(text.getText()+"Unable to parse units!\n");
					}
					
					//Adds each customer to the database and the text field
					text.setText(text.getText()+"==Customer==\n");
					try {
						for(Customer c : customers){
							DataAccess.getInstance().createUser(c);
							text.setText(text.getText()+"Parsed: "+c.toString()+'\n');
						}
					} catch (Exception e) {
						text.setText(text.getText()+"Unable to parse customers!\n");
					}
					
					//Adds each timeshare to the database and the text field
					text.setText(text.getText()+"==Timeshare==\n");
					try {
						for(TimeShare t : timeshares){
							DataAccess.getInstance().createTimeShare(t);
							text.setText(text.getText()+"Parsed: "+t.toString()+'\n');
						}
					} catch (Exception e) {
						text.setText(text.getText()+"Unable to parse timeshares!\n");
					}
					
					//Adds the frame to the window
					myFrame.add(text, BorderLayout.CENTER);
					image.setVisible(false);
				}
			}
		});
		menu.add(menuItem);

		// Build the second menu.
		menu = new JMenu("Reports");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("List all Customers");
		menuItem.setToolTipText("Present a list of the names of people who own any timeshares sorted Alphabetically.");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				List<Customer> data = null;
				String col1 = "Last Name", col2 = "First Name", col3 = "Phone #";
		        String results = "All Timeshare Customers:\n";
		        results += String.format("%-52s %-52s %-15s \n", col1, col2, col3); //unitname, unitnumber, week#
				try{
					data = dataAccess.getAllCustomers();
					if(data != null){
						Collections.sort(data);
			        	for(Customer customer : data){
			        		results += String.format("%-52s %-52s %-15s \n", 
			        				customer.getLastName(), customer.getFirstName(), customer.getPhoneNumber());
			        	}
		        	}
				}catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, "Error in search");
					sqle.printStackTrace();}
			        
				if(resultsPanel == null || resultTextArea == null){
					resultsPanel = new JPanel();
					resultTextArea = new JTextArea();
					image.setVisible(false);
					myFrame.add(resultsPanel, BorderLayout.CENTER);
					resultsPanel.add(resultTextArea);
				}
				resultTextArea.setText(results);
			}
		});
		menu.add(menuItem);

		//Must add actionListeners to these buttons. Might only need 1 that uses switch-case
		menuItem = new JMenuItem("Unit Owners");
		menuItem.setToolTipText("Names of people who own at least that many " +
				"weeks in the particular unit.");
		menu.add(menuItem);

		menuItem = new JMenuItem("Maintenance Shares");
		menu.add(menuItem);

		menuItem = new JMenuItem("Unit Customers");
		menu.add(menuItem);

		// Displays the unit names, numbers and weeks owned by a customer
		menuItem = new JMenuItem("Customer");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JPanel namePanel = new JPanel();  
			    JTextField fNameField = new JTextField("First Name", 50);  
			    namePanel.add(fNameField);  
			    JTextField lNameField = new JTextField("Last Name", 50);  
			    namePanel.add(lNameField);  
			    int value = JOptionPane.showConfirmDialog(null, namePanel, "Enter Name to Query Customer Timeshare Information",
			    											JOptionPane.OK_CANCEL_OPTION);  
			    if (value == JOptionPane.OK_OPTION)  
			    {  
			        String fName = fNameField.getText();  
			        String lName = lNameField.getText();  
			        Customer searchCustomer = new Customer(fName, lName, null);
			        String col1 = "Unit Name", col2 = "Unit #", col3 = "Week #";
			        String results = "For Customer: " + fName + " " + lName + "\n";
			        results += String.format("%-55s %-15s %-15s \n", col1, col2, col3); //unitname, unitnumber, week#
			        List<TimeShare> data = null;
			        try{
			        	data = dataAccess.getTimeShares(searchCustomer);
			        	if(data != null){
				        	for(TimeShare share : data){
				        		results += String.format("%-55s %-15s %-15i \n", 
				        				share.getUnitName(), share.getUnitNumber(), Integer.toString(share.getWeek()));
				        	}
			        	}
			        }catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, "Error in search");
					sqle.printStackTrace();}
			        
					if(resultsPanel == null || resultTextArea == null){
						resultsPanel = new JPanel();
						resultTextArea = new JTextArea();
						image.setVisible(false);
						myFrame.add(resultsPanel, BorderLayout.CENTER);
						resultsPanel.add(resultTextArea);
					}
					resultTextArea.setText(results);

			        
			    }  
			}
		});
		menuItem.setToolTipText("Displays the unit names, numbers and weeks owned by particular customer.");
		menu.add(menuItem);

		// Names the customers that own at least one week in a particular unit
		menuItem = new JMenuItem("Unit Owners");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JPanel unitPanel = new JPanel();  
			    JTextField unitField = new JTextField("Unit Name", 50);  
			    unitPanel.add(unitField);  
			    int value = JOptionPane.showConfirmDialog(null, unitPanel, "Enter Name of Unit to query Timeshare Owners",
			    											JOptionPane.OK_CANCEL_OPTION);  
			    if (value == JOptionPane.OK_OPTION)  
			    {  
			        String unitName = unitField.getText();  
			        String col1 = "First Name", col2 = "Last Name", col3 = "Weeks Owned";
			        String results = "For Unit: " + unitName + "\n";
			        results += String.format("%-55s %-55s %-15s \n", col1, col2, col3); 
			        List<String[]> data = null;
//			        try {
//						data = dataAccess.getOwners(unitName);
//						if(data != null){
//				        	for(String[] str : data){
//				        		results += String.format("%-55s %-55s %-15s \n", str[0], str[1], str[2]);
//				        	}
//						}
//					} catch (SQLException sqle) {JOptionPane.showMessageDialog(null, "Error in search");
//					sqle.printStackTrace();}
			        
			        if(resultsPanel == null || resultTextArea == null){
						resultsPanel = new JPanel();
						resultTextArea = new JTextArea();
						image.setVisible(false);
						myFrame.add(resultsPanel, BorderLayout.CENTER);
						resultsPanel.add(resultTextArea);
					}
					resultTextArea.setText(results);        
			    }
			}
		});
		menuItem.setToolTipText("Names the people who own one or more weeks in a particular unit.");
		menu.add(menuItem);

		menuItem = new JMenuItem("Unit");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Update when method is completed
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Week Shares");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JTextArea text = new JTextArea();
				String week = JOptionPane.showInputDialog("Please input a week number (1-52)");
				try {
					List<String> customers = DataAccess.getInstance().getCustomers(Integer.parseInt(week));
					for(String s : customers){
						text.setText(text.getText()+s);
					}
				} catch (Exception e) {
					text.setText("Was unable to get a list of the customers for week: "+week+'\n');
					e.printStackTrace();
				}
				myFrame.add(text, BorderLayout.CENTER);
				image.setVisible(false);
			}
		});
		menu.add(menuItem);

		myFrame.setJMenuBar(menuBar);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}

	public static void main(String args[]){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}
		new GUIAccess();
	}
}
