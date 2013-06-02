package com.game.enities;

import javax.vecmath.Vector2d;

public class Ray {

	private Vector2d p1;
	private Vector2d p2;
	private boolean hitXAxis;

	public Ray(Vector2d p1, Vector2d p2, boolean hitXAxis) {
		this.p1 = p1;
		this.p2 = p2;
		this.hitXAxis = hitXAxis;

	}
	
	
	
	public boolean didHitXAxis() {
		return hitXAxis;
	}



	public Ray() {
		this.p1 = new Vector2d();
		this.p1 = new Vector2d();
	}
	
	public Vector2d getP1() {
		return p1;
	}
	
	public void setP1(Vector2d p1) {
		this.p1 = p1;
	}
	
	public Vector2d getP2() {
		return p2;
	}
	
	public void setP2(Vector2d p2) {
		this.p2 = p2;
	}
	
	public void setHitXAxis(boolean hitXAxis) {
		this.hitXAxis = hitXAxis;
	}
	
	public double getLength() {
		
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;	
		double distortedLength = Math.sqrt((dx * dx) + (dy * dy));
		return distortedLength;		
	}
}
