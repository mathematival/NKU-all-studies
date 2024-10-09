package hhh;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{
	private int gap = 27;//���߿ճ��ľ���
	private int unit = 36;//ÿ����Ŀ��
	private int screenWidth;
	private int screenHeight;
	private int x1=62,y1=19;//���Ͻǵ�����

    Image background = new ImageIcon("background.jpg").getImage();
    Image blackChess = new ImageIcon("blackChess.png").getImage();
    Image whiteChess = new ImageIcon("whiteChess.png").getImage();
	
	public PaintPanel() {
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int row = (y-y1) / unit;
				if((y-y1)%unit > unit/2 ){
					row++;
				}
				int col = (x-x1) / unit;
				if( (x-x1)%unit > unit/2){
					col++;
				}
				Vars.control.reportUserPressMouse(row,col);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				screenWidth = getWidth();
				screenHeight = getHeight();
				int min = Math.min(screenHeight, screenWidth);
				gap=min/25;
				unit = (min - gap*2 )/(Model.WIDTH-1);
				x1 = (screenWidth-unit*(Model.WIDTH-1)) /2;
				y1 = (screenHeight-unit*(Model.WIDTH-1))/2;
				repaint();
			}
		});
	}
	
	public void hehe(int winner){
		switch(winner){
		case Chess.SPACE:
			break;
		case Chess.BLACK:
			Vars.control.setIfStart(false);
			Vars.messagePanel.setIsTurn(false);//��ʱ��ͣ
			Vars.eastPanel.changeEnd();
			Vars.messagePanel.updateMessage1("����:��0��");
			Vars.model.review();//������̣���ʼ��¼���̲���
			repaint();
			JOptionPane.showMessageDialog(null, "�ڷ�ʤ��");
			Vars.westPanel.addText("�Ծֽ���");
			Vars.westPanel.addText("�ڷ�ʤ��");
			break;
		case Chess.WHITE:
			Vars.control.setIfStart(false);
			Vars.messagePanel.setIsTurn(false);//��ʱ��ͣ
			Vars.eastPanel.changeEnd();
			Vars.messagePanel.updateMessage1("����:��0��");
			Vars.model.review();//������̣���ʼ��¼���̲���
			repaint();
			JOptionPane.showMessageDialog(null, "�׷�ʤ��");
			Vars.westPanel.addText("�Ծֽ���");
			Vars.westPanel.addText("�׷�ʤ��");
			break;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background,x1-gap, y1-gap, Math.min(screenHeight, screenWidth), Math.min(screenHeight, screenWidth), this);
		drawChessPanel(g);
		drawChess(g);
	}


	private void drawChess(Graphics g) {
		for(int row=0;row<Model.WIDTH;row++){
			for(int col=0;col<Model.WIDTH;col++){
				int c = Vars.model.getChess(row,col);
				switch(c){
				case Chess.SPACE:
					break;
				case Chess.BLACK:
					//g.setColor(Color.black);
					//g.fillOval(x1+unit*col-unit/2, y1+unit*row-unit/2, unit, unit);
					g.drawImage(blackChess, x1+unit*col-unit/2, y1+unit*row-unit/2, unit, unit,this);
					break;
				case Chess.WHITE:
					//g.setColor(Color.white);
					//g.fillOval(x1+unit*col-unit/2, y1+unit*row-unit/2, unit, unit);
					g.drawImage(whiteChess, x1+unit*col-unit/2, y1+unit*row-unit/2, unit, unit,this);
					break;
				}
			}
		}
	}


	private void drawChessPanel(Graphics g) {
		for(int i=0;i<Model.WIDTH;i++){
			g.drawLine(x1, y1+unit*i, x1+unit*(Model.WIDTH-1), y1+unit*i);
			g.drawLine(x1+unit*i, y1, x1+unit*i, y1+unit*(Model.WIDTH-1));
		}
		
	}
}
