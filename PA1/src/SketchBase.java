//****************************************************************************
// SketchBase.  
//****************************************************************************
// Comments : 
//   Subroutines to manage and draw points, lines an triangles
//
// History :
//   Aug 2014 Created by Jianming Zhang (jimmie33@gmail.com) based on code by
//   Stan Sclaroff (from CS480 '06 poly.c)

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.*;
import java.lang.Math; 


public class SketchBase 
{
	public SketchBase()
	{
		// deliberately left blank
	}
	
	// draw a point
	public static void drawPoint(BufferedImage buff, Point2D p)
	{
		buff.setRGB(p.x, buff.getHeight()-p.y-1, p.c.getBRGUint8());
	}
	
	//////////////////////////////////////////////////
	//	Implement the following two functions
	//////////////////////////////////////////////////
	
	// draw a line segment
	public static void drawLine(BufferedImage buff, Point2D p1, Point2D p2)
	{
		
		//base case: if p1 and p2 are the same points
		if((p1.x == p2.x)&&(p1.y == p2.y))
		{
			drawPoint(buff,p1);
		}
		else
		{
			int absdX = Math.abs(p2.x-p1.x);
			int absdY = Math.abs(p2.y-p1.y);
			
			//float slope = (float)dY/dX;
			
			if(absdX>absdY)
			{
				if(p1.x<p2.x)
				{
					drawLineLessThanOne(buff,p1,p2);

				}
				else
				{
					drawLineLessThanOne(buff,p2,p1);
				}
			}
			else
			{
				if(p1.y<p2.y)
				{
					drawLineMoreThanOne(buff,p1,p2);
				}
				else
				{
					drawLineMoreThanOne(buff,p2,p1);
				}
			}
		}
		
		
		
		
	}
	//Anti-Aliasing
	public static void drawLine(BufferedImage buff, Point2D p1, Point2D p2,Boolean ifA)
	{
		//the drawline function for anti-aliasing
		if(ifA)
		{
			if((p1.x == p2.x)&&(p1.y == p2.y))
			{
				drawPoint(buff,p1);
			}
			else
			{
				int ori_Height = buff.getHeight();
				int ori_Width = buff.getWidth();
				int ori_type = buff.getType();
				
				int scalepara = 3;
				
				int new_Height = ori_Height*scalepara;
				int new_Width = ori_Width*scalepara;
				
				//new p1
				Point2D newp1 = copy(p1);
				newp1.x = newp1.x*scalepara;
				newp1.y = newp1.y*scalepara;
				
				//new p2
				Point2D newp2 = copy(p2);
				newp2.x = newp2.x*scalepara;
				newp2.y = newp2.y*scalepara;
				
				
				BufferedImage highBuff = new BufferedImage(new_Width,new_Height,ori_type);
				
				int absdX = Math.abs(p2.x-p1.x);
				int absdY = Math.abs(p2.y-p1.y);
				Point2D[] list;
				if(absdX>absdY)
				{
					if(p1.x<p2.x)
					{
						list = buffConversion(buff,drawLineLessThanOne(highBuff,newp1,newp2,true),p1,p2);
						

					}
					else
					{
						list = buffConversion(buff,drawLineLessThanOne(highBuff,newp2,newp1,true),p2,p1);
					}
				}
				else
				{
					if(p1.y<p2.y)
					{
						list = buffConversionT(buff,drawLineMoreThanOne(highBuff,newp1,newp2,true),p1,p2);
					}
					else
					{
						list = buffConversionT(buff,drawLineMoreThanOne(highBuff,newp2,newp1,true),p2,p1);
					}
				}
				
				for(int i = 0;i<list.length;i++)
				{
					drawPoint(buff,list[i]);
				}
			}
		
			
	
		}
		else
		{
			drawLine(buff,p1,p2);
		}	
	}
	

	
	public static void drawLineLessThanOne(BufferedImage buff, Point2D p1, Point2D p2)
	{
		//drawline function when the slope is 0<x<1
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		int sign = 1;

		int P = 2*dY-dX;
		
		if(dY<0)
		{
			sign = -1;
			dY = dY*sign;
		}
		
		int e = 0;
		for(int i = p1.x, j = p1.y;i<=p2.x;i++)
		{
			Point2D cur = new Point2D();

			cur.x = i;
			cur.y = j;
			cur.c = colorInterpolation(cur.x,cur.y,p1,p2);
			
			drawPoint(buff,cur);
			
			if(P<0)
			{
				P = P + 2*dY;
			}
			else
			{
				P = P + 2*dY - 2*dX;
				j = j + sign;
			}

			
			
		}
		
	}
	
