package nz.ac.aut.wjm2202.rendereer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;

import nz.ac.aut.wjm2202.objects.CreateDisplayLists;
import nz.ac.aut.wjm2202.objects.GLModel;
import nz.ac.aut.wjm2202.start.Building;
import nz.ac.aut.wjm2202.start.Camera;
import nz.ac.aut.wjm2202.start.Grid;
import nz.ac.aut.wjm2202.start.HeadsUpDisplay;
import nz.ac.aut.wjm2202.start.ParticleSystem;
import nz.ac.aut.wjm2202.start.SkyBox;
import nz.ac.aut.wjm2202.start.XYZ;
import nz.ac.aut.wjm2202.utils.CollisionDetection;
import nz.ac.aut.wjm2202.utils.SceenLighting;
import nz.ac.aut.wjm2202.utils.Textures;

public class Rendereer {
	
	private GL2 gl;
	private XYZ xyz;
	public Camera camera;
	public GLUquadric sphere;
	public Grid grid;
	public GLU glu;
	public static Textures textures;
	public SkyBox sky;
	public CreateDisplayLists displayLists;
	public SceenLighting lights;
	public HeadsUpDisplay HUD;
	public Building building;
	public ParticleSystem fire;
	public CollisionDetection cd;
	public double[] HUDloc = {0.0, 0.0, 0.0};
	public static int PLANE = 100;
	public Texture texture;
	
	// plane move values
	public double[] plane = {-75.0, 75.0, -650.0};
	public double velocity = 0.5;
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
	public double lookAtDistence = 1.0f;
	public double animationDistance = 10.1f;
	public double INCREMENT = 1;
	public double startVelocity = 1;
	public double turnValue = 0.5;
	public boolean up, down, left, right, moveF, moveB, moveU, moveD, straifL, straifR = false;
	public boolean crash = false;

	public Rendereer(GL2 gltwo) {
		gl = gltwo;
		init();
	}
	
	public void draw(){
		
		think();
		crash = cd.think();
		if(crash && plane[1] > 0 ){
			velocity = 0;
			plane[1] -= 1.0;
		}else if (crash){
			velocity = 0;
		}
		
		// clear the depth and color buffers
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		camera.draw(gl);
		lights.draw();
		grid.draw();
		sky.draw();
		building.draw();
		xyz.draw(gl);
		fire.draw(gl);
		displayLists.draw(plane[0], plane[1], plane[2], pitchAngle, yawAngle);
		
		/**
		 * 
		 * draw the hud first in othographic projection
		 * the camera will change it for the rest of the screen back to projection
		 * push/pop attribute to make ot ortho only for hud
		 */

		gl.glPushAttrib(0);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();
			glu.gluOrtho2D(-100, 100, -100, 100);
			gl.glDisable(GL2.GL_DEPTH_TEST);
			gl.glDisable(GL2.GL_CULL_FACE);
			gl.glDisable(GL2.GL_TEXTURE_2D);
			gl.glDisable(GL2.GL_LIGHTING);
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();
		    gl.glColor3f( 1.0f, 1.0f, 1.0f);
			HUD.draw();
		gl.glPopAttrib();

		
		gl.glFlush();
	}
	
	private void think(){
		if(down){
			pitchAngle -= pitchAdjust*5;
			if(pitchAngle < MIN_PITCH){
				pitchAngle = MIN_PITCH;
			}
			//HUD.setHUD(camera.cameraEye, camera.pitchAngle, camera.yawAngle);

		}
		if(up){
			pitchAngle += pitchAdjust*5;
			if(pitchAngle > MAX_PITCH){
				pitchAngle = MAX_PITCH;
			}
			//HUD.setHUD(camera.cameraEye, camera.pitchAngle, camera.yawAngle);
		}
		if(left){
			yawAngle += yawAdjust*3;
			if(yawAngle >= 360){
				yawAngle = yawAngle%360;
			}
			plane = project(plane, yawAngle, pitchAngle, 0);
		}
		if(right){
			yawAngle -= yawAdjust*3;
			if(yawAngle <= 0.0f){
				yawAngle +=360.0f;
			}
			plane = project(plane, yawAngle, pitchAngle, 0);
		}
		if(moveF){
			plane = project(plane, yawAngle, pitchAngle, animationDistance*3);

		}
		if(moveB){
			plane = project(plane, yawAngle, pitchAngle, -animationDistance*3);

		}
		if(straifL){
			plane = this.project(plane, yawAngle + 90.0f, 0.0, animationDistance*0.9);

		}
		if(straifR){
			plane = this.project(plane, yawAngle + -90.0f, 0.0, animationDistance*0.9);

		}
		if(moveU){
			plane[1] = plane[1] + INCREMENT;
			plane = project(plane, yawAngle, pitchAngle, 0);
		}
		if(moveD){
			plane[1] = plane[1] - INCREMENT;
			plane = project(plane, yawAngle, pitchAngle, 0);
		}
		//HUD.setHUD(camera.cameraEye, camera.pitchAngle, camera.yawAngle, lookAtDistence);
		if(!crash){
			if(pitchAngle < 0 ){
				if(velocity < 1){
					velocity += 0.001;
				}
			} else if (pitchAngle > 0){
				if(velocity > 0.1){
					velocity -= 0.001;
				}
			}
			if(velocity > 2){
				velocity = 2;
			}else if (velocity < 0.2 ){
				plane[1] -= 0.5;
			}
		}

		plane = project(plane, yawAngle, pitchAngle, velocity);
	}
	
	public void update(){
		plane = project(plane, yawAngle, pitchAngle, lookAtDistence);
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
	
	private void init(){
		textures = new Textures(gl);
		glu = new GLU();
		xyz = new XYZ();
		building = new Building(gl, textures);
		HUD = new HeadsUpDisplay(gl, this);
		camera  = new Camera(this, gl, HUD);
		grid = new Grid(gl);
		sky = new SkyBox(gl);
		cd  = new CollisionDetection(this);
		lights = new SceenLighting(gl, this, camera);
		displayLists = new CreateDisplayLists(gl, textures);
		GLModel missile = displayLists.getMissile();
		fire = new ParticleSystem(missile);
		texture = textures.getTexture(14);
	}
	
	public double[] getPlane(){
		return plane;
	}

}
