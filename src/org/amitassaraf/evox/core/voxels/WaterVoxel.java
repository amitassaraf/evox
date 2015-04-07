package org.amitassaraf.evox.core.voxels;

import static org.amitassaraf.evox.core.Constants.BACK;
import static org.amitassaraf.evox.core.Constants.BACK_TRUE;
import static org.amitassaraf.evox.core.Constants.BOTTOM;
import static org.amitassaraf.evox.core.Constants.BOTTOM_TRUE;
import static org.amitassaraf.evox.core.Constants.FRONT;
import static org.amitassaraf.evox.core.Constants.FRONT_TRUE;
import static org.amitassaraf.evox.core.Constants.LEFT;
import static org.amitassaraf.evox.core.Constants.LEFT_TRUE;
import static org.amitassaraf.evox.core.Constants.RIGHT;
import static org.amitassaraf.evox.core.Constants.RIGHT_TRUE;
import static org.amitassaraf.evox.core.Constants.SIDE_BACK;
import static org.amitassaraf.evox.core.Constants.SIDE_BOTTOM;
import static org.amitassaraf.evox.core.Constants.SIDE_FRONT;
import static org.amitassaraf.evox.core.Constants.SIDE_LEFT;
import static org.amitassaraf.evox.core.Constants.SIDE_RIGHT;
import static org.amitassaraf.evox.core.Constants.SIDE_TOP;
import static org.amitassaraf.evox.core.Constants.TOP;
import static org.amitassaraf.evox.core.Constants.TOP_TRUE;
import static org.amitassaraf.evox.core.Constants.VOXEL_SIZE;

import java.nio.FloatBuffer;

import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.view.TextureAtlas;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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

public class WaterVoxel extends Voxel {
	
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
	public int renderVoxel(FloatBuffer buff, Chunk node, Vector3f offset,
			byte face, byte[] lights, TextureAtlas atlas) {
		float rx = node.getBoundingbox().getMinX();
		float ry = node.getBoundingbox().getMinY();
		float rz = node.getBoundingbox().getMinZ();
		rx += offset.x;
		ry += offset.y;
		rz += offset.z;

		float baseColor = .086f;
		float alpha = .75f;

		float size = VOXEL_SIZE;
		int faceCount = 0;

		// System.out.println(st);
		// System.out.println(ste);

		// TOP
		if ((face & TOP) == TOP_TRUE) {
			float colorValue = (float) (Math.pow(lights[SIDE_TOP] / 15f, 1.4f) + baseColor);
			// System.out.println(lights[SIDE_TOP]);
			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[0]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(size + rx).put(size + ry).put(rz).put(1f); // Top Right Of
																// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Right
			// Of The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		// BOTTOM
		if ((face & BOTTOM) == BOTTOM_TRUE) {
			float colorValue = (float) (Math.pow(lights[SIDE_BOTTOM] / 15f,
					1.4f) + baseColor);

			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[1]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(size + rx).put(+ry).put(size + rz).put(1f); // Top Right Of
																	// The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad
													// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(ry).put(rz).put(1f); // Bottom Right Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		// FRONT
		if ((face & FRONT) == FRONT_TRUE) {
			float colorValue = (float) (Math
					.pow(lights[SIDE_FRONT] / 15f, 1.4f) + baseColor);

			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[2]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(size + rx).put(ry).put(rz).put(1f); // Top Right Of The
															// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Left Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Right
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		// BACK
		if ((face & BACK) == BACK_TRUE) {
			float colorValue = (float) (Math.pow(lights[SIDE_BACK] / 15f, 1.4f) + baseColor);

			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[3]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(rx).put(ry).put(size + rz).put(1f); // Top Right Of The
															// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Left Of
																// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Left
																		// Of
			// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Right
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		// RIGHT
		if ((face & RIGHT) == RIGHT_TRUE) {
			float colorValue = (float) (Math
					.pow(lights[SIDE_RIGHT] / 15f, 1.4f) + baseColor);

			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[4]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(rx).put(ry).put(rz).put(1f); // Top Right Of The Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Right Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		// LEFT
		if ((face & LEFT) == LEFT_TRUE) {
			float colorValue = (float) (Math.pow(lights[SIDE_LEFT] / 15f, 1.4f) + baseColor);

			Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[5]);
			Vector2f ste = atlas.getEndCoordinate(st);
			buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Right Of
																// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(size + rx).put(ry).put(rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Right
			// Of The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(alpha);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		return faceCount;
	}

}
