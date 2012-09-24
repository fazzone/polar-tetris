package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import tetris.Board;

@SuppressWarnings("serial")
public  class BoardPanel extends JPanel {
	final Board board;
	final Color clearColor;
	BoardProjection proj;
	Color[] cs = {Color.black, Color.darkGray, Color.darkGray.brighter()};
	
	public BoardPanel(Board b, Color c, BoardProjection prj) {
		board = b;
		this.clearColor = c;
		proj = prj;
	}
	public void paint(Graphics g) {
		draw((Graphics2D)g);
	}
	void draw(Graphics2D g) {
		clear(g);
		for (int x = 0; x < board.width; x++)
			for (int y = 0; y < board.height; y++)
				drawCell(g, x, y);
	}
	void drawCell(Graphics2D g, int x, int y) {
		AffineTransform tx = g.getTransform();
		g.setColor(getColor(x, y));
		g.fill(proj.project(board, x, y, getWidth(), getHeight()));
		g.setTransform(tx);
	}
	void clear(Graphics2D g) {
		g.setColor(clearColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	Color getColor(int x, int y) {
		if (board.get(x, y))
			return board.getColor(x,y);
		return cs[x % cs.length];
		
	}

}