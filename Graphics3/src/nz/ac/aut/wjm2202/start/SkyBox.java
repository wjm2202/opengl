package nz.ac.aut.wjm2202.start;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

import nz.ac.aut.wjm2202.rendereer.Rendereer;
import nz.ac.aut.wjm2202.utils.Textures;

public class SkyBox {
	
	GL2 gl;
	public Textures texture;
	public Texture[] tex = new Texture[5];
	private SkyCoords sky = new SkyCoords();
	private float whiteSpecular[] = { 1.0f, 1.0f, 0.0f, 1.0f };

	public SkyBox(GL2 gltwo) {
		gl = gltwo;
		init();
	}
	
	public void draw(){
		for(int i =0 ; i < sky.q.length; i++){
			//System.out.println("number of sides draw "+ i);
			QuadOfPoints q = sky.q[i];
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
			gl.glVertex3d(q.p[0].x, q.p[0].y, q.p[0].z);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(q.p[1].x, q.p[1].y, q.p[1].z);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(q.p[2].x, q.p[2].y, q.p[2].z);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(q.p[3].x, q.p[3].y, q.p[3].z);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[i].disable(gl);
		}

	}
	
	private void init(){
		texture = Rendereer.textures;
		tex[0] = texture.getTexture(0);          // back
		tex[1] = texture.getTexture(1);          // front
		tex[2] = texture.getTexture(2);          // left
		tex[3] = texture.getTexture(3);          // right
		tex[4] = texture.getTexture(4);          // top
	}

}
