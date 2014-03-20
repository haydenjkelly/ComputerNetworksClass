package udpgroupchat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class WorkerThread extends Thread {

	private DatagramPacket rxPacket;
	private DatagramSocket socket;

	public WorkerThread(DatagramPacket packet, DatagramSocket socket) {
		this.rxPacket = packet;
		this.socket = socket;
	}

	@Override
	public void run() {
		// convert the rxPacket's payload to a string
		String payload = new String(rxPacket.getData(), 0, rxPacket.getLength())
				.trim();

		// dispatch request handler functions based on the payload's prefix

		if (payload.startsWith("REGISTER")) {
			onRegisterRequested(payload);
			return;
		}

		if (payload.startsWith("UNREGISTER")) {
			onUnregisterRequested(payload);
			return;
		}

		if (payload.startsWith("SEND")) {
			onSendRequested(payload);
			return;
		}

		//
		// implement other request handlers here...
		//

		if (payload.startsWith("INFO")) {
			onInfoRequested(payload);
			return;
		}

		if (payload.startsWith("MSG")) {
			onMsgRequested(payload);
			return;
		}
		
		if (payload.startsWith("POLL")) {
			onPollRequested(payload);
			return;
		}
		
		// if we got here, it must have been a bad request, so we tell the
		// client about it
		onBadRequest(payload);
	}
	
	private void onMsgRequested(String payload) {
		String[] words = payload.split(" ");
		String[] message = Arrays.copyOfRange(words, 2, words.length);
		String messageText = "";
		for (String word : message) {
			messageText = messageText + " " + word;
		}
		
		for (ClientEndPoint clientEndPoint : Server.clientEndPoints) {
			
			if (clientEndPoint.group.startsWith(words[1]))
			{
				try {
					send("MESSAGE: " + clientEndPoint.name + " says" + messageText + "\n", clientEndPoint.address, clientEndPoint.port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			send("Message Sent to " + words[1] + "\n", this.rxPacket.getAddress(), this.rxPacket.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void onPollRequested(String payload) {
		// TODO Auto-generated method stub
		
	}

	private void onInfoRequested(String payload) {
		String[] words = payload.split(" ");
		
		ClientEndPoint newClientEndPoint = new ClientEndPoint(this.rxPacket.getAddress(), this.rxPacket.getPort(),words[1],words[2]);

		ClientEndPoint oldClientEndPoint = new ClientEndPoint(this.rxPacket.getAddress(), this.rxPacket.getPort());

		
		// check if client is in the set of registered clientEndPoints
		if (Server.clientEndPoints.contains(oldClientEndPoint)) {
			Server.clientEndPoints.remove(oldClientEndPoint);
			
			Server.clientEndPoints.add(newClientEndPoint);

			// tell client we're OK
			try {
				send("+SUCCESS: Hi " + newClientEndPoint.name + "! Welcome to group: " + newClientEndPoint.group + "\n", this.rxPacket.getAddress(), this.rxPacket.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String payload, InetAddress address, int port) throws IOException {
		DatagramPacket txPacket = new DatagramPacket(payload.getBytes(), payload.length(), address, port);
		this.socket.send(txPacket);
	}

	private void onRegisterRequested(String payload) {
		InetAddress address = this.rxPacket.getAddress();
		int port = this.rxPacket.getPort();

		// create a client object, and put it in the map that assigns names
		// to client objects
		Server.clientEndPoints.add(new ClientEndPoint(address, port));
		// note that calling clientEndPoints.add() with the same endpoint info
		// (address and port)
		// multiple times will not add multiple instances of ClientEndPoint to
		// the set, because ClientEndPoint.hashCode() is overridden. See
		// http://docs.oracle.com/javase/7/docs/api/java/util/Set.html for
		// details.

		// tell client we're OK
		try {
			send("REGISTERED\n", this.rxPacket.getAddress(),
					this.rxPacket.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onUnregisterRequested(String payload) {
		ClientEndPoint clientEndPoint = new ClientEndPoint(
				this.rxPacket.getAddress(), this.rxPacket.getPort());

		// check if client is in the set of registered clientEndPoints
		if (Server.clientEndPoints.contains(clientEndPoint)) {
			// yes, remove it
			Server.clientEndPoints.remove(clientEndPoint);
			try {
				send("UNREGISTERED\n", this.rxPacket.getAddress(),
						this.rxPacket.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// no, send back a message
			try {
				send("CLIENT NOT REGISTERED\n", this.rxPacket.getAddress(),
						this.rxPacket.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void onSendRequested(String payload) {
		// the message is comes after "SEND" in the payload
		String message = payload.substring("SEND".length() + 1,
				payload.length()).trim();
		for (ClientEndPoint clientEndPoint : Server.clientEndPoints) {
			try {
				send("MESSAGE: " + message + "\n", clientEndPoint.address,
						clientEndPoint.port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void onBadRequest(String payload) {
		try {
			send("BAD REQUEST\n", this.rxPacket.getAddress(),
					this.rxPacket.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
