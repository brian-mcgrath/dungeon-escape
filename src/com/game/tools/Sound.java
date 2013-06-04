package com.game.tools;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound implements Runnable {
	private String url;
	private Clip clip;

	public Sound(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try {
			AudioInputStream inputStream = 
					AudioSystem.getAudioInputStream
					(new File("lib\\sounds\\" + url));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} catch (Exception e) {
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
