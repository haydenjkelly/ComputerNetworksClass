package udpgroupchat.server;

import java.net.InetAddress;

public class ClientEndPoint {
	InetAddress address;
	int port;
	String name;
	String group;
	
	public ClientEndPoint() {
		this.address = null;
		this.port = 0;
		this.name = "";
		this.group = "";	
	}
	
	public ClientEndPoint(InetAddress addr, int port) {
		this.address = addr;
		this.port = port;
		this.name = "";
		this.group = "";
		
	}
	
	public ClientEndPoint(InetAddress addr, int port, String name, String group) {
		this.address = addr;
		this.port = port;
		this.name = name;
		this.group = group;
		
	}

	@Override
	public int hashCode() {
		// the hashcode is the exclusive or (XOR) of the port number and the hashcode of the address object
		return this.port ^ this.address.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		// two endpoints are considered equal if their hash codes are equal
		return this.hashCode() == other.hashCode();
	}
	
}
