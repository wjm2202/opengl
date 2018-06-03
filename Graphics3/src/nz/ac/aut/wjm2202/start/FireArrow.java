package nz.ac.aut.wjm2202.start;

import java.util.Random;

import com.jogamp.opengl.GL2;

import nz.ac.aut.wjm2202.objects.GLModel;

public class FireArrow {

	public GL2 gl;
	public Point3f loc;
	private Random rand = new Random(System.currentTimeMillis());
	public float speed =0.8f;
	public GLModel missile = null;
	
	public FireArrow(GLModel missile, float x, float y, float z) {
		this.missile = missile;
		this.loc = new Point3f(x,y,z);
		this.speed = rand.nextFloat()*6;
	}
	
	public void draw(GL2 gl){
		think();
	    gl.glColor3d(1.0,0.0,0.0); // red 
	    gl.glPushMatrix();
	    	gl.glTranslated(loc.x-90, loc.y, loc.z);
	    	gl.glRotated(270, 0, 0, 1);
	    	gl.glScaled(3, 3, 3);
	    	missile.opengldraw(gl);
	    gl.glPopMatrix();
	}
	public void think(){
		loc.y += speed;
		if(loc.y >= 400){
			loc.y = 0.0f;
		}
	}


}
