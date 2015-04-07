package org.amitassaraf.evox.net.kryo;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.EntityPlayer;
import org.amitassaraf.evox.net.kryo.packets.LoginPacket01;
import org.amitassaraf.evox.net.kryo.packets.PlayerDataPacket04;
import org.amitassaraf.evox.net.kryo.packets.PlayerDisconnectPacket09;
import org.amitassaraf.evox.net.kryo.packets.PlayerMovePacket05;
import org.amitassaraf.evox.net.kryo.packets.WorldBlockChangePacket06;
import org.amitassaraf.evox.net.kryo.packets.WorldDataPacket02;
import org.amitassaraf.evox.net.kryo.packets.WorldDataRequestPacket07;
import org.amitassaraf.evox.net.kryo.packets.WorldLoadStatePacket03;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;

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

public class ClientManagerKryo {

	private String username;
	private Connection connection;
	private EntityPlayer player;
	private GameServerKryo server;
	private int chunkCount = 0;

	public ClientManagerKryo(GameServerKryo server, Connection conn,
			LoginPacket01 req) {
		this.username = req.getUsername();
		this.connection = conn;
		this.server = server;
		player = new EntityPlayer(new Vector3f(req.getX(), req.getY(),
				req.getZ()), req.getUsername());
	}

	public void processPacket(Object object) {
		if (object instanceof PlayerMovePacket05) {
			PlayerMovePacket05 pack = (PlayerMovePacket05) object;
			/**
			 * Update the player on our server
			 */
			player.setLocation(new Vector3f(pack.getX(), pack.getY(), pack
					.getZ()));

			PlayerMovePacket05 newpack = new PlayerMovePacket05();
			newpack.setUsername(pack.getUsername());
			newpack.setX(pack.getX());
			newpack.setY(pack.getY());
			newpack.setZ(pack.getZ());
			server.broadcast(this, newpack);
		} else if (object instanceof WorldBlockChangePacket06) {
			WorldBlockChangePacket06 pack = (WorldBlockChangePacket06) object;

			if (pack.isPlace()) {
				server.getWorld().overrideVoxelAt(
						new Vector3f(pack.getBlockX(), pack.getBlockY(),
								pack.getBlockZ()),
						server.getWorld().getExternalNodeByLocation(
								new Vector3f(pack.getChunkX(),
										pack.getChunkY(), pack.getChunkZ())),
						pack.getVoxel());
			} else {
				server.getWorld().overrideVoxelAt(
						new Vector3f(pack.getBlockX(), pack.getBlockY(),
								pack.getBlockZ()),
						server.getWorld().getExternalNodeByLocation(
								new Vector3f(pack.getChunkX(),
										pack.getChunkY(), pack.getChunkZ())),
						(byte) 0);
			}

			server.broadcast(this, pack);
		} else if (object instanceof PlayerDisconnectPacket09) {
			PlayerDisconnectPacket09 dis = (PlayerDisconnectPacket09) object;
			server.broadcast(this, dis);
			disconnect();
		
		} else if (object instanceof WorldDataRequestPacket07) {
			if (chunkCount >= server.getWorld().getNodes().size()) {
				WorldLoadStatePacket03 pack = new WorldLoadStatePacket03();
				pack.setWorldLoaded(true);
				connection.sendTCP(pack);
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				for (ClientManagerKryo cmk : server.getClients()) {
					if (cmk != this) {
						PlayerDataPacket04 player = new PlayerDataPacket04();
						player.setUsername(cmk.getUsername());
						player.setX(cmk.getPlayer().getLocation().x);
						player.setY(cmk.getPlayer().getLocation().y);
						player.setZ(cmk.getPlayer().getLocation().z);
						connection.sendTCP(player);
					}
				}
				
				return;
			}

			Chunk c = server.getWorld().getNodes().get(chunkCount);
			byte[] voxels = flattenVoxelArray(c.getVoxels());

			int amountOfDataChunks = (voxels.length % 1000 == 0 ? (int) (voxels.length / 1000)
					: (int) (voxels.length / 1000) + 1);

			WorldLoadStatePacket03 pack = new WorldLoadStatePacket03();
			pack.setAmountOfDataChunks(amountOfDataChunks);
			pack.setEntireChunkDataSize(voxels.length);
			connection.sendTCP(pack);

			for (int i = 0; i < amountOfDataChunks; i++) {
				WorldDataPacket02 response = new WorldDataPacket02();
				response.setVoxels(get1KBData(voxels, i));
				response.setPos((byte) i);
				connection.sendTCP(response);

				// Every 5 packets, wait 1 ms, to not overflow the buffer of
				// Kryo
				// TODO: Tweak this value (Try and push it to the limit)
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}

			WorldLoadStatePacket03 end = new WorldLoadStatePacket03();
			end.setChunkDataLoaded(true);
			end.setX(c.getBoundingbox().getMinX());
			end.setY(c.getBoundingbox().getMinY());
			end.setZ(c.getBoundingbox().getMinZ());
			connection.sendTCP(end);

			chunkCount++;
			System.out.println("Num:" + chunkCount);
		}
	}

	private void disconnect() {
		server.disconnect(this);
	}

	private byte[] get1KBData(byte[] voxels, int offset) {
		byte[] data = new byte[1000];

		int pos = 0;
		for (int i = 0; i < 1000 && ((offset * 1000) + i) < voxels.length; i++) {
			data[pos] = voxels[(offset * 1000) + i];
			pos += 1;
		}

		return data;
	}

	private byte[] flattenVoxelArray(byte[][][] voxels) {
		byte[] res = new byte[Constants.NODE_WIDTH * Constants.NODE_LENGTH
				* Constants.NODE_HEIGHT];
		for (int x = 0; x < Constants.NODE_WIDTH; x++)
			for (int z = 0; z < Constants.NODE_LENGTH; z++)
				for (int y = 0; y < Constants.NODE_HEIGHT; y++)
					res[x + Constants.NODE_WIDTH
							* (z + Constants.NODE_LENGTH * y)] = voxels[x][z][y];

		return res;
	}

	public String getUsername() {
		return username;
	}

	public Connection getConnection() {
		return connection;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

}
