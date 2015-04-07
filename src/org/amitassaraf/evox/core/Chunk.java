
package org.amitassaraf.evox.core;

import org.amitassaraf.evox.core.voxels.Voxel;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

import static org.amitassaraf.evox.core.Constants.*;

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

public class Chunk {

	/**
	 * Chunk mesh builder - This will build the chunk to be rendered.
	 */
	private ChunkMeshBuilder ecmb;

	/**
	 * The chunk boundingbox and Voxel volume data
	 */
	private BoxAABB3 boundingbox;
	private byte[][][] voxels;
	/*
	 * Byte data format: LLLL
	 */
	/**
	 * Extra chunk data such as faces and lighting
	 */
	private byte[][][] lighting;
	private byte[][][] voxelsFaces;

	/**
	 * Primary cocnstructor
	 * 
	 * @param boundingbox
	 * @param voxels
	 */
	public Chunk(BoxAABB3 boundingbox, byte[][][] voxels) {
		this.boundingbox = boundingbox;
		this.voxels = voxels;
		voxelsFaces = new byte[voxels.length][voxels[0].length][voxels[0][0].length];
		lighting = new byte[Constants.NODE_WIDTH][Constants.NODE_LENGTH][Constants.NODE_HEIGHT];
		ecmb = new ChunkMeshBuilder(this);
		resetAllFaces();
	}

	/**
	 * Secondary constructor
	 * 
	 * @param boundingbox
	 */
	public Chunk(BoxAABB3 boundingbox) {
		this.boundingbox = boundingbox;
		lighting = new byte[Constants.NODE_WIDTH][Constants.NODE_LENGTH][Constants.NODE_HEIGHT];
		voxels = new byte[Constants.NODE_WIDTH][Constants.NODE_LENGTH][Constants.NODE_HEIGHT];
		voxelsFaces = new byte[voxels.length][voxels[0].length][voxels[0][0].length];
		ecmb = new ChunkMeshBuilder(this);
		resetAllFaces();
	}

	/**
	 * Reset all face data in chunk
	 */
	public void resetAllFaces() {
		for (int x = 0; x < voxels.length; x++) {
			for (int z = 0; z < voxels[x].length; z++) {
				for (int y = 0; y < voxels[x][z].length; y++) {
					voxelsFaces[x][z][y] = TOP | BOTTOM | FRONT | BACK | RIGHT
							| LEFT;
				}
			}
		}
	}

