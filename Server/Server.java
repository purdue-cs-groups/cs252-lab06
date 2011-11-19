import java.util.*; 
import java.io.*; 
import java.net.*;

public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Starting server...");
        
        // create a new instance of the directory server
        DirectoryServer ds = new DirectoryServer(6900); 
		
		Thread t1 = new Thread(ds);        
        t1.start();

        // create a new instance of the heartbeat monitor
		ConnectionChecker cs = new ConnectionChecker(ds);
        
        Thread connectionChecker = new Thread(cs);
		connectionChecker.start();
        
        // create a new instance of the voice server
        VoiceServer vs = new VoiceServer(6901);
        
        Thread t2 = new Thread(vs);        
        t2.start();
    }
}

