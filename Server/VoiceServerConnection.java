import java.util.*; 
import java.io.*; 
import java.net.*;

public class VoiceServerConnection implements Runnable
{
    private VoiceServer _host = null; 
    Socket _clientSocket = null;
    
    VoiceServerConnection(VoiceServer host, Socket clientSocket)
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

            String line;
            
            // read each line
            while ((line = br.readLine()) != null)
            {
                // do something
            }
        }
        catch (IOException ex)
        {
            // TODO: handle this exception
        }
    }
}

