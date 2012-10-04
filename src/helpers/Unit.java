package helpers;

public class Unit {
	
	private String unitName;
	private String unitNumber;
	private int maintenanceCost;
	private int maxWeeks;
	private int anualMaintenenceCost;
	private int maintenenceShare;
	
	
	
	public Unit(String unitName, String unitNumber, int maintenanceCost,
			int maxWeeks, int anualMaintenenceCost, int maintenenceShare) {
		this.unitName = unitName;
		this.unitNumber = unitNumber;
		this.maintenanceCost = maintenanceCost;
		this.maxWeeks = maxWeeks;
		this.anualMaintenenceCost = anualMaintenenceCost;
		this.maintenenceShare = maintenenceShare;
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
	public int getMaintenanceCost() {
		return maintenanceCost;
	}
	public void setMaintenanceCost(int maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}
	public int getMaxWeeks() {
		return maxWeeks;
	}
	public void setMaxWeeks(int maxWeeks) {
		this.maxWeeks = maxWeeks;
	}
	public int getAnualMaintenenceCost() {
		return anualMaintenenceCost;
	}
	public void setAnualMaintenenceCost(int anualMaintenenceCost) {
		this.anualMaintenenceCost = anualMaintenenceCost;
	}
	public int getMaintenenceShare() {
		return maintenenceShare;
	}
	public void setMaintenenceShare(int maintenenceShare) {
		this.maintenenceShare = maintenenceShare;
	}
	
	
}
