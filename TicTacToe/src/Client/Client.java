package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import Server.ClientEndPoint;
import Server.Server;

public class Client {

	String serverAddress;
	int serverPort;
	
	//Player Data
	String myName;
	int myId;
	int myPiece;
	
	//Opponent Data
	String oppName;
	int oppId;
	int oppPiece;
	
	//Game Data
	int gameboard[];
	
	boolean turn;

	// constructor
	Client(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		
		this.myName = "";
		this.myId = 0;
		this.myPiece = 0;
		
		this.oppName = "";
		this.oppId = 0;
		this.oppPiece = 0;
		
		this.gameboard = new int[9];
		for (int i = 0; i <= 8; i++)
		{
			gameboard[i] = 0;
		}
		this.turn = false;
	}

	// start up the server
	public void start() {
		DatagramSocket socket = null;
		try {

			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			socket = new DatagramSocket();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter a unique id:");
			String id = br.readLine();
			
			this.myId = Integer.parseInt(id);
			
			System.out.print("\nEnter a unique name:");
			String name = br.readLine();
			
			this.myName = name;
			
			// send "REGISTER" to the server
			String command = "REGISTER " + " " + id + " " + name;
			DatagramPacket txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			// receive the server's response
			byte[] buf = new byte[Server.MAX_PACKET_SIZE];
			DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			socket.receive(rxPacket);
			String payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			
			// send "REGISTER" to the server
			command = "JOIN " + " " + id;
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			System.out.println("Currently finding you a worthy opponent!");
			
			// and then keep on receiving packets in an infinite loop
			while (true) {
				
				if(this.turn == true) {
					System.out.println("\nWhere would you like to make your move? ");
					
					//check if valid move
					int index = Integer.parseInt(br.readLine());
					if (index >= 0 && index <= 8 && this.gameboard[index] == 0) {
						this.gameboard[index] = this.myPiece;
					} else {
						while (this.gameboard[index] != 0 || index < 0 || index > 8)
						{
							System.out.print("\nPlease Enter a valid space. Where would you like to make your move? ");
							index = Integer.parseInt(br.readLine());
						}
					}
					
					command = "PLAY " + " " + this.myId + " " + this.oppId + " " + index;
					txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
					socket.send(txPacket);
					
					printBoard();
					this.turn = false;
				}
				
				socket.receive(rxPacket);
				payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
				
				if (payload.startsWith("GAMESTART")) {
					System.out.println("Opponent Found!");
					onGameStartRequested(payload);
				}		
				if (payload.startsWith("PLAY")) {
					onPlayRequested(payload);
				}		
				if (payload.startsWith("ENDGAME")) {
					onEndGameRequested(payload);
				}	

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket!=null && !socket.isClosed())
				socket.close();
		}
	}
	
	private void onGameStartRequested(String payload) {
		StringTokenizer st = new StringTokenizer(payload);
		st.nextToken();
		
		String piece = st.nextToken();
		int opponentId = Integer.parseInt(st.nextToken());
		String opponentName = st.nextToken();
		
		this.oppId = opponentId;
		this.oppName = opponentName;
		if (piece.startsWith("X")) {
			this.myPiece = 1;
			this.oppPiece = 2;
			this.turn = true;
		} else {
			this.oppPiece = 1;
			this.myPiece = 2;
			this.turn = false;
		}
	}
	