	public static Point2D[] drawLineLessThanOne(BufferedImage buff, Point2D p1, Point2D p2,boolean ifA)
	{
		//the drawline function with slope 0<x<1
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		int sign = 1;

		int P = 2*dY-dX;
		
		if(dY<0)
		{
			sign = -1;
			dY = dY*sign;
		}
		

		Point2D[] ret = new Point2D[dX+1];
		int r = 0;

		for(int i = p1.x, j = p1.y;i<=p2.x;i++)
		{
			Point2D cur = new Point2D();

			cur.x = i;
			cur.y = j;
			cur.c = p1.c;
			//cur.c = colorInterpolation(cur.x,cur.y,p1,p2);
			
			//drawPoint(buff,cur);

			ret[r] = cur;
			r++;

			
			if(P<0)
			{
				P = P + 2*dY;
			}
			else
			{
				P = P + 2*dY - 2*dX;
				j = j + sign;

				
			}
			
		}
		return ret;
		
	}
	
	public static void drawLineMoreThanOne(BufferedImage buff, Point2D p1, Point2D p2)
	{
		//the drawline function with slope >1
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		
		int sign = 1;

		int P = 2*dX-dY;
		
		if(dX<0)
		{
			sign = -1;
			dX = dX*sign;
		}
		
		int e = 0;
		
		for(int j = p1.y,i = p1.x;j<=p2.y;j++)
		{
			Point2D cur = new Point2D();
			cur.x = i;
			cur.y = j;
			cur.c = colorInterpolation(cur.x,cur.y,p1,p2);
			
			drawPoint(buff,cur);
			
			if(P<0)
			{
				P = P + 2*dX;
			}
			else
			{
				P = P + 2*dX - 2*dY;
				i = i + sign;
			}

			
			
		}
		
	}
	
	public static Point2D[] drawLineMoreThanOne(BufferedImage buff, Point2D p1, Point2D p2,Boolean ifA)
	{
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		
		int sign = 1;

		int P = 2*dX-dY;
		
		if(dX<0)
		{
			sign = -1;
			dX = dX*sign;
		}
		

		Point2D[] ret = new Point2D[dY+1];
		int r = 0;
		
		for(int j = p1.y,i = p1.x;j<=p2.y;j++)
		{
			Point2D cur = new Point2D();
			cur.x = i;
			cur.y = j;
			cur.c = p1.c;
			//cur.c = colorInterpolation(cur.x,cur.y,p1,p2);
			ret[r] = cur;
			r++;
			//drawPoint(buff,cur);
			
			if(P<0)
			{
				P = P + 2*dX;
			}
			else
			{
				P = P + 2*dX - 2*dY;
				i = i + sign;
			}

			
			
		}
		return ret;
		
	}
	
	public static Point2D[] buffConversion(BufferedImage buff,Point2D[] list,Point2D p1,Point2D p2)
	{
		int newx = p1.x*3;
		int newy = p1.y*3;
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		
		Point2D[] ret = new Point2D[Math.abs(dX)+1];
		
		Color buffcolor = new Color(buff.getRGB(0,0));
		float red = buffcolor.getRed()/255.0f;
		float green = buffcolor.getGreen()/255.0f;
		float blue = buffcolor.getBlue()/255.0f;
		for(int i = 0;i<ret.length;i++)
		{
			ret[i] = new Point2D(0,0,new ColorType(red,green,blue));
			
//			c.x = 0;
//			c.y = 0;
//			c.c = new ColorType(0.0f,0.0f,0.0f);
		}
		
		for(int i = 0;i<list.length;i++)
		{
			Point2D elem = list[i];
			
			int ori_x = p1.x+(int)Math.floor((float)(elem.x-newx)/(float)3);
			int ori_y = p1.y+(int)Math.floor((float)(elem.y-newy)/(float)3);
			ret[ori_x-p1.x].x = ori_x;
			ret[ori_x-p1.x].y = ori_y;
			ret[ori_x-p1.x].c = colorConversion(p1.c,ret[ori_x-p1.x].c);
			
		}
		
		return ret;
	}
	
