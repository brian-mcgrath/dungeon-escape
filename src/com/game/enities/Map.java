package com.game.enities;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector2d;
import com.game.tools.ImageTool;

/**
 * @author Brian McGrath
 *
 */
public class Map {
	private int[][] mapArray;
	private int tileSize;
	private List<List<BufferedImage>> lightWallImages;
	private List<List<BufferedImage>> darkWallImages;

	
	/**
	 * @param mapNumber
	 * @param tileSize
	 */
	public Map(int mapNumber, int tileSize) {
		this.mapArray = loadMap(mapNumber);
		this.tileSize = tileSize;
		lightWallImages = loadWallSlices(9, false);
		darkWallImages = loadWallSlices(9, true);
	}

	
	/**
	 * @param numberOfWalls
	 * @param darken
	 * @return
	 */
	private List<List<BufferedImage>> loadWallSlices(int numberOfWalls, boolean darken) {
		String wallImagePath = "lib\\textures\\walls\\wall_";
		String wallImageName;
		BufferedImage image;
		List<BufferedImage> imageSlices;
		List<List<BufferedImage>> images = new ArrayList<List<BufferedImage>>(numberOfWalls);

		for (int i = 0; i < numberOfWalls; i++) {
			wallImageName = wallImagePath + (i + 1) + ".png";
			image = ImageTool.loadImage(wallImageName);
			if (darken) {
				image = ImageTool.darken(image, .8f);
			}

			imageSlices = ImageTool.splitImage(image, tileSize, 1);
			images.add(imageSlices);
		}
		return images;

	}

	
	/**
	 * @param mazeNumber
	 * @return
	 */
	public int[][] loadMap(int mazeNumber) {
		String map = "lib\\maps\\map_" + 1 + ".txt";
		File a = new File(map);
		FileInputStream fis = null;
		String mapString = "";
		String line = null;
		String[] rows = null;
		String[] cols = null;
		String[][] mapStringArray = null;
		int[][] mapIntArray = null;

		try {
			fis = new FileInputStream(a);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		try {
			while ((line = in.readLine()) != null) {
				mapString = mapString + line + "\n";
			}

			rows = mapString.split("\n");
			cols = rows[0].split(",");
			mapStringArray = new String[rows.length][cols.length];
			mapIntArray = new int[rows.length][cols.length];

			for (int i = 0; i < rows.length; i++) {
				cols = rows[i].split(",");
				mapStringArray[i] = cols;
			}

			for (int i = 0; i < mapStringArray.length; i++) {
				for (int j = 0; j < mapStringArray[0].length; j++) {
					mapIntArray[i][j] = Integer.parseInt(mapStringArray[i][j]);
				}
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapIntArray;
	}

	
	/**
	 * @param wallNumber
	 * @param index
	 * @return
	 */
	public BufferedImage getImageSliceByIndex(int wallNumber, int index, boolean dark) {
		if(dark) {
			return lightWallImages.get(wallNumber).get(index);
		}
		return darkWallImages.get(wallNumber).get(index);
		
	}


	/**
	 * @return
	 */
	public int getNumberOfRows() {
		return mapArray.length;
	}

	
	/**
	 * @return
	 */
	public int getNumberOfColumns() {
		return mapArray[1].length;
	}

	
	/**
	 * @param v
	 * @param rayAngle
	 * @return
	 */
	public int getTileX(Vector2d v, double rayAngle) {
		return ((int) v.x) / tileSize;
	}

	
	/**
	 * @param v
	 * @param rayAngle
	 * @return
	 */
	public int getTileY(Vector2d v, double rayAngle) {
		return ((int) v.y) / tileSize;
	}

	
	/**
	 * @param v
	 * @param rayAngle
	 * @return
	 */
	public int getTileValue(Vector2d v, double rayAngle) {
		int x = getTileX(v, rayAngle);
		int y = getTileY(v, rayAngle);
		return mapArray[y][x];
	}
	
	
	/**
	 * @return
	 */
	public int getTileSize() {
		return tileSize;
	}

	
	/**
	 * @param tileSize
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	
	/**
	 * @return
	 */
	public int[][] getMapArray() {
		return mapArray;
	}
}
