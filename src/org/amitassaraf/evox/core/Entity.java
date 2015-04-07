package org.amitassaraf.evox.core;

import static org.amitassaraf.evox.core.Constants.BACK;
import static org.amitassaraf.evox.core.Constants.BACK_TRUE;
import static org.amitassaraf.evox.core.Constants.BOTTOM;
import static org.amitassaraf.evox.core.Constants.BOTTOM_TRUE;
import static org.amitassaraf.evox.core.Constants.FRONT;
import static org.amitassaraf.evox.core.Constants.FRONT_TRUE;
import static org.amitassaraf.evox.core.Constants.LEFT;
import static org.amitassaraf.evox.core.Constants.LEFT_TRUE;
import static org.amitassaraf.evox.core.Constants.RIGHT;
import static org.amitassaraf.evox.core.Constants.RIGHT_TRUE;
import static org.amitassaraf.evox.core.Constants.SIDE_BACK;
import static org.amitassaraf.evox.core.Constants.SIDE_BOTTOM;
import static org.amitassaraf.evox.core.Constants.SIDE_FRONT;
import static org.amitassaraf.evox.core.Constants.SIDE_LEFT;
import static org.amitassaraf.evox.core.Constants.SIDE_RIGHT;
import static org.amitassaraf.evox.core.Constants.SIDE_TOP;
import static org.amitassaraf.evox.core.Constants.TOP;
import static org.amitassaraf.evox.core.Constants.TOP_TRUE;
import static org.amitassaraf.evox.core.Constants.VOXEL_SIZE;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.voxels.Voxel;
import org.amitassaraf.evox.view.TextureAtlas;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

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

public abstract class Entity {

	/**
	 * Mesh builder VBO handle & VBOi Handle Also the indices Count
	 */
	private int vboHandel;
	private int indicesCount;
	private int vboiHandel;
	
	/** The speed of this entity in the space. */
	protected Vector3f speed = new Vector3f();
	
	protected final float SKIN = 0.000001f;
	
	protected boolean usingGravity = true;

	/**
	 * Entity id counter
	 */
	private static int idCounter = 0;

	/**
	 * Entity ID the entity hitbox The potential chunks this entity could
	 * collide with
	 */
	private int id;
	protected BoxAABB3 hitBox;
	private ArrayList<Chunk> collisionChunks;

	/**
	 * Primary constructor
	 * 
	 * @param location
	 * @param size
	 */
	public Entity(Vector3f location, float size) {
		id = idCounter;
		idCounter++;
		collisionChunks = new ArrayList<Chunk>();
		hitBox = new BoxAABB3(location, size);
	}

	/**
	 * Secondary constructor
	 * 
	 * @param location
	 * @param size
	 */
	public Entity(Vector3f location, Vector3f size) {
		id = idCounter;
		idCounter++;
		collisionChunks = new ArrayList<Chunk>();
		hitBox = new BoxAABB3(location, size);
	}

	/**
	 * Update entity (Mainly collision chunks)
	 * 
	 * @param game
	 */
	public void update(Game game, double delta) {
		collisionChunks.clear();

		for (Chunk node : game.getWorld().getNodes()) {
			if (hitBox.intersectsAABBNoY(node.getBoundingbox())) {
				collisionChunks.add(node);
			}
		}
		
		if (!game.getWorld().getEntityBuildQueue().contains(this))
			game.getWorld().getEntityBuildQueue().add(this);
		
		//calculateBelowBlockHeight(game);
		if (usingGravity) {
			speed.y -= Constants.WORLD_GRAVITY * delta;
		}
		move(game, (float) delta);
	}
	

	/**
	 * On hit this entity
	 * 
	 * @param w
	 * @param e
	 */
	public void onHit(VoxelWorld w, Entity e) {

	}

	/**
	 * On this entity hitting the ground
	 * 
	 * @param w
	 */
	public void onHitGround(VoxelWorld w) {

	}

	/**
	 * On this entity movement
	 * 
	 * @param w
	 */
	public void onMove(VoxelWorld w) {

	}

	/**
	 * Explode this entity
	 */
	public void explode() {
		// TODO
	}

