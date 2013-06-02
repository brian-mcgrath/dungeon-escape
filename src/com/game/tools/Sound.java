package com.game.tools;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound implements Runnable{
	private String url;
	private Clip clip;

	public Sound(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try {
			File fileIn = new File("lib\\sounds\\" + url);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(fileIn);
			clip = AudioSystem.getClip();
			clip.open(inputStream);
//			System.out.println("Loaded: sounds\\" + url);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void playOnce() {
		clip.start();
	}
	
	
	public void startLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopLoop() {
		clip.stop();
	}
}


