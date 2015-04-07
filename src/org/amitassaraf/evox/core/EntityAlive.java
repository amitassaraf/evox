package org.amitassaraf.evox.core;

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

public class EntityAlive extends Entity {

	/**
	 * Entity health Entity stamina Entity mana Entity hunger Entity walking
	 * speed
	 */
	private int health = 100;
	private int stamina = 100;
	private int mana = 100;
	private int hunger = 20;
	private float jumpSpeed = 50.0f;
	protected float walkingSpeed = 35;

	/**
	 * Primary constructor
	 * 
	 * @param location
	 * @param size
	 */
	public EntityAlive(Vector3f location, float size) {
		super(location, size);
	}

	/**
	 * Secondary constructor
	 * 
	 * @param location
	 * @param size
	 */
	public EntityAlive(Vector3f location, Vector3f size) {
		super(location, size);
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public float getWalkingSpeed() {
		return walkingSpeed;
	}

	public void setWalkingSpeed(float walkingSpeed) {
		this.walkingSpeed = walkingSpeed;
	}
	int c = 0;
	public void jump(Game game) {
		hitBox.add(0, SKIN, 0);
		if (speed.y == 0 && 
				game.getWorld().checkEntityCollision(this, true) != null) {
			System.out.println("speed y" + speed.y);
			speed.y = jumpSpeed;
			c+=1;
			System.out.println(c);
		}
		hitBox.sub(0, SKIN, 0);		
	}

}
