package klotski.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import jdk.internal.util.xml.impl.Pair;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import klotski.model.Board;
import klotski.controller.AboutController;
import klotski.controller.LicenseController;
import klotski.controller.MovePieceController;
import klotski.controller.OpenController;
import klotski.controller.QuitController;
import klotski.controller.ResetPuzzleController;
import klotski.controller.SaveController;
import klotski.controller.SelectPieceController;
import klotski.controller.SetConfigController;
import klotski.controller.ShowMatrixController;

public class KlotskiApp extends JFrame {
	Board board;	
	PuzzleView puzzleView;
	JLabel movesCounter;
	JButton btnReset;
	JButton btnmove;
	Point storedPoint;

	//Necessary to suppress an Eclipse warning
	private static final long serialVersionUID = 5052390254637954176L;

	private JPanel contentPane;

	/** return actionable elements */
	public JLabel getMovesCounter() { return movesCounter; }
	public PuzzleView getPuzzleView() { return puzzleView; }
	public JButton getResetButton() { return btnReset; }
	public JButton getMoveButton() { return btnmove; }

	/**
	 * Create the frame.
	 */
	public KlotskiApp(Board b) {
		this.board = b;
		setTitle("Sliding Blocks");
		setFocusable(true);
		requestFocus();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JFileChooser fc = new JFileChooser();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);


		/********************\
		 *   Klotski Menu   *
		\********************/

		JMenu mnKlotski = new JMenu("Sliding Blocks");
		menuBar.add(mnKlotski);

		//		JMenuItem mntmSave = new JMenuItem("Save as...");
		//		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
		//				InputEvent.CTRL_MASK));
		//		mnKlotski.add(mntmSave);
		//
		//		mntmSave.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				if (fc.showSaveDialog(KlotskiApp.this) == 
		//						JFileChooser.APPROVE_OPTION) {
		//					String path = fc.getSelectedFile().getAbsolutePath();
		//					new SaveController(board, Paths.get(path)).save();
		//				}
		//			}
		//		});
		//
		//		JMenuItem mntmOpen = new JMenuItem("Open...");
		//		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 
		//				InputEvent.CTRL_MASK));
		//		mnKlotski.add(mntmOpen);
		//
		//		mntmOpen.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				if (fc.showOpenDialog(KlotskiApp.this) == 
		//						JFileChooser.APPROVE_OPTION) {
		//					String path = fc.getSelectedFile().getAbsolutePath();
		//					new OpenController(KlotskiApp.this, board, Paths.get(path))
		//					.open();
		//				}
		//			}
		//		});

