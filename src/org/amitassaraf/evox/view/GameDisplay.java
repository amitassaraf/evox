package org.amitassaraf.evox.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

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

public class GameDisplay {

	/**
	 * is the display closed?
	 */
	private boolean closed = false;

	/**
	 * Game camera and game instance
	 */
	private final Camera gameCamera;
	private final Game game;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public GameDisplay(Game game) {
		// Init Generics
		this.game = game;

		try {
			setDisplayMode(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT,
					false);
			Display.setTitle(Constants.TITLE + " | " + Constants.GAME_VERSION_S);
			Display.setVSyncEnabled(Constants.vSyncEnabled);
			Display.create();
		} catch (LWJGLException exception) {
			Logger.getGlobal().log(Level.SEVERE,
					"Lwjgl Exception was thrown! " + exception.getMessage());
		}

		gameCamera = new Camera(game);

		// Init generics
		initOpenGL();

		Mouse.setGrabbed(true);
	}

	/**
	 * Init opengl states
	 */
	public void initOpenGL() {

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(Constants.FIELD_OF_VIEW,
				((float) Display.getWidth() / (float) Display.getHeight()),
				Constants.NEAR_DISTANCE, Constants.FAR_DISTANCE);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

		// FUNCTIONS
		GL11.glClearColor(204f / 255f, 1f, 248f / 255f, 0.0f); // Blueish
																// Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthMask(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST); // Really Nice
		// Perspective Calculations
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		/*
		 * POLYGON MODE VVVVVVVVVVVVVVVVVV
		 */

		// GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );

		// LIGHTING
		GL11.glEnable(GL11.GL_LIGHTING);
		// LIGHTING
		float lightAmbient[] = { 0.7f, 0.7f, 0.7f, 1.0f }; // Ambient Light
															// Values
		float lightDiffuse[] = { 1.3f, 1.3f, 1.3f, 1.0f }; // Diffuse Light
															// Values
		float lightPosition[] = { 35f, 500f, 35f, 1.0f }; // Diffuse Light
															// Values

		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) temp
				.asFloatBuffer().put(lightAmbient).flip()); // Setup The Ambient
															// Light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) temp
				.asFloatBuffer().put(lightDiffuse).flip()); // Setup The Diffuse
															// Light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) temp
				.asFloatBuffer().put(lightPosition).flip()); // Setup The
																// Diffuse Light

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,
				GL11.GL_AMBIENT_AND_DIFFUSE);

		/*
		 * Shadow states:
		 */

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		{
			FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
			fogColor.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

			int fogMode = GL11.GL_LINEAR;
			GL11.glFogi(GL11.GL_FOG_MODE, fogMode);
			GL11.glFog(GL11.GL_FOG_COLOR, fogColor);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.001f);
			GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_DONT_CARE);
			GL11.glFogf(GL11.GL_FOG_START, Constants.FAR_DISTANCE-100f);
			GL11.glFogf(GL11.GL_FOG_END, Constants.FAR_DISTANCE);
		}
		GL11.glEnable(GL11.GL_FOG);

		this.exitOnGLError("init");

	}

	/**
	 * Main game method, its the gameloop
	 */
	public void gameLoop() {
		while (!closed && !Display.isCloseRequested()) {

			game.updateEngine();
			game.update();

			game.getRenderManager().render();

			Display.update();
			Display.sync(Constants.TARGET_FPS);
		}
		game.getRenderManager().cleanUp();
		game.cleanUp();
		Display.destroy();
		System.exit(0);
	}

	/**
	 * Method that can set the display mode for us
	 * 
	 * @param width
	 * @param height
	 * @param fullscreen
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width)
				&& (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width)
							&& (current.getHeight() == height)) {
						if ((targetDisplayMode == null)
								|| (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode
											.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display
										.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x"
						+ height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height
					+ " fullscreen=" + fullscreen + e);
		}
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public Camera getGameCamera() {
		return gameCamera;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	private void exitOnGLError(String errorMessage) {
		int errorValue = GL11.glGetError();

		if (errorValue != GL11.GL_NO_ERROR) {
			String errorString = GLU.gluErrorString(errorValue);
			System.err.println("ERROR - " + errorMessage + ": " + errorString);

			if (Display.isCreated())
				Display.destroy();
			System.exit(-1);
		}
	}

	public static FloatBuffer asFlippedFloatBuffer(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

}