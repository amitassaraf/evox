package org.amitassaraf.evox.core.voxels;

import static org.amitassaraf.evox.core.Constants.SIDE_TOP;
import static org.amitassaraf.evox.core.Constants.VOXEL_SIZE;

import java.nio.FloatBuffer;
import java.util.Random;

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

public class StaticBillboardVoxel extends Voxel {
	
	private static Random rand = new Random();

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
		float size = VOXEL_SIZE;

		rx += offset.x;
		ry += offset.y;
		rz += offset.z;
		
		float baseColor = .086f;

		int faceCount = 0;

		// System.out.println(st);
		// System.out.println(ste);
		
		// FRONT
		float colorValue = (float) (Math.pow(lights[SIDE_TOP] / 15f, 1.4f) + baseColor);

		Vector2f st = atlas.getCoordinate(this.VoxelData.faceTexture[0]);
		Vector2f ste = atlas.getEndCoordinate(st);
		buff.put(size + rx).put(ry).put(rz + size/2f).put(1f); // Top Right Of The
														// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(rx).put(ry).put(rz + size/2f).put(1f); // Top Left Of The Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(rx).put(size + ry).put(rz + size/2f).put(1f); // Bottom Left Of The
														// Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx).put(size + ry).put(rz + size/2f).put(1f); // Bottom Right
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;

		// LEFT
		colorValue = (float) (Math.pow(lights[SIDE_TOP] / 15f, 1.4f) + baseColor);

		st = atlas.getCoordinate(this.VoxelData.faceTexture[1]);
		ste = atlas.getEndCoordinate(st);
		buff.put(size + rx - size/2f).put(ry).put(size + rz).put(1f); // Top Right Of
															// The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(ste.getY());

		buff.put(size + rx - size/2f).put(ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(ste.getY());

		buff.put(size + rx - size/2f).put(size + ry).put(rz).put(1f); // Bottom Left
															// Of The
		// Quad (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(st.getX()).put(st.getY());

		buff.put(size + rx - size/2f).put(size + ry).put(size + rz).put(1f); // Bottom
																	// Right
		// Of The Quad
		// (Top)
		buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(ste.getX()).put(st.getY());

		faceCount++;
		
		return faceCount;
	}

}
