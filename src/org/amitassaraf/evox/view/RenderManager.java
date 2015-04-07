package org.amitassaraf.evox.view;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.HashMap;

import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.GameState;
import org.amitassaraf.evox.resources.ShaderManager;
import org.amitassaraf.evox.timing.FPSManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Author: Amit Assaraf � 2013-2014 Israel Jerusalem | Givon Hahadasha Egoz St.
 * House 3 Right | All rights to this code are reserved to/for the author aka
 * Amit Assaraf. Any publishing of this code without authorization from the
 * author will lead to bad consciences. Therfore do not redistribute this
 * file/code. No snippets of this code may be redistributed/published.
 * 
 * Contact information: Amit Assaraf - Email: soit@walla.com Phone: 0505964411
 * (Israel)
 *
 */

public class RenderManager {

	/**
	 * Hashmap to store all the renderers according to their states
	 */
	private HashMap<GameState, Renderer> renderers;

	/**
	 * The manager of the frustum
	 */
	private FrustumManager frustumManager;

	/**
	 * The text manager, TODO: should be switched out for a bitmap one later
	 */
	private TextManager textManager;

	/**
	 * The FPS Manager
	 */
	private FPSManager debugFPS;

	/**
	 * The game instance
	 */
	private Game game;

	/**
	 * The shader manager
	 */
	private ShaderManager shaderManager;

	/**
	 * The manager of the GUI
	 */
	private GUIManager guiManager;

	/**
	 * When was the last frame
	 */
	private long lastFrame = 0;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public RenderManager(Game game) {
		this.game = game;
		frustumManager = new FrustumManager(game);
		textManager = new TextManager(game);
		debugFPS = new FPSManager(game);
		renderers = new HashMap<GameState, Renderer>();
		shaderManager = new ShaderManager();
		guiManager = new GUIManager(game);
		guiManager.addEngineGUI();
	}

	/**
	 * Method to initiate all renderers
	 */
	public void initRenderers() {
		for (Renderer r : renderers.values()) {
			r.init();
		}
	}

	/**
	 * Build the current renderer
	 */
	public void build() {
		renderers.get(game.getGameState()).build();
		guiManager.build();
	}

	/**
	 * Render according to the current game state.
	 */
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear The Screen
															// And The Depth
															// Buffer

		GL20.glUseProgram(game.getRenderManager().getShaderManager().getShaderProgramID());
		{
			// Upload matrices to the uniform variables
			renderers.get(game.getGameState()).render();
		}
		GL20.glUseProgram(0);

		Enable2D();
		{
			guiManager.render();
			debugFPS.updateFPS();
			renderDebug();
		}
		Disable2D();

		lastFrame = System.currentTimeMillis();

	}

	/**
	 * Render debug information onto the screen
	 */
	private void renderDebug() {
		textManager.renderString("Player Coords: "
				+ game.getGameDisplay().getGameCamera().getPosition()
						.toString(), 50, 20);
		textManager.renderString("Camera Rotation: "
				+ game.getGameDisplay().getGameCamera().getRotation()
						.toString(), 50, 50);
		textManager.renderString("TIME: "
				+ game.getTimeManager().getTimeOfDay(),
				Display.getWidth() - 200, 45);
	}

	/**
	 * Method to enable 2D
	 */
	public void Enable2D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight(), 0, -1, 1);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}

	/**
	 * Method to disable 2D
	 */
	public void Disable2D() {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Method to cleaup all renderers
	 */
	public void cleanUp() {
		for (Renderer r : renderers.values()) {
			r.cleanUp();
		}
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public HashMap<GameState, Renderer> getRenderers() {
		return renderers;
	}

	public FrustumManager getFrustumManager() {
		return frustumManager;
	}

	public TextManager getTextManager() {
		return textManager;
	}

	public ShaderManager getShaderManager() {
		return shaderManager;
	}

	public long getLastFrame() {
		return lastFrame;
	}

	public void setLastFrame(long lastFrame) {
		this.lastFrame = lastFrame;
	}

	public FPSManager getDebugFPS() {
		return debugFPS;
	}

	public void setDebugFPS(FPSManager debugFPS) {
		this.debugFPS = debugFPS;
	}
	
	

}