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
 * food.java - implement a foodModel
 */
public class food extends Component implements Animate{

	private Component food;

	private double scale=1;
	private double x;
	private double y;
	private double z;
	boolean iseaten = false;
	
	
	
	public food(Point3D p, float scale) {
		super(new Point3D(p));
		
		this.scale = scale;
		Random rand = new Random(); 

		x = rand.nextDouble();
		z = rand.nextDouble();

		
		food = new Component(new Point3D(0, 0, 0), new SphereDisplayable(scale,2*scale,2*scale,2*scale,x,0,z));
		this.setPosition(new Point3D(x,1.0,z));
		this.addChild(food);

		
		food.setColor(new FloatColor(0.3f, 0.6f, 1f));


	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		if (config_list.size() > 1) {
			this.setConfiguration(config_list.get(0));
		}
	}
	
	public Component getFood() {
		return food;
		
	}
	
	//initialize the velocity
	double vx=0.00, vy=-0.02, vz=0.00;
	
	@Override
	public void animationUpdate(GL2 gl) {
	
		
		Point3D pos = this.position();

		//if touch ground,stop
		if ( pos.y()  < -1.9) {
			vy = 0.0;
		}

	

		this.setPosition(new Point3D(pos.x()+vx, pos.y()+vy, pos.z()+vz));
		
	}
	

	
}
