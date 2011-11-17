import java.io.*; 
import java.net.*; 

public class Connection implements Runnable
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

