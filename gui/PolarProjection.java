package gui;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import tetris.Board;

public class PolarProjection extends BoardProjection {
	int centerR = 50;
	Board board;
	int W, H;
	double projTheta(int x) {
		return x * 2*Math.PI / board.width;
	}
	double projRadius(int y) {
		double R = getMaxRadius();
		return y * R / board.height;
	}
	double getMaxRadius() {
		return Math.min(W, H) / 2 - centerR;
	}
	AffineTransform getTransform(int x, int yy) {
		int y = board.height - 1 - yy;
		AffineTransform rot = AffineTransform.getRotateInstance(projTheta(x));
		AffineTransform trans = AffineTransform.getTranslateInstance(0, projRadius(y));
		AffineTransform center = AffineTransform.getTranslateInstance(W/2, H/2);
		return tconcat(center, tconcat(rot, trans));
	}

	Shape getShape(int x, int yy) {
		int y = board.height - 1 - yy;
		double minR = centerR + projRadius(y);
		double maxR = centerR + projRadius(y + 1);
		double arcang = Math.abs(projTheta(x) - projTheta(x + 1));
		//s = r * theta
		double minW = minR * arcang;
		double maxW = maxR * arcang;
		
		Rectangle2D minRect = new Rectangle2D.Double(-minW/2, centerR, minW, maxR - minR);
		Rectangle2D maxRect = new Rectangle2D.Double(-maxW/2, centerR, maxW, maxR - minR); 
		double ax = minRect.getX(),				ay = minRect.getY();
		double bx = ax + minRect.getWidth(), 	by = ay;
		double cx = bx, 						cy = ay + minRect.getHeight();
		double dx = ax, 						dy = cy;
		cx = maxRect.getX() + maxRect.getWidth();
		dx = maxRect.getX();
		int[] xs = {(int)ax, (int)bx, (int)cx, (int)dx, (int)ax};
		int[] ys = {(int)ay, (int)by, (int)cy, (int)dy, (int)ay};
		return new Polygon(xs, ys, 4);
	}
	public Shape project(int x, int y) {
		return getTransform(x,y).createTransformedShape(getShape(x,y));
	}
	Shape project(Board b, int x, int y, int w, int h) {
		board = b;
		W = w;
		H = h;
		Shape s = project(x,y);
		return s;
	}
	
}
