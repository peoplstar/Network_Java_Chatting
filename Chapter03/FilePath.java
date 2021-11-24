package Chapter03;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilePath extends JFrame implements ActionListener {

	private JTextField enter;
	private JTextArea filearea;
	private JTextArea contentarea;
	public FilePath() {
		super("File Class Test");
		enter = new JTextField("���� �� ���丮���� �Է��ϼ���");
		enter.addActionListener(this);
		filearea = new JTextArea();
		contentarea = new JTextArea();
		add(enter, BorderLayout.NORTH);
		add(filearea, BorderLayout.CENTER);
		add(contentarea, BorderLayout.SOUTH);
		filearea.setBorder(new LineBorder(Color.black));
		contentarea.setBorder(new LineBorder(Color.black));

		addWindowListener(new WinListener());
		setSize(600, 800);
		setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
		File name = new File(e.getActionCommand());
		filearea.setText("");
		contentarea.setText("");
		if (name.exists()) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� (E)���� kk�� mm��"); 
				filearea.setText(name.getName() + "�� �����Ѵ�.\n" +
				(name.isFile() ? "�����̴�.\n" : "������ �ƴϴ�.\n") + 
				(name.isDirectory() ? "���丮�̴�.\n" : "���丮�� �ƴϴ�.\n") +
				(name.isAbsolute() ? "�������̴�.\n" : "�����ΰ� �ƴϴ�.\n") +
				"������ ������¥�� : " + simpleDateFormat.format(name.lastModified()) +
				"\n������ ���̴� : " + name.length() +
				"\n������ ��δ� : " + name.getPath() +
				"\n�����δ� : " + name.getAbsolutePath() +
				"\n���԰�δ� : " + name.getCanonicalPath() +
				"\n���� ���丮�� : " + name.getParent());
			} catch (IOException e1) {}
				if (name.isFile()) {
					try {
						RandomAccessFile r= new RandomAccessFile(name, "r");
						StringBuffer buf = new StringBuffer();
						String text;
						contentarea.append("\n\n");
						while ((text = r.readLine()) != null) 
							buf.append(text + "\n");
						contentarea.append(buf.toString());
					} catch (IOException e2) {}
				}
				
				else if (name.isDirectory()) {
					String directory[] = name.list();
					contentarea.append("\n\n���丮�� ������ : \n");
					for (int i = 0; i < directory.length; i++) {
						contentarea.append(directory[i] + "\n");
					}
				}
		}
		else {
			contentarea.setText(e.getActionCommand() + "�� �������� �ʴ´�.\n");
		}
		
	}
	
	public static void main(String[] args) {
		FilePath f = new FilePath();
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
