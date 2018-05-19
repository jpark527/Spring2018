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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



@SuppressWarnings("serial")
public class P11_14 extends JPanel {

	private JButton btn;
	private Obj[] obj;
	private int index, length = 30;
	private boolean on;
	
	public P11_14() {
		create();
		wire();
	}
	
	public void create() {
		on=true;
		index=0;
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		btn = new JButton("CLICK");
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.add(btn);
		add(bottom, BorderLayout.SOUTH);
		obj = new Obj[length];
		for(int i=0; i<length; ++i)
			obj[i] = new Obj();
	}
	
	public void wire() {
		MouseListener mouse = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseReleased(MouseEvent e) {
				obj[index%length].setX(e.getX());
				obj[index%length].setY(e.getY());
				++index;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }
		};
		addMouseListener(mouse);
		ActionListener lis = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<length; ++i)
					obj[i].moveBy(1, 1, 500);
				repaint();
			}			
		};
		Timer move = new Timer(5, lis);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(on)
					move.start();
				else
					move.stop();
				on = !on;
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Random rand = new Random();
		super.paintComponent(g);
		Graphics2D[] circles = new Graphics2D[length];
		for(int i=0; i<length; ++i) {
			Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			circles[i] = (Graphics2D) g;
			circles[i].setColor(color);
			circles[i].fillOval(obj[i].getX(), obj[i].getY(), 25, 25);
		}
	}
	
	public static void main(String[] args) {
		final int FRAME_WIDTH = 500, FRAME_HEIGHT = 565;
		JFrame frame = new JFrame("P11_14");
		JPanel p = new P11_14();
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}

}
