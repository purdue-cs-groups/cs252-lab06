import java.util.*; 
import java.io.*; 
import java.net.*;

public class VoiceServer implements Runnable
{
    private int _port = 0;
    Map<String, VoiceServerConnection> _connections = null;
    
    VoiceServer(int port)
    {
        _port = port;
        _connections = new HashMap<String, VoiceServerConnection>();
    }

    public void run()
    {
        System.out.println("Voice Server started.");
        
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
                VoiceServerConnection cn = new VoiceServerConnection(this, clientSocket);                
                _connections.put(clientSocket.getInetAddress().getHostAddress(), cn);
                                
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

