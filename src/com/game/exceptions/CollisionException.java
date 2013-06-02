package com.game.exceptions;

public class CollisionException extends RuntimeException{

	private static final long serialVersionUID = -7453433647580951660L;

	public CollisionException(String message) {
       super(message);
    }

    public CollisionException(Throwable throwable) {
    	super();
    }
}
