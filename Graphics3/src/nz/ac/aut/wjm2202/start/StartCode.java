package nz.ac.aut.wjm2202.start;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import nz.ac.aut.wjm2202.rendereer.Rendereer;
/**
 * assignmnet 3
 * @author glen osborne 14852903
 *
 */
public class StartCode implements GLEventListener, KeyListener{
	
	private static int WIN_HEIGHT = 800;
	private static int WIN_WIDTH = 1200;
	double windowWidth      = 1184;             // 1x1
	double windowHeight     = 761;
	public static GLU glu;
	private static final int FPS = 60; // animator's target frames per second

	public StartCode() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		glu = new GLU();
		Frame frame = new Frame("Paper Plane World");
		GLCanvas canvas = new GLCanvas();
		StartCode app = new StartCode();
		canvas.addGLEventListener(app);
		//canvas.addKeyListener(app);
		frame.add(canvas);
		frame.addKeyListener(app);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(() -> {
					animator.stop();
					System.exit(0);
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.setFocusable(true);
		canvas.requestFocus();
        //frame.setUndecorated(true);     // no decoration such as title bar
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);  // full screen mode
		animator.start();
		frame.requestFocusInWindow();
		
	}
	
	private static Rendereer rendereer;

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		switch (code) {

			case KeyEvent.VK_UP: {
				rendereer.up = true;
				rendereer.displayLists.up = true;
				break;
			}
			case KeyEvent.VK_DOWN: {
				rendereer.down = true;
				rendereer.displayLists.down = true;
				break;
			}
			case KeyEvent.VK_LEFT: {
				rendereer.left = true;
				rendereer.displayLists.left = true;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rendereer.right = true;
				rendereer.displayLists.right = true;
				break;
			}

			case KeyEvent.VK_W: {
				rendereer.moveF = true;
				break;
			}
			case KeyEvent.VK_S: {
				rendereer.moveB = true;
				break;
			}
			case KeyEvent.VK_A: {
				rendereer.straifL = true;
				break;
			}
			case KeyEvent.VK_D: {
				rendereer.straifR = true;
				break;
			}
			case KeyEvent.VK_E: {
				rendereer.moveU = true;
				break;
			}
			case KeyEvent.VK_C: {
				rendereer.moveD = true;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {

			case KeyEvent.VK_UP: {
				rendereer.up = false;
				rendereer.displayLists.up = false;
				break;
			}
			case KeyEvent.VK_DOWN: {
				rendereer.down = false;
				rendereer.displayLists.down = false;
				break;
			}
			case KeyEvent.VK_LEFT: {
				rendereer.left = false;
				rendereer.displayLists.left = false;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rendereer.right = false;
				rendereer.displayLists.right = false;
				break;
			}

			case KeyEvent.VK_W: {
				rendereer.moveF = false;
				break;
			}
			case KeyEvent.VK_S: {
				rendereer.moveB = false;
				break;
			}
			case KeyEvent.VK_A: {
				rendereer.straifL = false;
				break;
			}
			case KeyEvent.VK_D: {
				rendereer.straifR = false;
				break;
			}
			case KeyEvent.VK_E: {
				rendereer.moveU = false;
				break;
			}
			case KeyEvent.VK_C: {
				rendereer.moveD = false;
				break;
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		rendereer.draw();
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		rendereer = new Rendereer(gl);
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
	    gl.glClearDepth(1.0f);      // set clear depth value to farthest
	    gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
	    gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
	    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
	    gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		
		
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int width, int height) {
		GL2 gl = arg0.getGL().getGL2();
		newWindowSize(gl, width, height);
		
	}
    public void newWindowSize(GL2 gl, int width, int height) {
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

}
