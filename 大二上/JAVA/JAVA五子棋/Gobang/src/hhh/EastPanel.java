package hhh;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EastPanel extends JPanel{
	
	private JButton Btn1 = new JButton("����");
	private JButton Btn1two = new JButton("��һ��");
	
	private JButton Btn2 = new JButton("����");
	private JButton Btn2two = new JButton("��һ��");
	
	private JButton Btn3 = new JButton("����һ��");
	private JButton Btn4 = new JButton("�˳���Ϸ");
	
	private JPanel cardPanel1 = new JPanel(new CardLayout());
	private CardLayout cardLayout1;
	
	private JPanel cardPanel2 = new JPanel(new CardLayout());
	private CardLayout cardLayout2;
	
	public EastPanel() {
		setLayout(new GridLayout(4, 1,0,30));
		
        cardPanel1.add(Btn1, "����");
        cardPanel1.add(Btn1two, "��һ��");
        cardLayout1 = (CardLayout) cardPanel1.getLayout();
        
        cardPanel2.add(Btn2, "����");
        cardPanel2.add(Btn2two, "��һ��");
        cardLayout2 = (CardLayout) cardPanel2.getLayout();
        
		Btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true&&Vars.control.getIfStart()==true){
					if(Vars.control.getCanRegret()==false){
						JOptionPane.showMessageDialog(null, "�����ڲ��ܻ���");
						return;
					}
					int option = JOptionPane.showConfirmDialog(null, "�����Ҫ������");
					if(option==JOptionPane.YES_OPTION){
						Vars.net.sendRegret("?");
					}
				}
			}
		});
		Btn1two.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true){
					Vars.model.Back();
					int p = Vars.model.getCurr();
					Vars.messagePanel.updateMessage1("����:��"+p+"��");
					Vars.paintPanel.repaint();
				}
			}
		});
		
		Btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true&&Vars.control.getIfStart()==true){
					int option = JOptionPane.showConfirmDialog(null, "�����Ҫ������");
					if(option==JOptionPane.YES_OPTION){
						int winner = -Vars.control.getLocalColor();
						Vars.net.sendGiveup(true);
						Vars.paintPanel.hehe(winner);
					}
				}
			}
		});
		Btn2two.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true){
					Vars.model.Next();
					int p = Vars.model.getCurr();
					Vars.messagePanel.updateMessage1("����:��"+p+"��");
					Vars.paintPanel.repaint();
				}
			}
		});
		
		Btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true){
					int option = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ����һ����");
					if(option==JOptionPane.YES_OPTION){
						Vars.net.sendRestart("?");
					}
				}
			}
		});
		Btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "�����Ҫ�˳���Ϸ��");
				if(option==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		
		add(cardPanel1);
		add(cardPanel2);
		add(Btn3);
		add(Btn4);
	}
	
	public void changeEnd(){
		cardLayout1.show(cardPanel1,"��һ��");
		cardLayout2.show(cardPanel2,"��һ��");
	}
	public void changeStart(){
		cardLayout1.show(cardPanel1,"����");
		cardLayout2.show(cardPanel2,"����");
	}
}
