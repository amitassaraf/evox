package org.amitassaraf.evox.core.environment;

import java.nio.FloatBuffer;
import java.util.Random;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.Static;
import org.amitassaraf.evox.view.TextureAtlas;

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

public class Cloud extends Static {

	/**
	 * Random object to generate a random sized chunk
	 */
	private static Random rand = new Random();

	/**
	 * Primary constructor
	 * @param location
	 */
	public Cloud(Vector3f location) {
		super(location, new Vector3f(rand.nextInt(200) + 50,
				rand.nextInt(50) + 20, rand.nextInt(200) + 50));
		this.setTexture(false);
	}

	/**
	 * Overriding the build/render method fo the static
	 */
	@Override
	public int render(FloatBuffer buff, Game game, TextureAtlas atlas) {
		float rx = this.getBoundingBox().getMinX();
		float ry = this.getBoundingBox().getMinY();
		float rz = this.getBoundingBox().getMinZ();

		float red = 1f;
		float green = 1f;
		float blue = 1f;
		float alpha = 0.7f;
		
		Vector3f size = this.getBoundingBox().getSize();

		buff.put(size.x + rx).put(size.y + ry).put(rz).put(1f); // Top Right Of
																// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);

		buff.put(rx).put(size.y + ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom Left
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom
																			// Right
		// Of The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(-1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(+ry).put(size.z + rz).put(1f); // Top Right Of
																	// The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(size.z + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(rz).put(1f); // Bottom Left Of The Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(1.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(ry).put(rz).put(1f); // Top Right Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(rz).put(1f); // Top Left Of The Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(size.y + ry).put(rz).put(1f); // Bottom Left Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(size.y + ry).put(rz).put(1f); // Bottom Right
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(size.z + rz).put(1f); // Top Right Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(ry).put(size.z + rz).put(1f); // Top Left Of
																// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom
																			// Left
																			// Of
		// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom Right
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(0.0f).put(0.0f).put(-1.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(rz).put(1f); // Top Right Of The Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(ry).put(size.z + rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom Left
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(rx).put(size.y + ry).put(rz).put(1f); // Bottom Right Of The
														// Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);

		buff.put(size.x + rx).put(ry).put(size.z + rz).put(1f); // Top Right Of
																// The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(ry).put(rz).put(1f); // Top Left Of The Quad
														// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(size.y + ry).put(rz).put(1f); // Bottom Left
																// Of The
		// Quad (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);


		buff.put(size.x + rx).put(size.y + ry).put(size.z + rz).put(1f); // Bottom
																			// Right
		// Of The Quad
		// (Top)
		buff.put(red).put(green).put(blue).put(alpha);
		buff.put(-1.0f).put(0.0f).put(0.0f); // Normal
		buff.put(-1f).put(-1f);

		return 6;
	}

}
