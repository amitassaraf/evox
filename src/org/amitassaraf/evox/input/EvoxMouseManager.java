
package org.amitassaraf.evox.input;

import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.Raytracer;
import org.amitassaraf.evox.core.EngineUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha 
 * Egoz St. House 3 Right | All rights to this code are reserved to/for
 * the author aka Amit Assaraf. Any publishing of this code without
 * authorization from the author will lead to bad consciences. Therfore
 * do not redistribute this file/code. No snippets of this code may be
 * redistributed/published.
 * 
 * Contact information:
 *   Amit Assaraf - Email: soit@walla.com Phone: 0505964411 (Israel)
 *
 */

public class EvoxMouseManager {

	/**
	 * Game instance
	 */
	private Game game;
	
	/**
	 * The delay between block remove and block placements
	 */
	private long clickDelay = System.currentTimeMillis();
	
	/**
	 * Primary constructor
	 * @param game
	 */
	public EvoxMouseManager(Game game) {
		this.game = game;
	}
	
	/**
	 * Update mouse method
	 */
	public void updateMouse() {
        if (Mouse.isGrabbed()) {
        	processMouse(1, 85, -85); //MOUSE SPEED, MAX up,MAX DOWN
        	
        	if (Mouse.isButtonDown(0) && System.currentTimeMillis()-clickDelay >= Constants.PLACE_REMOVE_DELAY) {
        		// A plotter with 0, 0, 0 as the origin and blocks that are 1x1x1.
        		// Reset view and model matrices
        		Matrix4f viewMatrix = new Matrix4f();
        		
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().x), new Vector3f(1, 0, 0), 
        				viewMatrix, viewMatrix);
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().y), new Vector3f(0, 1, 0), 
        				viewMatrix, viewMatrix);
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().z), new Vector3f(0, 0, 1), 
        				viewMatrix, viewMatrix);
        		
        		// Translate camera
        		javax.vecmath.Vector3f cPos = game.getGameDisplay().getGameCamera().getPosition();
        		javax.vecmath.Vector3f translated = new javax.vecmath.Vector3f(cPos.x + game.getPlayers().get(0).getEntityHitBox().getSize().z/2,
        				cPos.y,
        				cPos.z + game.getPlayers().get(0).getEntityHitBox().getSize().z/2);
        		
        		Matrix4f.translate(EngineUtils.toGLVector(translated), viewMatrix, viewMatrix);

        		Raytracer raytrace = new Raytracer();
        		new Thread() {
					public void run() {
		        		try {
							raytrace.raycast(new float[]{translated.x,translated.y,translated.z},
									new float[]{viewMatrix.m02*-1,viewMatrix.m12*-1,viewMatrix.m22*-1}, Constants.MAX_REACH, game.getWorld());
							if (raytrace.getVoxelCallBack() != null)
								game.getWorld().removeVoxelAt(EngineUtils.toMathVector(raytrace.getVoxelCallBack().getArrayLoc()), raytrace.getVoxelCallBack().getParent());
		        		} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}.start();
        		clickDelay = System.currentTimeMillis();
        	}
        	
        	if (Mouse.isButtonDown(1) && System.currentTimeMillis()-clickDelay >= Constants.PLACE_REMOVE_DELAY) {
        		// A plotter with 0, 0, 0 as the origin and blocks that are 1x1x1.
        		// Reset view and model matrices
        		Matrix4f viewMatrix = new Matrix4f();
        		
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().x), new Vector3f(1, 0, 0), 
        				viewMatrix, viewMatrix);
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().y), new Vector3f(0, 1, 0), 
        				viewMatrix, viewMatrix);
        		Matrix4f.rotate((float) Math.toRadians(game.getGameDisplay().getGameCamera().getRotation().z), new Vector3f(0, 0, 1), 
        				viewMatrix, viewMatrix);
        		
        		// Translate camera
        		javax.vecmath.Vector3f cPos = game.getGameDisplay().getGameCamera().getPosition();
        		javax.vecmath.Vector3f translated = new javax.vecmath.Vector3f(cPos.x + game.getPlayers().get(0).getEntityHitBox().getSize().z/2f,
        				cPos.y,
        				cPos.z + game.getPlayers().get(0).getEntityHitBox().getSize().z/2f);
        		System.out.println(translated);
        		
        		Matrix4f.translate(EngineUtils.toGLVector(translated), viewMatrix, viewMatrix);

        		Raytracer raytrace = new Raytracer();
        		new Thread() {
					public void run() {
		        		try {
							raytrace.raycast(new float[]{translated.x,translated.y,translated.z},
									new float[]{viewMatrix.m02*-1,viewMatrix.m12*-1,viewMatrix.m22*-1}, Constants.MAX_REACH, game.getWorld());
							if (raytrace.getVoxelCallBack() != null)
								game.getWorld().putVoxelAt(EngineUtils.toMathVector(raytrace.getVoxelCallBack().getArrayLoc()), raytrace.getVoxelCallBack().getParent(), raytrace.getVoxelCallBack().getFaceArr(),(byte) 2);
		        		} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}.start();
				
        		clickDelay = System.currentTimeMillis();
        	}
        }
	}
	
	/** Processes mouse input and converts it in to camera movement. */
	public void processMouse() {
		final float MAX_LOOK_UP = 90;
		final float MAX_LOOK_DOWN = -90;
		float mouseDX = Mouse.getDX() * 0.16f;
		float mouseDY = Mouse.getDY() * 0.16f;
		if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX >= 360) {
			game.getGameDisplay().getGameCamera().getRotation().y = game.getGameDisplay().getGameCamera().getRotation().y + mouseDX - 360;
		} else if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX < 0) {
			game.getGameDisplay().getGameCamera().getRotation().y = 360 - game.getGameDisplay().getGameCamera().getRotation().y + mouseDX;
		} else {
			game.getGameDisplay().getGameCamera().getRotation().y += mouseDX;
		}
		if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY >= MAX_LOOK_DOWN && game.getGameDisplay().getGameCamera().getRotation().x - mouseDY <= MAX_LOOK_UP) {
			game.getGameDisplay().getGameCamera().getRotation().x += -mouseDY;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY < MAX_LOOK_DOWN) {
			game.getGameDisplay().getGameCamera().getRotation().x = MAX_LOOK_DOWN;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY > MAX_LOOK_UP) {
			game.getGameDisplay().getGameCamera().getRotation().x = MAX_LOOK_UP;
		}
	}

	/**
	 * Processes mouse input and converts it in to camera movement.
	 *
	 * @param mouseSpeed
	 *            the speed (sensitivity) of the mouse, 1.0 should suffice
	 */
	public void processMouse(float mouseSpeed) {
		final float MAX_LOOK_UP = 90;
		final float MAX_LOOK_DOWN = -90;
		float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
		float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
		if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX >= 360) {
			game.getGameDisplay().getGameCamera().getRotation().y = game.getGameDisplay().getGameCamera().getRotation().y + mouseDX - 360;
		} else if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX < 0) {
			game.getGameDisplay().getGameCamera().getRotation().y = 360 - game.getGameDisplay().getGameCamera().getRotation().y + mouseDX;
		} else {
			game.getGameDisplay().getGameCamera().getRotation().y += mouseDX;
		}
		if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY >= MAX_LOOK_DOWN && game.getGameDisplay().getGameCamera().getRotation().x - mouseDY <= MAX_LOOK_UP) {
			game.getGameDisplay().getGameCamera().getRotation().x += -mouseDY;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY < MAX_LOOK_DOWN) {
			game.getGameDisplay().getGameCamera().getRotation().x = MAX_LOOK_DOWN;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY > MAX_LOOK_UP) {
			game.getGameDisplay().getGameCamera().getRotation().x = MAX_LOOK_UP;
		}
	}

	/**
	 * Processes mouse input and converts it into camera movement.
	 *
	 * @param mouseSpeed
	 *            the speed (sensitivity) of the mouse, 1.0 should suffice
	 * @param maxLookUp
	 *            the maximum angle in degrees at which you can look up
	 * @param maxLookDown
	 *            the maximum angle in degrees at which you can look down
	 */
	public void processMouse(float mouseSpeed, float maxLookUp,
			float maxLookDown) {
		float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
		float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
		if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX >= 360) {
			game.getGameDisplay().getGameCamera().getRotation().y = game.getGameDisplay().getGameCamera().getRotation().y + mouseDX - 360;
		} else if (game.getGameDisplay().getGameCamera().getRotation().y + mouseDX < 0) {
			game.getGameDisplay().getGameCamera().getRotation().y = 360 - game.getGameDisplay().getGameCamera().getRotation().y + mouseDX;
		} else {
			game.getGameDisplay().getGameCamera().getRotation().y += mouseDX;
		}
		if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY >= maxLookDown && game.getGameDisplay().getGameCamera().getRotation().x - mouseDY <= maxLookUp) {
			game.getGameDisplay().getGameCamera().getRotation().x += -mouseDY;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY < maxLookDown) {
			game.getGameDisplay().getGameCamera().getRotation().x = maxLookDown;
		} else if (game.getGameDisplay().getGameCamera().getRotation().x - mouseDY > maxLookUp) {
			game.getGameDisplay().getGameCamera().getRotation().x = maxLookUp;
		}
	}

}