
package org.amitassaraf.evox.core;

import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.view.TextureAtlas;

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

public abstract class Static {

	/**
	 * Static bounding box is static textured texture atlas texture number
	 */
	private BoxAABB3 boundingBox;
	private boolean texture = false;
	private int textureAtlasID;
	private int textureNumber;
	private StaticMeshBuilder meshBuilder;

	/**
	 * Primary constructor
	 * 
	 * @param location
	 * @param size
	 */
	public Static(Vector3f location, Vector3f size) {
		boundingBox = new BoxAABB3(location, size);
		meshBuilder = new StaticMeshBuilder(this);
	}

	/**
	 * Abstract render method AKA build method
	 * 
	 * @param buff
	 * @param game
	 * @return
	 */
	public abstract int render(FloatBuffer buff, Game game, TextureAtlas atlas);

	/**
	 * Getters and Setters
	 */

	public boolean isTexture() {
		return texture;
	}

	public void setTexture(boolean texture) {
		this.texture = texture;
	}

	public BoxAABB3 getBoundingBox() {
		return boundingBox;
	}

	public int getTextureAtlasID() {
		return textureAtlasID;
	}

	public void setTextureAtlasID(int textureAtlasID) {
		this.textureAtlasID = textureAtlasID;
	}

	public int getTextureNumber() {
		return textureNumber;
	}

	public void setTextureNumber(int textureNumber) {
		this.textureNumber = textureNumber;
	}

	public StaticMeshBuilder getMeshBuilder() {
		return meshBuilder;
	}

}