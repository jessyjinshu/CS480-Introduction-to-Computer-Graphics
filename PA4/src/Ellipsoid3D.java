//****************************************************************************
//      Ellipsoid3D class
//****************************************************************************
// History :
//   Dec 9, 2019 Created by Jinshu Yang
//

public class Ellipsoid3D
{
	//ellipsoid parameters
	private Vector3D center;
	private float rx,ry,rz;
	
	private int m,n;
	public Mesh3D mesh;
	public Surface surface;
	private Material mats;
	
	//initializa the ellipsoid
	public Ellipsoid3D(float _x, float _y, float _z, float _rx,float _ry,float _rz, int _m, int _n, Material _mats)
	{
		center = new Vector3D(_x,_y,_z);
		rx = _rx;
		ry = _ry;
		rz = _rz;
		m = _m;
		n = _n;
		mats = _mats;
		initMesh();
		surface = new Surface(mesh,mats,center);
		
		
	}
	
	public Surface getSurface(){
		return surface;
	}
	
	public void set_center(float _x, float _y, float _z)
	{
		center.x=_x;
		center.y=_y;
		center.z=_z;
		fillMesh();  // update the triangle mesh
	}
	
	public void set_radius(float _rx, float _ry, float _rz)
	{
		rx = _rx;
		ry = _ry;
		rz = _rz;
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
		fillMesh();  // set the mesh vertices and normals
	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	private void fillMesh()
	{
		int i,j;		
		float theta, phi;
		float d_theta=(float)(2.0*Math.PI)/ ((float)(m-1));
		float d_phi=(float)Math.PI / ((float)n-1);
		float c_theta,s_theta;
		float c_phi, s_phi;
		Vector3D utheta, uphi;
		
		utheta = new Vector3D(0,0,0);
		uphi = new Vector3D(0,0,0);
		
		for(i=0,theta=-(float)Math.PI;i<m;++i,theta += d_theta)
	    {
			c_theta=(float)Math.cos(theta);
			s_theta=(float)Math.sin(theta);
			
			for(j=0,phi=(float)(-0.5*Math.PI);j<n;++j,phi += d_phi)
			{
				// vertex location
				c_phi = (float)Math.cos(phi);
				s_phi = (float)Math.sin(phi);
				mesh.v[i][j].x=center.x+rx*c_phi*c_theta;
				mesh.v[i][j].y=center.y+ry*c_phi*s_theta;
				mesh.v[i][j].z=center.z+rz*s_phi;
				
				
				mesh.n[i][j].x = c_phi*c_theta;
				mesh.n[i][j].y = c_phi*s_theta;
				mesh.n[i][j].z=s_phi;
				mesh.n[i][j].normalize();
				
				
		
			}
	    }
	}
	
	public void Translate(float x, float y, float z){
		Matrix.Translate(x, y, z, center,mesh);
		center.x += x;
		center.y += y;
		center.z += z;
	}
	
	public void Scaling(float x, float y, float z){
		Matrix.Scaling( x,  y,  z, center,  mesh);
		
	
	}
	
	public void RotateX(float theta){
		Matrix.RotateX(theta, center, mesh);
		
			
		}
	
	public void RotateY(float theta){
		Matrix.RotateY(theta, center, mesh);
		
			
		}
	
	public void RotateZ(float theta){
		Matrix.RotateZ(theta, center, mesh);
		
			
		}
}