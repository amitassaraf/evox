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

public class WorldBlockChangePacket06 {
	
	private boolean place = false;
	private float blockX,blockY,blockZ;
	private float chunkX,chunkY,chunkZ;
	private byte voxel;
	
	public WorldBlockChangePacket06(){
	}

	public boolean isPlace() {
		return place;
	}

	public void setPlace(boolean place) {
		this.place = place;
	}

	public float getBlockX() {
		return blockX;
	}

	public void setBlockX(float blockX) {
		this.blockX = blockX;
	}

	public float getBlockY() {
		return blockY;
	}

	public void setBlockY(float blockY) {
		this.blockY = blockY;
	}

	public float getBlockZ() {
		return blockZ;
	}

	public void setBlockZ(float blockZ) {
		this.blockZ = blockZ;
	}

	public float getChunkX() {
		return chunkX;
	}

	public void setChunkX(float chunkX) {
		this.chunkX = chunkX;
	}

	public float getChunkY() {
		return chunkY;
	}

	public void setChunkY(float chunkY) {
		this.chunkY = chunkY;
	}

	public float getChunkZ() {
		return chunkZ;
	}

	public void setChunkZ(float chunkZ) {
		this.chunkZ = chunkZ;
	}

	public byte getVoxel() {
		return voxel;
	}

	public void setVoxel(byte voxel) {
		this.voxel = voxel;
	}
	
}
