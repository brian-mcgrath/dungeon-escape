package com.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.Game;

public class StartScreen extends JPanel{

	private boolean visible;
	private BufferedImage background;
	private BufferedImage text;
	private Game game;
	private int counter;
	
	public StartScreen(Game game, BufferedImage background, BufferedImage text) {
		this.game = game;
		this.background = background;
		this.text = text;
		counter = 0;
	}

	private static final long serialVersionUID = -7410728876600475300L;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawText(g);
	}

	private void drawText(Graphics g) {
		counter = counter + 1;
		
		Graphics2D g2d = (Graphics2D) g;
//		g2d.setColor(Color.WHITE);
//		int fontSize = game.getHeight() / 20;
//		Font smallFont = new Font("URW Chancery L", Font.BOLD, fontSize);
//		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
//		g2d.setFont(smallFont);
		int width = game.getWidth();
		int height = game.getHeight();
//
//		String text = "Press S to start";
//		int textWidth = g2d.getFontMetrics().stringWidth(text);
//		int x = (width / 2) - (textWidth / 2);
//		int y = (height / 3) * 2;
//		g2d.setBackground(Color.WHITE);
//		g2d.drawString(text, x, y);
		
//		JLabel label = new JLabel("Press S to start");
//		this.add(label);
		int textWidth = text.getWidth() * 2;
		int x = (width / 2) - (textWidth / 2);
		int y = (height / 3) * 2;
		
		
		if (counter == 30) {
			if (visible) {
				visible = false;
			}
			else {
				visible = true;
			}
			counter = 0;
		}
		
		if(visible) {
			g2d.drawImage(text, x, y, text.getWidth() * 2, text.getHeight() * 2, this);
		}

		
	}

	private void drawBackground(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
//		g2d.drawImage(background, 600, 480, this);
		g2d.drawImage(background, 0, 0, game.getWidth(), game.getHeight(), this);
		
	}
	

}
