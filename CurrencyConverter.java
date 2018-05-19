package homeworkGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// P10.34
@SuppressWarnings("serial")
public class CurrencyConverter extends JPanel {
	
	private JTextField us, euro;
	private JLabel forUs, forEuro;
	private JButton left, right;
	
	public CurrencyConverter() {
		create();
		wire();
	}

	public void create() {
		setLayout(new GridLayout(3,2));
		us = new JTextField();
		euro = new JTextField();
		forUs = new JLabel("US Dollars: ");
		forEuro = new JLabel("Euro: ");
		left = new JButton("<");
		right = new JButton(">");
		add(forUs);
		add(us);
		add(left);
		add(right);
		add(forEuro);
		add(euro);
	}
	
	public void wire() {
		ActionListener toLeft = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double result = Double.parseDouble(euro.getText());
					result *= 1.42;
					String convert = Double.toString(result);
					us.setText(upTo5Decimal(convert));
				} catch(Exception ingored) {
					System.out.println("Invalid Input.");
				}
			}	
		};
		ActionListener toRight = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				try {
					double result = Double.parseDouble(us.getText());
					result *= (1/1.42);
					String convert = Double.toString(result);
					euro.setText(upTo5Decimal(convert));
				} catch(Exception ingored) {
					System.out.println("Invalid Input.");
				}
			}	
		};
		left.addActionListener(toLeft);
		right.addActionListener(toRight);
	}
	
	private String upTo5Decimal(String num) {
		if(num.length()>7)
			return num.substring(0, 7);
		return num;
	}
	
	public static void main(String[] args) {
		final int FRAME_WIDTH = 250, FRAME_HEIGHT = 120;
		JFrame frame = new JFrame("Currency Converter");
		JPanel p = new CurrencyConverter();
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}

}
