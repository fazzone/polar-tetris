package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tetris.Game;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	Game game = new Game(16, 25, true);
	JLabel linesLabel = new JLabel("0");
	PolarProjection polar = new PolarProjection();
	CartesianProjection cartesian = new CartesianProjection();
	public GameFrame() {
		// standard JFrame stuff
		setSize(950, 810);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// the BoardPanel that shows the actual game was behaving strangely so I
		// put it in a box
		// doesn't seem to have fixed the problem (getWidth, Height would return
		// the size of the window)
		setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
		Box gameBox = new Box(BoxLayout.PAGE_AXIS);
		final BoardPanel bp = new BoardPanelAA(game.board, Color.white, cartesian);
		gameBox.add(bp);

		// side pane stuff; it's a box containing a board panel and another box
		Box sidePane = new Box(BoxLayout.PAGE_AXIS);
		Box scoreBox = new Box(BoxLayout.Y_AXIS);
		linesLabel.setFont(new Font("Arial", Font.BOLD, 72));
		linesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		scoreBox.add(linesLabel);
		sidePane.add(scoreBox);
		gameBox.setBorder(BorderFactory.createLineBorder(Color.black, 12));
		add(gameBox);
		add(sidePane);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_SPACE:
						// translate until we hit bottom or another piece
						while (game.translate(0, 1))
							;
						// do the new-piece related-stuff
						newPiece();
						break;
					case KeyEvent.VK_UP:
						game.rotate();
						break;
					case KeyEvent.VK_P:
						System.out.println("p");
						if (bp.proj == polar)
							bp.proj = cartesian;
						else bp.proj = polar;
						break;
				}
				
				// pressing ctrl-left or ctrl-right snap the piece to the
				// furthest-left and furthest-right position, respectively
				boolean tSucc;
				Point trans = getTranslate(e.getKeyCode());
				if (trans != null)
					do
						tSucc = game.translate(trans.x, trans.y);
					while (e.isControlDown() && tSucc);

				repaint();
			}
		});

		setVisible(true);

		// main loop
		while (true) {
			// not 100% sure this is necessary, but I've never had any bugs that
			// looked
			// like synchronization bugs...
			synchronized (game) {
				if (!game.translate(0, 1))
					newPiece();
				repaint();
			}
			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {}
		}
	}
	void newPiece() {
		game.newPiece();
		linesLabel.setText("" + game.lines);
	}
	// so the keylistener can be nice.
	static Point getTranslate(int keycode) {
		switch (keycode) {
			case KeyEvent.VK_LEFT:		return new Point(-1, 0);
			case KeyEvent.VK_RIGHT:		return new Point(1, 0);
			case KeyEvent.VK_DOWN:		return new Point(0, 1);
		}
		return null;
	}
	public static void main(String[] args) {
		new GameFrame();
	}
}