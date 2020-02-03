//****************************************************************************
//      DepthBuffer class
//****************************************************************************
// 
//   Dec 9, 2019 Created by Jinshu Yang
//

import java.awt.image.BufferedImage;
import java.util.*;

public class DepthBuffer{
	private int width;
	private int height;
	private int[][] buffer;
	
	//initializa the buffer
	public DepthBuffer(int _width, int _height){
		width=_width;
		height=_height;
		buffer = new int[width][height];
		for (int i =0; i < width; i++){
			for(int j = 0; j< height; j++){
				buffer[i][j] = -9999;
			}}
		
	}
	//get buffer
	public int getDepth(int x, int y){
		return buffer[x][y];
	}
	
	//set buffer
	public void setDepth(int x, int y, int z){

		buffer[x][y] = z;
	}
	
	//clean buffer
	public void depthclear(){
		for (int i =0; i < width; i++){
			
			for(int j = 0; j< height; j++){
				
				buffer[i][j] = -9999;
				
			}
		}
		
		
	}
	
	public void printBuff(){
		for (int i =0; i < width; i++){
			
				System.out.println(buffer[128][128]);
			
		}
	}
	
}