package org.amitassaraf.evox.core;

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

import java.nio.FloatBuffer;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.Static;
import org.amitassaraf.evox.view.TextureAtlas;
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

public class StaticBillboard extends Static {

	/**
	 * Primary constructor
	 * 
	 * @param location
	 */
	public StaticBillboard(Vector3f location, Vector3f size,
			int billboardTexture) {
		super(location, size);
		this.setTexture(true);
		super.setTextureNumber(billboardTexture);
	}

	/**
	 * Overriding the build/render method of the static
	 */
	@Override
	public int render(FloatBuffer buff, Game game, TextureAtlas atlas) {
		float rx = this.getBoundingBox().getMinX();
		float ry = this.getBoundingBox().getMinY();
		float rz = this.getBoundingBox().getMinZ();
		
		float size = 10f;

		float colorValue = 1f;
		int faceCount = 0;

		// TOP
		Vector2f st = atlas.getCoordinate(this.getTextureNumber());
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
		// BOTTOM
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
		// FRONT
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
		// BACK
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
		// RIGHT
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
		// LEFT
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
		return faceCount;
	}

}
