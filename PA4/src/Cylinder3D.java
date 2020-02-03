//****************************************************************************
//      Cylinder3D class
//****************************************************************************
// 
//  Dec 9, 2019 Created by Jinshu Yang
//
import java.awt.image.BufferedImage;
import java.util.*;

public class Cylinder3D
{
	//Cylinder parameters
	private Vector3D center;
	private float r;
	private float height;
	private int m,n;
	
	//Cylinder mesh, body, uppercap, lower cap
	public Mesh3D mesh;
	public Mesh3D upperMesh;
	public Mesh3D lowerMesh;
	public ArrayList<Surface> surfaces;
	
	//Cylinder surfaces
	public Surface body;
	public Surface uppercap;
	public Surface lowercap;
	private Material mats;
	
	//initializa the cylinder
	public Cylinder3D(float _x, float _y, float _z, float _r, float _height, int _m, int _n, Material _mats)
	{
		center = new Vector3D(_x,_y,_z);
		height = _height;
		r = _r;
		m = _m;
		n = _n;
		mats = _mats;
		initMesh();
		surfaces = new ArrayList<Surface>();
		body = new Surface(mesh,mats,center);
		uppercap = new Surface(upperMesh,mats,center);
		lowercap = new Surface(lowerMesh,mats,center);
		surfaces.add(body);
		surfaces.add(uppercap);
		surfaces.add(lowercap);

		
		
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
	
	public void set_radius(float _r)
	{
		r = _r;
		fillMesh(); // update the triangle mesh
	}
	
	public void set_m(int _m)
	{
		m = _m;
		initMesh(); // resized the mesh, must re-initialize
	}
	
	public void set_n(int _n)
	{
		n = _n;
		initMesh(); // resized the mesh, must re-initialize
	}
	
	public int get_n()
	{
		return n;
	}
	
	public int get_m()
	{
		return m;
	}

	private void initMesh()
	{
		mesh = new Mesh3D(m,n);
		upperMesh = new Mesh3D(m,n);
		lowerMesh = new Mesh3D(m,n);
		fillMesh();  // set the mesh vertices and normals
	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	private void fillMesh()
	{
		int i,j;		
		int i1,j1;
		int i2,j2;
		float theta, z;
		float d_theta=(float)(2.0*Math.PI)/ ((float)(m-1));
		float d_z=(float)height/ ((float)n-1);
		float c_theta,s_theta;
		Vector3D dtheta = new Vector3D();
		Vector3D dz = new Vector3D();
		
		float t, t1;
		float d_t = (float)(2.0*Math.PI)/((float)m-1);
	
		float radius,radius1;
		
		float d_radius = (float)r/((float)n-1);
		float c_t,s_t;
		float c_t1,s_t1;
		
//		float c_phi, s_phi;
		//fill up the body mesh
		for(i=0,theta=-(float)Math.PI;i<m;++i,theta += d_theta)
	    {
			c_theta=(float)Math.cos(theta);
			s_theta=(float)Math.sin(theta);
			
			for(j=0,z=(float)(-0.5*height);j<n;++j,z += d_z)
			{
				// vertex location
				
				mesh.v[i][j].x=center.x+r*c_theta;
				mesh.v[i][j].y=center.y+r*s_theta;
				mesh.v[i][j].z=center.z+z;
				// unit normal to sphere at this vertex
				
				dtheta.x = -s_theta*r;
				dtheta.y = c_theta*r;
				dtheta.z = 0;
				
				dz.x = 0;
				dz.y = 0;
				dz.z = 1;
				
				dtheta.crossProduct(dz, mesh.n[i][j]);
				mesh.n[i][j].normalize();
				
			}
	    }
		//fill up the uppercap
		for (i1 = 0, t =(float)Math.PI;i1<m;++i1,t -= d_t ){
			c_t=(float)Math.cos(t);
			s_t=(float)Math.sin(t);
			for (j1 =0, radius = 0;j1<n;++j1,radius += d_radius){
				upperMesh.v[i1][j1].x=center.x+radius*c_t;
				upperMesh.v[i1][j1].y=center.y+radius*s_t;
				upperMesh.v[i1][j1].z=center.z+(height)/2;
				
				upperMesh.n[i1][j1] = new Vector3D(0,0,1);
			}
		}
		//fill up the lowercap
		for (i2 = 0, t1 =-(float)Math.PI;i2<m;++i2,t1 += d_t ){
			c_t1=(float)Math.cos(t1);
			s_t1=(float)Math.sin(t1);
			for (j2 =0, radius1 = 0;j2<n;++j2,radius1 += d_radius){
				lowerMesh.v[i2][j2].x=center.x+radius1*c_t1;
				lowerMesh.v[i2][j2].y=center.y+radius1*s_t1;
				lowerMesh.v[i2][j2].z=center.z-(height)/2;
				
				lowerMesh.n[i2][j2] = new Vector3D(0,0,-1);
			}
		}
		
		
	}
	
	public void Translate(float x, float y, float z){
		Matrix.Translate(x, y, z, center,mesh);
		Matrix.Translate(x, y, z, center,upperMesh);
		Matrix.Translate(x, y, z, center,lowerMesh);
		center.x += x;
		center.y += y;
		center.z += z;
	}
	
	public void Scaling(float x, float y, float z){
		Matrix.Scaling( x,  y,  z, center,  mesh);
		Matrix.Scaling( x,  y,  z, center,  upperMesh);
		Matrix.Scaling( x,  y,  z, center,  lowerMesh);
		
	
	}
	
	public void RotateX(float theta){
		Matrix.RotateX(theta, center, mesh);
		Matrix.RotateX(theta, center, upperMesh);
		Matrix.RotateX(theta, center, lowerMesh);
		
		
			
		}
	
	public void RotateY(float theta){
		Matrix.RotateY(theta, center, mesh);
		Matrix.RotateY(theta, center, upperMesh);
		Matrix.RotateY(theta, center, lowerMesh);

		
			
		}
	
	public void RotateZ(float theta){
		Matrix.RotateZ(theta, center, mesh);
		Matrix.RotateZ(theta, center, upperMesh);
		Matrix.RotateZ(theta, center, lowerMesh);
		
		
			
		}
}