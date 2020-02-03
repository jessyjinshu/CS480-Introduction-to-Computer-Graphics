//****************************************************************************
//      Surface class
//****************************************************************************
// 
//   December 7, 2019 Created by Jinshu Yang
//
import java.awt.image.BufferedImage;
import java.util.*;

public class Surface {
	public Mesh3D mesh;
	private Material mats;
	private Vector3D center;
	private Vector3D view_vector = new Vector3D(0,0,-1);

	
	
	//constructor
	public Surface(Mesh3D _mesh, Material _mat, Vector3D _center){
		mesh = _mesh;
		mats = _mat;
		center = _center;
		
	}
	

	//function to detect which shading
	public void drawSurface( ArrayList<InfiniteLight> lights, String shad){
		switch (shad){
		case "Flat":
			drawFlat(lights);
		case "Gouraud":
			drawGouraun(lights);
		case "Phong":
			drawPhong(lights);
		
	
		
		}
			
		
		
		
	}
	//function to translate the surface
	public void Translate(float x, float y, float z){
		Matrix.Translate(x, y, z, center,mesh);
		center.x += x;
		center.y += y;
		center.z += z;
	}
	//function to scale the surface
	public void Scaling(float x, float y, float z){
		Matrix.Scaling( x,  y,  z, center,  mesh);
		
	
	}
	//functions to rotate along x,y,z axis
	public void RotateX(float theta){
		Matrix.RotateX(theta, center, mesh);
		
			
		}
	
	public void RotateY(float theta){
		Matrix.RotateY(theta, center, mesh);
		
			
		}
	
	public void RotateZ(float theta){
		Matrix.RotateZ(theta, center, mesh);
		
			
		}
	
	//flat shading function
		public void drawFlat(ArrayList<InfiniteLight> lights){
		
		Vector3D v0,v1, v2, n0, n1, n2;
		Vector3D triangle_normal;
		Point3D[] tri = {new Point3D(), new Point3D(), new Point3D()};
        int cols,rows;
		cols = mesh.getCols();
		rows = mesh.getRows();
		
		for(int i=0; i < rows-1; ++i)
	    {
			for(int j=0; j <cols-1; ++j)
			{
				v0 = mesh.v[i][j];
				v1 = mesh.v[i][j+1];
				v2 = mesh.v[i+1][j+1];
						
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
				{	
					
					 
					
						// flat shading: use the normal to the triangle itself
						n2 = n1 = n0 =  triangle_normal;
						
						tri[0].c = calLight(lights, n0,v0);
						
						tri[2].c = tri[1].c = tri[0].c;
					

					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[0].z = (int)v0.z;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[1].z = (int)v1.z;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;
					tri[2].z = (int)v2.z;

					SketchBase.drawTriangle(lab9.buff,tri[0],tri[1],tri[2],false);      
				}
				
				
				v0 = mesh.v[i][j];
				v1 = mesh.v[i+1][j+1];
				v2 = mesh.v[i+1][j];
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
				{	
					
					n2 = n1 = n0 =  triangle_normal;
					tri[0].c = calLight(lights, n0,v0);
					tri[2].c = tri[1].c = tri[0].c;
					
		
					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[0].z = (int)v0.z;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[1].z = (int)v1.z;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;
					tri[2].z = (int)v2.z;
					
					SketchBase.drawTriangle(lab9.buff,tri[0],tri[1],tri[2],false);      
				}
			}	
	    }
	}
		
