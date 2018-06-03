package nz.ac.aut.wjm2202.objects;

import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import nz.ac.aut.wjm2202.utils.Textures;

public class CreateDisplayLists {

	public GL2 gl;
	private GLModel planeModel = null;
	private GLModel trees = null;
	public GLModel missile = null;
	public GLModel cartoon = null;
	public GLModel car = null;
	public double turn = 1;
	public double climb = 1;
	public double climbRate = 0.0;
	public int numModels = 6;
	public boolean left, right, up, down = false;
	public String[] modelNames = new String[numModels];
	public String[] materials = new String[numModels];
	public double[][] treeLoc = new double[120][120];
	public double[] carLoc = new double[] {-72, 1, -600};
	public double[] carLoc2 = new double[] {-78, 1, 580};
	public double[] carLoc3 = new double[] {-78, 1, 300};
	public double[] carLoc4 = new double[] {-78, 1, 0};
	public double carSpeed = 5.0;
	Textures textures;
	Texture texture;
	
	private Random rand = new Random(System.currentTimeMillis());
	
	public CreateDisplayLists(GL2 gl, Textures textures) {
		this.gl = gl;
		this.textures = textures;
		init();
	}
	public void draw(double x, double y, double z, double pitch, double yaw){
		// plane
		think();
		gl.glPushMatrix();
			gl.glColor3d(1, 1, 1);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,   whiteSpecular ,0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, noShininess, 0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blueDiffuse, 0);
			//gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, zeroMaterial, 0);
			//texture.bind(gl);
			//texture.enable(gl); 
			//climbRate = pitch * 0.1;
			//System.out.println("climbrate: "+climbRate);
			gl.glTranslated(x,y,z);
			gl.glRotated(4, 0, 0, 0);
			gl.glRotated(90, 0, 1, 0);
			
			if(up){
				gl.glRotated(pitch*0.25, 1, 0, 0);
			}else if (down){
				gl.glRotated(pitch*0.25, 1, 0, 0);
			}
			//gl.glRotated(pitch, 1, 0, 0);
			if(left){
				gl.glRotated(yaw-turn, 0, 1, 0);    
			}else if(right){
				gl.glRotated(yaw+turn, 0, 1, 0);        
			}else{
				gl.glRotated(yaw, 0, 1, 0);
			}
			gl.glScaled(0.1, 0.1, 0.1);
			planeModel.opengldraw(gl);
			//texture.disable(gl);
		gl.glPopMatrix(); 
		
		
		// trees left
		gl.glPushMatrix();
		int tx = 0;
		int tz = 0;
		gl.glScaled(0.5, 0.5, 0.5);
			for(int w = 20; w < 200; w += 20){
				if((tx%50)==0){
					tx++;
				}
				for(int d = 80; d < 700; d += 20){
					if((d%50)==0){
						tz++;
					}
					gl.glPushMatrix();
						gl.glTranslated(w+treeLoc[tx][tz],10,d+treeLoc[tx][tz]);
						gl.glRotated(treeLoc[tx][tz], 0, 1, 0);
						trees.opengldraw(gl);
					gl.glPopMatrix();
				}
			}

		gl.glPopMatrix();
		 // trees right
		gl.glPushMatrix();
		int trx = 0;
		int trz = 0;
		gl.glScaled(0.5, 0.5, 0.5);
			for(int w = 200; w < 400; w += 20){
				if((trx%50)==0){
					trx++;
				}
				for(int d = 80; d < 700; d += 20){
					if((d%50)==0){
						trz++;
					}
					gl.glPushMatrix();
						gl.glTranslated(w+treeLoc[trx][trz],10,d+treeLoc[trx][trz]);
						gl.glRotated(treeLoc[trx][trz], 0, 1, 0);
						trees.opengldraw(gl);
					gl.glPopMatrix();
				}
			}

		gl.glPopMatrix();
		// car for road
		gl.glPushMatrix();

		gl.glTranslated(carLoc[0], carLoc[1], carLoc[2]);
			car.opengldraw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(carLoc2[0], carLoc2[1], carLoc2[2]);
			car.opengldraw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(carLoc3[0], carLoc3[1], carLoc3[2]);
			car.opengldraw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(carLoc4[0], carLoc4[1], carLoc4[2]);
			car.opengldraw(gl);
		gl.glPopMatrix();
	/*	gl.glPushMatrix();
		trx = 0;
	    trz = 0;
		gl.glScaled(0.5, 0.5, 0.5);
			for(int w = 600; w < 800; w += 20){
				if((trx%50)==0){
					trx++;
				}
				for(int d = 80; d < 700; d += 20){
					if((d%50)==0){
						trz++;
					}
					gl.glPushMatrix();
						gl.glTranslated(w+treeLoc[trx][trz],10,d+treeLoc[trx][trz]);
						gl.glRotated(-90, 1, 0, 0);
						gl.glRotated(treeLoc[trx][trz], 0, 1, 0);
						gl.glDisable(GL2.GL_LIGHTING);
						cartoon.opengldraw(gl);
						gl.glEnable(GL2.GL_LIGHTING);
					gl.glPopMatrix();
				}
			}

		gl.glPopMatrix();  */
		
                
	}
	
	public void think(){
		carLoc[2] += carSpeed;
		carLoc2[2] -= carSpeed;
		carLoc3[2] -= carSpeed;
		carLoc4[2] -= carSpeed;
		if(carLoc[2] > 580){
			carLoc[2] = -580;
		}
		if(carLoc2[2] < -580){
			carLoc2[2] = 580;
		}
		if(carLoc3[2] < -580){
			carLoc3[2] = 580;
		}  
		if(carLoc4[2] < -580){
			carLoc4[2] = 580;
		}
	}

	
	public void init(){
		modelNames[0] = "untitled.obj";
		materials[0] = "untitled.mtl";
		modelNames[1] = "Lowpoly_tree_sample.obj";
		materials[1] = "Lowpoly_tree_sample.mtl";
		modelNames[2] = "missile.obj";
		materials[2] = "missile.mtl";
		modelNames[3] = "tree.obj";
		materials[3] = "tree.mtl";
		modelNames[4] = "free_car_1.obj";
		materials[4] = "free_car_1.mtl";
		
		for(int w = 0; w < 110; w++){
			for(int d = 0; d < 110; d++){
				treeLoc[d][w] = rand.nextDouble()*100;
			}
		} 
		
		//texture = textures.getTexture(14);
		if (loadModels() == false){
			System.out.println("model didnt load");
			System.exit(1);
		}else{
			System.out.println("model loaded!");
		}
		
	}
	private Boolean loadModels(){
		planeModel = ModelLoaderOBJ.LoadModel("assets/models/"+modelNames[0], "assets/models/"+materials[0], gl);
		trees = ModelLoaderOBJ.LoadModel("assets/models/"+modelNames[1], "assets/models/"+materials[1], gl);
		missile = ModelLoaderOBJ.LoadModel("assets/models/"+modelNames[2], "assets/models/"+materials[2], gl);
		cartoon = ModelLoaderOBJ.LoadModel("assets/models/"+modelNames[3], "assets/models/"+materials[3], gl);
		car = ModelLoaderOBJ.LoadModel("assets/models/"+modelNames[4], "assets/models/"+materials[4], gl);
		if(planeModel == null){
			return false;
		}
		return true;
	}
	
	public GLModel getMissile(){
		return missile;
	}

}