	private void onPlayRequested(String payload) {
		StringTokenizer st = new StringTokenizer(payload);
		st.nextToken();
		
		int opponentId = Integer.parseInt(st.nextToken());
		int index = Integer.parseInt(st.nextToken());
		
		this.gameboard[index] = this.oppPiece;
		if (isGameOver()!=0) {
			
			printBoard();
			System.out.println("GAMEOVER!\n\n\n");
			
			if (isGameOver() == this.myPiece) 
				System.out.println("Congratulations! Youve Won!");
			else
				System.out.println("Im Sorry! Youre a Loser!");
			
			System.out.println("Do you want to play another game?");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			try {
				String playAgain = br.readLine();
				if (playAgain.startsWith("y") || playAgain.startsWith("Y"))
				{
					String command = "JOIN " + " " + this.myId;
					DatagramPacket txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
					DatagramSocket socket = new DatagramSocket();
					socket.send(txPacket);
				} else {
					String command = "ENDGAME " + " " + this.oppId + " " + this.myId;
					DatagramPacket txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
					DatagramSocket socket = new DatagramSocket();
					socket.send(txPacket);
					
					command = "UNREGISTER " + " " + this.myId;
					txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
					socket.send(txPacket);
					
					System.out.println("I hope you enjoyed playing!");

				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.oppId = 0;
			this.oppName = "";
			this.oppPiece = 0;
			
			for (int i = 0; i <= 8; i++)
			{
				gameboard[i] = 0;
			}
			
			turn = false;
		}
		
		System.out.println("\nOpponent made a move ");
		printBoard();
		
		this.turn = true;
	}

	private void onEndGameRequested(String payload) {
		StringTokenizer st = new StringTokenizer(payload);
		st.nextToken();
		
		int opponentId = Integer.parseInt(st.nextToken());
				
		this.oppId = 0;
		this.oppName = "";
		this.oppPiece = 0;
		
		for (int i = 0; i <= 8; i++)
		{
			gameboard[i] = 0;
		}
		
		turn = false;
		
		System.out.println("Do you want to play another game?");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

		String playAgain;
		try {
			playAgain = br.readLine();
			if (playAgain.startsWith("y") || playAgain.startsWith("Y"))
			{
				String command = "JOIN " + " " + this.myId;
				DatagramPacket txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
				DatagramSocket socket = new DatagramSocket();
				socket.send(txPacket);
			} else {				
				String command = "UNREGISTER " + " " + this.myId;
				DatagramPacket txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
				DatagramSocket socket = new DatagramSocket();
				socket.send(txPacket);
				
				System.out.println("I hope you enjoyed playing!");

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		

		
	}
	
	private int isGameOver() {
		for (int i = 0; i <= 6; i=i+3) {
			if (this.gameboard[i] == this.oppPiece && this.gameboard[i+1] == this.oppPiece && this.gameboard[i+2] == this.oppPiece)
				return this.oppPiece;
		}
		for (int i = 0; i <= 2; i++) {
			if (this.gameboard[i] == this.oppPiece && this.gameboard[i+3] == this.oppPiece && this.gameboard[i+6] == this.oppPiece)
				return this.oppPiece;
		}
		if (this.gameboard[0] == this.oppPiece && this.gameboard[4] == this.oppPiece && this.gameboard[8] == this.oppPiece)
			return this.oppPiece;
		if (this.gameboard[2] == this.oppPiece && this.gameboard[4] == this.oppPiece && this.gameboard[6] == this.oppPiece)
			return this.oppPiece;
		
		for (int i = 0; i <= 6; i=i+3) {
			if (this.gameboard[i] == this.myPiece && this.gameboard[i+1] == this.myPiece && this.gameboard[i+2] == this.myPiece)
				return this.myPiece;
		}
		for (int i = 0; i <= 2; i++) {
			if (this.gameboard[i] == this.myPiece && this.gameboard[i+3] == this.myPiece && this.gameboard[i+6] == this.myPiece)
				return this.myPiece;
		}
		if (this.gameboard[0] == this.myPiece && this.gameboard[4] == this.myPiece && this.gameboard[8] == this.myPiece)
			return this.myPiece;
		if (this.gameboard[2] == this.myPiece && this.gameboard[4] == this.myPiece && this.gameboard[6] == this.myPiece)
			return this.myPiece;
		
		return 0;
		
	}
	
	private void printBoard() {
        System.out.println(this.gameboard[0] + "|" + this.gameboard[1] + "|" + this.gameboard[2]);
        System.out.println("-----");
        System.out.println(this.gameboard[3] + "|" + this.gameboard[4] + "|" + this.gameboard[5]);
        System.out.println("-----");
        System.out.println(this.gameboard[6] + "|" + this.gameboard[7] + "|" + this.gameboard[8]);
	}
	
	// main method
	public static void main(String[] args) {
		int serverPort = 20000;
		String serverAddress = "54.186.249.228";

		// check if server address and port were given as command line arguments
		if (args.length > 0) {
			serverAddress = args[0];
		}

		if (args.length > 1) {
			try {
				serverPort = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.out.println("Invalid serverPort specified: " + args[0]);
				System.out.println("Using default serverPort " + serverPort);
			}
		}

		// instantiate the client
		Client client = new Client(serverAddress, serverPort);

		// start it
		client.start();
	}

}
