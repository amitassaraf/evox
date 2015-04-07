
package org.amitassaraf.evox.timing;

import org.amitassaraf.evox.core.Game;
import org.lwjgl.opengl.Display;

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

public class FPSManager {
	
	/**
	 * Calculated Game FPS
	 */
    private int fps;
    
    /**
     * Time of last frame
     */
    private long lastFPS;
    
    /**
     * Actual FPS
     */
    private int showFPS = 0;
    
    /**
     * Game instance
     */
    private Game game;
    
    /**
     * Primary constructor
     * @param game
     */
    public FPSManager(Game game) {
    	this.game = game;
    	TimeManager.getDelta();
    	lastFPS = TimeManager.getTime();
    }
    
    /**
     * Update FPS of Game
     */
    public void updateFPS() {
        if (TimeManager.getTime() - lastFPS > 1000) {
        	showFPS = fps;
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
        renderFPS();
    }
    
    /**
     * Render FPS
     */
    public void renderFPS() {
    	game.getRenderManager().getTextManager().renderString("FPS: "+showFPS,Display.getWidth()-200,20);
    }

	public int getShowFPS() {
		return showFPS;
	}

}