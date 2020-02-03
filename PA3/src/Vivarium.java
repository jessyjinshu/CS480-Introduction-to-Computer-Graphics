 /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
import javax.media.opengl.*;

import com.jogamp.opengl.util.*;
import java.util.*;
/**
 * Vivarium.java - draw the creatures in the Tank
 */
public class Vivarium implements Displayable, Animate {
	private Tank tank;
	public ArrayList<Component> vivarium = new ArrayList<Component>();
	public ArrayList<Component> newadd = new ArrayList<Component>();

	public Vivarium() {
		tank = new Tank(4.0f, 4.0f, 4.0f);
		//initalize creature here
		vivarium.add(new food(new Point3D(0, 1, 0), 0.2f));
		vivarium.add(new food(new Point3D(0, 1, 0), 0.2f));
		vivarium.add(new food(new Point3D(0, 1, 0), 0.2f));
		vivarium.add(new DragonflyModel(new Point3D(0, 0, 0), 0.1f,this));
		vivarium.add(new DragonflyModel(new Point3D(0, 0, 0), 0.1f,this));
//		vivarium.add(new DragonflyModel(new Point3D(0, 0, 0), 0.2f,this));
//		vivarium.add(new DragonflyModel(new Point3D(0, 0, 0), 0.2f,this));
//		vivarium.add(new DragonflyModel(new Point3D(0, 0, 0), 0.2f,this));
		vivarium.add(new BirdModel(new Point3D(-1, -1, -1), 0.3f,this));
		vivarium.add(new Ant(new Point3D(0, 0, 0),"ant",this));
	}

	public void initialize(GL2 gl) {
		tank.initialize(gl);
		for (Component object : vivarium) {
			object.initialize(gl);
		}
	}

	public void update(GL2 gl) {
		tank.update(gl);
		for (Component object : vivarium) {
			object.update(gl);
		}
	}

	public void draw(GL2 gl) {
		tank.draw(gl);
		for (Component object : vivarium) {
			if(object instanceof food)
			{
				if(!((food) object).iseaten)
				{
					object.draw(gl);
				}
			}
			else if(object instanceof DragonflyModel)
			{
				if(((DragonflyModel) object).isalive)
				{
					object.draw(gl);
				}
			}
			else 
			{
				object.draw(gl);
			}

			
		}
	}
	
	//function to add food
	public void addFood() {
		System.out.println("here");
		Component newfood = new food(new Point3D(0, 1, 0), 0.2f);
		vivarium.add(newfood);
		newadd.add(newfood);

		System.out.println("size"+vivarium.size());
		
	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		// assign configurations in config_list to all Components in here
	}

	@Override
	public void animationUpdate(GL2 gl) {
		for (Component example : vivarium) {
			if (example instanceof Animate) {
				((Animate) example).animationUpdate(gl);
			}
		}
	}
}
