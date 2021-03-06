
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jogamp.opengl.util.gl2.GLUT;

/**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */

public class Ant extends Component implements Animate, Selection{
	/** The OpenGL utility toolkit object. */
	private final GLUT glut = new GLUT();
	
	
	/** The head to be modeled. */
	private final Component head;
	/** The body to be modeled. */
	private final Component body;
	/** The tail to be modeled. */
	private final Component tail;
	/** The midbody to be modeled. */
	private final Component midbody;

	/** The leg on the Ant to be modeled. */
	private final Leg[] legs;
	
	/** The Antenna on the Ant to be modeled. */
	private final Antenna[] antennas;
	
	/** The part of Antennas. */
	private final Component antenna1_top;
	private final Component antenna2_top;
	private final Component antenna1_end;
	private final Component antenna2_end;
	
	/** The camera of the view point. */
	private final Component camera;
	

	
	/** The set of all components. */
	private final List<Component> components;
	/** The set of components which are currently selected for rotation. */
	final Set<Component> selectedComponents = new HashSet<Component>(18);
	/** The set of legs which are currently selected for rotation. */
	final Set<Leg> selectedLegs = new HashSet<Leg>(22);
	/** The set of Antennas which are currently selected for rotation. */
	final Set<Antenna> selectedAntennas = new HashSet<Antenna>(4);
	
	/** The set of components which contains the left legs. */
	private final List<Component> leftcom = new ArrayList<Component>();
	/** The set of components which contains the right legs. */
	private final List<Component> rightcom = new ArrayList<Component>();
	
	/** The color for components which are selected for rotation. */
	public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  	/** The color for components which are not selected for rotation. */
	public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
	
	


	/** The radius and height of the components which comprise the head. */
	public static final double HEAD_RADIUS = 0.4;
	public static final double HEAD_HEIGHT = 0.4;
	
	/** The radius and height of the components which comprise the body. */
	public static final double BODY_HEIGHT = 0.75;
	public static final double BODY_RADIUS = 0.4;
	
	/** The radius and height of the components which comprise the middle body. */
	public static final double MIDBODY_HEIGHT = 0.75;
	public static final double MIDBODY_RADIUS = 0.4;
	
	/** The radius and height of the components which comprise the tail. */
	public static final double TAIL_HEIGHT = 0.75;
	public static final double TAIL_RADIUS = 0.4;
	
	/** The radius and height of the components which comprise the top part of the leg(close to body). */
	public static final double LEG_TOP_RADIUS = 0.03;
	public static final double LEG_TOP_HEIGHT = 0.4;
	
	/** The radius and height of the components which comprise the middle part of the leg. */
	public static final double LEG_MIDDLE_RADIUS = 0.02;
	public static final double LEG_MIDDLE_HEIGHT = 0.4;
	
	/** The radius and height of the components which comprise the end part of the leg. */
	public static final double LEG_END_RADIUS = 0.015;
	public static final double LEG_END_HEIGHT = 0.1;
	
	/** The radius and height of the components which comprise the top part of Antenna(close to head). */
	public static final double ANTENNA_TOP_RADIUS = 0.02;
	public static final double ANTENNA_TOP_HEIGHT = 0.4;
	
	/** The radius and height of the components which comprise the end part of Antenna. */
	public static final double ANTENNA_END_RADIUS = 0.01;
	public static final double ANTENNA_END_HEIGHT = 0.2;
	
	/** The initial position */
	public static final Point3D INITIAL_POSITION = new Point3D(0, 0, 1);
	
	
	
	
	public static final String HEAD_NAME = "head" ;
	public static final String BODY_NAME = "body" ;
	public static final String MIDBODY_NAME = "midbody" ;

	public static final String TAIL_NAME = "tail" ;
	
	public static final String ANTENNA1_TOP_NAME = "antena1 top";
	public static final String ANTENNA2_TOP_NAME = "antenna2 top";
	
	public static final String ANTENNA1_END_NAME = "antena1 end";
	public static final String ANTENNA2_END_NAME = "antenna2 end";
	
	public static final String CAMERA_NAME = "camera";
	
  	public static final String LEFT_LEG1_TOP_NAME = "leftleg1 top";
  	public static final String LEFT_LEG1_MIDDLE_NAME = "leftleg1 middle";
  	public static final String LEFT_LEG1_END_NAME = "leftleg1 end";
  	
