package org.amitassaraf.evox.net.kryo;

import java.io.IOException;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.VoxelWorld;
import org.amitassaraf.evox.net.kryo.packets.LoginAckPacket08;
import org.amitassaraf.evox.net.kryo.packets.LoginPacket01;
import org.amitassaraf.evox.net.kryo.packets.PlayerDataPacket04;
import org.amitassaraf.evox.net.kryo.packets.PlayerMovePacket05;
import org.amitassaraf.evox.net.kryo.packets.WorldBlockChangePacket06;
import org.amitassaraf.evox.net.kryo.packets.WorldDataPacket02;
import org.amitassaraf.evox.net.kryo.packets.WorldDataRequestPacket07;
import org.amitassaraf.evox.net.kryo.packets.WorldLoadStatePacket03;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.util.TcpIdleSender;

/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha s Egoz
 * St. House 3 Right | All rights to this code are reserved to/for the author
 * aka Amit Assaraf. Any publishing of this code without authorization from the
 * author will lead to bad consciences. Therfore do not redistribute this
 * file/code. No snippets of this code may be redistributed/published.
 * 
 * Contact information: Amit Assaraf - Email: soit@walla.com Phone: 0505964411
 * (Israel)
 *
 */

public class GameServerKryo {

	private Server server;
	private ArrayList<ClientManagerKryo> clients;
	private VoxelWorld world;
	private Game game;

	public GameServerKryo(Game game) {
		this.server = new Server();
		this.game = game;
		clients = new ArrayList<ClientManagerKryo>();
		world = new VoxelWorld(this.game, true);
		world.generate();

		Kryo kryo = server.getKryo();
		kryo.register(LoginPacket01.class);
		kryo.register(WorldDataPacket02.class);
		kryo.register(WorldLoadStatePacket03.class);
		kryo.register(PlayerDataPacket04.class);
		kryo.register(PlayerMovePacket05.class);
		kryo.register(WorldBlockChangePacket06.class);
		kryo.register(WorldDataRequestPacket07.class);
		kryo.register(LoginAckPacket08.class);
		kryo.register(byte[].class);
		kryo.register(int[].class);
	}

	public void startServer() {
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Add client connection listener
		 */
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof LoginPacket01) {
					LoginPacket01 request = (LoginPacket01) object;
					System.out.println(request.getUsername()
							+ " has connected. ");

					/**
					 * Login the client
					 */
					clients.add(new ClientManagerKryo(GameServerKryo.this,
							connection, request));

					/**
					 * Send him login ack
					 */
					connection.sendTCP(new LoginAckPacket08().setLoggedIn(true));

					PlayerDataPacket04 newPlayer = new PlayerDataPacket04()
							.setUsername(request.getUsername())
							.setX(request.getX()).setY(request.getY())
							.setZ(request.getZ());

					/**
					 * Broadcast new player to all previous players
					 */
					broadcast(clients.get(clients.size() - 1), newPlayer);
				} else {
					for (ClientManagerKryo c : clients) {
						if (c.getConnection().equals(connection)) {
							c.processPacket(object);
							break;
						}
					}
				}
			}
		});
	}

	public void broadcast(ClientManagerKryo serverClient, Object packet) {
		/**
		 * Broadcast the change to everyone else
		 */
		for (int i = 0; i < clients.size(); i++) {
			if (!clients.get(i).equals(serverClient)) {
				clients.get(i).getConnection().sendTCP(packet);
			}
		}
	}

	public VoxelWorld getWorld() {
		return world;
	}

	public Server getServer() {
		return server;
	}

	public ArrayList<ClientManagerKryo> getClients() {
		return clients;
	}

	public void disconnect(ClientManagerKryo clientManagerKryo) {
		clients.remove(clients.indexOf(clientManagerKryo));
	}

}
