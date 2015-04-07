package org.amitassaraf.evox.core.environment;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;
import javax.vecmath.Vector3f;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

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

public class CloudManager {

	/**
	 * Random object to generate random location for clouds
	 */
	private static Random rand = new Random();

	/**
	 * List of clouds and their VBO and VBOi Handles
	 */
	private ArrayList<Cloud> clouds;
	private int vboHandel;
	private int vboiHandel;
	private int indicesCount;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public CloudManager(Game game) {
		clouds = new ArrayList<Cloud>();
		int max = rand.nextInt(25) + 20;
		for (int i = 0; i < max; i++) {
			clouds.add(new Cloud(
					new Vector3f(
							rand.nextInt((int) (Constants.NODE_WIDTH * Constants.NODE_AMOUNT)),
							Constants.CLOUD_HEIGHT_IN_SKY,
							rand.nextInt((int) (Constants.NODE_WIDTH * Constants.NODE_AMOUNT)))));
		}
	}

	/**
	 * The build method for the clouds
	 * 
	 * @param game
	 */
	public void build(Game game) {
		int faceCount = 0;
		FloatBuffer buff = BufferUtils
				.createFloatBuffer(66 * 6 * clouds.size());
		for (Cloud cloud : clouds) {
			faceCount += cloud.render(buff, game, null);
		}

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

	public int getVboHandel() {
		return vboHandel;
	}

	public void setVboHandel(int vboHandel) {
		this.vboHandel = vboHandel;
	}

	public int getVboiHandel() {
		return vboiHandel;
	}

	public void setVboiHandel(int vboiHandel) {
		this.vboiHandel = vboiHandel;
	}

	public int getIndicesCount() {
		return indicesCount;
	}

	public void setIndicesCount(int indicesCount) {
		this.indicesCount = indicesCount;
	}

	public ArrayList<Cloud> getClouds() {
		return clouds;
	}

}
