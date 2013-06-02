package com.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.vecmath.Vector2d;
import com.game.Game;
import com.game.characters.Player;
import com.game.enities.Map;
import com.game.enities.Ray;
import com.game.tools.Angle;

public class PlayerScreen extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final double DEGREES_30 = Math.PI / 6;
	private static final double DEGREES_60 = DEGREES_30 * 2;
	private Game game;
	private Player player;
	private Map maze;
	private int tileSize;

	
	public PlayerScreen(Game game)
	{
		this.game = game;
		this.maze = game.getLevel().getMaze();
		this.player = game.getPlayer();
		this.tileSize = maze.getTileSize();
	}

	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBackground(g, 10);
		drawMaze(g);
		drawWeapon(g);
		drawStatus(g);
		g.dispose();
	}
	
	public void drawStatus(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		int clips = player.getWeapon().getNumberOfClips();
		int bullets = player.getWeapon().getAmmoInCurrentClip();
		int health = player.getHealth();


		int fontSize = game.getHeight() / 40;
		Font smallFont = new Font("URW Chancery L", Font.BOLD, fontSize);

		String healthOffset = "";
		String bulletOffset = "";
		
		if (health < 100) {
			healthOffset = healthOffset + " ";
		}
		else if (health < 10) {
			healthOffset = healthOffset + "  ";
		}
		
		if (bullets < 10) {
			bulletOffset = bulletOffset + " ";
		}
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		g2d.setFont(smallFont);
		
		String healthString = "health: " + healthOffset + health;
		String ammoString = "ammo: " + clips + " | " + bulletOffset + bullets;
		int ammoStringWidth = g2d.getFontMetrics().stringWidth(ammoString);
		int healthStringWidth = g2d.getFontMetrics().stringWidth(healthString);
		int stringHeight = g2d.getFontMetrics(smallFont).getHeight();
		int rectWidth1 = healthStringWidth + 16;
		int rectWidth2 = ammoStringWidth + 16;
		int rectHeight = stringHeight + 16;
		int x1 = 20;
		int x2 = game.getWidth() - ammoStringWidth - 16 - 16;
		int y = game.getHeight() - 16 - 38;	
		
		g2d.drawString(healthString, x1, y);
		g2d.drawString(ammoString, x2, y);
		
		
		Rectangle rect1 = new Rectangle(x1 - 8, y - stringHeight - 8, rectWidth1, rectHeight);
		g2d.draw(rect1);		
		Rectangle rect2 = new Rectangle(x2 - 8, y - stringHeight - 8, rectWidth2, rectHeight);
		g2d.draw(rect2);
	}
	
	public void drawWeapon(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage weaponImage = player.getWeapon().getCurrentImage();
		int width = weaponImage.getWidth() * 2;
		int height = weaponImage.getHeight() * 2;
		int xPos = (game.getWidth() / 2) - (width / 2);
		int yPos = game.getHeight() - height - 38;
		g2d.drawImage(weaponImage, xPos, yPos,width, height, this);
	}

	
	public void drawBackground(Graphics g, double longest)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, game.getWidth(), game.getHeight() / 2);//	
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, (game.getHeight() / 2) + 1, game.getWidth(), game.getHeight() / 2);
	}

	
	public void drawMaze(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		double distanceToProjectionPlane = (game.getWidth() / 2) / Math.tan(DEGREES_30);
		double projectionSliceLength, distortedDistanceToSlice, correctDistanceToSlice;
		double angleIncrement = (DEGREES_60) / game.getWidth();
		int tileValue, y1, length;
		int centerOfPlane = game.getHeight() / 2;
		Angle rayAngle = new Angle(DEGREES_30);
		Ray ray = new Ray();
		Angle angle = new Angle(0);
		Vector2d collisionVector;
		BufferedImage imageSlice;

		for (int i = game.getWidth() - 1; i >= 0; i--)
		{
			ray = player.getRaycaster().castRay(maze, rayAngle.getValue());
			distortedDistanceToSlice = ray.getLength();
			correctDistanceToSlice = distortedDistanceToSlice * Math.cos(rayAngle.getValue());
			collisionVector = ray.getP2();
			angle.setValue(player.getAngle().getValue() + rayAngle.getValue());	
			tileValue = maze.getTileValue(collisionVector, angle.getValue());
			projectionSliceLength = (maze.getTileSize() * distanceToProjectionPlane) / correctDistanceToSlice;		
			y1 = centerOfPlane - (int) ((projectionSliceLength / 2));
			length = (int) projectionSliceLength;
			imageSlice = getImageSlice(tileValue - 1, ray);	
			g2d.drawImage(imageSlice, i, y1, 1, length, this);			
			rayAngle.decrementValueBy(angleIncrement);
		}
	}
	
	
	private BufferedImage getImageSlice(int tileValue, Ray ray) {
		int offset;
		if(ray.didHitXAxis()) {
			offset = ((int) (ray.getP2().x)) % tileSize;
		}
		else {
			offset = ((int) (ray.getP2().y)) % tileSize;
		}
		
		return maze.getImageSliceByIndex(tileValue, offset, ray.didHitXAxis());
		
		
	}
}