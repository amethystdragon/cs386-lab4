package dataAccess;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import junit.framework.Assert;

import helpers.Customer;
import helpers.TimeShare;
import helpers.Unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataAccessTest {

	DataAccessInterface connector;
	@Before
	public void setUp() throws Exception {
		connector = DataAccess.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		connector = null;
	}

	@Test
	public void testGetAllCustomers() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnitOwners() {
		try {
			Assert.assertEquals(3, connector.unitOwners("Comstock", "123", 1).size());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create the Timeshare");
		}
	}

	@Test
	public void testGetMaintenanceShares() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnitCustomers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTimeShares() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOwners() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetCustomers() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUser() {
		try {
			connector.createUser(new Customer("Testy", "McTester", "555-555-5555"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create the user");
		}
	}

	@Test
	public void testCreateUnit() {
		try {
			connector.createUnit(new Unit("Comstock", "123", 2, 25, 4));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create the Unit");
		}
	}

	@Test
	public void testCreateTimeShare() {
		try {
			connector.createTimeShare(new TimeShare(new Customer("Testy", "McTester", "555-555-5555"), new Unit("Comstock", "123", 2, 25, 4), 5));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create the Timeshare");
		}
	}
}