  	public static final String LEFT_LEG2_TOP_NAME = "leftleg2 top";
  	public static final String LEFT_LEG2_MIDDLE_NAME = "leftleg2 middle";
  	public static final String LEFT_LEG2_END_NAME = "leftleg2 end";
  	
  	public static final String LEFT_LEG3_TOP_NAME = "leftleg3 top";
  	public static final String LEFT_LEG3_MIDDLE_NAME = "leftleg3 middle";
  	public static final String LEFT_LEG3_END_NAME = "leftleg3 end";
  	
  	public static final String RIGHT_LEG1_TOP_NAME = "rightleg1 top";
  	public static final String RIGHT_LEG1_MIDDLE_NAME = "rightleg1 middle";
  	public static final String RIGHT_LEG1_END_NAME = "rightleg1 end";
  	
  	public static final String RIGHT_LEG2_TOP_NAME = "rightleg2 top";
  	public static final String RIGHT_LEG2_MIDDLE_NAME = "rightleg2 middle";
  	public static final String RIGHT_LEG2_END_NAME = "rightleg2 end";
  	
  	public static final String RIGHT_LEG3_TOP_NAME = "rightleg3 top";
  	public static final String RIGHT_LEG3_MIDDLE_NAME = "rightleg3 middle";
  	public static final String RIGHT_LEG3_END_NAME = "rightleg3 end";
  	
  	
  	

  	
  	/** The function which takes the component number and maps to the component */
  	private Component mapNum2Component(int componentNum) {
  		switch(componentNum) {
  			case  0: return this.midbody;
			case  1: return this.body;
			case  2: return this.head;
			case  3: return this.tail;
			case  4: return this.legs[0].endJoint();
			case  5: return this.legs[0].middleJoint();
			case  6: return this.legs[0].topJoint();
			case  7: return this.legs[1].endJoint();
			case  8: return this.legs[1].middleJoint();
			case  9: return this.legs[1].topJoint();
			case 10: return this.legs[2].endJoint();
			case 11: return this.legs[2].middleJoint();
			case 12: return this.legs[2].topJoint();
			case 13: return this.legs[3].endJoint();
			case 14: return this.legs[3].middleJoint();
			case 15: return this.legs[3].topJoint();
			case 16: return this.legs[4].endJoint();
			case 17: return this.legs[4].middleJoint();
			case 18: return this.legs[4].topJoint();
			case 19: return this.legs[5].endJoint();
			case 20: return this.legs[5].middleJoint();
			case 21: return this.legs[5].topJoint();
			case 22: return this.antenna1_top;
			case 23: return this.antenna1_end;
			case 24: return this.antenna2_top;
			case 25: return this.antenna2_end;
			default: throw new IllegalArgumentException("componentNum over index"); 
  		}
  	}
  	  	
  	/** The function which takes the component name and maps to the component. */
  	private Component mapName2Component(String componentName) {
  		switch(componentName) {
	  		case  HEAD_NAME: return this.head;
			case  BODY_NAME: return this.body;
			case  MIDBODY_NAME: return this.midbody;
			case  TAIL_NAME: return this.tail;
			case  LEFT_LEG1_TOP_NAME: return this.legs[0].topJoint();
			case  LEFT_LEG1_MIDDLE_NAME: return this.legs[0].middleJoint();
			case  LEFT_LEG1_END_NAME: return this.legs[0].endJoint();
			case  LEFT_LEG2_TOP_NAME: return this.legs[1].topJoint();
			case  LEFT_LEG2_MIDDLE_NAME: return this.legs[1].middleJoint();
			case  LEFT_LEG2_END_NAME: return this.legs[1].endJoint();
			case  LEFT_LEG3_TOP_NAME: return this.legs[2].topJoint();
			case  LEFT_LEG3_MIDDLE_NAME: return this.legs[2].middleJoint();
			case  LEFT_LEG3_END_NAME: return this.legs[2].endJoint();
			case  RIGHT_LEG1_TOP_NAME: return this.legs[3].topJoint();
			case  RIGHT_LEG1_MIDDLE_NAME: return this.legs[3].middleJoint();
			case  RIGHT_LEG1_END_NAME: return this.legs[3].endJoint();
			case  RIGHT_LEG2_TOP_NAME: return this.legs[4].topJoint();
			case  RIGHT_LEG2_MIDDLE_NAME: return this.legs[4].middleJoint();
			case  RIGHT_LEG2_END_NAME: return this.legs[4].endJoint();
			case  RIGHT_LEG3_TOP_NAME: return this.legs[5].topJoint();
			case  RIGHT_LEG3_MIDDLE_NAME: return this.legs[5].middleJoint();
			case  RIGHT_LEG3_END_NAME: return this.legs[5].endJoint();
			case ANTENNA1_TOP_NAME: return this.antenna1_top;
			case ANTENNA1_END_NAME: return this.antenna1_end;
			case ANTENNA2_TOP_NAME: return this.antenna2_top;
			case ANTENNA2_END_NAME: return this.antenna2_end;
			default: throw new IllegalArgumentException("componentName doesn't exist");
  		}
  	}
  	
