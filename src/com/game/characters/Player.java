package com.game.characters;

import javax.vecmath.Vector2d;

import com.game.enities.Map;
import com.game.enities.Raycaster;
import com.game.enities.Weapon;
import com.game.tools.Angle;

/**
 * @author Brian McGrath
 *
 */
public class Player {
	private Angle angle;
	private Vector2d playerVector;
	private boolean turningLeft = false;
	private boolean turningRight = false;
	private boolean movingForward = false;
	private boolean movingBack = false;
	private Weapon weapon;
	private double velocity;
	private int health;
	private Raycaster raycaster;

	
	/**
	 * @param posX
	 * @param posY
	 * @param angle
	 */
	public Player(double posX, double posY, Angle angle) {
		this.playerVector = new Vector2d(posX, posY);
		this.angle = angle;
		this.weapon = new Weapon(1);
		this.velocity = 4;
		this.health = 100;
		setRaycaster(new Raycaster(this));
	}

	
	/**
	 * @param rotationSpeed
	 */
	public void turn(double rotationSpeed) {
		angle.incrementValueBy(Math.toRadians(rotationSpeed));
	}

	
	/**
	 * @param acceleration
	 * @param maze
	 */
	public void move(double acceleration, Map maze) {
		double dx = playerVector.x
				+ (Math.cos(angle.getValue()) * acceleration);
		double dy = playerVector.y
				+ (Math.sin(angle.getValue()) * acceleration);
		Vector2d vX = new Vector2d(dx, playerVector.y);
		Vector2d vY = new Vector2d(playerVector.x, dy);
		if (!(collisionOccurs(vX, maze))) {
			playerVector.x = dx;
		}
		if (!(collisionOccurs(vY, maze))) {
			playerVector.y = dy;
		}
	}

	
	/**
	 * @param v
	 * @param maze
	 * @return
	 */
	public boolean collisionOccurs(Vector2d v, Map maze) {
		boolean inWall = false;
		int xPos = maze.getTileX(v, angle.getValue());
		int yPos = maze.getTileY(v, angle.getValue());
		if (maze.getMapArray()[yPos][xPos] != 0) {
			inWall = true;
		}
		return inWall;
	}
	
	
	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}

	
	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	
	/**
	 * @param weapon
	 *            the weapon to set
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	
	/**
	 * @return
	 */
	public Vector2d getPlayerVector() {
		return playerVector;
	}

	
	/**
	 * @param v
	 */
	public void setPlayerVector(Vector2d v) {
		playerVector = v;
	}

	
	/**
	 * @return
	 */
	public boolean isTurningLeft() {
		return turningLeft;
	}

	
	/**
	 * @param turningLeft
	 */
	public void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}

	
	/**
	 * @return
	 */
	public boolean isTurningRight() {
		return turningRight;
	}

	
	/**
	 * @param turningRight
	 */
	public void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	
	/**
	 * @return
	 */
	public boolean isMovingForward() {
		return movingForward;
	}

	
	/**
	 * @param movingForward
	 */
	public void setMovingForward(boolean movingForward) {
		this.movingForward = movingForward;
	}

	
	/**
	 * @return
	 */
	public boolean isMovingBack() {
		return movingBack;
	}

	
	/**
	 * @param movingBack
	 */
	public void setMovingBack(boolean movingBack) {
		this.movingBack = movingBack;
	}

	
	/**
	 * @return
	 */
	public int getPosX() {
		return (int) (playerVector.x + 0.5);
	}

	
	/**
	 * @return
	 */
	public int getPosY() {
		return (int) (playerVector.y + 0.5);
	}

	
	/**
	 * @return
	 */
	public Angle getAngle() {
		return angle;
	}


	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	
	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	
	/**
	 * @return the raycaster
	 */
	public Raycaster getRaycaster() {
		return raycaster;
	}

	
	/**
	 * @param raycaster
	 *            the raycaster to set
	 */
	public void setRaycaster(Raycaster raycaster) {
		this.raycaster = raycaster;
	}
}
