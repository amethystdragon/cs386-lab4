package dataAccess;

import helpers.Customer;
import helpers.TimeShare;
import helpers.Unit;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
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

		// TODO For importing
		// Didn't know if we wanted something like this, so I just added it in
		// We can change this later if we want
		menuItem = new JMenuItem("Select File...");
		menuItem.setToolTipText("Choose the file in which you would like to read data from.");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				File file;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);

				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					file = chooser.getSelectedFile();
					int length = file.getName().length();
					if(file.getName().substring(length-3).matches("txt")) {
						// TODO
						System.out.println("Success!");
					} else {
						JOptionPane.showMessageDialog(null, "Invalid File Type");
					}
				}
			}
		});
		menu.add(menuItem);

		// Build the second menu.
		menu = new JMenu("Reports");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("List of Customers");
		menuItem.setToolTipText("Present a list of the names of people who own at least the " +
				"selected number of weeks in the particular unit.");
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
		menuItem.setArmed(true);
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
			        try {
						data = dataAccess.getOwners(unitName);
						if(data != null){
				        	for(String[] str : data){
				        		results += String.format("%-55s %-55s %-15s \n", str[0], str[1], str[2]);
				        	}
						}
					} catch (SQLException sqle) {JOptionPane.showMessageDialog(null, "Error in search");
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
		menuItem.setToolTipText("Names the people who own one or more weeks in a particular unit.");
		menuItem.setArmed(true);
		menu.add(menuItem);
		

		menuItem = new JMenuItem("Week Shares");
		menu.add(menuItem);

		myFrame.setJMenuBar(menuBar);
		//myFrame.pack();
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
