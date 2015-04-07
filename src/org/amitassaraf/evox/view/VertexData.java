
package org.amitassaraf.evox.view;

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

public class VertexData {
	// Vertex data
	private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
	private float[] rgba = new float[] {1f, 1f, 1f, 1f};
	private float[] normal = new float[] {0f, 0f,0f};
	private float[] st = new float[] {0f,0f};
	
	// The amount of bytes an element has
	public static final int elementBytes = 4;
	
	// Elements per parameter
	public static final int positionElementCount = 4;
	public static final int colorElementCount = 4;
	public static final int normalElementCount = 3;
	public static final int stElementCount = 2;
	
	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int colorByteCount = colorElementCount * elementBytes;
	public static final int normalByteCount = normalElementCount * elementBytes;
	public static final int stByteCount = stElementCount * elementBytes;
	
	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int colorByteOffset = positionByteOffset + positionBytesCount;
	public static final int normalByteOffset = colorByteOffset + colorByteCount;
	public static final int stByteOffset = normalByteOffset + normalByteCount;
	
	// The amount of elements that a vertex has
	public static final int elementCount = positionElementCount + 
			colorElementCount + normalElementCount + stByteOffset;	
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	public static final int stride = positionBytesCount + colorByteCount + 
			normalByteCount + stByteCount;
	
	// Setters
	public void setXYZ(float x, float y, float z) {
		this.setXYZW(x, y, z, 1f);
	}
	
	public void setRGB(float r, float g, float b) {
		this.setRGBA(r, g, b, 1f);
	}
	
	public void setNORMAL(float s, float t,float x) {
		this.normal = new float[] {s, t,x};
	}
	
	public void setST(float s, float t) {
		this.st = new float[] {s, t};
	}
	
	public void setXYZW(float x, float y, float z, float w) {
		this.xyzw = new float[] {x, y, z, w};
	}
	
	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] {r, g, b, 1f};
	}
	
	// Getters	
	public float[] getElements() {
		float[] out = new float[VertexData.elementCount];
		int i = 0;
		
		// Insert XYZW elements
		out[i++] = this.xyzw[0];
		out[i++] = this.xyzw[1];
		out[i++] = this.xyzw[2];
		out[i++] = this.xyzw[3];
		// Insert RGBA elements
		out[i++] = this.rgba[0];
		out[i++] = this.rgba[1];
		out[i++] = this.rgba[2];
		out[i++] = this.rgba[3];
		// Insert normal elements
		out[i++] = this.normal[0];
		out[i++] = this.normal[1];
		out[i++] = this.normal[2];
		//Insert texture elements
		out[i++] = this.st[0];
		out[i++] = this.st[1];
		
		return out;
	}
	
	public float[] getXYZW() {
		return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
	}
	
	public float[] getXYZ() {
		return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2]};
	}
	
	public float[] getRGBA() {
		return new float[] {this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
	}
	
	public float[] getRGB() {
		return new float[] {this.rgba[0], this.rgba[1], this.rgba[2]};
	}
	
	public float[] getNORMAL() {
		return new float[] {this.normal[0], this.normal[1], this.normal[2]};
	}
	
	public float[] getST() {
		return new float[] {this.st[0], this.st[1]};
	}
}