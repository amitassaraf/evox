
package org.amitassaraf.evox.input;

import org.amitassaraf.evox.core.Game;

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

public abstract class EvoxMultiInput {

	/**
	 * Instance on input manager
	 */
	protected final EvoxInputManager im;
	
	/**
	 * Primary constructor
	 * @param im
	 */
	public EvoxMultiInput(EvoxInputManager im) {
		this.im = im;
	}
	
	/**
	 * The action method for the input
	 * @param game
	 */
	public abstract void action(Game game);

	public EvoxInputManager getIm() {
		return im;
	}

}