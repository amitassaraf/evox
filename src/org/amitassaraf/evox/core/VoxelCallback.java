
package org.amitassaraf.evox.core;

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

public class VoxelCallback {

	/**
	 * Is the callback out of the world bounds?
	 */
	private boolean outOfBounds = false;

	/**
	 * Voxel ID we hit
	 */
	private int voxelId = -1;

	/**
	 * The array index the voxel is at
	 */
	private Vector3f arrayLoc;

	/**
	 * The parent of the voxel
	 */
	private Chunk parent;

	/**
	 * The face we hit
	 */
	private int[] faceArr;

	/**
	 * Primary constructor
	 * 
	 * @param o
	 * @param id
	 * @param arrayLoc
	 * @param node
	 */
	public VoxelCallback(boolean o, int id, Vector3f arrayLoc, Chunk node) {
		this.outOfBounds = o;
		this.voxelId = id;
		this.arrayLoc = arrayLoc;
		this.parent = node;
	}

	/**
	 * To string
	 */
	public String toString() {
		return "Callback at: [" + arrayLoc + "] in chunk [" + parent
				+ "] voxel: [" + voxelId + "]";
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public boolean isOutOfBounds() {
		return outOfBounds;
	}

	public void setOutOfBounds(boolean outOfBounds) {
		this.outOfBounds = outOfBounds;
	}

	public int getVoxelId() {
		return voxelId;
	}

	public void setVoxelId(int voxelId) {
		this.voxelId = voxelId;
	}

	public Vector3f getArrayLoc() {
		return arrayLoc;
	}

	public void setArrayLoc(Vector3f arrayLoc) {
		this.arrayLoc = arrayLoc;
	}

	public Chunk getParent() {
		return parent;
	}

	public void setParent(Chunk parent) {
		this.parent = parent;
	}

	public int[] getFaceArr() {
		return faceArr;
	}

	public void setFaceArr(int[] faceArr) {
		this.faceArr = faceArr;
	}

}