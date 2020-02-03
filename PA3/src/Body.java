 /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
/**
 * Body.java - draw the body part of the Ant
 */
public class Body extends Circular implements Displayable {
	
	private int callListHandle;
	float scale = 0.5f;
	
	public int get_handle() {
		return this.callListHandle;
	}
	
	public Body(double radius, GLUT glut) {
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
		
		gl.glTranslated(0, 0, -0.4*scale);
		gl.glScalef(0.7f*scale, 0.8f*scale, 1.2f*scale);
		this.glut().glutSolidSphere(this.radius(), 36, 18);
		gl.glPopMatrix();
		
		gl.glEndList();
	}
	
}