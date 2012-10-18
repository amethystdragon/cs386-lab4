package dataAccess;

import helpers.Customer;
import helpers.TimeShare;
import helpers.Unit;

import java.sql.SQLException;
import java.util.List;

public interface DataAccessInterface {
	///////////////////////////////////////////////////////////////////////////
	//Get Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * All customers (by last, first)
	 * @return a list of all customers 
	 */
	public List<Customer> getAllCustomers() throws SQLException;
	/**
	 * Names of people who own at least that many weeks in the particular unit.
	 * @param name - unit number
	 * @param number - unit name
	 * @param weeks - week number
	 * @return
	 */
	public List<Customer> unitOwners(String name, String number, int weeks) throws SQLException;
	/**
	 * Share of the maintenance for the unit	
	 * @param name
	 * @param number
	 * @return
	 */
	public int getMaintenanceShares(String name, String number) throws SQLException;
	/**
	 * Names of the people who own one or more weeks
	 * @param unit
	 * @return - gets the customers who own at least one share of the unit
	 */
	public List<Customer> getUnitCustomers(Unit unit) throws SQLException;
	/**
	 * Unit names, numbers, and week numbers that that person owns
	 * @param customer
	 * @return - time shares the customer owns
	 */
	public List<TimeShare> getTimeShares(Customer customer) throws SQLException;
	/**
	 * Display the owners in that unit sorted by week number
	 * @param unitName
	 * @return - lists of the owners of the unit
	 */
	public List<Customer> getOwners(String unitName) throws SQLException;
	/**
	 * Display who owns each unit during that week
	 * @param week
	 * @return what is owned by who during the week specified
	 */
	public List<String> getCustomers(int week) throws SQLException;
	
	///////////////////////////////////////////////////////////////////////////
	//Creation Methods
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * Creates a new user in the database
	 * @param customer
	 * @return - true if successful, false if not
	 */
	public boolean createUser(Customer customer) throws SQLException;
	/**
	 * Create a new unit in the database
	 * @param unit
	 * @return - true if successful, false if not
	 */
	public boolean createUnit(Unit unit) throws SQLException;
	/**
	 * Create a new time share in the database
	 * @param timeshare
	 * @return - true if successful, false if not
	 */
	public boolean createTimeShare(TimeShare timeshare) throws SQLException;	
}
