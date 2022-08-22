import com.sun.source.tree.ArrayAccessTree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author ivyzhang, CS10 Spring 2022 Polyline.java
 */
public class Polyline implements Shape {
	private Color color;
	ArrayList<Point> points; // an array list to store all the points
	int x1, y1, x2, y2;

	/**
	 * Constructor: set color and store the point information in points
	 */
	public Polyline(Point point, Color color){
		points = new ArrayList<>();
		x1 = point.x;
		y1 = point.y;
		points.add(point);
		this.color = color;
	}

	public Polyline(ArrayList<Point> points, Color color) {
		this.points = points;
		this.color = color;
	}

	// TODO: YOUR CODE HERE
	@Override
	public void moveBy(int dx, int dy) {
		for (int i=0; i<points.size();i++){
			points.get(i).translate(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean contains(int x, int y) {
		for (int i=0; i<points.size()-1;i++){
			if (Segment.pointToSegmentDistance(x, y, points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y) <= 10){
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (int i=0; i < points.size()-1; i++){
			// System.out.println(points.get(i).);
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
		}
	}

	@Override
	public String toString() {
		String s = "";

		for (Point p : points) {
			// System.out.println(p);
			s += (p.x + "," + p.y + ",");
		}

		return "polyline"+" "+s+" "+color.getRGB();
	}

	public void setEnd(Point p) {
		this.x1 = this.x2;
		this.y1 = this.y2;
		this.x2 = p.x;
		this.y2 = p.y;
		points.add(p);
	}
}
