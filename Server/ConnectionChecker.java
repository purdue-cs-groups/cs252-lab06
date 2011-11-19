import java.util.*; 
import java.io.*; 
import java.net.*;

public class ConnectionChecker implements Runnable
{
    private DirectoryServer _ds = null;
    private int _sleepTime = 5000; // how often to check in ms
    private int _checkTime = 5000; // whatever is considered old in ms

    ConnectionChecker (DirectoryServer ds)
    {
        _ds = ds;
    }

    public void run()
    {
        System.out.println("ConnectionChecker is in action...");

        while (true)
        {
            System.out.println("Checking for stale threads...");
            Long currTime = Calendar.getInstance().getTimeInMillis();
            Long timeDiff = new Long(0);

            for (Map.Entry<String, DirectoryServerConnection> e : _ds._connections.entrySet())
            {
                timeDiff = currTime - e.getValue()._lastKeepAliveTime;

                if (timeDiff > _checkTime)
                {
                    // kill connection
                    try
                    {
                        e.getValue()._clientSocket.close();
                    }
                    catch (IOException ex)
                    {
                        // TODO: handle this exception            
                    }
                    
                    System.out.println(e.getKey() + " needs to die.");
                }
                else
                {
                    // connection is active
                    System.out.println(e.getKey() + " can live.");
                }
            }

            try
            {
                Thread.sleep(_sleepTime);    
            }
            catch (InterruptedException e)
            {
                // TODO: handle this exception
            }
        }
    }
}
