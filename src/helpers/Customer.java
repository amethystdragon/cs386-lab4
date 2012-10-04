package helpers;

public class Customer {

	/**
	 * Stores the phone number of the user
	 */
	private String firstName;
	/**
	 * Stores the first name of the user
	 */
	private String lastName;
	/**
	 * Stores the phoen number of the user
	 */
	private String phoneNumber;
	
	/**
	 * Generates a user object
	 * @param first - first name of the user
	 * @param last - last name of the user
	 * @param phone - phone number of the user
	 */
	public Customer(String first, String last, String phone){
		this.firstName = first;
		this.lastName = last;
		this.phoneNumber = phone;
	}
	
	/**
	 * Gets the first name of the user
	 * @return - firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets the first name of the user
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Gets the last name of the user
	 * @return - lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets the last name of the user
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Gets the phone number of the user
	 * @return - phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Sets the phoen number of the customer
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
