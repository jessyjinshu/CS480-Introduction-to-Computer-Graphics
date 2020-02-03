//****************************************************************************
//      Infinite Light class which contains (Infinite light,point light and ambient light)
//****************************************************************************
// 
//  Dec 9, 2019 Created by Jinshu Yang
//
public class InfiniteLight
{
	//light paramters
	public Vector3D location;
	public Vector3D direction;
	public ColorType color;
	public boolean Infinite, Spotlight, Point,Ambient;
	public boolean attenuation;
	public float angular;
	public float radatten;
	private final float maxDist = 200;
	private final float al = 2;
	private final float maxAngle = (float)Math.PI/8;
	
	public void LightRotate(Quaternion q, Vector3D center)
	{
		Quaternion q_inv = q.conjugate();
		Vector3D vec;
		
		Quaternion p;
		
		
				// apply pivot rotation to vertices, given center point
				p = new Quaternion((float)0.0,location.minus(center)); 
				p=q.multiply(p);
				p=p.multiply(q_inv);
				vec = p.get_v();
				location=vec.plus(center);
				
				// rotate the normals
				p = new Quaternion((float)0.0,direction);
				p=q.multiply(p);
				p=p.multiply(q_inv);
				direction = p.get_v();
			
		}
	
	//light constructor
	public InfiniteLight(ColorType _c)
	{
		color = _c;
		Infinite = false;
		Spotlight = false;
		Point = false;
		
		}

	//inf light constructor
	public void InfiLight(Vector3D v){
		Infinite = true;
		direction = v;
		location = new Vector3D(0,0,0);
	}
	//point light constructor
	public void PointLight(Vector3D loc){
		Point = true;
		location = loc;
		direction = new Vector3D(0,0,0);
	}
	//amb light constructor
	public void AmbLight(){
		Ambient = true;

	}
	
	public void SpotLight(Vector3D loc, Vector3D dir){
		direction = dir;
		location = loc;
		direction.normalize();
		Spotlight = true;
	}
	
	public void fraddatten(Vector3D x){
		float dist  = location.dist(x);
		//System.out.printf("distance %f \n",dist);
		if(dist > maxDist){
			radatten = 0;
		
		}
		else
			radatten = 1/(float)(0.3+0.0005*dist+0.00003*Math.pow(dist, 2));
			
	}
	
	public void fangular(Vector3D x){
		
		float angle = direction.dotProduct(x);
		
		if (angle < maxAngle) {
			angular = (float)Math.pow(angle, al);
		}
		else{
			angular = 0;
		}
	}
		
	public ColorType applyLight(Material mat, Vector3D v, Vector3D n, Vector3D ps){
		ColorType res = new ColorType(1,1,1);
		if (Infinite){
			res = applyLightInfinite(mat,v,n,ps);
		}
		if(Point){
			res = applyLightPoint(mat,v,n,ps);
		}
		if(Spotlight){
			res = applyLightSpot(mat,v,n,ps);
		}
		if(Ambient) {
			res = applyAmbient(mat);
		}
		res.r = (float) Math.min(1.0, res.r);
		res.g = (float) Math.min(1.0, res.g);
		res.b = (float) Math.min(1.0, res.b);
		return res;
	}
	// apply this light source to the vertex / normal, given material
	// return resulting color value
	public ColorType applyLightSpot(Material mat, Vector3D v, Vector3D n, Vector3D ps){
		
		Vector3D dir = ps.minus(location);
	
		dir.normalize();
		ColorType res = new ColorType();
		fraddatten(ps);
		fangular(dir);
		
		
		
		
		
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0
		double dot = dir.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse)
			{
				res.r = (float)(dot*mat.kd.r*color.r);
				res.g = (float)(dot*mat.kd.g*color.g);
				res.b = (float)(dot*mat.kd.b*color.b);
			}
			// specular component
			if(mat.specular)
			{
				Vector3D r = dir.reflect(n);
				dot = r.dotProduct(v);
				if(dot<0.0)
				{
					res.r += (float)Math.pow((-dot*mat.ks.r*color.r),mat.ns);
					res.g += (float)Math.pow((-dot*mat.ks.g*color.g),mat.ns);
					res.b += (float)Math.pow((-dot*mat.ks.b*color.b),mat.ns);
				}
			}
			
