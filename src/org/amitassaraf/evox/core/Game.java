
package org.amitassaraf.evox.core;

import org.amitassaraf.evox.core.environment.EnvironmentManager;
import org.amitassaraf.evox.input.EvoxInputManager;
import org.amitassaraf.evox.net.kryo.GameClientKryo;
import org.amitassaraf.evox.net.kryo.GameServerKryo;
import org.amitassaraf.evox.net.kryo.packets.PlayerDisconnectPacket09;
import org.amitassaraf.evox.resources.ResourceManager;
import org.amitassaraf.evox.timing.TimeManager;
import org.amitassaraf.evox.view.GameDisplay;
import org.amitassaraf.evox.view.RenderManager;
import org.amitassaraf.evox.view.renderers.GameRenderer;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

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

public abstract class Game {

	/**
	 * Current game state
	 */
	private GameState gameState = GameState.GAME;

	/**
	 * List on players in-game
	 */
	private final ArrayList<EntityPlayer> players;

	/**
	 * Engine objects
	 */
	private final GameDisplay gameDisplay;
	private final TimeManager timeManager;
	private final RenderManager renderManager;
	private final EvoxInputManager inputManager;
	private final ResourceManager resourceManager;
	private final EnvironmentManager environmentManager;
	private final GameClientKryo networkManager;
	private VoxelWorld world;

	/**
	 * Main constructor
	 */
	public Game() {
		System.out.println("Starting server...");
		GameServerKryo server = new GameServerKryo(this);
		server.startServer();

		world = new VoxelWorld(this);

		players = new ArrayList<EntityPlayer>();
		players.add(new EntityPlayer(new Vector3f(600, 300, 600)));

		System.out.println("Connecting to server...");
		networkManager = new GameClientKryo(this);
		networkManager.connect("127.0.0.1");

		System.out.println("Loading world..");
		while (!networkManager.isWorldLoaded()) {
			System.out.println("Still loading...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Starting game..");

		timeManager = new TimeManager(this);
		inputManager = new EvoxInputManager(this);
		gameDisplay = new GameDisplay(this);
		environmentManager = new EnvironmentManager(this);

		// System.out.println("Loading world..");
		// world.generate();

		renderManager = new RenderManager(this);

		System.out.println("Loading resources...");
		resourceManager = new ResourceManager(this);
		resourceManager.loadGLSLShaders();
		resourceManager.loadTextureAtlases();
		loadResources();

		System.out.println("Registering inputs and renderers");
		registerCustomRenderers();
		registerInputs();

		System.out.println("Building lists...");
		renderManager.getRenderers()
				.put(GameState.GAME, new GameRenderer(this));
		renderManager.initRenderers();
		renderManager.build();
		timeManager.start();
		
		for (Chunk node : world.getNodes()) {
			if (players.get(0).getEntityHitBox()
					.intersectsAABBNoY(node.getBoundingbox())) {
				players.get(0).getCollisionChunks().add(node);
			}
		}
		
		world.getStaticObjects().get(0).getMeshBuilder().build(this);

		System.out.println("Done.");
		gameDisplay.gameLoop();

		if (networkManager.isConnected()) {
			PlayerDisconnectPacket09 pack = new PlayerDisconnectPacket09();
			pack.setUsername(networkManager.getNetworkIdentifier());
			networkManager.sendPacket(pack);
		}
	}

	/**
	 * Abstract Methods
	 */
	public abstract void update();

	public abstract void loadResources();

	public abstract void registerInputs();

	public abstract void registerCustomRenderers();

	public abstract void cleanUp();

	/**
	 * Update engine
	 */
	public void updateEngine() {
		inputManager.process();
		environmentManager.update();
		
		while (!world.getBuildQueue().isEmpty()) {
			Chunk c = world.getBuildQueue().remove(0);
			c.getEcmb().build(this);
		}

		for (Entity e : players) {
			e.update(this, TimeManager.getDelta());
		}

		//for (int i = 1; i < players.size(); i++) {
		//	System.out.println(players.get(i).getUsername() + " is at "
		//			+ players.get(i).getLocation().toString());
		//}
	}

	/**
	 * Getters and Setters
	 */

	public GameDisplay getGameDisplay() {
		return gameDisplay;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public EvoxInputManager getInputManager() {
		return inputManager;
	}

	public ArrayList<EntityPlayer> getPlayers() {
		return players;
	}

	public TimeManager getTimeManager() {
		return timeManager;
	}

	public RenderManager getRenderManager() {
		return renderManager;
	}

	public VoxelWorld getWorld() {
		return world;
	}

	public void setWorld(VoxelWorld world) {
		this.world = world;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public EnvironmentManager getEnvironmentManager() {
		return environmentManager;
	}

	public GameClientKryo getNetworkManager() {
		return networkManager;
	}

}