	public void buildEntity(Game game) {
		int faceCount = 0;
		FloatBuffer buff = BufferUtils.createFloatBuffer(52*6);
		float rx = hitBox.getMinX();
		float ry = hitBox.getMinY();
		float rz = hitBox.getMinZ();

		float baseColor = .086f;

		float size = VOXEL_SIZE;
		Voxel vox = Voxel.voxels[6];

		TextureAtlas atlas = game.getResourceManager().getTextureAtlases()
				.get(vox.VoxelData.getTextureAtlas());

		byte[] lights = new byte[] { 15, 15, 15, 15, 15, 15 };

		// System.out.println(st);
		// System.out.println(ste);

		// TOP
		float colorValue = (float) (Math.pow(lights[SIDE_TOP] / 15f, 1.4f) + baseColor);
		// System.out.println(lights[SIDE_TOP]);
		Vector2f st = atlas.getCoordinate(vox.VoxelData.faceTexture[0]);
		Vector2f ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Top Right Of
															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Right
		// Of The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// BOTTOM
		colorValue = (float) (Math.pow(lights[SIDE_BOTTOM] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(vox.VoxelData.faceTexture[1]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(+ry).put(size + rz).put(1f); // Top Right Of
																// The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad
												// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// FRONT
		colorValue = (float) (Math.pow(lights[SIDE_FRONT] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(vox.VoxelData.faceTexture[2]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(ry).put(rz).put(1f); // Top Right Of The
														// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Left Of The
														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Right
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// BACK
		colorValue = (float) (Math.pow(lights[SIDE_BACK] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(vox.VoxelData.faceTexture[3]);
		ste = atlas.getEndCoordinate(st);
		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Right Of The
														// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Left Of
															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Left
																	// Of
		// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Right
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// RIGHT
		colorValue = (float) (Math.pow(lights[SIDE_RIGHT] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(vox.VoxelData.faceTexture[4]);
		ste = atlas.getEndCoordinate(st);
		buff.put(rx).put(ry).put(rz).put(1f); // Top Right Of The Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// LEFT
		colorValue = (float) (Math.pow(lights[SIDE_LEFT] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(vox.VoxelData.faceTexture[5]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Right Of
															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(size + rx).put(ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Right
		// Of The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		// OpenGL expects to draw vertices in counter clockwise order by default
		short[] indices = new short[faceCount * 6];
		short count = 0;
		for (short i = 0; i < indices.length; i += 6) {
			indices[i] = (short) (0 + (count));
			indices[i + 1] = (short) (1 + (count));
			indices[i + 2] = (short) (2 + (count));
			indices[i + 3] = (short) (2 + (count));
			indices[i + 4] = (short) (3 + (count));
			indices[i + 5] = (short) (0 + (count));
			count += 4;
		}
		indicesCount = indices.length;
		ShortBuffer indicesBuffer = BufferUtils.createShortBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		// Create a new VBO for the indices and select it (bind) - INDICES
		vboiHandel = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiHandel);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		buff.flip();

		// Create a new Vertex Buffer Object in memory and select it (bind)
		vboHandel = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandel);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public Vector3f getLocation() {
		return hitBox.getLocation();
	}

	public void setLocation(Vector3f location) {
		hitBox.setLocation(location);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Chunk> getCollisionChunks() {
		return collisionChunks;
	}

	public BoxAABB3 getEntityHitBox() {
		return hitBox;
	}

	public int getVboHandel() {
		return vboHandel;
	}

	public void setVboHandel(int vboHandel) {
		this.vboHandel = vboHandel;
	}

	public int getIndicesCount() {
		return indicesCount;
	}

	public void setIndicesCount(int indicesCount) {
		this.indicesCount = indicesCount;
	}

	public int getVboiHandel() {
		return vboiHandel;
	}

	public void setVboiHandel(int vboiHandel) {
		this.vboiHandel = vboiHandel;
	}

	private void move(Game game, float delta) {
		moveXZAxises(game, delta);
		moveYAxis(game, delta);
	}
	
	private void moveYAxis(Game game, float delta) {
		if (usingGravity) {
			Vector3f currentPos = hitBox.getLocation();
			hitBox.setLocation(currentPos.x, currentPos.y + speed.y * delta,
					currentPos.z);
			BoxAABB3 collisionBox = game.getWorld().checkEntityCollision(this, true);
			if (speed.y <= 0 && collisionBox != null &&
					collisionBox.getMinY() <= hitBox.getMaxY()) {
				hitBox.setLocation(currentPos.x, 
						collisionBox.getMinY() + hitBox.getSize().y + Constants.VOXEL_SIZE + SKIN,
						currentPos.z);
					speed.y = 0;
			}
		} else {
			
		}
	}
	
	private void moveXZAxises(Game game, float delta) {
		Vector3f oldPos = new Vector3f(hitBox.getLocation());
		hitBox.setLocation(oldPos.x + speed.x * delta, oldPos.y,
				oldPos.z + speed.z * delta);
		BoxAABB3 collisionBox = game.getWorld().checkEntityCollision(this, false);
		
		if (collisionBox != null) {
			hitBox.setLocation(oldPos);
			speed.x = 0;
			speed.z = 0;
//			System.out.println("touching");
		} else {
//			System.out.println("not touching");
		}
	}
	
	public void applyGravity(boolean newState) {
		usingGravity = newState;
	}
	
	public boolean isUsingGravity() {
		return usingGravity;
	}
	
}
