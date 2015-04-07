package org.amitassaraf.evox.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.amitassaraf.evox.core.BoxAABB3;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

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

public class BoxRenderer {

	/**
	 * Mesh builder VBO handle & VBOi Handle Also the indices Count
	 */
	private int vboHandel;
	private int indicesCount;
	private int vboiHandel;

	private BoxAABB3 boundingBox;

	public BoxRenderer(BoxAABB3 bou) {
		this.boundingBox = bou;
	}

	/**
	 * Start building the chunk
	 * 
	 * @param node
	 * @param game
	 */
	public void build(Game game) {
		int faceCount = 1;
		FloatBuffer buff = BufferUtils.createFloatBuffer(66 * 6);
		faceCount += renderBox(buff, game.getResourceManager()
				.getTextureAtlases().get(3));

		// OpenGL expects to draw vertices in counter clockwise order by default
		short[] indices = new short[faceCount * 6];
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
	 * Method to render / Build a voxel
	 * 
	 * @param buff
	 * @param node
	 * @param offset
	 * @param face
	 * @param lights
	 * @param atlas
	 * @return
	 */
	public int renderBox(FloatBuffer buff, TextureAtlas atlas) {
		float rx = boundingBox.getMinX();
		float ry = boundingBox.getMinY();
		float rz = boundingBox.getMinZ();

		float colorValue = 1f;

		float maxX = boundingBox.getMaxX();
		float maxY = boundingBox.getMaxY();
		float maxZ = boundingBox.getMaxZ();

		int faceCount = 0;

		// System.out.println(st);
		// System.out.println(ste);

		// TOP
		// System.out.println(lights[SIDE_TOP]);
		Vector2f st = atlas.getCoordinate(0);
		Vector2f ste = atlas.getEndCoordinate(st);
		buff.put(maxX).put(maxY).put(rz).put(1f); // Top Right Of

															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());


		buff.put(rx).put(maxY).put(rz).put(1f); // Top Left Of The Quad

														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());


		buff.put(rx).put(maxY).put(maxZ).put(1f); // Bottom Left
			// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(maxX).put(maxY).put(maxZ).put(1f); // Bottom

																	// Right
		// Of The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// BOTTOM


		buff.put(maxX).put(+ry).put(maxZ).put(1f); // Top Right Of

																// The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());


		buff.put(rx).put(ry).put(maxZ).put(1f); // Top Left Of The Quad

														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad
												// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(maxX).put(ry).put(rz).put(1f); // Bottom Right Of The

														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// FRONT


		buff.put(maxX).put(ry).put(rz).put(1f); // Top Right Of The

														// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());


		buff.put(rx).put(maxY).put(rz).put(1f); // Bottom Left Of The

														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(maxX).put(maxY).put(rz).put(1f); // Bottom Right

															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// BACK


		buff.put(rx).put(ry).put(maxZ).put(1f); // Top Right Of The

														// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());


		buff.put(maxX).put(ry).put(maxZ).put(1f); // Top Left Of

															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());


		buff.put(maxX).put(maxY).put(maxZ).put(1f); // Bottom

																	// Left
																	// Of
		// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(rx).put(maxY).put(maxZ).put(1f); // Bottom Right

															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// RIGHT

		buff.put(rx).put(ry).put(rz).put(1f); // Top Right Of The Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());


		buff.put(rx).put(ry).put(maxZ).put(1f); // Top Left Of The Quad

														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());


		buff.put(rx).put(maxY).put(maxZ).put(1f); // Bottom Left
		// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(rx).put(maxY).put(rz).put(1f); // Bottom Right Of The

														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		// LEFT

		buff.put(maxX).put(ry).put(maxZ).put(1f); // Top Right Of

															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());


		buff.put(maxX).put(ry).put(rz).put(1f); // Top Left Of The Quad

														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());


		buff.put(maxX).put(maxY).put(rz).put(1f); // Bottom Left

															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());


		buff.put(maxX).put(maxY).put(maxZ).put(1f); // Bottom

																	// Right
		// Of The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		return faceCount;
	}

	public int getVboHandel() {
		return vboHandel;
	}

	public void setVboHandel(int vboHandel) {
		this.vboHandel = vboHandel;
	}

	public int getIndicesCount() {
		return indicesCount;
	}

	public void setIndicesCount(int indicesCount) {
		this.indicesCount = indicesCount;
	}

	public int getVboiHandel() {
		return vboiHandel;
	}

	public void setVboiHandel(int vboiHandel) {
		this.vboiHandel = vboiHandel;
	}

	public BoxAABB3 getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoxAABB3 boundingBox) {
		this.boundingBox = boundingBox;
	}

}
