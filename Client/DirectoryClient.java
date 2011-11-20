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
	 * 
	 * 
	 */
	public boolean getDirectory() {
		if (socket == null) return false;
		InputStream is = null;
		BufferedReader br = null;
		String line;
		
		try {
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			// read the line
        	while ((line = br.readLine()) != null) {
				// incomplete
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}