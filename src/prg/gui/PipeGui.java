package prg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jetstorm.stormFrame.StormConstraints;
import org.jetstorm.stormFrame.StormFrame;

import prg.com.Board;

public class PipeGui extends StormFrame implements MouseListener, Runnable
{
	public static void main(final String[] args)
	{
		PipeGui pipe = new PipeGui();
		pipe.setVisible(true);
	}
	public static final String mp3path = "";
	private static final long serialVersionUID = -3073330793054821525L;
	private Thread thread;
	private PipeScreen screen;
	private Board board;
	private int timer;
	public PipeGui()
	{
		super("Pipes", false, false);
	}

	@Override
	public void actionHandler(Object source)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionListeners()
	{
		screen.addMouseListener(this);
	}

	@Override
	public void buildDefaultElements()
	{
		buildElement(screen, new StormConstraints());
	}

	@Override
	public void configureElements()
	{
		screen = new PipeScreen();
		screen.setBackground(Color.BLACK);
		screen.setPreferredSize(new Dimension(340, 360));
		restart();
	}
	private void restart()
	{
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		screen.setBoard(null);
		board = new Board(10, 10);
		screen.setBoard(board);
		timer = board.getHeight()*board.getWidth()/2;
		screen.timer = timer;
	}

	public void mouseClicked(MouseEvent arg0)
	{
		
	}

	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	public void mouseExited(MouseEvent arg0)
	{
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		int xPos = arg0.getX();
		int yPos = arg0.getY();
		if(xPos >= PipeScreen.BORDERWIDTH && xPos < screen.getWidth() - PipeScreen.BORDERWIDTH &&
				yPos >= PipeScreen.BORDERWIDTH + PipeScreen.TITLEHEIGHT && yPos < screen.getHeight() - PipeScreen.BORDERWIDTH) {
			xPos = (xPos - PipeScreen.BORDERWIDTH)/ PipeScreen.TILESIZE;
			yPos = (yPos - PipeScreen.BORDERWIDTH - PipeScreen.TITLEHEIGHT)/ PipeScreen.TILESIZE;
			board.rotate(xPos, yPos);
			screen.paint(screen.getGraphics());
		}
	}

	public void mouseReleased(MouseEvent arg0)
	{
		
	}

	public void run()
	{
		int counter = 0;
		while(true) {
			if(!this.isFocused()) {
				screen.paused = true;
			} else {
				screen.paused = false;
			}
			if(counter == 10) {
				counter = 0;
				if(screen.getGraphics() != null) {
					screen.paint(screen.getGraphics());
				}
				if(timer > 0) {
					//timer--;
					screen.timer = timer;
				} else {
					restart();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(this.isFocused()) {
				counter++;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
