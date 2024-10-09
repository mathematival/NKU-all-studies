package demo;

import java.awt.Component;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRender extends JLabel implements ListCellRenderer<String>{
	ImageIcon img1 = new ImageIcon("tom.png");
	ImageIcon img2 = new ImageIcon("jerry.png");
	ImageIcon img3 = new ImageIcon("cuihua.png");
	LinkedHashMap<String, ImageIcon> map = new LinkedHashMap<>();
	public ComboBoxRender() {
		map.put("Tom", img1);
		map.put("Jerry", img2);
		map.put("Cuihua", img3);
		setOpaque(true); //����͸��
        setHorizontalAlignment(CENTER);//���ñ���ǩˮƽ���ж���
        setVerticalAlignment(CENTER);//���ñ���ǩ��ֱ���ж���

	}
	@Override
	public Component getListCellRendererComponent(JList list, String value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		System.out.println(value);
		if (isSelected) {//�ص㣬�����Ǹ����û�ѡ����񣬸�����ǩ���ñ���ɫ��ǰ��ɫ
            setBackground(list.getSelectionBackground());//�����Լ��ı�ǩ��ʾʲô����ɫ������ֱ��ָ������һ����Ҫ�ɼ�List
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		ImageIcon icon = map.get(value);
		setIcon(icon);
		if (icon !=null){
			setText(value);
		}else{
			System.out.println(value + "null");
		}
		

		return this;
	}
	

}