  	public void setModelStates(final ArrayList<Configuration> config_list) {
  		for (int i = 0; i < config_list.size(); i++) {
  			if ( 0 <= i && i <= 25) {
  				mapNum2Component(i).setAngles(config_list.get(i));
  			}
  		}
  	}
  	
  	public void setModelStates(final Map<String, Configuration> state) {
  		for (Map.Entry<String, Configuration> entry: state.entrySet()) {
  			this.mapName2Component(entry.getKey()).setAngles(entry.getValue());
  		}
  	}
  	
  	/**
     * Prints the joints on the specified PrintStream.
     * 
     * @param printStream
     *          The stream on which to print each of the components.
     */
    public void printJoints(final PrintStream printStream) {
      for (final Component component : this.components) {
        printStream.println(component);
      }
    }

    /** The function select components into the selectedComponent list */
  	public void toggleSelection(int selectionNum) {
  		if ( 0 <= selectionNum && selectionNum <= 25) {
  			Component component = mapNum2Component(selectionNum);
  			if ( this.selectedComponents.contains(component) ) {
  				this.selectedComponents.remove(component);
  				component.setColor(INACTIVE_COLOR);
  			}
  			else {
  		      this.selectedComponents.add(component);
  		      component.setColor(ACTIVE_COLOR);
  		    }
		}
  	}
  	
  	/** The function selects the legs according to the user's action */
  	public void toggleSelectionLeg(int selectionNum) {
  		if ( 0 <= selectionNum && selectionNum <= 5) {
  			Leg cur = legs[selectionNum];
  			if ( this.selectedLegs.contains(cur) ) {
  				this.selectedLegs.remove(cur);
  				this.selectedComponents.remove(cur.topJoint);
  				this.selectedComponents.remove(cur.middleJoint);
  				this.selectedComponents.remove(cur.endJoint);
  				cur.topJoint.setColor(INACTIVE_COLOR);
  				cur.middleJoint.setColor(INACTIVE_COLOR);
  				cur.endJoint.setColor(INACTIVE_COLOR);
  			}
  			else {
  		      this.selectedLegs.add(cur);
  		      this.selectedComponents.add(cur.topJoint);
  		      this.selectedComponents.add(cur.middleJoint);
  		      this.selectedComponents.add(cur.endJoint);
  		        cur.topJoint.setColor(ACTIVE_COLOR);
				cur.middleJoint.setColor(ACTIVE_COLOR);
				cur.endJoint.setColor(ACTIVE_COLOR);
  		    }
		}
  	}
  	
  	/** The function selects the parts of leg by user's action */
  	public void toggleSelectionLegParts(Component component) {
  	    if (this.selectedComponents.contains(component)) {
  	      this.selectedComponents.remove(component);
  	      component.setColor(INACTIVE_COLOR);
  	    } else {
  	      this.selectedComponents.add(component);
  	      component.setColor(ACTIVE_COLOR);
  	    }
  	}
  	
