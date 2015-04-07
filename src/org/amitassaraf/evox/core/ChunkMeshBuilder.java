
package org.amitassaraf.evox.core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.amitassaraf.evox.core.voxels.Voxel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

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

public class ChunkMeshBuilder {

	/**
	 * The parent of this mesh builder
	 */
	private Chunk parent;

	/**
	 * Mesh builder VBO handle & VBOi Handle Also the indices Count
	 * for Opaque blocks
	 */
	private int vboHandelOpaque;
	private int indicesCountOpaque;
	private int vboiHandelOpaque;
	
	/**
	 * Mesh builder VBO handle & VBOi Handle Also the indices Count
	 * for Non Opaque blocks
	 */
	private int vboHandelNonOpaque;
	private int indicesCountNonOpaque;
	private int vboiHandelNonOpaque;


	/**
	 * Primary constructor
	 * 
	 * @param etn
	 */
	public ChunkMeshBuilder(Chunk etn) {
		this.parent = etn;
	}

	/**
	 * Build chunk
	 * 
	 * @param game
	 */
	public void build(Game game) {

		buildChunk(parent, game);
	}

	/**
	 * Start building the chunk
	 * 
	 * @param node
	 * @param game
	 */
	public void buildChunk(Chunk node, Game game) {
		int faceCount = 0;
		int faceCountNonOpaque = 0;
		ArrayList<Chunk> neighbours = node.getNeighbors(game.getWorld());
		FloatBuffer buff = BufferUtils.createFloatBuffer(66
				* (Constants.NODE_WIDTH) * (Constants.NODE_LENGTH)
				* (Constants.NODE_HEIGHT));
		FloatBuffer buffNonOpaque = BufferUtils.createFloatBuffer(66
				* (Constants.NODE_WIDTH) * (Constants.NODE_LENGTH)
				* (Constants.NODE_HEIGHT));
		for (int x = 0; x < node.getVoxels().length; x++) {
			for (int z = 0; z < node.getVoxels()[x].length; z++) {
				for (int y = 0; y < node.getVoxels()[x][z].length; y++) {
					if (Voxel.voxels[node.getVoxels()[x][z][y]] != null
							&& node.getVoxels()[x][z][y] != 0
							&& Voxel.voxels[node.getVoxels()[x][z][y]].VoxelData
									.isOpaque()) {
						faceCount += Voxel.voxels[node.getVoxels()[x][z][y]]
								.renderVoxel(
										buff,
										node,
										new Vector3f(x * Constants.VOXEL_SIZE,
												y * Constants.VOXEL_SIZE, z
														* Constants.VOXEL_SIZE),
										node.getVoxelsFaces()[x][z][y],
										node.getLightAt(x, y, z, neighbours),
										game.getResourceManager()
												.getTextureAtlases()
												.get(Voxel.voxels[node
														.getVoxels()[x][z][y]].VoxelData
														.getTextureAtlas()));
					} else if (Voxel.voxels[node.getVoxels()[x][z][y]] != null
							&& node.getVoxels()[x][z][y] != 0
							&& !Voxel.voxels[node.getVoxels()[x][z][y]].VoxelData
									.isOpaque()) {
						faceCountNonOpaque += Voxel.voxels[node.getVoxels()[x][z][y]]
								.renderVoxel(
										buffNonOpaque,
										node,
										new Vector3f(x * Constants.VOXEL_SIZE,
												y * Constants.VOXEL_SIZE, z
														* Constants.VOXEL_SIZE),
										node.getVoxelsFaces()[x][z][y],
										node.getLightAt(x, y, z, neighbours),
										game.getResourceManager()
												.getTextureAtlases()
												.get(Voxel.voxels[node
														.getVoxels()[x][z][y]].VoxelData
														.getTextureAtlas()));
					}
				}
			}
		}

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
		
		/**
		 *  NON _ OPAQUE 
		 * 
		 */
		
		// OpenGL expects to draw vertices in counter clockwise order by default
		indices = new int[faceCountNonOpaque * 6];
		count = 0;
		for (int i = 0; i < indices.length; i += 6) {
			indices[i] = (int) (0 + (count));
			indices[i + 1] = (int) (1 + (count));
			indices[i + 2] = (int) (2 + (count));
			indices[i + 3] = (int) (2 + (count));
			indices[i + 4] = (int) (3 + (count));
			indices[i + 5] = (int) (0 + (count));
			count += 4;
		}
		indicesCountNonOpaque = indices.length;
		indicesBuffer =  BufferUtils.createIntBuffer(indicesCountNonOpaque);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		// Create a new VBO for the indices and select it (bind) - INDICES
		vboiHandelNonOpaque = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiHandelNonOpaque);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		buffNonOpaque.flip();

		// Create a new Vertex Buffer Object in memory and select it (bind)
		vboHandelNonOpaque = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandelNonOpaque);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffNonOpaque, GL15.GL_STATIC_DRAW);

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
	
	public int getVboHandelOpaque() {
		return vboHandelOpaque;
	}

	public void setVboHandelOpaque(int vboHandelOpaque) {
		this.vboHandelOpaque = vboHandelOpaque;
	}

	public int getIndicesCountOpaque() {
		return indicesCountOpaque;
	}

	public void setIndicesCountOpaque(int indicesCountOpaque) {
		this.indicesCountOpaque = indicesCountOpaque;
	}

	public int getVboiHandelOpaque() {
		return vboiHandelOpaque;
	}

	public void setVboiHandelOpaque(int vboiHandelOpaque) {
		this.vboiHandelOpaque = vboiHandelOpaque;
	}

	public int getVboHandelNonOpaque() {
		return vboHandelNonOpaque;
	}

	public void setVboHandelNonOpaque(int vboHandelNonOpaque) {
		this.vboHandelNonOpaque = vboHandelNonOpaque;
	}

	public int getIndicesCountNonOpaque() {
		return indicesCountNonOpaque;
	}

	public void setIndicesCountNonOpaque(int indicesCountNonOpaque) {
		this.indicesCountNonOpaque = indicesCountNonOpaque;
	}

	public int getVboiHandelNonOpaque() {
		return vboiHandelNonOpaque;
	}

	public void setVboiHandelNonOpaque(int vboiHandelNonOpaque) {
		this.vboiHandelNonOpaque = vboiHandelNonOpaque;
	}

}