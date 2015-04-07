
package org.amitassaraf.evox.core;

import javax.swing.LookAndFeel;
import javax.vecmath.Vector3f;

import org.amitassaraf.evox.view.BoxRenderer;

import com.sun.org.apache.xpath.internal.axes.WalkingIterator;

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

public class EntityPlayer extends EntityAlive {
	
	private String username = "Me";
	private BoxRenderer render;
	
	/**
	 * Primary constructor
	 * 
	 * @param location
	 */
	public EntityPlayer(Vector3f location) {
		super(location, new Vector3f(7, 15, 7));
		render = new BoxRenderer(getEntityHitBox());
	}
	
	/**
	 * Primary constructor
	 * 
	 * @param location
	 */
	public EntityPlayer(Vector3f location,String username) {
		super(location, new Vector3f(7, 15, 7));
		render = new BoxRenderer(getEntityHitBox());
		this.username = username;
	}
	
	/**
	 * Update entity (Mainly collision chunks)
	 * 
	 * @param game
	 */
	public void update(Game game, boolean isPlayer) {
		super.getCollisionChunks().clear();

		for (Chunk node : game.getWorld().getNodes()) {
			if (super.getEntityHitBox().intersectsAABBNoY(node.getBoundingbox())) {
				super.getCollisionChunks().add(node);
			}
		}
		
		if (!isPlayer && !game.getWorld().getEntityBuildQueue().contains(this)) { //Dont build, if its only you!
			game.getWorld().getEntityBuildQueue().add(this);
		}
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSpeedCameraBased(Game game, int forwardMul, int sideMul) {
		Vector3f cameraRotation = game.getGameDisplay().getGameCamera().getRotation();
		double cameraAngle = cameraRotation.y;
		double fact = 1;
		if (forwardMul != 0 && sideMul != 0) {
			fact = 1.0 / Math.sqrt(2);
		}
		float dx = sideMul * walkingSpeed;
		float dz = forwardMul * walkingSpeed;
		speed.z = (float) (dx
				* (float) Math.cos(Math.toRadians(cameraAngle - 90))
				+ dz
				* Math.cos(Math.toRadians(cameraAngle)));
		speed.z *= fact;
		speed.x = (float) -( dx
				* (float) Math.sin(Math.toRadians(cameraAngle - 90))
				+ dz
				* Math.sin(Math.toRadians(cameraAngle)));
		speed.x *= fact;
	}

	public BoxRenderer getRender() {
		return render;
	}

	public void setRender(BoxRenderer render) {
		this.render = render;
	}

}