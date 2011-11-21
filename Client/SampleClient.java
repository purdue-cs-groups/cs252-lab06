import java.util.*;
import java.net.*;
import java.io.*;

public class SampleClient
{    
    public static void main(String[] args)
    {
        if (args.length == 2)
        {
            try
            {
                DirectoryClient dc = new DirectoryClient(args[0]);
                dc.connect();
                System.out.println("Connected to server.");
                
                dc.addUser(args[1]);
                System.out.println("User \"" + args[1] + "\" added to server.");
                
                System.out.println("Current directory:");
               
                ArrayList<User> directory = dc.getDirectory(); 
                for (User u : directory)
                {
                    System.out.println(u.getUsername() + " is " + u.getStatus() + " at " + u.getIPAddress() + ".");
                }
            }
            catch (Exception ex)
            {
                // TODO: handle this exception
            }
        }
        else
        {
            System.out.println("Usage: SampleClient <serverIPAddress> <username>");
            System.exit(0);
        }
    }
}

