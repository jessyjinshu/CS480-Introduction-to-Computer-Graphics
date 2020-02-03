 /**
 * @author Jinshu Yang U19470900 jinshuy@bu.edu
 * @since Fall 2019
 */
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

public class Body extends Circular implements Displayable {
	
	private int callListHandle;
	
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
		
		gl.glTranslated(0, 0, -0.4);
		gl.glScalef(0.7f, 0.8f, 1.2f);
		this.glut().glutSolidSphere(this.radius(), 36, 18);
		gl.glPopMatrix();
		
		gl.glEndList();
	}
	
}