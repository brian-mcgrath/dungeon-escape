package com.game.enities;

import java.awt.image.BufferedImage;
import java.util.List;
import com.game.tools.ImageTool;

public class Weapon implements Runnable{

	private int id;
	private int numberOfClips;
	private int ammoInCurrentClip;
	private boolean firing;
	private boolean reloading;
	private List<BufferedImage> images;
	private int currentSprite;
	private Thread animateWeapon;
	private int[] shotAnimation;
	private int[] reloadAnimation;
	
	public Weapon(int id) {
		this.id = id;	
		currentSprite = 0;
		firing = false;
		reloading = false;
		images = loadImages(id);
		numberOfClips = getMaxClips(id);
		ammoInCurrentClip = getFullClip(id);
		animateWeapon = new Thread(this);
		animateWeapon.start();
		
		shotAnimation = loadShotAnimation(id);
		reloadAnimation = loadReloadAnimation(id);
	}
	
	private int[] loadReloadAnimation(int id2) {
		int animation[] = null;
		if (id == 1) {
			animation = new int[]{0,4,3,0};
		}
		else if (id == 2) {
			animation = new int[]{0,3,4,5,4,3,0};
		}

		return animation;
	}

	private int[] loadShotAnimation(int id2) {
		int animation[] = null;
		if (id == 1) {
			animation = new int[]{0,2,5,1,0};
		}
		else if (id == 2) {
			animation = new int[]{0,1,2,1,0,3,4,5,4,3,0};
		}
		else {
			animation = new int[]{0,1,2,1,0};
		}
		return animation;
	}
	
	public int getMaxClips(int id)
	{
		if (id == 1)
		{
			return 8;
		}
		else if (id == 2)
		{
			return 5;
		}
		return 0;
	}
	
	public void fire()
	{	
		if(id == 0)
		{
			firing = true;
		}
		else if(ammoInCurrentClip > 0)
		{
			firing = true;
			ammoInCurrentClip = ammoInCurrentClip - 1;
		}
	}
	
	public void reload()
	{		
		if ((numberOfClips > 0) && (id != 0))
		{
			reloading = true;
			numberOfClips = numberOfClips - 1;
			ammoInCurrentClip = getFullClip(id);
		}
	}
	
	private List<BufferedImage> loadImages(int id) {
		String imageUrl = "lib\\textures\\weapons\\weapon_sprites_" + id + ".png";
		BufferedImage img = ImageTool.loadImage(imageUrl);
		return ImageTool.splitImage(img, getNumberOfFrames(id), 1);
	}
	
	private int getNumberOfFrames(int id)
	{
		if ((id == 1) || (id == 2))
		{
			return 6;
		}
		return 3;
	}
	
	private int getFullClip(int id)
	{
		if (id == 1)
		{
			return 15;
		}
		else if (id == 2)
		{
			return 8;
		}
		return 0;
	}
		

	/**
	 * @return the currentSprite
	 */
	public int getCurrentSprite() {
		return currentSprite;
	}
	/**
	 * @param currentSprite the currentSprite to set
	 */
	public void setCurrentSprite(int currentSprite) {
		this.currentSprite = currentSprite;
	}
	/**
	 * @return the images
	 */
	public List<BufferedImage> getImages() {
		return images;
	}
	
	public BufferedImage getCurrentImage() {
		return images.get(currentSprite);
		
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<BufferedImage> images) {
		this.images = images;
	}



	/**
	 * @return the firing
	 */
	public boolean isFiring() {
		return firing;
	}

	/**
	 * @param firing the firing to set
	 */
	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	

	/**
	 * @return the numberOfClips
	 */
	public int getNumberOfClips() {
		return numberOfClips;
	}

	/**
	 * @param numberOfClips the numberOfClips to set
	 */
	public void setNumberOfClips(int numberOfClips) {
		this.numberOfClips = numberOfClips;
	}

	/**
	 * @return the ammoInCurrentClip
	 */
	public int getAmmoInCurrentClip() {
		return ammoInCurrentClip;
	}

	/**
	 * @param ammoInCurrentClip the ammoInCurrentClip to set
	 */
	public void setAmmoInCurrentClip(int ammoInCurrentClip) {
		this.ammoInCurrentClip = ammoInCurrentClip;
	}

	/**
	 * @return the reloading
	 */
	public boolean isReloading() {
		return reloading;
	}

	/**
	 * @param reloading the reloading to set
	 */
	public void setReloading(boolean reloading) {
		this.reloading = reloading;
	}
	
	private void invokeSleep(long amount)
	{
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int i = 0;
		while(true){
			if(firing) {
				i = i + 1;
				currentSprite = shotAnimation[i];
				
				if(i == (shotAnimation.length-1)) {
					firing = false;
					
					i = 0;
				}
			}
			if(reloading)
			{
				i = i + 1;
				currentSprite = reloadAnimation[i];
				
				if(i == (reloadAnimation.length-1)) {
					reloading = false;
					i = 0;
				}
			}
			invokeSleep(80);
		}
	}
}
