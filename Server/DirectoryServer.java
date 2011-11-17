import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class DirectoryServer
{
    protected static int SERVER_PORT = 4444;

    public static void main(String[] args)
    {
        System.out.println("Starting server...");
        
        // create a new instance of the server
        Server sv = new Server();
        
        // launch a new thread for this server
        Thread th = new Thread(sv);        
        th.start();
    }

    public static class Server implements Runnable
    {
        public void run()
        {
            System.out.println("Server started.");
            
            try
            {
                // create a socket for handling incoming requests
                ServerSocket server = new ServerSocket(SERVER_PORT);

                while (true)
                {
                    System.out.println("Listening for conections on " + SERVER_PORT + "...");
                    
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
    
    public static class Connection implements Runnable
    {
        public Socket clientSocket = null;
        
        public void run()
        {
            try
            {
                System.out.println("Thread for connection started.");
                
                // load in the stream data
                InputStream is = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                // read each line
                String line;
                while ((line = br.readLine()) != null)
                {
                    System.out.println("RECV: " + line);
                }
            }
            catch (IOException ex)
            {
                // TODO: handle this exception
            }
        }
    }
}

