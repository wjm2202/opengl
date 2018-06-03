package nz.ac.aut.wjm2202.utils;

import com.jogamp.opengl.GL2;

import nz.ac.aut.wjm2202.rendereer.Rendereer;
import nz.ac.aut.wjm2202.start.Camera;

public class SceenLighting {

	public GL2 gl;
	// position of Light 0
	public Rendereer rend;
	public Camera camera;
	private float lightPosition[] = { 0.0f, 775.0f, 0.0f, 1.0f };
	private float planePosition[] = { 0.0f, 100.0f, 0.0f, 1.0f };
	private float planefwd[] = {0.0f, 0.0f, 0.0f};
	public float[] spot_dir = {0.0f, -1.0f, 0.0f};
	public float[] spot_fwd = {0.0f, 0.0f, 1.0f};
	float spot_angle=20.0f;
	float spot_angle2=10.0f;
	// amount of global ambient light
	float globalAmbientLight[] = { 1.0f, 1.0f, 1.0f, 1 };
	
	public SceenLighting(GL2 gl, Rendereer rend, Camera camera) {
		this.rend = rend;
		this.camera = camera;
		this.gl = gl;
		init();
	}
	
	public float[] convert(double[] plane){
		float[] temp = new float[3];
		temp[0] = (float)plane[0];
		temp[1] = (float)plane[1];
		temp[2] = (float)plane[2];
		return temp;
	}
	
	public void think(){
		float[] temp = convert(rend.getPlane());
		planePosition[0] = temp[0];
		planePosition[1] = temp[1]-1f;
		planePosition[2] = temp[2];
		planefwd[2] = (float) Math.cos(Math.toRadians(rend.yawAngle));
		planefwd[1] = (float) rend.pitchAngle/360;
		planefwd[0] = (float) Math.sin(Math.toRadians(rend.yawAngle));
		
	}
	
	public void draw(){
		think();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		//gl.glLoadIdentity();

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, planePosition, 0);
	    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION,spot_dir,0);
	    gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF,(float)spot_angle);
	      // "smoothing" the border of the lightcone
	      // change this for effect
	    gl.glLighti(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 20);
		
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, planePosition, 0);
	    gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION,planefwd,0);
	    gl.glLightf(GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF,(float)spot_angle2);
	      // "smoothing" the border of the lightcone
	      // change this for effect
	    gl.glLighti(GL2.GL_LIGHT2, GL2.GL_SPOT_EXPONENT, 10); 
	}
	
	public void init(){
		// parameters for light 0
		float ambientLight[] = { 0.6f, 0.6f, 0.6f, 1 }; // no ambient
		float diffuseLight[] = { 0.6f, 0.6f, 0.6f, 1 }; // white light for diffuse
		float specularLight[] = { 1, 1, 1, 1 }; // white light for specular 

		// setup the light 0 properties
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);   
		
        float spot_ambient[] =  {0.8f,0.8f,0.8f,1.0f };
        float spot_diffuse[] =  {0.8f,0.8f,0.8f,1.0f };
        float spot_specular[] =  {0.8f,0.8f,0.8f,1.0f };
        
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT,  spot_ambient,0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE,  spot_diffuse,0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, spot_specular,0);
		
	    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION,  planePosition,0);
	    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION,spot_dir,0);
	    gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF,(float)spot_angle);
	    gl.glLighti(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 20);
	    
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT,  spot_ambient,0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE,  spot_diffuse,0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, spot_specular,0);
		
	    gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION,  planePosition,0);
	    gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION,spot_fwd,0);
	    gl.glLightf(GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF,(float)spot_angle2);
	    gl.glLighti(GL2.GL_LIGHT2, GL2.GL_SPOT_EXPONENT, 20);   
	    
	    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbientLight, 0);
	    
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHT2);
        gl.glEnable(GL2.GL_NORMALIZE);
        
        //gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LESS);
		
	} 

}
