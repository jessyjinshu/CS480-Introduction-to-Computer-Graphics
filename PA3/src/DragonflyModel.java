
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.*;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;
/**
* @author Jinshu Yang U19470900 jinshuy@bu.edu
* @since Fall 2019
*/
/**
 * DragonflyModel.java - implement a DragonFlyModel
 */
public class DragonflyModel extends Component implements Animate{

	
	private Component head;
	private Component body;
	private Component leftwing1;
	private Component leftwing2;
	private Component rightwing1;
	private Component rightwing2;
//	private Component dragon;
	
	private double flopspeed = 5;
	private double flopflagLeft1 = -1;
	private double flopflagLeft2 = -1;
	private double flopflagRight1 = 1;
	private double flopflagRight2 = 1;
	private double rotateSpeed = 1;
	private double scale=1;
	
	private double dangerrange = 0.3;
	
	Vivarium vivarium;
	BirdModel bird;
	boolean isalive;
	
	private double foodF = 0.5;
	private double dragonF = 0.1;
	private double wallF = 0.01;
	private double preyF = 10;
	private double speedlimit = 0.02;
	double vx=0.1, vy=0.1, vz=0.1;
	
	
	
	public DragonflyModel(Point3D p, float scale,Vivarium vivarium) {
		super(new Point3D(p));
		
		this.scale = scale;
		this.vivarium = vivarium;
		this.isalive = true;
		//randomSpeed();
		
		Random rand = new Random();
		double dx = rand.nextDouble();
		double dy = rand.nextDouble();
		double dz = rand.nextDouble();
		this.setPosition(new Point3D(dx,dy,dz));
		
		//body part
		head = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,0.8f,0.9f,1f,-1.2,0.0,0.0));
		body = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,3f,0.8f,1f,0,0.0,0.0));
		leftwing1 = new Component(new Point3D(0,0,0), new SphereDisplayable(scale,0.5f,0.2f,2f,-0.5,0.0,1.0));
		leftwing2 = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,0.5f,0.2f,2f,-0.2,0.0,1.0));
		rightwing1 = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,0.5f,0.2f,2f,-0.5,0.0,-1.0));
		rightwing2 = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,0.5f,0.2f,2f,-0.2,0.0,-1.0));
		
		head.setColor(new FloatColor(0.3f, 0.6f, 1f));
		body.setColor(new FloatColor(0.3f, 0.6f, 1f));
		leftwing1.setColor(new FloatColor(0.3f, 0.6f, 1f));
		leftwing2.setColor(new FloatColor(0.3f, 0.6f, 1f));
		rightwing1.setColor(new FloatColor(0.3f, 0.6f, 1f));
		rightwing2.setColor(new FloatColor(0.3f, 0.6f, 1f));
		
		
		
		//this.dragon = dragon;
		this.addChild(body);
		body.addChildren(head,leftwing1,leftwing2,rightwing1,rightwing2);
		//this.setPosition(new Point3D(-1.2,0,0));
		//this.addChildren(head,body,leftwing1,leftwing2,rightwing1,rightwing2);
		//this.addChild(leftwing1);
		
//		this.setYNegativeExtent(-30);
//		this.setYPositiveExtent(30);
		
