package helpers;

public class TimeShre {

	private String firstName;
	private String lastName;
	private String unitName;
	private String unitNumber;
	private String week;
	
	
	public TimeShre(String firstName, String lastName, String unitName,
			String unitNumber, String week) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.unitName = unitName;
		this.unitNumber = unitNumber;
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
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
	
	
	
	
}
