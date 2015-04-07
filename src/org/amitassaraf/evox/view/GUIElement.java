package org.amitassaraf.evox.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.amitassaraf.evox.core.BoxAABB2;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
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

public class GUIElement {

	/**
	 * Texture atlasID;
	 */
	private int textureAtlasID;
	
	/**
	 * Texture number
	 */
	private int textureNumber = 0;
	
	/**
	 * Gui element hitbox 2d
	 */
	private BoxAABB2 boundingBox;
	
	/**
	 * VBO ID
	 */
	private int vboHandel;
	private int vboiHandel;
	private int indicesCount;
	
	/**
	 * Is this element clickable
	 */
	private boolean clickAble = false;
	
	/**
	 * Primary constructor
	 */
	public GUIElement() {
		
	}
	
	/**
	 * Action of what will happen
	 */
	public void action() {
		
	}
	
	/**
	 * Render/Build method of the element
	 * @param game
	 */
	/**
	 * Overriding the build/render method for the static
	 */
	public void build(Game game) {
		FloatBuffer buff = BufferUtils.createFloatBuffer((2*4*4+(3*4)+(2*4)));
		
		float rx = this.getBoundingBox().getLocation().x;
		float ry = this.getBoundingBox().getLocation().y;

		float red = 1f;
		float green = 1f;
		float blue = 1f;
		float alpha = 1f;
		
		Vector2f size = this.getBoundingBox().getSize();
		Vector2f st = game.getResourceManager().getTextureAtlases().get(textureAtlasID).getCoordinate(textureNumber);
		Vector2f ste = game.getResourceManager().getTextureAtlases().get(textureAtlasID).getEndCoordinate(st);

		buff.put(rx).put(ry).put(0).put(1f); // Top Right Of
																// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.x).put(st.y);

		buff.put(rx).put(size.y + ry).put(0).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.x).put(ste.y);


		buff.put(size.x + rx).put(size.y + ry).put( 0).put(1f); // Bottom Left
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.x).put(ste.y);


		buff.put(size.x + rx).put(ry).put( 0).put(1f); // Bottom
																			// Right
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.x).put(st.y);


		// OpenGL expects to draw vertices in counter clockwise order by default
		short[] indices = new short[6];
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

	public int getTextureAtlasID() {
		return textureAtlasID;
	}

	public GUIElement setTextureAtlasID(int textureAtlasID) {
		this.textureAtlasID = textureAtlasID;
		return this;
	}

	public BoxAABB2 getBoundingBox() {
		return boundingBox;
	}

	public GUIElement setBoundingBox(BoxAABB2 boundingBox) {
		this.boundingBox = boundingBox;
		return this;
	}

	public boolean isClickAble() {
		return clickAble;
	}

	public GUIElement setClickAble(boolean clickAble) {
		this.clickAble = clickAble;
		return this;
	}

	public int getTextureNumber() {
		return textureNumber;
	}

	public GUIElement setTextureNumber(int textureNumber) {
		this.textureNumber = textureNumber;
		return this;
	}

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
	
}
