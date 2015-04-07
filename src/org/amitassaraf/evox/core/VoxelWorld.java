package org.amitassaraf.evox.core;

import static org.amitassaraf.evox.core.Constants.NODE_AMOUNT;
import static org.amitassaraf.evox.core.Constants.NODE_HEIGHT;
import static org.amitassaraf.evox.core.Constants.NODE_LENGTH;
import static org.amitassaraf.evox.core.Constants.NODE_WIDTH;
import static org.amitassaraf.evox.core.Constants.SIDE_BACK;
import static org.amitassaraf.evox.core.Constants.SIDE_FRONT;
import static org.amitassaraf.evox.core.Constants.SIDE_LEFT;
import static org.amitassaraf.evox.core.Constants.SIDE_RIGHT;
import static org.amitassaraf.evox.core.Constants.VOXEL_SIZE;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.environment.Sun;
import org.amitassaraf.evox.core.terrain.SimplexNoise;
import org.amitassaraf.evox.core.voxels.Voxel;
import org.amitassaraf.evox.input.keyboard.PlayerMovmentInput;
import org.amitassaraf.evox.net.kryo.packets.WorldBlockChangePacket06;

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

public class VoxelWorld {

	/**
	 * List of chunks
	 */
	private ArrayList<Chunk> nodes;
	/**
	 * Game instance
	 */
	private Game game;
	/**
	 * Node factor
	 */
	private int nodeFactor = -1;
	/**
	 * Height map data
	 */
	private float[][] heightMapData;
	/**
	 * List of static objects
	 */
	private ArrayList<Static> staticObjects;
	/**
	 * Build queue of chunks that need to be rebuilt
	 */
	private ArrayList<Chunk> chunkBuildQueue;
	/**
	 * Build queue of entity that need to be rebuilt
	 */
	private ArrayList<Entity> entityBuildQueue;

	/**
	 * Is this a server world?
	 */
	private boolean isServer;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public VoxelWorld(Game game) {
		this.game = game;
		staticObjects = new ArrayList<Static>();
		staticObjects.add(new Sun(new Vector3f(0, 400, 0)));
		nodes = new ArrayList<Chunk>();
		chunkBuildQueue = new ArrayList<Chunk>();
		entityBuildQueue = new ArrayList<Entity>();
	}

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public VoxelWorld(Game game, boolean isServer) {
		this.game = game;
		this.isServer = isServer;
		staticObjects = new ArrayList<Static>();
		nodes = new ArrayList<Chunk>();
		chunkBuildQueue = new ArrayList<Chunk>();
	}

	/**
	 * Generate the world
	 */
	public void generate() {
		// try {
		// loadHeightmap();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		SimplexNoise simplexNoise = new SimplexNoise(128, 0.25,
				new Random().nextInt(5000));

		double xStart = 0;
		double XEnd = 2048;
		double yStart = 0;
		double yEnd = 2048;

		int xResolution = 2048;
		int yResolution = 2048;

		int[][] result = new int[xResolution][yResolution];

		for (int i = 0; i < xResolution; i++) {
			for (int j = 0; j < yResolution; j++) {
				int x = (int) (xStart + i * ((XEnd - xStart) / xResolution));
				int y = (int) (yStart + j * ((yEnd - yStart) / yResolution));
				result[i][j] = (int) ((1 + simplexNoise.getNoise(x, y)) * 20);
			}
		}

		for (int i = 2; i < NODE_AMOUNT; i++) {
			if (i * i == NODE_AMOUNT) {
				nodeFactor = i;
				break;
			}
		}
		if (nodeFactor == -1) {
			nodeFactor = 5;
		}

