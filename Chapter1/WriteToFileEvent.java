package Chapter1;

import java.io.*;
import java.awt.event.*;
import java.awt.*;

class WriteToFileEvent extends Frame implements ActionListener {
	Label lfile, ldata;
	TextField tfile, tdata;
	Button save, close;
	String filename, data;
	byte buffer[] = new byte[80];
	
	public WriteToFileEvent(String str) {
		super(str);
		setLayout(new FlowLayout());
		lfile = new Label("�����̸��� �Է��ϼ���.");
		add(lfile);
		tfile = new TextField(20);
		add(tfile);
		ldata = new Label("������ �����͸� �Է��ϼ���.");
		add(ldata);
		tdata = new TextField(20);
		add(tdata);
		save = new Button("�����ϱ�");
		save.addActionListener(this);
		add(save);
		
		close = new Button("�ݱ�");
		close.addActionListener(this);
		add(close);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}


	public static void main(String args[]) {
		WriteToFileEvent text = new WriteToFileEvent("��������");
		text.setSize(340, 300);
		text.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		filename = tfile.getText();
		data = tdata.getText();
		buffer = data.getBytes();
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			fout.write(buffer);
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}
}

class WinListener extends WindowAdapter {
	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}
