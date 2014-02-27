package bananabank.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MTServer {
	
	private static final int PORT = 4444;

	public static void main(String[] args) throws IOException {
		
		BananaBank bank = new BananaBank("accounts.txt");
		
		ServerSocket ss = new ServerSocket(PORT);
		System.out.println("ServerSocket created");
		
		ArrayList<WorkerThread> threads = new ArrayList<WorkerThread>();
		
		try {
		
			for(;;) {
				System.out.println("Waiting for client connection on port " + PORT);				
				Socket cs = ss.accept();
				System.out.println("Client connected");
				WorkerThread t = new WorkerThread(cs, bank);
				t.start();
				threads.add(t);
			}
		
		} catch(IOException e) {
			//..
		}
		
		//stop all worker threads
		for (WorkerThread workerThread : threads) {
			try {
				workerThread.join();
			} catch (InterruptedException e) {}
		}
		
	}

}