	/**
	 * Get light value around this coordinate
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public byte[] getLightAt(int x, int y, int z, ArrayList<Chunk> neigh) {
		byte[] arr = new byte[6];
		if (y + 1 < lighting[0][0].length
				&& (voxels[x][z][y + 1] == 0 || Voxel.voxels[voxels[x][z][y + 1]].VoxelData
						.isLightSource()) || !Voxel.voxels[voxels[x][z][y + 1]].VoxelData.isOpaque())
			arr[SIDE_TOP] = lighting[x][z][y + 1];
		if (y - 1 >= 0
				&& (voxels[x][z][y - 1] == 0 || Voxel.voxels[voxels[x][z][y - 1]].VoxelData
						.isLightSource() || !Voxel.voxels[voxels[x][z][y - 1]].VoxelData.isOpaque()))
			arr[SIDE_BOTTOM] = lighting[x][z][y - 1];
		if (z - 1 >= 0
				&& (voxels[x][z - 1][y] == 0 || Voxel.voxels[voxels[x][z - 1][y]].VoxelData
						.isLightSource() || !Voxel.voxels[voxels[x][z - 1][y]].VoxelData.isOpaque()))
			arr[SIDE_FRONT] = lighting[x][z - 1][y];
		else if (z - 1 < 0) {
			for (Chunk c : neigh) {
				if (!c.equals(this)
						&& this.getDirectionToChunk(c) == SIDE_FRONT) {
					arr[SIDE_FRONT] = c.getLighting()[x][c.getLighting()[0].length - 1][y];
					if (arr[SIDE_FRONT] == 0)
						arr[SIDE_FRONT] = 1;
					break;
				}
			}
		}
		if (z + 1 < lighting[0].length
				&& (voxels[x][z + 1][y] == 0 || Voxel.voxels[voxels[x][z + 1][y]].VoxelData
						.isLightSource() || !Voxel.voxels[voxels[x][z + 1][y]].VoxelData.isOpaque()))
			arr[SIDE_BACK] = lighting[x][z + 1][y];
		else if (z + 1 >= lighting[0].length) {
			for (Chunk c : neigh) {
				if (!c.equals(this) && this.getDirectionToChunk(c) == SIDE_BACK) {
					arr[SIDE_BACK] = c.getLighting()[x][0][y];
					if (arr[SIDE_BACK] == 0)
						arr[SIDE_BACK] = 1;
					break;
				}
			}
		}
		if (x - 1 >= 0
				&& (voxels[x - 1][z][y] == 0 || Voxel.voxels[voxels[x - 1][z][y]].VoxelData
						.isLightSource() || !Voxel.voxels[voxels[x - 1][z][y]].VoxelData.isOpaque()))
			arr[SIDE_RIGHT] = lighting[x - 1][z][y];
		else if (x - 1 < 0) {
			for (Chunk c : neigh) {
				if (!c.equals(this)
						&& this.getDirectionToChunk(c) == SIDE_RIGHT) {
					arr[SIDE_RIGHT] = c.getLighting()[c.getLighting().length - 1][z][y];
					if (arr[SIDE_RIGHT] == 0)
						arr[SIDE_RIGHT] = 1;
					break;
				}
			}
		}
		if (x + 1 < lighting.length
				&& (voxels[x + 1][z][y] == 0 || Voxel.voxels[voxels[x + 1][z][y]].VoxelData
						.isLightSource() || !Voxel.voxels[voxels[x + 1][z][y]].VoxelData.isOpaque()))
			arr[SIDE_LEFT] = lighting[x + 1][z][y];
		else if (x + 1 >= lighting.length) {
			for (Chunk c : neigh) {
				if (!c.equals(this) && this.getDirectionToChunk(c) == SIDE_LEFT) {
					arr[SIDE_LEFT] = c.getLighting()[0][z][y];
					if (arr[SIDE_LEFT] == 0)
						arr[SIDE_LEFT] = 1;
					break;
				}
			}
		}
		return arr;
	}

	/**
	 * Lighting update constants
	 */
	ArrayList<Vector3f> memory = new ArrayList<Vector3f>();
	ArrayList<Vector3f> lightList = new ArrayList<Vector3f>();

	boolean flag;

	/**
	 * update Chunk lighting values
	 * 
	 * @param game
	 * @throws Exception
	 */
	public void updateLightingValues(Game game) throws Exception {
		lightList.clear();
		lighting = new byte[Constants.NODE_WIDTH][Constants.NODE_LENGTH][Constants.NODE_HEIGHT];

		for (int x = lighting.length - 1; x >= 0; x--) {
			for (int z = lighting[x].length - 1; z >= 0; z--) {
				flag = false;
				for (int y = lighting[x][z].length - 1; y >= 0; y--) {
					if (flag
							&& (voxels[x][z][y] == 0 || !Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque())) {
						lighting[x][z][y] = (byte) (game
								.getEnvironmentManager()
								.getPrevWorldLightUpdate() < 6
								&& game.getTimeManager().getTimeOfDay() < 3500 ? game
								.getEnvironmentManager()
								.getPrevWorldLightUpdate() : 6);
					} else if (voxels[x][z][y] != 0
							&& Voxel.voxels[voxels[x][z][y]].VoxelData
									.isLightSource()) {
						lighting[x][z][y] = 15;
						lightList.add(new Vector3f(x, y, z));
					} else if (voxels[x][z][y] == 0
							|| !Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque()
					// && y - 1 >= 0
					// && voxels[x][z][y - 1] != 0
					// && !Voxel.voxels[voxels[x][z][y - 1]]
					// .getVoxelData().isLightSource()
					) {
						lighting[x][z][y] = (byte) (game
								.getEnvironmentManager()
								.getPrevWorldLightUpdate() * 2 < 12
								&& game.getTimeManager().getTimeOfDay() < 3500 ? game
								.getEnvironmentManager()
								.getPrevWorldLightUpdate() * 2 : 12);
						// flag = true;

					} else if (voxels[x][z][y] != 0) {
						flag = true;
					}
				}
			}
		}

		memory.clear();
		ArrayList<Chunk> neighbors = this.getNeighbors(game.getWorld());
		// updateSideAccordingToLights(neighbors);

		while (lightList.size() > 0) {
			Vector3f cube = lightList.get(0);
			if (!memory.contains(cube)
					&& lighting[(int) cube.x][(int) cube.z][(int) cube.y] > 1) {
				addTransparentNeighbors(lightList, cube, neighbors);
				memory.add(cube);
				lightList.remove(cube);
			}
			if (memory.contains(cube)) {
				byte tmp = (byte) (maxNeighborLight(cube, neighbors) - 1);
				if (tmp > lighting[(int) cube.x][(int) cube.z][(int) cube.y]
						&& (voxels[(int) cube.x][(int) cube.z][(int) cube.y] == 0 || !Voxel.voxels[voxels[(int) cube.x][(int) cube.z][(int) cube.y]].VoxelData
								.isLightSource()))
					lighting[(int) cube.x][(int) cube.z][(int) cube.y] = tmp;
				lightList.remove(cube);
			} else {
				byte light = (byte) (maxNeighborLight(cube, neighbors) - 1);

				if (light == 0
						|| (voxels[(int) cube.x][(int) cube.z][(int) cube.y] != 0 && Voxel.voxels[voxels[(int) cube.x][(int) cube.z][(int) cube.y]].VoxelData
								.isLightSource()))
					break;

				lighting[(int) cube.x][(int) cube.z][(int) cube.y] = light;

				if (lighting[(int) cube.x][(int) cube.z][(int) cube.y] == 1)
					break;
			}
		}

	}

