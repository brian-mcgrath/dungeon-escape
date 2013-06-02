package com.game.screens;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.vecmath.Vector2d;
import com.game.Game;
import com.game.characters.Player;
import com.game.enities.Map;
import com.game.enities.Ray;

public class TestScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game game;
	private Player player;
	private Map maze;

	public TestScreen(Game game) {
		this.game = game;
		this.maze = game.getLevel().getMaze();
		this.player = game.getPlayer();
		this.setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMaze(g);
//		drawAngle(g);
//		drawTileLocation(g);
//		drawRays(g);
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

	
	public void drawRays(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Vector2d playerVector = player.getPlayerVector();
		Vector2d collisionVector;
		Ray ray;
		double rayAngle = Math.PI / 6;
		double angleIncrement = (Math.PI / 3) / game.getWidth();
		
		g2d.setColor(Color.CYAN);
		g2d.setStroke(new BasicStroke(2));
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		for (int i = 0; i < game.getWidth(); i++) {
			ray = player.getRaycaster().castRay(maze, rayAngle);
			collisionVector = ray.getP2();
			g2d.drawLine((int) playerVector.x, (int) playerVector.y, (int) collisionVector.x, (int) collisionVector.y);
			
//			if (i == game.getWidth() / 2) {
//				g2d.setStroke(new BasicStroke(4));
//				g2d.setColor(Color.RED);
//				g2d.drawLine((int) playerVector.x, (int) playerVector.y, (int) collisionVector.x, (int) collisionVector.y);
//				g2d.setStroke(new BasicStroke(1));
//				g2d.setColor(Color.GREEN);	
//			}	
			
			rayAngle = rayAngle - angleIncrement;
		}
	}

	
//	public void drawAngle(Graphics g) {
//		Graphics2D g2d = (Graphics2D) g;
//		Font font = new Font("URW Chancery L", Font.BOLD, 12);
//		g2d.setFont(font);
//		g2d.setColor(Color.WHITE);
//		String angleAsString = Double.toString(player.getAngle().getValue());
//		g2d.drawString(player.getAngleInDegrees(), 20, 40);
//		g2d.drawString(angleAsString, 20, 30);
//		g2d.drawString(player.getAngleInDegrees(), 20, 40);
//		g2d.drawString(angleAsString, 20, 30);
//	}
	

	public void drawTileLocation(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("URW Chancery L", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		Vector2d playerVector = player.getPlayerVector();
		int tileX = maze.getTileX(playerVector, player.getAngle().getValue());
		int tileY = maze.getTileY(playerVector, player.getAngle().getValue());
		String tileString = Integer.toString(tileX) + ", " + Integer.toString(tileY);
		g2d.drawString(tileString, 20, 90);
	}


	public void drawPlayer(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(player.getAngle().getValue(), player.getPosX(), player.getPosY());
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(player.getPosX() - 2, player.getPosY() - 2, 4, 4);
		g2d.drawLine(player.getPosX(), player.getPosY(), player.getPosX(), player.getPosY());
	}
}
