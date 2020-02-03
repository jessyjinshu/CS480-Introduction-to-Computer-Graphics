/**
 * PA2.java - driver for the hand model simulation
 * 
 * History:
 * 
 * 15 October 2019
 * - finish for PA2 for HW
 * Jinshu Yang <jinshuy@bu.edu>
 * 
 * 13 September 2019
 * 
 * - Cleaned code for reuse
 * 
 * (Zezhou Sun <micou@bu.edu>)
 * 
 * 19 February 2011
 * 
 * - added documentation
 * 
 * (Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>)
 * 
 * 16 January 2008
 * 
 * - translated from C code by Stan Sclaroff
 * 
 * (Tai-Peng Tian <tiantp@gmail.com>)
 * 
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
 * The main class which drives the hand model simulation.
 * 
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2008
 */
public class PA2 extends JFrame implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	/**
	 * A finger which has a palm joint, a middle joint, and a distal joint.
	 * 
	 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
	 * @since Spring 2011
	 */

	/** The default width of the created window. */
	public static final int DEFAULT_WINDOW_HEIGHT = 512;
	/** The default height of the created window. */
	public static final int DEFAULT_WINDOW_WIDTH = 512;
	/** The initial position of the top level component in the scene. */
	public static final Point3D INITIAL_POSITION = new Point3D(0, 0, -3);
	/** The angle by which to rotate the joint on user request to rotate. */
	public static final double ROTATION_ANGLE = 2.0;
	/** Randomly generated serial version UID. */
	private static final long serialVersionUID = -7060944143920496524L;

	/**
	 * Runs the hand simulation in a single JFrame.
	 * 
	 * @param args This parameter is ignored.
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
	/** The OpenGL utility object. */
	private final GLU glu = new GLU();
	/** The last x and y coordinates of the mouse press. */
	private int last_x = 0, last_y = 0;
	/** Whether the world is being rotated. */
	private boolean rotate_world = false;
	/** The axis around which to rotate the selected joints. */
	private Axis selectedAxis = Axis.X;

	/** Whether the state of the model has been changed. */
	private boolean stateChanged = true;
	/** The quaternion which controls the rotation of the world. */
	private Quaternion viewing_quaternion = new Quaternion();

	//private final Arm arm;
	private final Ant ant;

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

		this.setTitle("CS480/CS680 : Hand Simulator");
		this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//this.arm = new Arm(INITIAL_POSITION, "arm");
		this.ant = new Ant(INITIAL_POSITION, "ant");
	}

	/**
	 * Redisplays the scene containing the hand model.
	 * 
	 * @param drawable The OpenGL drawable object with which to create OpenGL
	 *                 models.
	 */
	public void display(final GLAutoDrawable drawable) {
		final GL2 gl = (GL2) drawable.getGL();

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
			//this.arm.update(gl);
			this.ant.update(gl);
			this.stateChanged = false;
		}

		// redraw the components
		//this.arm.draw(gl);
		this.ant.draw(gl);
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param drawable      This parameter is ignored.
	 * @param modeChanged   This parameter is ignored.
	 * @param deviceChanged This parameter is ignored.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// intentionally unimplemented
	}

	/**
	 * Initializes the scene and model.
	 * 
	 * @param drawable {@inheritDoc}
	 */
	public void init(final GLAutoDrawable drawable) {
		final GL2 gl = (GL2) drawable.getGL();

		// perform any initialization needed by the hand model
		//this.arm.initialize(gl);
		this.ant.initialize(gl);

		// initially draw the scene
		//this.arm.update(gl);
		this.ant.update(gl);

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
	 * @param key The key press event object.
	 */
	public void keyPressed(final KeyEvent key) {
		BaseConfiguration bc = new BaseConfiguration(0, 0, 0);
		switch (key.getKeyCode()) {
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP:
			if (this.selectedAxis.equals(Axis.X)) {
				bc.setXAngle(ROTATION_ANGLE);
			} else if (this.selectedAxis.equals(Axis.Y)) {
				bc.setYAngle(ROTATION_ANGLE);
			} else if (this.selectedAxis.equals(Axis.Z)) {
				bc.setZAngle(ROTATION_ANGLE);
			}
			this.ant.changeSelected(bc);
			this.stateChanged = true;
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN:
			if (this.selectedAxis.equals(Axis.X)) {
				bc.setXAngle(-ROTATION_ANGLE);
			} else if (this.selectedAxis.equals(Axis.Y)) {
				bc.setYAngle(-ROTATION_ANGLE);
			} else if (this.selectedAxis.equals(Axis.Z)) {
				bc.setZAngle(-ROTATION_ANGLE);
			}
			this.ant.changeSelected(bc);
			this.stateChanged = true;
			break;
		default:
			break;
		}
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param key This parameter is ignored.
	 */
	public void keyReleased(final KeyEvent key) {
		// intentionally unimplemented
	}

	private final TestCases testCases = new TestCases();

	private void setModelState(final Map<String, Configuration> state) {

		this.ant.setModelStates(state);

		this.stateChanged = true;

	}

	/**
	 * Interprets typed keys according to the following scheme:
	 * 
	 * 1 : toggle the first left leg active in rotation
	 * 
	 * 2 : toggle the second left leg active in rotation
	 * 
	 * 3 : toggle the third left leg active in rotation
	 * 
	 * 4 : toggle the first right leg active in rotation
	 * 
	 * 5 : toggle the second right leg active in rotation
	 * 
	 * 6 : toggle the third right leg for rotation
	 * 
	 * 7 : toggle the middle body for rotation
	 * 
	 * 8 : toggle the body for rotation
	 * 
	 * 9 : toggle the head for rotation
	 * 
	 * 0 : toggle the tail for rotation
	 * 
	 * _ : toggle the left antenna
	 * 
	 * + : toggle the right antenna
	 * 
	 * X : use the X axis rotation at the active joint(s)
	 * 
	 * Y : use the Y axis rotation at the active joint(s)
	 * 
	 * Z : use the Z axis rotation at the active joint(s)
	 * 
	 * C : resets the hand to the stop sign
	 * 
	 * - : select the top joint of leg 
	 * 
	 * = : select the middle joint of leg
	 * 
	 * [ : select the end joint of leg
	 * 
	 * ( : select the top joint of antenna
	 * 
	 * ) : select the end joint of antenna
	 * 
	 * R : resets the view to the initial rotation
	 * 
	 * K : prints the angles of the five fingers for debugging purposes
	 * 
	 * Q, Esc : exits the program
	 * 
	 */
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

		// resets to the stop sign
		case 'C':
		case 'c':
			this.setModelState(this.testCases.stop());
			break;

		// set the state of the hand to the next test case
		case 'T':
		case 't':
			this.setModelState(this.testCases.next());
			break;

		// set the viewing quaternion to 0 rotation
		case 'R':
		case 'r':
			this.viewing_quaternion.reset();
			break;

		// Toggle which legs(s) are affected by the current rotation
		case '1':
			toggleSelectionLeg(0);
			break;
		case '2':
			toggleSelectionLeg(1);
			break;
		case '3':
			toggleSelectionLeg(2);
			break;
		case '4':
			toggleSelectionLeg(3);
			break;
		case '5':
			toggleSelectionLeg(4);
			break;
		case '6':
			toggleSelectionLeg(5);
			break;
		//toggle the middle body
		case '7':
			toggleSelection(0);
			break;
		//toggle the body
		case '8':
			toggleSelection(1);
			break;
		//toggle the head
		case '9':
			toggleSelection(2);
			break;
		//toggle the tail
		case '0':
			toggleSelection(3);
			break;
		//toggle the left antenna
		case '_':
			toggleSelectionAntenna(0);
			break;
		//toggle the right antenna
		case '+':
			toggleSelectionAntenna(1);
			break;
		//toggle part of the legs
		case '-': //top part
			for (final Ant.Leg leg :this.ant.selectedLegs) {
				this.ant.toggleSelectionLegParts(leg.endJoint());
				this.ant.toggleSelectionLegParts(leg.middleJoint());
				this.stateChanged = true;
		      }
			break;
		case '=': //middle part

			for (final Ant.Leg leg :this.ant.selectedLegs) {
				this.ant.toggleSelectionLegParts(leg.topJoint());
				this.ant.toggleSelectionLegParts(leg.endJoint());
				this.stateChanged = true;
		      }
			break;
		case '[': // end part
			for (final Ant.Leg leg :this.ant.selectedLegs) {
				this.ant.toggleSelectionLegParts(leg.topJoint());
				this.ant.toggleSelectionLegParts(leg.middleJoint());
				this.stateChanged = true;
		      }
			break;
		case ')': // end part of antenna
			for (final Ant.Antenna antenna :this.ant.selectedAntennas) {
				this.ant.toggleSelectionAntennaParts(antenna.topJoint());
				this.stateChanged = true;
		      }
			break;
		case '(': // top part of antenna
			for (final Ant.Antenna antenna :this.ant.selectedAntennas) {
				this.ant.toggleSelectionAntennaParts(antenna.endJoint());
				this.stateChanged = true;
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
	 * @param printStream The stream on which to print each of the components.
	 */
	private void printJoints(final PrintStream printStream) {
		this.ant.printJoints(printStream);
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param mouse This parameter is ignored.
	 */
	public void mouseClicked(MouseEvent mouse) {
		// intentionally unimplemented
	}

	/**
	 * Updates the rotation quaternion as the mouse is dragged.
	 * 
	 * @param mouse The mouse drag event object.
	 */
	public void mouseDragged(final MouseEvent mouse) {
		if (this.rotate_world) {
			// get the current position of the mouse
			final int x = mouse.getX();
			final int y = mouse.getY();

			// get the change in position from the previous one
			final int dx = x - this.last_x;
			final int dy = y - this.last_y;

			
			//compute the magnitude
			final float mag = (float)Math.sqrt(dx*dx+dy*dy);
			
			final float[] axis;
			
			//avoid divide by zero
			if(mag==0)
			{
				axis = new float[] {1,0,0};
			}
			else
			{
				
				axis = new float[] {(float)(dy/mag),(float)(dx/mag),0};
			}
			
			//compute the unit angle
			final float unit_rotation_angle = 3.1415927f/180.0f;
			
			//get the current rotation angle
			final float angle = mag*unit_rotation_angle;

			final float cos = (float)Math.cos(0.5f*angle);
			final float sin = (float)Math.sin(0.5f*angle);
			
			final Quaternion q = new Quaternion(cos,sin*axis[0],sin*axis[1],sin*axis[2]);

			this.viewing_quaternion = q.multiply(this.viewing_quaternion);
			

			// normalize to counteract accumulating round-off error
			this.viewing_quaternion.normalize();

			// save x, y as last x, y
			this.last_x = x;
			this.last_y = y;
		}
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param mouse This parameter is ignored.
	 */
	public void mouseEntered(MouseEvent mouse) {
		// intentionally unimplemented
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param mouse This parameter is ignored.
	 */
	public void mouseExited(MouseEvent mouse) {
		// intentionally unimplemented
	}

	/**
	 * This method is intentionally unimplemented.
	 * 
	 * @param mouse This parameter is ignored.
	 */
	public void mouseMoved(MouseEvent mouse) {
		// intentionally unimplemented
	}

	/**
	 * Starts rotating the world if the left mouse button was released.
	 * 
	 * @param mouse The mouse press event object.
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
	 * @param mouse The mouse release event object.
	 */
	public void mouseReleased(final MouseEvent mouse) {
		if (mouse.getButton() == MouseEvent.BUTTON1) {
			this.rotate_world = false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param drawable {@inheritDoc}
	 * @param x        {@inheritDoc}
	 * @param y        {@inheritDoc}
	 * @param width    {@inheritDoc}
	 * @param height   {@inheritDoc}
	 */
	public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
		final GL2 gl = (GL2) drawable.getGL();

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

	private void toggleSelection(final int componentNum) {
		this.ant.toggleSelection(componentNum);

		this.stateChanged = true;
	}
	//toggle the leg that selected by user
	private void toggleSelectionLeg(final int componentNum) {
		this.ant.toggleSelectionLeg(componentNum);

		this.stateChanged = true;
	}
	
	//toggle the antenna that selected by user
	private void toggleSelectionAntenna(final int componentNum) {
		this.ant.toggleSelectionAntenna(componentNum);
		this.stateChanged = true;
	}
	


	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}
}
