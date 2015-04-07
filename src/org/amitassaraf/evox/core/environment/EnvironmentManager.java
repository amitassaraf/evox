package org.amitassaraf.evox.core.environment;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.BoxAABB3;
import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.GameState;
import org.amitassaraf.evox.view.renderers.GameRenderer;
import org.lwjgl.opengl.GL11;

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

public class EnvironmentManager {

	/**
	 * Game instance
	 */
	private Game game;

	/**
	 * Boolean is gravity being applied
	 */
	private boolean gravity = false;

	/**
	 * Boolean to check if gravity SHOULD be applied
	 */
	private boolean applyGravity;
	
	/**
	 * Time of last time update
	 */
	private long delaySinceTimeUpdate = System.currentTimeMillis();

	/**
	 * Cloud manager instance
	 */
	private CloudManager cloudManager;

	/**
	 * Delay between the movement of clouds
	 */
	private long delayOfMove = System.currentTimeMillis();

	/**
	 * Time divisor
	 */
	private float div = 0.01f;

	private int prevWorldLightUpdate;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public EnvironmentManager(Game game) {
		this.game = game;
		this.cloudManager = new CloudManager(game);
		cloudManager.build(game);
	}

	/**
	 * Update the environment
	 */
	public void update() {

		dayNightCycle();
		moveClouds();
	}

	/**
	 * Method to move the clouds
	 */
	private void moveClouds() {
		if (System.currentTimeMillis() - delayOfMove >= Constants.CLOUD_MOVEMENT_INTERVAL) {
			for (Cloud c : cloudManager.getClouds()) {
				if (c.getBoundingBox().getLocation().z >= Constants.NODE_WIDTH
						* Constants.NODE_AMOUNT) {
					c.getBoundingBox()
							.getLocation()
							.set(c.getBoundingBox().getLocation().x,
									c.getBoundingBox().getLocation().y, 0);
				} else {
					c.getBoundingBox()
							.getLocation()
							.set(c.getBoundingBox().getLocation().x,
									c.getBoundingBox().getLocation().y,
									c.getBoundingBox().getLocation().z + 3);
				}
			}
			cloudManager.build(game);
			delayOfMove = System.currentTimeMillis();
		}
	}

	/**
	 * Day and night cycle update method
	 */
	private void dayNightCycle() {
		if (System.currentTimeMillis() - delaySinceTimeUpdate >= Constants.TICK_TIME_MS * 10) {
			long timeOfDay = game.getTimeManager().getTimeOfDay();
			// System.out.println(timeOfDay);
			if (timeOfDay >= 23000) {
				timeOfDay = 0;
				game.getTimeManager().setTimeOfDay(0);
				div = 0.01f;
			}
			if (timeOfDay >= Constants.NIGHT && div <= 3.5) {
				GL11.glClearColor((float) ((0.05f) / (div * 2)),
						(float) ((0.95f) / (div * 2)),
						(float) ((0.825f) / (div * 2)), 0.0f); // Blueish
				if (div < 1)
					Constants.ambientLight.set(0.825f / (div + 1),
							0.8f / (div + 1), 0.825f / (div + 1));
				else
					Constants.ambientLight.set(0.425f / (div), 0.4f / (div),
							0.425f / (div));
				delaySinceTimeUpdate = System.currentTimeMillis();
				div += 0.005;

				if ((((int) timeOfDay - 13500) / 500) == 8 - prevWorldLightUpdate
						&& prevWorldLightUpdate > 1) {
					// System.out.println(prevWorldLightUpdate);
					prevWorldLightUpdate -= 1;
					game.getWorld().updateWorldLight();
				}
				// System.out.println(div);
			} else if (timeOfDay <= Constants.DAY) {
				if (timeOfDay < 1000)
					timeOfDay = 1000;

				if (((int) timeOfDay / 500) > prevWorldLightUpdate
						&& prevWorldLightUpdate < 7) {
					// System.out.println((int) timeOfDay / 500);
					prevWorldLightUpdate = ((int) timeOfDay / 500);
					game.getWorld().updateWorldLight();
				}

				GL11.glClearColor((0.1f) * (timeOfDay / 1000f),
						(0.3f) * (timeOfDay / 1000f),
						(0.272f) * (timeOfDay / 1000f), 0.0f); // Blueish
				Constants.ambientLight.set(0.175f * (timeOfDay / 700f),
						0.175f * (timeOfDay / 700f),
						0.175f * (timeOfDay / 700f));
				delaySinceTimeUpdate = System.currentTimeMillis();
			}
		}
	}

	/**
	 * Method that applies gravity to the player
	 */

	// int count = 0;

	/**
	 * GETTERS AND SETTERS
	 */

	public boolean isGravity() {
		return gravity;
	}

	public CloudManager getCloudManager() {
		return cloudManager;
	}

	public int getPrevWorldLightUpdate() {
		return prevWorldLightUpdate;
	}

	public void setPrevWorldLightUpdate(int prevWorldLightUpdate) {
		this.prevWorldLightUpdate = prevWorldLightUpdate;
	}


	public boolean isApplyGravity() {
		return applyGravity;
	}

	public void setApplyGravity(boolean applyGravity) {
		this.applyGravity = applyGravity;
	}
	
}
