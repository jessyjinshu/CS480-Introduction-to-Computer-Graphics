/**
 * PA2.java - driver for the ant model simulation
 
 */


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl
import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

/**
 * The main class which drives the ant model simulation.
 * @author Yuanpei Wang
 * 		   wangyp@bu.edu
 *         U14043099
 *         Fall 2018
 *skeleton code from
 *    
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2008
 *
 * Modified template given online.
 */
public class PA2 extends JFrame implements GLEventListener, KeyListener,
    MouseListener, MouseMotionListener {

  /**
   * A leg which has a bodyJoint, a middleJoint, and a distalJoint, and a feet joint
   */
	
	private class Leg {
     	private final Component distalJoint;
		private final Component middleJoint;
		private final Component bodyJoint;
		private final Component feetJoint;
		private final List<Component> joints;
		
	    public Leg(final Component bodyJoint, final Component middleJoint, final Component distalJoint,final Component feetJoint){
	    	
	    	this.bodyJoint = bodyJoint;
	    	this.middleJoint = middleJoint;
	    	this.distalJoint = distalJoint;
	    	this.feetJoint = feetJoint;
	    	this.joints = Collections.unmodifiableList(Arrays.asList(this.distalJoint, this.middleJoint, this.bodyJoint,this.feetJoint));
	    }
	    
	    
	    Component distalJoint() {
	    	return this.distalJoint;
	    }
	    Component feetJoint() {
	    	return this.feetJoint;
	    }
	    
	    Component middleJoint() {
	    	return this.middleJoint;
	    }
	    
	    Component bodyJoint() {
	    	return this.bodyJoint;
	    }
	    
	   
	    
	    List<Component> joints() {
	      return this.joints;
	    }
	    
  }		


  /** The color for components which are selected for rotation. */
  public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  /** The default width of the created window. */
  public static final int DEFAULT_WINDOW_HEIGHT = 512;
  /** The default height of the created window. */
  public static final int DEFAULT_WINDOW_WIDTH = 512;
  /** The height of the distal joint on each of the legs. */
  public static final double DISTAL_JOINT_HEIGHT = 0.8;
  /** The height of the middle joint on each of the legs. */
  public static final double MIDDLE_JOINT_HEIGHT = 0.1;
  /** The height of the body joint on each of the legs. */
  public static final double BODY_JOINT_HEIGHT = 0.45;
  /** The height of the feet joint on each of the legs. */
  public static final double FEET_JOINT_HEIGHT = 0.2;
  /** The radius of the feet joint on each of the legs. */
  public static final double FEET_JOINT_RADIUS = 0.015;
  /** The height of the head. */
  public static final double TAIL_HEIGHT = -0.14;
  /** The height of the body. */
  public static final double BODY_HEIGHT = 0.3;
  /** The height of the distal joint on each TENTACLE. 触角*/
  public static final double TENTACLE_DISTAL_JOINT_HEIGHT = 0.6;
  /** The height of the middle joint on each TENTACLE. */
  public static final double TENTACLE_MIDDLE_JOINT_HEIGHT = 0.05;
  /** The height of the body joint on each TENTACLE. */
  public static final double TENTACLE_BODY_JOINT_HEIGHT = 0.3;
  /** The radius of the TENTACLE. */
  public static final double TENTACLE_LEG_RADIUS = 0.03;
  /** The radius of each joint which comprises the leg. */
  public static final double BODY_LEG_RADIUS = 0.04;
  public static final double MIDDLE_LEG_RADIUS = 0.02;
  public static final double DISTAL_LEG_RADIUS = 0.02;
  /** The radius of the head. */
  public static final double TAIL_RADIUS = 0.4;
  /** The radius of the body. */
  public static final double BODY_RADIUS = 0.4;
  /** The height of the head. */
  public static final double HEAD_HEIGHT = 0.75;
  /** THE RADIUS OF THE HEAD. */
  public static final double HEAD_RADIUS = 0.3;

  /** The radius of the TENTACLE rotator. */
  public static final double TENTACLE_ROTATOR_RADIUS = 0.03;
  /** The color for components which are not selected for rotation. */
  public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
  /** The initial position of the top level component in the scene. */
  public static final Point3D INITIAL_POSITION = new Point3D(1.5, 1.5, 2);
  /** The angle by which to rotate the joint on user request to rotate. */
  public static final double ROTATION_ANGLE = 2.0;
  /** Randomly generated serial version UID. */
  private static final long serialVersionUID = -7060944143920496524L;


  /**
   * Runs the hand simulation in a single JFrame.
   * 
   * @param args
   *          This parameter is ignored.
   */
  public static void main(final String[] args) {
    new PA2().animator.start();
  }

  /**
   * The animator which controls the framerate at which the canvas is animated.
   */
  final FPSAnimator animator;
  /** The canvas on which we draw the scene. */
  private final GLCanvas canvas;
  /** The capabilities of the canvas. */
  private final GLCapabilities capabilities = new GLCapabilities(null);
  /** The legs on the head to be modeled. */
  private final Leg[] legs;
  /** The OpenGL utility object. */
  private final GLU glu = new GLU();
  /** The OpenGL utility toolkit object. */
  private final GLUT glut = new GLUT();
  /** The head to be modeled */
  private final Component head;
  /** The tail to be modeled. */
  private final Component tail;
  /** The body to be modeled. */
  private final Component body;
  /** The last x and y coordinates of the mouse press. */
  private final Component bodyToTail;
  private int last_x = 0, last_y = 0;
  /** Whether the world is being rotated. */
  private boolean rotate_world = false;
  /** The axis around which to rotate the selected joints. */
  private Axis selectedAxis = Axis.X;
  /** The set of components which are currently selected for rotation. */
  private final Set<Component> selectedComponents = new HashSet<Component>(18);
  /**
   * The set of legs which have been selected for rotation.
   * 
   * Selecting a joint will only affect the joints in this set of selected
   * legs.
   **/
  private final Set<Leg> selectedLegs = new HashSet<Leg>(8);
  /** Whether the state of the model has been changed. */
  private boolean stateChanged = true;
  /**
   * The top level component in the scene which controls the positioning and
   * rotation of everything in the scene.
   */
  private final Component topLevelComponent;
  /** The quaternion which controls the rotation of the world. */
  private Quaternion viewing_quaternion = new Quaternion();
  /** The set of all components. */
  private final List<Component> components;

  public static String LEG1_BODY_NAME = "leg1 body";
  public static String LEG1_MIDDLE_NAME = "leg1 middle";
  public static String LEG1_DISTAL_NAME = "leg1 distal";
  public static String LEG1_FEET_NAME = "leg1 FEET";
  
  public static String LEG2_BODY_NAME = "leg2 body";
  public static String LEG2_MIDDLE_NAME = "leg2 middle";
  public static String LEG2_DISTAL_NAME = "leg2 distal";
  public static String LEG2_FEET_NAME = "leg2 FEET";
 
  public static String LEG3_BODY_NAME = "leg3 body";
  public static String LEG3_MIDDLE_NAME = "leg3 middle";
  public static String LEG3_DISTAL_NAME = "leg3 distal";
  public static String LEG3_FEET_NAME = "leg3 FEET";
  
  public static String LEG4_BODY_NAME = "leg4 body";
  public static String LEG4_MIDDLE_NAME = "leg4 middle";
  public static String LEG4_DISTAL_NAME = "leg4 distal";
  public static String LEG4_FEET_NAME = "leg4 FEET";
  
  public static String LEG5_BODY_NAME = "leg5 body";
  public static String LEG5_MIDDLE_NAME = "leg5 middle";
  public static String LEG5_DISTAL_NAME = "leg5 distal";
  public static String LEG5_FEET_NAME = "leg5 FEET";
  
  public static String LEG6_BODY_NAME = "leg6 body";
  public static String LEG6_MIDDLE_NAME = "leg6 middle";
  public static String LEG6_DISTAL_NAME = "leg6 distal";
  public static String LEG6_FEET_NAME = "leg6 FEET";
  public static String LEG7_FEET_NAME = "leg7 FEET";
  public static String LEG8_FEET_NAME = "leg8 FEET";
  
  public static String TENTACLE1_BODY_NAME = "leg9 TENTACLE body";
  public static String TENTACLE1_MIDDLE_NAME = "leg9 TENTACLE middle";
  public static String TENTACLE1_DISTAL_NAME = "leg9 TENTACLE distal";

  public static String TENTACLE2_BODY_NAME = "leg10 TENTACLE body";
  public static String TENTACLE2_MIDDLE_NAME = "leg10 TENTACLE middle";
  public static String TENTACLE2_DISTAL_NAME = "leg10 TENTACLE distal";
  
  public static String Body_To_Tail = "BODYTOTAIL";
  public static String Tail_NAME = "Tail";
  public static String BODY_NAME = "body";
  public static String HEAD_NAME = "head";
  public static String EYE1_NAME = "eye1";
  public static String EYE2_NAME = "eye2";
  
  public static String TOP_LEVEL_NAME = "top level";

  /**
   * Initializes the necessary OpenGL objects and adds a canvas to this JFrame.
   */
  public PA2() {
    this.capabilities.setDoubleBuffered(true);

    this.canvas = new GLCanvas(this.capabilities);
    this.canvas.addGLEventListener(this);
    this.canvas.addMouseListener(this);
    this.canvas.addMouseMotionListener(this);
    this.canvas.addKeyListener(this);
    // this is true by default, but we just add this line to be explicit
    this.canvas.setAutoSwapBufferMode(true);
    this.getContentPane().add(this.canvas);

    // refresh the scene at 60 frames per second
    this.animator = new FPSAnimator(this.canvas, 60);

    this.setTitle("CS480/CS680 PA2: Ant Simulator");
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    
    final Component feet1 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG1_FEET_NAME);
    final Component feet2 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG2_FEET_NAME);
    final Component feet3 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG3_FEET_NAME);
    final Component feet4 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG4_FEET_NAME);
    final Component feet5 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG5_FEET_NAME);
    final Component feet6 = new Component(new Point3D(0, 0,
            DISTAL_JOINT_HEIGHT), new RoundedCylinder(FEET_JOINT_RADIUS,
            FEET_JOINT_HEIGHT, this.glut), LEG6_FEET_NAME);
    final Component feet7 = new Component(new Point3D(0, 0,
            0), new RoundedCylinder(FEET_JOINT_RADIUS,
            0, this.glut), LEG7_FEET_NAME);
    final Component feet8 = new Component(new Point3D(0, 0,
            0), new RoundedCylinder(FEET_JOINT_RADIUS,
            0, this.glut), LEG8_FEET_NAME);
    // all the distal joints
   
    final Component distal1 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG1_DISTAL_NAME);
    final Component distal2 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG2_DISTAL_NAME);
    final Component distal3 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG3_DISTAL_NAME);
    final Component distal4 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG4_DISTAL_NAME);
    final Component distal5 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG5_DISTAL_NAME);
    final Component distal6 = new Component(new Point3D(0, 0,
            MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
            DISTAL_JOINT_HEIGHT, this.glut), LEG6_DISTAL_NAME);
    
    final Component distal9_TENTACLE = new Component(new Point3D(0, 0,
            TENTACLE_MIDDLE_JOINT_HEIGHT), new RoundedCylinder(TENTACLE_LEG_RADIUS,
            TENTACLE_DISTAL_JOINT_HEIGHT, this.glut), TENTACLE1_DISTAL_NAME);
    
    final Component distal10_TENTACLE = new Component(new Point3D(0, 0,
            TENTACLE_MIDDLE_JOINT_HEIGHT), new RoundedCylinder(TENTACLE_LEG_RADIUS,
            TENTACLE_DISTAL_JOINT_HEIGHT, this.glut), TENTACLE2_DISTAL_NAME);

    // all the middle joints
    final Component middle1 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG1_MIDDLE_NAME);
    final Component middle2 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG2_MIDDLE_NAME);
    final Component middle3 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG3_MIDDLE_NAME);
    final Component middle4 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG4_MIDDLE_NAME);
    final Component middle5 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG5_MIDDLE_NAME);
    final Component middle6 = new Component(new Point3D(0, 0,
            BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
            MIDDLE_JOINT_HEIGHT, this.glut), LEG6_MIDDLE_NAME);
   
    final Component middle9_TENTACLE = new Component(new Point3D(0, 0,
            TENTACLE_BODY_JOINT_HEIGHT), new RoundedCylinder(TENTACLE_LEG_RADIUS,
            TENTACLE_MIDDLE_JOINT_HEIGHT, this.glut), TENTACLE1_MIDDLE_NAME);
    final Component middle10_TENTACLE = new Component(new Point3D(0, 0,
            TENTACLE_BODY_JOINT_HEIGHT), new RoundedCylinder(TENTACLE_LEG_RADIUS,
            TENTACLE_MIDDLE_JOINT_HEIGHT, this.glut), TENTACLE2_MIDDLE_NAME);

    // all the body joints
    final Component body1 = new Component(new Point3D(0.2, 0, 0.15),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG1_BODY_NAME);
    final Component body2 = new Component(new Point3D(-0.2, 0, 0.15),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG2_BODY_NAME);
    final Component body3 = new Component(new Point3D(0.2, 0, 0.3),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG3_BODY_NAME);
    final Component body4 = new Component(new Point3D(-0.2, 0, 0.3),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG4_BODY_NAME);
    final Component body5 = new Component(new Point3D(0.2, 0, 0.45),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG5_BODY_NAME);
    final Component body6 = new Component(new Point3D(-0.2, 0, 0.45),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG6_BODY_NAME);
    
    final Component body9_TENTACLE = new Component(new Point3D(0.18, -0.15, 0.2),
        new RoundedCylinder(TENTACLE_LEG_RADIUS, TENTACLE_BODY_JOINT_HEIGHT, this.glut),
        TENTACLE1_BODY_NAME);
    final Component body10_TENTACLE = new Component(new Point3D(-0.18, -0.15, 0.2),
        new RoundedCylinder(TENTACLE_LEG_RADIUS, TENTACLE_BODY_JOINT_HEIGHT, this.glut),
        TENTACLE2_BODY_NAME);
    

    final Component eye1 = new Component(new Point3D(0.15,-0.07 ,0.42 ),  new Eye(0.1, this.glut),EYE1_NAME);
    final Component eye2 = new Component(new Point3D(-0.15,-0.07 ,0.42 ),  new Eye(0.1, this.glut),EYE2_NAME);
    		
    		
    // put together the legs for easier selection by keyboard input later on
    this.legs = new Leg[] { new Leg( body1, middle1, distal1,feet1),
        new Leg(body2, middle2, distal2,feet2),
        new Leg(body3, middle3, distal3,feet3),
        new Leg( body4, middle4, distal4,feet4),
        new Leg( body5, middle5, distal5,feet5),
        new Leg( body6, middle6, distal6,feet6),
        new Leg( body9_TENTACLE, middle9_TENTACLE, distal9_TENTACLE,feet7),
        new Leg( body10_TENTACLE, middle10_TENTACLE, distal10_TENTACLE,feet8)};

    // the head and body
    this.body = new Component(new Point3D(0, -0.1, BODY_HEIGHT), new Body(
        BODY_RADIUS, this.glut), BODY_NAME);
    this.tail = new Component(new Point3D(0, 0, TAIL_HEIGHT), new Tail(
            TAIL_RADIUS, this.glut), Tail_NAME);
    this.head = new Component(new Point3D(0, -0.2, HEAD_HEIGHT), new Head(HEAD_RADIUS,this.glut), HEAD_NAME);
    this.bodyToTail = new Component(new Point3D(0, 0, 1), new Head(0.2,this.glut), Body_To_Tail);
    // the top level component which provides an initial position and rotation
    // to the scene (but does not cause anything to be drawn)
    this.topLevelComponent = new Component(INITIAL_POSITION, TOP_LEVEL_NAME);

    //Attach all the components together
    this.topLevelComponent.addChild(this.bodyToTail);
    this.bodyToTail.addChildren(this.body,this.tail);
    this.body.addChildren(body1, body2, body3, body4, body5, body6,this.head);
    this.head.addChildren(body9_TENTACLE,body10_TENTACLE,eye1,eye2);

    
    body1.addChild(middle1);
    body2.addChild(middle2);
    body3.addChild(middle3);
    body4.addChild(middle4);
    body5.addChild(middle5);
    body6.addChild(middle6);
   
    body9_TENTACLE.addChild(middle9_TENTACLE);
    body10_TENTACLE.addChild(middle10_TENTACLE);
    
    
    middle1.addChild(distal1);
    middle2.addChild(distal2);
    middle3.addChild(distal3);
    middle4.addChild(distal4);
    middle5.addChild(distal5);
    middle6.addChild(distal6);
    
    distal1.addChild(feet1);
    distal2.addChild(feet2);
    distal3.addChild(feet3);
    distal4.addChild(feet4);
    distal5.addChild(feet5);
    distal6.addChild(feet6);
    
   
    middle9_TENTACLE.addChild(distal9_TENTACLE);
    middle10_TENTACLE.addChild(distal10_TENTACLE);
    
    // rotate the camera to starting position
    this.topLevelComponent.rotate(Axis.Y, 220);
    this.topLevelComponent.rotate(Axis.X, -130);
    
    // rotate all components to be in the stop position.
    body6.rotate(Axis.Y, -39);
    body5.rotate(Axis.Y, 59);
    body4.rotate(Axis.Y, -105);
    body3.rotate(Axis.Y, 105);
    body2.rotate(Axis.Y, -142);
    body1.rotate(Axis.Y, 130);
    
    body6.rotate(Axis.X, 28);
    body5.rotate(Axis.X, 39);
    body4.rotate(Axis.X, 42);
    body3.rotate(Axis.X, 38);
    body2.rotate(Axis.X, 20);
    body1.rotate(Axis.X, 12);
    
    body1.rotate(Axis.Z, -28);
    body2.rotate(Axis.Z, 24);
    body3.rotate(Axis.Z, -28);
    body4.rotate(Axis.Z, 28);
    body5.rotate(Axis.Z, -32);
    body6.rotate(Axis.Z, 28);
   
    body9_TENTACLE.rotate(Axis.X, 84);
    body10_TENTACLE.rotate(Axis.X, 84);
    
    body9_TENTACLE.rotate(Axis.Y,8);
    body10_TENTACLE.rotate(Axis.Y, -20);
    
    distal1.rotate(Axis.X, -68);
    distal2.rotate(Axis.X, -64);
    distal3.rotate(Axis.X, -66);
    distal4.rotate(Axis.X, -68);
    distal5.rotate(Axis.X, -60);
    distal6.rotate(Axis.X, -90);
    

    
    feet1.rotate(Axis.X, 40);
    feet2.rotate(Axis.X, 40);
    feet3.rotate(Axis.X, 40);
    feet4.rotate(Axis.X, 40);
    feet5.rotate(Axis.X, 40);
    feet6.rotate(Axis.X, 40);

    distal9_TENTACLE.rotate(Axis.X, -90);
    distal10_TENTACLE.rotate(Axis.X, -90);
    
    this.head.rotate(Axis.X,-45);
    this.body.rotate(Axis.X,-20);
    this.tail.rotate(Axis.X, 154);
    
    
    
    // set rotation limits for the head.
    this.body.setXPositiveExtent(90);
    this.body.setXNegativeExtent(-90);
    this.body.setYPositiveExtent(25);
    this.body.setYNegativeExtent(-25);
    this.body.setZPositiveExtent(20);
    this.body.setZNegativeExtent(-20);
    // set rotation limits for the tail.
    this.tail.setXPositiveExtent(240);
    this.tail.setXNegativeExtent(100);
    this.tail.setYPositiveExtent(70);
    this.tail.setYNegativeExtent(-70);
    this.tail.setZPositiveExtent(0);
    this.tail.setZNegativeExtent(0);
    // set rotation limits for the head.
    this.head.setXPositiveExtent(70);
    this.head.setXNegativeExtent(-70);
    this.head.setYPositiveExtent(60);
    this.head.setYNegativeExtent(-60);
    this.head.setZPositiveExtent(70);
    this.head.setZNegativeExtent(-70);


    
    // set rotation limits for the middle joints of the legs and the TENTACLEs.
    for (final Component middleJoint : Arrays.asList(middle1, middle2, middle3, 
    		middle4, middle5, middle6, middle9_TENTACLE, middle10_TENTACLE)) {
        middleJoint.setXPositiveExtent(10);
        middleJoint.setXNegativeExtent(-20);
        middleJoint.setYPositiveExtent(5);
        middleJoint.setYNegativeExtent(-5);
        middleJoint.setZPositiveExtent(25);
        middleJoint.setZNegativeExtent(-25);
      }
    //set rotation limit for body joints
    for (final Component bodyJoint : Arrays.asList(body1, body3, 
    	 body5,  body9_TENTACLE)) {
    	bodyJoint.setXPositiveExtent(50);
    	bodyJoint.setXNegativeExtent(-50);
    	bodyJoint.setYPositiveExtent(200);
    	bodyJoint.setYNegativeExtent(0);
    	bodyJoint.setZPositiveExtent(50);
    	bodyJoint.setZNegativeExtent(-50);
      }
    for (final Component bodyJoint : Arrays.asList( body2,  
    		body4, body6, body10_TENTACLE)) {
    	bodyJoint.setXPositiveExtent(50);
    	bodyJoint.setXNegativeExtent(-50);
    	bodyJoint.setYPositiveExtent(0 );
    	bodyJoint.setYNegativeExtent(-200);
    	bodyJoint.setZPositiveExtent(50);
    	bodyJoint.setZNegativeExtent(-50);
      }
    
    // set rotation limits for the distal joints of the legs and the TENTACLEs.
    for (final Component distalJoint : Arrays.asList(distal1, distal2, distal3, 
    		distal4, distal5, distal6, distal9_TENTACLE, distal10_TENTACLE)) {
        distalJoint.setXPositiveExtent(0);
        distalJoint.setXNegativeExtent(-150);
        distalJoint.setYPositiveExtent(15);
        distalJoint.setYNegativeExtent(-12.5);
        distalJoint.setZPositiveExtent(25);
        distalJoint.setZNegativeExtent(-25);
      }
    
    // set rotation limits for the feet joints of the legs and the TENTACLEs.
    for (final Component feetJoint : Arrays.asList(feet1,feet2,feet3,feet4,feet6)) {
    	feetJoint.setXPositiveExtent(50);
    	feetJoint.setXNegativeExtent(-50);
    	feetJoint.setYPositiveExtent(50);
    	feetJoint.setYNegativeExtent(-50);
    	feetJoint.setZPositiveExtent(50);
    	feetJoint.setZNegativeExtent(-50);
      }
    
    // create the list of all the components for debugging purposes.
    this.components = Arrays.asList(body1, middle1, distal1, feet1, body2, middle2,
        distal2, feet2,  body3, middle3, distal3, feet3, body4, middle4, distal4, feet4, body5,
        middle5, distal5, feet5, body6, middle6, distal6, feet6, body9_TENTACLE, middle9_TENTACLE, distal9_TENTACLE,
         body10_TENTACLE, middle10_TENTACLE, distal10_TENTACLE,this.tail, this.body,this.head,this.bodyToTail);
    
        
        
}
  /**
   * Redisplays the scene containing the hand model.
   * 
   * @param drawable
   *          The OpenGL drawable object with which to create OpenGL models.
   */
  public void display(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // clear the display
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // from here on affect the model view
    gl.glMatrixMode(GL2.GL_MODELVIEW);

    // start with the identity matrix initially
    gl.glLoadIdentity();

    // rotate the world by the appropriate rotation quaternion
    gl.glMultMatrixf(this.viewing_quaternion.toMatrix(), 0);

    // update the position of the components which need to be updated
    // TODO only need to update the selected and JUST deselected components
    if (this.stateChanged) {
      this.topLevelComponent.update(gl);
      this.stateChanged = false;
    }

    // redraw the components
    this.topLevelComponent.draw(gl);
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param drawable
   *          This parameter is ignored.
   * @param modeChanged
   *          This parameter is ignored.
   * @param deviceChanged
   *          This parameter is ignored.
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged) {
    // intentionally unimplemented
  }

  /**
   * Initializes the scene and model.
   * 
   * @param drawable
   *          {@inheritDoc}
   */
  public void init(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // perform any initialization needed by the hand model
    this.topLevelComponent.initialize(gl);

    // initially draw the scene
    this.topLevelComponent.update(gl);

    // set up for shaded display of the hand
    final float light0_position[] = { 1, 1, 1, 0 };
    final float light0_ambient_color[] = { 0.25f, 0.25f, 0.25f, 1 };
    final float light0_diffuse_color[] = { 1, 1, 1, 1 };

    gl.glPolygonMode(GL.GL_FRONT, GL2.GL_FILL);
    gl.glEnable(GL2.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glShadeModel(GL2.GL_SMOOTH);

    // set up the light source
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light0_ambient_color, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse_color, 0);

    // turn lighting and depth buffering on
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_NORMALIZE);
  }

  /**
   * Interprets key presses according to the following scheme:
   * 
   * up-arrow, down-arrow: increase/decrease rotation angle
   * 
   * @param key
   *          The key press event object.
   */
  public void keyPressed(final KeyEvent key) {
    switch (key.getKeyCode()) {
    case KeyEvent.VK_KP_UP:
    case KeyEvent.VK_UP:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    case KeyEvent.VK_KP_DOWN:
    case KeyEvent.VK_DOWN:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, -ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    default:
      break;
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param key
   *          This parameter is ignored.
   */
  public void keyReleased(final KeyEvent key) {
    // intentionally unimplemented
  }

  private final TestCases testCases = new TestCases();

  private void setModelState(final Map<String, Angled> state) {
	  
    this.tail.setAngles(state.get(Tail_NAME));
    this.body.setAngles(state.get(BODY_NAME));
    this.head.setAngles(state.get(HEAD_NAME));
    this.bodyToTail.setAngles(state.get(Body_To_Tail));
    this.legs[0].bodyJoint().setAngles(state.get(LEG1_BODY_NAME));
    this.legs[0].middleJoint().setAngles(state.get(LEG1_MIDDLE_NAME));
    this.legs[0].distalJoint().setAngles(state.get(LEG1_DISTAL_NAME));
    this.legs[0].feetJoint().setAngles(state.get(LEG1_FEET_NAME));
    this.legs[1].bodyJoint().setAngles(state.get(LEG2_BODY_NAME));
    this.legs[1].middleJoint().setAngles(state.get(LEG2_MIDDLE_NAME));
    this.legs[1].distalJoint().setAngles(state.get(LEG2_DISTAL_NAME));
    this.legs[1].feetJoint().setAngles(state.get(LEG2_FEET_NAME));
    this.legs[2].bodyJoint().setAngles(state.get(LEG3_BODY_NAME));
    this.legs[2].middleJoint().setAngles(state.get(LEG3_MIDDLE_NAME));
    this.legs[2].distalJoint().setAngles(state.get(LEG3_DISTAL_NAME));
    this.legs[2].feetJoint().setAngles(state.get(LEG3_FEET_NAME));
    this.legs[3].bodyJoint().setAngles(state.get(LEG4_BODY_NAME));
    this.legs[3].middleJoint().setAngles(state.get(LEG4_MIDDLE_NAME));
    this.legs[3].distalJoint().setAngles(state.get(LEG4_DISTAL_NAME));
    this.legs[3].feetJoint().setAngles(state.get(LEG4_FEET_NAME));
    this.legs[4].bodyJoint().setAngles(state.get(LEG5_BODY_NAME));
    this.legs[4].middleJoint().setAngles(state.get(LEG5_MIDDLE_NAME));
    this.legs[4].distalJoint().setAngles(state.get(LEG5_DISTAL_NAME));
    this.legs[4].feetJoint().setAngles(state.get(LEG5_FEET_NAME));
    this.legs[5].bodyJoint().setAngles(state.get(LEG6_BODY_NAME));
    this.legs[5].middleJoint().setAngles(state.get(LEG6_MIDDLE_NAME));
    this.legs[5].distalJoint().setAngles(state.get(LEG6_DISTAL_NAME));
    this.legs[5].feetJoint().setAngles(state.get(LEG6_FEET_NAME));
    this.legs[6].bodyJoint().setAngles(state.get(TENTACLE1_BODY_NAME));
    this.legs[6].middleJoint().setAngles(state.get(TENTACLE1_MIDDLE_NAME));
    this.legs[6].distalJoint().setAngles(state.get(TENTACLE1_DISTAL_NAME));
    this.legs[7].bodyJoint().setAngles(state.get(TENTACLE2_BODY_NAME));
    this.legs[7].middleJoint().setAngles(state.get(TENTACLE2_MIDDLE_NAME));
    this.legs[7].distalJoint().setAngles(state.get(TENTACLE2_DISTAL_NAME));

    this.stateChanged = true;
    

  }

//  * Interprets typed keys according to the following scheme:
//	   * 
//	   * 1 : toggle the first finger leg active in rotation
//	   * 
//	   * 2 : toggle the second leg active in rotation
//	   * 
//	   * 3 : toggle the third leg active in rotation
//	   * 
//	   * 4 : toggle the fourth leg active in rotation
//	   * 
//	   * 5 : toggle the fifth leg active in rotation
//	   * 
//	   * 6 : toggle the sixth leg active in rotation
//	   * 
//	   * 7 : toggle the first TENTACLE active in rotation
//	   * 
//	   * 8 : toggle the second TENTACLE active in rotation
//	   * 
//	   * - : toggle the Tail 
//	   * 
//	   * = : toggle the body
//	   * 
//	   * h : toggle the head
//	   * 
//	   * X : use the X axis rotation at the active joint(s)
//	   * 
//	   * Y : use the Y axis rotation at the active joint(s)
//	   * 
//	   * Z : use the Z axis rotation at the active joint(s)
//	   * 
//	   * C : resets the ant to the stop sign
//	   * 
//	   * M : select middle joint
//	   * 
//	   * D : select distal joint
//	   * 
//	   * F : select feet joint
//	   * 
//	   * B : select body joint
//	   * 
//	   * T : select bodytotail joint (pivot point)
//	   * 
//	   * R : resets the view to the initial rotation
//	   * 
//	   * K : prints the angles of the five fingers for debugging purposes
//	   * 
//	   * p : print angle info of all components
//	   * 
//	   * Q, Esc : exits the program
  public void keyTyped(final KeyEvent key) {
    switch (key.getKeyChar()) {
    case 'Q':
    case 'q':
    case KeyEvent.VK_ESCAPE:
      new Thread() {
        @Override
        public void run() {
          PA2.this.animator.stop();
        }
      }.start();
      System.exit(0);
      break;

    // print the angles of the components
    case 'K':
    case 'k':
      printJoints();
      break;
      
    case 'p':
    case 'P':
    	printAngles();
    	break;

     //resets to the stop sign
    case 'C':
    case 'c':
      this.setModelState(this.testCases.stop());
      break;

    //set the state of the hand to the next test case
    case 'T':
    case 't':
      this.setModelState(this.testCases.next());
      break;

    // set the viewing quaternion to 0 rotation
    case 'R':
    case 'r':
      this.viewing_quaternion.reset();
      break;

    // Toggle which leg(s) are affected by the current rotation
    case '1':
      toggleSelection(this.legs[0]);
      break;
    case '2':
      toggleSelection(this.legs[1]);
      break;
    case '3':
      toggleSelection(this.legs[2]);
      break;
    case '4':
      toggleSelection(this.legs[3]);
      break;
    case '5':
      toggleSelection(this.legs[4]);
      break;
    case '6':
      toggleSelection(this.legs[5]);
      break;
      // Toggle which TENTACLE(s) are active in rotation
    case '7':
      toggleSelection(this.legs[6]);
      break;
    case '8':
        toggleSelection(this.legs[7]);
        break;
    
      
      // Toggle the head
    case '-':
      toggleSelection(this.tail);
      break;
    // Toggle the body
    case '=':
      toggleSelection(this.body);
      break;
      
    case 'W':
    case 'w':
        toggleSelection(this.bodyToTail);
        break;
      
    case 'h':
    case 'H':
        toggleSelection(this.head);
        break;


    // toggle which joints are affected by the current rotation
    case 'D':
    case 'd':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.distalJoint());
      }
      break;
    case 'M':
    case 'm':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.middleJoint());
      }
      break;
    case 'B':
    case 'b':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.bodyJoint());
      }
      break;
      
    case 'F':
    case 'f':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.feetJoint());
      }
      break;

    // change the axis of rotation at current active joint
    case 'X':
    case 'x':
      this.selectedAxis = Axis.X;
      break;
    case 'Y':
    case 'y':
      this.selectedAxis = Axis.Y;
      break;
    case 'Z':
    case 'z':
      this.selectedAxis = Axis.Z;
      break;
    default:
      break;
    }
  }

  /**
   * Prints the joints on the System.out print stream.
   */
  private void printJoints() {
    this.printJoints(System.out);
  }
  

  /**
   * Prints the joints on the specified PrintStream.
   * 
   * @param printStream
   *          The stream on which to print each of the components.
   */
  private void printJoints(final PrintStream printStream) {
    for (final Component component : this.components) {
      printStream.println(component);
    }
  }
  private void printAngles(){
	  this.printAngles(System.out);
  }
  
  private void printAngles(final PrintStream printStream){
	  for (final Component component : this.components){
		  printStream.println(component.name());
		  printStream.println("X angle");
		  printStream.println(component.xAngle());
		  printStream.println("Y angle");
		  printStream.println(component.yAngle());
		  printStream.println("Z angle");
		  printStream.println(component.zAngle());
	  }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseClicked(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Updates the rotation quaternion as the mouse is dragged.
   * 
   * @param mouse
   *          The mouse drag event object.
   */
  public void mouseDragged(final MouseEvent mouse) {
	if (this.rotate_world) {
		// get the current position of the mouse
		final int x = mouse.getX();
		final int y = mouse.getY();
	
		// get the change in position from the previous one
		final int dx = x - this.last_x;
		final int dy = y - this.last_y;
	
		// create a unit vector in the direction of the vector (dy, dx, 0)
		final double magnitude = Math.sqrt(dx * dx + dy * dy);
		final float[] axis = magnitude == 0 ? new float[]{1,0,0}: // avoid dividing by 0
			new float[] { (float) (dy / magnitude),(float) (dx / magnitude), 0 };
	
		// calculate appropriate quaternion
		final float viewing_delta = 3.1415927f / 180.0f;
		final float s = (float) Math.sin(0.5f * viewing_delta);
		final float c = (float) Math.cos(0.5f * viewing_delta);
		final Quaternion Q = new Quaternion(c, s * axis[0], s * axis[1], s
				* axis[2]);
		this.viewing_quaternion = Q.multiply(this.viewing_quaternion);
	
		// normalize to counteract acccumulating round-off error
		this.viewing_quaternion.normalize();
	
		// save x, y as last x, y
		this.last_x = x;
		this.last_y = y;
	}
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseEntered(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseExited(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseMoved(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Starts rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse press event object.
   */
  public void mousePressed(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.last_x = mouse.getX();
      this.last_y = mouse.getY();
      this.rotate_world = true;
    }
  }

  /**
   * Stops rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse release event object.
   */
  public void mouseReleased(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.rotate_world = false;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @param drawable
   *          {@inheritDoc}
   * @param x
   *          {@inheritDoc}
   * @param y
   *          {@inheritDoc}
   * @param width
   *          {@inheritDoc}
   * @param height
   *          {@inheritDoc}
   */
  public void reshape(final GLAutoDrawable drawable, final int x, final int y,
      final int width, final int height) {
    final GL2 gl = (GL2)drawable.getGL();

    // prevent division by zero by ensuring window has height 1 at least
    final int newHeight = Math.max(1, height);

    // compute the aspect ratio
    final double ratio = (double) width / newHeight;

    // reset the projection coordinate system before modifying it
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();

    // set the viewport to be the entire window
    gl.glViewport(0, 0, width, newHeight);

    // set the clipping volume
    this.glu.gluPerspective(25, ratio, 0.1, 100);

    // camera positioned at (0,0,6), look at point (0,0,0), up vector (0,1,0)
    this.glu.gluLookAt(0, 0, 12, 0, 0, 0, 0, 1, 0);

    // switch back to model coordinate system
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

  private void toggleSelection(final Component component) {
    if (this.selectedComponents.contains(component)) {
      this.selectedComponents.remove(component);
      component.setColor(INACTIVE_COLOR);
    } else {
      this.selectedComponents.add(component);
      component.setColor(ACTIVE_COLOR);
    }
    this.stateChanged = true;
  }

  private void toggleSelection(final Leg leg) {
    if (this.selectedLegs.contains(leg)) {
      this.selectedLegs.remove(leg);
      this.selectedComponents.removeAll(leg.joints());
      for (final Component joint : leg.joints()) {
        joint.setColor(INACTIVE_COLOR);
      }
    } else {
      this.selectedLegs.add(leg);
    }
    this.stateChanged = true;
  }

@Override
public void dispose(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub
	
}
}
