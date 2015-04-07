
package org.amitassaraf.evox.resources;

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

public class ShaderManager {
	
	/**
	 * Shader locations
	 */
	private int shaderProgramID;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int rotationMatrixLocation;
	private int globalLightLocation;
	
	/**
	 * Primary constructor
	 */
	public ShaderManager() {	
	}


	/**
	 * GETTERS AND SETTERS 
	 */
	
	public int getShaderProgramID() {
		return shaderProgramID;
	}

	public void setShaderProgramID(int shaderProgramID) {
		this.shaderProgramID = shaderProgramID;
	}

	public int getProjectionMatrixLocation() {
		return projectionMatrixLocation;
	}

	public void setProjectionMatrixLocation(int projectionMatrixLocation) {
		this.projectionMatrixLocation = projectionMatrixLocation;
	}

	public int getViewMatrixLocation() {
		return viewMatrixLocation;
	}

	public void setViewMatrixLocation(int viewMatrixLocation) {
		this.viewMatrixLocation = viewMatrixLocation;
	}

	public int getRotationMatrixLocation() {
		return rotationMatrixLocation;
	}

	public void setRotationMatrixLocation(int viewMatrixLocation) {
		this.rotationMatrixLocation = viewMatrixLocation;
	}

	public int getGlobalLightLocation() {
		return globalLightLocation;
	}

	public void setGlobalLightLocation(int globalLightLocation) {
		this.globalLightLocation = globalLightLocation;
	}

}