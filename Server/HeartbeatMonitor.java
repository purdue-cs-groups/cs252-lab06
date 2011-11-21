import java.util.*; 
import java.io.*; 
import java.net.*;

public class HeartbeatMonitor implements Runnable
{
    private DirectoryServer _ds = null;
    private int _sleepTime = 5000; // how often to check in ms
    private int _checkTime = 15000; // whatever is considered old in ms

    HeartbeatMonitor (DirectoryServer ds)
    {
        _ds = ds;
    }

    public void run()
    {
        System.out.println("HeartbeatMonitor is in action...");

        while (true)
        {
            System.out.println("Checking for stale threads...");
            Long currTime = Calendar.getInstance().getTimeInMillis();
            Long timeDiff = new Long(0);

            Map.Entry<String, DirectoryServerConnection> target1 = null;
            for (Map.Entry<String, DirectoryServerConnection> e : _ds._connections.entrySet())
            {
                timeDiff = currTime - e.getValue()._lastKeepAliveTime;

                if (timeDiff > _checkTime)
                {
                    // kill connection
                    try
                    {
                        User target2 = null;
                        for (User u : _ds._directory)
                        {
                            if (u.getIPAddress().equals(e.getValue()._clientSocket.getRemoteSocketAddress().toString().substring(1)))
                            {
                                target2 = u;
                            }
                        }
                        
                        if (target2 != null)
                        {
                            _ds._directory.remove(target2);                            
                        }
                        
                        e.getValue()._clientSocket.close();                        
                        target1 = e;
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
            
            if (target1 != null)
            {
                _ds._connections.remove(target1.getKey());                            
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
