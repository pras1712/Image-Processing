import java.awt.Color;

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.*;
import acm.util.ErrorException;


public class VectorAdjustedAveraging extends GraphicsProgram{
	
//	private GImage face = new GImage("IMG_3349.JPG");
	private static final int ACCURACY = 100;
	
	public void run(){
		GImage face = new GImage("pras.png");
		face.scale(0.5);
//		add(face, 0, 0);
		
		int[][] pixelArray = face.getPixelArray();
		for(int i = 0; i < 50; i++){
			average(pixelArray);
		}
		GImage newFace = new GImage(pixelArray);
		newFace.scale(0.8);
		add(newFace);
		

		
	}
	
	private void average(int[][] array){
		int height = array.length;
		int width = array[0].length;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int r = avgRed(array, i, j);
				int g = avgGreen(array, i, j);
				int b = avgBlue(array, i, j);
				array[i][j] = createRGBPixel(r, g, b);
				
			}
		}
		
	}
	

	
	private int avgRed(int [][] array, int i, int j){
		int total = 0;
		int counter = 0;
		int [] arrayIJ = new int[3];
		arrayIJ[0] = getRed(array[i][j]);
		arrayIJ[1] = getGreen(array[i][j]);
		arrayIJ[2] = getBlue(array[i][j]);
		
		int [] otherArray = new int[3]; 
		if(0 < i && i < array.length - 1 && 0 < j && j < array[0].length - 1){
			for(int a = -1; a < 2; a++ ){
				for(int b = -1; b < 2; b++ ){
					
					otherArray[0] = getRed(array[i][j]);
					otherArray[1] = getGreen(array[i][j]);
					otherArray[2] = getBlue(array[i][j]);
					
					if (areVectorClose(arrayIJ, otherArray)){
						total += getRed(array[i+a][j+b]);
						counter++;
					}
				}
			}
			total /= counter;
		} else{
			total = getRed(array[i][j]);
		}
		return total;
	}
	
	private int avgGreen(int [][] array, int i, int j){
		int total = 0;
		int counter = 0;
		int [] arrayIJ = new int[3];
		arrayIJ[0] = getRed(array[i][j]);
		arrayIJ[1] = getGreen(array[i][j]);
		arrayIJ[2] = getBlue(array[i][j]);
		
		int [] otherArray = new int[3]; 
		if(0 < i && i < array.length - 1 && 0 < j && j < array[0].length - 1){
			for(int a = -1; a < 2; a++ ){
				for(int b = -1; b < 2; b++ ){
					
					otherArray[0] = getRed(array[i][j]);
					otherArray[1] = getGreen(array[i][j]);
					otherArray[2] = getBlue(array[i][j]);
					
					if (areVectorClose(arrayIJ, otherArray)){
						total += getGreen(array[i+a][j+b]);
						counter++;
					}
				}
			}
			total /= counter;
		} else{
			total = getGreen(array[i][j]);
		}
		return total;
	}
	
	
	private int avgBlue(int [][] array, int i, int j){
		int total = 0;
		int counter = 0;
		int [] arrayIJ = new int[3];
		arrayIJ[0] = getRed(array[i][j]);
		arrayIJ[1] = getGreen(array[i][j]);
		arrayIJ[2] = getBlue(array[i][j]);
		
		int [] otherArray = new int[3]; 
		if(0 < i && i < array.length - 1 && 0 < j && j < array[0].length - 1){
			for(int a = -1; a < 2; a++ ){
				for(int b = -1; b < 2; b++ ){
					
					otherArray[0] = getRed(array[i][j]);
					otherArray[1] = getGreen(array[i][j]);
					otherArray[2] = getBlue(array[i][j]);
					
					if (areVectorClose(arrayIJ, otherArray)){
						total += getBlue(array[i+a][j+b]);
						counter++;
					}
				}
			}
			total /= counter;
		} else{
			total = getBlue(array[i][j]);
		}
		return total;
	}
	

	private double vectorDifferenceDistance(int[] v1, int[] v2){
		double total = 0;
		for(int i = 0; i < v1.length; i++){
			total += Math.pow(v1[i] - v2[i], 2);
		}
		return Math.sqrt(total);
	}
	
	private boolean areVectorClose(int[] v1, int[] v2){
		double distance = vectorDifferenceDistance(v1, v2);
		if(distance < ACCURACY) return true;
		return false;
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