		//gouraud shading function
		public void drawGouraun(ArrayList<InfiniteLight> lights){

			
			
			Vector3D v0,v1, v2, n0, n1, n2;
			Vector3D triangle_normal;
			Point3D[] tri = {new Point3D(), new Point3D(), new Point3D()};
	        int cols,rows;
			cols = mesh.getCols();
			rows = mesh.getRows();
			
			
			
			for(int i=0; i < rows-1; ++i)
		    {
				for(int j=0; j <cols-1; ++j)
				{
					v0 = mesh.v[i][j];
					v1 = mesh.v[i][j+1];
					v2 = mesh.v[i+1][j+1];
					
					
				
					triangle_normal = computeTriangleNormal(v0,v1,v2);
					if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
					{	//gourauding shading
						
							n0 = mesh.n[i][j];
							n1 = mesh.n[i][j+1];
							n2 = mesh.n[i+1][j+1];
							
							
							
							
							tri[0].c = calLight(lights, n0,v0);
							tri[1].c = calLight(lights, n1,v1);
							tri[2].c = calLight(lights, n2,v2);
							
						

						tri[0].x = (int)v0.x;
						tri[0].y = (int)v0.y;
						tri[0].z = (int)v0.z;
						tri[1].x = (int)v1.x;
						tri[1].y = (int)v1.y;
						tri[1].z = (int)v1.z;
						tri[2].x = (int)v2.x;
						tri[2].y = (int)v2.y;
						tri[2].z = (int)v2.z;

						SketchBase.drawTriangle(lab9.buff,tri[0],tri[1],tri[2],true);      
					}
					
					
					v0 = mesh.v[i][j];
					v1 = mesh.v[i+1][j+1];
					v2 = mesh.v[i+1][j];
					triangle_normal = computeTriangleNormal(v0,v1,v2);
					
					if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
					{	
						
							n0 = mesh.n[i][j];
							n1 = mesh.n[i+1][j+1];
							n2 = mesh.n[i+1][j];
							
							
							
							tri[0].c = calLight(lights, n0,v0);
							tri[1].c = calLight(lights, n1,v1);
							tri[2].c = calLight(lights, n2,v2);
						
			
						tri[0].x = (int)v0.x;
						tri[0].y = (int)v0.y;
						tri[0].z = (int)v0.z;
						tri[1].x = (int)v1.x;
						tri[1].y = (int)v1.y;
						tri[1].z = (int)v1.z;
						tri[2].x = (int)v2.x;
						tri[2].y = (int)v2.y;
						tri[2].z = (int)v2.z;
						
						SketchBase.drawTriangle(lab9.buff,tri[0],tri[1],tri[2],true);      
					}
				}	
		    }
		}
		//phong shading
		public void drawPhong(ArrayList<InfiniteLight> lights){
			
			Vector3D v0,v1, v2, n0, n1, n2;
			Vector3D triangle_normal;
			Point3D[] tri = {new Point3D(), new Point3D(), new Point3D()};
	        int cols,rows;
			cols = mesh.getCols();
			rows = mesh.getRows();
			
			
			
			for(int i=0; i < rows-1; ++i)
		    {
				for(int j=0; j <cols-1; ++j)
				{
					v0 = mesh.v[i][j];
					v1 = mesh.v[i][j+1];
					v2 = mesh.v[i+1][j+1];
					
					
				
					triangle_normal = computeTriangleNormal(v0,v1,v2);
					if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
					{	
						
							n0 = mesh.n[i][j];
							n1 = mesh.n[i][j+1];
							n2 = mesh.n[i+1][j+1];
							
							
							tri[0].c = calLight(lights, n0,v0);
							tri[1].c = calLight(lights, n1,v1);
							tri[2].c = calLight(lights, n2,v2);
							
							tri[0].setNormal(n0);
							tri[1].setNormal(n1);
							tri[2].setNormal(n2);
							
						

						tri[0].x = (int)v0.x;
						tri[0].y = (int)v0.y;
						tri[0].z = (int)v0.z;
						tri[1].x = (int)v1.x;
						tri[1].y = (int)v1.y;
						tri[1].z = (int)v1.z;
						tri[2].x = (int)v2.x;
						tri[2].y = (int)v2.y;
						tri[2].z = (int)v2.z;
						drawTriangle(lab9.buff, tri[0],tri[1],tri[2],true, lights);

						
					}
					
					
					v0 = mesh.v[i][j];
					v1 = mesh.v[i+1][j+1];
					v2 = mesh.v[i+1][j];
					triangle_normal = computeTriangleNormal(v0,v1,v2);
					
					if(view_vector.dotProduct(triangle_normal) < 0.0)  // front-facing triangle?
					{	
						
							n0 = mesh.n[i][j];
							n1 = mesh.n[i+1][j+1];
							n2 = mesh.n[i+1][j];
							
							tri[0].c = calLight(lights, n0,v0);
							tri[1].c = calLight(lights, n1,v1);
							tri[2].c = calLight(lights, n2,v2);
							
							tri[0].setNormal(n0);
							tri[1].setNormal(n1);
							tri[2].setNormal(n2);
						
			
						tri[0].x = (int)v0.x;
						tri[0].y = (int)v0.y;
						tri[0].z = (int)v0.z;
						tri[1].x = (int)v1.x;
						tri[1].y = (int)v1.y;
						tri[1].z = (int)v1.z;
						tri[2].x = (int)v2.x;                                                                                                                                                                                                               
						tri[2].y = (int)v2.y;
						tri[2].z = (int)v2.z;
						
						drawTriangle(lab9.buff, tri[0],tri[1],tri[2],true, lights);					
						}
				}	
		    }
		}

