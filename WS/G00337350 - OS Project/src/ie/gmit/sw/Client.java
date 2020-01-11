package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.io.ObjectInputStream;

import java.net.*;


//Start Of Client
public class Client {

	private String clubName;
	private String clubId;
	private String clubEmail;
	private Socket connection;
	private String email;
	private Scanner console;
	private String ipAddress;
	private int portAddress;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private double availableFunds;
	private String agentName;
	private String agentId;
	private String agentEmail;
	private String userCategory;
	private String loginOrRegistration;
	private boolean userLoggedIn = false;
	private int choice;

	public Client() {
		console = new Scanner(System.in);

		System.out.println("Enter the IP Address:");
		ipAddress = console.nextLine();

		System.out.println("Enter the TCP Port:");
		portAddress = console.nextInt();

	}

	public void sendEmail(Double f) {
		try {
			out.writeObject(f);
			out.flush();
			System.out.println("Client>" + f);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	public void sendEmail(String send) {
		try {
			out.writeObject(send);
			out.flush();
			System.out.println("Client>" + send);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	} 
	
	public static void main(String[] args) {
		Client temp = new Client();
		temp.ClientApp();
	}
	
	public void ClientApp() {

		try {
			connection = new Socket(ipAddress, portAddress);

			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client Side ready to communicate");

			//Client
			email = (String) in.readObject();
			System.out.println(email);
			loginOrRegistration = login();
			sendEmail(loginOrRegistration);

			//Login
			if (email.equalsIgnoreCase("a")) {
				email = (String) in.readObject();
				System.out.println(email);
				userCategory = clubOrAgent();

				sendEmail(userCategory);
				login(userCategory);

			}

			//Register
			else if (email.equalsIgnoreCase("b")) {
				email = (String) in.readObject();
				System.out.println(email);
				userCategory = clubOrAgent();
				sendEmail(userCategory);

				registration(userCategory);

			}

			email = (String) in.readObject();
			System.out.println(email);

			//Club
			if (userCategory.equalsIgnoreCase("a")) {
			
			}
			//If club isn't chose then agent will be.
			else if (userCategory.equalsIgnoreCase("b")) {
				
				choice = (int) in.readObject();
				System.out.println("Option selected = " + choice);

				email = (String) in.readObject();
				System.out.println(email);
			}

			out.close();
			in.close();
			connection.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	} // end of clientApp()

	public String login() {
		String choice;

		do {
			choice = console.next();
		} while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2"));

		return choice;
	}

	public String clubOrAgent() {
		String choice = "a";
		while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
			choice = console.next();
		}

		return choice;
	}

	public void registration(String user) throws ClassNotFoundException, IOException {

		boolean userRegstd = true;

		do {
			if (user.equals("1")) { // club
				registerClub();
			} else if (user.equals("2")) {
				registerAgent();
			}

			userRegstd = (boolean) in.readObject();
			System.out.println(userRegstd);

			if (userRegstd == false) {
				email = (String) in.readObject();
				System.out.println(email);
			}
		} while (userRegstd == false);

		email = (String) in.readObject();
		System.out.println(email);

		email = (String) in.readObject();
		System.out.println(email);

		login(user);
	} // end of register()
	public void login(String user) throws ClassNotFoundException, IOException {
		do {
			if (user.equalsIgnoreCase("a")) {
				email = (String) in.readObject();
				System.out.println(email);
				clubName = console.next();
				sendEmail(clubName);

				email = (String) in.readObject();
				System.out.println(email);
				agentId = console.next();
				sendEmail(agentId);
			} else if (user.equalsIgnoreCase("b")) {
				email = (String) in.readObject(); // agent name
				System.out.println(email);
				agentName = console.next();
				sendEmail(agentName);

				email = (String) in.readObject(); // agent id
				System.out.println(email);
				agentId = console.next();
				sendEmail(agentId);

			}
			userLoggedIn = (boolean) in.readObject();
			System.out.println(userLoggedIn + " is logged in");

		} while (userLoggedIn = false);

	} //End of login()
	
	public void registerClub() throws ClassNotFoundException, IOException {
		email = (String) in.readObject();
		System.out.println(email);
		clubName = console.next();
		sendEmail(clubName);

		email = (String) in.readObject();
		System.out.println(email);
		clubId = console.next();
		sendEmail(clubId);

		email = (String) in.readObject();
		System.out.println(email);
		clubEmail = console.next();
		sendEmail(clubEmail);

		email = (String) in.readObject();
		System.out.println(email);
		availableFunds = console.nextDouble();
		sendEmail(availableFunds);
	}

	public void registerAgent() throws ClassNotFoundException, IOException {
		email = (String) in.readObject(); // club name
		System.out.println(email);
		clubName = console.next();
		sendEmail(agentName);

		email = (String) in.readObject(); // club id
		System.out.println(email);
		clubId = console.next();
		sendEmail(agentId);

		email = (String) in.readObject(); // club email
		System.out.println(email);
		clubEmail = console.next();
		sendEmail(agentEmail);
	} 



} // end of Client class
