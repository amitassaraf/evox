package org.amitassaraf.evox.view;

import java.awt.Font;
import org.amitassaraf.evox.core.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

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


public class TextManager {

	/**
	 * Game font
	 */
	private UnicodeFont  font;

	/**
	 * Primary constructor
	 * @param game
	 */
	public TextManager(Game game) {
		init();
	}

	/**
	 * Initiate fonts
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new UnicodeFont(awtFont);
	    font.getEffects().add(new ColorEffect(java.awt.Color.white));
	    font.addAsciiGlyphs();
	    try {
	        font.loadGlyphs();
	    } catch (SlickException ex) {
	    }
	}
	
	/**
	 * Render a string
	 * @param s
	 * @param x
	 * @param y
	 */
	public void renderString(String s, float x, float y) {
		font.drawString(x, y, s);
	}
	
	/**
	 * render a string
	 * @param s
	 * @param x
	 * @param y
	 * @param c
	 */
	public void renderString(String s, float x, float y,Color c) {
		font.drawString(x, y, s, c);
	}
	
}