	/**
	 * Update the lighting on every side according to it's neighbor
	 * 
	 * @param neighbors
	 */
	private void updateSideAccordingToLights(ArrayList<Chunk> neighbors) {
		for (Chunk c : neighbors) {
			if (c.equals(this)) {
				continue;
			}
			switch (this.getDirectionToChunk(c)) {
			case SIDE_RIGHT: {
				for (int z = 0; z < lighting[0].length; z++) {
					for (int y = lighting[0][0].length - 1; y >= 0; y--) {
						if (c.getVoxels()[c.getVoxels().length - 1][z][y] == 0
								&& voxels[0][z][y] == 0
								&& c.getLighting()[c.getLighting().length - 1][z][y] - 1 > lighting[0][z][y]) {

							lighting[0][z][y] = (byte) (c.getLighting()[c
									.getLighting().length - 1][z][y] - 1);
							// if (lighting[0][z][y] > 1) {
							// lightList.add(new Vector3f(0, y, z));
							// }
						}

					}
				}
			}
				break;
			case SIDE_LEFT: {
				for (int z = 0; z < lighting[0].length; z++) {
					for (int y = lighting[0][0].length - 1; y >= 0; y--) {
						if (c.getVoxels()[0][z][y] == 0
								&& voxels[lighting.length - 1][z][y] == 0
								&& c.getLighting()[0][z][y] - 1 > lighting[lighting.length - 1][z][y]) {
							lighting[lighting.length - 1][z][y] = (byte) (c
									.getLighting()[0][z][y] - 1);
							// if (lighting[lighting.length - 1][z][y] > 1) {
							// lightList.add(new Vector3f(lighting.length - 1,
							// y, z));
							// }
						}
					}
				}
			}
				break;
			case SIDE_FRONT: {
				for (int x = 0; x < lighting.length; x++) {
					for (int y = lighting[0][0].length - 1; y >= 0; y--) {
						if (c.getVoxels()[x][lighting[0].length - 1][y] == 0
								&& voxels[x][0][y] == 0
								&& c.getLighting()[x][lighting[0].length - 1][y] - 1 > lighting[x][0][y]) {
							lighting[x][0][y] = (byte) (c.getLighting()[x][lighting[0].length - 1][y] - 1);
							// if (lighting[x][0][y] > 1) {
							// lightList.add(new Vector3f(x, y, 0));
							// }
						}
					}
				}
			}
				break;
			case SIDE_BACK: {
				for (int x = 0; x < lighting.length; x++) {
					for (int y = lighting[0][0].length - 1; y >= 0; y--) {
						if (c.getVoxels()[x][0][y] == 0
								&& voxels[x][lighting[0].length - 1][y] == 0
								&& c.getLighting()[x][0][y] - 1 > lighting[x][lighting[0].length - 1][y]) {
							lighting[x][lighting[0].length - 1][y] = (byte) (c
									.getLighting()[x][0][y] - 1);
							// if (lighting[x][lighting[0].length - 1][y] > 1) {
							// lightList.add(new Vector3f(x, y,
							// lighting[0].length - 1));
							// }
						}
					}
				}
			}
				break;
			case SIDE_TOP:
				System.err.println("implement");
				break;
			case SIDE_BOTTOM:
				System.err.println("implement");
				break;
			default:
				System.out.println("ERROR!");
				break;
			}
		}

	}

