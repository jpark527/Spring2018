package homeworkGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MovingRects extends JPanel {
	
	private JButton btn;
	private int x1, y1, x2, y2;
	private boolean switched, changeOfDirection1, changeOfDirection2, activated;
	
	private void changeDir() {
		if(Math.abs(x1-x2)<25 && Math.abs(y1-y2)<25) {
			changeOfDirection1 = !changeOfDirection1;
			changeOfDirection2 = !changeOfDirection2;
		}
		if(x1 >= 475 || y1 >=475) 
			changeOfDirection1 = false;
		else if(x1 <= 0 || y1 <= 0)
			changeOfDirection1 = true;
		
		if(x2 <=0 || y2 >=475)
			changeOfDirection2 = false;
		else if(x2 >= 475 || y2 <= 0)
			changeOfDirection2 = true;
	}
	
	public MovingRects() {
		create();
		wire();
	}
	
	
	public void create() {
		changeOfDirection1=changeOfDirection2=switched=true;
		activated=false;
		x1=y1=y2=0;
		x2=475;
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		JPanel bottom = new JPanel();
		btn = new JButton("CLICK");
		bottom.add(btn);
		add(bottom, BorderLayout.SOUTH);
		bottom.setBackground(Color.LIGHT_GRAY);
	}
	
	public void wire() {
		MouseListener mlis = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseReleased(MouseEvent e) {
				if(switched) {
					x1 = e.getX();
					y1 = e.getY();
				} else {
					x2 = e.getX();
					y2 = e.getY();
				}
				switched = !switched;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }
			
		};
		addMouseListener(mlis);
		ActionListener move = new MyActionListener();
		Timer movement = new Timer(10, move);
		ActionListener lis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activated = !activated;
				if(activated)
					movement.start();
				else
					movement.stop();		
			}
			
		};
		btn.addActionListener(lis);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D react1 = (Graphics2D) g;
		react1.setColor(Color.MAGENTA);
		react1.fillRect(x1, y1, 25, 25);
		
		Graphics2D react2 = (Graphics2D) g;
		react2.setColor(Color.MAGENTA);
		react2.fillRect(x2, y2, 25, 25);
	}
	
	public class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println("x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
			changeDir();
			if(changeOfDirection1) {
				x1 += 1;				
				y1 += 1;				
			} else if(!changeOfDirection1){		
				x1 -= 1;				
				y1 -= 1;
			}
			if(changeOfDirection2) {
				x2 -= 1;
				y2 += 1;
			} else if(!changeOfDirection2) {
				x2 += 1;
				y2 -= 1;
			}
			repaint();		
		}
		
	}
	
	public static void main(String[] args) {
		final int FRAME_WIDTH = 500, FRAME_HEIGHT = 565;
		JFrame frame = new JFrame("Moving Squares Demo");
		JPanel p = new MovingRects();
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}

}
