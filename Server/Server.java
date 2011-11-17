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
        
        // create a new instance of the ringer server
        // TODO: RingerServer rs = new RingerServer(6901);    
        
        // TODO: Thread t2 = new Thread(rs);        
        // TODO: t2.start();
        
        // create a new instance of the voice server
        // TODO: VoiceServer vs = new VoiceServer(6902);
        
        // TODO: Thread t3 = new Thread(vs);        
        // TODO: t3.start();
    }
}

