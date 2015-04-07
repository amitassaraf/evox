package org.amitassaraf.evox.view.renderers;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import org.amitassaraf.evox.core.Chunk;
import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.EngineUtils;
import org.amitassaraf.evox.core.EntityPlayer;
import org.amitassaraf.evox.core.Game;
import org.amitassaraf.evox.core.Static;
import org.amitassaraf.evox.view.BoxRenderer;
import org.amitassaraf.evox.view.GameDisplay;
import org.amitassaraf.evox.view.Renderer;
import org.amitassaraf.evox.view.Skybox;
import org.amitassaraf.evox.view.VertexData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
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

public class GameRenderer extends Renderer {

	/**
	 * Game instance (as this is the game renderer :-D )
	 */
	private Game game;

	/**
	 * Game skybox
	 */
	private Skybox skybox;

	/**
	 * Primary constructor
	 * 
	 * @param game
	 */
	public GameRenderer(Game game) {
		this.game = game;
		skybox = new Skybox();

	}

	/**
	 * Initiate renderer
	 */
	@Override
	public void init() {
	}

	/**
	 * Build renderer
	 */
	@Override
	public void build() {
		for (Chunk etn : game.getWorld().getNodes()) {
			etn.resetAllFaces();
			etn.internalFaceUpdate();
			try {
				etn.updateLightingValues(game);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		game.getWorld().crossChunkCulling();
		game.getWorld().edgeWorldCulling();

		for (Chunk etn : game.getWorld().getNodes()) {
			etn.getEcmb().build(game);
		}

		skybox.build(game);

	}

	/**
	 * Temporary buffers for proj and modl
	 */
	FloatBuffer proj = BufferUtils.createFloatBuffer(16);
	FloatBuffer modl = BufferUtils.createFloatBuffer(16);

	@Override
	public void render() {

		GL11.glLoadIdentity();
		GL11.glRotatef((float) game.getGameDisplay().getGameCamera()
				.getRotation().x, 1, 0, 0);
		GL11.glRotatef((float) game.getGameDisplay().getGameCamera()
				.getRotation().y, 0, 1, 0);
		GL11.glRotatef((float) game.getGameDisplay().getGameCamera()
				.getRotation().z, 0, 0, 1);

		Vector3f cPos = game
				.getGameDisplay().getGameCamera().getPosition();
		Vector3f translated = new Vector3f(cPos.x + game.getPlayers().get(0).getEntityHitBox().getSize().z/2,
				cPos.y,
				cPos.z + game.getPlayers().get(0).getEntityHitBox().getSize().z/2);
		
		org.lwjgl.util.vector.Vector3f pos = EngineUtils.toNegitive(translated);
		GL11.glTranslatef(pos.x, pos.y, pos.z);

		GL11.glLight(
				GL11.GL_LIGHT1,
				GL11.GL_POSITION,
				GameDisplay.asFlippedFloatBuffer(new float[] { pos.x, pos.y,
						pos.z, 1 }));

		game.getRenderManager().getFrustumManager().extractFrustum();

		int loc1 = GL20.glGetUniformLocation(game.getRenderManager()
				.getShaderManager().getShaderProgramID(), "defColor");
		// This is the location of the renderMode uniform variable in our
		// program.
		GL20.glUniform3f(loc1, Constants.ambientLight.x,
				Constants.ambientLight.y, Constants.ambientLight.z);

		setTextureUnit0(game.getRenderManager().getShaderManager()
				.getShaderProgramID());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

		/*
		 * if (game.getTimeManager().getTimeOfDay() >= Constants.NIGHT) {
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.getResourceManager()
		 * .getTextureAtlases().get(skybox.getTextureAtlasID())
		 * .getAtlasTextureId());
		 * 
		 * GL11.glDisable(GL11.GL_CULL_FACE);
		 * GL11.glDisable(GL11.GL_DEPTH_TEST);
		 * 
		 * GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, skybox.getVboHandel());
		 * 
		 * GL11.glVertexPointer(VertexData.positionElementCount, GL11.GL_FLOAT,
		 * VertexData.stride, VertexData.positionByteOffset);
		 * GL11.glColorPointer(VertexData.colorElementCount, GL11.GL_FLOAT,
		 * VertexData.stride, VertexData.colorByteOffset);
		 * GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
		 * VertexData.normalByteOffset);
		 * GL11.glTexCoordPointer(VertexData.stElementCount, GL11.GL_FLOAT,
		 * VertexData.stride, VertexData.stByteOffset);
		 * 
		 * // Bind to the index VBO that has all the information about the //
		 * order of the vertices GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
		 * skybox.getVboiHandel());
		 * 
		 * // Draw the vertices GL11.glDrawElements(GL11.GL_TRIANGLES,
		 * skybox.getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);
		 * 
		 * // Put everything back to default (deselect)
		 * GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		 * 
		 * GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		 * 
		 * GL11.glEnable(GL11.GL_DEPTH_TEST); GL11.glEnable(GL11.GL_CULL_FACE);
		 * 
		 * }
		 */

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.getResourceManager()
				.getTextureAtlases().get(0).getAtlasTextureId());
		
		for (Chunk etn : game.getWorld().getNodes()) {
			Vector3f bb = etn.getBoundingbox().getSize();

			if (game.getRenderManager()
					.getFrustumManager()
					.cubeInFrustum(etn.getBoundingbox().getMinX(),
							etn.getBoundingbox().getMinY(),
							etn.getBoundingbox().getMinZ(),
							EngineUtils.toGLVector(bb))) {

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, etn.getEcmb()
						.getVboHandelOpaque());

				GL11.glVertexPointer(VertexData.positionElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.positionByteOffset);
				GL11.glColorPointer(VertexData.colorElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.colorByteOffset);
				GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
						VertexData.normalByteOffset);
				GL11.glTexCoordPointer(VertexData.stElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.stByteOffset);

				// Bind to the index VBO that has all the information about the
				// order of the vertices
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, etn.getEcmb()
						.getVboiHandelOpaque());

				// Draw the vertices
				GL11.glDrawElements(GL11.GL_TRIANGLES, etn.getEcmb()
						.getIndicesCountOpaque(), GL11.GL_UNSIGNED_INT, 0);

				// Put everything back to default (deselect)
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			}
		}
		
		for (int i = 0; i < game.getWorld().getStaticObjects().size(); i++) {
			Static obj = game.getWorld().getStaticObjects().get(i);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, obj.getMeshBuilder().getVboHandel());

			GL11.glVertexPointer(VertexData.positionElementCount,
					GL11.GL_FLOAT, VertexData.stride,
					VertexData.positionByteOffset);
			GL11.glColorPointer(VertexData.colorElementCount, GL11.GL_FLOAT,
					VertexData.stride, VertexData.colorByteOffset);
			GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
					VertexData.normalByteOffset);
			GL11.glTexCoordPointer(VertexData.stElementCount, GL11.GL_FLOAT,
					VertexData.stride, VertexData.stByteOffset);

			// Bind to the index VBO that has all the information about the
			// order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
					obj.getMeshBuilder().getVboiHandel());

			// Draw the vertices
			GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getMeshBuilder().getIndicesCount(),
					GL11.GL_UNSIGNED_SHORT, 0);

			// Put everything back to default (deselect)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}		
		
		
		/**
		 * Render non opaque chunk blocks
		 */
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.getResourceManager()
				.getTextureAtlases().get(3).getAtlasTextureId());
		
		BoxRenderer renderer = game.getPlayers().get(0).getRender();
		renderer.build(game);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, renderer.getVboHandel());

		GL11.glVertexPointer(VertexData.positionElementCount,
				GL11.GL_FLOAT, VertexData.stride,
				VertexData.positionByteOffset);
		GL11.glColorPointer(VertexData.colorElementCount, GL11.GL_FLOAT,
				VertexData.stride, VertexData.colorByteOffset);
		GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
				VertexData.normalByteOffset);
		GL11.glTexCoordPointer(VertexData.stElementCount, GL11.GL_FLOAT,
				VertexData.stride, VertexData.stByteOffset);

		// Bind to the index VBO that has all the information about the
		// order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
				renderer.getVboiHandel());

		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, renderer.getIndicesCount(),
				GL11.GL_UNSIGNED_SHORT, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.getResourceManager()
				.getTextureAtlases().get(0).getAtlasTextureId());
		
		for (Chunk etn : game.getWorld().getNodes()) {
			Vector3f bb = etn.getBoundingbox().getSize();

			if (game.getRenderManager()
					.getFrustumManager()
					.cubeInFrustum(etn.getBoundingbox().getMinX(),
							etn.getBoundingbox().getMinY(),
							etn.getBoundingbox().getMinZ(),
							EngineUtils.toGLVector(bb))) {

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, etn.getEcmb()
						.getVboHandelNonOpaque());

				GL11.glVertexPointer(VertexData.positionElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.positionByteOffset);
				GL11.glColorPointer(VertexData.colorElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.colorByteOffset);
				GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
						VertexData.normalByteOffset);
				GL11.glTexCoordPointer(VertexData.stElementCount,
						GL11.GL_FLOAT, VertexData.stride,
						VertexData.stByteOffset);

				// Bind to the index VBO that has all the information about the
				// order of the vertices
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, etn.getEcmb()
						.getVboiHandelNonOpaque());

				// Draw the vertices
				GL11.glDrawElements(GL11.GL_TRIANGLES, etn.getEcmb()
						.getIndicesCountNonOpaque(), GL11.GL_UNSIGNED_INT, 0);

				// Put everything back to default (deselect)
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			}
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		// ///////////////////////////////RENDER
		// ENTITYS\\\\\\\\\\\\\\\\\\\\\\\\\\\

		for (int i = 1; i < game.getPlayers().size(); i++) {
			EntityPlayer player = game.getPlayers().get(i);
			Vector3f bb = player.getEntityHitBox().getSize();

			// if (game.getRenderManager()
			// .getFrustumManager()
			// .cubeInFrustum(player.getEntityHitBox().getMinx(),
			// player.getEntityHitBox().getMiny(),
			// player.getEntityHitBox().getMinz(),
			// EngineUtils.toGLVector(bb))) {

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, player.getVboHandel());

			GL11.glVertexPointer(VertexData.positionElementCount,
					GL11.GL_FLOAT, VertexData.stride,
					VertexData.positionByteOffset);
			GL11.glColorPointer(VertexData.colorElementCount, GL11.GL_FLOAT,
					VertexData.stride, VertexData.colorByteOffset);
			GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
					VertexData.normalByteOffset);
			GL11.glTexCoordPointer(VertexData.stElementCount, GL11.GL_FLOAT,
					VertexData.stride, VertexData.stByteOffset);

			// Bind to the index VBO that has all the information about the
			// order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
					player.getVboiHandel());

			// Draw the vertices
			GL11.glDrawElements(GL11.GL_TRIANGLES, player.getIndicesCount(),
					GL11.GL_UNSIGNED_SHORT, 0);

			// Put everything back to default (deselect)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// }
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		/*
		 */// ////////////////////////////RENDER CLOUDS\\\\\\\\\\\\\\\\\\\\

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, game.getEnvironmentManager()
				.getCloudManager().getVboHandel());

		GL11.glVertexPointer(VertexData.positionElementCount, GL11.GL_FLOAT,
				VertexData.stride, VertexData.positionByteOffset);
		GL11.glColorPointer(VertexData.colorElementCount, GL11.GL_FLOAT,
				VertexData.stride, VertexData.colorByteOffset);
		GL11.glNormalPointer(GL11.GL_FLOAT, VertexData.stride,
				VertexData.normalByteOffset);
		GL11.glTexCoordPointer(VertexData.stElementCount, GL11.GL_FLOAT,
				VertexData.stride, VertexData.stByteOffset);

		// Bind to the index VBO that has all the information about the
		// order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, game
				.getEnvironmentManager().getCloudManager().getVboiHandel());

		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, game.getEnvironmentManager()
				.getCloudManager().getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

	}

	public void setTextureUnit0(int programId) {
		// Please note your program must be linked before calling this and I
		// would advise the program be in use also.
		int loc = GL20.glGetUniformLocation(programId, "texture1");
		// First of all, we retrieve the location of the sampler in memory.
		GL20.glUniform1i(loc, 0);
		// Then we pass the 0 value to the sampler meaning it is to use texture
		// unit 0.
	}

	@Override
	public void cleanUp() {
		for (Chunk etn : game.getWorld().getNodes()) {
			glDeleteBuffers(etn.getEcmb().getVboHandelOpaque());
			glDeleteBuffers(etn.getEcmb().getVboHandelNonOpaque());
		}
	}

	public Skybox getSkybox() {
		return skybox;
	}

	public void setSkybox(Skybox skybox) {
		this.skybox = skybox;
	}

}