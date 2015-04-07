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

public class WorldDataPacket02 {

	private byte[] voxels;
	private byte pos;

	public WorldDataPacket02() {
	}

	public byte[] getVoxels() {
		return voxels;
	}

	public void setVoxels(byte[] voxels) {
		this.voxels = voxels;
	}

	public byte getPos() {
		return pos;
	}

	public void setPos(byte pos) {
		this.pos = pos;
	}

}
