package com.game.tools;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageTool {

	public static BufferedImage loadImage(String url)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(url));
			System.out.println("Loaded: " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static List<BufferedImage> splitImage(BufferedImage img, int cols, int rows)
	{	
        int w = img.getWidth()/cols;  
        int h = img.getHeight()/rows;  
        int num = 0;  
        List<BufferedImage> imgs = new ArrayList<BufferedImage>(w*h);  
        for(int y = 0; y < rows; y++) {  
            for(int x = 0; x < cols; x++) {  
                imgs.add(num, new BufferedImage(w, h, img.getType()));
                Graphics2D g = imgs.get(num).createGraphics();  
                g.drawImage(img, 0, 0, w, h, w*x, h*y, w*x+w, h*y+h, null);  
                g.dispose();  
                num++;  
            }  
        }  
        return imgs;  
     }  
	
	public static BufferedImage darken(BufferedImage image, float amount)
	{	
	    RescaleOp op = new RescaleOp(amount, 0, null);
	    return op.filter(image, null);
	}
}
