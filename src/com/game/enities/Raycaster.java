package com.game.enities;

import javax.vecmath.Vector2d;
import com.game.characters.Player;
import com.game.tools.Angle;

public class Raycaster {
	
	private final static double DEGREES_90 = Math.PI / 2;
	private final static double DEGREES_180 = Math.PI;
	private final static double DEGREES_270 = (Math.PI / 2) * 3;
	private Player player;

	

	public Raycaster(Player player) {
		this.player = player;
	}


	public Ray castRay(Map map, double angleOffset)
	{	
		Angle rayAngle = new Angle(player.getAngle().getValue() + angleOffset);
		Vector2d playerVector = player.getPlayerVector();	
		Vector2d xAxisCollisionVector = detectHorizontalCollision(map, rayAngle.getValue());
		Vector2d yAxisCollisionVector = detectVerticalCollision(map, rayAngle.getValue());
		Ray xRay = new Ray(playerVector, xAxisCollisionVector, true);
		Ray yRay = new Ray(playerVector, yAxisCollisionVector, false);	
		return getShortest(xRay, yRay);
	}

	
	public Vector2d detectVerticalCollision(Map maze, double rayAngle)
	{
		Vector2d playerVector = player.getPlayerVector();
		Vector2d collisionVector = playerVector;

		if (rayAngle == DEGREES_90)
		{
			collisionVector = new Vector2d(playerVector.x, (maze.getNumberOfRows()-1) * maze.getTileSize());
		}
		else if (rayAngle == DEGREES_270)
		{
			collisionVector = new Vector2d(playerVector.x, maze.getTileSize());
		}
		else
		{
			collisionVector = detectInitialVerticalTile(rayAngle, maze);
			collisionVector = detectFinalVerticalTile(maze, collisionVector, rayAngle);
		}
		return collisionVector;
	}
	
	public Vector2d detectInitialVerticalTile(double rayAngle, Map maze)
	{
		double opposite;
		double adjacent;
		int tileX;
		
		
		if ((rayAngle > DEGREES_90) && (rayAngle < DEGREES_270))
		{
			tileX = maze.getTileX(player.getPlayerVector(), rayAngle);
			adjacent = ((double) (tileX * maze.getTileSize())) - player.getPlayerVector().x ;
			opposite = adjacent * (Math.tan(rayAngle));
			return new Vector2d(((double) (tileX * maze.getTileSize()) - 0.0001), player.getPlayerVector().y + opposite);
		}
		else
		{	
			tileX = maze.getTileX(player.getPlayerVector(), rayAngle) + 1;
			adjacent = player.getPlayerVector().x - ((double) (tileX * maze.getTileSize())) ;
			opposite = adjacent * (Math.tan(rayAngle));
			return new Vector2d(((double) (tileX * maze.getTileSize())), player.getPlayerVector().y - opposite);		
		}
	}
	
	public Vector2d detectFinalVerticalTile(Map maze, Vector2d collisionVector, double rayAngle)
	{
		double sign = 1;
		
		if ((rayAngle > DEGREES_90)  && (rayAngle < DEGREES_270))
		{
			sign = sign * -1;
		}

		int tileX = maze.getTileX(collisionVector, rayAngle);
		int tileY = maze.getTileY(collisionVector, rayAngle);
		
		if ((tileY > maze.getNumberOfRows() - 1) || (tileY < 0) || (tileX > maze.getNumberOfColumns() - 1) || (tileX < 0))
		{
			return collisionVector;
		}
			
		while(maze.getMapArray()[tileY][tileX] == 0)
		{	
			double dx = collisionVector.x + (sign * maze.getTileSize());
			double dy = collisionVector.y + (sign * (maze.getTileSize()) * Math.tan(rayAngle));
			collisionVector.set(dx, dy);	
			tileX = maze.getTileX(collisionVector, rayAngle);
			tileY = maze.getTileY(collisionVector, rayAngle);
			
			if ((tileY > maze.getNumberOfRows() - 1) || (tileY < 0) || (tileX > maze.getNumberOfColumns() - 1) || (tileX < 0))
			{
				return collisionVector;
			}		
		}
		return collisionVector;
	}
	
	public Vector2d detectHorizontalCollision(Map maze, double rayAngle)
	{
		
		Vector2d playerVector = player.getPlayerVector();
		Vector2d collisionVector = playerVector;
		
		if (rayAngle == 0)
		{
			collisionVector = new Vector2d(maze.getTileSize() * (maze.getNumberOfColumns()-1), playerVector.y);
		}
		else if (rayAngle == DEGREES_180)
		{
			collisionVector = new Vector2d(maze.getTileSize(), playerVector.y);
		}
		else
		{
			collisionVector = detectInitialHorizontalTile(rayAngle, maze);
			collisionVector = detectFinalHorizontalTile(maze, collisionVector, rayAngle);
		}
		return collisionVector;
	}
	
	public Vector2d detectInitialHorizontalTile(double rayAngle, Map maze)
	{		
		double opposite;
		double adjacent;
		int tileY;
		
		if(rayAngle > DEGREES_180)
		{	
			tileY = maze.getTileY(player.getPlayerVector(), rayAngle);
			opposite = player.getPlayerVector().y - ((double) (tileY * maze.getTileSize())) ;
			adjacent = opposite / (Math.tan(rayAngle));
			return new Vector2d(player.getPlayerVector().x - adjacent, ((double) (tileY * maze.getTileSize()) - 0.0001));		
		}
		else
		{
			tileY = maze.getTileY(player.getPlayerVector(), rayAngle) + 1;
			opposite = ((double) (tileY * maze.getTileSize())) - player.getPlayerVector().y;
			adjacent = opposite / (Math.tan(rayAngle));
			return new Vector2d(player.getPlayerVector().x + adjacent, ((double) (tileY * maze.getTileSize())));
		}
	}
	
	public Vector2d detectFinalHorizontalTile(Map maze, Vector2d collisionVector, double rayAngle)
	{	
		double sign = 1;
		
		if (rayAngle > DEGREES_180)
		{
			sign = sign * -1;
		}

		int tileX = maze.getTileX(collisionVector, rayAngle);
		int tileY = maze.getTileY(collisionVector, rayAngle);
		
		if ((tileY > maze.getNumberOfRows() - 1) || (tileY < 0) || (tileX > maze.getNumberOfColumns() - 1) || (tileX < 0))
		{
			return collisionVector;
		}

		while(maze.getMapArray()[tileY][tileX] == 0)
		{		
			double dx = collisionVector.x + (sign * (maze.getTileSize()) / Math.tan(rayAngle));
			double dy = collisionVector.y + (sign * maze.getTileSize());
			collisionVector.set(dx, dy);	
			tileX = maze.getTileX(collisionVector, rayAngle);
			tileY = maze.getTileY(collisionVector, rayAngle);
			if ((tileY > maze.getNumberOfRows() - 1) || (tileY < 0) || (tileX > maze.getNumberOfColumns() - 1) || (tileX < 0))
			{
				return collisionVector;
			}		
		}
		return collisionVector;
	}
	
	
	private Ray getShortest(Ray xRay, Ray yRay)
	{
		double xLength = xRay.getLength();
		double yLength = yRay.getLength();
		
		if (xLength < yLength)
		{
			xRay.setHitXAxis(true);
			return xRay;
			
		}
		else
		{
			xRay.setHitXAxis(false);
			return yRay;
		}
	}

}
