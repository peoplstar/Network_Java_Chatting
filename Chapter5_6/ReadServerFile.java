package Chapter5_6;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.net.*;
import java.io.*;

public class ReadServerFile extends Frame implements ActionListener{
	
	private TextField enter;	
	private TextArea htmlInfor;
	private TextArea contentsInfor;
	public ReadServerFile() {
		/*
		super("호스트 파일 읽기");
		setLayout(new BorderLayout());
		enter = new TextField("URL를 입력하세요.");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		
		
		htmlInfoer = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add(htmlInfoer, BorderLayout.CENTER);
		
		contentsInfor = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY); 
		add(contentsInfor, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(350, 450);
		setVisible(true);
		*/
		super("호스트 파일 읽기");
		Panel inputpanel = new Panel();
		setLayout(new BorderLayout());
		inputpanel.setLayout(new BorderLayout());
		enter = new TextField("URL를 입력하세요.");
		enter.addActionListener(this);
		inputpanel.add("North", enter);
		add("North", inputpanel);
		
		Panel outputpanel = new Panel();
		outputpanel.setLayout(new BorderLayout());
		htmlInfor = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
		htmlInfor.setEditable(false);
		outputpanel.add("North", new Label("HTML Information"));
		outputpanel.add("Center", htmlInfor);
		
		Panel ipclasspanel = new Panel();
		ipclasspanel.setLayout(new BorderLayout());
		contentsInfor = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY); 
		contentsInfor.setEditable(false);
		ipclasspanel.add("North", new Label("Contents Information"));
		ipclasspanel.add("Center", contentsInfor);
		add("Center", outputpanel);
		add("South", ipclasspanel);
		setSize(350, 500);
		addWindowListener(new WinListener());
		setSize(350, 450);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		URL url;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = e.getActionCommand();
		try {
			//htmlinfor : protocol, host name, port num, filename ,hashcode, 
			
			url = new URL(location);
			URLConnection connection = url.openConnection();
			connection.connect();
			htmlInfor.setText("Protocol : " + url.getProtocol() + "\n");
			htmlInfor.append("Host : " + url.getHost() + "\n");
			htmlInfor.append("Port Number : " + url.getPort() + "\n");
			htmlInfor.append("File : " + url.getFile() + "\n");
			htmlInfor.append("HashCode : " + url.hashCode() + "\n");
			
			Object o = url.getContent();
			/*if (o instanceof InputStream) 
				contentsInfor.setText("Object Type : Text type");
			else if (o instanceof ImageProducer)
				contentsInfor.setText("Object Type : Image type");
			else {
				contentsInfor.setText("Object Type : Anything type" + "\n");
				contentsInfor.append(o.getClass().getName());
			}
			*/
			if (connection.getContentType().contains("video")) {
				contentsInfor.setText("Object Type : Video type");
			}
			else if (connection.getContentType().contains("image")) {
				contentsInfor.setText("Object Type : Image type");
			}
			else if (connection.getContentType().contains("audio")) {
				contentsInfor.setText("Object Type : Audio type");
			}
			else  {
				contentsInfor.setText("Object Type : Anything type" + "\n");
				contentsInfor.append(o.getClass().getName());
			}
		} catch(MalformedURLException mal) {
			contentsInfor.setText("URL 형식이 잘못되었습니다.");
		} catch(IOException io) {
			contentsInfor.setText(io.toString());
		} catch(Exception ex) {
			contentsInfor.setText("호스트 컴퓨터의 파일만을 열 수 있습니다.");
		}
	}
	
	public static void main(String[] args) {
		ReadServerFile read = new ReadServerFile();
	}

	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
