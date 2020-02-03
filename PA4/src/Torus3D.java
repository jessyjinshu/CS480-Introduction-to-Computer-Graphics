//****************************************************************************
//       Torus class
//****************************************************************************
// History :
//   Nov 9, 2014 Created by Stan Sclaroff
//   Dec 10, 2019 Modified by Jinshu Yang
//

public class Torus3D
{
	private Vector3D center;
	private float r,r_axial;
	private int m,n; // mesh dimensions
	public Mesh3D mesh;
	private Surface surface;
	private Material mats;
	
	public Torus3D(float _x, float _y, float _z, float _r, float _r_axial, int _m, int _n, Material _mats)
	{
		center = new Vector3D(_x,_y,_z);
		r = _r;
		r_axial = _r_axial;
		m = _m;
		n = _n;
		mats = _mats;
		initMesh();
		surface = new Surface(mesh, mats,center);
	}
	
	public Surface getSurface(){
		return surface;
	}
	public void set_center(float _x, float _y, float _z)
	{
		center.x=_x;
		center.y=_y;
		center.z=_z;
		fillMesh(); 		// update the triangle mesh
	}
	
	public void set_radius(float _r)
	{
		r = _r;
		fillMesh();		// update the triangle mesh
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
		fillMesh();
	}
		
	// given the current parameters for the torus
	// fill the triangle mesh with vertices and normals
	private void fillMesh()
	{
		int i,j;		
		float theta, phi;
		float d_theta=(float)(2.0*Math.PI)/ ((float)m-1);
		float d_phi=(float)(2.0*Math.PI) / ((float)n-1);
		float c_theta,s_theta;
		float c_phi, s_phi;
		Vector3D du = new Vector3D();
		Vector3D dv = new Vector3D();
		
		for(i=0,theta=(float)-Math.PI;i<m;++i,theta += d_theta)
	    {
			c_theta=(float)Math.cos(theta);
			s_theta=(float)Math.sin(theta);
			
			for(j=0,phi=(float)-Math.PI;j<n;++j,phi += d_phi)
			{
				// follow the formulation for torus given in textbook
				c_phi = (float)Math.cos(phi);
				s_phi = (float)Math.sin(phi);
				mesh.v[i][j].x=center.x+(r_axial+r*c_phi)*c_theta;
				mesh.v[i][j].y=center.y+(r_axial+r*c_phi)*s_theta;
				mesh.v[i][j].z=center.z+r*s_phi;
				
				// compute partial derivatives
				// then use cross-product to get the normal
				// and normalize to produce a unit vector for the normal
				du.x = -(r_axial+r*c_phi)*s_theta;
				du.y = (r_axial+r*c_phi)*c_theta;
				du.z = 0;
				
				dv.x = -r*s_phi*c_theta;
				dv.y = -r*s_phi*s_theta;
				dv.z = r*c_phi;
				
				du.crossProduct(dv, mesh.n[i][j]);
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