
package org.amitassaraf.evox.core;

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

public class BoxAABB3 {

	/*
	 * Size of bounding box
	 */
	private Vector3f size;
	/*
	 * Location of bounding box
	 */
	private Vector3f location;

	/*
	 * Main constructor
	 */
	public BoxAABB3(Vector3f location, float size) {
		this.size = new Vector3f(size, size, size);
		this.location = location;
	}

	/*
	 * Secondary constructor
	 */
	public BoxAABB3(Vector3f location, Vector3f size) {
		this.size = size;
		this.location = location;
	}

	/*
	 * checks if intersects coordinates
	 */
	public boolean inside(float x, float y, float z) {
		if (x >= location.x && x <= location.x + size.x) {
			if (y >= location.y && y <= location.y + size.y) { // ?
				if (z >= location.z && z <= location.z + size.z) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Check if intersects coordinates without Y axis
	 */
	public boolean insideNoY(float x, float z) {
		//System.out.println(" < " + x + " , " + z + " > ");
		//System.out.println(" < " + location.x + " , " + location.z + " > ");
		//System.out.println(" < " + size.x + " , " + size.z + " > ");
		if (x >= location.x && x <= location.x + size.x) {
			if (z >= location.z && z <= location.z + size.z) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Check if intersects vector without Y axis
	 */
	public boolean insideNoY(Vector3f vec) {
		float x = vec.x;
		float z = vec.z;
		return insideNoY(x, z);
	}

	/*
	 * Checks if intersects vector
	 */
	public boolean inside(Vector3f vec) {
		float x = vec.x;
		float z = vec.z;
		float y = vec.y;
		return inside(x, y, z);
	}

	/*
	 * Checks if this bounding box intersects with another one
	 */
	public boolean intersectsAABB(BoxAABB3 brother) {
		//S == Min
		//B == Max
		if (brother.inside(this.getMinX(), this.getMinY(), this.getMinZ())) //SSS
			return true;
		if (brother.inside(this.getMaxX(), this.getMaxY(), this.getMaxZ())) //BBB
			return true;
		//if (brother.inside(this.getCenterX(), this.getCenterY(), this.getCenterZ()))
		//	return true;
		if (brother.inside(this.getMinX(), this.getMinY(), this.getMaxZ())) //SSB
			return true;
		if (brother.inside(this.getMinX(), this.getMaxY(), this.getMaxZ())) //SBB
			return true;
		if (brother.inside(this.getMaxX(), this.getMinY(), this.getMaxZ())) //BSB
			return true;
		if (brother.inside(this.getMaxX(), this.getMaxY(), this.getMinZ())) //BBS
			return true;
		if (brother.inside(this.getMinX(), this.getMaxY(), this.getMinZ())) //SBS
			return true;
		if (brother.inside(this.getMaxX(), this.getMinY(), this.getMinZ())) //BSS
			return true;
		return false;
	}

	/*
	 * Checks if this bounding box intersects another one without Y axis
	 */
	public boolean intersectsAABBNoY(BoxAABB3 brother) {
		//if (brother.insideNoY(this.getMinX(), this.getMinZ()))
		//	return true;
		//if (brother.insideNoY(this.getMaxX(), this.getMaxZ()))
		//	return true;
		if (brother.insideNoY(this.getCenterX(), this.getCenterZ()))
			return true;
		//if (brother.insideNoY(this.getMinX(), this.getMaxZ()))
		//	return true;
		//if (brother.insideNoY(this.getMaxX(), this.getMinZ()))
		//	return true;
		return false;
	}
	
	public void add(float x, float y, float z) {
		this.location.x += x;
		this.location.y += y;
		this.location.z += z;
	}
	
	public void sub(float x, float y, float z) {
		this.location.x -= x;
		this.location.y -= y;
		this.location.z -= z;
	}

	/*
	 * Getters and setters
	 */
	public Vector3f getSize() {
		return size;
	}

	public void setSize(Vector3f size) {
		this.size = size;
	}

	public float getCenterX() {
		return location.x + size.x / 2;
	}

	public float getCenterY() {
		return location.y + size.y / 2;
	}

	public float getCenterZ() {
		return location.z + size.z / 2;
	}

	public float getMinX() {
		return location.x;
	}

	public float getMinY() {
		return location.y;
	}

	public float getMinZ() {
		return location.z;
	}

	public float getMaxX() {
		return location.x + size.x;
	}

	public float getMaxY() {
		return location.y - size.y;
	}

	public float getMaxZ() {
		return location.z + size.z;
	}

	public Vector3f getLocation() {
		return location;
	}

	public void setLocation(Vector3f location) {
		this.location = location;
	}
	
	public void setLocation(float x, float y, float z) {
		this.location.x = x;
		this.location.y = y;
		this.location.z = z;
	}

}