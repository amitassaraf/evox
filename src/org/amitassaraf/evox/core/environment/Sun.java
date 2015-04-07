package org.amitassaraf.evox.core.environment;

import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.Static;
import org.amitassaraf.evox.core.StaticBillboard;
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

public class Sun extends StaticBillboard {

	public Sun(Vector3f location) {
		super(location, new Vector3f(10, 10, 10), 23);
	}


}
