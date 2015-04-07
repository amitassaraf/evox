package org.amitassaraf.evox.view;

import org.lwjgl.util.vector.Vector2f;


/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha 
 * Egoz St. House 3 Right | All rights to this code are reserved to/for
 * the author aka Amit Assaraf. Any publishing of this code without
 * authorization from the author will lead to bad consciences. Therfore
 * do not redistribute this file/code. No snippets of this code may be
 * redistributed/published.
 * 
 * Contact information:
 *   Amit Assaraf - Email: soit@walla.com Phone: 0505964411 (Israel)
 *
 */

public class TextureAtlas {
	
	/**
	 * Texture atlas values
	 */
	private float cellSizeX;
	private float cellSizeY;
	private float atlasWidth;
	private float atlasHeight;
	
	/**
	 * Texture atlas OpenGL Texture Handle ID
	 */
	private int atlasTextureId;
	
	/**
	 * Primary constructor
	 * @param atlasTextureId
	 * @param cell
	 * @param width
	 * @param height
	 */
	public TextureAtlas(int atlasTextureId,float cell, float width, float height) {
		this.atlasTextureId = atlasTextureId;
		this.cellSizeX = cell;
		this.cellSizeY = cell;
		this.atlasWidth = width;
		this.atlasHeight = height;
	}
	
	/**
	 * Get the ending coordinate of the texture for point P
	 * @param p
	 * @return
	 */
	public Vector2f getEndCoordinate(Vector2f p) {
		float x = ((float)cellSizeX/(float)atlasWidth);
		float y = ((float)cellSizeY/(float)atlasHeight);
		x += p.x;
		y += p.y;
		return new Vector2f(x,y);
	}
	
	/**
	 * Get the starting coordinate of the texture for texture number I
	 * @param number
	 * @return
	 */
	public Vector2f getCoordinate(int number) {
		int count = 0;
		float offsetx = 0;
		float offsety = 0;
		
		while (count < number) {
			offsetx += cellSizeX;
			if (offsetx >= atlasWidth) {
				offsety += cellSizeY;
				offsetx = 0;
				if (offsety >= atlasHeight) {
					System.err.println("Coordinate out of range of atlas");
					return null;
				}
			}
			count++;
		}
		return new Vector2f((float)offsetx/(float)atlasWidth,(float)offsety/(float)atlasHeight);
	}
	
	/**
	 * GETTERS AND SETTERS
	 */

	public float getCellSizeX() {
		return cellSizeX;
	}

	public void setCellSizeX(float cellSizeX) {
		this.cellSizeX = cellSizeX;
	}

	public float getCellSizeY() {
		return cellSizeY;
	}

	public void setCellSizeY(float cellSizeY) {
		this.cellSizeY = cellSizeY;
	}

	public float getAtlasWidth() {
		return atlasWidth;
	}

	public void setAtlasWidth(float atlasWidth) {
		this.atlasWidth = atlasWidth;
	}

	public float getAtlasHeight() {
		return atlasHeight;
	}

	public void setAtlasHeight(float atlasHeight) {
		this.atlasHeight = atlasHeight;
	}

	public int getAtlasTextureId() {
		return atlasTextureId;
	}

	public void setAtlasTextureId(int atlasTextureId) {
		this.atlasTextureId = atlasTextureId;
	}

}
