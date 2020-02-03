//****************************************************************************
//      Box3D class
//****************************************************************************
// History :
//   Dec 9, 2019 Created by Jinshu Yang
//
import java.awt.image.BufferedImage;
import java.util.*;
public class Box3D
{
	private Vector3D center;
	private float height,width,length;

	//6 sides of the box
	private Mesh3D top;
	private Mesh3D bot;
	private Mesh3D left;
	private Mesh3D right;
	private Mesh3D front;
	private Mesh3D back;
	// 8 vertices
	private Vector3D v0,v1,v2,v3,v4,v5,v6,v7;
	
	private Surface topCap,botCap,leftCap,rightCap,frontCap,backCap;
	public ArrayList<Surface> surfaces;
	private Material mats;
	
	//initializa the box
	public Box3D(float _x, float _y, float _z, float _h, float _l, float _w, Material _mats)
	{
		center = new Vector3D(_x,_y,_z);
		height = _h;
		length = _l;
		width = _w;
	
		mats = _mats;
		surfaces = new ArrayList<Surface>();
		
		initMesh();
		topCap = new Surface(top,mats,center);
		surfaces.add(topCap);
		botCap = new Surface(bot,mats,center);
		surfaces.add(botCap);
		leftCap = new Surface(left,mats,center);
		surfaces.add(leftCap);
		rightCap = new Surface(right,mats,center);
		surfaces.add(rightCap);
		frontCap = new Surface(front,mats,center);
		surfaces.add(frontCap);
		backCap = new Surface(back,mats,center);
		surfaces.add(backCap);

		
		
	}
	
	public ArrayList<Surface> getSurface(){
		return surfaces;
	}
	
	public void set_center(float _x, float _y, float _z)
	{
		center.x=_x;
		center.y=_y;
		center.z=_z;
		fillMesh();  // update the triangle mesh
	}
	
	public void set_dimension(float _h, float _l, float _w)
	{
		height = _h;
		length = _l;
		width = _w;
		fillMesh(); // update the triangle mesh
	}
	
	
	private void initMesh()
	{
		top = new Mesh3D(2,2);
		bot = new Mesh3D(2,2);
		left = new Mesh3D(2,2);
		right = new Mesh3D(2,2);
		front = new Mesh3D(2,2);
		back = new Mesh3D(2,2);
		fillMesh();  // set the mesh vertices and normals
	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	private void fillMesh()
	{
		//fill the mesh by doing six caps
		v0 = new Vector3D(center.x-length/2,center.y+height/2,center.z+width/2);
		v1 = new Vector3D(center.x-length/2,center.y+height/2,center.z-width/2);
		v2 = new Vector3D(center.x+length/2,center.y+height/2,center.z-width/2);
		v3 = new Vector3D(center.x+length/2,center.y+height/2,center.z+width/2);
		v4 = new Vector3D(center.x-length/2,center.y-height/2,center.z+width/2);
		v5 = new Vector3D(center.x-length/2,center.y-height/2,center.z-width/2);
		v6 = new Vector3D(center.x+length/2,center.y-height/2,center.z-width/2);
		v7 = new Vector3D(center.x+length/2,center.y-height/2,center.z+width/2);
		
		top.v[0][0] = v1;
		top.v[0][1] = v2;
		top.v[1][0] = v0;
		top.v[1][1] = v3;
		
		top.n[0][0] = new Vector3D(0,1,0);
		top.n[0][1] = new Vector3D(0,1,0);
		top.n[1][0] = new Vector3D(0,1,0);
		top.n[1][1] = new Vector3D(0,1,0);

		bot.v[0][0] = v4;
		bot.v[0][1] = v7;
		bot.v[1][0] = v5;
		bot.v[1][1] = v6;
		
		bot.n[0][0] = new Vector3D(0,-1,0);
		bot.n[0][1] = new Vector3D(0,-1,0);
		bot.n[1][0] = new Vector3D(0,-1,0);
		bot.n[1][1] = new Vector3D(0,-1,0);

		left.v[0][0] = v0;
		left.v[0][1] = v4;
		left.v[1][0] = v1;
		left.v[1][1] = v5;
		
		left.n[0][0] = new Vector3D(-1,0,0);
		left.n[0][1] = new Vector3D(-1,0,0);
		left.n[1][0] = new Vector3D(-1,0,0);
		left.n[1][1] = new Vector3D(-1,0,0);
	
		right.v[0][0] = v3;
		right.v[0][1] = v2;
		right.v[1][0] = v7;
		right.v[1][1] = v6;

		right.n[0][0] = new Vector3D(1,0,0);
		right.n[0][1] = new Vector3D(1,0,0);
		right.n[1][0] = new Vector3D(1,0,0);
		right.n[1][1] = new Vector3D(1,0,0);

		front.v[0][0] = v0;
		front.v[0][1] = v3;
		front.v[1][0] = v4;
		front.v[1][1] = v7;
		
		front.n[0][0] = new Vector3D(0,0,1);
		front.n[0][1] = new Vector3D(0,0,1);
		front.n[1][0] = new Vector3D(0,0,1);
		front.n[1][1] = new Vector3D(0,0,1);

		
		back.v[0][0] = v2;
		back.v[0][1] = v1;
		back.v[1][0] = v6;
		back.v[1][1] = v5;
		
		back.n[0][0] = new Vector3D(0,0,-1);
		back.n[0][1] = new Vector3D(0,0,-1);
		back.n[1][0] = new Vector3D(0,0,-1);
		back.n[1][1] = new Vector3D(0,0,-1);

	}

	//translate function
	public void Translate(float x, float y, float z){
		Matrix.Translate(x, y,  z,  center, bot);
		Matrix.Translate(x, y,  z,  center, top);
		Matrix.Translate(x, y,  z,  center, left);
		Matrix.Translate(x, y,  z,  center, right);
		Matrix.Translate(x, y,  z,  center, front);
		Matrix.Translate(x, y,  z,  center, back);
		center.x += x;
		center.y += y;
		center.z += z;
	}
	
	public void Scaling(float x, float y, float z){
		Matrix.Scaling(x,y,z,center,bot);
		Matrix.Scaling(x,y,z,center,top);
		Matrix.Scaling(x,y,z,center,left);
		Matrix.Scaling(x,y,z,center,right);
		Matrix.Scaling(x,y,z,center,front);
		Matrix.Scaling(x,y,z,center,back);
		
	}
	
	public void RotateX(float theta){
		Matrix.RotateX(theta, center, bot);
		Matrix.RotateX(theta, center, top);
		Matrix.RotateX(theta, center, front);
		Matrix.RotateX(theta, center, back);
		Matrix.RotateX(theta, center, left);
		Matrix.RotateX(theta, center, right);
			
		}
	
	public void RotateY(float theta){
		Matrix.RotateY(theta, center, bot);
		Matrix.RotateY(theta, center, top);
		Matrix.RotateY(theta, center, front);
		Matrix.RotateY(theta, center, back);
		Matrix.RotateY(theta, center, left);
		Matrix.RotateY(theta, center, right);
			
		}
	
	public void RotateZ(float theta){
		Matrix.RotateZ(theta, center, bot);
		Matrix.RotateZ(theta, center, top);
		Matrix.RotateZ(theta, center, front);
		Matrix.RotateZ(theta, center, back);
		Matrix.RotateZ(theta, center, left);
		Matrix.RotateZ(theta, center, right);
			
		}
	
}