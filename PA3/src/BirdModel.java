 /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.*;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;
/**
 * BirdModel.java - an object implement the BirdModel
 */
public class BirdModel extends Component implements Animate{

	//body parts of BirdModel
	private Component head;
	private Component antenna1;
	private Component antenna2;
	private Component leftwing;
	private Component rightwing;

	//the speed of wing flop
	private double flopspeed = 5;
	private double flopflagLeft = -1;
	private double flopflagRight = 1;

	//default scale
	private double scale=1;
	
	//vivarium object
	Vivarium vivarium;
	
	//the scale vector of potential function
	private double preyF = 5;
	private double foodF = 0.5;
	private double wallF = 0.01;
	private double speedlimit = 0.02;
	
	
	//the constructor class of BirdModel
	public BirdModel(Point3D p, float scale,Vivarium vivarium) {
		super(new Point3D(p));
		
		this.scale = scale;
		this.vivarium = vivarium;
		
		
		//body part
		head = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,1.4f,1.4f,2f,-0.5,0.0,0.0));
		antenna1 = new Component(new Point3D(0, 0.3*scale, 0.2*scale), new ConeDisplayable(scale,1f,1f,1f,-0.5,0.0,0.0));
		antenna2 = new Component(new Point3D(0, 0.3*scale, -0.2*scale), new ConeDisplayable(scale,1f,1f,1f,-0.5,0.0,0.0));
		leftwing = new Component(new Point3D(-0.3*scale,0,0), new SphereDisplayable(scale,1.2f,0.2f,1.3f,0.1,0.0,1.2));
		rightwing = new Component(new Point3D(-0.3*scale, 0, 0), new BirdDisplayable(scale,1.2f,0.2f,1.3f,0.1,0.0,-1.2));

		//rotate each part
		antenna1.rotate(Axis.X, -80);
		antenna2.rotate(Axis.X, -100);
		
		leftwing.rotate(Axis.Z, 90);
		leftwing.rotate(Axis.Y, 45);
		
		rightwing.rotate(Axis.Z, 90);
		rightwing.rotate(Axis.Y, -45);
		
		//add the hierachical order
		this.addChildren(head);
		head.addChildren(antenna1,antenna2,leftwing,rightwing);

	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		if (config_list.size() > 1) {
			this.setConfiguration(config_list.get(0));
		}
	}
	public Component getBird() {
		return head;
	}
	
	//initialize the velocity
	double vx=0, vy=0, vz=0;
	//initialize the orientation
    private Quaternion orientation = new Quaternion();
	//the original orientation of the model
	private Point3D or_ori = new Point3D(-1,0,0);

	
	@Override
	public void animationUpdate(GL2 gl) {
		Quaternion q;
		
		//animation of flopping wings
		flopflagLeft = wingflopLeft(leftwing,flopflagLeft);
		flopflagRight = wingflopRight(rightwing,flopflagRight);

		
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
			vx = speedlimit-0.01;
		}
		else if(vx<=-speedlimit)
		{
			vx = -speedlimit+0.01;
		}
		
		if(vy>=speedlimit)
		{
			vy = speedlimit-0.01;
		}
		else if(vy<=-speedlimit)
		{
			vy = -speedlimit+0.01;
		}
		
		if(vz>=speedlimit)
		{
			vz = speedlimit-0.01;
		}
		else if(vz<=-speedlimit)
		{
			vz = -speedlimit+0.01;
		}
		
		//if touched the wall, bouce back
		if ( pos.x() - 2*this.scale < -2 || pos.x()+2*this.scale > 2) {
			vx = -vx;
		}
		if ( pos.y() - 2*this.scale < -2 || pos.y() + 2*this.scale > 2) {
			vy = -vy;
		}
		if ( pos.z() - 2*this.scale < -2 || pos.z() + 2*this.scale > 2) {
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
			double rotation_angle = Math.acos(or_n.dotProduct(tar_n)); //get the rotation angle
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
		collisionDetection();
		this.setPosition(new Point3D(pos.x()+vx, pos.y()+vy, pos.z()+vz));
	}
	
	//function to detect the collision of other objects
	public void collisionDetection() {
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
	
	public double wingflopLeft(Component wing,double flopflagLeft) {
		double cur = wing.yAngle();
		double delta;
		if(cur>=60)
		{
			flopflagLeft = -flopflagLeft;
			delta = flopspeed*flopflagLeft;
		}
		else if(cur<=30)
		{
			flopflagLeft = -flopflagLeft;
			delta = flopspeed*flopflagLeft;
		}
		else
		{
			delta = flopspeed*flopflagLeft;
		}
		wing.rotate(Axis.Y,delta);	
		return flopflagLeft;
		
	}
	
	public double wingflopRight(Component wing,double flopflagRight) {
		double cur = wing.yAngle();
		double delta;
		if(cur>=-30)
		{
			flopflagRight = -flopflagRight;
			delta = flopspeed*flopflagRight;
		}
		else if(cur<=-60)
		{
			flopflagRight = -flopflagRight;
			delta = flopspeed*flopflagRight;
		}
		else
		{
			delta = flopspeed*flopflagRight;
		}
		wing.rotate(Axis.Y,delta);
		return flopflagRight;
		
	}
	//computer potential function with walls
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
			
			if(cur instanceof DragonflyModel)
			{
				if(((DragonflyModel) cur).isalive) {
					Point3D ret = potentialFunction(((DragonflyModel) cur).position(),false,preyF);
					tx = tx + ret.x();
					ty = ty + ret.y();
					tz = tz + ret.z();
					
				}
			}
			else if(cur instanceof food)
			{
				if(!((food) cur).iseaten) {
					Point3D ret = potentialFunction(((food) cur).position(),false,foodF);
					tx = tx + ret.x();
					ty = ty + ret.y();
					tz = tz + ret.z();
					
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
		//System.out.println(pos);
		
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
