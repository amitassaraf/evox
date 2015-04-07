package org.amitassaraf.evox.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
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

public class Skybox {

	/**
	 * Skybox VBO Handle and VBOi Handle
	 */
	private int vboHandel;
	private int vboiHandel;

	/**
	 * Skybox texture atlas ID and face texture numbers
	 */
	private int textureAtlasID;
	private byte[] faceTextures;

	/**
	 * Indices count
	 */
	private int indicesCount;

	/**
	 * Primary constructor
	 */
	public Skybox() {
		textureAtlasID = 2;
		faceTextures = new byte[] { 1, 9, 5, 7, 4, 6 };
	}

	/**
	 * Method to build the skybox
	 * 
	 * @param game
	 */
	public void build(Game game) {
		FloatBuffer buff = BufferUtils.createFloatBuffer(66 * 6);
		float size = Constants.FAR_DISTANCE;
		
		float rx = game.getPlayers().get(0).getEntityHitBox().getLocation().x-size/2;
		float ry = game.getPlayers().get(0).getEntityHitBox().getLocation().y-size/2;
		float rz = game.getPlayers().get(0).getEntityHitBox().getLocation().z-size/2;

		float red = 1f;
		float blue = 1f;
		float green = 1f;

		int faceCount = 0;

		TextureAtlas atlas = game.getResourceManager().getTextureAtlases()
				.get(textureAtlasID);

		// TOP
		Vector2f st = atlas.getCoordinate(this.faceTextures[0]);
		Vector2f ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Top Right Of
															// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Right
		// Of The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		st = atlas.getCoordinate(this.faceTextures[1]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(+ry).put(size + rz).put(1f); // Top Right Of
																// The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad
												// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		st = atlas.getCoordinate(this.faceTextures[2]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(ry).put(rz).put(1f); // Top Right Of The
														// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Left Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Right
															// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		st = atlas.getCoordinate(this.faceTextures[3]);
		ste = atlas.getEndCoordinate(st);
		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Right Of The
														// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Left Of
															// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Left
																	// Of
		// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Right
															// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		st = atlas.getCoordinate(this.faceTextures[4]);
		ste = atlas.getEndCoordinate(st);
		buff.put(rx).put(ry).put(rz).put(1f); // Top Right Of The Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(red).put(green).put(green).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		st = atlas.getCoordinate(this.faceTextures[5]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Right Of
															// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(size + rx).put(ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Right
		// Of The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(1f);
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
	
	public int getTextureAtlasID() {
		return textureAtlasID;
	}

	public int getVboiHandel() {
		return vboiHandel;
	}

	public void setVboiHandel(int vboiHandel) {
		this.vboiHandel = vboiHandel;
	}

	public int getVboHandel() {
		return vboHandel;
	}

	public void setVboHandel(int vboHandel) {
		this.vboHandel = vboHandel;
	}

	public void setTextureAtlasID(int textureAtlasID) {
		this.textureAtlasID = textureAtlasID;
	}

	public byte[] getFaceTextures() {
		return faceTextures;
	}

	public void setFaceTextures(byte[] faceTextures) {
		this.faceTextures = faceTextures;
	}

	public int getIndicesCount() {
		return indicesCount;
	}

	public void setIndicesCount(int indicesCount) {
		this.indicesCount = indicesCount;
	}

}
