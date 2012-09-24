package tetris;

import java.awt.Color;
import java.awt.Point;

//we can use Point[] (in fact Point[4]) for everything because
//each tetromino in tetris has 4 points
public enum Piece {
	T(PieceRotations.Ts, new Color(255,0,255)),
	I(PieceRotations.Is, new Color(0,213,213)),
	S(PieceRotations.Ss, new Color(0,177,0)),
	Z(PieceRotations.Zs, new Color(255,0,0)),
	O(PieceRotations.Os, new Color(209,209,0)),
	J(PieceRotations.Js, new Color(77,77,255)),
	L(PieceRotations.Ls, new Color(255,128,0));
	
	final Point[][] points;
	final Color color;
	static int probs[] = {1, 10, 1, 1, 1, 1, 1};
	Piece(Point[][] ps, Color c) {
		points = ps;
		color = c;
	}
	
	public static Piece random() {
	    return values()[(int)(Math.random() * values().length)];
	}
	
	public static Point[] translate(Point[] ps, int tx, int ty) {
		Point[] nps = new Point[4];
		for (int i=0; i<ps.length; i++)
			nps[i] = new Point(ps[i].x + tx, ps[i].y + ty);
		return nps;
	}
}