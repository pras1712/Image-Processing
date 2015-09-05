import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import javax.swing.*;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import javax.swing.event.*;

import java.awt.event.*;

public class TestStuff extends GraphicsProgram{
	
	
	private int[][] array;
	private GLabel currentLabel;
	
	public void run(){
//		for(int i = 0; i<255; i++){
//			GRect newRect = new GRect(5, 100);
//			newRect.setFilled(true);
//			newRect.setFillColor(new Color(255, 0, 0, i));
//			newRect.setColor(new Color(255, 0, 0, i));
//
//			add(newRect, 5*i, 100);
//			
//			
//		}
		
		
		GRect newRect = new GRect(100, 100);
		newRect.setFilled(true);
		newRect.setFillColor(new Color(255, 0, 0, 255));
		add(newRect, 0, 0);
		GRect nRect = new GRect(100, 100);
		nRect.setFilled(true);
		nRect.setFillColor(new Color(255, 0, 0, 0));
		add(nRect, 0, 100);
		
		
//		GImage face = new GImage("pras.png");
//		add(face, 0, 0);
//		
//		array = face.getPixelArray();
//		
//		System.out.println(GImage.getAlpha(array[100][100]));
//		
//
//		int height = array.length;
//		int width = array[0].length;
//		for(int i = 0; i < height; i++){
//			for(int j = 0; j < width; j++){
//				if( GImage.getAlpha(array[i][j]) < 255){
//					System.out.println(i);
//				}
//				
//			}
//		}
	}
	
	private int getLuminosity(int pixel){
		int r = GImage.getRed(pixel);
		int g = GImage.getGreen(pixel);
		int b = GImage.getBlue(pixel);

		return GMath.round(0.299*r + 0.587*g + 0.144*b);
	}
	
	

}
