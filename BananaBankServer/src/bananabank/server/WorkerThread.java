package bananabank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerThread extends Thread {

	Socket clientSocket;
	BananaBank bank;

	public WorkerThread(Socket cs, BananaBank b) {
		this.clientSocket = cs;
		this.bank = b;
	}

	@Override
	public void run() {
		System.out.println("WORKER" + Thread.currentThread().getId()
				+ ": Worker thread starting");
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream ps = new PrintStream(this.clientSocket.getOutputStream());
			String line;

			while ((line = r.readLine()) != null) {
				
				System.out.println("WORKER" + Thread.currentThread().getId()+ ": Received: " + line);
				
				if (line.startsWith("SHUTDOWN")) {
					bank.save("accounts.txt");
					
					ArrayList<Account> allAccounts = new ArrayList<Account>();
					allAccounts.addAll(bank.getAllAccounts());
					
					int bankBalance = 0;
					
					for (Account account : allAccounts) {
						bankBalance += account.getBalance();
					}
					ps.println(bankBalance);
					
					return;
					
				} else {
					
					String[] parsedLine = line.split("\\s+");
					
					int AmountToBeTransfered = Integer.parseInt(parsedLine[0]);
					int srcAccountNumber = Integer.parseInt(parsedLine[1]);
					int dstAccountNumber = Integer.parseInt(parsedLine[2]);
					
					Account srcA = bank.getAccount(srcAccountNumber);
					Account dstA = bank.getAccount(dstAccountNumber);
					
					if (srcA==null) {
						ps.println("Account " + srcAccountNumber + " does not exist");
					} else if (dstA==null) {
						ps.println("Account " + dstAccountNumber + " does not exist");
					} else if (srcA.getBalance() < AmountToBeTransfered) {
						ps.println("Account " + srcA.getAccountNumber() + " has insufficient funds. Current balance is " + srcA.getBalance());
					} else {
						srcA.transferTo(AmountToBeTransfered, dstA);
						ps.println(AmountToBeTransfered + " transferred from account " + srcA.getAccountNumber() + " to account " + dstA.getAccountNumber());
					}
				}
				
				
				
			}
			
			r.close();
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
