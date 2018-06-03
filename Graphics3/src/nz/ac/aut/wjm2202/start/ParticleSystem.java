package nz.ac.aut.wjm2202.start;

import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;

import nz.ac.aut.wjm2202.objects.GLModel;

public class ParticleSystem {
	private ArrayList<FireArrow> arrows;
	public static int INIT_POP = 100;
	private Random rand = new Random(System.currentTimeMillis());
	public GLModel missile = null;
	private int population = 0;
	
	public ParticleSystem(GLModel missile){
		arrows = new ArrayList<FireArrow>();
		this.missile = missile;
		init();
	}
	
	public void init(){
		for(int i =0; i<INIT_POP;i++){
			FireArrow fa = new FireArrow(missile, (rand.nextFloat()*200)-60, rand.nextFloat()* -600, rand.nextFloat()* 500);
			arrows.add(fa);
			this.population++;
		}
		for(FireArrow a : arrows){
			a.think();
		}
	}
	
	public void draw(GL2 gl){
		for (FireArrow p : arrows){
			p.think();
			p.draw(gl);
		}
	}
	
	public void think(){
		if (population <= INIT_POP){
			for (int i = 0; i< 10; i++){
				FireArrow sp = new FireArrow(missile, rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
			}
		}
	}

}
