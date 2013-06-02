package com.game.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.game.Game;
import com.game.characters.Player;
import com.game.enities.Map;

public class MapScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Player player;
	private Map maze;

	public MapScreen(Game game) {
		this.maze = game.getLevel().getMaze();
		this.player = game.getPlayer();
		this.setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMaze(g);
		drawPlayer(g);
	}

	public void drawMaze(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		
		int tileSize = maze.getTileSize();
		int innerTileSize = tileSize / 4;
		int tileOffset = tileSize / 8 * 3;
		
		for (int i = 0; i < maze.getMapArray()[0].length; i++) {
			for (int j = 0; j < maze.getMapArray().length; j++) {
				if (maze.getMapArray()[j][i] == 0) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect((i * tileSize), (j * tileSize), tileSize, tileSize);
					g2d.setColor(Color.RED);
					g2d.fillRect((i * tileSize) + tileOffset, (j * tileSize) + tileOffset, innerTileSize, innerTileSize);
				}
				if (maze.getMapArray()[j][i] == 1) {
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.fillRect((i * tileSize), (j * tileSize), tileSize, tileSize);
				}
				if (maze.getMapArray()[j][i] == 2) {
					g2d.setColor(Color.WHITE);
					g2d.fillRect((i * tileSize), (j * tileSize), tileSize, tileSize);
				}
				if ((maze.getMapArray()[j][i] == 3) || (maze.getMapArray()[j][i] == 4) || (maze.getMapArray()[j][i] == 5)){
					g2d.setColor(Color.BLUE);
					g2d.fillRect((i * tileSize), (j * tileSize), tileSize, tileSize);
				}
			}
		}
	}


	public void drawPlayer(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(player.getAngle().getValue(), player.getPosX(), player.getPosY());
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(player.getPosX() - 2, player.getPosY() - 2, 4, 4);
	}
}