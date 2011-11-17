import java.util.*; 
import java.io.*; 
import java.net.*;

public class User
{
    private String _username;
    private String _ipAddress;
    
    public User(String username, String ipAddress)
    {
        _username = username;
        _ipAddress = ipAddress;
    }
    
    public String getUsername()
    {
        return _username;
    }
    
    public String getIPAddress()
    {
        return _ipAddress;
    }
}