	public static Point2D[] buffConversionT(BufferedImage buff,Point2D[] list,Point2D p1,Point2D p2)
	{
		int newx = p1.x*3;
		int newy = p1.y*3;
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		
		Point2D[] ret = new Point2D[Math.abs(dY)+1];
		Color buffcolor = new Color(buff.getRGB(0,0));
		float red = buffcolor.getRed()/255.0f;
		float green = buffcolor.getGreen()/255.0f;
		float blue = buffcolor.getBlue()/255.0f;
		
		for(int i = 0;i<ret.length;i++)
		{
			ret[i] = new Point2D(0,0,new ColorType(red,green,blue));
			

		}
		
		for(int i = 0;i<list.length;i++)
		{
			Point2D elem = list[i];
			
			int ori_x = p1.x+(int)Math.floor((float)(elem.x-newx)/(float)3);
			int ori_y = p1.y+(int)Math.floor((float)(elem.y-newy)/(float)3);
			ret[ori_y-p1.y].x = ori_x;
			ret[ori_y-p1.y].y = ori_y;
			ret[ori_y-p1.y].c = colorConversion(p1.c,ret[ori_y-p1.y].c);
			
		}
		
		return ret;
	}
	
	public static ColorType colorConversion(ColorType line, ColorType ori)
	{
		ColorType newcolor = new ColorType(0.0f,0.0f,0.0f);
		
		newcolor.r = ((float)1/(float)3)*line.r+((float)2/(float)3)*ori.r;
		newcolor.g = ((float)1/(float)3)*line.g+((float)2/(float)3)*ori.g;
		newcolor.b = ((float)1/(float)3)*line.b+((float)2/(float)3)*ori.b;
		return newcolor;
	}
	public static ColorType colorInterpolation(int x, int y, Point2D p1, Point2D p2)
	{
		ColorType newcolor = new ColorType(0.0f,0.0f,0.0f);
		
		float totalDist = (float)Math.sqrt(Math.pow((p2.x-p1.x),2)+Math.pow((p2.y-p1.y),2));
		float distCurToP1 = (float)Math.sqrt(Math.pow((x-p1.x),2)+Math.pow((y-p1.y),2));
		
		newcolor.r = ((totalDist-distCurToP1)/totalDist)*p1.c.r+(distCurToP1/totalDist)*p2.c.r;
		newcolor.g = ((totalDist-distCurToP1)/totalDist)*p1.c.g+(distCurToP1/totalDist)*p2.c.g;
		newcolor.b = ((totalDist-distCurToP1)/totalDist)*p1.c.b+(distCurToP1/totalDist)*p2.c.b;
		
		return newcolor;
	}
	
