package nz.ac.aut.wjm2202.start;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

import nz.ac.aut.wjm2202.utils.Textures;

public class Building {

	public GL2 gl;
	public Textures texture;
	public Texture[] tex = new Texture[5];
	public static int BUILDING = 140;

	
	public QuadOfPoints[] q = new QuadOfPoints[5];
	public QuadOfPoints quad;
	public static double WIDTH = 10;
	public static double DEPTH = 10;
	public static double HEIGHT = 10;
	public static double X_NEG = -WIDTH;
	public static double X_POS = WIDTH;
	public static double Y_NEG = -HEIGHT;
	public static double Y_POS = HEIGHT;
	public static double Z_NEG = -DEPTH;
	public static double Z_POS = DEPTH;
	private float whiteSpecular[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	
	public Building(GL2 gl, Textures tex) {
		this.gl = gl;
		this.texture = tex;
		init();
	}
	
	public void draw(){
		gl.glPushMatrix();
			gl.glTranslated(-75.0, 0.0, -650.0);  // -760.0)
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
			gl.glTranslated(0.0, 10.0, 0.0);
			gl.glCallList(BUILDING);
		gl.glPopMatrix();

	}
	
	public void init(){
		tex[0] = texture.getTexture(11);   // window 
		tex[1] = tex[0];                   // window 
		tex[2] = texture.getTexture(13);   // wall
		tex[3] = tex[2];   				   // wall
		tex[4] = texture.getTexture(12);   // roof
		
		Point3f backbL = new Point3f(X_NEG, 0, Z_POS);              // back bottom left
		Point3f backtL = new Point3f(X_NEG, Y_POS, Z_POS);           // back top left
		Point3f backtr = new Point3f(X_POS, Y_POS, Z_POS);        // back top right
		Point3f backbr = new Point3f(X_POS, 0, Z_POS);           // back bottom right
		QuadOfPoints back = new QuadOfPoints(backbr, backtr, backtL, backbL);
		q[0] = back;
		Point3f frontfbr = new Point3f(X_NEG, 0, Z_NEG);              // right bottom right front
		Point3f frontftr = new Point3f(X_NEG, Y_POS, Z_NEG);           // rignt  top right front
		Point3f frontbtr = new Point3f(X_POS, Y_POS, Z_NEG);        // right  top rear
		Point3f frontbbr = new Point3f(X_POS, 0, Z_NEG);           // left bottom right
		QuadOfPoints front = new QuadOfPoints(frontfbr, frontftr, frontbtr, frontbbr);
		q[1] = front;
		Point3f leftfb = new Point3f(X_POS, 0, Z_NEG);              // left bottom left
		Point3f leftft = new Point3f(X_POS, Y_POS, Z_NEG);           // left top left
		Point3f leftbt = new Point3f(X_POS, Y_POS, Z_POS);        // left top right
		Point3f leftbb = new Point3f(X_POS, 0, Z_POS);           // left bottom right
		QuadOfPoints left = new QuadOfPoints(leftfb, leftft, leftbt, leftbb);
		q[2] = left;
		Point3f rightfbr = new Point3f(X_NEG, 0, Z_NEG);              // right bottom right front
		Point3f rightftr = new Point3f(X_NEG, Y_POS, Z_NEG);           // rignt  top right front
		Point3f rightbtr = new Point3f(X_NEG, Y_POS, Z_POS);        // right  top rear
		Point3f rightbbr = new Point3f(X_NEG, 0, Z_POS);           // left bottom right
		QuadOfPoints right = new QuadOfPoints(rightbbr, rightbtr, rightftr, rightfbr );
		q[3] = right;
		Point3f toplf = new Point3f(X_NEG, Y_POS, Z_NEG);              // right bottom right front
		Point3f toplr = new Point3f(X_NEG, Y_POS, Z_POS);           // rignt  top right front
		Point3f toprr = new Point3f(X_POS, Y_POS, Z_POS);        // right  top rear
		Point3f toprf = new Point3f(X_POS, Y_POS, Z_NEG);           // left bottom right
		QuadOfPoints top = new QuadOfPoints(toplf, toplr, toprr, toprf);
		q[4] = top;
		buildingDL();
		
	}
	
	public void buildingDL(){
		gl.glNewList(BUILDING, GL2.GL_COMPILE);
		for(int i =0 ; i < q.length; i++){
			//System.out.println("number of sides draw "+ i);
			QuadOfPoints c = q[i];
			tex[i].enable(gl);                        // enable the texture
			tex[i].bind(gl);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, whiteSpecular, 0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, redDiffuse, 0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, zeroMaterial, 0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, noShininess, 0);
			gl.glMatrixMode(GL2.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslated(0.5, 0.5, 0.0);

			 if (i == 0) {
				gl.glRotated(-90, 0, 0, 1);   // back
			} else if (i == 1) {
				gl.glRotated(-90, 0, 0, 1);   // front 
			} else if (i == 2) {
				gl.glRotated(-90, 0, 0, 1);   // left
			} else if (i == 3) {
				gl.glRotated(-90, 0, 0, 1);   // right
			} else if (i == 4){
				gl.glRotated(90, 0, 0, 1);  // top
			}
			
			gl.glTranslated(-0.5, -0.5, 0.0);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[i].getImageTexCoords();
			gl.glVertex3d(c.p[0].x, c.p[0].y, c.p[0].z);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(c.p[1].x, c.p[1].y, c.p[1].z);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(c.p[2].x, c.p[2].y, c.p[2].z);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(c.p[3].x, c.p[3].y, c.p[3].z);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[i].disable(gl);
		}
	gl.glEndList();
	}

}
