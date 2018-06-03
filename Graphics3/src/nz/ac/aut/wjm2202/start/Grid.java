package nz.ac.aut.wjm2202.start;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

import nz.ac.aut.wjm2202.rendereer.Rendereer;
import nz.ac.aut.wjm2202.utils.Textures;

public class Grid {

	public GL2 gl;
	public double Y = -100;
	public Textures texture;
	char[][] map = new char[165][165];
	private int numtex = 10;
	public Texture[] tex = new Texture[numtex];
	public static int GRASS = 1;
	public static int WATER = 2;
	public static int SAND = 3;
	public static int ROAD = 4;
	public static int FOREST = 5;
	public static int FLOWER = 6;
	public static int MUD = 7;
	public static int CROPS = 8;
	public static int TREES = 9;
	public static int POND = 10;
	private float zeroMaterial[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	private float blueDiffuse[] = { 0.1f, 0.5f, 0.8f, 1.0f };
	private float whiteSpecular[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	private float noShininess[] = { 0.0f }; // no highlight
	private float highShininess[] = { 20.0f }; // small, sharp highlight

	
	public Grid(GL2 gltwo) {
		gl = gltwo;
		init();
		grassDl();
		waterDl();
		sandDl();
		roadDl();
		forestDl();
		flowerDl();
		mudDl();
		cropsDl();
		treesDl();
		pondDl();
	}
	
	public void draw(){
		int X = -1;
		int Z = -1;
		for(double z = SkyCoords.Z_NEG; z <= SkyCoords.Z_POS; z +=10){
			Z++;
			X = 0;
			//System.out.println();
			for(double x = SkyCoords.X_NEG ;x <= SkyCoords.X_POS; x +=10){
				X++;
				if(map[Z][X] == '0'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(GRASS);
					gl.glPopMatrix();
				} else if (map[Z][X] == '1'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(WATER);
					gl.glPopMatrix();
				} else if (map[Z][X] == '2'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(SAND);
					gl.glPopMatrix();
				} else if (map[Z][X] == '3'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glRotated(90, 0, 1, 0);
						gl.glCallList(ROAD);
					gl.glPopMatrix();
				} else if (map[Z][X] == '4'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
					gl.glCallList(ROAD);
					gl.glPopMatrix();
				}else if (map[Z][X] == '5'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(FOREST);
					gl.glPopMatrix();
				}
				else if (map[Z][X] == '6'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(FLOWER);
					gl.glPopMatrix();
				}
				else if (map[Z][X] == '7'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(MUD);
					gl.glPopMatrix();
				}
				else if (map[Z][X] == '8'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(CROPS);
					gl.glPopMatrix();
				}
				else if (map[Z][X] == '9'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(TREES);
					gl.glPopMatrix();
				}
				else if (map[Z][X] == 'p'){
					gl.glPushMatrix();
						gl.glTranslated(x, 0, z);
						gl.glCallList(POND);
					gl.glPopMatrix();
				}

			}
		}
	}
	
	public void grassDl(){
		// grass display list
		gl.glNewList(GRASS, GL2.GL_COMPILE);
			tex[0].enable(gl);                        // enable the texture
			tex[0].bind(gl);
			//gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   whiteSpecular ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, noShininess, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, zeroMaterial, 0);  
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[0].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[0].disable(gl);
		gl.glEndList();
	}
	public void waterDl(){
		// grass display list
		gl.glNewList(WATER, GL2.GL_COMPILE);
			tex[1].enable(gl);                        // enable the texture
			tex[1].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);  
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[1].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[1].disable(gl);
		gl.glEndList();
	}
	public void sandDl(){
		// grass display list
		gl.glNewList(SAND, GL2.GL_COMPILE);
			tex[2].enable(gl);                        // enable the texture
			tex[2].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[2].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[2].disable(gl);
		gl.glEndList();
	}
	public void roadDl(){
		// grass display list
		gl.glNewList(ROAD, GL2.GL_COMPILE);
			tex[3].enable(gl);                        // enable the texture
			tex[3].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);  
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[3].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[3].disable(gl);
		gl.glEndList();
	}
	public void forestDl(){
		// grass display list
		gl.glNewList(FOREST, GL2.GL_COMPILE);
			tex[4].enable(gl);                        // enable the texture
			tex[4].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[4].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[4].disable(gl);
		gl.glEndList();
	}
	public void flowerDl(){
		// grass display list
		gl.glNewList(FLOWER, GL2.GL_COMPILE);
			tex[5].enable(gl);                        // enable the texture
			tex[5].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[5].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[5].disable(gl);
		gl.glEndList();
	}
	
	public void mudDl(){
		// grass display list
		gl.glNewList(MUD, GL2.GL_COMPILE);
			tex[6].enable(gl);                        // enable the texture
			tex[6].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[6].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[6].disable(gl);
		gl.glEndList();
	}
	
	public void cropsDl(){
		// grass display list
		gl.glNewList(CROPS, GL2.GL_COMPILE);
			tex[7].enable(gl);                        // enable the texture
			tex[7].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[7].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[7].disable(gl);
		gl.glEndList();
	}
	
	public void treesDl(){
		// grass display list
		gl.glNewList(TREES, GL2.GL_COMPILE);
			tex[8].enable(gl);                        // enable the texture
			tex[8].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[8].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[8].disable(gl);
		gl.glEndList();
	}
	
	public void pondDl(){
		// grass display list
		gl.glNewList(POND, GL2.GL_COMPILE);
			tex[9].enable(gl);                        // enable the texture
			tex[9].bind(gl);
			gl.glColor3d(1.0,1.0,1.0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   blueDiffuse ,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, highShininess,0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteSpecular, 0);   
			gl.glBegin(GL2.GL_QUADS);
			TextureCoords texcoords = tex[9].getImageTexCoords();
			gl.glVertex3d(0, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.bottom());
			gl.glVertex3d(10, 0, 0);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.left(), texcoords.top());
			gl.glVertex3d(10, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.top());
			gl.glVertex3d(0, 0, 10);
			gl.glNormal3d(0, 1, 0);
			gl.glTexCoord2f(texcoords.right(), texcoords.bottom());
			gl.glEnd();
			tex[9].disable(gl);
		gl.glEndList();
	}

	
	private void init(){
		texture = Rendereer.textures;
		tex[0] = texture.getTexture(5);   // grass
		tex[1] = texture.getTexture(6);   // water
		tex[2] = texture.getTexture(7);   // sand
		tex[3] = texture.getTexture(8);   // road
		tex[4] = texture.getTexture(9);   // forest floor
		tex[5] = texture.getTexture(10);  // flower beds
		tex[6] = texture.getTexture(14);  // mud
		tex[7] = texture.getTexture(15);  // crops
		tex[8] = texture.getTexture(16);  // trees
		tex[9] = texture.getTexture(17);  // pond
		readMap();

	}
	private void readMap(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("assets/map.txt"));
			String line;
			int count = 0;
			while((line = reader.readLine()) != null){
				char[] temp = line.toCharArray();
				for(int c = 0; c < temp.length; c++){
					//  y row x column
					map[count][c] = temp[c];
				}
				count++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found grid");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception in grid");
			e.printStackTrace();
		}
	}
	public void printMap(){
		for(int x = 0; x < 160; x++){
			for(int y = 0; y< 160; y++){
				System.out.print(map[x][y]);
			}
			System.out.println();
		} 
	}
}
