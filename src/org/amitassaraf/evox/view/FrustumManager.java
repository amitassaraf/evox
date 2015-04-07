package org.amitassaraf.evox.view;

import org.amitassaraf.evox.core.Constants;
import org.amitassaraf.evox.core.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

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

public class FrustumManager {

	/**
	 * Frustum variables
	 */
	private float nearPlaneHeight;
	private float nearPlaneWidth;
	private float farPlaneHeight;
	private float farPlaneWidth;
	private float[][] frustum;
	public static float FRUSTUM_PADDING;
	public static float FOV_DIVISOR = 8f;
	private FloatBuffer proj = BufferUtils.createFloatBuffer(16);
	private FloatBuffer modl = BufferUtils.createFloatBuffer(16);
	
	/**
	 * Primary constructor
	 * @param game
	 */
	public FrustumManager(Game game) {
		//CALCULATE NEAR PLANE W-H
		nearPlaneHeight = (float) (2 * Math.tan(Constants.FIELD_OF_VIEW / 2) * Constants.NEAR_DISTANCE);
		nearPlaneWidth = nearPlaneHeight * Display.getWidth()/Display.getHeight();
		//CALCULATE FAR PLANE W-H
		farPlaneHeight = (float) (2 * Math.tan(Constants.FIELD_OF_VIEW / 2) * Constants.FAR_DISTANCE);
		farPlaneWidth = farPlaneHeight * Display.getWidth()/Display.getHeight();
		frustum = new float[6][4];
		FRUSTUM_PADDING = Constants.FIELD_OF_VIEW/FOV_DIVISOR;
	}
	