	// draw a triangle

	
	public static void drawTriangle(BufferedImage buff, Point2D p1, Point2D p2, Point2D p3, boolean do_smooth)
	{
		//base case
		if(ifEqual(p1,p2)&&ifEqual(p1,p3))
		{
			drawPoint(buff,p1);
		}
		else if(ifEqual(p1,p2))
		{
			if(do_smooth==false)
			{
				p3.c = p1.c;
			}
			drawLine(buff,p1,p3);
		}
		else if(ifEqual(p2,p3))
		{
			if(do_smooth==false)
			{
				p3.c = p1.c;
			}
			drawLine(buff,p1,p3);
		}
		else if(ifEqual(p1,p3))
		{
			if(do_smooth==false)
			{
				p2.c = p1.c;
			}
			drawLine(buff,p1,p2);
		}
		else
		{
			
			Point2D top = max(max(p1,p2),p3);
			Point2D btom = min(min(p1,p2),p3);
			Point2D mid = middlePoint(p1,p2,p3,top,btom);
			
			if (do_smooth==false){
				top = copyColor(top,p1);
				btom = copyColor(btom,p1);
				mid = copyColor(mid,p1);
				}
			
			int upRange = top.y-mid.y;
			int downRange = mid.y - btom.y;
			
			int disTopMid = top.x-mid.x;
			int disTopBtom = top.x-btom.x;
			int disMidBtom = mid.x-btom.x;
			

			
			float slopeTopMid = (float)(top.y-mid.y)/(top.x-mid.x);
			float slopeTopBtom = (float)(top.y-btom.y)/(top.x-btom.x);
			float slopeMidBtom = (float)(mid.y-btom.y)/(mid.x-btom.x);
			
			float bTopMid = top.y-slopeTopMid*top.x;
			float bTopBtom = top.y-slopeTopBtom*top.x;
			float bMidBtom = mid.y-slopeMidBtom*mid.x;
			
			if(upRange>0)
			{
				//System.out.println("darw");
				for(int i = top.y;i>=mid.y;i--)
				{
					Point2D left = new Point2D();
					//left.c = top.c;
					
					if(disTopMid==0)
					{
						left.x = top.x;
					}
					else
					{
						left.x = (int)((i-bTopMid)/slopeTopMid);
					}

					left.y = i;
					
					
					if(left.x>mid.x)
					{
						left.c = colorInterpolation(left.x,left.y,mid,top);
					}
					else
					{
						left.c = colorInterpolation(left.x,left.y,top,mid);
					}
					
					
					
					//drawPoint(buff,left);
					Point2D right = new Point2D();
					//right.c = top.c;
					
					if(disTopBtom==0)
					{
						right.x = top.x;
					}
					else
					{
						right.x = (int)((i-bTopBtom)/slopeTopBtom);
					}

					
					right.y = i;
					
					if(right.x>btom.x)
					{
						right.c = colorInterpolation(right.x,right.y,btom,top);
					}
					else
					{
						right.c = colorInterpolation(right.x,right.y,top,btom);
					}
					
					drawLine(buff,left,right);
				}
			}
			
			if(downRange>0)
			{
				//System.out.println("darw11");
				for(int j = btom.y;j<=mid.y;j++)
				{
					Point2D left = new Point2D();
					//left.c = top.c;
					
					if(disMidBtom==0)
					{
						left.x = btom.x;
					}
					else
					{
						left.x = (int)((j-bMidBtom)/slopeMidBtom);
					}
					//left.x = (int)((j-bMidBtom)/slopeMidBtom);
					left.y = j;
					
					if(left.x>btom.x)
					{
						left.c = colorInterpolation(left.x,left.y,btom,mid);
					}
					else
					{
						left.c = colorInterpolation(left.x,left.y,mid,btom);
					}
					
					Point2D right = new Point2D();
					right.c = top.c;
					
					if(disTopBtom==0)
					{
						right.x = btom.x;
					}
					else
					{
						right.x = (int)((j-bTopBtom)/slopeTopBtom);
					}
					right.y = j;
					
					if(right.x>btom.x)
					{
						right.c = colorInterpolation(right.x,right.y,btom,top);
					}
					else
					{
						right.c = colorInterpolation(right.x,right.y,top,btom);
					}
					
					drawLine(buff,left,right);
				}
			}
			
		}
		
	}
	
	
	public static boolean ifEqual(Point2D p1, Point2D p2)
	{
		if((p1.x==p2.x)&&(p1.y==p2.y))
		{
			return true;
		}
		return false;
	}
	
	public static Point2D min(Point2D p1,Point2D p2)
	{
		if(p1.y<p2.y)
		{
			return p1;
		}
		return p2;
	}
	
	public static Point2D minx(Point2D p1,Point2D p2)
	{
		if(p1.x<p2.x)
		{
			return p1;
		}
		return p2;
	}
	
	
	public static Point2D max(Point2D p1,Point2D p2)
	{
		if(p1.y>p2.y)
		{
			return p1;
		}
		return p2;
	}
	
	public static Point2D maxx(Point2D p1,Point2D p2)
	{
		if(p1.x>p2.x)
		{
			return p1;
		}
		return p2;
	}
	
