import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class Frame extends JFrame {
	public Frame() {
		super("Крестики-нолики (v1.1.22)");
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setSize(490,580);
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cont = this.getContentPane();
		Panel p = new Panel();
		cont.add(p);
		this.setVisible(true);
	}
}
