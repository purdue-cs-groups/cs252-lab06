import java.io.*; 
import java.net.*; 

public class Server
{
    protected static int SERVER_PORT = 4444;

    public static void main(String[] args)
    {
        System.out.println("Starting server...");
        
        // create a new instance of the server
        DirectoryServer ds = new DirectoryServer(SERVER_PORT);
        
        // launch a new thread for this server
        Thread th = new Thread(ds);        
        th.start();
    }
}

