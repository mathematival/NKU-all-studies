package hhh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/*
 * ��һ���ַ�����ð��ǰ�����ѡ��
 * chess:3,3
 * chat:xxxxx
 * regret:?
 * regret:Yes    JOptionPanel showConfirmdialog
 * regret:NO     ����һ���Ի������󱻾ܾ�
 * 
 * restart:?
 * restart:Yes
 * restart:No
 * 
 * giveup:true
 * connect:true
 * 
 * click:Black
 * click:White
 */

public class NetHelper {
	public static final int PROT = 8000;
	private Socket s;
	private BufferedReader in;
	private PrintStream out;
	
	//ʹ�ö��̣߳���ֹ�������ڽ����ź���һ����
	public void startListen(){
		new Thread(){
			public void run() {
				listen();
			}
		}.start();
	}
	private void listen(){
		try {
			ServerSocket ss =new ServerSocket(PROT);
			Socket s = ss.accept();
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream(),true);
			startReadThread();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void connect(String ip,int port){
		try {
			Socket s =new Socket(ip,port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream(),true);
			
			out.println("connect:true");
			Vars.control.setIfConnect(true);
			Vars.control.setIfStart(true);
			Vars.westPanel.addText("���ӳɹ�");
			Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
			
			startReadThread();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void startReadThread() {
		new Thread(){
			public void run() {
				while(true){
					try {
						String line = in.readLine();
						if(line.startsWith("connect:true")){
							Vars.control.setIfConnect(true);
							Vars.control.setIfStart(true);
							Vars.westPanel.addText("���ӳɹ�");
							Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
						}
						else if(line.startsWith("chess:")){
							otherChess(line.substring("chess:".length()));
						}
						else if(line.startsWith("chat:")){
							Vars.westPanel.addText(line.substring("chat:".length()));
						}
						else if(line.startsWith("regret:")){
							RegretChess(line.substring("regret:".length()));
						}
						else if(line.startsWith("restart:")){
							Restart(line.substring("restart:".length()));
						}
						else if(line.startsWith("giveup:true")){
							int winner = Vars.control.getLocalColor();
							Vars.paintPanel.hehe(winner);
						}
						else if(line.startsWith("click:")){
							Vars.messagePanel.otherButtonSelected(line.substring("click:".length()));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void Restart(String s) {
		if(s.startsWith("?")){
			int option = JOptionPane.showConfirmDialog(null, "�Է���Ҫ���¿�ʼ���Ƿ�ͬ�⣿");
			if(option==JOptionPane.YES_OPTION){
				sendRestart("Yes");
				Vars.control.reStart();
				Vars.westPanel.addText("�Ծ����¿�ʼ");
			}
			else{
				sendRestart("No");
				Vars.westPanel.addText("�ܾ��Ծ����¿�ʼ");
			}
		}
		else if(s.startsWith("Yes")){
			Vars.control.reStart();
			Vars.westPanel.addText("�Ծ����¿�ʼ");
		}
		else if(s.startsWith("No")){
			JOptionPane.showMessageDialog(null, "�Է��ܾ����������");
			Vars.westPanel.addText("�Է��ܾ��Ծ����¿�ʼ");
		}
	}
	private void RegretChess(String s){
		if(s.startsWith("?")){
			int option = JOptionPane.showConfirmDialog(null, "�Է���Ҫ���壬�Ƿ�ͬ�⣿");
			if(option==JOptionPane.YES_OPTION){
				sendRegret("Yes");
				Vars.control.otherRegretChess();
				Vars.westPanel.addText("ͬ��Է�����");
			}
			else{
				sendRegret("No");
				Vars.westPanel.addText("�ܾ��Է�����");
			}
		}
		else if(s.startsWith("Yes")){
			Vars.control.RegretChess();
			Vars.westPanel.addText("�Է�ͬ������Ļ���");
		}
		else if(s.startsWith("No")){
			JOptionPane.showMessageDialog(null, "�Է��ܾ����������");
			Vars.westPanel.addText("�Է��ܾ�����Ļ���");
		}
	}
	private void otherChess(String line) {
		String[]param = line.split(",");
		int row = Integer.parseInt(param[0]);
		int col = Integer.parseInt(param[1]);
		Vars.control.otherChess(row,col);
		
	}
	
	public void sendChess(int row,int col){
		if(out!=null){
			out.println("chess:"+row+","+col);
		}
	}
	public void sendChat(String line){
		if(out!=null){
			out.println("chat:"+line);
		}
	}
	public void sendGiveup(boolean p){
		if(out!=null){
			if(p==true){
				out.println("giveup:true");
			}
			
		}
	}
	public void sendRegret(String s) {
		if(out!=null){
			out.println("regret:"+s);
		}
	}
	public void sendClick(String s) {
		if(out!=null){
			out.println("click:"+s);
		}
	}
	public void sendRestart(String s) {
		if(out!=null){
			out.println("restart:"+s);
		}
	}
	
}
