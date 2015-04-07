
package org.amitassaraf.evox.core;

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

public class BoxAABB2 {
	/*
	 * Size of the hitbox
	 */
	private Vector2f size;
	/*
	 * Location of the hitbox
	 */
	private Vector2f location;

	/*
	 * Main constructor
	 */
	public BoxAABB2(float size, Vector2f location) {
		this.size = new Vector2f(size, size);
		this.location = location;
	}

	/*
	 * Secondary constructor
	 */
	public BoxAABB2(Vector2f size, Vector2f location) {
		this.size = size;
		this.location = location;
	}

	/*
	 * Check if intersects bounding box
	 */
	public boolean intersects(float x, float y) {
		if ((x >= location.x && x <= location.x + size.x)
				&& (y <= location.y + size.y && y >= location.y)) {
			return true;
		}
		return false;
	}

	/*
	 * Check if intersects bounding box
	 */
	public boolean intersects(Vector2f vec) {
		float x = vec.x;
		float y = vec.y;
		if ((x >= location.x && x <= location.x + size.x)
				&& (y <= location.y + size.y && y >= location.y)) {
			return true;
		}
		return false;
	}

	/*
	 * Check if another bounding box intersets this bounding box
	 */
	public boolean intersectsAABB(BoxAABB2 brother) {
		if (brother.intersects(location.x + size.x, location.y + size.y))
			return true;
		if (brother.intersects(location.x, location.y))
			return true;
		if (brother
				.intersects(location.x + size.x / 2, location.y + size.y / 2))
			return true;
		if (this.intersects(brother.getLocation().x + brother.getSize().x,
				brother.getLocation().y + brother.getSize().y))
			return true;
		if (this.intersects(brother.getLocation().x, brother.getLocation().y))
			return true;
		if (this.intersects(brother.getLocation().x + brother.getSize().x / 2,
				brother.getLocation().y + brother.getSize().y / 2))
			return true;
		return false;
	}

	/*
	 * Getters and setters
	 */
	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public float getCenterx() {
		return location.x + size.x / 2;
	}

	public float getCentery() {
		return location.y + size.y / 2;
	}

	public float getMinx() {
		return location.x;
	}

	public float getMiny() {
		return location.y;
	}

	public float getMaxx() {
		return location.x + size.x;
	}

	public float getMaxy() {
		return location.y + size.y;
	}

	public Vector2f getLocation() {
		return location;
	}

	public void setLocation(Vector2f location) {
		this.location = location;
	}

}
