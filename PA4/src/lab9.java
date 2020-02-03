//****************************************************************************
//       Example Main Program for CS480 PA1
//****************************************************************************
// Description: 
//   
//   This is a template program for the sketching tool.  
//
//     LEFTMOUSE: draw line segments 
//     RIGHTMOUSE: draw triangles 
//
//     The following keys control the program:
//
//		Q,q: quit 
//		C,c: clear polygon (set vertex count=0)
//		R,r: randomly change the color
//		S,s: toggle the smooth shading for triangle 
//			 (no smooth shading by default)
//		T,t: show testing examples
//		>:	 increase the step number for examples
//		<:   decrease the step number for examples
//
//****************************************************************************
// History :
//   Aug 2004 Created by Jianming Zhang based on the C
//   code by Stan Sclaroff
//   Nov 2014 modified to include test cases
//   Nov 5, 2019 Updated by Zezhou Sun
//   Dec 9, 2019 Updated by Jinshu Yang
//


import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.*; 
import java.awt.image.*;
//import java.io.File;
//import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

//import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl


public class lab9 extends JFrame
	implements GLEventListener, KeyListener, MouseListener, MouseMotionListener
{
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WINDOW_WIDTH=512;
	private static final int DEFAULT_WINDOW_HEIGHT=512;
	private final float DEFAULT_LINE_WIDTH=1.0f;

	private GLCapabilities capabilities;
	private GLCanvas canvas;
	private FPSAnimator animator;

	final private int numTestCase;
	private int testCase;
	public static BufferedImage buff;
	@SuppressWarnings("unused")
	private ColorType color;
	private Random rng;
	
	 // specular exponent for materials
	public static int ns=5; 
	
	public static boolean amb=true; 
	
	private static ArrayList<Point3D> lineSegs;
	private static ArrayList<Point3D> triangles;
	private boolean doSmoothShading;
	public static int Nsteps;

	/** The quaternion which controls the rotation of the world. */
    public static Quaternion viewing_quaternion = new Quaternion();
    public static Vector3D viewing_center = new Vector3D((float)(DEFAULT_WINDOW_WIDTH/2),(float)(DEFAULT_WINDOW_HEIGHT/2),(float)0.0);
    /** The last x and y coordinates of the mouse press. */
    private int last_x = 0, last_y = 0;
    /** Whether the world is being rotated. */
    private boolean rotate_world = false;
    public static DepthBuffer depthbuff;
    public static Boolean doSmooth;
    private Scene scene;
    private boolean specular, diffuse, ambient;
    
    private final String Flat = "Flat";
	private final String Phong = "Phong";
	private final String Gouraud = "Gouraud";
	
	private boolean light1, light2, light3;
	
	private String shadedType = Flat ;
	
	private float ka,ks,kd;
	
	private float translateX,translateY,translateZ, scaleX,scaleY, scaleZ , rotateX,rotateY,rotateZ;
    
	public lab9()
	{
	    capabilities = new GLCapabilities(null);
	    capabilities.setDoubleBuffered(true);  // Enable Double buffering

	    canvas  = new GLCanvas(capabilities);
	    canvas.addGLEventListener(this);
	    canvas.addMouseListener(this);
	    canvas.addMouseMotionListener(this);
	    canvas.addKeyListener(this);
	    canvas.setAutoSwapBufferMode(true); // true by default. Just to be explicit
	    canvas.setFocusable(true);
	    getContentPane().add(canvas);

	    animator = new FPSAnimator(canvas, 60); // drive the display loop @ 60 FPS

	    numTestCase = 3;
	    testCase = 0;
	    Nsteps = 12;

	    setTitle("CS480/680 Lab 11");
	    setSize( DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    setResizable(false);
	    
	    rng = new Random();
	    color = new ColorType(1.0f,0.0f,0.0f);
	    lineSegs = new ArrayList<Point3D>();
	    triangles = new ArrayList<Point3D>();
	    doSmoothShading = false;
	    specular = true;
	    diffuse = true;
	    ambient = true;
	    light1 = true;
	    light2 = true;
	    light3 = true;
	    ka =0;
	    ks = 0;
	    kd =0;
	    translateX = 0;
	    translateY=0;
	    translateZ=0;
	    scaleX = 1;
	    scaleY = 1;
	    scaleZ = 1;
	    rotateX = 0;
	    rotateY = 0;
	    rotateZ = 0;
	    
	    depthbuff = new DepthBuffer(DEFAULT_WINDOW_WIDTH,DEFAULT_WINDOW_HEIGHT);
	    
	}

	public void run()
	{
		animator.start();
	}

	public static void main( String[] args )
	{
	    lab9 P = new lab9();
	    P.run();
	}

	//*********************************************** 
	//  GLEventListener Interfaces
	//*********************************************** 
	public void init( GLAutoDrawable drawable) 
	{
	    GL gl = drawable.getGL();
	    gl.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f);
	    gl.glLineWidth( DEFAULT_LINE_WIDTH );
	    Dimension sz = this.getContentPane().getSize();
	    buff = new BufferedImage(sz.width,sz.height,BufferedImage.TYPE_3BYTE_BGR);
	    clearPixelBuffer();
	    
	    scene = new Scene(Nsteps,Nsteps);

	    scene.setScene0();
	}

	// Redisplaying graphics
	public void display(GLAutoDrawable drawable)
	{
	    GL2 gl = drawable.getGL().getGL2();
	    WritableRaster wr = buff.getRaster();
	    DataBufferByte dbb = (DataBufferByte) wr.getDataBuffer();
	    byte[] data = dbb.getData();

	    gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
	    gl.glDrawPixels (buff.getWidth(), buff.getHeight(),
                GL2.GL_BGR, GL2.GL_UNSIGNED_BYTE,
                ByteBuffer.wrap(data));
        
	    drawTestCase();
	}

	// Window size change
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		// deliberately left blank
	}
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
	      boolean deviceChanged)
	{
		// deliberately left blank
	}
	
	static void clearPixelBuffer()
	{
		
		lineSegs.clear();
    	    triangles.clear();
		Graphics2D g = buff.createGraphics();
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
	    g.dispose();
	    depthbuff.depthclear();
	    
	    
	}
	
	// drawTest
	void drawTestCase()
	{  
		/* clear the window and vertex state */
		
		clearPixelBuffer();
        scene.setNsteps(Nsteps);
        scene.setNS(ns);
        scene.setSDA(specular, diffuse, ambient);
        scene.setShaded(shadedType);
        scene.setLight(light1, light2, light3);
        
        

		switch (testCase){
    	case 0:
    		scene.setScene0();
    		break;
    	case 1:
    		scene.setScene1();
    		break;
    	case 2:
    		scene.setScene2();
    		break;
		
		}
		
		scene.translate(translateX,translateY,translateZ);
		//scene.Scale(scaleX, scaleY, scaleZ);
		//scene.Rotate(rotateX, rotateY, rotateZ);
		scene.cameraRotate(viewing_center, viewing_quaternion);
		scene.drawScene();
		
		
	}
	


	//*********************************************** 
	//          KeyListener Interfaces
	//*********************************************** 
	public void keyTyped(KeyEvent key)
	{
	//      Q,q: quit 
	//      C,c: clear polygon (set vertex count=0)
	//		>:	 increase the step number for Nstpes
	//		<:   decrease the step number for Nsteps
	//     +,-:  increase or decrease NS (specular component)

	    switch ( key.getKeyChar() ) 
	    {
	    case 'Q' :
	    case 'q' : 
	    	new Thread()
	    	{
	          	public void run() { animator.stop(); }
	        }.start();
	        System.exit(0);
	        break;
	    case 'R' :
	    case 'r' :
	    	scene = new Scene(Nsteps,Nsteps);
	    	
	    	break;
	    case 'C' :
	    case 'c' :
	    viewing_quaternion = new Quaternion();
	    translateX =0;
	    translateY =0;
	    translateZ =0;
	    rotateX =0;
	    rotateY =0;
	    rotateZ =0;
	    scaleX = 1;
	    scaleY = 1;
	    scaleZ = 1;
	    	clearPixelBuffer();
	    	break;
	    case 'f' :
	    case 'F' :
	    	shadedType = Flat; //flat shading
	    	break;
	    case 'P' :
	    case 'p' :
	    	shadedType = Phong; // phong shading
	    	break;
	    case 'G' :
	    case 'g' :
	    	shadedType = Gouraud; // gouraud shading
	    	break;
	    	
	    case 'T' :
	    case 't' : 
	    	testCase = (testCase+1)%numTestCase; //run testcases
	    	
	      break; 
	    case '<':  
	        Nsteps = Nsteps < 4 ? Nsteps: Nsteps / 2;
	        drawTestCase();
	        break;
	    case '>':
	        Nsteps = Nsteps > 190 ? Nsteps: Nsteps * 2;
	        
	        drawTestCase();
	        break;
	    case '+':
	    	    ns++;
	        drawTestCase();
	        break;
	    case '_':
	    		if (ns>0){
	    		ns--;}
	        drawTestCase();
	    		break;
	    		
	    case 'd':
	    case 'D'://diffuse
	    		diffuse = !diffuse;
	    		drawTestCase();
	    		break;
	    case 'A':
	    case 'a': //ambient
    			ambient = !ambient;
    			drawTestCase();
    			break;
	    case 's': 
	    case 'S'://specular
	    		specular = !specular;
    			drawTestCase();
    			break;
    			
	    case '1': //light1 on/off 
	    		light1 =!light1;
    			drawTestCase();
    			break;
	    case '2': //light2 on/off 
    		light2 =!light2;
			drawTestCase();
			break;
	    case '3':
    		light3 =!light3;
			drawTestCase();
			break;
	    case '4': //ambient off
    		amb = false;
			drawTestCase();
			break;
	    case '5': //ambient on
    		amb = true;
			drawTestCase();
			break;
	    case '[':
	    		scene.changeKA(false);
	    		drawTestCase();
	    		break;
	    case ']':
    		
    		scene.changeKA(true);
    		drawTestCase();
    		break;
    		
	    case '-':
    		scene.changeKD(false);
    		drawTestCase();
    		break;
	    case '=':
		
		scene.changeKD(true);
		drawTestCase();
		break;
		
    case ',':
		scene.changeKS(false);
		drawTestCase();
		break;
    case '.':
	
	scene.changeKS(true);
	drawTestCase();
	break;
	
    case 'x': //translate on x direction
    translateX += 20;
    drawTestCase();
    break;
    case 'X': //translate on x direction
        translateX -= 20;
        drawTestCase();
        break;
    case 'y': //translate on y direction
        translateY += 20;
        drawTestCase();
        break;
    case 'Y': //translate on y direction
        translateY -= 20;
            drawTestCase();
            break;
    case 'z': //translate on z direction
            translateZ += 20;
            drawTestCase();
            break;
    case 'Z': //translate on z direction
                translateZ -= 20;
                drawTestCase();
                break;
    case 'b':
        scaleX += 0.2;
        drawTestCase();
        break;
    case 'B':
		scaleX	 -= 0.2;
            drawTestCase();
            break;
    case 'n':
        scaleY += 0.2;
        drawTestCase();
        break;
    case 'N':
		scaleY	 -= 0.2;
            drawTestCase();
            break;
    case 'm':
        scaleZ += 0.2;
        drawTestCase();
        break;
    case 'M':
		scaleZ	 -= 0.2;
            drawTestCase();
            break;
    case '8':
        rotateX += Math.PI/8;
        drawTestCase();
        break;
    case '9':
    		rotateY += Math.PI/8;
            drawTestCase();
            break;
    case '0':
		rotateZ += Math.PI/8;
        drawTestCase();
        break;
	    		
	    default :
	        break;
	    }
	}

	public void keyPressed(KeyEvent key)
	{
	    switch (key.getKeyCode()) 
	    {
	    case KeyEvent.VK_ESCAPE:
	    	new Thread()
	        {
	    		public void run()
	    		{
	    			animator.stop();
	    		}
	        }.start();
	        System.exit(0);
	        break;
	      default:
	        break;
	    }
	}

	public void keyReleased(KeyEvent key)
	{
		// deliberately left blank
	}

	//************************************************** 
	// MouseListener and MouseMotionListener Interfaces
	//************************************************** 
	public void mouseClicked(MouseEvent mouse)
	{
		// deliberately left blank
	}
	  public void mousePressed(MouseEvent mouse)
	  {
	    int button = mouse.getButton();
	    if ( button == MouseEvent.BUTTON1 )
	    {
	      last_x = mouse.getX();
	      last_y = mouse.getY();
	      rotate_world = true;
	    }
	  }

	  public void mouseReleased(MouseEvent mouse)
	  {
	    int button = mouse.getButton();
	    if ( button == MouseEvent.BUTTON1 )
	    {
	      rotate_world = false;
	    }
	  }

	public void mouseMoved( MouseEvent mouse)
	{
		// Deliberately left blank
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
	      final float magnitude = (float)Math.sqrt(dx * dx + dy * dy);
	      if(magnitude > 0.0001)
	      {
	    	  // define axis perpendicular to (dx,-dy,0)
	    	  // use -y because origin is in upper lefthand corner of the window
	    	  final float[] axis = new float[] { -(float) (dy / magnitude),
	    			  (float) (dx / magnitude), 0 };

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

	  }
	  
	public void mouseEntered( MouseEvent mouse)
	{
		// Deliberately left blank
	}

	public void mouseExited( MouseEvent mouse)
	{
		// Deliberately left blank
	} 


	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}