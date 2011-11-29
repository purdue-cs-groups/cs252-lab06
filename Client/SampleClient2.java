import java.util.*;
import java.net.*;
import java.io.*;

public class SampleClient2
{    
    public static void main(String[] args)
    {
          try
            {
                DirectoryClient dc = new DirectoryClient("lore.cs.purdue.edu");
                dc.connect();
            }
            catch (Exception ex)
            {
	            System.out.printf("Error!!!");
            }
    }
}