		private Vector3D computeTriangleNormal(Vector3D v0, Vector3D v1, Vector3D v2)
	{
		Vector3D e0 = v1.minus(v2);
		Vector3D e1 = v0.minus(v2);
		Vector3D norm = e0.crossProduct(e1);
		
		if(norm.magnitude()>0.000001)
			norm.normalize();
		else 	// detect degenerate triangle and set its normal to zero
			norm.set((float)0.0,(float)0.0,(float)0.0);

		return norm;
	}
	
		private ColorType applyAmbient(Material mat){
		ColorType res = new ColorType();
		res.r= (float) mat.ka.r;
		res.g= (float) mat.ka.g;
		res.b= (float) mat.ka.b;
		
		
		
		return res;
	}

	//apply light illumination
	private ColorType calLight(ArrayList<InfiniteLight> lights, Vector3D n2, Vector3D v2){
		
		
		ColorType res = new ColorType(0,0,0);
		if (mats.ambient){
			if(lab9.amb)
			{
				res = applyAmbient(mats);
			}

		}
		for (int l = 0; l <lights.size();l++){
			res.addColor(lights.get(l).applyLight(mats, view_vector, n2,v2));
			
		
		
	}
		return res;
	}

//draw line segements with phong shading
	public void drawLinePhong(BufferedImage buff, Point3D p1, Point3D p2,  ArrayList<InfiniteLight> lights)
{
    int x0=p1.x, y0=p1.y, z0 = p1.z;
    //System.out.println(z0);
    int xEnd=p2.x, yEnd=p2.y,zEnd = p2.z;
    int dx = Math.abs(xEnd - x0),  dy = Math.abs(yEnd - y0), dz = Math.abs(zEnd - z0);

    if(dx==0 && dy==0)
    {
    	SketchBase.drawPoint(buff,p1);
    	return;
    }
    
    // if slope is greater than 1, then swap the role of x and y
    boolean x_y_role_swapped = (dy > dx); 
    if(x_y_role_swapped)
    {
    	x0=p1.y; 
    	y0=p1.x;
    	xEnd=p2.y; 
    	yEnd=p2.x;
    	dx = Math.abs(xEnd - x0);
    	dy = Math.abs(yEnd - y0);
    }
    
    // initialize the decision parameter and increments
    int p = 2 * dy - dx;
    int twoDy = 2 * dy,  twoDyMinusDx = 2 * (dy - dx);
    int x=x0, y=y0, z = z0;
    
    // set step increment to be positive or negative
    int step_x = x0<xEnd ? 1 : -1;
    int step_y = y0<yEnd ? 1 : -1;
    
    // deal with setup for color interpolation
    // first get r,g,b integer values at the end points
//    int r0=p1.c.getR_int(), rEnd=p2.c.getR_int();
//    int g0=p1.c.getG_int(), gEnd=p2.c.getG_int();
//    int b0=p1.c.getB_int(), bEnd=p2.c.getB_int();
    float xn0,xnEnd,yn0,ynEnd,zn0,znEnd;
    if(x_y_role_swapped){
    xn0 = p1.normal.y;
    	xnEnd = p2.normal.y;
    yn0 = p1.normal.x;
    ynEnd = p2.normal.x;
    zn0 = p1.normal.z;
    	znEnd = p2.normal.z;
    }
    else
    {
    	 xn0 = p1.normal.x;
    	 xnEnd = p2.normal.x;
     yn0 = p1.normal.y;
     ynEnd = p2.normal.y;
     zn0 = p1.normal.z;
     znEnd = p2.normal.z;
    }
    
    // compute the change in r,g,b 
    float dxn=Math.abs(xnEnd-xn0), dyn=Math.abs(ynEnd - yn0), dzn=Math.abs(znEnd-zn0);
    
    // set step increment to be positive or negative 
    int step_xn = xn0<xnEnd ? 1 : -1;
    int step_yn = yn0<ynEnd ? 1 : -1;
    int step_zn = zn0<znEnd ? 1 : -1;
    
    // compute whole step in each color that is taken each time through loop
    float whole_step_xn = step_xn*(dxn/dx);
    float whole_step_yn = step_yn*(dyn/dx);
    float whole_step_zn = step_zn*(dzn/dx);
    
    // compute remainder, which will be corrected depending on decision parameter
//    dr=dr%dx;
//    dg=dg%dx; 
//    db=db%dx;
    
    // initialize decision parameters for red, green, and blue
    float p_xn = 2 * dxn - dx;
    float twoDxn = 2 * dxn,  twoDxnMinusDx = 2 * (dxn - dx);
    float xn=xn0;
    
    float p_yn = 2 * dyn - dx;
    float twoDyn = 2 * dyn,  twoDynMinusDx = 2 * (dyn - dx);
    float yn=yn0;
    
    float p_zn = 2 * dzn - dx;
    float twoDzn = 2 * dzn,  twoDznMinusDx = 2 * (dzn - dx);
    float zn=zn0;
    
    // draw start pixel
    if(x_y_role_swapped)
    {
    ColorType res = new ColorType(0,0,0);
    
    Vector3D normal = new Vector3D(yn,xn,zn);
    normal.normalize();
    res = calLight(lights,normal, new Vector3D (y,x,z));
    Point3D point = new Point3D(y,x,z,res);
    
    	SketchBase.drawPoint(buff, point);
    

    	}
    else
    {
    	 ColorType res = new ColorType(0,0,0);
    	 Vector3D normal = new Vector3D(xn,yn,zn);
    	  normal.normalize();
    	 res = calLight(lights,normal, new Vector3D (x,y,z));
    	 Point3D point = new Point3D(x,y,z,res);
    	 SketchBase.drawPoint(buff, point);
    	
    }
    		
    
    while (x != xEnd) 
    {
    	// increment x and y
    	x+=step_x;
    	z = (int)(z0+(x-x0)/(x0-xEnd) * (z0-zEnd));
    	if (p < 0)
    		p += twoDy;
    	else 
    	{
    		y+=step_y;
    		p += twoDyMinusDx;
    	}
	        
    	// increment r by whole amount slope_r, and correct for accumulated error if needed
    	xn+=whole_step_xn;
    	if (p_xn < 0)
    		p_xn += twoDxn;
    	else 
    	{
    		xn+=step_xn;
    		p_xn += twoDxnMinusDx;
    	}
	    
    	// increment g by whole amount slope_b, and correct for accumulated error if needed  
    	yn+=whole_step_yn;
    	if (p_yn < 0)
    		p_yn += twoDyn;
    	else 
    	{
    		yn+=step_yn;
    		p_yn += twoDynMinusDx;
    	}
	    
    	// increment b by whole amount slope_b, and correct for accumulated error if needed
    	zn+=whole_step_zn;
    	if (p_zn < 0)
    		p_zn += twoDzn;
    	else 
    	{
    		zn+=step_zn;
    		p_zn += twoDznMinusDx;
    	}
	    
    	if(x_y_role_swapped)
    	{
    		ColorType res = new ColorType(0,0,0);
    	    
    	    Vector3D normal = new Vector3D(yn,xn,zn);
    	    normal.normalize();
    	    res = calLight(lights,normal, new Vector3D (y,x,z));
    	    Point3D point = new Point3D(y,x,z,res);
    	    
    	    	SketchBase.drawPoint(buff, point);
   
    	}
    	else
    	{
    		ColorType res = new ColorType(0,0,0);
    		Vector3D normal = new Vector3D(xn,yn,zn);
    	    normal.normalize();
       	 res = calLight(lights,normal, new Vector3D (x,y,z));
       	 Point3D point = new Point3D(x,y,z,res);
       	 SketchBase.drawPoint(buff, point);
    	}
    }
}

//draw triangles with Phong shading
	public void drawTriangle(BufferedImage buff, Point3D p1, Point3D p2, Point3D p3, boolean do_smooth, ArrayList<InfiniteLight> lights)
{
    // sort the triangle vertices by ascending x value
    Point3D p[] = SketchBase.sortTriangleVerts(p1,p2,p3);
    
    int x; 
    float y_a, y_b, z_a,z_b;
    float dy_a, dy_b,dz_a,dz_b;
    float dxn_a=0, dyn_a=0, dzn_a=0, dxn_b=0, dyn_b=0, dzn_b=0;
    float xn_a, yn_a,zn_a,xn_b,yn_b,zn_b;
    
    Point3D side_a = new Point3D(p[0]), side_b = new Point3D(p[0]);
    xn_a = p[0].normal.x;
    yn_a = p[0].normal.y;
    zn_a = p[0].normal.z;
    xn_b = p[0].normal.x;
    yn_b = p[0].normal.y;
    zn_b = p[0].normal.z;
    
 	side_a.setNormal(new Vector3D(xn_a,yn_a,zn_a));
	side_b.setNormal(new Vector3D(xn_b,yn_b,zn_b));
    if(!do_smooth)
    {
    	side_a.c = new ColorType(p1.c);
    	side_b.c = new ColorType(p1.c);
    }
    
    y_b = p[0].y;
    z_b = p[0].z;
    dy_b = ((float)(p[2].y - p[0].y))/(p[2].x - p[0].x);
    dz_b = ((float)(p[2].z - p[0].z))/(p[2].x - p[0].x);
    
    if(do_smooth)
    {
    	// calculate slopes in r, g, b for segment b
    	dxn_b = ((float)(p[2].normal.x - p[0].normal.x))/(p[2].x - p[0].x);
    	dyn_b = ((float)(p[2].normal.y - p[0].normal.y))/(p[2].x - p[0].x);
    	dzn_b = ((float)(p[2].normal.z - p[0].normal.z))/(p[2].x - p[0].x);
    }
    
    // if there is a left-hand part to the triangle then fill it
    if(p[0].x != p[1].x)
    {
    	y_a = p[0].y;
    	z_a = p[0].z;
    	dy_a = ((float)(p[1].y - p[0].y))/(p[1].x - p[0].x);
	dz_a = ((float)(p[1].z - p[0].z))/(p[1].x - p[0].x);    
    	if(do_smooth)
    	{
    		// calculate slopes in r, g, b for segment a
    		dxn_a = ((float)(p[1].normal.x - p[0].normal.x))/(p[1].x - p[0].x);
    		dyn_a = ((float)(p[1].normal.y - p[0].normal.y))/(p[1].x - p[0].x);
    		dzn_a = ((float)(p[1].normal.z - p[0].normal.z))/(p[1].x - p[0].x);
    	}
	    
	    // loop over the columns for left-hand part of triangle
	    // filling from side a to side b of the span
	    for(x = p[0].x; x < p[1].x; ++x)
	    {
	    	drawLinePhong(buff, side_a, side_b,lights);

	    	++side_a.x;
	    	++side_b.x;
	    	y_a += dy_a;
	    	y_b += dy_b;
	    	z_a += dz_a;
	    	z_b += dz_b;
	    	side_a.y = (int)y_a;
	    	side_b.y = (int)y_b;
	    	side_a.z = (int)z_a;
	    	side_b.z = (int)z_b;
	    	if(do_smooth)
	    	{
	    		xn_a +=dxn_a;
	    		xn_b +=dxn_b;
	    		yn_a +=dyn_a;
	    		yn_b +=dyn_b;
	    		zn_a +=dzn_a;
	    		zn_b +=dzn_b;
	    	}
	    	side_a.setNormal(new Vector3D(xn_a,yn_a,zn_a));
	    	side_b.setNormal(new Vector3D(xn_b,yn_b,zn_b));

	    }
    }
    
    // there is no right-hand part of triangle
    if(p[1].x == p[2].x)
    	return;
    
    // set up to fill the right-hand part of triangle 
    // replace segment a
    side_a = new Point3D(p[1]);
    side_a.setNormal(new Vector3D(p[1].normal.x,p[1].normal.y,p[1].normal.z));
    xn_a = side_a.normal.x;
    yn_a = side_a.normal.y;
    zn_a = side_a.normal.z;
    if(!do_smooth)
    	side_a.c =new ColorType(p1.c);
 	side_a.setNormal(new Vector3D(xn_a,yn_a,zn_a));
	side_b.setNormal(new Vector3D(xn_b,yn_b,zn_b));
    y_a = p[1].y;
    z_a = p[1].z;
    dy_a = ((float)(p[2].y - p[1].y))/(p[2].x - p[1].x);
    dz_a = ((float)(p[2].z - p[1].z))/(p[2].x - p[1].x);
    if(do_smooth)
    {
    	// calculate slopes in r, g, b for replacement for segment a
    	dxn_a = ((float)(p[2].normal.x- p[1].normal.x))/(p[2].x - p[1].x);
    	dyn_a = ((float)(p[2].normal.y- p[1].normal.y))/(p[2].x - p[1].x);
    	dzn_a = ((float)(p[2].normal.z - p[1].normal.z))/(p[2].x - p[1].x);
    }

    // loop over the columns for right-hand part of triangle
    // filling from side a to side b of the span
    for(x = p[1].x; x <= p[2].x; ++x)
    {
    	drawLinePhong(buff, side_a, side_b,lights);
	    
    	++side_a.x;
    	++side_b.x;
    	y_a += dy_a;
    	y_b += dy_b;
    	z_a += dz_a;
    	z_b += dz_b;
    	side_a.y = (int)y_a;
    	side_b.y = (int)y_b;
    	side_a.z = (int)z_a;
    	side_b.z = (int)z_b;
    	if(do_smooth)
    	{
    		xn_a +=dxn_a;
    		xn_b +=dxn_b;
    		yn_a +=dyn_a;
    		yn_b +=dyn_b;
    		zn_a +=dzn_a;
    		zn_b +=dzn_b;
    	}
    	side_a.setNormal(new Vector3D(xn_a,yn_a,zn_a));
    	side_b.setNormal(new Vector3D(xn_b,yn_b,zn_b));
    }
}
}
	
