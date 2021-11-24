package Chapter03;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ImageCopyTest extends JFrame implements ActionListener {

	private JTextField enter;
	private JTextArea progress;
	private RandomAccessFile jpgfile;
	private RandomAccessFile copyfile;
	
	public ImageCopyTest() {
		super("ImageCopy Test");
		enter = new JTextField("image 파일은 jpg로 입력하세요.");
		enter.addActionListener(this);
		progress = new JTextArea();

		add(enter, BorderLayout.NORTH);
		add(progress, BorderLayout.CENTER);

		addWindowListener(new WinListener());
		setSize(400, 400);
		setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
		File name = new File(e.getActionCommand());
		try {
			jpgfile = new RandomAccessFile("test.jpg", "r");
			copyfile = new RandomAccessFile(name, "w");
		} catch (FileNotFoundException fnf) {}
	}
	
	public static void main(String[] args) {
		ImageCopyTest f = new ImageCopyTest();
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
