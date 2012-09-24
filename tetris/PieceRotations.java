package tetris;

import java.awt.Point;

public class PieceRotations {
	//declared using 2-length int[]s for brevity and clarity
	private static final int[][] T_piece = {{0,0},{1,0},{1,1},{2,0}};
	static final Point[][] Ts = iterate_rotate(toPoints(T_piece), 4);
	
	private static final int[][] I_piece = {{0,0},{1,0},{2,0},{3,0}};
	static final Point[][] Is = iterate_rotate(toPoints(I_piece), 2);
	
	private static final int[][] S_piece = {{0,1},{1,0},{1,1},{2,0}};
	static final Point[][] Ss = iterate_rotate(toPoints(S_piece), 2);
	
	private static final int[][] Z_piece = {{0,0},{1,0},{1,1},{2,1}};
	static final Point[][] Zs = iterate_rotate(toPoints(Z_piece), 2);
	
	private static final int[][] O_piece = {{0,0},{0,1},{1,0},{1,1}};
	static final Point[][] Os = iterate_rotate(toPoints(O_piece), 1);
	
	private static final int[][] J_piece = {{0,0},{1,0},{2,0},{2,1}};
	static final Point[][] Js = iterate_rotate(toPoints(J_piece), 4);
	
	private static final int[][] L_piece = {{0,0},{0,1},{1,0},{2,0}};
	static final Point[][] Ls = iterate_rotate(toPoints(L_piece), 4);
 
	//array of int[2] -> array of Point
	private static Point[] toPoints(int[][] ias) {
		Point[] ps = new Point[ias.length];
		for (int i=0; i<ias.length; i++)
			ps[i] = new Point(ias[i][0], ias[i][1]);
		return ps;
	}
	private static Point[] rotate(Point[] in) {
		Point[] ps = new Point[in.length];
		for (int i=0; i<in.length; i++)
			ps[i] = new Point(1-in[i].y, in[i].x);
		return ps;
	}
	private static Point[][] iterate_rotate(Point[] in, int N) {
		Point[][] rs = new Point[N][];
		rs[0] = in;
		for (int i=1; i<N; i++)
			rs[i] = rotate(rs[i-1]);
		return rs;
	}
}