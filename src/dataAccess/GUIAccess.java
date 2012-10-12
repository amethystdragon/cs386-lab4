package trussm;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class GUIAccess extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public GUIAccess(){
		
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		JFrame myFrame = new JFrame("This is my frame");
		myFrame.setSize(300,400);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Reports");
		menu.addSeparator();
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
		
		menuItem = new JMenuItem("Customer");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Unit");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Week Shares");
		menu.add(menuItem);
		
		myFrame.setJMenuBar(menuBar);
		myFrame.setVisible(true);
	}
	public static void main(String args[]){
		
		GUIAccess gui = new GUIAccess();
	}

}
