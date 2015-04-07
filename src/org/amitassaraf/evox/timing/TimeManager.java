
package org.amitassaraf.evox.timing;

import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.Sys;

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

public class TimeManager extends Thread {	
	
	/**
	 * Time since last FRAME
	 */
    private static long lastFrame;
    
    /**
     * Game instance
     */
	private Game game;
	
	/**
	 * Current time of day
	 */
	private long timeOfDay = 0;

	private boolean paused;
	    
    public TimeManager(Game game) {
    	this.game = game;
    }
	
    /**
     * Method to get the current delta
     * @return
     */
	public static double getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta / 1000.0;
    }

	/**
	 * Method to get the machine time
	 * @return
	 */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	 }
	
	/**
	 * Main time manager method
	 */
	public void run() {
		while (!game.getGameDisplay().isClosed()) {
			tick();
			timeOfDay += 1;
			try {
				Thread.sleep((long) (Constants.TICK_TIME_MS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			
			while (paused) {
				try {
					Thread.sleep((long) (Constants.TICK_TIME_MS));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	
	/**
	 * Method to tick the world :-D
	 */
	public void tick() {

	}

	/**
	 * GETTERS AND SETTERS 
	 */
	
	public long getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(long timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public void togglePause() {
		paused = !paused;
	}

}
