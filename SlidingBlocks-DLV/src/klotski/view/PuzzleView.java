package klotski.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import klotski.model.Board;
import klotski.model.Piece;

public class PuzzleView extends JPanel {
	private static final long serialVersionUID = 3251334679791843551L;

	Board board;

	Image offScreenImage = null;
	Graphics offScreenGraphics = null;

	final int spacing = 5;

	final int squareSize = 100;

	public int getSquareSize() { return squareSize; }


	public PuzzleView(Board b) {
		this.board = b;
	}


	@Override
	public Dimension getPreferredSize() {
		int width = squareSize * board.getWidth();
		int height = squareSize * board.getHeight() + 6;

		return new Dimension(width, height);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (offScreenImage == null) {
			Dimension s = getPreferredSize();
			offScreenImage = this.createImage(s.width, s.height);
			if (offScreenImage == null) { return; }

			offScreenGraphics = offScreenImage.getGraphics();
			redraw();
		}

		g.drawImage(offScreenImage, 0, 0, this);

	}


	public void redraw() {
		if (offScreenImage == null) { return; }

		Dimension s = getPreferredSize();
		offScreenGraphics.clearRect(0, 0, s.width, s.height);

		Dimension s1 = getPreferredSize();
		offScreenGraphics.setColor(Color.decode("#dddddd"));
		offScreenGraphics.fillRect(0, 0, s1.width, s1.height);

		Piece[] p = board.getPieces();
		int[] currentDims;
		for (int i = 0; i < p.length; ++i) {
			currentDims = p[i].getDims();
			if (p[i] == board.getSelectedPiece())
				offScreenGraphics.setColor(Color.decode("#008cff")); // blue
			else if (i == 0)
				offScreenGraphics.setColor(Color.decode("#e06d78")); // red
			else
				offScreenGraphics.setColor(Color.decode("#fffee7"));
			offScreenGraphics.fillRect(currentDims[0] * squareSize + spacing,
					currentDims[1] * squareSize + spacing,
					currentDims[2] * squareSize - spacing * 2,
					currentDims[3] * squareSize - spacing * 2);

			offScreenGraphics.setColor(Color.decode("#222222"));
			offScreenGraphics.drawRect(currentDims[0] * squareSize + spacing,
					currentDims[1] * squareSize + spacing,
					currentDims[2] * squareSize - spacing * 2,
					currentDims[3] * squareSize - spacing * 2);
		}

		// LINEA ROSSA VITTORIA
		if(board.getConfiguration()==4) {
			offScreenGraphics.setColor(Color.decode("#e06d78"));
			offScreenGraphics.fillRect(100,500,squareSize*2,6);
		}else {
			offScreenGraphics.setColor(Color.decode("#e06d78"));
			offScreenGraphics.fillRect(0,board.getWinX()*100,6,squareSize);
		}

		if (board.checkWin()) {
			offScreenGraphics.setColor(Color.black);
			
			if(board.getConfiguration()==1) {
				offScreenGraphics.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
				offScreenGraphics.drawString("Congratulazioni!", 40, 72);
				offScreenGraphics.drawString("Hai vinto!", 90, 172);
			}
			else {
				offScreenGraphics.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,45));
				offScreenGraphics.drawString("Congratulazioni!", 10, 72);
				offScreenGraphics.drawString("Hai vinto!", 105, 172);
			}
				
		}
	}


	public void refresh() {
		if (offScreenImage == null) { return; }
		offScreenGraphics.clearRect(0, 0, offScreenImage.getWidth(this),
				offScreenImage.getHeight(this));
		redraw();
		repaint();
	}
}
