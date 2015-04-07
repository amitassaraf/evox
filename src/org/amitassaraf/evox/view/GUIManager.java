package org.amitassaraf.evox.view;

import java.util.ArrayList;

import org.amitassaraf.evox.core.BoxAABB2;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

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

public class GUIManager {
	
	/**
	 * Game instance
	 */
	private Game game;
	
	/**
	 * list of on-screen gui elements
	 */
	private ArrayList<GUIElement> guiList;
	
	/**
	 * primary constructor
	 * @param game
	 */
	public GUIManager(Game game) {
		this.game = game;
		guiList = new ArrayList<>();
	}
	
	/**
	 * Add engine GUI components
	 */
	public void addEngineGUI() {
		guiList.add(new GUIElement().setTextureAtlasID(1).setBoundingBox(new BoxAABB2(50f, new Vector2f(Display.getWidth()/2f-25f,Display.getHeight()/2f-25f))));
	}
	
	/**
	 * Build all elements
	 */
	public void build() {
		for (GUIElement elem : guiList) {
			elem.build(game);
		}
	}

	/**
	 * Render all GUI components
	 */
	public void render() {

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

		for (GUIElement elem : guiList) {
			
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.getResourceManager()
					.getTextureAtlases().get(elem.getTextureAtlasID()).getAtlasTextureId());
			
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, elem
						.getVboHandel());
				
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
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, elem
						.getVboiHandel());

				// Draw the vertices
				GL11.glDrawElements(GL11.GL_TRIANGLES, elem
						.getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);

				// Put everything back to default (deselect)
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	

}
