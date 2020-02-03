//****************************************************************************
//      Scene class----the class to display different scenes in the file
//****************************************************************************
// 
//    Dec 9, 2019 Created by Jinshu Yang
//

import java.awt.image.BufferedImage;
import java.util.*;

public class Scene{
	
	public Vector3D cameraP ;
	//shaded type
	private final String Flat = "Flat";
	private final String Phong = "Phong";
	private final String Gouraud = "Gouraud";
	private String shadedType;
	
	//sphere
	private Sphere3D sphere, sphere1,sphere2,sphere3,sphere4,sphere5;

	private float sphere_Radius = 50;
	private int Nsteps,Msteps;
	//torus
	private Torus3D torus, torus1, torus2;
	private float torus_radius = 25;
	private int sphereR, torusR;
	//cylinder
	private Cylinder3D cylinder;
	private int cylinderR = 20;
	private int cylinderH = 100;
	//box
	private Box3D box, box1, box2, box3;
	private float length = 50;
	private float width = 50;
	private float height = 50;
	
	//Superellipsoid
	private Superellipsoid3D superellipsoid;
	//ellipsoid
	float radius = (float)50.0;
	private Ellipsoid3D ellipsoid;
	private float rx = (float)1.0*radius;
	private float ry =(float)1.2*radius;
	private float rz = (float)1.3*radius;
	
	
	private Vector3D view_center = lab9.viewing_center;
	//materials for all objectss
	private ColorType torus_ka, sphere_ka, torus_kd, sphere_kd, torus_ks, sphere_ks, cylinder_ka,cylinder_kd,cylinder_ks,box_ka,box_kd,box_ks;
	private ColorType ellipsoid_ka, ellipsoid_kd, ellipsoid_ks;
	private Material matsSphere, matsTorus,matsCylinder,matsBox,matsEllipsoid;
	private int ns;
	//light
	private ColorType light_color1;
	private Vector3D light_direction1;
	
	private ColorType light_color2;
	private Vector3D light_direction2;
	
	private ColorType light_color3;
	private Vector3D light_direction3;
	private Vector3D light_location3;
	
	private InfiniteLight light1;
	private InfiniteLight light2;
	private InfiniteLight light3;
	
	private ArrayList<InfiniteLight> lights;
	private ArrayList<Surface> surfaces;
	private ArrayList<Material> mats;
	private Boolean doSmooth = true;
	
	private float cameraX;
	private float cameraY;
	private float cameraZ;
	
	public Scene(int _Nsteps, int _Msteps){
		
		
		shadedType = Phong;
		
		Nsteps = _Nsteps;
		Msteps = _Msteps;
	
		//initialize mats
		torus_ka = new ColorType(1.0f,0.5f,0.1f);
		sphere_ka = new ColorType(0.8f,0.8f,0.5f);
		cylinder_ka = new ColorType(0.1f,0.1f,0.8f);
		box_ka = new ColorType(0.1f,0.1f,0.1f);
		ellipsoid_ka =  new ColorType(0.8f,0.1f,0.5f);
		
		torus_kd = new ColorType(0.0f,0.5f,0.9f);
		sphere_kd = new ColorType(0.9f,0.3f,0.1f);
		cylinder_kd = new ColorType(0.6f,0.5f,0.5f);
		box_kd = new ColorType(0.3f,0.8f,0.8f);
		ellipsoid_kd = new ColorType(0.2f,0.3f,0.5f);
		
		torus_ks = new ColorType(1.0f,1.0f,1.0f);
		sphere_ks = new ColorType(0.5f,0.5f,0.5f);
		cylinder_ks = new ColorType(1.0f,1.0f,1.0f);
		box_ks = new ColorType(1.0f,1.0f,1.0f);
		ellipsoid_ks = new ColorType(1.0f,1.0f,1.0f);
		
		
		
		
		mats = new ArrayList<Material>();
		matsSphere = new Material(sphere_ka, sphere_kd, sphere_ks, ns);
		matsTorus = new Material(torus_ka, torus_kd,torus_ks,ns);
		matsCylinder = new Material(cylinder_ka,cylinder_kd,cylinder_ks,ns);
		matsBox = new Material(box_ka,box_kd,box_ks,ns);
		matsEllipsoid = new Material(ellipsoid_ka,ellipsoid_kd,ellipsoid_ks,ns);
		
		mats.add(matsSphere);
		mats.add(matsTorus);
		mats.add(matsCylinder);
		mats.add(matsBox);
		mats.add(matsEllipsoid);

		
		
		
		
		}
	//second scene
	public void setScene1(){
		//initialize light
		light_color1 = new ColorType(0.8f,0.8f,0.8f);
		light_direction1 = new Vector3D((float)(0),(float)(-1.0/Math.sqrt(2.0)),(float)(1.0/Math.sqrt(2.0)));
		light1 = new InfiniteLight(light_color1);
		light1.InfiLight(light_direction1);
		
		//initialize light
		light_color2 = new ColorType(1f,1f,1f);
		light_direction2 = new Vector3D((float)(150f),(float)(250f),(float)(50f));
		light2 = new InfiniteLight(light_color2);
		light2.PointLight(light_direction2);
		
		light_color3 = new ColorType(1f,1f,1f);
		light_direction3 = new Vector3D((float)(0),(float)(-1.0/Math.sqrt(2.0)),(float)(-1.0/Math.sqrt(2.0)));
		light_location3 = new Vector3D((float)(200f),(float)(100f),(float)(200f));
		light3 = new InfiniteLight(light_color3);
		light3.SpotLight(light_location3,light_direction3);
		
		//initialize objects
		cylinder = new Cylinder3D((float)200, (float)140, (float)60.0, (float)1.5*cylinderR,(float) cylinderH*2, Nsteps, Msteps,mats.get(2));
		cylinder.RotateY((float)Math.PI/2);
		box = new Box3D((float)150, (float)250, (float)140, height, length,width,mats.get(3));
		superellipsoid = new Superellipsoid3D((float)350+cameraX, (float)300+cameraY, (float)250+cameraZ,rx,ry,rx,Nsteps,Nsteps,mats.get(4));


		surfaces = new ArrayList<Surface>();
		surfaces.add(superellipsoid.getSurface());

		
		for (int i = 0; i <cylinder.getSurface().size();i++ ){
			surfaces.add(cylinder.getSurface().get(i));
		}
		for (int i = 0; i <box.getSurface().size();i++ ){
					surfaces.add(box.getSurface().get(i));
				}

	}
	