	/**
	 * Check if point is inside the frustum
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean pointInFrustum( float x, float y, float z )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	      if( frustum[p][0] * x + frustum[p][1] * y + frustum[p][2] * z + frustum[p][3] <= 0 )
	         return false;
	   return true;
	}
	
	/**
	 * Check if sphere is in frustum
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @return
	 */
	public boolean sphereInFrustum( float x, float y, float z, float radius )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	      if( frustum[p][0] * x + frustum[p][1] * y + frustum[p][2] * z + frustum[p][3] <= -radius )
	         return false;
	   return true;
	}
	
	/**
	 * Check if cube is in frustum
	 * @param x
	 * @param y
	 * @param z
	 * @param size
	 * @return
	 */
	public boolean cubeInFrustum( float x, float y, float z, float size )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	   {
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y - size) + frustum[p][2] * (z - size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y - size) + frustum[p][2] * (z - size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y + size) + frustum[p][2] * (z - size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y + size) + frustum[p][2] * (z - size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y - size) + frustum[p][2] * (z + size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y - size) + frustum[p][2] * (z + size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y + size) + frustum[p][2] * (z + size) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y + size) + frustum[p][2] * (z + size) + frustum[p][3] > 0 )
	         continue;
	      return false;
	   }
	   return true;
	}
	
	/**
	 * Check if cube is in frustum
	 * @param x
	 * @param y
	 * @param z
	 * @param size
	 * @return
	 */
	public boolean cubeInFrustum( float x, float y, float z, Vector3f size )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	   {
	      if( frustum[p][0] * (x - size.x) + frustum[p][1] * (y - size.y) + frustum[p][2] * (z - size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size.x) + frustum[p][1] * (y - size.y) + frustum[p][2] * (z - size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size.x) + frustum[p][1] * (y + size.y) + frustum[p][2] * (z - size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size.x) + frustum[p][1] * (y + size.y) + frustum[p][2] * (z - size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size.x) + frustum[p][1] * (y - size.y) + frustum[p][2] * (z + size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size.x) + frustum[p][1] * (y - size.y) + frustum[p][2] * (z + size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size.x) + frustum[p][1] * (y + size.y) + frustum[p][2] * (z + size.z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size.x) + frustum[p][1] * (y + size.y) + frustum[p][2] * (z + size.z) + frustum[p][3] > 0 )
	         continue;
	      return false;
	   }
	   return true;
	}
	
	/**
	 * Method that updates the frustum
	 */
	public void extractFrustum()
	{
		proj.clear();
		modl.clear();
	   float[] clip = new float[16];
	   float   t;

	   /* Get the current PROJECTION matrix from OpenGL */
	   GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, proj );

	   /* Get the current MODELVIEW matrix from OpenGL */
	   GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modl );

	   /* Combine the two matrices (multiply projection by modelview) */
	   clip[ 0] = modl.get(0) * proj.get(0) + modl.get(1)  * proj.get(4)  + modl.get(2)  * proj.get(8)  + modl.get(3)  * proj.get(12) ;
	   clip[ 1] = modl.get(0) * proj.get(1)  + modl.get(1)  * proj.get(5)  + modl.get(2)  * proj.get(9)  + modl.get(3)  * proj.get(13) ;
	   clip[ 2] = modl.get(0) * proj.get(2)  + modl.get(1)  * proj.get(6)  + modl.get(2)  * proj.get(10)  + modl.get(3)  * proj.get(14) ;
	   clip[ 3] = modl.get(0) * proj.get(3)  + modl.get(1)  * proj.get(7)  + modl.get(2)  * proj.get(11)  + modl.get(3)  * proj.get(15) ;

	   clip[ 4] = modl.get(4) * proj.get( 0) + modl.get(5) * proj. get( 4) + modl.get(6) * proj.get( 8) + modl.get(7) * proj.get(12);
	   clip[ 5] = modl.get(4) * proj.get( 1) + modl.get(5) * proj.get( 5) + modl.get(6) * proj.get( 9) + modl.get(7) * proj.get(13);
	   clip[ 6] = modl.get(4) * proj.get( 2) + modl.get(5) * proj.get( 6) + modl.get(6) * proj.get(10) + modl.get(7) * proj.get(14);
	   clip[ 7] = modl.get(4) * proj.get( 3) + modl.get(5) * proj.get( 7) + modl.get(6) * proj.get(11) + modl.get(7) * proj.get(15);

	   clip[ 8] = modl.get(8) * proj.get( 0) + modl.get(9) * proj.get( 4) + modl.get(10) * proj.get( 8) + modl.get(11) * proj.get(12);
	   clip[ 9] = modl.get(8) * proj.get( 1) + modl.get(9) * proj.get( 5) + modl.get(10) * proj.get( 9) + modl.get(11) * proj.get(13);
	   clip[10] = modl.get(8) * proj.get( 2) + modl.get(9) * proj.get( 6) + modl.get(10) * proj.get(10) + modl.get(11) * proj.get(14);
	   clip[11] = modl.get(8) * proj.get( 3) + modl.get(9) * proj.get( 7) + modl.get(10) * proj.get(11) + modl.get(11) * proj.get(15);

	   clip[12] = modl.get(12) * proj.get( 0) + modl.get(13) * proj.get( 4) + modl.get(14) * proj.get( 8) + modl.get(15) * proj.get(12);
	   clip[13] = modl.get(12) * proj.get( 1) + modl.get(13) * proj.get( 5) + modl.get(14) * proj.get( 9) + modl.get(15) * proj.get(13);
	   clip[14] = modl.get(12) * proj.get( 2) + modl.get(13) * proj.get( 6) + modl.get(14) * proj.get(10) + modl.get(15) * proj.get(14);
	   clip[15] = modl.get(12) * proj.get( 3) + modl.get(13) * proj.get( 7) + modl.get(14) * proj.get(11) + modl.get(15) * proj.get(15);

	   /* Extract the numbers for the RIGHT plane */
	   frustum[0][0] = clip[ 3] - clip[ 0];
	   frustum[0][1] = clip[ 7] - clip[ 4];
	   frustum[0][2] = clip[11] - clip[ 8];
	   frustum[0][3] = clip[15] - clip[12];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2] );
	   frustum[0][0] /= t;
	   frustum[0][1] /= t;
	   frustum[0][2] /= t;
	   frustum[0][3] /= t;

	   /* Extract the numbers for the LEFT plane */
	   frustum[1][0] = clip[ 3] + clip[ 0];
	   frustum[1][1] = clip[ 7] + clip[ 4];
	   frustum[1][2] = clip[11] + clip[ 8];
	   frustum[1][3] = clip[15] + clip[12];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2] );
	   frustum[1][0] /= t;
	   frustum[1][1] /= t;
	   frustum[1][2] /= t;
	   frustum[1][3] /= t;

	   /* Extract the BOTTOM plane */
	   frustum[2][0] = clip[ 3] + clip[ 1];
	   frustum[2][1] = clip[ 7] + clip[ 5];
	   frustum[2][2] = clip[11] + clip[ 9];
	   frustum[2][3] = clip[15] + clip[13];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2] );
	   frustum[2][0] /= t;
	   frustum[2][1] /= t;
	   frustum[2][2] /= t;
	   frustum[2][3] /= t;

	   /* Extract the TOP plane */
	   frustum[3][0] = clip[ 3] - clip[ 1];
	   frustum[3][1] = clip[ 7] - clip[ 5];
	   frustum[3][2] = clip[11] - clip[ 9];
	   frustum[3][3] = clip[15] - clip[13];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2] );
	   frustum[3][0] /= t;
	   frustum[3][1] /= t;
	   frustum[3][2] /= t;
	   frustum[3][3] /= t;

	   /* Extract the FAR plane */
	   frustum[4][0] = clip[ 3] - clip[ 2];
	   frustum[4][1] = clip[ 7] - clip[ 6];
	   frustum[4][2] = clip[11] - clip[10];
	   frustum[4][3] = clip[15] - clip[14];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2] );
	   frustum[4][0] /= t;
	   frustum[4][1] /= t;
	   frustum[4][2] /= t;
	   frustum[4][3] /= t;

	   /* Extract the NEAR plane */
	   frustum[5][0] = clip[ 3] + clip[ 2];
	   frustum[5][1] = clip[ 7] + clip[ 6];
	   frustum[5][2] = clip[11] + clip[10];
	   frustum[5][3] = clip[15] + clip[14];

	   /* Normalize the result */
	   t = (float) Math.sqrt( frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2] );
	   frustum[5][0] /= t;
	   frustum[5][1] /= t;
	   frustum[5][2] /= t;
	   frustum[5][3] /= t;
	}
	
	/**
	 * GETTERS AND SETTERS
	 */

	public float getNearPlaneHeight() {
		return nearPlaneHeight;
	}

	public float getNearPlaneWidth() {
		return nearPlaneWidth;
	}

	public float getFarPlaneHeight() {
		return farPlaneHeight;
	}

	public float getFarPlaneWidth() {
		return farPlaneWidth;
	}


}