		Random rand = new Random();
		for (int x = 0; x < nodeFactor; x++) {
			for (int z = 0; z < nodeFactor; z++) {
				nodes.add(new Chunk(new BoxAABB3(new Vector3f((NODE_WIDTH * VOXEL_SIZE)
						* x, 0, (NODE_LENGTH * VOXEL_SIZE) * z), new Vector3f(NODE_WIDTH
								* VOXEL_SIZE, NODE_HEIGHT * VOXEL_SIZE, NODE_LENGTH
								* VOXEL_SIZE))));
				Chunk c = nodes.get(nodes.size() - 1);
				for (int xx = 0; xx < c.getVoxels().length; xx++) {
					for (int zz = 0; zz < c.getVoxels()[xx].length; zz++) {
						for (int y = 0; y < result[xx + (x * NODE_WIDTH)][zz
								+ (z * NODE_LENGTH)]; y++) {
							if (y < result[xx + (x * NODE_WIDTH)][zz
									+ (z * NODE_LENGTH)] - 1) {
								c.getVoxels()[xx][zz][y] = 2;
							} else {
								c.getVoxels()[xx][zz][y] = 1;
							}
						}
						if (rand.nextInt(50) == 3 && xx > 1 && zz > 1
								&& xx < c.getVoxels().length - 2
								&& zz < c.getVoxels()[0].length - 2) {
							//TREE
							int y = result[xx + (x * NODE_WIDTH)][zz
									+ (z * NODE_LENGTH)];
							c.getVoxels()[xx][zz][(y)] = 4;
							c.getVoxels()[xx][zz][(y + 1)] = 4;
							c.getVoxels()[xx][zz][(y + 2)] = 4;
							c.getVoxels()[xx][zz][(y + 3)] = 4;
							c.getVoxels()[xx][zz + 1][(y + 3)] = 5;
							c.getVoxels()[xx + 1][zz][(y + 3)] = 5;
							c.getVoxels()[xx - 1][zz][(y + 3)] = 5;
							c.getVoxels()[xx][zz - 1][(y + 3)] = 5;
							c.getVoxels()[xx + 1][zz + 1][(y + 3)] = 5;
							c.getVoxels()[xx - 1][zz - 1][(y + 3)] = 5;
							c.getVoxels()[xx + 1][zz - 1][(y + 3)] = 5;
							c.getVoxels()[xx - 1][zz + 1][(y + 3)] = 5;
							c.getVoxels()[xx][zz][(y + 4)] = 5;
						} else if (rand.nextInt(100) == 3 && xx > 1 && zz > 1
								&& xx < c.getVoxels().length - 2
								&& zz < c.getVoxels()[0].length - 2) {
							//ROCK
							int y = result[xx + (x * NODE_WIDTH)][zz
									+ (z * NODE_LENGTH)];
							c.getVoxels()[xx][zz][(y)] = 2;
							if (rand.nextInt(3) == 2)
								c.getVoxels()[xx][zz][(y + 1)] = 2;
							switch (rand.nextInt(6)) {
							case 0:
								c.getVoxels()[xx][zz + 1][(y)] = 2;
								break;
							case 1:
								c.getVoxels()[xx][zz - 1][(y)] = 2;
								break;
							case 2:
								c.getVoxels()[xx + 1][zz][(y)] = 2;
								break;
							case 3:
								c.getVoxels()[xx - 1][zz][(y)] = 2;
								break;
							default:
								break;
							}
						} else if (rand.nextInt(11) == 3) {
							int y = result[xx + (x * NODE_WIDTH)][zz
							  									+ (z * NODE_LENGTH)];
							if (rand.nextInt(4) == 0)
								c.getVoxels()[xx][zz][y] = 8;
							else
								c.getVoxels()[xx][zz][y] = 7;
						}
					}
				}
				if (nodes.size() >= NODE_AMOUNT) {
					break;
				}
			}
			if (nodes.size() >= NODE_AMOUNT) {
				break;
			}
		}

