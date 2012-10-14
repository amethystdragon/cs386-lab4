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
	public List<Customer> unitOwners(String name, String number, int weeks)throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		ResultSet query;
		try {
			query = query("SELECT `FirstName`,`LastName`,`PhoneNumber` FROM Customer WHERE `CustomerID` IN (" +
					"SELECT CustomerID FROM `Schedule` WHERE `UnitID`= " +
						"(SELECT `UnitId` FROM `Unit` WHERE `UnitName`='"+name+"' AND `UnitNumber`='"+number+"'" +
					") GROUP BY `Schedule`.`CustomerID` HAVING COUNT(Week) > "+weeks+")");
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
	public int getMaintenanceShares(String name, String number) throws SQLException {
		ResultSet query;
		try{
			query = query("SELECT `MaintenenceShare` FROM `Unit` WHERE `UnitName`='"+name+"' AND `UnitNumber`='"+number+"'");
			query.next();
			return Integer.parseInt(query.getString(1));
		} catch (SQLException e) {
			System.err.println("Could not get the list of users");
			e.printStackTrace();
			return -1;
		}
	}

	/* 
	 * Malcolm
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getUnitCustomers(helpers.Unit)
	 */
	@Override
	public List<Customer> getUnitCustomers(Unit unit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Will
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getTimeShares(helpers.Customer)
	 */
	@Override
	public List<TimeShare> getTimeShares(Customer customer) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Joe
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getOwners(helpers.Unit)
	 */
	@Override
	public List<Customer> getOwners(String unitName) throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		ResultSet query;
		try {
			query = query("select c.FirstName, c.LastName, c.PhoneNumber " +
					"from Customer c " +
					"where c.CustomerID = " +
					"(select s.CustomerID " +
					"from Schedule s " +
					"where s.UnitID = " +
					"(select u.UnitID from Unit u " +
					"where u.name = " + unitName + "))");
			while (query.next()) {
				customers.add(new Customer(query.getString(1),
						query.getString(2), query.getString(3)));
			}
		} catch (SQLException e) {
			System.err.println("Could not get the list of customers owning " +
					"this unit: " + unitName);
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccess.DataAccessInterface#getCustomers(int)
	 */
	@Override
	public List<Unit> getCustomers(int week) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
