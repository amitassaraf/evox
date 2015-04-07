package org.amitassaraf.evox.core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

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

public class StaticMeshBuilder {

	/**
	 * The parent of this mesh builder
	 */
	private Static parent;

	/**
	 * Mesh builder VBO handle & VBOi Handle Also the indices Count
	 * for Opaque blocks
	 */
	private int vboHandelOpaque;
	private int indicesCountOpaque;
	private int vboiHandelOpaque;


	/**
	 * Primary constructor
	 * 
	 * @param etn
	 */
	public StaticMeshBuilder(Static etn) {
		this.parent = etn;
	}

	/**
	 * Build chunk
	 * 
	 * @param game
	 */
	public void build(Game game) {

		buildStatic(parent, game);
	}

	/**
	 * Start building the chunk
	 * 
	 * @param node
	 * @param game
	 */
	public void buildStatic(Static parent, Game game) {
		int faceCount = 0;
		FloatBuffer buff = BufferUtils.createFloatBuffer(66 * 6);
		faceCount += parent.render(buff, game, game.getResourceManager()
				.getTextureAtlases()
				.get(parent.getTextureAtlasID()));

		// OpenGL expects to draw vertices in counter clockwise order by default
		int[] indices = new int[faceCount * 6];
		int count = 0;
		for (int i = 0; i < indices.length; i += 6) {
			indices[i] = (int) (0 + (count));
			indices[i + 1] = (int) (1 + (count));
			indices[i + 2] = (int) (2 + (count));
			indices[i + 3] = (int) (2 + (count));
			indices[i + 4] = (int) (3 + (count));
			indices[i + 5] = (int) (0 + (count));
			count += 4;
		}
		indicesCountOpaque = indices.length;
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesCountOpaque);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		// Create a new VBO for the indices and select it (bind) - INDICES
		vboiHandelOpaque = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiHandelOpaque);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		buff.flip();

		// Create a new Vertex Buffer Object in memory and select it (bind)
		vboHandelOpaque = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandelOpaque);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Create a VBO ID Handle
	 * 
	 * @return
	 */
	public static int createVBOID() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(buffer);
		return buffer.get(0);
	}

	/**
	 * GETTERS AND SETTERS
	 */
	
	public int getVboHandel() {
		return vboHandelOpaque;
	}

	public void setVboHandelOpaque(int vboHandelOpaque) {
		this.vboHandelOpaque = vboHandelOpaque;
	}

	public int getIndicesCount() {
		return indicesCountOpaque;
	}

	public void setIndicesCountOpaque(int indicesCountOpaque) {
		this.indicesCountOpaque = indicesCountOpaque;
	}

	public int getVboiHandel() {
		return vboiHandelOpaque;
	}

	public void setVboiHandel(int vboiHandelOpaque) {
		this.vboiHandelOpaque = vboiHandelOpaque;
	}

}
