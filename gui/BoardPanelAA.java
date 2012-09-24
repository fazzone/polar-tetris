package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import tetris.Board;

@SuppressWarnings("serial")
public class BoardPanelAA extends BoardPanel {
	Color lastDrawn[][];
	BufferedImage buf;
	Graphics2D gBuf;
	BoardProjection lastProj;
	public BoardPanelAA(Board b, Color c, BoardProjection prj) {
		super(b, c, prj);
		lastDrawn = new Color[b.width][b.height];
		dirtyAll();
	}
	void draw(Graphics2D g) {
		if (!bufValid())
			setupBuf();
		if (proj != lastProj) {
			clear(gBuf);
			dirtyAll();
		}
		for (int x=0; x<board.width; x++)
			for (int y=0; y<board.height; y++)
				if (getColor(x,y) != lastDrawn[x][y]) {
					drawCell(gBuf, x, y);
					lastDrawn[x][y] = getColor(x,y);
				}
				
		lastProj = proj;
		g.drawImage(buf, 0, 0, null);
	}
	boolean bufValid() {
		return buf != null && buf.getWidth() == getWidth() && buf.getHeight() == getHeight();
	}
	void setupBuf() {
		buf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		gBuf = buf.createGraphics();
		clear(gBuf);
		gBuf.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gBuf.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		dirtyAll();
	}
	void dirtyAll() {
		for (int x=0; x<lastDrawn.length; x++)
			Arrays.fill(lastDrawn[x], null);
	}
}