	//first scene
	public void setScene0(){
		//initialize light
		light_color1 = new ColorType(1.0f,1.0f,1.0f);
		light_direction1 = new Vector3D((float)(0),(float)(-1.0/Math.sqrt(2.0)),(float)(1.0/Math.sqrt(2.0)));
		light1 = new InfiniteLight(light_color1);
		light1.InfiLight(light_direction1);
		
		//initialize light
		light_color2 = new ColorType(1f,1f,1f);
		light_direction2 = new Vector3D((float)(100f),(float)(200f),(float)(30f));
		light2 = new InfiniteLight(light_color2);
		light2.PointLight(light_direction2);
		
		light_color3 = new ColorType(0f,0f,1f);
		light3 = new InfiniteLight(light_color3);
		light3.AmbLight();
		
		
		surfaces = new ArrayList<Surface>();
		
		//initialize objects
		torus = new Torus3D((float)250.0+cameraX, (float)250.0+cameraY, (float)0.0+cameraZ, (float)1*torus_radius, (float)1.5*torus_radius, Nsteps, Nsteps,mats.get(1));
		ellipsoid = new Ellipsoid3D((float)100+cameraX, (float)100+cameraY, (float)0+cameraZ,rx,ry,rz,Nsteps,Nsteps,mats.get(4));
		box = new Box3D((float)400+cameraX, (float)400+cameraY, (float)0+cameraZ, height, length,width,mats.get(3));
		sphere = new Sphere3D((float)100.0+cameraX, (float)400.0+cameraY, (float)0.0+cameraZ, (float)1*sphere_Radius, Nsteps, Msteps,mats.get(0));
		cylinder = new Cylinder3D((float)400.0+cameraX, (float)100.0+cameraY, (float)0.0+cameraZ, (float)1.5*cylinderR,(float) cylinderH, Nsteps, Msteps,mats.get(2));
		//superellipsoid = new Superellipsoid3D((float)100+cameraX, (float)250+cameraY, (float)0+cameraZ,rx,rx,rx,Nsteps,Nsteps,mats.get(4));
		cylinder.RotateX((float)Math.PI/2);
		
		surfaces.add(ellipsoid.getSurface());
		//surfaces.add(superellipsoid.getSurface());
		surfaces.add(sphere.getSurface());
		surfaces.add(torus.getSurface());
		
		for (int i = 0; i <box.getSurface().size();i++ ){
			surfaces.add(box.getSurface().get(i));
		}

		for (int i = 0; i <cylinder.getSurface().size();i++ ){
			surfaces.add(cylinder.getSurface().get(i));
		}
		
	}
	
