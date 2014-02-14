package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

	private static final int PORT = 3333;

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(PORT);
		System.out.println("ServerSocket created");
		
		while (true) {
			System.out.println("Waiting for client connection on port " + PORT);
			Socket cs = ss.accept();
			System.out.println("Client connected");

			BufferedReader r = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			String line;
			StringBuilder uppercase_line = new StringBuilder();
			while ((line = r.readLine()) != null) {
				System.out.println("Server Received this: " + line);
				for(int i = 0; i < line.length(); i++){
					char letter = line.charAt(i);
					char uppercase_letter = Character.toUpperCase(letter);
					uppercase_line.append(uppercase_letter);
				}
				
				PrintStream ps = new PrintStream(cs.getOutputStream());
				ps.println(uppercase_line);
				
			}
			System.out.println("Client disconnected");
			r.close();
		}
	}
}
