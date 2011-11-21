import java.util.*;
import java.net.*;
import java.io.*;

/*
 * @class DirectoryClient
 * opens a connection to the server
 * writes lines of a XML file to the server
 */
public class DirectoryClient {
	
	private int ipAddr;
	private int port = 6900;
	private Socket socket;
	
	public DirectoryClient() {
		ipAddr = 0;
		socket = null;
	}
	
	/** 
	 * opens a connection to the server
	 * @return true if connection is established
	 * @return false if connection is refused
	 */
	public boolean openConnection() {
		InetAddress addr = InetAddress.getByAddress(ipAddr);			
		try {
			System.out.println("Opening a socket to the server......");
			socket = new Socket(addr, port);		
		} catch (UnknownHostException u) {
			System.out.println("Unknown Host Exception: " + u.getMessage());
			return false;
		} catch (IOException i) {
			System.out.println("IO Exception: " + i.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * adds a user to the server
	 * @return true if the user is added
	 * @return false if otherwise or if socket is not initialized
	 */
	public boolean addUserToServer(String username) {
		// if the connection to the server is not opened, return false;
		if (socket == null) return false; 
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			//TODO: make username generic @ <Username> .. </Username>
			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("<AddUser>");
			bw.newLine();
			bw.write("<Username>" + username + "</Username>");
			bw.newLine();
			bw.write("</AddUser>");
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
			return false;
		} finally {
			os.close();
			bw.close();
		}
		return true;
	}
	
	/**
	 * gets a set of XML lines from the server, and prints the results out on the 
	 * Android device accordingly
	 * @return true if no errors
	 * @return false if an exception is thrown
	 */
	public boolean getDirectory() {
		if (socket == null) return false;
		InputStream is = null;
		BufferedReader br = null;
		String line;
		// TODO: print accordingly to user
		try {
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			// read the line
        	while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.startsWith("<Username>")) {
					
				}
				else if (line.startsWith("<IPAddress>")) {
					
				}
				else if (line.startsWith("<Status>")) {
					
				}
			}
		} catch (Exception e) {
			return false;
		} finally {
			is.close();
			br.close();
		}
		return true;
	}
	
	/**
	 * sends a phone call request to the server
	 * @param contactIpAddr the IP address of the contact
	 */ 
	public boolean sendCallToServer(int contactIpAddr) {
		if (socket == null) return false; 
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("<SendCall>");
			bw.write(contactIpAddr);
			bw.write("</SendCall");
		} catch (Exception e) {
			return false;
		} finally {
			os.close();
			bw.close();
		}
		return true;
	}
	
	/**
	 * sends a request to the server to terminate the phone call
	 * @param contactIpAddr of the 'other' user
	 */
	public boolean hangUpToServer(int contactIpAddr) {
		if (socket == null) return false;
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("<HangUp>");
			bw.write(contactIpAddr);
			bw.write("</HangUp>");
		} catch (Exception e) {
			return false;
		} finally {
			os.close();
			bw.close();
		}
		return true;
	}
	
	
}