package ie.gmit.sw;

public class Player {
		//Variable
		private String name;
		private int age;
		private int playerId;
		private String status;	//For sale/ Sold/ Suspended
		private String position; //Different Positions (Goalkeeper/Midfield/Attack/)
		private String clubId;
		private String agentId;
		private double value;
	
	//	Constructor
	public Player(String name, int age, int playerId, String clubId, String agentId, double valuaton, String status,
			String position) {
		super();
		this.name = name;
		this.age = age;
		this.playerId = playerId;
		this.status = status;
		this.position = position;
		this.clubId = clubId;
		this.agentId = agentId;
		this.value = valuaton;
	}

	//	Getters and Setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	public String getClubId() {
		return clubId;
	}
	
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	
	public String getAgentId() {
		return agentId;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public double getValuaton() {
		return value;
	}
	
	public void setValuaton(double valuaton) {
		this.value = valuaton;
	}
}