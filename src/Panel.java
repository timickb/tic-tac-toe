import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Panel extends JPanel {
	private int[][] field;
	private final int SIZE=150;
	private final int WH = 3;
	private boolean last = true;
	private boolean game = true;
	private int winner = 0;
	private Font font;
	private Mouse mouse;
	private Timer t;
	
	private class Mouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getX() > (10+SIZE*WH) || e.getY() > (10+SIZE*WH) || e.getX() < 10 || e.getY() < 10) return;
			
			int x = e.getX()/SIZE;
			int y = e.getY()/SIZE;

			if(field[x][y] > 0) return;

			if(last) {
				field[x][y] = 2;
				System.out.println("В клетке " + x + ";" + y + " теперь нолик");
			} else {
				field[x][y] = 1;
				System.out.println("В клетке " + x + ";" + y + " теперь крестик");
			}
			last = !last;
			repaint();
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	public Panel() {
		font = new Font("Calibri", 0 ,60);
		mouse = new Mouse();
		this.addMouseListener(mouse);
		this.setFocusable(true);
		field = new int[WH][WH];
		for(int i=0;i<WH;i++) {
			for(int j=0;j<WH;j++) {
				field[i][j] = 0;
			}
		}
		
		t = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Если есть победитель - завершаем игру
				if(winner() > 0) {
					stopGame(winner());
					return;
				}
				//Если заполнено все поле - завершаем игру
				int f = 0;
				for(int i = 0; i < WH; i++) {
					for(int j = 0; j < WH; j++) {
						if(field[i][j] > 0) f++;
					}
				}
				if(f > 8) stopGame(0);
				
			}

		});
		t.start();
	}
	private int winner() {
		//Проверяем комбинации крестика	
		if((field[0][0] == 1 && field[1][0] == 1 && field[2][0] == 1)
				|| (field[0][1] == 1 && field[1][1] == 1 && field[2][1] == 1)
				|| (field[0][2] == 1 && field[1][2] == 1 && field[2][2] == 1)
				|| (field[0][0] == 1 && field[0][1] == 1 && field[0][2] == 1)
				|| (field[1][0] == 1 && field[1][1] == 1 && field[1][2] == 1)
				|| (field[2][0] == 1 && field[2][1] == 1 && field[2][2] == 1)
				|| (field[0][0] == 1 && field[1][1] == 1 && field[2][2] == 1)
				|| (field[2][0] == 1 && field[1][1] == 1 && field[0][2] == 1)) return 1;
		
		//Проверяем комбинации нолика
		if((field[0][0] == 2 && field[1][0] == 2 && field[2][0] == 2)
				|| (field[0][1] == 2 && field[1][1] == 2 && field[2][1] == 2)
				|| (field[0][2] == 2 && field[1][2] == 2 && field[2][2] == 2)
				|| (field[0][0] == 2 && field[0][1] == 2 && field[0][2] == 2)
				|| (field[1][0] == 2 && field[1][1] == 2 && field[1][2] == 2)
				|| (field[2][0] == 2 && field[2][1] == 2 && field[2][2] == 2)
				|| (field[0][0] == 2 && field[1][1] == 2 && field[2][2] == 2)
				|| (field[2][0] == 2 && field[1][1] == 2 && field[0][2] == 2)) return 2;
		return 0;
	}
	private void stopGame(int w) {
		this.winner = w;
		game = false;
		t.stop();
		this.removeMouseListener(mouse);
		repaint();
	}
	public void paintComponent(Graphics gr) {
		if(game) {
			gr.translate(10, 10);
			renderField(gr);
			renderStatus(gr);
		} else {
			renderEnd(gr);
		}
	}
	private void renderStatus(Graphics gr) {
		//Чей ход?
		gr.setColor(Color.BLACK);
		gr.setFont(font);
		gr.clearRect(50, SIZE*WH+10, 500, 500);
		if(last) gr.drawString("Ход нолика", 50, 64+SIZE*WH);
		else gr.drawString("Ход крестика", 50, 64+SIZE*WH);
	}
	private void renderEnd(Graphics gr) {
		gr.setColor(Color.BLUE);
		gr.fillRoundRect(20, 100, 450, 200, 25, 25);
		gr.setColor(Color.WHITE);
		gr.setFont(font);
		if(winner==0) gr.drawString("Ничья", 125, 220);
		if(winner==1) gr.drawString("Победил крестик", 20, 220);
		if(winner==2) gr.drawString("Победил нолик", 25, 220);
	}
	private void renderField(Graphics gr) {
		for(int i=0;i<WH;i++) {
			for(int j=0;j<WH;j++) {
				switch(field[i][j]) {
				case 0:
					gr.setColor(Color.WHITE);
					gr.fillRect(i*SIZE, j*SIZE, SIZE, SIZE);
				break;
				case 1:
					gr.setColor(Color.RED);
					gr.drawLine(i*SIZE, j*SIZE, (i*SIZE)+SIZE, (j*SIZE)+SIZE);
					gr.drawLine(i*SIZE+SIZE, j*SIZE, (i*SIZE), (j*SIZE)+SIZE);
				break;
				case 2:
					gr.setColor(Color.BLUE);
					gr.drawOval(i*SIZE+2, j*SIZE+2, SIZE-4, SIZE-4);
				break;
				}
			}
		}
		
		for(int i=0;i<WH;i++) {
			for(int j=0;j<WH;j++) {
				gr.setColor(Color.BLACK);
				gr.drawRect(i*SIZE, j*SIZE, SIZE, SIZE);
			}
		}
	}
}
