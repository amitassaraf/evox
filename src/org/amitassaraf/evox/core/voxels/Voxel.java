
package org.amitassaraf.evox.core.voxels;

import java.nio.FloatBuffer;
import org.amitassaraf.evox.core.Chunk;
import static org.amitassaraf.evox.core.Constants.*;
import org.amitassaraf.evox.view.TextureAtlas;
import org.lwjgl.util.vector.Vector2f;
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

public class Voxel {

	public class VoxelData {
		/**
		 * Voxel Data
		 */
		public boolean lightSource;
		public short[] faceTexture;
		public int textureAtlas;
		public boolean opaque = true;
		public boolean solid = true;
		public boolean renderSides = false;

		public VoxelData() {
			faceTexture = new short[6];
			opaque = true;
			solid = true;
			renderSides = false;
		}

		public boolean isLightSource() {
			return lightSource;
		}

		public void setLightSource(boolean lightSource) {
			this.lightSource = lightSource;
		}

		public short[] getFaceTexture() {
			return faceTexture;
		}

		public void setFaceTexture(short[] faceTexture) {
			this.faceTexture = faceTexture;
		}

		public int getTextureAtlas() {
			return textureAtlas;
		}

		public void setTextureAtlas(int textureAtlas) {
			this.textureAtlas = textureAtlas;
		}

		public boolean isOpaque() {
			return opaque;
		}

		public void setOpaque(boolean opaque) {
			this.opaque = opaque;
		}

		public boolean isSolid() {
			return solid;
		}

		public void setSolid(boolean solid) {
			this.solid = solid;
		}

		public boolean isRenderSides() {
			return renderSides;
		}

		public void setRenderSides(boolean renderSides) {
			this.renderSides = renderSides;
		}

	}

	/**
	 * Array of possible voxels
	 */
	public static final Voxel[] voxels = new Voxel[128];

	/**
	 * The voxel Data
	 */
	public VoxelData VoxelData;

	/**
	 * Primary constructor
	 */
	public Voxel() {
		VoxelData = new VoxelData();
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
	public int renderVoxel(FloatBuffer buff, Chunk node, Vector3f offset,
			byte face, byte[] lights, TextureAtlas atlas) {
		float rx = node.getBoundingbox().getMinX();
		float ry = node.getBoundingbox().getMinY();
		float rz = node.getBoundingbox().getMinZ();
		rx += offset.x;
		ry += offset.y;
		rz += offset.z;
		float size = VOXEL_SIZE;

		float baseColor = .086f;

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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Right
			// Of The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad
													// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(1.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(ry).put(rz).put(1f); // Bottom Right Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Left Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(1.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Right
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(size + rx).put(ry).put(size + rz).put(1f); // Top Left Of
																// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Left
																		// Of
			// The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Right
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(rx).put(ry).put(size + rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(rx).put(size + ry).put(size + rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(rx).put(size + ry).put(rz).put(1f); // Bottom Right Of The
															// Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
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
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(ste.getY());

			buff.put(size + rx).put(ry).put(rz).put(1f); // Top Left Of The Quad
															// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(ste.getY());

			buff.put(size + rx).put(size + ry).put(rz).put(1f); // Bottom Left
																// Of The
			// Quad (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(st.getX()).put(st.getY());

			buff.put(size + rx).put(size + ry).put(size + rz).put(1f); // Bottom
																		// Right
			// Of The Quad
			// (Top)
			buff.put(colorValue).put(colorValue).put(colorValue).put(1f);
			buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
			buff.put(ste.getX()).put(st.getY());

			faceCount++;
		}
		return faceCount;
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public VoxelData getVoxelData() {
		return VoxelData;
	}

	public Voxel setTextureAtlas(int id) {
		this.VoxelData.setTextureAtlas(id);
		return this;
	}

	public Voxel setFaceTexture(short faceTexture) {
		for (int i = 0; i < 6; i++) {
			this.VoxelData.faceTexture[i] = faceTexture;
		}
		return this;
	}

	public Voxel setFaceTexture(short[] faceTexture) {
		this.VoxelData.faceTexture = faceTexture;
		return this;
	}

	public Voxel setLightSource(boolean flag) {
		this.VoxelData.setLightSource(flag);
		return this;
	}
	
	public Voxel setOpaque(boolean flag) {
		this.VoxelData.setOpaque(flag);
		return this;
	}
	
	public Voxel setSolid(boolean flag) {
		this.VoxelData.setSolid(flag);
		return this;
	}
	
	public Voxel setRenderSides(boolean flag) {
		this.VoxelData.setRenderSides(flag);
		return this;
	}



	/*
	 * 
	 */
	static {
		voxels[1] = new Voxel().setTextureAtlas(0).setFaceTexture(
				new short[] { 0, 2, 3, 3, 3, 3 });
		voxels[2] = new Voxel().setTextureAtlas(0).setFaceTexture(
				new short[] { 1, 1, 1, 1, 1, 1 });
		voxels[3] = new Voxel().setTextureAtlas(0)
				.setFaceTexture((short) 105)
				.setLightSource(true);
		voxels[4] = new Voxel().setTextureAtlas(0).setFaceTexture(
				new short[] { 21, 21, 20, 20, 20, 20 });
		voxels[5] = new Voxel().setTextureAtlas(0).setFaceTexture((short) 52).setOpaque(false);
		voxels[6] = new Voxel().setTextureAtlas(0).setFaceTexture((short) 4);
		voxels[7] = new StaticBillboardVoxel().setTextureAtlas(0).setFaceTexture(
				new short[] { 91, 90 }).setOpaque(false).setSolid(false);
		voxels[8] = new StaticBillboardVoxel().setTextureAtlas(0).setFaceTexture(
				new short[] { 89, 89 }).setOpaque(false).setSolid(false);
		voxels[9] = new WaterVoxel().setTextureAtlas(0).setFaceTexture((short) 205)
				.setOpaque(false).setSolid(false).setRenderSides(true);
	}

}