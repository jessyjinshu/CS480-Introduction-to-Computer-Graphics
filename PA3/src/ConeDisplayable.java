import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;

/**
 * ConeDisplayable.java - an object which can draw itself using OpenGL
 */
public class ConeDisplayable implements Displayable{

	private int callListHandle;
//	private int leftwingHandle;
//	private int rightwingHandle;
	
	private float scale;
	private float xscale;
	private float yscale;
	private float zscale;
	private double xt;
	private double yt;
	private double zt;
	

	
	
	private GLUquadric qd;
	
	public ConeDisplayable(final float scale,final float xscale,final float yscale,final float zscale,double x,double y,double z) {
		this.scale = scale;
		this.xscale = xscale*scale;
		this.yscale = yscale*scale;
		this.zscale = zscale*scale;
		this.xt = x*scale;
		this.yt = y*scale;
		this.zt = z*scale;
				
		
	}
	
	/*
	 * Method to be called for data retrieving
	 * 
	 * */
	@Override
	public void draw(GL2 gl) {
		gl.glCallList(this.callListHandle);

	}

	/*
	 * Initialize our example model and store it in display list
	 * 
	 * */
	@Override
	public void initialize(GL2 gl) {
		this.callListHandle = gl.glGenLists(1);
		gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
		
		GLU glu = new GLU();
		this.qd = glu.gluNewQuadric();
		GLUT glut = new GLUT();

		gl.glPushMatrix();
		gl.glTranslated(xt, yt, zt);
		gl.glScalef(xscale, yscale, zscale);
		glut.glutSolidCone(0.3f, 0.7f, 36, 18);
		gl.glPopMatrix();
		

		gl.glEndList();
	}
	

}