	/**
	 * Get the max light value around vector
	 * 
	 * @param cube
	 * @param neighbors
	 * @return
	 */
	private byte maxNeighborLight(Vector3f cube, ArrayList<Chunk> neighbors) {
		byte max = -128;

		if (cube.x - 1 >= 0
				&& lighting[(int) cube.x - 1][(int) cube.z][(int) cube.y] > max)
			max = lighting[(int) cube.x - 1][(int) cube.z][(int) cube.y];

		if (cube.x + 1 < lighting.length
				&& lighting[(int) cube.x + 1][(int) cube.z][(int) cube.y] > max)
			max = lighting[(int) cube.x + 1][(int) cube.z][(int) cube.y];

		if (cube.z - 1 >= 0
				&& lighting[(int) cube.x][(int) cube.z - 1][(int) cube.y] > max)
			max = lighting[(int) cube.x][(int) cube.z - 1][(int) cube.y];

		if (cube.z + 1 < lighting[0].length
				&& lighting[(int) cube.x][(int) cube.z + 1][(int) cube.y] > max)
			max = lighting[(int) cube.x][(int) cube.z + 1][(int) cube.y];

		if (cube.y - 1 >= 0
				&& lighting[(int) cube.x][(int) cube.z][(int) cube.y - 1] > max)
			max = lighting[(int) cube.x][(int) cube.z][(int) cube.y - 1];

		if (cube.y + 1 < lighting[0][0].length
				&& lighting[(int) cube.x][(int) cube.z][(int) cube.y + 1] > max)
			max = lighting[(int) cube.x][(int) cube.z][(int) cube.y + 1];

		return max;
	}

	/**
	 * Add all transparent cubes that are neighboring the vector
	 * 
	 * @param lightList
	 * @param cube
	 * @param neighbours
	 */
	private void addTransparentNeighbors(ArrayList<Vector3f> lightList,
			Vector3f cube, ArrayList<Chunk> neibours) {
		if (cube.x - 1 >= 0
				&& voxels[(int) cube.x - 1][(int) cube.z][(int) cube.y] == 0
				|| !Voxel.voxels[voxels[(int) cube.x - 1][(int) cube.z][(int) cube.y]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x - 1, cube.y, cube.z));

		if (cube.x + 1 < voxels.length
				&& voxels[(int) cube.x + 1][(int) cube.z][(int) cube.y] == 0
				|| !Voxel.voxels[voxels[(int) cube.x + 1][(int) cube.z][(int) cube.y]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x + 1, cube.y, cube.z));

		if (cube.z - 1 >= 0
				&& voxels[(int) cube.x][(int) cube.z - 1][(int) cube.y] == 0
				|| !Voxel.voxels[voxels[(int) cube.x][(int) cube.z - 1][(int) cube.y]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x, cube.y, cube.z - 1));

		if (cube.z + 1 < voxels[0].length
				&& voxels[(int) cube.x][(int) cube.z + 1][(int) cube.y] == 0
				|| !Voxel.voxels[voxels[(int) cube.x][(int) cube.z + 1][(int) cube.y]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x, cube.y, cube.z + 1));

		if (cube.y - 1 >= 0
				&& voxels[(int) cube.x][(int) cube.z][(int) cube.y - 1] == 0
				|| !Voxel.voxels[voxels[(int) cube.x][(int) cube.z][(int) cube.y - 1]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x, cube.y - 1, cube.z));

		if (cube.y + 1 < voxels[0][0].length
				&& voxels[(int) cube.x][(int) cube.z][(int) cube.y + 1] == 0
				|| !Voxel.voxels[voxels[(int) cube.x][(int) cube.z][(int) cube.y + 1]].VoxelData
						.isOpaque())
			lightList.add(new Vector3f(cube.x, cube.y + 1, cube.z));

	}

