package ie.gmit.sw;

public class Club {
	//Variables
	private String name;
	private String email;
	private String clubID;
	private double fundsAvailable;
	
	//Constructor
	public Club(String name, String clubID, String email, double fundsAvailable) {
		super();
		this.name = name;
		this.clubID = clubID;
		this.email = email;
		this.fundsAvailable = fundsAvailable;
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClubID() {
		return clubID;
	}
	
	public void setClubID(String clubID) {
		this.clubID = clubID;
	}
	
	public double getFundsAvailable() {
		return fundsAvailable;
	}
	
	public void setFundsAvailable(double fundsAvailable) {
		this.fundsAvailable = fundsAvailable;
	}	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	

}
