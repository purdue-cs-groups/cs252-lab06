import java.util.*; 
import java.io.*; 
import java.net.*;

public class DirectoryServer implements Runnable
{
    private int _port = 0;
    Map<String, Socket> _sockets = null;
    ArrayList<User> _directory = null;
 
    DirectoryServer(int port)
    {
        _port = port;
        _directory = new ArrayList<User>();
        _sockets = new HashMap<String, Socket>();
    }

    public void run()
    {
        System.out.println("Directory Server started.");
        
        try
        {
            // create a socket for handling incoming requests
            ServerSocket server = new ServerSocket(_port);

            while (true)
            {
                System.out.println("Listening for conections on port " + _port + "...");
                
                // wait for an incoming connection
                Socket clientSocket = server.accept();
                _sockets.put(clientSocket.getInetAddress().getHostAddress(), clientSocket);
                
                System.out.println("Connection received.");
                
                System.out.println("Launching new thread for connection...");
                
                // create a new connection for this socket
                DirectoryServerConnection cn = new DirectoryServerConnection(this, clientSocket);
                
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
    
    public void sendUpdatedDirectory()
    {
        try
        {
            for (Map.Entry<String, Socket> e : _sockets.entrySet())
            {
                PrintWriter out = new PrintWriter(e.getValue().getOutputStream(), true);
                
                out.println("<Directory>");
                for (User u : _directory)
                {
                    out.println("<User>");
                    out.println("<Username>" + u.getUsername() + "</Username>");
                    out.println("<IPAddress>" + u.getIPAddress() + "</IPAddress>");
                    out.println("</User>");
                }
                out.println("</Directory>");
                
                out.close();
            }
        }
        catch (IOException ex)
        {
            // handle this exception
        }
    }
}