	/**
	 * Get opposite side from the one given
	 * 
	 * @param dir
	 * @return
	 */
	public int getOtherSide(int dir) {
		switch (dir) {
		case SIDE_TOP:
			return SIDE_BOTTOM;
		case SIDE_BOTTOM:
			return SIDE_TOP;
		case SIDE_LEFT:
			return SIDE_RIGHT;
		case SIDE_RIGHT:
			return SIDE_LEFT;
		case SIDE_FRONT:
			return SIDE_BACK;
		case SIDE_BACK:
			return SIDE_FRONT;
		default:
			return -1;
		}
	}

	/**
	 * Hide all faces of chunk in a certain direction
	 * 
	 * @param dir
	 */
	public void hideAllFaces(int dir) {
		switch (dir) {
		case SIDE_TOP: {
			for (int x = 0; x < voxelsFaces.length; x++) {
				for (int z = 0; z < voxelsFaces[x].length; z++) {
					if (((voxelsFaces[x][z][0] ^ TOP) & TOP) != TOP_TRUE)
						voxelsFaces[x][z][0] ^= TOP;
				}
			}
			break;
		}
		case SIDE_BOTTOM: {
			for (int x = 0; x < voxelsFaces.length; x++) {
				for (int z = 0; z < voxelsFaces[x].length; z++) {
					if (((voxelsFaces[x][z][voxelsFaces[x][z].length - 1] ^ BOTTOM) & BOTTOM) != BOTTOM_TRUE)
						voxelsFaces[x][z][voxelsFaces[x][z].length - 1] ^= BOTTOM;
				}
			}
			break;
		}
		case SIDE_RIGHT: {
			for (int z = 0; z < voxelsFaces[0].length; z++) {
				for (int y = 0; y < voxelsFaces[0][z].length; y++) {
					if (((voxelsFaces[0][z][y] ^ RIGHT) & RIGHT) != RIGHT_TRUE)
						voxelsFaces[0][z][y] ^= RIGHT;
				}
			}
			break;
		}
		case SIDE_LEFT: {
			for (int z = 0; z < voxelsFaces[0].length; z++) {
				for (int y = 0; y < voxelsFaces[0][z].length; y++) {
					if (((voxelsFaces[voxelsFaces.length - 1][z][y] ^ LEFT) & LEFT) != LEFT_TRUE)
						voxelsFaces[voxelsFaces.length - 1][z][y] ^= LEFT;
				}
			}
			break;
		}
		case SIDE_FRONT: {
			for (int x = 0; x < voxelsFaces.length; x++) {
				for (int y = 0; y < voxelsFaces[x][0].length; y++) {
					if (((voxelsFaces[x][0][y] ^ FRONT) & FRONT) != FRONT_TRUE)
						voxelsFaces[x][0][y] ^= FRONT;
				}
			}
			break;
		}
		case SIDE_BACK: {
			for (int x = 0; x < voxelsFaces.length; x++) {
				for (int y = 0; y < voxelsFaces[x][0].length; y++) {
					if (((voxelsFaces[x][voxelsFaces[x].length - 1][y] ^ BACK) & BACK) != BACK_TRUE)
						voxelsFaces[x][voxelsFaces[x].length - 1][y] ^= BACK;
				}
			}
			break;
		}
		}
	}

