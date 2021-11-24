package Chapter05_06;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.applet.*;

public class GetHostInfor extends Frame implements ActionListener{
	
	TextField hostname, ipclass;
	Button getinfor;
	TextArea display, ipdisplay;
	
	public static void main(String[] args) {
		GetHostInfor host = new GetHostInfor("InetAddress CLASS");
		host.setVisible(true);
	}
	
	public GetHostInfor(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		
		Panel inputpanel = new Panel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("호스트 이름:"));
		hostname = new TextField("", 30);
		getinfor = new Button("호스트 정보 얻기");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		getinfor.addActionListener(this);
		add("North", inputpanel);
		
		Panel outputpanel = new Panel();
		outputpanel.setLayout(new BorderLayout());
		display = new TextArea("", 10, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("인터넷 주소"));
		outputpanel.add("Center", display);
		
		Panel ipclasspanel = new Panel();
		ipclasspanel.setLayout(new BorderLayout());
		ipdisplay = new TextArea("", 10, 10);
		ipdisplay.setEditable(false);
		ipclasspanel.add("North", new Label("IP CLASS"));
		ipclasspanel.add("Center", ipdisplay);
		add("Center", outputpanel);
		add("South", ipclasspanel);
		setSize(270, 500);
	}
	
	public void actionPerformed(ActionEvent e) {
		String name = hostname.getText();
		
		try {
			InetAddress inet = InetAddress.getByName(name);
			byte[] a = inet.getAddress();
			char b = ipClass(a);
			// String ip = inet.getHostName() + "\n";
			//display.append(ip);
			InetAddress[] allIP = InetAddress.getAllByName(name);
			display.setText("");
			for (int i = 0; i < allIP.length; i++) {
				display.append(allIP[i].toString() + "\n");
			}
			ipdisplay.setText(String.valueOf(b));
			
		} catch (UnknownHostException ue) {
			String ip = name + " : 해당 호스트가 없습니다.\n";
			display.setText(ip);
		}
	}
	
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte < 128) ? 'A' : (highByte < 192) ? 'B' : (highByte < 224) ? 'C' : 
			(highByte < 240) ? 'D' : 'E';
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