	public static Point2D middlePoint(Point2D p1,Point2D p2,Point2D p3,Point2D t,Point2D b)
	{
		if((!ifEqual(p1,t))&&(!ifEqual(p1,b)))
		{
			return p1;
		}
		else if((!ifEqual(p2,t))&&(!ifEqual(p2,b)))
		{
			return p2;
		}
		else
		{
			return p3;
		}
	}
	
	public static Point2D copy(Point2D tar)
	{
		Point2D cur = new Point2D();
		cur.x = tar.x;
		cur.y = tar.y;
		cur.c.r = tar.c.r;
		cur.c.g = tar.c.g;
		cur.c.b = tar.c.b;
		
		return cur;
		
	}
	
	public static Point2D copyColor(Point2D cur,Point2D tar)
	{
		cur.c.r = tar.c.r;
		cur.c.g = tar.c.g;
		cur.c.b = tar.c.b;
		
		return cur;
		
	}
	
	
	
	/////////////////////////////////////////////////
	// for texture mapping (Extra Credit for CS680)
	/////////////////////////////////////////////////
	public static void triangleTextureMap(BufferedImage buff, BufferedImage texture, Point2D p1, Point2D p2, Point2D p3)
	{
		if(ifEqual(p1,p2)&&ifEqual(p1,p3))
		{
			drawPoint(buff,p1);
		}
		else
		{
			Point2D top = max(max(p1,p2),p3);
			Point2D btom = min(min(p1,p2),p3);
			Point2D mid = middlePoint(p1,p2,p3,top,btom);
			
			int triHeight = top.y-btom.y;
			int triWidth = maxx(maxx(p1,p2),p3).x-minx(minx(p1,p2),p3).x;
			
			int upRange = top.y-mid.y;
			int downRange = mid.y - btom.y;
			
			int disTopMid = top.x-mid.x;
			int disTopBtom = top.x-btom.x;
			int disMidBtom = mid.x-btom.x;
			

			
			float slopeTopMid = (float)(top.y-mid.y)/(top.x-mid.x);
			float slopeTopBtom = (float)(top.y-btom.y)/(top.x-btom.x);
			float slopeMidBtom = (float)(mid.y-btom.y)/(mid.x-btom.x);
			
			float bTopMid = top.y-slopeTopMid*top.x;
			float bTopBtom = top.y-slopeTopBtom*top.x;
			float bMidBtom = mid.y-slopeMidBtom*mid.x;
			
			if(upRange>0)
			{
				//System.out.println("darw");
				for(int i = top.y;i>=mid.y;i--)
				{
					Point2D left = new Point2D();
					//left.c = top.c;
					
					if(disTopMid==0)
					{
						left.x = top.x;
					}
					else
					{
						left.x = (int)((i-bTopMid)/slopeTopMid);
					}

					left.y = i;
					
					left.c = textureColor(left.x-minx(minx(p1,p2),p3).x,left.y-btom.y,texture,triHeight,triWidth);
					

					
					
					
					//drawPoint(buff,left);
					Point2D right = new Point2D();
					//right.c = top.c;
					
					if(disTopBtom==0)
					{
						right.x = top.x;
					}
					else
					{
						right.x = (int)((i-bTopBtom)/slopeTopBtom);
					}

					
					right.y = i;
					
					right.c = textureColor(right.x-minx(minx(p1,p2),p3).x,right.y-btom.y,texture,triHeight,triWidth);
					

					
					drawLineTexture(buff,left,right,texture,triHeight,triWidth,minx(minx(p1,p2),p3).x,btom.y);
				}
			}
			
			if(downRange>0)
			{
				//System.out.println("darw11");
				for(int j = btom.y;j<=mid.y;j++)
				{
					Point2D left = new Point2D();
					//left.c = top.c;
					
					if(disMidBtom==0)
					{
						left.x = btom.x;
					}
					else
					{
						left.x = (int)((j-bMidBtom)/slopeMidBtom);
					}
					//left.x = (int)((j-bMidBtom)/slopeMidBtom);
					left.y = j;
					
					
					left.c = textureColor(left.x-minx(minx(p1,p2),p3).x,left.y-btom.y,texture,triHeight,triWidth);
					
					drawPoint(buff,left);

					
					Point2D right = new Point2D();
					right.c = top.c;
					
					if(disTopBtom==0)
					{
						right.x = btom.x;
					}
					else
					{
						right.x = (int)((j-bTopBtom)/slopeTopBtom);
					}
					right.y = j;
					
					right.c = textureColor(right.x-minx(minx(p1,p2),p3).x,right.y-btom.y,texture,triHeight,triWidth);

					
					drawLineTexture(buff,left,right,texture,triHeight,triWidth,minx(minx(p1,p2),p3).x,btom.y);
				}
			}
			
			
		}
		
	}
	

	
	public static ColorType textureColor(int x, int y,BufferedImage texture,int triHeight, int triWidth)
	{
		if(x<0)
		{
			x = 0;
		}
		if(y<0)
		{
			y = 0;
		}
		int tHeight = texture.getHeight()-1;
		int tWidth = texture.getWidth()-1;
		//System.out.println("the height is"+tHeight);
		//System.out.println("the width is"+tWidth);
		float x_ratio = ((float)x)/((float)triWidth);
		float y_ratio = ((float)y)/((float)triHeight);
		//System.out.println("the x is "+x);
		//System.out.println("the ratio is "+x_ratio);
		float u = x_ratio*tWidth;
		float v = y_ratio*tHeight;
		
		int u_floor = (int)Math.floor(u);
		int u_ceil = (int)Math.ceil(u);
		
		int v_floor = (int)Math.floor(v);
		int v_ceil = (int)Math.ceil(v);
		
		//System.out.println("the texel will be get at"+u_floor+" and "+v_floor);
//		ColorType cur_color = getTextureColor(u_floor,v_floor,texture);
//		
//		return cur_color;
		
		//vertice1
		Point2D vertex1 = new Point2D(u_ceil,v_floor,getTextureColor(u_ceil,v_floor,texture));
		
		//vertice2
		Point2D vertex2 = new Point2D(u_ceil,v_ceil,getTextureColor(u_ceil,v_ceil,texture));
		
		//vertice2
		Point2D vertex3 = new Point2D(u_floor,v_ceil,getTextureColor(u_floor,v_ceil,texture));
		
		//vertice2
		Point2D vertex4 = new Point2D(u_floor,v_floor,getTextureColor(u_floor,v_floor,texture));
		
		//left
		ColorType left_color = TextureColorInterpolation((float)u_floor,v,vertex3,vertex4);
		
		//right
		ColorType right_color = TextureColorInterpolation((float)u_ceil,v,vertex1,vertex2);
		
		//cur_point
		ColorType cur_color = TextureColorInterpolationWithFloatPoint(u,v,(float)u_floor,v,left_color,(float)u_ceil,v,right_color);
		
		return cur_color;
		
	}
	
