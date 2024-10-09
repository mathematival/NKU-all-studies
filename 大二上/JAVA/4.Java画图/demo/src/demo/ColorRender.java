package demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ColorRender extends JLabel implements ListCellRenderer<Color>{
	//׼�� һ��map�����ڱ�����ǰ׼���õ�ͼƬ
	public HashMap<Color,Icon>  icons = new HashMap<>();

	
	public ColorRender() {
		setOpaque(true); //����͸��
		setPreferredSize(new Dimension(200, 37));
		icons.put(Color.green,new ImageIcon("green.png")   );//��map�з�����ǰ׼����ͼƬ
        icons.put(Color.red,new ImageIcon("red.png"));     //��Color������Ϊ����
        icons.put(Color.blue,new ImageIcon("blue.png"));     //��Color������Ϊ����

	}
	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list,
			Color value, int index, boolean isSelected, boolean cellHasFocus) {
		setIcon(icons.get(value));
		return this;
	}

}
