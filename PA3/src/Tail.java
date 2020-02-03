 /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
/**
 * Tail.java - draw the tail part of the Ant
 */
public class Tail extends Circular implements Displayable {
	
	private int callListHandle;
	float scale = 0.5f;
	
	public int get_handle() {
		return this.callListHandle;
	}
	
	public Tail(double radius, GLUT glut) {
		super(radius, glut);
	}

	@Override
	public void draw(GL2 gl) {
		gl.glCallList(this.callListHandle);
		
	}

	@Override
	public void initialize(GL2 gl) {
		this.callListHandle = gl.glGenLists(1);
		
		gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
		
		gl.glPushMatrix();
		
		gl.glTranslated(0, 0.3*scale, -1.6*scale);
		gl.glScalef(1f*scale, 0.9f*scale, 1.3f*scale);
		this.glut().glutSolidSphere(this.radius(), 36, 18);
		gl.glPopMatrix();
		
		gl.glEndList();
	}
	
}
