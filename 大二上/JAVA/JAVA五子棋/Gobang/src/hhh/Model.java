package hhh;

import java.util.ArrayList;

public class Model {
	private Model(){}
	private static Model instance=null;
	public static Model getinstance(){
		if(instance==null){
			instance=new Model();
		}
		return instance;
	}
	public static final int WIDTH=19;
	
	private int[][]data=new int[WIDTH][WIDTH];
	private ArrayList<Chess>before= new ArrayList<>();
	private int curr = 0;//��¼���̵�����һ��,Ҳ������������ʾ�˼�������
	
	public void Restart(){
		while(before.size()!=0){
			Chess p = before.remove(before.size()-1);
			data[p.row][p.col]=Chess.SPACE;
		}
		
	}
	//����ʱ���������,curr��0
	public void review(){
		for(int row=0;row<Model.WIDTH;row++){
			for(int col=0;col<Model.WIDTH;col++){
				data[row][col]=Chess.SPACE;
			}
		}
		curr=0;
	}
	//��һ��
	public boolean Back(){
		if(curr>0){
			curr--;
			Chess p = before.get(curr);
			data[p.row][p.col]=Chess.SPACE;
			return true;
		}
		else{
			return false;
		}
	}
	
	//��һ��
	public boolean Next(){
		if(curr<before.size()){
			Chess p = before.get(curr);
			data[p.row][p.col] = p.color;
			curr++;
			return true;
		}
		else{
			return false;
		}
	}
	
	public void regret(int c){
		Chess p = before.remove(before.size()-1);
		data[p.row][p.col]=Chess.SPACE;
		if(p.color!=c){//���û�л��嵽c��ɫ�����ٻ���һ��
			p = before.remove(before.size()-1);
			data[p.row][p.col]=Chess.SPACE;
		}
	}
	public boolean canRegret(int c){
		if(before.size()==0){
			return false;
		}
		else if(before.size()==1&&before.get(0).color!=c){
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean putChess(int row,int col,int color){
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH&&data[row][col]==Chess.SPACE){
			before.add(new Chess(row, col, color));
			data[row][col]=color;
			return true;
		}
		return false;
	}
	public int getChess(int row,int col){
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH){
			return data[row][col];
		}
		return Chess.SPACE;
	}
	public int whoWin(){
		if(before.size()<9){
			return Chess.SPACE;
		}
		int lastRow = before.get(before.size()-1).row;
		int lastCol = before.get(before.size()-1).col;
		int lastColor = before.get(before.size()-1).color;
		
		int count=1;
		//�ж�ˮƽ����
		for(int i=lastRow-1;i>=0;i--){
			if(data[i][lastCol]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		for(int i=lastRow+1;i<WIDTH;i++){
			if(data[i][lastCol]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		//�ж���ֱ����
		count=1;
		for(int j=lastCol-1;j>=0;j--){
	        if(data[lastRow][j]==lastColor){
	            count++;
	            if (count>=5){
	                return lastColor;
	            }
	        }
	        else{
	            break;
	        }
	    }
		for(int j=lastCol+1;j<WIDTH;j++){
			if(data[lastRow][j]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		//�ж���������
		count=1;
		for(int i=lastRow-1,j=lastCol-1;i>=0&&j>=0;i--,j--){
			if(data[i][j]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		for(int i=lastRow+1,j=lastCol+1;i<WIDTH&&j<WIDTH;i++,j++){
			if(data[i][j]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		//�ж���������
		count=1;
		for(int i=lastRow+1,j=lastCol-1;i<WIDTH&&j>=0;i++,j--){
			if(data[i][j]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		for(int i=lastRow-1,j=lastCol+1;i>=0&&j<WIDTH;i--,j++){
			if(data[i][j]==lastColor){
				count++;
				if(count>=5){
					return lastColor;
				}
			}
			else{
				break;
			}
		}
		return Chess.SPACE;
	}
	
	public int getCurr() {
		return curr;
	}
	
}