//		this.setExtentSwitch(false);
	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		if (config_list.size() > 1) {
			this.setConfiguration(config_list.get(0));
		}
	}
	
	public Component getBody() {
		return body;
	}

	
	//double vx=0.0, vy=0.0, vz=0.0;
	
    private Quaternion orientation = new Quaternion();
	
	private Point3D or_ori = new Point3D(-1,0,0);
	//private Point3D tar_ori = new Point3D(-1,0,0);
	
	
	
	@Override
	public void animationUpdate(GL2 gl) {
		Quaternion q;

		flopflagLeft1 = wingflopLeft(leftwing1,flopflagLeft1);
		flopflagLeft2 = wingflopLeft(leftwing2,flopflagLeft2);
		flopflagRight1 = wingflopRight(rightwing1,flopflagRight1);
		flopflagRight2 = wingflopRight(rightwing2,flopflagRight2);
		
		
		Point3D pos = this.position();

		//get orientation vector from the potential function
		Point3D tar_ori = PotentialField();
		Point3D wall_ori = PotentialFieldwall();
		//calculate the resulted velocity
		vx = vx+tar_ori.x()*0.01+wall_ori.x()*0.01;
		vy = vy+tar_ori.y()*0.01+wall_ori.y()*0.01;
		vz = vz+tar_ori.z()*0.01+wall_ori.z()*0.01; 
		
		//if exceeds the spacelimit,slowdown
		if(vx>=speedlimit)
		{
			vx = speedlimit;
		}
		else if(vx<=-speedlimit)
		{
			vx = -speedlimit;
		}
		
		if(vy>=speedlimit)
		{
			vy = speedlimit;
		}
		else if(vy<=-speedlimit)
		{
			vy = -speedlimit;
		}
		
		if(vz>=speedlimit)
		{
			vz = speedlimit;
		}
		else if(vz<=-speedlimit)
		{
			vz = -speedlimit;
		}
		
		//if touched the wall, bouce back
		if ( pos.x() - 1.2*this.scale < -2 || pos.x()+1.2*this.scale > 2) {
			vx = -vx;
		}
		if ( pos.y() - 1.2*this.scale < -2 || pos.y() + 1.2*this.scale > 2) {
			vy = -vy;
		}
		if ( pos.z() - 1.2*this.scale < -2 || pos.z() + 1.2*this.scale > 2) {
			vz = -vz;
		}
		//define the target orientation
		tar_ori = new Point3D(vx,vy,vz);
		

		
		//if need to rotate the direction of motion
		if(!or_ori.samePoint(tar_ori) && !tar_ori.samePoint(new Point3D(0.0,0.0,0.0)))
		{

			Point3D rotation_axis = or_ori.crossProduct(tar_ori);
			
			//if rotation 180 degrees
			if((rotation_axis.x()==0.0)&&(rotation_axis.y()==0.0)&&(rotation_axis.z()==0.0))
			{
				rotation_axis = new Point3D(0.0,-or_ori.z(),or_ori.y());
						
			}
			Point3D or_n = or_ori.normalize();
			Point3D tar_n = tar_ori.normalize();
			Point3D rotation_n = rotation_axis.normalize();
			double round_dot_product = Math.round( or_n.dotProduct(tar_n) * 10000000000.0 ) / 10000000000.0;
			double rotation_angle = Math.acos(or_n.dotProduct(tar_n));
			if(rotation_angle == 3.141592653589793)
			{
				float cos = (float)0.0;
				float sin = (float)1.0;
				q = new Quaternion((float)cos,(float)(sin*rotation_n.x()),(float)(sin*rotation_n.y()),(float)(sin*rotation_n.z()));
				
				
			}
			else if(round_dot_product == 1.0000000000 || round_dot_product==0.999999999)
			{
				float cos = 1;
				float sin = 0;
				q = new Quaternion((float)cos,(float)(sin*rotation_n.x()),(float)(sin*rotation_n.y()),(float)(sin*rotation_n.z()));
			}
			else
			{
				q = new Quaternion((float)Math.cos(rotation_angle/2),(float)(Math.sin(rotation_angle/2)*rotation_n.x()),(float)(Math.sin(rotation_angle/2)*rotation_n.y()),(float)(Math.sin(rotation_angle/2)*rotation_n.z()));
			}
				
			this.orientation = q.multiply(this.orientation);
			this.preMatrix = this.orientation.to_matrix();
			or_ori = tar_ori;
			
		
		}
		
		collsionDetection();
			
		this.setPosition(new Point3D(pos.x()+vx, pos.y()+vy, pos.z()+vz));

	}
	
	//the function to define the flopwings
	public double wingflopLeft(Component wing,double flopflagLeft) {
		double cur = wing.xAngle();
		double delta;
		if(cur>=30)
		{
			flopflagLeft = -flopflagLeft;
			delta = flopspeed*flopflagLeft;
		}
		else if(cur<=-30)
		{
			flopflagLeft = -flopflagLeft;
			delta = flopspeed*flopflagLeft;
		}
		else
		{
			delta = flopspeed*flopflagLeft;
		}
		wing.rotate(Axis.X,delta);	
		return flopflagLeft;
		
	}
	
	public double wingflopRight(Component wing,double flopflagRight) {
		double cur = wing.xAngle();
		double delta;
		if(cur>=30)
		{
			flopflagRight = -flopflagRight;
			delta = flopspeed*flopflagRight;
		}
		else if(cur<=-30)
		{
			flopflagRight = -flopflagRight;
			delta = flopspeed*flopflagRight;
		}
		else
		{
			delta = flopspeed*flopflagRight;
		}
		wing.rotate(Axis.X,delta);
		return flopflagRight;
		
	}
	
	//the function to detect collision with other creatures
	public void collsionDetection() {
		int l  = this.vivarium.vivarium.size();
		for(int i = 0;i<l;i++)
		{
			Component cur = this.vivarium.vivarium.get(i);
			if(cur instanceof food)
			{
				if(!((food) cur).iseaten) {
					if(this.position().distance(cur.position())<0.4)
					{
						((food) this.vivarium.vivarium.get(i)).iseaten = true;
					}		
				}
			}
			else if(cur instanceof BirdModel)
			{
				if(this.position().distance(cur.position())<0.6)
				{
					this.isalive = false;
				}
			}
			else if(cur instanceof Ant)
			{
				if(this.position().distance(cur.position())<0.6)
				{
					vx = 0;
					vy = 0;
					vz = 0;
				}		
				
			}
			
		}
		
	}
	//computer potential function with wall
	public Point3D PotentialFieldwall() {
		double tx = 0;
		double ty = 0;
		double tz = 0;
		
		double dx = this.position().x();
		double dy = this.position().y();
		double dz = this.position().z();
		
		Point3D w1 = new Point3D(2,dy,dz);
		Point3D w2 = new Point3D(-2,dy,dz);
		Point3D w3 = new Point3D(dx,2,dz);
		Point3D w4 = new Point3D(dx,-2,dz);
		Point3D w5 = new Point3D(dx,dy,2);
		Point3D w6 = new Point3D(dx,dy,-2);
		
		ArrayList<Point3D> walls = new ArrayList<Point3D>();
		walls.add(w1);
		walls.add(w2);
		walls.add(w3);
		walls.add(w4);
		walls.add(w5);
		walls.add(w6);
		
		for(int i = 0;i<walls.size();i++)
		{
			Point3D p = potentialFunction(walls.get(i),true,wallF);
			tx = tx + p.x();
			ty = ty + p.y();
			tz = tz + p.z();
			
		}
		
		Point3D t = new Point3D(tx,ty,tz);
		return t;
		
	}
	//compute the potential function with other creature
	public Point3D PotentialField() {
		double tx = 0;
		double ty = 0;
		double tz = 0;
		
		int l = this.vivarium.vivarium.size();
		for(int i = 0;i<l;i++)
		{
			Component cur = this.vivarium.vivarium.get(i);
			
			if(cur instanceof food)
			{
				if(!((food) cur).iseaten) {
					Point3D ret = potentialFunction(((food) cur).position(),false,foodF);
					tx = tx + ret.x();
					ty = ty + ret.y();
					tz = tz + ret.z();
					
				}
			}
			else if (cur instanceof DragonflyModel)
			{
				
				Point3D ret = potentialFunction(((DragonflyModel) cur).position(),true,dragonF);
				if(ret.samePoint(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)))
				{
					ret = new Point3D(0,0,0);
				}
				else
				{
					tx = tx + ret.x();
					ty = ty + ret.y();
					tz = tz + ret.z();
				}

			}
			else if (cur instanceof BirdModel)
			{
				if(cur.position().distance(this.position())<dangerrange)
				{
					Point3D ret = potentialFunction(((BirdModel) cur).position(),true,preyF);
					if(ret.samePoint(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)))
					{
						ret = new Point3D(0,0,0);
					}
					else
					{
						tx = tx + ret.x();
						ty = ty + ret.y();
						tz = tz + ret.z();
					}
				}
			}
		}
		Point3D total = new Point3D(tx,ty,tz);
		
		return total;
	}
	
	//function to computer the potential function
	public Point3D potentialFunction(Point3D p,boolean isrunaway,double F) {
		int runaway_flag = 1;
		Point3D pos = this.position();
		
		double rx;
		double ry;
		double rz;
		if(!isrunaway)
		{
			runaway_flag = -1;
		}
		rx = F*runaway_flag*(pos.x()-p.x())* Math.pow(Math.E, -(Math.pow(pos.x()-p.x(), 2))*Math.pow(pos.y()-p.y(), 2)*Math.pow(pos.z()-p.z(), 2));
		ry = F*runaway_flag*(pos.y()-p.y())* Math.pow(Math.E, -(Math.pow(pos.x()-p.x(), 2))*Math.pow(pos.y()-p.y(), 2)*Math.pow(pos.z()-p.z(), 2));
		rz = F*runaway_flag*(pos.z()-p.z())* Math.pow(Math.E, -(Math.pow(pos.x()-p.x(), 2))*Math.pow(pos.y()-p.y(), 2)*Math.pow(pos.z()-p.z(), 2));
		
		Point3D ret = new Point3D(rx,ry,rz);
		//ret = ret.normalize();
		return ret;
	}
	

	
	
	
}

