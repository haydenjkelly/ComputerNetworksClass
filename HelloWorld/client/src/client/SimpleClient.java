package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {
	
	private static final int PORT = 3333;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", PORT);
		System.out.println("Client is connected to the server");
		PrintStream ps = new PrintStream(s.getOutputStream());
		ps.println("lowercase string");
		
		
		String line;
		BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while ((line = r.readLine()) != null) {
			System.out.println("Client Received this: " + line);
		}
		ps.close();
	}
}