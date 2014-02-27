package bananabank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {
	
	private static final int PORT = 4444;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", PORT);

		PrintStream ps = new PrintStream(s.getOutputStream());

		ps.println("50 55555 44444");
		ps.println("SHUTDOWN");
		ps.println("50 55555 44444");
		
		String line;
		BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while ((line = r.readLine()) != null) {
			System.out.println(line);
		}
		ps.close();
	}
}