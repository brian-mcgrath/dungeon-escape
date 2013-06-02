package com.game.characters;

/**
 * @author Brian McGrath
 *
 */
public interface Enemy {
	
	/**
	 * @param x
	 * @param y
	 */
	void moveTo(int x, int y);
	
	
	/**
	 * 
	 */
	void die();
}
