import java.io.*; 
import java.net.*; 

public class DirectoryServer implements Runnable
{
    private int _port = 0;
    
    DirectoryServer(int port)
    {
        _port = port;
    }
    
    public void run()
    {
        System.out.println("Server started.");
        
        try
        {
            // create a socket for handling incoming requests
            ServerSocket server = new ServerSocket(_port);

            while (true)
            {
                System.out.println("Listening for conections on port " + _port + "...");
                
                // wait for an incoming connection
                Socket clientSocket = server.accept();
                
                System.out.println("Connection received.");
                
                System.out.println("Launching new thread for connection...");
                
                // create a new connection for this socket
                Connection cn = new Connection();
                cn.clientSocket = clientSocket;
                
                // launch a new thread for this connection
                Thread th = new Thread(cn);
                th.start();
            }
        }
        catch (IOException ex)
        {
            // TODO: handle this exception
        }
    }
}

