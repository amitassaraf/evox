package org.amitassaraf.evox.net.kryo.packets;

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

public class WorldLoadStatePacket03 {

	private int amountOfDataChunks;
	private int entireChunkDataSize;
	private boolean worldLoaded = false;
	private boolean chunkDataLoaded = false;
	
	private float x;
	private float y;
	private float z;

	public WorldLoadStatePacket03() {

	}

	public int getAmountOfDataChunks() {
		return amountOfDataChunks;
	}

	public void setAmountOfDataChunks(int amountOfDataChunks) {
		this.amountOfDataChunks = amountOfDataChunks;
	}

	public boolean isWorldLoaded() {
		return worldLoaded;
	}

	public void setWorldLoaded(boolean worldLoaded) {
		this.worldLoaded = worldLoaded;
	}

	public int getEntireChunkDataSize() {
		return entireChunkDataSize;
	}

	public void setEntireChunkDataSize(int entireChunkDataSize) {
		this.entireChunkDataSize = entireChunkDataSize;
	}

	public boolean isChunkDataLoaded() {
		return chunkDataLoaded;
	}

	public void setChunkDataLoaded(boolean chunkDataLoaded) {
		this.chunkDataLoaded = chunkDataLoaded;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
