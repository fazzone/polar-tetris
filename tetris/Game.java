package tetris;

import java.awt.Point;

public class Game {
	public final Board board;
	Piece currentPiece, nextPiece = Piece.random();
	Point cLoc;
	int cRot = 0;
	public int lines = 0;
	private boolean wrapped;
	public Game(int xdim, int ydim, boolean wrap) {
		board = new Board(xdim, ydim);
		wrapped = wrap;
		newPiece();
	}
	public void newPiece() {
		currentPiece = nextPiece;
		nextPiece = Piece.random();
		cLoc = new Point(board.width / 2, 0);
		cRot = 0;
		lines += board.clearLines();
		board.setAll(currentPoints(), currentPiece.color);
	}
	Point[] currentPoints() {
		return wrap(Piece.translate(currentPiece.points[cRot], cLoc.x, cLoc.y));
	}
	Point[] wrap(Point[] ps) {
		if (!wrapped)
			return ps;
		Point[] nps = new Point[ps.length];
		int ni = 0;
		for (Point p : ps)
			nps[ni++] = wrap(p);
		return nps;
	}
	Point wrap(Point p) {
		if (p.x < 0)
			return new Point(board.width + p.x, p.y);
		return new Point(p.x % board.width, p.y);
	}
	boolean check(Point[] ps) {
		for (Point p : ps)
			if (p.x < 0 || p.y < 0 || p.x >= board.width || p.y >= board.height || board.get(p.x,p.y))
				return false;
		return true;
	}
	public boolean translate(int tx, int ty) {
		board.setAll(currentPoints(), Board.blank);
		boolean success = check(wrap(Piece.translate(currentPoints(), tx, ty)));

		if (success) {
			cLoc.x += tx;
			cLoc.y += ty;
		}
		board.setAll(currentPoints(), currentPiece.color);
		return success;
	}
	int newRot(int inc) {
		return (cRot + inc) % currentPiece.points.length;
	}
	public void rotate() {
		int oldRot = cRot;
		board.setAll(currentPoints(), Board.blank);
		cRot = (cRot + 1) % currentPiece.points.length;
		if (!check(currentPoints()))
			cRot = oldRot;
		board.setAll(currentPoints(), currentPiece.color);
	}
}