package dataAccess;

import helpers.Customer;
import helpers.TimeShare;
import helpers.Unit;

import java.util.List;

public interface DataAccessInterface {
	///////////////////////////////////////////////////////////////////////////
	//Get Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * All customers (by last, first)
	 * @return a list of all customers 
	 */
	public List<Customer> getAllCustomers();
	/**
	 * Names of people who own at least that many weeks in the particular unit.
	 * @param name - unit number
	 * @param number - unit name
	 * @param weeks - week number
	 * @return
	 */
	public List<Unit> availableUnits(String name, String number, int weeks);
	/**
	 * Share of the maintenance for the unit	
	 * @param name
	 * @param number
	 * @return
	 */
	public int getMaintenanceShares(String name, String number);
	/**
	 * Names of the people who own one or more weeks
	 * @param unit
	 * @return - gets the customers who own at least one chare of the unit
	 */
	public List<Customer> getUnitCustomers(Unit unit);
	/**
	 * Unit names, numbers, and week numbers that that person owns
	 * @param customer
	 * @return - time shares the customer owns
	 */
	public List<TimeShare> getTimeShares(Customer customer);
	/**
	 * Display the owners in that unit sorted by week number
	 * @param unit
	 * @return - lists of the owners of the unit
	 */
	public List<Customer> getOwners(Unit unit);
	/**
	 * Display who owns each unit during that week
	 * @param week
	 * @return what are owned by who durn the week specified
	 */
	public List<Unit> getCustomers(int week);
	///////////////////////////////////////////////////////////////////////////
	//Creation Methods
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * Creates a new user in the database
	 * @param customer
	 * @return - true if sucessful, false if not
	 */
	public boolean createUser(Customer customer);
	/**
	 * Create a new unit in the database
	 * @param unit
	 * @return - true if sucessful, false if not
	 */
	public boolean createUnit(Unit unit);
	/**
	 * Create a new time share in the database
	 * @param timeshare
	 * @return - true if sucessful, false if not
	 */
	public boolean createTimeShare(TimeShare timeshare);	
	

}
