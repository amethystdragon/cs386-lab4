package dataAccess;

import helpers.Customer;
import helpers.TimeShare;
import helpers.Unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccess implements DataAccessInterface {
	/**
	 * Stores the connection to the MySQL server
	 */
	private static Connection connection = null;
	/**
	 * MySQL MM JDBC driver
	 */
	private String driverName = "com.mysql.jdbc.Driver";
	/**
	 * Stores the database name to connect to.
	 */
	private String mydatabase = "Timeshares";
	/**
	 * Stores the password to connect to the database with
	 */
	private String password = "R78933jGtCKAWczM";
	/**
	 * Stores the server name
	 */
	private String serverName = "localhost";
	/**
	 * Stores and executes MySQL statements
	 */
	private Statement stmt = null;
	/**
	 * Stores the URL for java.sql to use to connect to the server.
	 */
	private String url;
	/**
	 * Stores the username to connect to the database with
	 */
	private String username = "TimeshareUser";

	private static DataAccess instance;

	/**
	 * Main constructor. Makes a connection to the MySQL server and initializes
	 * the statement.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private DataAccess() throws ClassNotFoundException, SQLException {
		// Stores the credentials
		url = "jdbc:mysql://" + serverName + "/" + mydatabase;
		try {
			// Load the JDBC driver
			Class.forName(driverName);
			// Create a connection to the database
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(
					"Was not able to load the MySQL DB Connector. Check the build path.",
					e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(
					"Was not able to connect to the database. Check network connections.",
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() {
		try {
			connection.close();
			connection = null;
			url = "";
			url = null;
		} catch (SQLException e) {
		}
	}

	/**
	 * Executes a query on the statement passed in
	 * 
	 * @param query
	 *            - String statement of a MySQL query that is executed
	 * @return - If successful, ResultSet of the executes query, else returns
	 *         null.
	 * @throws SQLException
	 */
	private synchronized boolean execute(String query) throws SQLException {
		try {
			// Executes the passed in query
			stmt = connection.createStatement();
			stmt.execute(query);
			System.out.println(query);
			return true;
		} catch (SQLException e) {
			System.err.println("Was unable to execute query: " + query);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Executes a query on the statement passed in
	 * 
	 * @param query
	 *            - String statement of a MySQL query that is executed
	 * @return - If successful, ResultSet of the executes query, else returns
	 *         null.
	 * @throws SQLException
	 */
	private synchronized ResultSet query(String query) throws SQLException {
		try {
			// Executes the passed in query
			stmt = connection.createStatement();
			System.out.println(query);
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Was unable to query the database with query: "
					+ query);
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getAllCustomers()
	 */
	@Override
	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		ResultSet query;
		try {
			query = query("SELECT `FirstName`,`LastName`,`PhoneNumber` FROM `Customer`");
			while (query.next()) {
				customers.add(new Customer(query.getString(1), query
						.getString(2), query.getString(3)));
			}
		} catch (SQLException e) {
			System.err.println("Could not get the list of users");
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#availableUnits(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public List<Customer> unitOwners(String name, String number, int weeks)
			throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		ResultSet query;
		try {
			query = query("SELECT `FirstName`,`LastName`,`PhoneNumber` FROM Customer WHERE `CustomerID` IN ("
					+ "SELECT CustomerID FROM `Schedule` WHERE `UnitID`= "
					+ "(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='"
					+ name
					+ "' AND `UnitNumber`='"
					+ number
					+ "'"
					+ ") GROUP BY `Schedule`.`CustomerID` HAVING COUNT(Week) > "
					+ weeks + ")");
			while (query.next()) {
				customers.add(new Customer(query.getString(1), query
						.getString(2), query.getString(3)));
			}
		} catch (SQLException e) {
			System.err.println("Could not get the list of users");
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dataAccess.DataAccessInterface#getMaintenanceShares(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int getMaintenanceShares(String name, String number)
			throws SQLException {
		ResultSet query;
		try {
			query = query("SELECT `MaintenenceShare` FROM `Unit` WHERE `UnitName`='"
					+ name + "' AND `UnitNumber`='" + number + "'");
			query.next();
			return Integer.parseInt(query.getString(1));
		} catch (SQLException e) {
			System.err.println("Could not get the list of users");
			e.printStackTrace();
			return -1;
		}
	}

	/*
	 * Malcolm (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getUnitCustomers(helpers.Unit)
	 */
	@Override
	public List<Customer> getUnitCustomers(Unit unit) throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		ResultSet query;
		try {
			//TODO FIX
			query = query("SELECT `FirstName`,`LastName` FROM Customer WHERE `CustomerID` IN ("
					+ "SELECT CustomerID FROM `Schedule` WHERE `UnitID`= "
					+ "(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='"
					+ unit.getUnitName()
					+ "' AND `UnitNumber`='"
					+ unit.getUnitNumber()
					+ "'"
					+ ") GROUP BY `Schedule`.`CustomerID` HAVING COUNT(Week) > 0)");
			while (query.next()) {
				customers.add(new Customer(query.getString(1), query
						.getString(2), null));
			}
		} catch (SQLException e) {
			System.err.println("Could not get the list of users");
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	/*
	 * Will (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getTimeShares(helpers.Customer)
	 */
	@Override
	public List<TimeShare> getTimeShares(Customer customer) throws SQLException {
		List<TimeShare> timeShares = new ArrayList<TimeShare>();
		ResultSet query;
		try {
			query = query("SELECT u.UnitName, u.UnitNumber, s.Week FROM `Unit` u RIGHT JOIN " +
					"(SELECT `UnitID`, `Week` FROM Schedule WHERE `CustomerID`= " +
					"(SELECT `CustomerID`FROM `Customer` WHERE `FirstName`='"+customer.getFirstName()+
					"' AND `LastName`='"+customer.getLastName()+"')" +
					") s ON u.`UnitID`=s.`UnitID`;");

			while (query.next()) {
				timeShares.add(new TimeShare(customer, 
						new Unit(query.getString(1), query.getString(2), 0, 0, 0),
						Integer.parseInt(query.getString(3))));
			}
		} catch (SQLException e) {
			System.err.println("Error in timeshare query for customer: "
					+ customer);
			e.printStackTrace();
			timeShares = null;
		}
		return timeShares;
	}

	/*
	 * Joe (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getOwners(helpers.Unit)
	 */
	@Override
	public List<String[]> getOwners(String unitName) throws SQLException {
		List<String[]> info = new ArrayList<String[]>();
		ResultSet query;
		try {
			query = query("select distinct c.FirstName, c.LastName, count(c.LastName) from Customer c where c.CustomerID IN " +
					"(select s.CustomerID from Schedule s where s.UnitID IN (select u.UnitID from Unit u where u.unitName like '"
					+ unitName + "'));");
			while (query.next()) {
				info.add(new String[] { query.getString(1), query.getString(2),
						query.getString(3) });
			}
		} catch (SQLException e) {
			System.err.println("Could not get the list of customers owning "
					+ "this unit: " + unitName);
			e.printStackTrace();
			return null;
		}
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getCustomers(int)
	 */
	@Override
	public List<String> getCustomers(int week) throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSet query = query("SELECT `FirstName`,`LastName`,`UnitName`,`UnitNumber` FROM Schedule s "
				+ "INNER JOIN Customer c, Unit u WHERE s.UnitID=u.UnitID AND s.CustomerID=c.CustomerID AND `Week`="
				+ week);
		while (query.next()) {
			result.add(String.format("%s, %s - %s: %s", query.getString(2),
					query.getString(1), query.getString(3), query.getString(4)));
		}
		return result;
	}

	// ///////////////////////////////////////////////////////////////
	// Creators
	// ///////////////////////////////////////////////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#createUser(helpers.Customer)
	 */
	@Override
	public boolean createUser(Customer customer) throws SQLException {
		ResultSet query = query("SELECT COUNT(`FirstName`) FROM `Customer` WHERE `FirstName`='"
				+ customer.getFirstName()
				+ "' AND `LastName`='"
				+ customer.getLastName() + "'");
		query.next();
		// Checks to see if the customer exists
		if (Integer.parseInt(query.getString(1)) > 0)
			return false;
		return execute("INSERT INTO `Customer` (`FirstName`,`LastName`,`PhoneNumber`) VALUES ('"
				+ customer.getFirstName()
				+ "','"
				+ customer.getLastName()
				+ "','" + customer.getPhoneNumber() + "')");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#createUnit(helpers.Unit)
	 */
	@Override
	public boolean createUnit(Unit unit) throws SQLException {
		ResultSet query = query("SELECT COUNT(`UnitName`) FROM `Customer` WHERE `UnitName`='"
				+ unit.getUnitName()
				+ "' AND `UnitNumber`='"
				+ unit.getUnitNumber() + "'");
		query.next();
		// Checks to see if the unit already exists
		if (Integer.parseInt(query.getString(1)) > 0)
			return false;
		return execute("INSERT INTO `Unit` (`UnitName`,`UnitNumber`,`MaxWeeks`,"
				+ "`AnualMaintenenceCost`,`MaintenenceShare`) VALUES ('"
				+ unit.getUnitName()
				+ "','"
				+ unit.getUnitNumber()
				+ "','"
				+ unit.getMaxWeeks()
				+ "','"
				+ unit.getAnualMaintenenceCost()
				+ "','" + unit.getMaintenenceShare() + "')");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#createTimeShare(helpers.TimeShare)
	 */
	@Override
	public boolean createTimeShare(TimeShare timeshare) throws SQLException {
		// Checks to see if the user already has more then the max number of
		// weeks allowed
		ResultSet shares = query("SELECT COUNT(`Week`) FROM `Schedule`  WHERE `CustomerID`="
				+ "(SELECT `CustomerID` FROM `Customer` WHERE `FirstName`='First 1' AND `LastName`='Last 1') "
				+ "AND `UnitID`=(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='Comstock' AND `UnitNumber`='11')");
		ResultSet max = query("SELECT `MaxWeeks` FROM `Unit` WHERE `UnitName`='Comstock' AND `UnitNumber`='11'");
		shares.next();
		max.next();
		if (Integer.parseInt(shares.getString(1)) >= Integer.parseInt(max
				.getString(1)))
			return false;

		// Checks to see if the unit is already owned for that week
		shares = query("SELECT COUNT(`Week`) FROM `Schedule`  WHERE "
				+ "`UnitID`=(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='Comstock' AND `UnitNumber`='11') AND `Week`="
				+ timeshare.getWeek());
		shares.next();
		if (Integer.parseInt(shares.getString(1)) != 0)
			return false;

		// Add the timeshare
		return execute("INSERT INTO `Schedule` (`UnitID`,`CustomerID`,`Week`) VALUES ("
				// Gets the unit id from the database
				+ "(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='"
				+ timeshare.getUnitName()
				+ "' AND `UnitNumber`='"
				+ timeshare.getUnitNumber()
				+ "'),"
				// Gets the customer id from the database
				+ "(SELECT `CustomerID` FROM `Customer` WHERE `FirstName`='"
				+ timeshare.getFirstName()
				+ "' AND `LastName`='"
				+ timeshare.getLastName() + "')," + timeshare.getWeek() + ")");
	}

	/**
	 * Singleton method to get the instance of the database access
	 * 
	 * @return
	 */
	public static synchronized DataAccess getInstance()
			throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new DataAccess(); // Create the instance
		return instance;
	}
}
