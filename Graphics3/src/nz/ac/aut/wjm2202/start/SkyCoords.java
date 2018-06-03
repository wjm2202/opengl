package nz.ac.aut.wjm2202.start;

public class SkyCoords {

	public QuadOfPoints[] q = new QuadOfPoints[5];
	public static double WIDTH = 800;
	public static double DEPTH = 800;
	public static double HEIGHT = 800;
	
	public static double X_NEG = -WIDTH;
	public static double X_POS = WIDTH;
	
	public static double Y_NEG = -HEIGHT;
	public static double Y_POS = HEIGHT;
	
	public static double Z_NEG = -DEPTH;
	public static double Z_POS = DEPTH;
	
	
	public SkyCoords() {
		init();
	}
	
	private void init(){
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
		
		
	}

}
