import java.util.*; 
import java.io.*; 
import java.net.*;

public class ConnectionChecker implements Runnable {
	private DirectoryServer _ds = null;
	private int sleepTime = 5000; // how often to check in ms
	private int checkTime = 5000; // whatever is considered old in ms

	ConnectionChecker (DirectoryServer ds) {
		_ds = ds;
	}

	public void run() {
		System.out.println("ConnectionChecker is in action...");

		while (true) {
			System.out.println("Checking for stale threads...");
			Long currTime = Calendar.getInstance().getTimeInMillis();
			Long timeDiff = new Long(0);

			for (Map.Entry<String, Long> e : _ds._keepAliveTimes.entrySet()) {
				timeDiff = currTime - e.getValue();

				if (timeDiff > checkTime) {
					// kill connection
					try {
						_ds._connections.get(e.getKey())._clientSocket.close();
					} catch (Exception ex) {
						// TODO: Handle exception					
					}
					
					System.out.println(e.getKey() + " needs to die.");
				}
				else {
					// connection is active
					System.out.println(e.getKey() + " can live.");
				}
			}

			try {
				Thread.sleep(sleepTime);	
			} catch (Exception e) {
				// TODO: Handle exception
			}
		}
	}
}