	public static ColorType TextureColorInterpolation(float x, float y, Point2D p1,Point2D p2)
	{
		ColorType newcolor = new ColorType(0.0f,0.0f,0.0f);
		
		float totalDist = (float) 1.0;
		float distCurToP1 = (float)Math.sqrt(Math.pow((x-p1.x),2)+Math.pow((y-p1.y),2));
		
		newcolor.r = ((totalDist-distCurToP1)/totalDist)*p1.c.r+(distCurToP1/totalDist)*p2.c.r;
		newcolor.g = ((totalDist-distCurToP1)/totalDist)*p1.c.g+(distCurToP1/totalDist)*p2.c.g;
		newcolor.b = ((totalDist-distCurToP1)/totalDist)*p1.c.b+(distCurToP1/totalDist)*p2.c.b;
		
		return newcolor;
	}
	
	public static ColorType TextureColorInterpolationWithFloatPoint(float x, float y, float p1x, float p1y,ColorType c1, float p2x, float p2y,ColorType c2)
	{
		ColorType newcolor = new ColorType(0.0f,0.0f,0.0f);
		
		float totalDist = (float) 1.0;
		float distCurToP1 = (float)Math.sqrt(Math.pow((x-p1x),2)+Math.pow((y-p1y),2));
		
		newcolor.r = ((totalDist-distCurToP1)/totalDist)*c1.r+(distCurToP1/totalDist)*c2.r;
		newcolor.g = ((totalDist-distCurToP1)/totalDist)*c1.g+(distCurToP1/totalDist)*c2.g;
		newcolor.b = ((totalDist-distCurToP1)/totalDist)*c1.b+(distCurToP1/totalDist)*c2.b;
		
		return newcolor;
	}
	
