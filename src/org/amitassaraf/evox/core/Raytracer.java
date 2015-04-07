
package org.amitassaraf.evox.core;

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

public class Raytracer {

	private VoxelCallback voxelCallBack;

	/**
	 * Call the callback with (x,y,z,value,face) of all blocks along the line
	 * segment from point 'origin' in vector direction 'direction' of length
	 * 'radius'. 'radius' may be infinite.
	 * 
	 * 'face' is the normal vector of the face of that block that was entered.
	 * It should not be used after the callback returns.
	 * 
	 * If the callback returns a true value, the traversal will be stopped.
	 * 
	 * @throws Exception
	 */
	public void raycast(float[] origin, float[] direction, float radius,
			VoxelWorld world) throws Exception {
		// From "A Fast Voxel Traversal Algorithm for Ray Tracing"
		// by John Amanatides and Andrew Woo, 1987
		// <http://www.cse.yorku.ca/~amana/research/grid.pdf>
		// <http://citeseer.ist.psu.edu/viewdoc/summary?doi=10.1.1.42.3443>
		// Extensions to the described algorithm:
		// � Imposed a distance limit.
		// � The face passed through to reach the current cube is provided to
		// the callback.

		// The foundation of this algorithm is a parameterized representation of
		// the provided ray,
		// origin + t * direction,
		// except that t is not actually stored; rather, at any given point in
		// the
		// traversal, we keep track of the *greater* t values which we would
		// have
		// if we took a step sufficient to cross a cube boundary along that axis
		// (i.e. change the integer part of the coordinate) in the variables
		// tMaxX, tMaxY, and tMaxZ.

		double wx = Constants.NODE_WIDTH * Constants.NODE_AMOUNT
				* Constants.VOXEL_SIZE;
		double wz = Constants.NODE_LENGTH * Constants.NODE_AMOUNT
				* Constants.VOXEL_SIZE;
		double wy = Constants.NODE_HEIGHT * Constants.VOXEL_SIZE;

		// Cube containing origin point.
		double x = Math.floor(origin[0]);
		double y = Math.floor(origin[1]);
		double z = Math.floor(origin[2]);
		// Break out direction vector.
		double dx = direction[0];
		double dy = direction[1];
		double dz = direction[2];
		// Direction to increment x,y,z when stepping.
		double stepX = signum(dx);
		double stepY = signum(dy);
		double stepZ = signum(dz);
		// See description above. The initial values depend on the fractional
		// part of the origin.
		double tMaxX = intbound(origin[0], dx);
		double tMaxY = intbound(origin[1], dy);
		double tMaxZ = intbound(origin[2], dz);
		// The change in t when taking a step (always positive).
		double tDeltaX = stepX / dx;
		double tDeltaY = stepY / dy;
		double tDeltaZ = stepZ / dz;
		// Buffer for reporting faces to the callback.
		int[] face = new int[3];

		// Avoids an infinite loop.
		if (dx == 0 && dy == 0 && dz == 0)
			throw new Exception("Raycast in zero direction!");

		// Rescale from units of 1 cube-edge to units of 'direction' so we can
		// compare with 't'.
		radius /= Math.sqrt(dx * dx + dy * dy + dz * dz);

		while (/* ray has not gone past bounds of world */
		(stepX > 0 ? x < wx : x >= 0) && (stepY > 0 ? y < wy : y >= 0)
				&& (stepZ > 0 ? z < wz : z >= 0)) {

			// Invoke the callback, unless we are not *yet* within the bounds of
			// the
			// world.
			if (!(x < 0 || y < 0 || z < 0 || x >= wx || y >= wy || z >= wz))
				if (callback(x, y, z, world, face))
					break;

			// tMaxX stores the t-value at which we cross a cube boundary along
			// the
			// X axis, and similarly for Y and Z. Therefore, choosing the least
			// tMax
			// chooses the closest cube boundary. Only the first case of the
			// four
			// has been commented in detail.
			if (tMaxX < tMaxY) {
				if (tMaxX < tMaxZ) {
					if (tMaxX > radius)
						break;
					// Update which cube we are now in.
					x += stepX;
					// Adjust tMaxX to the next X-oriented boundary crossing.
					tMaxX += tDeltaX;
					// Record the normal vector of the cube face we entered.
					face[0] = (int) -stepX;
					face[1] = 0;
					face[2] = 0;
				} else {
					if (tMaxZ > radius)
						break;
					z += stepZ;
					tMaxZ += tDeltaZ;
					face[0] = 0;
					face[1] = 0;
					face[2] = (int) -stepZ;
				}
			} else {
				if (tMaxY < tMaxZ) {
					if (tMaxY > radius)
						break;
					y += stepY;
					tMaxY += tDeltaY;
					face[0] = 0;
					face[1] = (int) -stepY;
					face[2] = 0;
				} else {
					// Identical to the second case, repeated for simplicity in
					// the conditionals.
					if (tMaxZ > radius)
						break;
					z += stepZ;
					tMaxZ += tDeltaZ;
					face[0] = 0;
					face[1] = 0;
					face[2] = (int) -stepZ;
				}
			}
		}
	}

	private boolean callback(double x, double y, double z, VoxelWorld world,
			int[] face) {
		VoxelCallback vc = world.getVoxel(new Vector3f((float) x, (float) y,
				(float) z));
		if (vc != null && !vc.isOutOfBounds() && vc.getVoxelId() != 0) {
			System.out.println(vc);
			vc.setFaceArr(face);
			this.voxelCallBack = vc;
			return true;
		} else
			return false;
	}

	public double intbound(double s, double ds) {
		// Find the smallest positive t such that s+t*ds is an integer.
		if (ds < 0) {
			return intbound(-s, -ds);
		} else {
			s = mod(s, 1);
			// problem is now s+t*ds = 1
			return (1 - s) / ds;
		}
	}

	public double signum(double x) {
		return x > 0 ? 1 : x < 0 ? -1 : 0;
	}

	public double mod(double value, double modulus) {
		return (value % modulus + modulus) % modulus;
	}

	public VoxelCallback getVoxelCallBack() {
		return voxelCallBack;
	}

	public void setVoxelCallBack(VoxelCallback voxelCallBack) {
		this.voxelCallBack = voxelCallBack;
	}
}