		System.out.println("Chunk amount: " + nodes.size());
	}

	/**
	 * Add Chunk to list
	 */
	public void loadChunk(Chunk c) {
		System.out.println("Loading chunk at " + c.getBoundingbox().getMinX() + ","
				+ c.getBoundingbox().getMinZ());
		this.nodes.add(c);
	}

	/**
	 * Load heightmap
	 * 
	 * @throws IOException
	 */
	public void loadHeightmap() throws IOException {
		// Load the heightmap-image from its resource file
		BufferedImage heightmapImage = ImageIO.read(new File(
				"res/heightmaps/heightmap3.bmp"));
		// Initialise the data array, which holds the heights of the
		// heightmap-vertices, with the correct dimensions
		heightMapData = new float[heightmapImage.getWidth()][heightmapImage
				.getHeight()];
		// Lazily initialise the convenience class for extracting the separate
		// red, green, blue, or alpha channels
		// an int in the default RGB color model and default sRGB colourspace.
		Color colour;
		// Iterate over the pixels in the image on the x-axis
		for (int z = 0; z < heightMapData.length; z++) {
			// Iterate over the pixels in the image on the y-axis
			for (int x = 0; x < heightMapData[z].length; x++) {
				// Retrieve the colour at the current x-location and y-location
				// in the image
				colour = new Color(heightmapImage.getRGB(z, x));
				// Store the value of the red channel as the height of a
				// heightmap-vertex in 'data'. The choice for
				// the red channel is arbitrary, since the heightmap-image
				// itself only has white, gray, and black.
				heightMapData[z][x] = colour.getRed() - 30;
				heightMapData[z][x] /= 4;
				if (heightMapData[z][x] >= NODE_HEIGHT) {
					heightMapData[z][x] = NODE_HEIGHT - 1;
				}
				if (heightMapData[z][x] <= 0) {
					heightMapData[z][x] = 1;
				}
			}
		}
	}

	/**
	 * Culling faces across chunks
	 */
	public void crossChunkCulling() {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (i != j
						&& nodes.get(i).getDistanceToChunk(nodes.get(j)) <= NODE_WIDTH
								* VOXEL_SIZE) { // TODO : No support for
												// rectangular chunks
					nodes.get(i).updateAccordingTo(nodes.get(j),
							nodes.get(i).getDirectionToChunk(nodes.get(j)));
				}
			}
		}
	}

	/**
	 * Cross chunk culling for the chunks in the list
	 * 
	 * @param nodes
	 */
	public void crossChunkCulling(ArrayList<Chunk> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (i != j
						&& nodes.get(i).getDistanceToChunk(nodes.get(j)) <= NODE_WIDTH
								* VOXEL_SIZE) { // TODO : No support for
												// rectangular chunks
					nodes.get(i).updateAccordingTo(nodes.get(j),
							nodes.get(i).getDirectionToChunk(nodes.get(j)));
				}
			}
		}
	}

	/**
	 * World edge culling
	 */
	public void edgeWorldCulling() {
		for (Chunk node : nodes) {
			if (node.getBoundingbox().getLocation().x == 0) {
				node.hideAllFaces(SIDE_RIGHT);
			} else if (node.getBoundingbox().getLocation().x + NODE_WIDTH
					* VOXEL_SIZE == Math.sqrt(NODE_AMOUNT) * NODE_WIDTH
					* VOXEL_SIZE) {
				node.hideAllFaces(SIDE_LEFT);
			}

			if (node.getBoundingbox().getLocation().z == 0) {
				node.hideAllFaces(SIDE_FRONT);
			} else if (node.getBoundingbox().getLocation().z + NODE_LENGTH
					* VOXEL_SIZE == Math.sqrt(NODE_AMOUNT) * NODE_LENGTH
					* VOXEL_SIZE) {
				node.hideAllFaces(SIDE_BACK);
			}
		}
	}

	/**
	 * World edge culling
	 */
	public void edgeWorldCulling(ArrayList<Chunk> nodes) {
		for (Chunk node : nodes) {
			if (node.getBoundingbox().getLocation().x == 0) {
				node.hideAllFaces(SIDE_RIGHT);
			} else if (node.getBoundingbox().getLocation().x + NODE_WIDTH
					* VOXEL_SIZE == Math.sqrt(NODE_AMOUNT) * NODE_WIDTH
					* VOXEL_SIZE) {
				node.hideAllFaces(SIDE_LEFT);
			}

			if (node.getBoundingbox().getLocation().z == 0) {
				node.hideAllFaces(SIDE_FRONT);
			} else if (node.getBoundingbox().getLocation().z + NODE_LENGTH
					* VOXEL_SIZE == Math.sqrt(NODE_AMOUNT) * NODE_LENGTH
					* VOXEL_SIZE) {
				node.hideAllFaces(SIDE_BACK);
			}
		}
	}

	/**
	 * Get node by location
	 * 
	 * @param location
	 * @return
	 */
	public Chunk getExternalNodeByLocation(Vector3f location) {
		for (Chunk etn : nodes) {
			if (etn.getBoundingbox().getMinX() == location.x
					&& etn.getBoundingbox().getMinY() == location.y
					&& etn.getBoundingbox().getMinZ() == location.z) {
				return etn;
			}
		}
		return null;
	}

	/**
	 * Remove voxel at index location loc and Chunk node
	 * 
	 * @param loc
	 * @param node
	 */
	public void removeVoxelAt(Vector3f loc, Chunk node) {
		node.getVoxels()[(int) loc.x][(int) loc.z][(int) loc.y] = 0;

		if (!isServer) {
			ArrayList<Chunk> nodes = node.getNeighbors(this);
			nodes.add(0, node);
			for (Chunk c : nodes) {
				c.resetAllFaces();
				c.internalFaceUpdate();
				try {
					c.updateLightingValues(game);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			crossChunkCulling(nodes);
			edgeWorldCulling(nodes);

			chunkBuildQueue.addAll(nodes);
		}
	}
	
	Vector3f loc = new Vector3f();
	BoxAABB3 block = new BoxAABB3(loc, Constants.VOXEL_SIZE);
	/**
	 * Check if the entity collide with a block in the world.
	 * @param entity the entity to check
	 * @return the block that the entity has collide with.
	 */
	public BoxAABB3 checkEntityCollision(Entity entity, boolean searchFloor) {
		loc.set(0, 0, 0);
		for (int i = 0; i < entity.getCollisionChunks().size(); i++) {
			Chunk node = entity.getCollisionChunks().get(i);
			if (entity.getEntityHitBox()
					.intersectsAABB(node.getBoundingbox())) {
				loc.set(node.getBoundingbox().getLocation());
				block.setLocation(loc);
				for (int x = 0; x < node.getVoxels().length; x++) {
					for (int z = 0; z < node.getVoxels()[x].length; z++) {
						for (int y = node.getVoxels()[x][z].length - 1; y >= 0 ; y--) {
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
								
								if (searchFloor &&
										block.getMinY() < entity.getEntityHitBox().getMaxY()) {
									//floor
									return new BoxAABB3(block.getLocation(), block.getSize());
								} else if (!searchFloor &&
										block.getMinY() >= entity.getEntityHitBox().getMaxY()) {
									return new BoxAABB3(block.getLocation(), block.getSize());
								}
								//return new BoxAABB3(block.getLocation(), block.getSize());
							}

						}
					}
				}
			}
		}
		return null;
	}
	
	public void overrideVoxelAt(Vector3f loc, Chunk node, byte voxel) {
		node.getVoxels()[(int) loc.x][(int) loc.z][(int) loc.y] = voxel;

		if (!isServer) {
			nodes.add(0, node);
			for (Chunk c : nodes) {
				c.resetAllFaces();
				c.internalFaceUpdate();
				try {
					c.updateLightingValues(game);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			crossChunkCulling(nodes); // TODO check for using method #2
			edgeWorldCulling(nodes);

			chunkBuildQueue.addAll(nodes);
		}
	}

	/**
	 * add voxel at
	 * 
	 * @param loc
	 * @param node
	 * @param face
	 */
	public void putVoxelAt(Vector3f loc, Chunk node, int[] face, byte voxel) {
		Vector3f vec = new Vector3f();
		boolean placed = false;
		vec.set(node.getBoundingbox().getLocation().x + (loc.x * VOXEL_SIZE),
				node.getBoundingbox().getLocation().y + (loc.y * VOXEL_SIZE),
				node.getBoundingbox().getLocation().z + (loc.z * VOXEL_SIZE));
		BoxAABB3 bound = new BoxAABB3(vec, VOXEL_SIZE);
		if (game.getPlayers().get(0).getEntityHitBox().intersectsAABB(bound))
			return;

		Vector3f chunkLoc = new Vector3f();

		ArrayList<Chunk> nodes = node.getNeighbors(this);

		if (loc.x + face[0] >= 0 && loc.x + face[0] < node.getVoxels().length) {
			if (loc.z + face[2] >= 0
					&& loc.z + face[2] < node.getVoxels()[0].length) {
				if (loc.y + face[1] >= 0
						&& loc.y + face[1] < node.getVoxels()[0][0].length)
					node.getVoxels()[(int) loc.x + face[0]][(int) loc.z
							+ face[2]][(int) loc.y + face[1]] = voxel;
				chunkLoc.x = (int) loc.x + face[0];
				chunkLoc.z = (int) loc.z + face[2];
				chunkLoc.y = (int) loc.y + face[1];
				placed = true;
			} else {
				for (Chunk c : nodes) {
					if (face[2] == 1) {
						if (node.getDirectionToChunk(c) == SIDE_BACK) {
							c.getVoxels()[(int) (loc.x + face[0])][0][(int) (loc.y + face[1])] = voxel;
							chunkLoc.x = (int) loc.x + face[0];
							chunkLoc.z = 0;
							chunkLoc.y = (int) loc.y + face[1];

							placed = true;
							break;
						}
					} else if (face[2] == -1) {
						if (node.getDirectionToChunk(c) == SIDE_FRONT) {
							c.getVoxels()[(int) (loc.x + face[0])][c
									.getVoxels()[0].length - 1][(int) (loc.y + face[1])] = voxel;

							chunkLoc.x = (int) loc.x + face[0];
							chunkLoc.z = c.getVoxels()[0].length - 1;
							chunkLoc.y = (int) loc.y + face[1];
							placed = true;
							break;
						}
					}
				}
			}
		} else {
			for (Chunk c : nodes) {
				if (face[0] == 1) {
					if (node.getDirectionToChunk(c) == SIDE_LEFT) {
						c.getVoxels()[0][(int) (loc.z + face[2])][(int) (loc.y + face[1])] = voxel;
						chunkLoc.x = 0;
						chunkLoc.z = (int) loc.z + face[2];
						chunkLoc.y = (int) loc.y + face[1];
						placed = true;
						break;
					}
				} else if (face[0] == -1) {
					if (node.getDirectionToChunk(c) == SIDE_RIGHT) {
						c.getVoxels()[c.getVoxels().length - 1][(int) (loc.z + face[2])][(int) (loc.y + face[1])] = voxel;
						chunkLoc.x = c.getVoxels().length - 1;
						chunkLoc.z = (int) loc.z + face[2];
						chunkLoc.y = (int) loc.y + face[1];
						placed = true;
						break;
					}
				}
			}
		}

		if (placed) {
			if (!isServer) {
				nodes.add(0, node);
				for (Chunk c : nodes) {
					c.resetAllFaces();
					c.internalFaceUpdate();
					try {
						c.updateLightingValues(game);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				crossChunkCulling(nodes); // TODO check for using method #2
				edgeWorldCulling(nodes);

				chunkBuildQueue.addAll(nodes);

				WorldBlockChangePacket06 wbcPacket = new WorldBlockChangePacket06();
				wbcPacket.setPlace(true);
				wbcPacket.setVoxel(voxel);
				wbcPacket.setBlockX(chunkLoc.x);
				wbcPacket.setBlockY(chunkLoc.y);
				wbcPacket.setBlockZ(chunkLoc.z);
				wbcPacket.setChunkX(node.getBoundingbox().getMinX());
				wbcPacket.setChunkY(node.getBoundingbox().getMinY());
				wbcPacket.setChunkZ(node.getBoundingbox().getMinZ());
				game.getNetworkManager().sendPacket(wbcPacket);
			}
		}
	}

	/**
	 * Manually update the world lighting
	 */
	public void updateWorldLight() {
		for (Chunk c : nodes) {
			try {
				c.updateLightingValues(game);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		chunkBuildQueue.addAll(nodes);
	}

	/**
	 * Get voxel at world location v
	 * 
	 * @param v
	 * @return
	 */
	public VoxelCallback getVoxel(org.lwjgl.util.vector.Vector3f v) {
		Vector3f vec = new Vector3f(v.x, v.y, v.z);
		for (Chunk node : nodes) {
			if (node.getBoundingbox().inside(vec)) {
				BoxAABB3 block = new BoxAABB3(node.getBoundingbox()
						.getLocation(), VOXEL_SIZE);
				for (int x = 0; x < node.getVoxels().length; x++) {
					for (int z = 0; z < node.getVoxels()[x].length; z++) {
						block.setLocation(new Vector3f(node.getBoundingbox()
								.getLocation().x + (x * VOXEL_SIZE), 0, node
								.getBoundingbox().getLocation().z
								+ (z * VOXEL_SIZE)));
						if (block.insideNoY(vec)) {
							for (int y = node.getVoxels()[x][z].length - 1; y >= 0; y--) {
								block.setLocation(new Vector3f(node
										.getBoundingbox().getLocation().x
										+ (x * VOXEL_SIZE), node
										.getBoundingbox().getLocation().y
										+ (y * VOXEL_SIZE), node
										.getBoundingbox().getLocation().z
										+ (z * VOXEL_SIZE)));
								if (block.inside(vec)) {
									return new VoxelCallback(false,
											node.getVoxels()[x][z][y],
											new org.lwjgl.util.vector.Vector3f(
													x, y, z), node);
								}
							}
							return new VoxelCallback(true, -1, null, null);
						}
					}
				}
			}
		}
		return new VoxelCallback(true, -1, null, null);
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public ArrayList<Chunk> getNodes() {
		return nodes;
	}

	public ArrayList<Static> getStaticObjects() {
		return staticObjects;
	}

	public ArrayList<Chunk> getBuildQueue() {
		return chunkBuildQueue;
	}

	public void setBuildQueue(ArrayList<Chunk> buildQueue) {
		this.chunkBuildQueue = buildQueue;
	}

	public ArrayList<Entity> getEntityBuildQueue() {
		return entityBuildQueue;
	}

	public void setEntityBuildQueue(ArrayList<Entity> entityBuildQueue) {
		this.entityBuildQueue = entityBuildQueue;
	}

}
