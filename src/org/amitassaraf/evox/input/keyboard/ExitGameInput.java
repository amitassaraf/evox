
package org.amitassaraf.evox.input.keyboard;

import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.input.EvoxInput;
import org.lwjgl.input.Keyboard;

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

public class ExitGameInput extends EvoxInput {

	/**
	 * Primary constructor
	 */
	public ExitGameInput() {
		super(Keyboard.KEY_ESCAPE);
	}

	@Override
	public void action(Game game) {
		game.getGameDisplay().setClosed(true); //Close the game
	}

	

}