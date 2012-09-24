package gui;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import tetris.Board;

public class CartesianProjection extends BoardProjection {
	static final AffineTransform id = new AffineTransform();
	Shape project(Board b, int x, int y, int w, int h) {
		int cellSize  = Math.min(h / b.height, w / b.width); 	
		return new Rectangle(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
	}
}