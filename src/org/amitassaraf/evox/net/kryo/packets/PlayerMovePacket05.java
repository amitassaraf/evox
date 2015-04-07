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

public class PlayerMovePacket05 {

	private String username;
	private float x;
	private float y;
	private float z;

	public PlayerMovePacket05() {
	}

	public float getX() {
		return x;
	}

	public PlayerMovePacket05 setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public PlayerMovePacket05 setY(float y) {
		this.y = y;
		return this;
	}

	public float getZ() {
		return z;
	}

	public PlayerMovePacket05 setZ(float z) {
		this.z = z;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public PlayerMovePacket05 setUsername(String username) {
		this.username = username;
		return this;
	}

}
