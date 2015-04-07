package org.amitassaraf.evox.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

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

public class GameServer extends Thread implements IServer {

    public static final int MAX_DATA_SIZE = 1024;
	
	private ArrayList<ServerClientManager> clients;
	private int port = 25566;
	private DatagramSocket server;
	static GameParameters gameParams;
	private boolean running = false;
	//private VoxelWorld gameWorld;
	
	public GameServer(int port) {
		this.port = port;
		gameParams = new GameParameters();
		clients = new ArrayList<ServerClientManager>();
		
	}
	
	@Override
	public void initGameServer() {
        try {
            this.server = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Server Initialising..");		
	}
	
	public void run() {
        System.out.println("Server Started.");
        while(running) {
            byte[] data = new byte[MAX_DATA_SIZE];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            try {
            	server.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
           // processor.parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
        }
	}
	
    public void sendData(byte[] data, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
        try {
            server.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (ServerClientManager p : clients) {
            sendData(data,p.getClientAddress(),p.getClientPort());
        }
    }

	
	public void cleanServer() {
		
	}
	
	public void shutdownServer() {
		
	}

	/*
	 * Game parameters class
	 */
	class GameParameters {
	    /** True if the server is in online mode. */
	    private boolean onlineMode;

	    /** Indicates whether PvP is active on the server or not. */
	    private boolean pvpEnabled;

	    /** Determines if flight is allowed or not. */
	    private boolean allowFlight;

	    /** The server MOTD string. */
	    private String motd;
	    
	    /** Username of the server owner (for integrated servers) */
	    private String serverOwner;
	    private String folderName;
	    private String worldName;
		public boolean isOnlineMode() {
			return onlineMode;
		}
		public void setOnlineMode(boolean onlineMode) {
			this.onlineMode = onlineMode;
		}
		public boolean isPvpEnabled() {
			return pvpEnabled;
		}
		public void setPvpEnabled(boolean pvpEnabled) {
			this.pvpEnabled = pvpEnabled;
		}
		public boolean isAllowFlight() {
			return allowFlight;
		}
		public void setAllowFlight(boolean allowFlight) {
			this.allowFlight = allowFlight;
		}
		public String getMotd() {
			return motd;
		}
		public void setMotd(String motd) {
			this.motd = motd;
		}
		public String getServerOwner() {
			return serverOwner;
		}
		public void setServerOwner(String serverOwner) {
			this.serverOwner = serverOwner;
		}
		public String getFolderName() {
			return folderName;
		}
		public void setFolderName(String folderName) {
			this.folderName = folderName;
		}
		public String getWorldName() {
			return worldName;
		}
		public void setWorldName(String worldName) {
			this.worldName = worldName;
		}
	    
	}
	
}