	/**
	 * Update faces according to a chunk and the direction to him
	 * 
	 * @param node
	 * @param dir
	 */
	public void updateAccordingTo(Chunk node, int dir) {
		switch (dir) {
		case SIDE_FRONT:
			// ZERO Z
			for (int x = 0; x < voxels.length; x++) {
				for (int y = 0; y < voxels[x][0].length; y++) {
					if (node.getVoxels()[x][voxels[x].length - 1][y] != 0
							&& Voxel.voxels[node.getVoxels()[x][voxels[x].length - 1][y]].VoxelData
									.isOpaque() && voxels[x][0][y] != 0) {
						if (Voxel.voxels[voxels[x][0][y]].VoxelData
								.isOpaque() && ((voxelsFaces[x][0][y] ^ FRONT) & FRONT) != FRONT_TRUE)
							voxelsFaces[x][0][y] ^= FRONT;
					} else if (node.getVoxels()[x][voxels[x].length - 1][y] != 0 
							&& voxels[x][0][y] != 0
							&& Voxel.voxels[node.getVoxels()[x][voxels[x].length - 1][y]].VoxelData.isRenderSides()
							&& !Voxel.voxels[voxels[x][0][y]].VoxelData.isRenderSides()) {
						if (Voxel.voxels[voxels[x][0][y]].VoxelData
								.isOpaque() && ((voxelsFaces[x][0][y] ^ FRONT) & FRONT) != FRONT_TRUE)
							voxelsFaces[x][0][y] ^= FRONT;
					}
				}
			}
			break;
		case SIDE_BACK:
			// MAX Z
			for (int x = 0; x < voxels.length; x++) {
				for (int y = 0; y < voxels[x][voxels[x].length - 1].length; y++) {
					if (node.getVoxels()[x][0][y] != 0
							&& Voxel.voxels[node.getVoxels()[x][0][y]].VoxelData
									.isOpaque()
							&& voxels[x][voxels[x].length - 1][y] != 0) {
						if (Voxel.voxels[voxels[x][voxels[x].length - 1][y]].VoxelData
								.isOpaque() && ((voxelsFaces[x][voxels[x].length - 1][y] ^ BACK) & BACK) != BACK_TRUE)
							voxelsFaces[x][voxels[x].length - 1][y] ^= BACK;
					} else if (node.getVoxels()[x][0][y] != 0 
							&& voxels[x][voxels[x].length - 1][y] != 0
							&& Voxel.voxels[node.getVoxels()[x][0][y]].VoxelData.isRenderSides()
							&& !Voxel.voxels[voxels[x][voxels[x].length - 1][y]].VoxelData.isRenderSides()) {
						if (Voxel.voxels[voxels[x][voxels[x].length - 1][y]].VoxelData
								.isOpaque() &&  ((voxelsFaces[x][voxels[x].length - 1][y] ^ BACK) & BACK) != BACK_TRUE)
							voxelsFaces[x][voxels[x].length - 1][y] ^= BACK;
					}
				}
			}
			break;
		case SIDE_LEFT:
			// MAX X
			for (int z = 0; z < voxels[voxels.length - 1].length; z++) {
				for (int y = 0; y < voxels[voxels.length - 1][z].length; y++) {
					if (node.getVoxels()[0][z][y] != 0
							&& Voxel.voxels[node.getVoxels()[0][z][y]].VoxelData
									.isOpaque()
							&& voxels[voxels.length - 1][z][y] != 0) {
						if (Voxel.voxels[voxels[voxels.length - 1][z][y]].VoxelData
								.isOpaque() && ((voxelsFaces[voxels.length - 1][z][y] ^ LEFT) & LEFT) != LEFT_TRUE)
							voxelsFaces[voxels.length - 1][z][y] ^= LEFT;
					} else if (node.getVoxels()[0][z][y] != 0 
							&& voxels[voxels.length - 1][z][y] != 0
							&& Voxel.voxels[node.getVoxels()[0][z][y]].VoxelData.isRenderSides()
							&& !Voxel.voxels[voxels[voxels.length - 1][z][y]].VoxelData.isRenderSides()) {
						if (Voxel.voxels[voxels[voxels.length - 1][z][y]].VoxelData
								.isOpaque() && ((voxelsFaces[voxels.length - 1][z][y] ^ LEFT) & LEFT) != LEFT_TRUE)
							voxelsFaces[voxels.length - 1][z][y] ^= LEFT;
					}
				}
			}
			break;
		case SIDE_RIGHT:
			// ZERO X
			for (int z = 0; z < voxels[0].length; z++) {
				for (int y = 0; y < voxels[0][z].length; y++) {
					if (node.getVoxels()[voxels.length - 1][z][y] != 0
							&& Voxel.voxels[node.getVoxels()[voxels.length - 1][z][y]].VoxelData
									.isOpaque() && voxels[0][z][y] != 0) {
						if (Voxel.voxels[voxels[0][z][y]].VoxelData
								.isOpaque() && ((voxelsFaces[0][z][y] ^ RIGHT) & RIGHT) != RIGHT_TRUE)
							voxelsFaces[0][z][y] ^= RIGHT;
					} else if (node.getVoxels()[voxels.length - 1][z][y] != 0 
							&& voxels[0][z][y] != 0
							&& Voxel.voxels[node.getVoxels()[voxels.length - 1][z][y]].VoxelData.isRenderSides()
							&& !Voxel.voxels[voxels[0][z][y]].VoxelData.isRenderSides()) {
						if (Voxel.voxels[voxels[0][z][y]].VoxelData
								.isOpaque() && ((voxelsFaces[0][z][y] ^ RIGHT) & RIGHT) != RIGHT_TRUE)
							voxelsFaces[0][z][y] ^= RIGHT;
					}
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Perform an internal face update.
	 */
	public void internalFaceUpdate() {
		for (int x = 0; x < voxels.length; x++) {
			for (int z = 0; z < voxels[x].length; z++) {
				for (int y = 0; y < voxels[x][z].length; y++) {
					if (voxels[x][z][y] == 0 || Voxel.voxels[voxels[x][z][y]].VoxelData.isRenderSides())
						continue;
					// MINUS X Checks:
					if ((x - 1) >= 0) {
						if (voxels[x - 1][z][y] != 0
								&& Voxel.voxels[voxels[x - 1][z][y]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x - 1][z][y]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x - 1][z][y]].VoxelData
									.isOpaque() &&((voxelsFaces[x][z][y] ^ RIGHT) & RIGHT) != RIGHT_TRUE)
								voxelsFaces[x][z][y] ^= RIGHT;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x - 1][z][y] ^ LEFT) & LEFT) != LEFT_TRUE)
								voxelsFaces[x - 1][z][y] ^= LEFT;
						}
					}
					// PLUS X Checks:
					if ((x + 1) < voxels.length) {
						if (voxels[x + 1][z][y] != 0
								&& Voxel.voxels[voxels[x + 1][z][y]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x + 1][z][y]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x + 1][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y] ^ LEFT) & LEFT) != LEFT_TRUE)
								voxelsFaces[x][z][y] ^= LEFT;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x + 1][z][y] ^ RIGHT) & RIGHT) != RIGHT_TRUE)
								voxelsFaces[x + 1][z][y] ^= RIGHT;
						}
					}
					// MINUS Z Checks:
					if ((z - 1) >= 0) {
						if (voxels[x][z - 1][y] != 0
								&& Voxel.voxels[voxels[x][z - 1][y]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x][z - 1][y]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x][z - 1][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y] ^ FRONT) & FRONT) != FRONT_TRUE)
								voxelsFaces[x][z][y] ^= FRONT;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z - 1][y] ^ BACK) & BACK) != BACK_TRUE)
								voxelsFaces[x][z - 1][y] ^= BACK;
						}
					}
					// PLUS Z Checks:
					if ((z + 1) < voxels[x].length) {
						if (voxels[x][z + 1][y] != 0
								&& Voxel.voxels[voxels[x][z + 1][y]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x][z + 1][y]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x][z + 1][y]].VoxelData
									.isOpaque() &&((voxelsFaces[x][z][y] ^ BACK) & BACK) != BACK_TRUE)
								voxelsFaces[x][z][y] ^= BACK;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z + 1][y] ^ FRONT) & FRONT) != FRONT_TRUE)
								voxelsFaces[x][z + 1][y] ^= FRONT;
						}
					}
					// MINUS Y Checks:
					if ((y - 1) >= 0) {
						if (voxels[x][z][y - 1] != 0
								&& Voxel.voxels[voxels[x][z][y - 1]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x][z][y - 1]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x][z][y - 1]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y] ^ BOTTOM) & BOTTOM) != BOTTOM_TRUE)
								voxelsFaces[x][z][y] ^= BOTTOM;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y - 1] ^ TOP) & TOP) != TOP_TRUE)
								voxelsFaces[x][z][y - 1] ^= TOP;
						}
					}
					// PLUS Y Checks:
					if ((y + 1) < voxels[x][z].length) {
						if (voxels[x][z][y + 1] != 0
								&& Voxel.voxels[voxels[x][z][y + 1]].VoxelData
										.isOpaque() && !Voxel.voxels[voxels[x][z][y + 1]].VoxelData.isRenderSides()) {
							if (Voxel.voxels[voxels[x][z][y + 1]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y] ^ TOP) & TOP) != TOP_TRUE)
								voxelsFaces[x][z][y] ^= TOP;
							if (Voxel.voxels[voxels[x][z][y]].VoxelData
									.isOpaque() && ((voxelsFaces[x][z][y + 1] ^ BOTTOM) & BOTTOM) != BOTTOM_TRUE)
								voxelsFaces[x][z][y + 1] ^= BOTTOM;
						}
					}
					if ((y) == 0) {
						if (((voxelsFaces[x][z][y] ^ BOTTOM) & BOTTOM) != BOTTOM_TRUE)
							voxelsFaces[x][z][y] ^= BOTTOM;
					}
				}
			}
		}
	}

	/**
	 * Get distance from this chunk to another one
	 * 
	 * @param node
	 * @return
	 */
	public float getDistanceToChunk(Chunk node) {
		float disx = (float) Math.pow((this.getBoundingbox().getMinX() - node
				.getBoundingbox().getMinX()), 2);
		float disy = (float) Math.pow((this.getBoundingbox().getMinY() - node
				.getBoundingbox().getMinY()), 2);
		float disz = (float) Math.pow((this.getBoundingbox().getMinZ() - node
				.getBoundingbox().getMinZ()), 2);
		return (float) Math.sqrt(disx + disy + disz);
	}

	/**
	 * Get the direction to another chunk
	 * 
	 * @param node
	 * @return
	 */
	public int getDirectionToChunk(Chunk node) {
		/*
		 * front 0 back 1 left 2 right 3
		 */
		float x1 = this.getBoundingbox().getLocation().x;
		float x2 = node.getBoundingbox().getLocation().x;
		float z1 = this.getBoundingbox().getLocation().z;
		float z2 = node.getBoundingbox().getLocation().z;

		if (z1 < z2 && x1 == x2) {
			return SIDE_BACK;
		} else if (z1 > z2 && x1 == x2) {
			return SIDE_FRONT;
		} else if (x1 < x2 && z1 == z2) {
			return SIDE_LEFT;
		} else if (x1 > x2 && z1 == z2) {
			return SIDE_RIGHT;
		}
		return -1;
	}

	/**
	 * Get all neighbors
	 * 
	 * @param world
	 * @return
	 */
	public ArrayList<Chunk> getNeighbors(VoxelWorld world) {
		ArrayList<Chunk> nodes = new ArrayList<Chunk>();
		for (Chunk c : world.getNodes()) {
			if (!c.equals(this)
					&& this.getDirectionToChunk(c) != -1
					&& this.getDistanceToChunk(c) == Constants.NODE_WIDTH
							* Constants.VOXEL_SIZE) { // TODO: no support for
														// non
														// square chunks
				nodes.add(c);
			}
		}
		return nodes;
	}

	/**
	 * Get full neighbors AKA 8 neighbors instead of 4
	 * 
	 * @param world
	 * @return
	 */
	public ArrayList<Chunk> getFullNeighbors(VoxelWorld world) {
		ArrayList<Chunk> nodes = new ArrayList<Chunk>();
		for (Chunk c : world.getNodes()) {
			if (c != this
					&& this.getDistanceToChunk(c) <= Constants.NODE_WIDTH
							* Constants.VOXEL_SIZE * Math.sqrt(2)) { // TODO: no
																		// support
																		// for
																		// non
				nodes.add(c);
			}
		}
		return nodes;
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public BoxAABB3 getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(BoxAABB3 boundingbox) {
		this.boundingbox = boundingbox;
	}

	public byte[][][] getVoxels() {
		return voxels;
	}

	public void setVoxels(byte[][][] voxels) {
		this.voxels = voxels;
	}

	public ChunkMeshBuilder getEcmb() {
		return ecmb;
	}

	public void setEcmb(ChunkMeshBuilder ecmb) {
		this.ecmb = ecmb;
	}

	public byte[][][] getVoxelsFaces() {
		return voxelsFaces;
	}

	public byte[][][] getLighting() {
		return lighting;
	}

	public void setLighting(byte[][][] lighting) {
		this.lighting = lighting;
	}

}

