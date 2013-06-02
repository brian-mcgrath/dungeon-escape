package com.game.tools;

public class Angle {

	private final static double DEGREES_360 = Math.PI * 2;
	private double value;

	public Angle(double value) {
		this.value = value;
		setCorrectValue();
	}
	
	
	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
		setCorrectValue();
	}


	public void incrementValueBy(double amount) {
		
		value = value + amount;
		setCorrectValue();
	}
	
	
	public void decrementValueBy(double amount) {
		value = value - amount;
		setCorrectValue();
	}
	
	private void setCorrectValue() {
		if (value >= DEGREES_360) {
			value = value - DEGREES_360;
		}

		if (value < 0) {
			value = value + DEGREES_360;
		}
	}
	
	public String getAngleInDegrees()
	{
		int angleInDegrees = (int) (Math.toDegrees(value) + 0.5);
		
		if(angleInDegrees >= 360)
		{
			angleInDegrees = angleInDegrees - 360;
		}
		return Integer.toString(angleInDegrees);
	}
}
