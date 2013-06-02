package com.game.tools;

import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {
	
	private Sound footsteps;
	private Sound music;
	private List<Sound> gunShots;
	private Sound reload;
	
	public SoundPlayer() {
		footsteps = new Sound("footsteps_1.wav");
		footsteps.run();
		gunShots = loadGunshots();
		reload = new Sound("weapon_1_reload.wav");
	}
	
	private List<Sound> loadGunshots()
	{
		List<Sound> shots = new ArrayList<Sound>();
		String url;
		for (int i = 0; i < 3; i++)
		{
			url = "weapon_" + (i) + "_shot.wav";
			shots.add(new Sound(url));
		}
		return shots;
		
	}
	
	public void startMusic(String musicUrl) {
		music = new Sound(musicUrl);
		music.run();
		music.startLoop();
	}
	
	public void stopMusic() {
		music.stopLoop();
	}
	
	public void startFootsteps() {
		footsteps.startLoop();
	}
	
	public void stopFootsteps() {
		footsteps.stopLoop();
	}
	
	public void playGunshot(int id) {
		gunShots.get(id).run();
		gunShots.get(id).playOnce();
	}
	
	public void playReload() {
		reload.run();
		reload.playOnce();
	}
}
