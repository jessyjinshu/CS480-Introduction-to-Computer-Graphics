//****************************************************************************
//      Material class
//****************************************************************************
// // History :
//   Nov 6, 2014 Created by Stan Sclaroff
//   Nov 19, 2019 Modified by Zezhou Sun
//
//
public class Material 
{
	public ColorType ka, kd, ks;
	public int ns;
	public boolean specular, diffuse, ambient;
	
	public Material(ColorType _ka, ColorType _kd, ColorType _ks, int _ns)
	{
		ks = new ColorType(_ks);  // specular coefficient for r,g,b
		ka = new ColorType(_ka);  // ambient coefficient for r,g,b
		kd = new ColorType(_kd);  // diffuse coefficient for r,g,b
		ns = _ns;  // specular exponent
		
		// set boolean variables 
		specular = (ns>0 && (ks.r > 0.0 || ks.g > 0.0 || ks.b > 0.0));
		diffuse = (kd.r > 0.0 || kd.g > 0.0 || kd.b > 0.0);
		ambient = (ka.r > 0.0 || ka.g > 0.0 || ka.b > 0.0);
	}
	
	public void setNS(int NS){
		ns = NS;
		specular = (ns>0 && (ks.r > 0.0 || ks.g > 0.0 || ks.b > 0.0));
		//System.out.println(ns);
	}
	
	public void setSDA(boolean _specular, boolean _diffuse, boolean _ambient){
		specular = _specular;
		diffuse = _diffuse;
		ambient = _ambient;
	}
	public void changeKA(float n){
		if ((ka.r + n) < 1){
			if ((ka.r +n) >0 ){
				ka.r += n;
			}
		}
		if ((ka.g + n) < 1){
			if ((ka.g +n) >0 ){
				ka.g += n;
			}
		}
		if ((ka.b + n) < 1){
			if ((ka.b +n) >0 ){
				ka.b += n;
			}
		}
	}
	public void changeKD(float n){
		if ((kd.r + n) < 1){
			if ((kd.r +n) >0 ){
				kd.r += n;
			}
		}
		if ((kd.g + n) < 1){
			if ((kd.g +n) >0 ){
				kd.g += n;
			}
		}
		if ((kd.b + n) < 1){
			if ((kd.b +n) >0 ){
				kd.b += n;
			}
		}
	}
	public void changeKS(float n){
		if ((ks.r + n) < 1){
			if ((ks.r +n) >0 ){
				ks.r += n;
			}
		}
		if ((ks.g + n) < 1){
			if ((ks.g +n) >0 ){
				ks.g += n;
			}
		}
		if ((ks.b + n) < 1){
			if ((ks.b +n) >0 ){
				ks.b += n;
			}
		}
	}
}