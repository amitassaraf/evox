
package org.amitassaraf.evox.core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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

public class EngineUtils {

	/**
	 * Turn this array indexes into world coordinates
	 * 
	 * @param node
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static javax.vecmath.Vector3f toWorldCoordinates(Chunk node, int x,
			int y, int z) {
		return new javax.vecmath.Vector3f(node.getBoundingbox().getLocation().x
				+ (x * Constants.VOXEL_SIZE), node.getBoundingbox()
				.getLocation().y + (y * Constants.VOXEL_SIZE), node
				.getBoundingbox().getLocation().z + (z * Constants.VOXEL_SIZE));
	}

	/**
	 * Turn this array indexes into world coordinates
	 * 
	 * @param node
	 * @param vec
	 * @return
	 */
	public static javax.vecmath.Vector3f toWorldCoordinates(Chunk node,
			javax.vecmath.Vector3f vec) {
		return new javax.vecmath.Vector3f(node.getBoundingbox().getLocation().x
				+ (vec.x * Constants.VOXEL_SIZE), node.getBoundingbox()
				.getLocation().y + (vec.y * Constants.VOXEL_SIZE), node
				.getBoundingbox().getLocation().z
				+ (vec.z * Constants.VOXEL_SIZE));
	}

	/**
	 * Turn array indexes to world coordinates
	 * 
	 * @param node
	 * @param vec
	 * @return
	 */
	public static javax.vecmath.Vector3f toWorldCoordinates(Chunk node,
			Vector3f vec) {
		return new javax.vecmath.Vector3f(node.getBoundingbox().getLocation().x
				+ (vec.x * Constants.VOXEL_SIZE), node.getBoundingbox()
				.getLocation().y + (vec.y * Constants.VOXEL_SIZE), node
				.getBoundingbox().getLocation().z
				+ (vec.z * Constants.VOXEL_SIZE));
	}

	/**
	 * Distance between 2 vectors
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static float distance(javax.vecmath.Vector3f a,
			javax.vecmath.Vector3f b) {
		return (float) Math.sqrt((Math.pow((a.x - b.x), 2)
				+ Math.pow((a.y - b.y), 2) + Math.pow((a.z - b.z), 2)));
	}

	/**
	 * Change vector to a LWJGL Vector
	 * 
	 * @param vector
	 * @return
	 */
	public static Vector3f toGLVector(javax.vecmath.Vector3f vector) {
		return new Vector3f(vector.x, vector.y, vector.z);
	}

	/**
	 * Change vector to a vecmath vector
	 * 
	 * @param vector
	 * @return
	 */
	public static javax.vecmath.Vector3f toMathVector(Vector3f vector) {
		return new javax.vecmath.Vector3f(vector.x, vector.y, vector.z);
	}

	/**
	 * Turn vector to negitive
	 * 
	 * @param vector
	 * @return
	 */
	public static Vector3f toNegitive(Vector3f vector) {
		vector.set(vector.x * -1, vector.y * -1, vector.z * -1);
		return vector;
	}

	/**
	 * Turn vector to negitive
	 * 
	 * @param vector
	 * @return
	 */
	public static Vector3f toNegitive(javax.vecmath.Vector3f vector) {
		return new Vector3f(vector.x * -1, vector.y * -1, vector.z * -1);
	}

	/**
	 * @param v
	 *            the vector that is to be turned into an array of floats
	 *
	 * @return a float array where [0] is v.x, [1] is v.y, and [2] is v.z
	 */
	public static float[] asFloats(Vector3f v) {
		return new float[] { v.x, v.y, v.z };
	}

	/**
	 * @param elements
	 *            the amount of elements to check
	 *
	 * @return true if the contents of the two buffers are the same, false if
	 *         not
	 */
	public static boolean bufferEquals(FloatBuffer bufferOne,
			FloatBuffer bufferTwo, int elements) {
		for (int i = 0; i < elements; i++) {
			if (bufferOne.get(i) != bufferTwo.get(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param values
	 *            the byte values that are to be turned into a readable
	 *            ByteBuffer
	 *
	 * @return a readable ByteBuffer
	 */
	public static ByteBuffer asByteBuffer(byte... values) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(values.length);
		buffer.put(values);
		return buffer;
	}

	/**
	 * @param buffer
	 *            a readable buffer
	 * @param elements
	 *            the amount of elements in the buffer
	 *
	 * @return a string representation of the elements in the buffer
	 */
	public static String bufferToString(FloatBuffer buffer, int elements) {
		StringBuilder bufferString = new StringBuilder();
		for (int i = 0; i < elements; i++) {
			bufferString.append(" ").append(buffer.get(i));
		}
		return bufferString.toString();
	}

	/**
	 * @param matrix4f
	 *            the Matrix4f that is to be turned into a readable FloatBuffer
	 *
	 * @return a FloatBuffer representation of matrix4f
	 */
	public static FloatBuffer asFloatBuffer(Matrix4f matrix4f) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix4f.store(buffer);
		return buffer;
	}

	/**
	 * @param matrix4f
	 *            the Matrix4f that is to be turned into a FloatBuffer that is
	 *            readable to OpenGL (but not to you)
	 *
	 * @return a FloatBuffer representation of matrix4f
	 */
	public static FloatBuffer asFlippedFloatBuffer(Matrix4f matrix4f) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix4f.store(buffer);
		buffer.flip();
		return buffer;
	}

	/**
	 * @param values
	 *            the float values that are to be turned into a readable
	 *            FloatBuffer
	 *
	 * @return a readable FloatBuffer containing values
	 */
	public static FloatBuffer asFloatBuffer(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		return buffer;
	}

	/**
	 * @param amountOfElements
	 *            the amount of elements in the FloatBuffers
	 *
	 * @return an empty FloatBuffer with a set amount of elements
	 */
	public static FloatBuffer reserveData(int amountOfElements) {
		return BufferUtils.createFloatBuffer(amountOfElements);
	}

	/**
	 * @param values
	 *            the float values that are to be turned into a FloatBuffer
	 *
	 * @return a FloatBuffer readable to OpenGL (not to you!) containing values
	 */
	public static FloatBuffer asFlippedFloatBuffer(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

}