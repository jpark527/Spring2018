package myTimer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JPanel {
	
	static public final int LENGTH = 200; 
	
	private ImageIcon circle = new ImageIcon("/Users/j/Java/CS3B/TimerApp/circle.png");
	private JButton startAndPause, stop;
	private JTextField min, sec;
	private double num, denum;
	private boolean done = false;
	private Sound music = new Sound("/Users/j/Java/CS3B/TimerApp/relax.wav");
	
	public Main() {
		create();
		wire();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.drawOval(100, 100, LENGTH, LENGTH);
		if(num==0 && denum==0)
			g2.fillOval(100, 100, LENGTH, LENGTH);
		else
			g2.fillOval((int) (200 - LENGTH * (num/2/denum)), (int) (200 - LENGTH * (num/2/denum)), 
						(int)(LENGTH * (num/denum)), (int)(LENGTH * (num/denum)));
		g2.drawImage(circle.getImage(), 97, 47, 255, 255, this);
		repaint();
	}
	
	private void create() {
		num = denum = 0;
		setLayout(new BorderLayout());
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		startAndPause = new JButton("Start / Pause");
		stop = new JButton("Stop");
		bottom.add(startAndPause);
		bottom.add(stop);
		add(bottom, BorderLayout.SOUTH);
		
		JPanel top = new JPanel();
		JLabel minLabel = new JLabel("Minuite(s)  ");
		JLabel secLabel = new JLabel("Second(s)");
		min = new JTextField(3);
		sec = new JTextField(3);
		min.setText("20");
		sec.setText("0");
		top.add(min);
		top.add(minLabel);
		top.add(sec);
		top.add(secLabel);
		add(top, BorderLayout.NORTH);
	}
	
	private void wire() {
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(num==denum && denum != 0)
					done = true;
				if(done) {
					music.start();
					return;
				}
				if(denum==0) {
					int mins = min.getText().isEmpty() ? 0 : Integer.parseInt(min.getText().trim()) * 60,
						secs = sec.getText().isEmpty() ? 0 : Integer.parseInt(sec.getText().trim());
					denum =  mins + secs;
					if(denum==0)
						denum = 1;
				}
				++num;
				repaint();
			}
		});
		startAndPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				music.stop();
				if(done) {
					done = false;
					denum = num = 0;
					timer.stop();
					timer.start();
					return;
				}
				if(timer.isRunning())
					timer.stop();
				else
					timer.start();
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				music.stop();
				if(timer.isRunning())
					timer.stop();
				if(denum != 0)
					num = denum = 0;
				done = false;
			}
		});
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("JTimer");
		JPanel p = new Main();
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(425, 390));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}