  	/** The function selects antenna by user's action */
  	public void toggleSelectionAntenna(int selectionNum) {
  		if ( 0 <= selectionNum && selectionNum <= 1) {
  			Antenna cur = antennas[selectionNum];
  			if ( this.selectedAntennas.contains(cur) ) {
  				this.selectedAntennas.remove(cur);
  				this.selectedComponents.remove(cur.topJoint);
  				this.selectedComponents.remove(cur.endJoint);
  				cur.topJoint.setColor(INACTIVE_COLOR);
  				cur.endJoint.setColor(INACTIVE_COLOR);
  			}
  			else {
  		      this.selectedAntennas.add(cur);
  		      this.selectedComponents.add(cur.topJoint);
  		      this.selectedComponents.add(cur.endJoint);
  		        cur.topJoint.setColor(ACTIVE_COLOR);
				cur.endJoint.setColor(ACTIVE_COLOR);
  		    }
		}
  	}
  	/** The function selects parts of antenna by user's action */
  	public void toggleSelectionAntennaParts(Component component) {
  	    if (this.selectedComponents.contains(component)) {
  	      this.selectedComponents.remove(component);
  	      component.setColor(INACTIVE_COLOR);
  	    } else {
  	      this.selectedComponents.add(component);
  	      component.setColor(ACTIVE_COLOR);
  	    }
  	}
  	
  	
  	
  	
  	
  	
  	/** The function identify whether the selected leg is left or right to do the mirroring */
  	public void changeSelected(Configuration config) {
  		for(Component c: this.selectedComponents) {
  			if(this.inLeftOrRight(c))
  			{
  				c.changeConfiguration(config);
  			}
  			else
  			{
  				double x = config.xAngle();
  				double y = -config.yAngle();
  				double z = -config.zAngle();
  				
  				BaseConfiguration newconfig = new BaseConfiguration(0, 0, 0);
  				newconfig.setXAngle(x);
  				newconfig.setYAngle(y);
  				newconfig.setZAngle(z);
  				
  				c.changeConfiguration(newconfig);
  				
  			}
  			
  		}
  	}
  	
  	
	public Ant(final Point3D position, final String name) {
		// Ant object itself as a top level component, need initialization
		super(position, name);

		//all the end part of legs
	    final Component left1_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), LEFT_LEG1_END_NAME);
	    final Component left2_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), LEFT_LEG2_END_NAME);
	    final Component left3_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), LEFT_LEG3_END_NAME);
	    final Component right1_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), RIGHT_LEG1_END_NAME);
	    final Component right2_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), RIGHT_LEG2_END_NAME);
	    final Component right3_end = new Component(new Point3D(0, 0,
		        LEG_MIDDLE_HEIGHT), new RoundedCylinder(LEG_END_RADIUS,
		    	        LEG_END_HEIGHT, this.glut), RIGHT_LEG3_END_NAME);
	    
	    //all the middle part of legs
	    final Component left1_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), LEFT_LEG1_MIDDLE_NAME);
	    final Component left2_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), LEFT_LEG2_MIDDLE_NAME);
	    final Component left3_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), LEFT_LEG3_MIDDLE_NAME);
	    final Component right1_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), RIGHT_LEG1_MIDDLE_NAME);
	    final Component right2_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), RIGHT_LEG2_MIDDLE_NAME);
	    final Component right3_middle = new Component(new Point3D(0, 0,
		        LEG_TOP_HEIGHT), new RoundedCylinder(LEG_MIDDLE_RADIUS,
		    	        LEG_MIDDLE_HEIGHT, this.glut), RIGHT_LEG3_MIDDLE_NAME);
	    
	    //all the top part of legs
	    final Component left1_top = new Component(new Point3D(0.2, 0,
		        -0.35), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), LEFT_LEG1_TOP_NAME);
	    final Component left2_top = new Component(new Point3D(0.2, 0,
		        -0.5), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), LEFT_LEG2_TOP_NAME);
	    final Component left3_top = new Component(new Point3D(0.2, 0,
		        -0.7), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), LEFT_LEG3_TOP_NAME);
	    final Component right1_top = new Component(new Point3D(-0.2, 0,
		        -0.35), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), RIGHT_LEG1_TOP_NAME);
	    final Component right2_top = new Component(new Point3D(-0.2, 0,
		       -0.5), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), RIGHT_LEG2_TOP_NAME);
	    final Component right3_top = new Component(new Point3D(-0.2, 0,
		        -0.7), new RoundedCylinder(LEG_TOP_RADIUS,
		    	        LEG_TOP_HEIGHT, this.glut), RIGHT_LEG3_TOP_NAME);


	    // put together the legs for easier selection by keyboard input later on
	    this.legs = new Leg[] { new Leg(left1_end, left1_middle, left1_top),
	    		new Leg(left2_end, left2_middle, left2_top),
	    		new Leg(left3_end, left3_middle, left3_top),
	    		new Leg(right1_end, right1_middle, right1_top),
	    		new Leg(right2_end, right2_middle, right2_top),
	    		new Leg(right3_end, right3_middle, right3_top) };
	    
	    
	    //the head
	    this.head = new Component(new Point3D(0, 0,0), new Head(HEAD_RADIUS, this.glut), HEAD_NAME);
	    
	    //the body
	    this.body = new Component(new Point3D(0, 0, 0), new Body(BODY_RADIUS, this.glut), BODY_NAME);
	    
	    //the midbody
	    this.midbody = new Component(new Point3D(0, 0, 0), new Midbody(MIDBODY_RADIUS, this.glut), MIDBODY_NAME);
	    
	    //the tail
	    this.tail = new Component(new Point3D(0, 0, 0), new Tail(TAIL_RADIUS, this.glut), TAIL_NAME);
	    
	    //all the parts of antenna
	    this.antenna1_top = new Component(new Point3D(0.2, 0.2,
		        0.2), new RoundedCylinder(ANTENNA_TOP_RADIUS,
		    	        ANTENNA_TOP_HEIGHT, this.glut), ANTENNA1_TOP_NAME);
	    this.antenna2_top = new Component(new Point3D(-0.2, 0.2,
	    		0.2), new RoundedCylinder(ANTENNA_TOP_RADIUS,
		    	        ANTENNA_TOP_HEIGHT, this.glut), ANTENNA2_TOP_NAME);
	    this.antenna1_end = new Component(new Point3D(0, 0,
	    		ANTENNA_TOP_HEIGHT), new RoundedCylinder(ANTENNA_END_RADIUS,
		    	        ANTENNA_END_HEIGHT, this.glut), ANTENNA1_END_NAME);
	    this.antenna2_end = new Component(new Point3D(0, 0,
		        ANTENNA_TOP_HEIGHT), new RoundedCylinder(ANTENNA_END_RADIUS,
		    	        ANTENNA_END_HEIGHT, this.glut), ANTENNA2_END_NAME);
	    
	    //put together the antennas for easier selection by keyboard input later on
	    this.antennas = new Antenna[] {
	    		new Antenna(antenna1_end,antenna1_top),
	    		new Antenna(antenna2_end,antenna2_top),
	    };
	    
	    //the camera
	    this.camera = new Component(INITIAL_POSITION, CAMERA_NAME);
	    
	    
	    //add the left leg parts into the left list
	    this.leftcom.add(left1_end);
	    this.leftcom.add(left1_middle);
	    this.leftcom.add(left1_top);
	    this.leftcom.add(left2_end);
	    this.leftcom.add(left2_middle);
	    this.leftcom.add(left2_top);
	    this.leftcom.add(left3_end);
	    this.leftcom.add(left3_middle);
	    this.leftcom.add(left3_top);
	    
	    //add the right leg parts into the right list
	    this.rightcom.add(right1_end);
	    this.rightcom.add(right1_middle);
	    this.rightcom.add(right1_top);
	    this.rightcom.add(right2_end);
	    this.rightcom.add(right2_middle);
	    this.rightcom.add(right2_top);
	    this.rightcom.add(right3_end);
	    this.rightcom.add(right3_middle);
	    this.rightcom.add(right3_top);
	    
	    //establish the hierarchical order
	    this.addChild(camera);
	    //start by the middle body
	    this.camera.addChild(this.midbody);
	    //the middle body connected to the body and tail
	    this.midbody.addChildren(this.body,this.tail);
	    //the body connected to the head, and top part of leg
	    this.body.addChildren(this.head,left1_top,left2_top,left3_top,right1_top,right2_top,right3_top);
	    //the head connected to the top part of antenna
	    this.head.addChildren(this.antenna1_top,this.antenna2_top);
	    //the top part of antenna connected to the end part
	    this.antenna1_top.addChild(this.antenna1_end);
	    this.antenna2_top.addChild(this.antenna2_end);
	    
	    //top part of leg connects to the middle part of leg
	    left1_top.addChild(left1_middle);
	    left2_top.addChild(left2_middle);
	    left3_top.addChild(left3_middle);
	    right1_top.addChild(right1_middle);
	    right2_top.addChild(right2_middle);
	    right3_top.addChild(right3_middle);
	    
	    //middle part of leg connects to the end part of leg
	    left1_middle.addChild(left1_end);
	    left2_middle.addChild(left2_end);
	    left3_middle.addChild(left3_end);
	    right1_middle.addChild(right1_end);
	    right2_middle.addChild(right2_end);
	    right3_middle.addChild(right3_end);
	    
	    //rotate the camera view
	    this.camera.rotate(Axis.Y, -90);
	    this.camera.rotate(Axis.Z, -50);
	    
	    //rotate the head
	    this.head.rotate(Axis.X, 30);
	    
	    //rotate the body
	    this.body.rotate(Axis.X, 20);
	    
	    //rotate the middle body
	    this.midbody.rotate(Axis.Z, 20);
	    
	    //rotate the tail
	    this.tail.rotate(Axis.X,10);
	   
	    //rotate the antennas
	    this.antenna1_end.rotate(Axis.X,60);
	    this.antenna1_end.rotate(Axis.Y,30);
	    
	    this.antenna2_end.rotate(Axis.X,60);
	    this.antenna2_end.rotate(Axis.Y,-30);
	    
	    this.antenna1_top.rotate(Axis.X, 270);
	    this.antenna2_top.rotate(Axis.X, 270);
	    
	    //rotate each leg to let it looks like a ant's leg
	    left3_top.rotate(Axis.X, 10);
	    left3_top.rotate(Axis.Y, 130);
	    left3_top.rotate(Axis.Z, -30);
	    
	    right3_top.rotate(Axis.X, 10);
	    right3_top.rotate(Axis.Y, -130);
	    right3_top.rotate(Axis.Z, 30);
	    
	    left2_top.rotate(Axis.X, 40);
	    left2_top.rotate(Axis.Y, 100);
	    left2_top.rotate(Axis.Z, -30);
	    
	    right2_top.rotate(Axis.X, 40);
	    right2_top.rotate(Axis.Y, -100);
	    right2_top.rotate(Axis.Z, 30);
	    
	    left1_top.rotate(Axis.X, 10);
	    left1_top.rotate(Axis.Y, 60);
	    left1_top.rotate(Axis.Z, 0);
	    
	    right1_top.rotate(Axis.X, 10);
	    right1_top.rotate(Axis.Y, -60);
	    right1_top.rotate(Axis.Z, 0);

	    left3_middle.rotate(Axis.X, 60);
	    left3_middle.rotate(Axis.Y, 0);
	    left3_middle.rotate(Axis.Z, 0);
	    
	    left2_middle.rotate(Axis.X, 60);
	    left2_middle.rotate(Axis.Y, 0);
	    left2_middle.rotate(Axis.Z, 0);
	    
	    left1_middle.rotate(Axis.X, 60);
	    left1_middle.rotate(Axis.Y, 0);
	    left1_middle.rotate(Axis.Z, 0);
	    
	    right3_middle.rotate(Axis.X,60);
	    right3_middle.rotate(Axis.Y, 0);
	    right3_middle.rotate(Axis.Z, 0);
	    
	    right2_middle.rotate(Axis.X,60);
	    right2_middle.rotate(Axis.Y, 0);
	    right2_middle.rotate(Axis.Z, 0);
	    
	    right1_middle.rotate(Axis.X,60);
	    right1_middle.rotate(Axis.Y, 0);
	    right1_middle.rotate(Axis.Z, 0);
	    
	    left3_end.rotate(Axis.X, -45);
	    left3_end.rotate(Axis.Y, 0);
	    left3_end.rotate(Axis.Z, 0);
	    
	    left2_end.rotate(Axis.X, -45);
	    left2_end.rotate(Axis.Y, 0);
	    left2_end.rotate(Axis.Z, 0);
	    
	    left1_end.rotate(Axis.X, -45);
	    left1_end.rotate(Axis.Y, 0);
	    left1_end.rotate(Axis.Z, 0);
	    
	    right3_end.rotate(Axis.X,-45);
	    right3_end.rotate(Axis.Y, 0);
	    right3_end.rotate(Axis.Z, 0);
	    
	    right2_end.rotate(Axis.X,-45);
	    right2_end.rotate(Axis.Y, 0);
	    right2_end.rotate(Axis.Z, 0);
	    
	    right1_end.rotate(Axis.X,-45);
	    right1_end.rotate(Axis.Y, 0);
	    right1_end.rotate(Axis.Z, 0);
	    
	    // set rotation limits for the head.
	    this.body.setXPositiveExtent(25);
	    this.body.setXNegativeExtent(10);
	    this.body.setYPositiveExtent(2);
	    this.body.setYNegativeExtent(-2);
	    this.body.setZPositiveExtent(20);
	    this.body.setZNegativeExtent(-20);
	    
	    // set rotation limits for the tail.
	    this.tail.setXPositiveExtent(10);
	    this.tail.setXNegativeExtent(5);
	    this.tail.setYPositiveExtent(5);
	    this.tail.setYNegativeExtent(-5);
	    this.tail.setZPositiveExtent(0);
	    this.tail.setZNegativeExtent(0);
	    
	    // set rotation limits for the head.
	    this.head.setXPositiveExtent(50);
	    this.head.setXNegativeExtent(-40);
	    this.head.setYPositiveExtent(40);
	    this.head.setYNegativeExtent(-40);
	    this.head.setZPositiveExtent(40);
	    this.head.setZNegativeExtent(-40);
	   
	    //set rotation limits for the leg
	    left1_top.setXPositiveExtent(30);
	    left1_top.setXNegativeExtent(-10);
	    left1_top.setYPositiveExtent(80);
	    left1_top.setYNegativeExtent(40);
	    left1_top.setZPositiveExtent(50);
	    left1_top.setZNegativeExtent(-50);
	    
	    left2_top.setXPositiveExtent(60);
	    left2_top.setXNegativeExtent(20);
	    left2_top.setYPositiveExtent(120);
	    left2_top.setYNegativeExtent(80);
	    left2_top.setZPositiveExtent(0);
	    left2_top.setZNegativeExtent(-60);
	    
	    left3_top.setXPositiveExtent(30);
	    left3_top.setXNegativeExtent(-10);
	    left3_top.setYPositiveExtent(150);
	    left3_top.setYNegativeExtent(110);
	    left3_top.setZPositiveExtent(0);
	    left3_top.setZNegativeExtent(-60);
	    
	    right1_top.setXPositiveExtent(10);
	    right1_top.setXNegativeExtent(-30);
	    right1_top.setYPositiveExtent(-40);
	    right1_top.setYNegativeExtent(-80);
	    right1_top.setZPositiveExtent(50);
	    right1_top.setZNegativeExtent(-50);
	    
	    right2_top.setXPositiveExtent(60);
	    right2_top.setXNegativeExtent(20);
	    right2_top.setYPositiveExtent(-80);
	    right2_top.setYNegativeExtent(-120);
	    right2_top.setZPositiveExtent(60);
	    right2_top.setZNegativeExtent(0);
	    
	    right3_top.setXPositiveExtent(10);
	    right3_top.setXNegativeExtent(-30);
	    right3_top.setYPositiveExtent(-110);
	    right3_top.setYNegativeExtent(-150);
	    right3_top.setZPositiveExtent(60);
	    right3_top.setZNegativeExtent(0);
	    
	    for (final Component middle : Arrays.asList(left1_middle, left2_middle, left3_middle, 
	    		right1_middle,right2_middle, right3_middle)) {
	        middle.setXPositiveExtent(80);
	        middle.setXNegativeExtent(40);
	        middle.setYPositiveExtent(30);
	        middle.setYNegativeExtent(-30);
	        middle.setZPositiveExtent(40);
	        middle.setZNegativeExtent(-40);
	      }
	    
	    for (final Component end : Arrays.asList(left1_end, left2_end, left3_end, 
	    		right1_end,right2_end, right3_end)) {
	        end.setXPositiveExtent(0);
	        end.setXNegativeExtent(-90);
	        end.setYPositiveExtent(30);
	        end.setYNegativeExtent(-30);
	        end.setZPositiveExtent(40);
	        end.setZNegativeExtent(-40);
	      }
	    
	    //set the rotation limits for antenna
	    antenna1_top.setXPositiveExtent(300);
	    antenna1_top.setXNegativeExtent(250);
	    antenna1_top.setYPositiveExtent(10);
	    antenna1_top.setYNegativeExtent(-10);
	    antenna1_top.setZPositiveExtent(30);
	    antenna1_top.setZNegativeExtent(-30);
	    
	    antenna2_top.setXPositiveExtent(300);
	    antenna2_top.setXNegativeExtent(250);
	    antenna2_top.setYPositiveExtent(10);
	    antenna2_top.setYNegativeExtent(-10);
	    antenna2_top.setZPositiveExtent(30);
	    antenna2_top.setZNegativeExtent(-30);
	    
	    antenna1_end.setXPositiveExtent(90);
	    antenna1_end.setXNegativeExtent(30);
	    antenna1_end.setYPositiveExtent(60);
	    antenna1_end.setYNegativeExtent(0);
	    antenna1_end.setZPositiveExtent(30);
	    antenna1_end.setZNegativeExtent(-30);
	    
	    antenna2_end.setXPositiveExtent(90);
	    antenna2_end.setXNegativeExtent(30);
	    antenna2_end.setYPositiveExtent(0);
	    antenna2_end.setYNegativeExtent(-60);
	    antenna2_end.setZPositiveExtent(30);
	    antenna2_end.setZNegativeExtent(-30);

	    







	    // create the list of all the components for debugging purposes
	    this.components = Arrays.asList();
	}
	
	/** The function identity the leg is in left or right */
	/** left->True Right-> False */
	public boolean inLeftOrRight(final Component cur)
	{
		int i = this.leftcom.indexOf(cur);
		
		if(i != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** the leg class */
	class Leg {
	    
		/** The joints of leg */
	    private final Component endJoint;
	    private final Component middleJoint;
	    private final Component topJoint;
	    /** The list of all the joints in this finger. */
	    private final List<Component> joints;
	    
	    
	    public Leg(final Component endJoint, final Component middleJoint,
	        final Component topJoint) {
	      this.endJoint = endJoint;
	      this.middleJoint = middleJoint;
	      this.topJoint = topJoint;
	      

	      this.joints = Collections.unmodifiableList(Arrays.asList(this.topJoint,
	          this.middleJoint, this.endJoint));
	    }

	    /**
	     * Gets the end joint of this finger.
	     * 
	     * @return The end joint of this finger.
	     */
	    Component endJoint() {
	      return this.endJoint;
	    }

	    /**
	     * Gets an unmodifiable view of the list of the joints of this finger.
	     * 
	     * @return An unmodifiable view of the list of the joints of this finger.
	     */
	    List<Component> joints() {
	      return this.joints;
	    }

	    /**
	     * Gets the middle joint of this finger.
	     * 
	     * @return The middle joint of this finger.
	     */
	    Component middleJoint() {
	      return this.middleJoint;
	    }

	    /**
	     * Gets the top joint of this finger.
	     * 
	     * @return The top joint of this finger.
	     */
	    Component topJoint() {
	      return this.topJoint;
	    }
	}
	/** The Antenna class */
	class Antenna {
	    
		/** The different parts of antenna */
	    private final Component endJoint;
	    private final Component topJoint;
	    /** The list of all the joints in this finger. */
	    private final List<Component> joints;
	    
	    
	    public Antenna(final Component endJoint,
	        final Component topJoint) {
	      this.endJoint = endJoint;
	      this.topJoint = topJoint;
	      

	      this.joints = Collections.unmodifiableList(Arrays.asList(this.topJoint,
	           this.endJoint));
	    }

	    /**
	     * Gets the end joint of this finger.
	     * 
	     * @return The end joint of this finger.
	     */
	    Component endJoint() {
	      return this.endJoint;
	    }

	    /**
	     * Gets an unmodifiable view of the list of the joints of this finger.
	     * 
	     * @return An unmodifiable view of the list of the joints of this finger.
	     */
	    List<Component> joints() {
	      return this.joints;
	    }

	   
	    /**
	     * Gets the top joint of this finger.
	     * 
	     * @return The top joint of this finger.
	     */
	    Component topJoint() {
	      return this.topJoint;
	    }
	}


}