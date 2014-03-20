package udpgroupchat.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import udpgroupchat.server.Server;

public class Client {

	String serverAddress;
	int serverPort;

	// constructor
	Client(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}

	// start up the server
	public void start1() {
		DatagramSocket socket = null;
		try {

			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			socket = new DatagramSocket();

			String command = "REGISTER";
			DatagramPacket txPacket = new DatagramPacket(command.getBytes(),command.length(), serverSocketAddress);
			socket.send(txPacket);

			byte[] buf = new byte[Server.MAX_PACKET_SIZE];
			DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			socket.receive(rxPacket);
			String payload = new String(rxPacket.getData(), 0,rxPacket.getLength());
			System.out.println(payload);

			command = "SEND Hello everybody! The time is " + new SimpleDateFormat("HH:mm:ss").format(new Date());
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "INFO 1 Group1";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);

			while (true) {
				socket.receive(rxPacket);
				payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
				System.out.println(payload);
			}
		} catch (IOException e) {
			// we jump out here if there's an error
			e.printStackTrace();
		} finally {
			// close the socket
			if(socket!=null && !socket.isClosed())
				socket.close();
		}
	}

	public void start2() {
		DatagramSocket socket = null;
		try {

			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			socket = new DatagramSocket();

			String command = "REGISTER";
			DatagramPacket txPacket = new DatagramPacket(command.getBytes(),command.length(), serverSocketAddress);
			socket.send(txPacket);

			byte[] buf = new byte[Server.MAX_PACKET_SIZE];
			DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			socket.receive(rxPacket);
			String payload = new String(rxPacket.getData(), 0,rxPacket.getLength());
			System.out.println(payload);

			command = "SEND Hello everybody! The time is " + new SimpleDateFormat("HH:mm:ss").format(new Date());
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "INFO 2 Group1";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "MSG Group1 Hello Guy Number 1";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);

			while (true) {
				socket.receive(rxPacket);
				payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
				System.out.println(payload);
			}
		} catch (IOException e) {
			// we jump out here if there's an error
			e.printStackTrace();
		} finally {
			// close the socket
			if(socket!=null && !socket.isClosed())
				socket.close();
		}
	}
	
	public void start3() {
		DatagramSocket socket = null;
		try {

			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			socket = new DatagramSocket();

			String command = "REGISTER";
			DatagramPacket txPacket = new DatagramPacket(command.getBytes(),command.length(), serverSocketAddress);
			socket.send(txPacket);

			byte[] buf = new byte[Server.MAX_PACKET_SIZE];
			DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			socket.receive(rxPacket);
			String payload = new String(rxPacket.getData(), 0,rxPacket.getLength());
			System.out.println(payload);

			command = "SEND Hello everybody! The time is " + new SimpleDateFormat("HH:mm:ss").format(new Date());
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "INFO 3 Group2";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "SEND Hello everybody! My Name is 3";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			while (true) {
				socket.receive(rxPacket);
				payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
				System.out.println(payload);
			}
		} catch (IOException e) {
			// we jump out here if there's an error
			e.printStackTrace();
		} finally {
			// close the socket
			if(socket!=null && !socket.isClosed())
				socket.close();
		}
	}

	public void start4() {
		DatagramSocket socket = null;
		try {

			InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

			socket = new DatagramSocket();

			String command = "REGISTER";
			DatagramPacket txPacket = new DatagramPacket(command.getBytes(),command.length(), serverSocketAddress);
			socket.send(txPacket);

			byte[] buf = new byte[Server.MAX_PACKET_SIZE];
			DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			socket.receive(rxPacket);
			String payload = new String(rxPacket.getData(), 0,rxPacket.getLength());
			System.out.println(payload);

			command = "SEND Hello everybody! The time is " + new SimpleDateFormat("HH:mm:ss").format(new Date());
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);

			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "INFO 4 Group1";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			command = "MSG Group1 Hello Guys Number 1 and 2";
			txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
			socket.send(txPacket);
			
			socket.receive(rxPacket);
			payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
			System.out.println(payload);
			
			while (true) {
				socket.receive(rxPacket);
				payload = new String(rxPacket.getData(), 0, rxPacket.getLength());
				System.out.println(payload);
			}
		} catch (IOException e) {
			// we jump out here if there's an error
			e.printStackTrace();
		} finally {
			// close the socket
			if(socket!=null && !socket.isClosed())
				socket.close();
		}
	}
	
	// main method
	public static void main(String[] args) {
		int serverPort = Server.DEFAULT_PORT;
		String serverAddress = "localhost";

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

//		Client client1 = new Client(serverAddress, serverPort);
//		client1.start1();
		
//		Client client2 = new Client(serverAddress, serverPort);
//		client2.start2();
	
//		Client client3 = new Client(serverAddress, serverPort);
//		client3.start3();
		
		Client client4 = new Client(serverAddress, serverPort);
		client4.start4();

		
		
		
		
	}

}
