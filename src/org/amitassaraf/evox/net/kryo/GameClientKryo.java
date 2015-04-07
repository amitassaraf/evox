package org.amitassaraf.evox.net.kryo;

import static org.amitassaraf.evox.core.Constants.NODE_HEIGHT;
import static org.amitassaraf.evox.core.Constants.NODE_LENGTH;
import static org.amitassaraf.evox.core.Constants.NODE_WIDTH;
import static org.amitassaraf.evox.core.Constants.VOXEL_SIZE;

import java.io.IOException;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.BoxAABB3;
import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.EntityPlayer;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.net.kryo.packets.LoginAckPacket08;
import org.amitassaraf.evox.net.kryo.packets.LoginPacket01;
import org.amitassaraf.evox.net.kryo.packets.PlayerDataPacket04;
import org.amitassaraf.evox.net.kryo.packets.PlayerDisconnectPacket09;
import org.amitassaraf.evox.net.kryo.packets.PlayerMovePacket05;
import org.amitassaraf.evox.net.kryo.packets.WorldBlockChangePacket06;
import org.amitassaraf.evox.net.kryo.packets.WorldDataPacket02;
import org.amitassaraf.evox.net.kryo.packets.WorldDataRequestPacket07;
import org.amitassaraf.evox.net.kryo.packets.WorldLoadStatePacket03;
import org.apache.commons.lang3.RandomStringUtils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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

public class GameClientKryo {

	private Game game;
	private Client client;
	private final String networkIdentifier;
	private boolean worldLoaded = false;
	private boolean connected = false;
	private Connection server;

	/**
	 * Chunk load variables
	 */
	private byte[] temporaryData;
	private int temporaryAmountOfDataChunks;

	public GameClientKryo(Game game) {
		this.game = game;
		client = new Client();

		Kryo kryo = client.getKryo();
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

		networkIdentifier = RandomStringUtils.randomAlphanumeric(25);
	}

	public void connect(String ip) {
		client.start();
		try {
			client.connect(5000, ip, 54555, 54777);
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		LoginPacket01 request = new LoginPacket01();
		request.setX(game.getPlayers().get(0).getLocation().x);
		request.setY(game.getPlayers().get(0).getLocation().y);
		request.setZ(game.getPlayers().get(0).getLocation().z);
		request.setUsername(networkIdentifier);
		client.sendTCP(request);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof LoginAckPacket08) {
					LoginAckPacket08 pack = (LoginAckPacket08) object;

					if (pack.isLoggedIn()) {
						server = connection;
						connection.sendTCP(new WorldDataRequestPacket07());
					}
				} else if (object instanceof WorldDataPacket02) {
					WorldDataPacket02 response = (WorldDataPacket02) object;
					add1KBData(temporaryData, response.getVoxels(),
							(int) response.getPos());
					WorldDataRequestPacket07 pack = new WorldDataRequestPacket07();
					connection.sendTCP(pack);
				} else if (object instanceof WorldLoadStatePacket03) {
					WorldLoadStatePacket03 rec = (WorldLoadStatePacket03) object;

					if (rec.isWorldLoaded()) {
						worldLoaded = true;
					} else if (rec.isChunkDataLoaded()) {
						/**
						 * unflatten array
						 */
						byte[][][] unflattenedVoxels = new byte[Constants.NODE_WIDTH][Constants.NODE_LENGTH][Constants.NODE_HEIGHT];

						for (int x = 0; x < Constants.NODE_WIDTH; x++)
							for (int z = 0; z < Constants.NODE_LENGTH; z++)
								for (int y = 0; y < Constants.NODE_HEIGHT; y++)
									unflattenedVoxels[x][z][y] = temporaryData[x
											+ Constants.NODE_WIDTH
											* (z + Constants.NODE_LENGTH * y)];

						game.getWorld().loadChunk(
								new Chunk(new BoxAABB3(new Vector3f(rec.getX(),
										rec.getY(), rec.getZ()), new Vector3f(
										NODE_WIDTH * VOXEL_SIZE, NODE_HEIGHT
												* VOXEL_SIZE, NODE_LENGTH
												* VOXEL_SIZE)),
										unflattenedVoxels));

						connection.sendTCP(new WorldDataRequestPacket07());
					} else {
						temporaryData = new byte[rec.getEntireChunkDataSize()];
						temporaryAmountOfDataChunks = rec
								.getAmountOfDataChunks();
					}
				} else if (object instanceof PlayerDataPacket04) {
					PlayerDataPacket04 pack = (PlayerDataPacket04) object;
					game.getPlayers().add(
							new EntityPlayer(new Vector3f(pack.getX(), pack
									.getY(), pack.getZ()), pack.getUsername()));

				} else if (object instanceof PlayerMovePacket05) {
					System.out.println("Movement packets");
					PlayerMovePacket05 pack = (PlayerMovePacket05) object;
					for (int i = 1; i < game.getPlayers().size(); i++) {
						if (game.getPlayers().get(i).getUsername()
								.equalsIgnoreCase(pack.getUsername())) {
							game.getPlayers()
									.get(i)
									.setLocation(
											new Vector3f(pack.getX(), pack
													.getY(), pack.getZ()));
							System.out.println(game.getPlayers().get(i)
									.getUsername()
									+ " is at "
									+ game.getPlayers().get(i).getLocation()
											.toString());
							break;
						}
					}
				} else if (object instanceof PlayerDisconnectPacket09) {
					PlayerDisconnectPacket09 dis = (PlayerDisconnectPacket09) object;
					for (int i = 0; i < game.getPlayers().size(); i++) {
						if (game.getPlayers().get(i).getUsername()
								.equalsIgnoreCase(dis.getUsername())) {
							game.getPlayers().remove(i);
							break;
						}
					}
				} else if (object instanceof WorldBlockChangePacket06) {
					WorldBlockChangePacket06 pack = (WorldBlockChangePacket06) object;

					if (pack.isPlace()) {
						game.getWorld().overrideVoxelAt(
								new Vector3f(pack.getBlockX(),
										pack.getBlockY(), pack.getBlockZ()),
								game.getWorld()
										.getExternalNodeByLocation(
												new Vector3f(pack.getChunkX(),
														pack.getChunkY(), pack
																.getChunkZ())),
								pack.getVoxel());
					} else {
						game.getWorld().overrideVoxelAt(
								new Vector3f(pack.getBlockX(),
										pack.getBlockY(), pack.getBlockZ()),
								game.getWorld()
										.getExternalNodeByLocation(
												new Vector3f(pack.getChunkX(),
														pack.getChunkY(), pack
																.getChunkZ())),
								(byte) 0);
					}
				}
			}
		});
	}

	protected void add1KBData(byte[] temporaryData2, byte[] voxels, int pos) {
		for (int i = 0; i < voxels.length
				&& ((pos * 1000) + i) < temporaryData2.length; i++) {
			temporaryData2[(pos * 1000) + i] = voxels[i];
		}
	}

	public void sendPacket(Object obj) {
		this.server.sendTCP(obj);
	}

	public String getNetworkIdentifier() {
		return networkIdentifier;
	}

	public boolean isWorldLoaded() {
		return worldLoaded;
	}

	public boolean isConnected() {
		return connected;
	}

}
