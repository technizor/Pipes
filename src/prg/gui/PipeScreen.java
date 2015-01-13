package prg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import prg.com.Board;
import prg.com.Pipe;

public class PipeScreen extends JPanel
{
	private static final long serialVersionUID = 5713297013196067362L;
	private static final String pipeTilePath = "icon/pipesTile.png";
	private static final String timeMessagePath = "icon/timeMessage.png";
	private static final String gamePausedPath = "icon/gamePaused.png";
	private static final String backgroundPath = "icon/background.png";
	private BufferedImage tileset;
	private BufferedImage timeMessage;
	private BufferedImage gamePaused;
	private BufferedImage background;
	private Board board;
	public static final int BORDERWIDTH = 10;
	public static final int TITLEHEIGHT = 20;
	public static final int TILESIZE = 32;
	public static final int PIPEWIDTH = 12;
	public static final int PIPEINSET = 10;
	public int timer;
	public boolean paused;
	public PipeScreen()
	{
		try {
			tileset = ImageIO.read(PipeScreen.class.getResource(pipeTilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			timeMessage = ImageIO.read(PipeScreen.class.getResource(timeMessagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			gamePaused = ImageIO.read(PipeScreen.class.getResource(gamePausedPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			background = ImageIO.read(PipeScreen.class.getResource(backgroundPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		Image bufferImage = createImage(getWidth(), getHeight());
		Graphics bufferGraphics = bufferImage.getGraphics();
		Graphics2D g2D = (Graphics2D) bufferGraphics;
		g2D.clearRect(0, 0, getHeight(), getWidth());
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.drawImage(background, BORDERWIDTH, BORDERWIDTH+TITLEHEIGHT, this);
		if(board != null) {
			if(paused) {
				g2D.drawImage(gamePaused, getWidth()/2-100, getHeight()/2-30, this);
			} else {
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						Pipe pipe = board.getBoard()[i][j];
						if(pipe != null) {
							g2D.drawImage((Image) tileset.getSubimage(pipe.getOrientation()*TILESIZE, 
									pipe.getType()*TILESIZE + (board.getConnected(i, j) ? TILESIZE*4 : 0), 32, 32),
									BORDERWIDTH+i*TILESIZE, BORDERWIDTH+TITLEHEIGHT+j*TILESIZE, this);
						}
					}
				}
			}
			g2D.setColor(Color.WHITE);
			g2D.drawString("Time Remaining: " + timer, BORDERWIDTH, BORDERWIDTH*2);
			if(timer == 0) {
				g2D.drawImage(timeMessage, getWidth()/2-100, getHeight()/2-30, this);
			}
		} else {
			
		}
		g.drawImage(bufferImage, 0, 0, this);
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}
	public void paintWarning(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.BLACK);
		g2D.fillRect(getWidth()/2-50, getHeight()/2-10, 100, 20);
		g2D.setColor(Color.WHITE);
		g2D.drawString("Too Slow!", getWidth()/2, getHeight()/2);
	}
}