		JMenuItem mntmQuit = new JMenuItem("Chiudi");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
				InputEvent.CTRL_MASK));
		mnKlotski.add(mntmQuit);

		mntmQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (new QuitController().confirm(KlotskiApp.this)) {
					KlotskiApp.this.dispose();
				}
			}
		});


		/*****************\
		 *   Puzzle Menu   *
		\*****************/

		JMenu mnPuzzle = new JMenu("Puzzle");
		menuBar.add(mnPuzzle);

		JMenuItem mntmReset = new JMenuItem("Resetta Puzzle");
		mntmReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmReset);

		mntmReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ResetPuzzleController(KlotskiApp.this, board).reset();
			}
		});

		JMenuItem mntmConfig1 = new JMenuItem("Livello 1");
		mntmConfig1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmConfig1);

		mntmConfig1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				KlotskiApp.this.dispose();
				Board b = new Board(1);
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
			}
		});

		JMenuItem mntmConfig2 = new JMenuItem("Livello 2");
		mntmConfig2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmConfig2);

		mntmConfig2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KlotskiApp.this.dispose();
				Board b = new Board(2);
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
			}
		});

		JMenuItem mntmConfig3 = new JMenuItem("Livello 3");
		mntmConfig3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmConfig3);

		mntmConfig3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KlotskiApp.this.dispose();
				Board b = new Board(3);
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
			}
		});

		JMenuItem mntmConfig4 = new JMenuItem("Livello 4");
		mntmConfig4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
				InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmConfig4);

		mntmConfig4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KlotskiApp.this.dispose();
				Board b = new Board(4);
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
			}
		});


		/*****************\
		 *   Help Menu   *
		\*****************/

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("Credits");
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnHelp.add(mntmAbout);

		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutController(KlotskiApp.this).about();
			}
		});

		JMenuItem mntmLicense = new JMenuItem("License");
		mntmLicense.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mnHelp.add(mntmLicense);

		mntmLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LicenseController(KlotskiApp.this).show();
			}
		});



		/********************\
		 *   Close Window   *
		\********************/

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (new QuitController().confirm(KlotskiApp.this)) {
					KlotskiApp.this.dispose();
				}
			}
		});



		/*******************\
		 *   Puzzle View   *
		\*******************/

		puzzleView = new PuzzleView(board);
		puzzleView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KlotskiApp.this.requestFocus();
				storedPoint = e.getPoint();
				new SelectPieceController(KlotskiApp.this, board).select(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				KlotskiApp.this.requestFocus();
				Point newPoint = e.getPoint();
				int dx = newPoint.x - storedPoint.x;
				int dy = newPoint.y - storedPoint.y;
				if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
					// mouse dragged
					if (Math.abs(dx) > Math.abs(dy)) {
						// horizontal drag
						if (dx > 0) {
							new MovePieceController(KlotskiApp.this, board)
							.move(1);
							board.addUserAction("L");
						} else {
							new MovePieceController(KlotskiApp.this, board)
							.move(3);
							board.addUserAction("R");
						}
					} else {
						// vertical drag
						if (dy > 0) {
							new MovePieceController(KlotskiApp.this, board)
							.move(2);
							board.addUserAction("U");
						} else {
							new MovePieceController(KlotskiApp.this, board)
							.move(0);
							board.addUserAction("D");
						}
					}
				}
			}
		});
		puzzleView.setBounds(12, 12, puzzleView.getPreferredSize().width,
				puzzleView.getPreferredSize().height);
		contentPane.add(puzzleView);


		/*************************\
		 *   Keyboard Listener   *
		\*************************/

		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int kc = e.getKeyCode();
				if (kc == KeyEvent.VK_UP || kc == KeyEvent.VK_W || 
						kc == KeyEvent.VK_K) {
					// up
					new MovePieceController(KlotskiApp.this, board).move(0);
				} else if (kc == KeyEvent.VK_RIGHT || kc == KeyEvent.VK_D ||
						kc == KeyEvent.VK_L) {
					// right
					new MovePieceController(KlotskiApp.this, board).move(1);
				} else if (kc == KeyEvent.VK_DOWN || kc == KeyEvent.VK_S ||
						kc == KeyEvent.VK_J) {
					// down
					new MovePieceController(KlotskiApp.this, board).move(2);
				} else if (kc == KeyEvent.VK_LEFT || kc == KeyEvent.VK_A ||
						kc == KeyEvent.VK_H) {
					// left
					new MovePieceController(KlotskiApp.this, board).move(3);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}

		});


		/*******************\
		 *   GUI BUttons   *
		\*******************/
//		JButton btnPause = new JButton("Pause");
//		btnPause.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("PAUSED SOLVER");
//			}
//		});
//		btnPause.setFocusable(false);
//		btnPause.setBounds(425, 120, 100, 25);
//		contentPane.add(btnPause);

//		JButton btnPlay = new JButton("Play");
//		btnPlay.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//
//				System.out.println("Avviata risoluzione automatica temporizzata");
//				Play();
//				
//
//			}
//		});
//		btnPlay.setFocusable(false);
//		btnPlay.setBounds(525, 120, 100, 25);
//		contentPane.add(btnPlay);

//		JButton btnShow = new JButton("Show");
//		btnShow.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				new ShowMatrixController(board).show();
//			}
//		});
//		btnShow.setFocusable(false);
//		btnShow.setBounds(525, 70, 100, 25);
//		contentPane.add(btnShow);

		JButton btnmove = new JButton("Fai Mossa");
		btnmove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(board.checkWin()==false) {
					board.moveBlock();
					new MovePieceController(KlotskiApp.this, board).move(board.getNextDirection());
				}
		
			}
		});
		btnmove.setFocusable(false);
		btnmove.setBounds(425, 20, 100, 25);
		contentPane.add(btnmove);

		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				new ResetPuzzleController(KlotskiApp.this, board).reset();
				
				
				Board b = new Board(board.getConfiguration());
				KlotskiApp app = new KlotskiApp(b);
				app.setVisible(true);
				KlotskiApp.this.dispose();
			}
		});
		btnReset.setFocusable(false);
		btnReset.setBounds(525, 20, 100, 25);
		contentPane.add(btnReset);

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (new QuitController().confirm(KlotskiApp.this)) {
					KlotskiApp.this.dispose();
				}
			}
		});

		/******************\
		 *   GUI Labels   *
		\******************/

		JLabel lblMoves = new JLabel("Moves:");
		lblMoves.setBounds(475, 330, 66, 15);
		contentPane.add(lblMoves);

		movesCounter = new JLabel("0");
		movesCounter.setBounds(550, 330, 66, 15);
		contentPane.add(movesCounter);
	}
	public void Play() {
		for(int i =0;i<board.getMoveSequence().size();i++) {
			board.moveBlock();
 			new MovePieceController(KlotskiApp.this, board).move(board.getNextDirection());
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	
}
