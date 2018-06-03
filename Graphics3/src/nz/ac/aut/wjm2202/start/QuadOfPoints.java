package nz.ac.aut.wjm2202.start;

public class QuadOfPoints {
	
	public Point3f[] p = new Point3f[4];
	
	public QuadOfPoints(Point3f first, Point3f second, Point3f third, Point3f fourth) {
		p[0] = first;
		p[1] = second;
		p[2] = third;
		p[3] = fourth;
	}
}
