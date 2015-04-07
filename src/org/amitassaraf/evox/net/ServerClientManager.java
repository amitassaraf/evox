package org.amitassaraf.evox.net;

import java.net.Inet4Address;

/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha 
 * Egoz St. House 3 Right | All rights to this code are reserved to/for
 * the author aka Amit Assaraf. Any publishing of this code without
 * authorization from the author will lead to bad consciences. Therfore
 * do not redistribute this file/code. No snippets of this code may be
 * redistributed/published.
 * 
 * Contact information:
 *   Amit Assaraf - Email: soit@walla.com Phone: 0505964411 (Israel)
 *
 */

public class ServerClientManager extends Thread {
	
	private Inet4Address clientAddress;
	private int clientPort;
	private GameServer server;

	public ServerClientManager() {
		
	}
	
	public Inet4Address getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(Inet4Address clientAddress) {
		this.clientAddress = clientAddress;
	}
	public int getClientPort() {
		return clientPort;
	}
	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}
	public GameServer getServer() {
		return server;
	}
	public void setServer(GameServer server) {
		this.server = server;
	}

}
