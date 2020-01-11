package ie.gmit.sw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

//Server
public class Server {
	public static void main(String[] args) {
		ServerSocket listener;
		int clientid = 0;
		try {
			listener = new ServerSocket(10000, 10);

			while (true) {
				System.out.println("Main thread listening for incoming new connections");
				Socket newconnection = listener.accept();

				System.out.println("New connection received and spanning a thread");
				Connecthandler t = new Connecthandler(newconnection, clientid);
				clientid++;
				t.start();
			}

		}

		catch (IOException e) {
			System.out.println("Socket isn't opened");
			e.printStackTrace();
		}
	}

}

class Connecthandler extends Thread {

	Socket individualconnection;
	int socketid;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	int result;

	String userCategory;
	String clubName;
	String clubIdIn;
	String agentNameIn;
	String agentIdIn;
	String username;
	String passport;
	String clubbName;
	String clubId;
	String clubEmail;
	String name;
	String id;
	double fundsAvailable;
	String agentName;
	String agentId;
	String agentEmail;
	boolean loggedIn;
	boolean registered;
	int choice;
	String loginOrReg;

	ArrayList<Integer> arrPlayerId = new ArrayList<Integer>();
	ArrayList<String> arrClubId = new ArrayList<String>();
	ArrayList<String> arrClubName = new ArrayList<String>();
	ArrayList<String> arrUsername = new ArrayList<String>();
	ArrayList<String> arrId = new ArrayList<String>();
	ArrayList<Club> club = new ArrayList<Club>();
	ArrayList<Agent> agent = new ArrayList<Agent>();
	ArrayList<Player> player = new ArrayList<Player>();

	public Connecthandler(Socket s, int i) {
		individualconnection = s;
		socketid = i;
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendMessage(int msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendMessage(boolean msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void run() {

		try {

			out = new ObjectOutputStream(individualconnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(individualconnection.getInputStream());
			System.out.println("Connection" + socketid + " from IP address " + individualconnection.getInetAddress());

			Scanner console = new Scanner(System.in);
			Scanner fileS = new Scanner(new File("logindetails.txt"));

			// fills login array's
			while (fileS.hasNext()) {
				arrUsername.add(fileS.next());
				arrId.add(fileS.next());
			}

			loginOrReg = loginChoice();

			// Login
			if (loginOrReg.equalsIgnoreCase("a")) {
				userCategory = clubOrAgent();
				login(userCategory);
			} else if (loginOrReg.equalsIgnoreCase("b")) {
				userCategory = clubOrAgent();
				register(userCategory);
			}

			if (loggedIn) {
				if (userCategory.equalsIgnoreCase("a")) {
					sendMessage("----- Club Menu ----;");
				} else if (userCategory.equalsIgnoreCase("b")) {
					sendMessage("----- Agent Menu ------");
					choice = agentMenu();
					sendMessage(choice);

					if (choice == 1) {
						addPlayers();
					}
				}
			}
			console.close();
			fileS.close();
			
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			try {
				out.close();
				in.close();
				individualconnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void addPlayers() {
		int plr = arrPlayerId.size() + 1;
		arrPlayerId.add(plr);
		
		//Adds Player
		Player player1 = new Player("Ronaldo", 34 ,plr, "C123", "A123", 5000, "For Sale", "ATT");
		player.add(player1);
		
		sendMessage(player1.getName() + player1.getPlayerId());
	}

	private int agentMenu() {
		int choice=0;
		sendMessage("Please enter: \n1) Add a player \n2) Update a player's value \n3) Update player's status" );
		
		try {
			choice = (int) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
			
		return choice;
	}

	private void login(String category) throws ClassNotFoundException, IOException {
		loggedIn = false;
		
		while (loggedIn == false) {
			sendMessage("Please enter Name: ");
			name = (String)in.readObject();
			sendMessage("Please enter Id: ");
			id = (String)in.readObject();
			
			for (int i=0; i < arrUsername.size(); i++) {
				if (name.equals(arrUsername.get(i))) {
					loggedIn = true;
				}
			}	//	end of for()
			sendMessage(loggedIn);
		}	//	end of while()
		
	}	//	end of login()

	private void register(String category) throws IOException {
		FileWriter fileW = new FileWriter("loginFile.txt", true);
		BufferedWriter bw = new BufferedWriter(fileW);
		
		registered = false;
		
		while (registered == false) {
			if(category.equalsIgnoreCase("a")) {
				clubRegister();
			} 
			else if (category.equalsIgnoreCase("b")) {
				try {
					agentRegister();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			registered = checkRegistrationDetails(name, id);
			
			sendMessage(registered);
			
			if (registered == false) {
				sendMessage("Name/ Id already in use");
			}
		}
		arrUsername.add(name);
		arrId.add(id);
		
		bw.write("\n" + name + "\n" + id);
		sendMessage("Thank you, You have Registered successfully!");
		
		if(category.equalsIgnoreCase("a")) {
			sendMessage("Name: " + name + "\nId: "+ id+ "\nEmail: "
					+ clubEmail + "\n Available funds: " + fundsAvailable);
			
			addClub(id, name, clubEmail, fundsAvailable);
		} 
		else if (category.equalsIgnoreCase("b")) {
			sendMessage("Name: " + name + "\nId: " + id + "\nEmail :" + agentEmail);
		}
		
		bw.close();
		fileW.close();
		
		try {
			login(category);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Check for new registration details
	private boolean checkRegistrationDetails(String rName, String rId) {

		boolean unique = true;
		
		for (int i = 0; i < arrUsername.size(); i++) {
			if (name.equals(arrUsername.get(i))) {
				unique = false;
			}
		}
		
		for (int i = 0; i < arrId.size(); i++) {
			if (id.equals(arrId.get(i))) {
				unique = false;
			}
		}
		
		return unique;
		
	}	//	end of checkReg()

	private void addClub(String cId, String cName, String cEmail, double cfundsAvailable2) {
		
		club.add(new Club(cId, cName, cEmail, cfundsAvailable2));
	
	}

	//Register a new Agent
	private void agentRegister() throws ClassNotFoundException, IOException {
		sendMessage("Enter Agent Name:");
		name = (String)in.readObject();
		sendMessage("Enter Agent ID:");
		id = (String)in.readObject();
		sendMessage("Enter Agent Email:");
		agentEmail = (String)in.readObject();
		
	}	//	end of registerAgent()

	//Register a new Club
	private void clubRegister() {
		try {
			sendMessage("Enter Club name: ");
			name = (String)in.readObject();
			sendMessage("Enter Club Id:"); // check
			id = (String)in.readObject();
			sendMessage("Enter Club e-mail:"); 
			clubEmail = (String)in.readObject();
			sendMessage("Enter Funds Available for club:"); 
			fundsAvailable = (double) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//	Checks whether user is a Club or Agent
	private String clubOrAgent() {
		String choice="";
		
		while(!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
			sendMessage("Please enter:  a ) for Club or\n  b) for Agent");
			try {
				choice = (String)in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}	//	end of while()
		
		return choice;
	}
	
	//	Checks if user wants to log in or register
	private String loginChoice() {
		String choice="";
		
		while(!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
			sendMessage("Please enter:  a ) to login or\n  b) to register ");
			try {
				choice = (String)in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return choice;
	}

}	//	end of Server
