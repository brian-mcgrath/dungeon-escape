package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import javax.vecmath.Vector2d;

import com.game.characters.Player;
import com.game.enities.Level;
import com.game.screens.MapScreen;
import com.game.screens.PlayerScreen;
import com.game.tools.Angle;
import com.game.tools.SoundPlayer;

/**
 * @author Brian McGrath
 */
public class Game extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6995184366504082029L;
	private int TILE_SIZE = 64;
	private final int width = 640;
	private final int height = 480;
	private final int repaintTime = 25;
	private final String title = "Maze";
	private final String version = "Alpha 2.5";
	private Timer time;
	private Level level;
	private Player player = new Player(TILE_SIZE + TILE_SIZE / 2, TILE_SIZE
			+ TILE_SIZE / 2, new Angle(0));
	private SoundPlayer soundPlayer;
	private MapScreen mapScreen;
	private PlayerScreen playerScreen;
	private int currentScreen;
	private static final int shrink = 8;

	
	/**
	 * 
	 */
	public Game() {
		this.setIgnoreRepaint(true);
		this.time = new Timer(repaintTime, this);
		this.time.start();
		this.setTitle(title + " - " + version);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(new KeyInputHandler());
		this.soundPlayer = new SoundPlayer();
		this.level = new Level(1, TILE_SIZE, soundPlayer);
		this.mapScreen = new MapScreen(this);
		this.playerScreen = new PlayerScreen(this);
		this.currentScreen = 1;
		player.setVelocity(TILE_SIZE / 8);
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.initWindow();
	}

	
	
	/**
	 * 
	 */
	public void initWindow() {
		resizeScreen(16, 38);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		this.getContentPane().add(playerScreen);
		// this.getContentPane().add(playerScreen);
	}

	
	/**
	 * @return the mapScreen
	 */
	public MapScreen getMapScreen() {
		return mapScreen;
	}

	
	/**
	 * @param mapScreen
	 *            the mapScreen to set
	 */
	public void setMapScreen(MapScreen mapScreen) {
		this.mapScreen = mapScreen;
	}

	
	/**
	 * @return the playerScreen
	 */
	public PlayerScreen getPlayerScreen() {
		return playerScreen;
	}

	
	/**
	 * @param playerScreen
	 *            the playerScreen to set
	 */
	public void setPlayerScreen(PlayerScreen playerScreen) {
		this.playerScreen = playerScreen;
	}

	
	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	
	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	
	/**
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	
	/**
	 * @param w
	 * @param h
	 */
	public void resizeScreen(int w, int h) {
		setSize(width + w, height + h);
	}

	
	private class KeyInputHandler extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setTurningLeft(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setTurningRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (!player.isMovingBack() || !player.isMovingBack()) {
					soundPlayer.startFootsteps();
				}
				player.setMovingBack(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (!player.isMovingBack() || !player.isMovingBack()) {
					soundPlayer.startFootsteps();
				}
				player.setMovingForward(true);
			}
		}

		
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setTurningLeft(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

				player.setTurningRight(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				soundPlayer.stopFootsteps();
				player.setMovingBack(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				soundPlayer.stopFootsteps();
				player.setMovingForward(false);
			}
		}

		
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			if (e.getKeyChar() == KeyEvent.VK_SPACE) {
				if (player.getWeapon().getAmmoInCurrentClip() > 0) {
					if (!player.getWeapon().isFiring()) {
						player.getWeapon().fire();
						soundPlayer.playGunshot(player.getWeapon().getId());
					}
				}
				else {
					if (!player.getWeapon().isReloading()) {
						player.getWeapon().reload();
						soundPlayer.playReload();
					}
				}
			}
			if (e.getKeyChar() == KeyEvent.VK_M) {
				changeScreen();
			}
		}
	}

	
	public void changeScreen() {
		if (currentScreen == 1) {
			this.getContentPane().remove(playerScreen);
			this.getLevel().getMaze().setTileSize(64 / shrink);
			player.setVelocity(player.getVelocity() / shrink);
			player.setPlayerVector(new Vector2d(player.getPosX() / shrink,
					player.getPosY() / shrink));
			this.getContentPane().add(mapScreen);
			revalidate();
			currentScreen = 2;
		} else {
			this.getContentPane().remove(mapScreen);
			this.getLevel().getMaze().setTileSize(64);
			player.setVelocity(player.getVelocity() * shrink);
			player.setPlayerVector(new Vector2d(player.getPosX() * shrink,
					player.getPosY() * shrink));
			this.getContentPane().add(playerScreen);
			revalidate();
			currentScreen = 1;
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (player.isMovingForward()) {
			player.move(player.getVelocity(), level.getMaze());
		}
		if (player.isMovingBack()) {
			player.move(-1 * player.getVelocity(), level.getMaze());
		}
		if (player.isTurningLeft()) {
			player.turn(-2);
		}
		if (player.isTurningRight()) {
			player.turn(2);
		}
		repaint();
	}
}