	public static ColorType getTextureColor(int x, int y, BufferedImage texture)
	{
		ColorType newcolor = new ColorType(0.0f,0.0f,0.0f);
		Color c = new Color(texture.getRGB(x, y));
		
		newcolor.r = (float)c.getRed() / 255 * 1.f;
		newcolor.g = (float)c.getGreen() / 255 * 1.f;
		newcolor.b = (float)c.getBlue() / 255 * 1.f;
		
		return newcolor;
	}
	
	public static void drawLineTexture(BufferedImage buff, Point2D p1, Point2D p2,BufferedImage texture,int triHeight,int triWidth,int minx,int miny)
	{
		// replace the following line with your implementation
		drawPoint(buff, p2);
		
		//base case: if p1 and p2 are the same points
		if((p1.x == p2.x)&&(p1.y == p2.y))
		{
			drawPoint(buff,p1);
		}
		else
		{
			int absdX = Math.abs(p2.x-p1.x);
			int absdY = Math.abs(p2.y-p1.y);
			
			//float slope = (float)dY/dX;
			
			if(absdX>absdY)
			{
				if(p1.x<p2.x)
				{
					drawLineLessThanOneTexture(buff,p1,p2,texture,triHeight,triWidth,minx,miny);

				}
				else
				{
					drawLineLessThanOneTexture(buff,p2,p1,texture,triHeight,triWidth,minx,miny);
				}
			}
			else
			{
				if(p1.y<p2.y)
				{
					drawLineMoreThanOneTexture(buff,p1,p2,texture,triHeight,triWidth,minx,miny);
				}
				else
				{
					drawLineMoreThanOneTexture(buff,p2,p1,texture,triHeight,triWidth,minx,miny);
				}
			}
		}
		
		
		
		
	}
	
	public static void drawLineLessThanOneTexture(BufferedImage buff, Point2D p1, Point2D p2,BufferedImage texture,int triHeight, int triWidth,int minx,int miny)
	{
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		int sign = 1;

		int P = 2*dY-dX;
		
		if(dY<0)
		{
			sign = -1;
			dY = dY*sign;
		}
		
		int e = 0;
		for(int i = p1.x, j = p1.y;i<=p2.x;i++)
		{
			Point2D cur = new Point2D();

			cur.x = i;
			cur.y = j;
			cur.c = textureColor(cur.x-minx, cur.y-miny,texture,triHeight, triWidth);
			
			drawPoint(buff,cur);
			
			if(P<0)
			{
				P = P + 2*dY;
			}
			else
			{
				P = P + 2*dY - 2*dX;
				j = j + sign;
			}

			
			
		}
		
	}
	
	public static void drawLineMoreThanOneTexture(BufferedImage buff, Point2D p1, Point2D p2,BufferedImage texture,int triHeight, int triWidth,int minx,int miny)
	{
		int dX = p2.x-p1.x;
		int dY = p2.y-p1.y;
		
		int sign = 1;

		int P = 2*dX-dY;
		
		if(dX<0)
		{
			sign = -1;
			dX = dX*sign;
		}
		
		int e = 0;
		for(int j = p1.y,i = p1.x;j<=p2.y;j++)
		{
			Point2D cur = new Point2D();
			//System.out.println(i);
			//System.out.println(j);
			//System.out.println("line");
			cur.x = i;
			cur.y = j;
			cur.c = textureColor(cur.x-minx, cur.y-miny,texture,triHeight, triWidth);
			
			drawPoint(buff,cur);
			
			if(P<0)
			{
				P = P + 2*dX;
			}
			else
			{
				P = P + 2*dX - 2*dY;
				i = i + sign;
			}

			
			
		}
		
	}
	
	
	
	
}
