package com.grayscale;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Grayscale {
	public static File grayscale(File input, int n, boolean save) {

  	   BufferedImage  image;
  	   int width;
  	   int height;
  	   File output = null;
  	   
  	      try {
  	    	 image = ImageIO.read(input);
  	    	 for(int k=0; k<n; k++){
  	    		 
	  	         width = image.getWidth();
	  	         height = image.getHeight();
	  	         
	  	         for(int i=0; i<height; i++){
	  	         
	  	            for(int j=0; j<width; j++){
	  	            
	  	               Color c = new Color(image.getRGB(j, i));
	  	               int red = (int)(c.getRed() * 0.299);
	  	               int green = (int)(c.getGreen() * 0.587);
	  	               int blue = (int)(c.getBlue() *0.114);
	  	               Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
	  	               
	  	               image.setRGB(j,i,newColor.getRGB());
	  	            }
	  	         }
  	    	 }
  	    	 if(save){
  	    	 output = new File(input.getName()+"-grayscale.jpg");
	  	         ImageIO.write(image, "jpg", output);
  	    	 }
  	      } 
  	      catch (Exception e) {
  	    	  
  	      }
  	      return output;
 }

}
