package com.game.enities;

import java.util.ArrayList;

import com.game.characters.Enemy;
import com.game.tools.SoundPlayer;

/**
 * @author Brian McGrath
 *
 */
public class Level {
	private Map maze;
	private String musicUrl;
	private ArrayList<Enemy> enemies;
	private ArrayList<Item> items;
	private boolean playing;

	
	/**
	 * @param levelNumber
	 * @param TILE_SIZE
	 * @param soundPlayer
	 */
	public Level(int levelNumber, int TILE_SIZE, SoundPlayer soundPlayer) {
		this.maze = new Map(levelNumber, TILE_SIZE);
		this.musicUrl = "audio\\music\\level_" + levelNumber;
		this.playing = true;
		soundPlayer.startMusic("level_" + levelNumber + ".wav");
	}

	
	/**
	 * @return the maze
	 */
	public Map getMaze() {
		return maze;
	}

	
	/**
	 * @return the musicUrl
	 */
	public String getMusicUrl() {
		return musicUrl;
	}

	
	/**
	 * @return the enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	
	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	
	/**
	 * @return the playing
	 */
	public boolean isPlaying() {
		return playing;
	}
}
