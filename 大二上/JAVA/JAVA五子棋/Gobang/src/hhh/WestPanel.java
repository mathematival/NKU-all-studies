package hhh;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class WestPanel extends JPanel{
	private JTextArea textArea;
	private JTextField textTF;
	private JButton enter = new JButton("����") ;
		
	public WestPanel() {
		setLayout(new GridBagLayout());
		
        JScrollPane scrollPane = new JScrollPane(getTextArea());

        //���ò���Լ��
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;//���ˮƽ�ʹ�ֱ
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;//�ı������ڴ�ֱ����������
        gbc.insets = new Insets(10, 10, 0, 10);
        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weighty = 0;//�ָ�Ȩ��
        gbc.fill = GridBagConstraints.HORIZONTAL;//ֻ���ˮƽ
        gbc.insets = new Insets(5, 10, 5, 5);
        add(getTextTF(), gbc);

        gbc.gridx++;
        gbc.weightx = 0;//�ָ�Ȩ��
        gbc.fill = GridBagConstraints.NONE;//�����
        gbc.insets = new Insets(5, 5, 5, 10);
        add(enter, gbc);
		
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vars.control.getIfConnect()==true){
					String text = textTF.getText();
					if(text.isEmpty()==false){
						textTF.setText("");//����ı���
						addText("�ң�"+text);
						Vars.net.sendChat("�Է���"+text);
					}
				}
			}
		});
	}
	
	
	private JTextField getTextTF(){
		if(textTF==null){
			textTF = new JTextField(15);
			textTF.setFont(new Font("����", Font.PLAIN, 30));
			
		}
		return textTF;
	}
	private JTextArea getTextArea(){
		if(textArea==null){
			textArea = new JTextArea(20,15);
			
			DefaultCaret caret = (DefaultCaret) textArea.getCaret();//�Զ����¹��λ�ã�ȷ��ʼ�տɼ����µ��ı�
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			
			textArea.setFont(new Font("����", Font.PLAIN, 30));
			textArea.setEditable(false);//���ɱ༭
			
		}
		return textArea;
	}
	
	public void addText(String s){
		textArea.append(s);
		textArea.append("\n");
	}
}
