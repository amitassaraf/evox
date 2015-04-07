
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

public abstract class EvoxInput {
	
	/**
	 * Input key
	 */
	private final int key;
	
	/**
	 * Primary constructor
	 * @param key
	 */
	public EvoxInput(int key) {
		this.key = key; 
	}
	
	/**
	 * Action on input
	 * @param game
	 */
	public abstract void action(Game game);

	public int getKey() {
		return key;
	}
	
}