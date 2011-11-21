import java.util.*; 
import java.io.*; 
import java.net.*;

public class DirectoryServerConnection implements Runnable
{
    private DirectoryServer _host = null; 
    Socket _clientSocket = null;
    Long _lastKeepAliveTime = new Long(0);
    
    DirectoryServerConnection(DirectoryServer host, Socket clientSocket)
    {
        _host = host;
        _clientSocket = clientSocket; 
        _lastKeepAliveTime = Calendar.getInstance().getTimeInMillis();
    } 

    public void run()
    {
        try
        {
            System.out.println("Thread for connection started.");
            
            // load in the stream data
            InputStream is = _clientSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            
            // read each line
            while ((line = br.readLine()) != null)
            {
                // AddUser request
                if (line.startsWith("<AddUser>"))
                {
                    String username = br.readLine();
                    username = username.substring(10, username.length() - 11);

                    System.out.println("\nAddUser Request");
                    System.out.println("---------------");
                    System.out.println("New user:   " + username);
                    System.out.println("IP Address: " + _clientSocket.getRemoteSocketAddress().toString().replace('/', ''));
                    
                    addUser(username);
                }
                // GetDirectory request
                else if (line.startsWith("<GetDirectory>"))
                {
                    System.out.println("\nGetDirectory Request");
                    
                    getDirectory();
                }    
                // SendCall request
                else if (line.startsWith("<SendCall>"))
                {
                    String sendIP = br.readLine();
                    sendIP = sendIP.substring(11, sendIP.length() - 12);
                    System.out.println("\nSendCall Request");
                    System.out.println("----------------");
                    System.out.println("IP Address: " + sendIP);
                }
                // AcceptCall request
                else if (line.startsWith("<AcceptCall>"))
                {
                    String acceptIP = br.readLine();
                    acceptIP = acceptIP.substring(11, acceptIP.length() - 12);
                    System.out.println("\nAcceptCall Request");
                    System.out.println("------------------");
                    System.out.println("IP Address: " + acceptIP);
                }
                // Hangup request
                else if (line.startsWith("<Hangup>"))
                {
                    String hangupIP = br.readLine();
                    hangupIP = hangupIP.substring(11, hangupIP.length() - 12);
                    System.out.println("\nHangup Request");
                    System.out.println("--------------");
                    System.out.println("IP Address: " + hangupIP);
                }
                // KeepAlive request
                else if (line.startsWith("<KeepAlive>")) 
                {
                    _lastKeepAliveTime = Calendar.getInstance().getTimeInMillis();
                    
                    System.out.println("\nKeepAlive Request");
                    System.out.println("-----------------");
                    System.out.println("Timestamp for " + _clientSocket.getRemoteSocketAddress().toString().replace('/', '') + ": " + _lastKeepAliveTime);
                }
            }
        }
        catch (IOException ex)
        {
            // TODO: handle this exception
        }
    }
    
    public void addUser(String username)
    {
        User u = new User(username, _clientSocket.getRemoteSocketAddress().toString().replace('/', ''));
        _host._directory.add(u);

        _host.sendUpdatedDirectory();
    }
    
    public void getDirectory()
    {
        try
        {
            PrintWriter out = new PrintWriter(_clientSocket.getOutputStream(), true);
                        
            out.println("<Directory>");
            for (User u : _host._directory)
            {
                out.println("<User>");
                out.println("<Username>" + u.getUsername() + "</Username>");
                out.println("<IPAddress>" + u.getIPAddress() + "</IPAddress>");
                out.println("<Status>" + u.getStatus() + "</Status>");
                out.println("</User>");
            }
            out.println("</Directory>");
        }
        catch (IOException ex)
        {
            // TODO: handle this exception
        }
    }
}

