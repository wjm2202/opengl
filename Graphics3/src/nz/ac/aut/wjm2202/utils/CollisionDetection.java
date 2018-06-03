package nz.ac.aut.wjm2202.utils;

import nz.ac.aut.wjm2202.rendereer.Rendereer;
import nz.ac.aut.wjm2202.start.SkyCoords;

public class CollisionDetection {
	
	public double[] check = new double[] {0.0, 0.0, 0.0};
	Rendereer rend;
	
	public CollisionDetection(Rendereer rend) {
		this.rend = rend;
	}
	
	public boolean think(){
		check = rend.plane;
		boolean crash = false;
		// x 
		if(check[0] < SkyCoords.X_NEG || check[0] > SkyCoords.X_POS){
			crash = true;
		}
		if(check[1] <= 0 || check[1] > SkyCoords.Y_POS){
			crash = true;
		}
		if(check[2] < SkyCoords.Z_NEG || check[2] > SkyCoords.Z_POS){
			crash = true;
		}
		return crash;
	}

}
