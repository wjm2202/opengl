package nz.ac.aut.wjm2202.start;

/**
 * Assignment 3 Graphics
 * Glen Osborne 14852903 wjm2202
 */

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import nz.ac.aut.wjm2202.rendereer.Rendereer;

public class Camera {
	
	private static final double FOV = 60;
	
	public double windowWidth      = 1184;             // 1x1
	public double windowHeight     = 761;
	public GLUquadric sphere;
	public GLU glu;
	public GL2 gl;
	public HeadsUpDisplay HUD;
	 

	public  double MOVEMENT_SPEED = 0.1;
	public float MAX_PITCH = 85.0f;
	public float MIN_PITCH = -85.0f;
	public float pitchAngle = 0.0f;
	public float pitchAdjust = 0.1f;
	public float startPitchAdjust = 0.1f;
	public float yawAngle = 0.0f;
	public float yawAdjust = 0.5f;
	public float startYawAdjust = 0.5f;
	public double distXZ = 0;
	public double lookAtDistence = 10.2f;
	public double INCREMENT = 1;
	public double startVelocity = 1;
    
	// the point to look at
	public double[] cameraEye = {0.0, 10.0, -60.0};
	public double[] lookAt = {0, 0, 0};     // the camera is looking towards
	public double[] UP = {0.0, 1.0, 0.0};
	public double[] plane = {0.0, 10.0, 0.0};
	public double[] headsUP = {0.0, 0.0, 0.0};
	public float[] spot_dir = {0.0f, 1.0f, 0.0f};
	public float[] fogColor = {0.4f,0.4f,0.4f,0.01f};
	
	
	public Rendereer rend;
	
	public Camera(Rendereer rend, GL2 gl, HeadsUpDisplay HUD){
		this.rend = rend;
		this.gl = gl;
		this.HUD = HUD;
		init();
	}
	
	public void draw(GL2 gl){
		// set up projection first
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		plane = rend.getPlane();
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);    // 
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
    
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 2500);
        // set up the camera position and orientation
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        // fog
        gl.glEnable(GL2.GL_FOG);
        gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
        gl.glFogf(GL2.GL_FOG_MODE, GL2.GL_EXP);
        gl.glFogf(GL2.GL_FOG_DENSITY, 0.001f); 

        updateCameraEye();
        /* camera location current, look in direction 
         * arrays of locations to "look at"
         * */
        headsUP = project(cameraEye, rend.yawAngle, rend.pitchAngle, -(lookAtDistence)-5);
		HUD.setHUD(headsUP, rend.pitchAngle, rend.yawAngle, -lookAtDistence);
        glu.gluLookAt(cameraEye[0], cameraEye[1], cameraEye[2], //eye
                	     plane[0], plane[1], plane[2], // looking at 
                         UP[0], UP[1], UP[2]); // up
	}
	
	private void init(){
		glu = new GLU();
		sphere = glu.gluNewQuadric();
		gl.setSwapInterval(1);
		// Setup the drawing area and shading mode
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
	}
	
	public double[] project(double[] startLoc, double yaw, double pitch, double distence){
		double[] projected = {0.0, 0.0, 0.0};
		double YRads = Math.toRadians(yaw);
		double PRads = Math.toRadians(pitch);
		double yDist = distence * Math.sin(PRads);           // get distence in xz plane
		projected[1] = startLoc[1] + yDist;                        // get y
		double xzDist = distence * Math.cos(PRads);          // distence in zx plane
		projected[0] = startLoc[0] + xzDist * Math.sin(YRads);     // get x
		projected[2] = startLoc[2] + xzDist * Math.cos(YRads);     // get z
		return projected;
	}


	public void updateCameraEye(){
		cameraEye = project(plane, rend.yawAngle, rend.pitchAngle, -lookAtDistence);
	}
    
	 /**
     * Passes a new window size to the camera.
     * This method should be called from the <code>reshape()</code> method
     * of the main program.
     *
     * @param width the new window width in pixels
     * @param height the new window height in pixels
     */
    public void newWindowSize(int width, int height) {
		System.out.println("width: "+width+" height: "+height);
        	windowWidth = Math.max(1.0, width);
        	windowHeight = Math.max(1.0, height);
	      if (windowHeight == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)windowWidth / (float)windowHeight;
	 
	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);
	 
	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar
	 
	      // Enable the model-view transform
	      gl.glMatrixMode(GL2.GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset

    }
    
    public double[] getLookAt(){
    	return lookAt;
    }

}
