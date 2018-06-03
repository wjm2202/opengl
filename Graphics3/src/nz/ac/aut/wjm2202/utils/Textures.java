package nz.ac.aut.wjm2202.utils;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {
	
	GL2 gl;
	private int numTexures = 18;
	Texture[] textures = new Texture[numTexures];
	String[] fileNames = new String[numTexures];

	public Textures(GL2 gl) {
		this.gl = gl;
		this.init();
	}

	private void init(){
		fileNames[0] = "back.png";
		fileNames[1] = "front.png";
		fileNames[2] = "left.png";
		fileNames[3] = "right.png";
		fileNames[4] = "top.png";
		fileNames[5] = "grass.jpg";
		fileNames[6] = "water.jpg";
		fileNames[7] = "sand.jpg";
		fileNames[8] = "road.jpg";
		fileNames[9] = "forest.jpg";
		fileNames[10] = "flower.jpg";
		fileNames[11] = "window.jpg";
		fileNames[12] = "roof.jpg";
		fileNames[13] = "wall.jpg";
		fileNames[14] = "mud.jpg";
		fileNames[15] = "crops.jpg";
		fileNames[16] = "trees.jpg";
		fileNames[17] = "pond.jpg";
		
		this.importTextures();

	}
	private void importTextures(){
		for(int i =0 ; i< fileNames.length; i++){
			try{
				System.out.println(i+" texture path: "+"assets/textures/"+fileNames[i]);
				textures[i] = TextureIO.newTexture(new File("assets/textures/"+fileNames[i]), true);           // mini mapped
				textures[i].isUsingAutoMipmapGeneration();
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_NEAREST  );
				textures[i].enable(gl);
				textures[i].bind(gl);
			}catch(IOException e){
				System.out.println("error in Textures "+e);
			}
		}
	}
	public Texture getTexture(int texNum){
		Texture texture = null;
		if(texNum+1 <= textures.length){
			return textures[texNum];
		} else {
			try{
				texture = TextureIO.newTexture(new File("assets/textures/grass.jpg"), true);// mip mapped
			}catch(IOException e){
				System.out.println("error in Textures "+e);
			}
		}
		return texture;
	}
}
