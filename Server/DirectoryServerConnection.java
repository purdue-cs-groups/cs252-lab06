import java.io.*; 
import java.net.*; 

public class DirectoryServerConnection implements Runnable
{
    private DirectoryServer _host = null; 
    private Socket _clientSocket = null;
    
    DirectoryServerConnection(DirectoryServer host, Socket clientSocket)
    {
        _host = host;
        _clientSocket = clientSocket; 
    } 

    public void run()
    {
        try
        {
            System.out.println("Thread for connection started.");
            
            // load in the stream data
            InputStream is = _clientSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // read each line
            String line;
            while ((line = br.readLine()) != null)
            {
                parseStreamData(line);
            }
        }
        catch (IOException ex)
        {
            // TODO: handle this exception
        }
    }
    
    public void parseStreamData(String line)
    {
        System.out.println("RECV: " + line);
    }
}

