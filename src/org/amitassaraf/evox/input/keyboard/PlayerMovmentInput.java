
package org.amitassaraf.evox.input.keyboard;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import org.amitassaraf.evox.core.BoxAABB3;
import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.voxels.Voxel;
import org.amitassaraf.evox.input.EvoxInputManager;
import org.amitassaraf.evox.input.EvoxMultiInput;
import org.amitassaraf.evox.net.kryo.packets.PlayerMovePacket05;
import org.lwjgl.input.Keyboard;

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

public class PlayerMovmentInput extends EvoxMultiInput {

	/**
	 * Old position of the player
	 */
	//private Vector3f oldPos;

	/**
	 * Primary constructor
	 * 
	 * @param im
	 */
	public PlayerMovmentInput(EvoxInputManager im) {
		super(im);
		//oldPos = new Vector3f();
		loc = new Vector3f();
		block = new BoxAABB3(loc, Constants.VOXEL_SIZE);
	}

	@Override
	/**
	 * Process this input
	 * @param game
	 */
	public void action(Game game) {
		long delta = System.currentTimeMillis()
				- game.getRenderManager().getLastFrame();
		if (delta == 0) {
			delta += 1;
		}
		processKeyboard(game, delta);
	}

	/**
	 * Temporary location instance and boundingbox instance
	 */
	private BoxAABB3 block;
	private Vector3f loc;

	private int count;

