package helpers;

public class TimeShare {

	private String firstName;
	private String lastName;
	private String unitName;
	private String unitNumber;
	private int week;
	private Customer customer;
	private Unit unit;

	public TimeShare(String firstName, String lastName, String unitName,
			String unitNumber, int week) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.unitName = unitName;
		this.unitNumber = unitNumber;
		this.week = week;
	}

	public TimeShare(Customer customer, Unit unit, int week) {
		this.setCustomer(customer);
		this.setUnit(unit);
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.unitName = unit.getUnitName();
		this.unitNumber = unit.getUnitNumber();
		this.week = week;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
		this.unitName = unit.getUnitName();
		this.unitNumber = unit.getUnitNumber();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
	}
	@Override
	public String toString(){
		return customer.toString()+" - "+unit.toString();
	}
}
