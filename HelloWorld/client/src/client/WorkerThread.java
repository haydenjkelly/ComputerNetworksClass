package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WorkerThread extends Thread {

	Socket clientSocket;

	public WorkerThread(Socket cs) {
		this.clientSocket = cs;
	}

	@Override
	public void run() {
		System.out.println("WORKER" + Thread.currentThread().getId()
				+ ": Worker thread starting");
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			String line;
			StringBuilder uppercase_line = new StringBuilder();

			while ((line = r.readLine()) != null) {
				System.out.println("WORKER" + Thread.currentThread().getId()+ ": Received: " + line);
				for(int i = 0; i < line.length(); i++){
					char letter = line.charAt(i);
					char uppercase_letter = Character.toUpperCase(letter);
					uppercase_line.append(uppercase_letter);
				}
				PrintStream ps = new PrintStream(this.clientSocket.getOutputStream());
				ps.println(uppercase_line);
			}
			System.out.println("WORKER" + Thread.currentThread().getId()
					+ ": Client disconnected");
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("WORKER" + Thread.currentThread().getId()
				+ ": Worker thread finished");
	}

}