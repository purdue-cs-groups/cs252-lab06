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

            DirectoryServerConnection target1 = null;
            for (DirectoryServerConnection c : _ds._connections)
            {
                timeDiff = currTime - c._lastKeepAliveTime;

                if (timeDiff > _checkTime)
                {
                    // kill connection
                    try
                    {
                        User target2 = null;
                        for (User u : _ds._directory)
                        {
                            if (u.getIPAddress().equals(c.getIPAddress()))
                            {
                                target2 = u;
                            }
                        }
                        
                        if (target2 != null)
                        {
                            _ds._directory.remove(target2);                            
                        }
                        
                        c._clientSocket.close();                        
                        target1 = c;
                    }
                    catch (IOException ex)
                    {
                        // TODO: handle this exception            
                    }
                    
                    System.out.println(c.getIPAddress() + " needs to die.");
                }
                else
                {
                    // connection is active
                    System.out.println(c.getIPAddress() + " can live.");
                }
            }
            
            if (target1 != null)
            {
                _ds._connections.remove(target1);                            
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

