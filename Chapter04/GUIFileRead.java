package Chapter04;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

// GUI 이용해서 (swing) 파일을 읽어 [행]: 파일 내용 => 해당 파일 새로 복사 => 다시 출력까지
public class GUIFileRead extends JFrame implements ActionListener {

	private JTextField fileName;
	private JTextArea fileContent;
	private FileInputStream fin;
	private FileOutputStream fout;
	private InputStreamReader isr;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	public GUIFileRead() {
		super("File Read and index copy");
		fileName = new JTextField("파일을 입력하세요");
		fileName.addActionListener(this);
		fileContent = new JTextArea();
	
		add(fileName, BorderLayout.NORTH);
		add(fileContent, BorderLayout.CENTER);
	
		fileName.setBorder(new LineBorder(Color.black));
		fileContent.setBorder(new LineBorder(Color.black));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
		File name = new File(e.getActionCommand());
		String buf;

		if (name.exists()) {
			try {
				fin = new FileInputStream(fileName.getText());
				isr = new InputStreamReader(fin, "KSC5601");
				
				fout = new FileOutputStream("numbered_" + fileName.getText() + ".txt");
				osw = new OutputStreamWriter(fout, "KSC5601");
				bw = new BufferedWriter(osw);

				LineNumberReader lnr = new LineNumberReader(isr);
				while ((buf = lnr.readLine()) != null) {
					buf = Integer.toString(lnr.getLineNumber()) + " : " + buf;
					bw.write(buf + "\r\n");
					bw.flush();
					fileContent.append(buf + "\r\n");
				}
			} catch(IOException io) { System.out.println(io);}
		}
		try {
			fin.close();
			fout.close();
		} catch (IOException io) {}
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new GUIFileRead();
	}
}
