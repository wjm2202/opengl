package nz.ac.aut.wjm2202.start;
/**
 * Assignment 2 Graphics
 * Glen Osborne 14852903 wjm2202
 */

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class XYZ {

	public XYZ() {

	}
	public void draw(GL2 gltwo) {

		GL2 gl = gltwo;//.getGL().getGL2();
		gl.glDisable(GL2.GL_LIGHTING);
	    gl.glColor3d(1.0,0.0,0.0); // red x
	    gl.glBegin(GL.GL_LINES);
	    // x aix
	 
	    gl.glVertex3d(-4.0, 0.0f, 0.0f);
	    gl.glVertex3d(4.0, 0.0f, 0.0f);
	 
	    // arrow
	    gl.glVertex3d(4.0, 0.0f, 0.0f);
	    gl.glVertex3d(3.0, 1.0f, 0.0f);
	 
	    gl.glVertex3d(4.0, 0.0f, 0.0f);
	    gl.glVertex3d(3.0, -1.0f, 0.0f);
	    gl.glEnd();
	  
	 
	 
	 
	    // y 
	    gl.glColor3d(0.0,1.0,0.0); // green y
	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex3d(0.0, -4.0f, 0.0f);
	    gl.glVertex3d(0.0, 4.0f, 0.0f);
	 
	    // arrow
	    gl.glVertex3d(0.0, 4.0f, 0.0f);
	    gl.glVertex3d(1.0, 3.0f, 0.0f);
	 
	    gl.glVertex3d(0.0, 4.0f, 0.0f);
	    gl.glVertex3d(-1.0, 3.0f, 0.0f);
	    gl.glEnd();
	   
	 
	    // z 
	    gl.glColor3d(0.0,0.0,1.0); // blue z
	    gl.glBegin(GL.GL_LINES);
	    gl.glVertex3d(0.0, 0.0f ,-4.0f );
	    gl.glVertex3d(0.0, 0.0f ,4.0f );
	 
	    // arrow
	    gl.glVertex3d(0.0, 0.0f ,4.0f );
	    gl.glVertex3d(0.0, 1.0f ,3.0f );
	 
	    gl.glVertex3d(0.0, 0.0f ,4.0f );
	    gl.glVertex3d(0.0, -1.0f ,3.0f );
	    gl.glEnd();
	    
	    gl.glEnable(GL2.GL_LIGHTING);
	   
	}
}
