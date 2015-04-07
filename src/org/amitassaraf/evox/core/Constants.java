package org.amitassaraf.evox.core;

import org.lwjgl.util.vector.Vector3f;

public class Constants {
	
	/**
	 * Gameplay constants
	 */
	public static final long PLACE_REMOVE_DELAY = 200;
	public static final float TICK_TIME_MS = 20f; //DEF = 20
	public static final float MAX_REACH = 100f;
	public static final long CLOUD_MOVEMENT_INTERVAL = 300;
	public static final long CLOUD_HEIGHT_IN_SKY = 600;
	
	/**
	 * World constants
	 */
	public static final int NODE_AMOUNT = 16; // DEF= 529
	public static final int NODE_WIDTH = 16;
	public static final int NODE_LENGTH = 16;
	public static final int NODE_HEIGHT = 128;
	/**
	 * Display constants
	 */
	public static final float FIELD_OF_VIEW = 90F;
	public static final float FAR_DISTANCE = 2000F;
	public static final int DISPLAY_WIDTH = 800;
	public static final int DISPLAY_HEIGHT = 600;
	public static final float NEAR_DISTANCE = 2F;
	public static final int TARGET_FPS = 1000;
	public static final boolean vSyncEnabled = false;
	
	/**
	 * Game title and version in String
	 */
	public static final String TITLE = "Tech demo 'Pure Frostbite'";
	public static final String GAME_VERSION_S = "IN-DEV PRE ALPHA";

	/**
	 * Game version serial in LONG
	 */
	public static final long GAME_VERSION_L = 1;
	
	/**
	 * Environment constants
	 */
	public static final float WORLD_GRAVITY = 80f;
	public static final float NIGHT = 13500;
	public static final float DAY = 4500;
	
	/**
	 * Voxel and chunk constants
	 */
	public static final float VOXEL_SIZE = 10f;
	public static final Vector3f ambientLight = new Vector3f(0.075f,0.075f,0.075f);
	
	public static final byte TOP = 1 << 0; //1
	public static final byte BOTTOM = 1 << 1; //2
	public static final byte FRONT = 1 << 2; //4
	public static final byte BACK = 1 << 3; //8
	public static final byte RIGHT = 1 << 4; //16
	public static final byte LEFT = 1 << 5; //32
	
	public static final byte TOP_TRUE = 1; //1
	public static final byte BOTTOM_TRUE = 2; //2
	public static final byte FRONT_TRUE = 4; //4
	public static final byte BACK_TRUE = 8; //8
	public static final byte RIGHT_TRUE = 16; //16
	public static final byte LEFT_TRUE = 32; //32
	
	public static final int SIDE_TOP = 0;
	public static final int SIDE_BOTTOM = 1;
	public static final int SIDE_FRONT = 2;
	public static final int SIDE_BACK = 3;
	public static final int SIDE_RIGHT = 4;
	public static final int SIDE_LEFT = 5;
	
}
