package hhh;


public class Control {
	private Control(){}
	private static Control instance=null;
	public static Control getinstance(){
		if(instance==null){
			instance=new Control();
		}
		return instance;
	}
	
	private int localColor=Chess.BLACK;
	//�����ܷ�����
	private boolean ifConnect = false;//�Ƿ����ӣ���ֹһ��ʼ��ʱ��ť����㣩
	private boolean ifStart = false;//��Ϸ�Ƿ�ʼ���������ƽ�����л�,��ʱ�Ƿ�ʼ��
	private boolean canputChess = false;//�Ƿ�������壨���ڿ���ֻ��һ�������壬����û�о���������ɫ��ʱ��Ҳ�������壩
	
	private boolean canRegret = false;//�����û���壬�Ͳ��ܻ���
	
	public void reStart(){
		Vars.model.Restart();
		Vars.messagePanel.ButtonReset();
		Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
		Vars.eastPanel.changeStart();
		ifStart = true;
		canputChess = false;
		canRegret = false;
		
		Vars.paintPanel.repaint();
	}
	
	public void RegretChess() {
		Vars.model.regret(localColor);
		canputChess=true;
		canRegret = Vars.model.canRegret(localColor);
		//������ʾ��Ϣ
		if(localColor==Chess.BLACK){
			Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
		}
		else if(localColor==Chess.WHITE){
			Vars.messagePanel.updateMessage1("�ֵ��׷�����");
		}
		
		Vars.paintPanel.repaint();
	}
	public void otherRegretChess() {
		Vars.model.regret(-localColor);
		canputChess=false;
		canRegret = Vars.model.canRegret(localColor);
		//������ʾ��Ϣ
		if(localColor==Chess.BLACK){
			Vars.messagePanel.updateMessage1("�ֵ��׷�����");
		}
		else if(localColor==Chess.WHITE){
			Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
		}
		
		Vars.paintPanel.repaint();
	}
	
	public void reportUserPressMouse(int row, int col) {
		if(ifConnect==false||ifStart==false||canputChess==false){
			return;
		}
		boolean success = Vars.model.putChess(row,col,localColor);
		if(success){
			
			//localColor = -localColor;
			canputChess= false;
			canRegret = true;
			if(localColor==Chess.BLACK){
				Vars.messagePanel.updateMessage1("�ֵ��׷�����");
			}
			else if(localColor==Chess.WHITE){
				Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
			}
			Vars.paintPanel.repaint();
			Vars.net.sendChess(row, col);
			//whoWin
			int winner=Model.getinstance().whoWin();
			//hehe
			Vars.paintPanel.hehe(winner);
		}
	}

	public void otherChess(int row, int col) {
		boolean success = Vars.model.putChess(row,col,-localColor);
		if(success){
			
			//localColor = -localColor;
			canputChess= true;
			if(localColor==Chess.BLACK){
				Vars.messagePanel.updateMessage1("�ֵ��ڷ�����");
			}
			else if(localColor==Chess.WHITE){
				Vars.messagePanel.updateMessage1("�ֵ��׷�����");
			}
			Vars.paintPanel.repaint();
			
			//whoWin
			int winner=Model.getinstance().whoWin();
			//hehe
			Vars.paintPanel.hehe(winner);
		}
	}
	
	public void setChessColor(int color){
		localColor = color;
	}
	public void setCanputChess(boolean canputChess) {
		this.canputChess = canputChess;
	}
	public void setIfConnect(boolean ifConnect) {
		this.ifConnect = ifConnect;
	}
	public void setIfStart(boolean p) {
		this.ifStart = p;
	}

	public boolean getIfConnect() {
		return ifConnect;
	}
	public boolean getIfStart() {
		return ifStart;
	}
	public boolean getCanputChess() {
		return canputChess;
	}
	public boolean getCanRegret() {
		return canRegret;
	}
	public int getLocalColor() {
		return localColor;
	}

	
}