			// clamp so that allowable maximum illumination level is not exceeded'
			//System.out.printf("radatten %f \n",radatten);
			//System.out.printf("angular %f \n",angular);

			res.r = angular * radatten * res.r;
			res.g = angular * radatten * res.g;
			res.b = angular * radatten * res.b;
			res.r = (float) Math.min(1.0, res.r);
			res.g = (float) Math.min(1.0, res.g);
			res.b = (float) Math.min(1.0, res.b);
		}
		return(res);
	}
	
	public ColorType applyLightPoint(Material mat, Vector3D v, Vector3D n, Vector3D ps){
		
		Vector3D dir = location.minus(ps);
		dir.normalize();
		ColorType res = new ColorType();
		fraddatten(ps);
		fangular(dir);
		
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0
		double dot = dir.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse)
			{
				res.r = (float)(dot*mat.kd.r*color.r);
				res.g = (float)(dot*mat.kd.g*color.g);
				res.b = (float)(dot*mat.kd.b*color.b);
			}
			// specular component
			if(mat.specular)
			{
				Vector3D r = dir.reflect(n);
				dot = r.dotProduct(v);
				if(dot<0.0)
				{
					res.r += (float)Math.pow((-dot*mat.ks.r*color.r),mat.ns);
					res.g += (float)Math.pow((-dot*mat.ks.g*color.g),mat.ns);
					res.b += (float)Math.pow((-dot*mat.ks.b*color.b),mat.ns);
				}
			}
			
			// clamp so that allowable maximum illumination level is not exceeded
			res.r = (float) Math.min(1.0, res.r);
			res.g = (float) Math.min(1.0, res.g);
			res.b = (float) Math.min(1.0, res.b);
		}
		return(res);
	}
	
	public ColorType applyLightInfinite(Material mat, Vector3D v, Vector3D n, Vector3D ps){
		ColorType res = new ColorType();
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0
		double dot = direction.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse)
			{
				res.r = (float)(dot*mat.kd.r*color.r);
				res.g = (float)(dot*mat.kd.g*color.g);
				res.b = (float)(dot*mat.kd.b*color.b);
			}
			// specular component
			if(mat.specular)
			{
				Vector3D r = direction.reflect(n);
				dot = r.dotProduct(v);
				if(dot<0.0)
				{
					res.r += (float)Math.pow((-dot*mat.ks.r*color.r),mat.ns);
					res.g += (float)Math.pow((-dot*mat.ks.g*color.g),mat.ns);
					res.b += (float)Math.pow((-dot*mat.ks.b*color.b),mat.ns);
				}
			}
			
			// clamp so that allowable maximum illumination level is not exceeded
			res.r = (float) Math.min(1.0, res.r);
			res.g = (float) Math.min(1.0, res.g);
			res.b = (float) Math.min(1.0, res.b);
		}
		return(res);
	}
	
	public ColorType applyAmbient(Material mat){
		ColorType res = new ColorType();
		res.r= (float) mat.ka.r;
		res.g= (float) mat.ka.g;
		res.b= (float) mat.ka.b;
		
		
		
		return res;
	}
	
	public void rotateL(Quaternion q, Vector3D center)
	{
		Quaternion q_inv = q.conjugate();
		Vector3D vec;
		
		Quaternion p;
		
		
				// apply pivot rotation to vertices, given center point
				p = new Quaternion((float)0.0,location.minus(center)); 
				p=q.multiply(p);
				p=p.multiply(q_inv);
				vec = p.get_v();
				location=vec.plus(center);
				
				// rotate the normals
				p = new Quaternion((float)0.0,direction);
				p=q.multiply(p);
				p=p.multiply(q_inv);
				direction = p.get_v();
			}
		
}
