package org.amitassaraf.evox.view;

import org.amitassaraf.evox.core.Game;
import javax.vecmath.Vector3f;

/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha Egoz St.
 * House 3 Right | All rights to this code are reserved to/for the author aka
 * Amit Assaraf. Any publishing of this code without authorization from the
 * author will lead to bad consciences. Therfore do not redistribute this
 * file/code. No snippets of this code may be redistributed/published.
 * 
 * Contact information: Amit Assaraf - Email: soit@walla.com Phone: 0505964411
 * (Israel)
 *
 */

public class Camera {

	/**
	 * Camera rotation vector
	 */
	private Vector3f rotation;
	
	/**
	 * Game instance
	 */
	private Game game;
	
	/**
	 * Primary constructor
	 * @param game
	 */
	public Camera(Game game) {
		rotation = new Vector3f();
		this.game = game;
	}

	/**
	 * The position of the camera is the client's player position
	 * @return
	 */
	public Vector3f getPosition() {
		return game.getPlayers().get(0).getLocation();
	}

	public void setPosition(Vector3f position) {
		game.getPlayers().get(0).setLocation(position);
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}