	//third scene
	public void setScene2(){
		//initializa lights
		light_color1 = new ColorType(1.0f,1.0f,1.0f);
		light_direction1 = new Vector3D((float)(0),(float)(-1.0/Math.sqrt(2.0)),(float)(1.0/Math.sqrt(2.0)));
		light1 = new InfiniteLight(light_color1);
		light1.InfiLight(light_direction1);
		

		light_color2 = new ColorType(0.8f,0.8f,0.3f);
		light_direction2 = new Vector3D((float)(100f),(float)(200f),(float)(30f));
		light2 = new InfiniteLight(light_color2);
		light2.PointLight(light_direction2);
		

		
		
		//initialize objects
		surfaces = new ArrayList<Surface>();
		torus = new Torus3D((float)256.0, (float)250.0, (float)256.0, (float)1*torus_radius, (float)3*torus_radius, Nsteps, Nsteps,mats.get(1));
		torus1 = new Torus3D((float)256.0, (float)190.0, (float)256.0, (float)1*torus_radius, (float)3*torus_radius, Nsteps, Nsteps,mats.get(1));
		torus2 = new Torus3D((float)256.0, (float)310.0, (float)256.0, (float)1*torus_radius, (float)3*torus_radius, Nsteps, Nsteps,mats.get(1));
		sphere1 = new Sphere3D((float)128.0, (float)270.0, (float)128.0, (float)1*sphere_Radius, Nsteps, Msteps,mats.get(0));

		cylinder = new Cylinder3D((float)256.0, (float)256.0, (float)256.0, (float)1.5*cylinderR,(float) cylinderH*3, Nsteps, Msteps,mats.get(2));
		cylinder.RotateX((float)Math.PI/2);
		torus.RotateX((float)Math.PI/2);
		torus1.RotateX((float)Math.PI/2);
		torus2.RotateX((float)Math.PI/2);
		
		surfaces.add(sphere1.getSurface());
		surfaces.add(torus.getSurface());
		surfaces.add(torus1.getSurface());
		surfaces.add(torus2.getSurface());
		for (int i = 0; i <cylinder.getSurface().size();i++ ){
			surfaces.add(cylinder.getSurface().get(i));
		}
	}
	
	//set Nsteps
	public void setNsteps(int N){
		Nsteps = N;
		Msteps = N;
	}
	
	//draw all the surfaces
	public void drawScene(){
		
		
		for (int i = 0; i < surfaces.size();i++){
		//	System.out.println("1");
			//surfaces.get(i).setViewDirc(view_center, cameraP);
			surfaces.get(i).drawSurface(lights,shadedType);
		}
	}
	
	//set NS
	public void setNS( int _ns){
		for (int i = 0; i < mats.size();i++){
			mats.get(i).setNS(_ns);
		}
	}
	
	public void translate(float x,float y,float z){
		cameraX = x;
		cameraY = y;
		cameraZ = z;
	}
	
	public void Scale(float x,float y,float z){
		for (int i= 0; i < surfaces.size();i++){
		
			surfaces.get(i).Scaling(x,y,z);
			
			
			
		}
	}
	
	public void Rotate(float x,float y,float z){
		for (int i= 0; i < surfaces.size();i++){
		
			surfaces.get(i).RotateX(x);
			surfaces.get(i).RotateY(y);
			surfaces.get(i).RotateZ(z);
			
			
			
		}
	}
	
	//camera rotation
	public void cameraRotate(Vector3D view, Quaternion vq){
		//cameraP.rotateVector(vq, view);
		
		for (int i = 0; i< lights.size();i++){
			lights.get(i).rotateL(vq, view);
		}
		for (int i= 0; i < surfaces.size();i++){
			surfaces.get(i).mesh.rotateMesh(vq, view);
		}
		
		
	}
		
	
	//set materials
	public void setSDA(boolean _s, boolean _d, boolean _a){

		for (int i = 0; i < mats.size();i++){
			mats.get(i).setSDA(_s, _d, _a);
		}
	}
	
	//decide shading type
	public void setShaded(String type){
		shadedType = type;
	}
	
	//set light
	public void setLight(boolean l1, boolean l2, boolean l3){
		lights = new ArrayList<InfiniteLight>();

		if (l1){
			lights.add(light1);
		}
		if (l2){
			lights.add(light2);
			
		}

	}
	//change ka of all material
	public void changeKA(boolean t){
		for (int i =0 ; i< mats.size();i++){
			if (t){
			mats.get(i).changeKA(-0.05f);}
			else{
				mats.get(i).changeKA(0.05f);
			}
			
		}
	}
	//change kd of all material
	public void changeKD(boolean t){
		for (int i =0 ; i< mats.size();i++){
			if (t){
			mats.get(i).changeKD(-0.05f);}
			else{
				mats.get(i).changeKD(0.05f);
			}
			
		}
	}
	//change ks of all material
	public void changeKS(boolean t){
		for (int i =0 ; i< mats.size();i++){
			if (t){
			mats.get(i).changeKS(-0.05f);}
			else{
				mats.get(i).changeKS(0.05f);
			}
			
		}
	}
}
	
	
		
	
	

	       
	       
