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
		enter = new JTextField("파일 및 디렉토리명을 입력하세요");
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
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E)요일 kk시 mm분"); 
				filearea.setText(name.getName() + "이 존재한다.\n" +
				(name.isFile() ? "파일이다.\n" : "파일이 아니다.\n") + 
				(name.isDirectory() ? "디렉토리이다.\n" : "디렉토리가 아니다.\n") +
				(name.isAbsolute() ? "절대경로이다.\n" : "절대경로가 아니다.\n") +
				"마지막 수정날짜는 : " + simpleDateFormat.format(name.lastModified()) +
				"\n파일의 길이는 : " + name.length() +
				"\n파일의 경로는 : " + name.getPath() +
				"\n절대경로는 : " + name.getAbsolutePath() +
				"\n정규경로는 : " + name.getCanonicalPath() +
				"\n상위 디렉토리는 : " + name.getParent());
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
					contentarea.append("\n\n디렉토리의 내용은 : \n");
					for (int i = 0; i < directory.length; i++) {
						contentarea.append(directory[i] + "\n");
					}
				}
		}
		else {
			contentarea.setText(e.getActionCommand() + "은 존재하지 않는다.\n");
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
