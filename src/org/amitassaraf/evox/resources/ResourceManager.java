
package org.amitassaraf.evox.resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.view.TextureAtlas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

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

public class ResourceManager {

	/**
	 * Game instance
	 */
	private Game game;
	
	/**
	 * Hashmap to hold the textureAtlases
	 */
	private HashMap<Integer,TextureAtlas> textureAtlases; 
	
	/**
	 * Primary constructor
	 * @param game
	 */
	public ResourceManager(Game game) {
		this.game = game;
		textureAtlases = new HashMap<Integer,TextureAtlas>();
	}
	
	/**
	 * load game texture atlases TODO: This is temporary
	 */
	public void loadTextureAtlases() {
		textureAtlases.put(0, new TextureAtlas(loadPNGTexture("res/textures/atlases/atlas.png"),16,256,256));
		textureAtlases.put(1, new TextureAtlas(loadPNGTexture("res/textures/crosshair.png"),128,128,128));
		textureAtlases.put(2, new TextureAtlas(loadPNGTexture("res/textures/skyboxes/skybox.png"),512,2048,1536));
		textureAtlases.put(3, new TextureAtlas(loadPNGTexture("res/textures/atlases/atlasDebug.png"),16,256,256));

	}
	
	/**
	 * Load a PNG texture into the engine
	 * @param path
	 * @return
	 */
	private int loadPNGTexture(String path) {
		int texHandle = GL11.glGenTextures();  
		PNGDecoder decoder = null;
		ByteBuffer buf = null;
		InputStream in = null;
		try {
		   in = new FileInputStream(path);
		   decoder = new PNGDecoder(in);

		   buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		   decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		   buf.flip();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		   try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texHandle);   
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(),
				decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);   
		return texHandle;
	}

	/**
	 * Load the GLSL Shaders
	 */
	public void loadGLSLShaders() {
		// Load the vertex shader
		int vsId = this.loadShader("res/shaders/screen.vert",
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = this.loadShader("res/shaders/screen.frag",
				GL20.GL_FRAGMENT_SHADER);
	
		// Create a new shader program that links both shaders
		game.getRenderManager().getShaderManager().setShaderProgramID(GL20.glCreateProgram());
		GL20.glAttachShader(game.getRenderManager().getShaderManager().getShaderProgramID(), vsId);
		GL20.glAttachShader(game.getRenderManager().getShaderManager().getShaderProgramID(), fsId);

		GL20.glLinkProgram(game.getRenderManager().getShaderManager().getShaderProgramID());
		GL20.glValidateProgram(game.getRenderManager().getShaderManager().getShaderProgramID());

		this.exitOnGLError("setupShaders");
	}


	/**
	 * Load a shader
	 * @param filename
	 * @param type
	 * @return
	 */
	private int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		
		this.exitOnGLError("loadShader");
		
		return shaderID;
	}
	
	/**
	 * Method to exit incase an OpenGL error has occured
	 * @param errorMessage
	 */
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

	public HashMap<Integer, TextureAtlas> getTextureAtlases() {
		return textureAtlases;
	}

}