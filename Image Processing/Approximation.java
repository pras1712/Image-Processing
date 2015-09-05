import java.awt.Color;

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.*;
import acm.util.ErrorException;


public class Approximation extends GraphicsProgram{
	
	private static final int ACCURACY = 50;
	
	public void run(){
		GImage face = new GImage("pras.png");
		
		int[][] pixelArray = face.getPixelArray();
		approximate(pixelArray);
		patriot(pixelArray);
		GImage newFace = new GImage(pixelArray);
		newFace.scale(0.7);
		add(newFace);
		
	}
	
	private void patriot(int[][] array){
		int height = array.length;
		int width = array[0].length;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int pixel = array[i][j];
				int r =  getRed(pixel);
				int g = getGreen(pixel);
				int b = getBlue(pixel);
				r = approx(r);
				g = approx(g);
				b = approx(b);
				array[i][j] = createRGBPixel(r, g, b);
				
			}
		}
	}
	
	private void approximate(int[][] array){
		int height = array.length;
		int width = array[0].length;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int pixel = array[i][j];
				int r =  getRed(pixel);
				int g = getGreen(pixel);
				int b = getBlue(pixel);
				array[i][j] = createRGBPixel(approx(r), approx(g), approx(b));
				
			}
		}
		
	}
	
	
	private int approx(int a){
		int toReturn = 0;
		int remainder = a%ACCURACY;
		if(remainder < ACCURACY/2){
			toReturn = a - remainder ;
		} else{
			toReturn = a - remainder + ACCURACY;
		}
		if(toReturn < 255){
			return toReturn;
		}
		return 255;
	}
	
	public static int getRed(int pixel){
		return (pixel >> 16) & 0xFF;
	}
	
	
	public static int getGreen(int pixel){
		return (pixel >> 8) & 0xFF;
	}
	
	
	public static int getBlue(int pixel){
		return pixel & 0xFF;
	}
	
	public static int createRGBPixel(int r, int g, int b){
		return (0xFF << 24) |  (r << 16) | (g <<8 ) | b;
	}
	
	
	

}
