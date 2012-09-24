package gui;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import tetris.Board;

//
public abstract class BoardProjection {
	abstract Shape project(Board b, int W, int H, int x, int y);

	protected static AffineTransform tconcat(AffineTransform a, AffineTransform b) {
		AffineTransform c = new AffineTransform(a);
		c.concatenate(b);
		return c;
	}
}
