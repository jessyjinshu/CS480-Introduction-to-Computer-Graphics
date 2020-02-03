//****************************************************************************
//      Point3D class based on the original Point2D class
//****************************************************************************
// 
//   Dec 9, 2019 Created by Jinshu Yang
//
public class Point3D 
{
	public int x, y,z;
	public float u, v; // uv coordinates for texture mapping
	public ColorType c;
	public Vector3D normal;
	

	//constructos due to diferent input
	public Point3D (int _x, int _y,int _z, ColorType _c)
	{
		u = 0;
		v = 0;
		x = _x;
		y = _y;
		z = _z;
		c = _c;
	}
	public Point3D (int _x, int _y, int _z, ColorType _c, float _u, float _v)
	{
		u = _u;
		v = _v;
		x = _x;
		y = _y;
		z = _z;
		c = _c;
	}
	public Point3D ()
	{
		c = new ColorType(1.0f, 1.0f, 1.0f);
	}
	public Point3D ( Point3D p)
	{
		u = p.u;
		v = p.v;
		x = p.x;
		y = p.y;
		z = p.z;
		c = new ColorType(p.c.r, p.c.g, p.c.b);
	}
	
	
	public static ColorType colorInter(int x, int y, int z, Point3D p1 ,Point3D p2)
	{
		//for any point among a given line, find out its distance between two source points
		//and interpolate its color accordingly
		ColorType res = new ColorType(0,0,0);
		float dist2 = (float)Math.sqrt(Math.pow((p2.x-p1.x),2)+Math.pow((p2.y-p1.y),2) + Math.pow((p2.z-p1.z),2));
		float dist1 = (float)Math.sqrt(Math.pow((x-p1.x),2)+Math.pow((y-p1.y),2)+Math.pow((z-p1.z),2));
		
		
		res.r = (dist2-dist1)/dist2 * p1.c.r + dist1/dist2 * p2.c.r;
		res.g = (dist2-dist1)/dist2 * p1.c.g + dist1/dist2 * p2.c.g;
		res.b = (dist2-dist1)/dist2 * p1.c.b + dist1/dist2 * p2.c.b;
		
		return res;
	}

	public void setNormal(Vector3D _normal){
		normal = _normal;
	}

	public Vector3D returnNormal(){
		Vector3D res = new Vector3D(normal);
		return res;
	}
}