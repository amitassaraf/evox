
package org.amitassaraf.evox.input;

import org.amitassaraf.evox.core.BoxAABB3;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.EntityPlayer;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.input.keyboard.ExitGameInput;
import org.amitassaraf.evox.input.keyboard.PauseTimeInput;
import org.amitassaraf.evox.input.keyboard.PlayerMovmentInput;
import org.amitassaraf.evox.input.keyboard.RehookMouseInput;
import org.amitassaraf.evox.input.keyboard.UnhookMouseInput;
import org.amitassaraf.evox.view.BoxRenderer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Vector3f;

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

public class EvoxInputManager {
	
	/**
	 * Inputs for the game
	 */
	private ArrayList<EvoxInput> keyboardGame = new ArrayList<EvoxInput>();
	/**
	 * Multi-inputs for the game
	 */
	private ArrayList<EvoxMultiInput> keyboardMultiGame = new ArrayList<EvoxMultiInput>();
	/**
	 * Inputs for menu
	 */
	private ArrayList<EvoxInput> keyboardMenu = new ArrayList<EvoxInput>();
	/**
	 * Global inputs
	 */
	private ArrayList<EvoxInput> keyboardGlobal = new ArrayList<EvoxInput>();
	
	/**
	 * Mouse manager
	 */
	private EvoxMouseManager mouseManager;
	
	/**
	 * Game instance
	 */
	private final Game game;

	/**
	 * Primary constructor
	 * @param game
	 */
	public EvoxInputManager(Game game) {
		this.game = game;
		mouseManager = new EvoxMouseManager(game);
		registerInputs();
		keyboardMultiGame.add(new PlayerMovmentInput(this));
		keyboardGame.add(new PauseTimeInput());
		keyboardGame.add(new UnhookMouseInput());
		keyboardGame.add(new RehookMouseInput());
		keyboardGame.add(new EvoxInput(Keyboard.KEY_B) {
			
			@Override
			public void action(Game game) {
				game.getPlayers().get(0).applyGravity(!game.getPlayers().get(0).isUsingGravity());
			}
		});
	}
	
	/**
	 * Main process method. This will process game inputs
	 */
	public void process() {
		for (EvoxInput in : keyboardGlobal) {
			if (Keyboard.isKeyDown(in.getKey())) {
				in.action(game);
			}
		}
		switch (game.getGameState()) {
			case GAME: {
				mouseManager.updateMouse();
				for (EvoxInput in : keyboardGame) {
					if (Keyboard.isKeyDown(in.getKey())) {
						in.action(game);
					}
				}
				for (EvoxMultiInput in : keyboardMultiGame) {
					in.action(game);
				}
			} break;
			case MENU: {
				for (EvoxInput in : keyboardMenu) {
					if (Keyboard.isKeyDown(in.getKey())) {
						in.action(game);
					}
				}
			} break;
		default:
			break;
		}
	}
	
	/**
	 * Register engine inputs
	 */
	public void registerInputs() {
		try {
			registerInput("global", new ExitGameInput());
		} catch (Exception e) {
			Logger.getGlobal().log(Level.WARNING, "Wrong type exception! "+e.getMessage());		
		}
	}
	
	/**
	 * Method to register input
	 * @param type
	 * @param input
	 * @throws Exception
	 */
	public void registerInput(String type, EvoxInput input) throws Exception {
        if (type.equals("Game")) {
            keyboardGame.add(input);
        } else if (type.equals("game")) {
            keyboardGame.add(input);
        } else if (type.equals("menu")) {
            keyboardMenu.add(input);
        } else if (type.equals("Menu")) {
            keyboardMenu.add(input);
        } else if (type.equals("global")) {
            keyboardGlobal.add(input);
        } else if (type.equals("Global")) {
            keyboardGlobal.add(input);
        } else {
            throw new Exception("Wrong type!!");
        }
	}

	/**
	 * GETTERS AND SETTERS 
	 */
	
	public Game getGame() {
		return game;
	}

	public EvoxMouseManager getMouseManager() {
		return mouseManager;
	}

	public ArrayList<EvoxMultiInput> getKeyboardMultiGame() {
		return keyboardMultiGame;
	}

}