	/**
	 * Processes keyboard input and converts into camera movement.
	 *
	 * @param delta
	 *            the elapsed time since the last frame update in milliseconds
	 * @param speedX
	 *            the speed of the movement on the x-axis (normal = 1.0)
	 * @param speedY
	 *            the speed of the movement on the y-axis (normal = 1.0)
	 * @param speedZ
	 *            the speed of the movement on the z-axis (normal = 1.0)
	 *
	 * @throws IllegalArgumentException
	 *             if delta is 0 or delta is smaller than 0
	 */
	public void processKeyboard(Game game, float delta) {
		
		game.getEnvironmentManager().setApplyGravity(true);

		//oldPos.set(game.getGameDisplay().getGameCamera().getPosition());

		if (delta <= 0) {
			throw new IllegalArgumentException("delta " + delta
					+ " is 0 or is smaller than 0");
		}

		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP)
				|| Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN)
				|| Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				|| Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean flyDown = false; // Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
		
		int forwardMul = ((keyUp) ? -1 : 0) + ((keyDown) ? +1 : 0);
		int sideMul = ((keyRight) ? 1 : 0) + ((keyLeft) ? -1 : 0);
		
		game.getPlayers().get(0).setSpeedCameraBased(game, forwardMul, sideMul);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			game.getPlayers().get(0).jump(game);
		}
		
		/*
		float speedX = game.getPlayers().get(0).getWalkingSpeed() / 0.003f;
		float speedZ = game.getPlayers().get(0).getWalkingSpeed() / 0.003f;

		
		if (keyUp && keyRight && !keyLeft && !keyDown) {
			game.getPlayers().get(0).setSpeedCameraBased(game,  speedX * delta * 0.003f, 0, -speedZ * delta
					* 0.003f);
		}
		else if (keyUp && keyLeft && !keyRight && !keyDown) {
			game.getPlayers().get(0).setSpeedCameraBased(game, -speedX * delta * 0.003f, 0, -speedZ * delta
					* 0.003f);
		}
		else if (keyUp && !keyLeft && !keyRight && !keyDown) {
			game.getPlayers().get(0).setSpeedCameraBased(game, 0, 0, -speedZ * delta * 0.003f);
		}
		else if (keyDown && keyLeft && !keyRight && !keyUp) {
			game.getPlayers().get(0).setSpeedCameraBased(game, -speedX * delta * 0.003f, 0, speedZ * delta
					* 0.003f);
		}
		else if (keyDown && keyRight && !keyLeft && !keyUp) {
			game.getPlayers().get(0).setSpeedCameraBased(game, speedX * delta * 0.003f, 0, speedZ * delta
					* 0.003f);
		}
		else if (keyDown && !keyUp && !keyLeft && !keyRight) {
			game.getPlayers().get(0).setSpeedCameraBased(game, 0, 0, speedZ * delta * 0.003f);
		}
		else if (keyLeft && !keyRight && !keyUp && !keyDown) {
			game.getPlayers().get(0).setSpeedCameraBased(game, -speedX * delta * 0.003f, 0, 0);
		}
		else if (keyRight && !keyLeft && !keyUp && !keyDown) {
			game.getPlayers().get(0).setSpeedCameraBased(game, speedX * delta * 0.003f, 0, 0);
		}
		else {
			game.getPlayers().get(0).setSpeedCameraBased(game, 0, 0, 0);
		}
		/*
		if (flyUp && !flyDown) {
			game.getGameDisplay().getGameCamera().getPosition().y += speedY
					* delta * 0.003f;
		}

		if (flyDown && !flyUp) {
			game.getGameDisplay().getGameCamera().getPosition().y -= speedY
					* delta * 0.003f;
		}		

		/*if (!oldPos.equals(game.getGameDisplay().getGameCamera().getPosition())) {
			updatePlayer(game);
			
			updatePlayerTargetHeight(game);
			
			loc.set(0, 0, 0);
			for (int i = 0; i < game.getPlayers().get(0).getCollisionChunks().size(); i++) {
				Chunk node = game.getPlayers().get(0).getCollisionChunks().get(i);
				if (game.getPlayers().get(0).getEntityHitBox()
						.intersectsAABB(node.getBoundingbox())) {
					loc.set(node.getBoundingbox().getLocation());
					block.setLocation(loc);
					for (int x = 0; x < node.getVoxels().length; x++) {
						for (int z = 0; z < node.getVoxels()[x].length; z++) {
							for (int y = 0; y < node.getVoxels()[x][z].length; y++) {
								if (node.getVoxels()[x][z][y] == 0 || !Voxel.voxels[node.getVoxels()[x][z][y]].VoxelData.isSolid())
									continue;
								loc.set(node.getBoundingbox().getLocation().x
										+ (x * Constants.VOXEL_SIZE), node
										.getBoundingbox().getLocation().y
										+ (y * Constants.VOXEL_SIZE), node
										.getBoundingbox().getLocation().z
										+ (z * Constants.VOXEL_SIZE));
								block.setLocation(loc);

								if (node.getVoxels()[x][z][y] != 0 && game.getPlayers().get(0)
										.getEntityHitBox()
										.intersectsAABB(block)) {
									
									if (block.getMinY() < game.getPlayers().get(0).getEntityHitBox().getMaxY()) {
										//Falling
										game.getPlayers().get(0).getEntityHitBox().setLocation(
												game.getPlayers().get(0).getEntityHitBox().getMinX(),
												block.getMinY() - game.getPlayers().get(0).getEntityHitBox().getSize().y + block.getSize().y,
												game.getPlayers().get(0).getEntityHitBox().getMinZ());
										game.getEnvironmentManager().setApplyGravity(false);
									} else {
										game.getPlayers().get(0).getLocation().set(oldPos);
									}
									// game.getPlayers().get(0) //
									// .onMove(game.getWorld());
									// game.getRenderManager()
									// .getRenderers() //
									// .get(GameState.GAME)) //
									// .getSkybox().build(game);
								}

							}
						}
					}
				}
				updatePlayer(game);
			}
		}*/
	}

	public void updatePlayer(Game game) {
		count += 1;
		game.getPlayers().get(0).update(game, true);

		/**
		 * Send to the network our new position
		 */
		if (game.getNetworkManager().isConnected() && count >= 4) {
			count = 0;
			game.getNetworkManager().sendPacket(
					new PlayerMovePacket05()
							.setUsername(
									game.getNetworkManager()
											.getNetworkIdentifier())
							.setX(game.getGameDisplay().getGameCamera()
									.getPosition().x)
							.setY(game.getGameDisplay().getGameCamera()
									.getPosition().y)
							.setZ(game.getGameDisplay().getGameCamera()
									.getPosition().z));
		}
	}
	
	/**
	 * Move in the direction you're looking. That is, this method assumes a new
	 * coordinate system where the axis you're looking down is the z-axis, the
	 * axis to your left is the x-axis, and the upward axis is the y-axis.
	 *
	 * @param dx
	 *            the movement along the x-axis
	 * @param dy
	 *            the movement along the y-axis
	 * @param dz
	 *            the movement along the z-axis
	 */
	/*
	public void moveFromLook(Game game, float dx, float dy, float dz) {
		game.getGameDisplay().getGameCamera().getPosition().z += dx
				* (float) cos(toRadians(game.getGameDisplay().getGameCamera()
						.getRotation().y - 90))
				+ dz
				* cos(toRadians(game.getGameDisplay().getGameCamera()
						.getRotation().y));
		game.getGameDisplay().getGameCamera().getPosition().x -= dx
				* (float) sin(toRadians(game.getGameDisplay().getGameCamera()
						.getRotation().y - 90))
				+ dz
				* sin(toRadians(game.getGameDisplay().getGameCamera()
						.getRotation().y));
		// game.getGameDisplay().getGameCamera().getPosition().y += dy * (float)
		// sin(toRadians(game.getGameDisplay().getGameCamera().getRotation().x -
		// 90)) + dz
		// *
		// sin(toRadians(game.getGameDisplay().getGameCamera().getRotation().x));
	}*/

}