package nz.ac.aut.wjm2202.start;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import nz.ac.aut.wjm2202.rendereer.Rendereer;

public class HeadsUpDisplay {

	public GL2 gl;
	public GLUT glut;
	public Rendereer rend;
	public static int HUD = 23;
	public static int pitch = 24;
	public double HUD_X = 0.0f;
	public double HUD_Y = 0.0f;
	public double HUD_Z = 0.0f;
	public double[] HUDloc = {0.0, 0.0, 0.0};
	public double pitchAngle = 0.0f;
	public double yawAngle = 0.0f;
	public double indi = 0.00235294117647058823529411764706;
	public double INDI_Y = 0.0;
	public double center = 0.7;
	
	public HeadsUpDisplay(GL2 gl, Rendereer rend) {
		this.gl = gl;
		this.rend = rend;
		glut = new GLUT();
		init();
	}
	
	public void draw(){

		think();

		//border
		gl.glPushMatrix();
			gl.glCallList(HUD);
		gl.glPopMatrix();
		
		// pitch indicator box
		gl.glBegin(GL2.GL_QUADS);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4d(0, 1, 0, 0.4);
		gl.glLineWidth(2.0f);
	    gl.glBegin(GL2.GL_LINE_LOOP);
	    gl.glVertex2d(-0.9, 0.5);
	    gl.glVertex2d(-0.9, 0.9);
	    gl.glVertex2d(-0.5, 0.9);
	    gl.glVertex2d(-0.5, 0.5);
		gl.glEnd();
		// pitch indicator bar
		gl.glBegin(GL2.GL_QUADS);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4d(0, 1, 0, 0.4);
		gl.glLineWidth(2.0f);
	    gl.glBegin(GL2.GL_LINES);
	    gl.glVertex2d(-0.9, center+INDI_Y);
		gl.glVertex2d(-0.5, center+INDI_Y);
	    gl.glEnd();
	    // pitch value
	    if((pitchAngle < -40) || (pitchAngle > 40)){
	    	gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
	    } else {
	    	gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
	    }
	    gl.glRasterPos2d(-0.8, 0.4);
	    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Pitch: "+pitchAngle);
	    if(rend.plane[1] < 12){
	    	gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
	    } else {
	    	gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
	    }
	    gl.glRasterPos2d(-0.8, 0.3);
	    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Height: "+round(rend.plane[1], 2));
	    if(rend.velocity < 0.2){
	    	gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
	    } else {
	    	gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
	    }
	    gl.glRasterPos2d(-0.8, 0.2);
	    glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Speed: "+round(rend.velocity*12.5,2));
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private void init(){
		drawHUD();
		drawPitchIndicator();
	}
	
	private void think(){
		
		if(pitchAngle != 0.0f){
			if(pitchAngle != 0 ){
				INDI_Y = pitchAngle * indi*2;
			}else {
				INDI_Y = 0.0;
			}
		} 
	}
	
	private void drawHUD(){
		gl.glNewList(HUD, GL2.GL_COMPILE);
			//gl.glColor3d(1, 0, 0);
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			gl.glColor4d(0, 1, 0, 0.4);
			gl.glLineWidth(2.0f);
		    gl.glBegin(GL2.GL_LINE_LOOP);
		    gl.glVertex2d(-0.9, -0.9);
		    gl.glVertex2d(-0.9, 0.9);
		    gl.glVertex2d(0.9, 0.9);
		    gl.glVertex2d(0.9, -0.9);
		    gl.glEnd();
		gl.glEndList();
	}
	
	private void drawPitchIndicator(){
		gl.glNewList(pitch, GL2.GL_COMPILE);
			//gl.glColor3d(0, 1, 0);
			gl.glBegin(GL2.GL_LINES);
		    gl.glVertex2f(0.0f, 5.0f);
			gl.glVertex2f(5.0f, 5.0f);
			gl.glEnd();
		gl.glEndList();
	} 
	
	public void setHUD(double[] cam, double pitchAngle, double yawAngle, double distence){
		HUDloc = cam;
		this.yawAngle = yawAngle;
		this.pitchAngle = pitchAngle;
	}
	
	public double[] project(double[] startLoc, double yaw, double pitch, double distance){
		double[] projected = {0.0, 0.0, 0.0};
		double YRads = Math.toRadians(yaw);
		double PRads = Math.toRadians(pitch);
		double yDist = distance * Math.sin(PRads);           // get distence in xz plane
		projected[1] = startLoc[1] + yDist;                        // get y
		double xzDist = distance * Math.cos(PRads);          // distence in zx plane
		projected[0] = startLoc[0] + xzDist * Math.sin(YRads);     // get x
		projected[2] = startLoc[2] + xzDist * Math.cos(YRads);     // get z
		return projected;
	}

}
