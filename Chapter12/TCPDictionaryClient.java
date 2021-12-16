package Chapter12;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
/*
- Ŭ���̾�Ʈ�� ������ �α��� �� �� ���� ���� �̿� ����
- ���� �α��� ����� �� ����
- Ŭ���̾�Ʈ�� �ܾ ����/����/������ �� ����
- ������ �����带 �̿��� ����ó��
- ������ ���Ϸ� ����
- Ŭ���̾�Ʈ-������ �������� �޽��� ����
 */
public class TCPDictionaryClient extends Frame implements ActionListener, KeyListener {
	
	TextArea display;
	TextField wtext, ltext;
	Label mlbl, wlbl, loglbl;
	Button onoff;
	BufferedWriter output;
	BufferedReader input;
	Socket client = null;
	StringBuffer clientdata;
	String serverdata;
	String ID = null;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_QUERY = 1021;  // /q request
	private static final int REQ_DELETE = 1022; // /d request
	private static final int REQ_UPDATE = 1023; // /u request content
	private static final int REQ_LOGOUT = 9999;
	
	public TCPDictionaryClient() {
		super("Ŭ���̾�Ʈ");

		mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
		add(mlbl, BorderLayout.NORTH);

		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);

		Panel ptotal = new Panel(new BorderLayout());

		Panel pword = new Panel(new BorderLayout());
		wlbl = new Label("��ȭ��");
		wtext = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
	  	wtext.addKeyListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	  	pword.add(wlbl, BorderLayout.WEST);
	  	pword.add(wtext, BorderLayout.CENTER);
	  	ptotal.add(pword, BorderLayout.CENTER);
  	
	  	onoff = new Button("ON");
	  	onoff.addActionListener(this);
	  	
	  	Panel plabel = new Panel(new BorderLayout());
	  	loglbl = new Label("�α׿�");
	  	ltext = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
	  	ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	  	plabel.add(loglbl, BorderLayout.WEST);
	  	plabel.add(ltext, BorderLayout.CENTER);
	  	plabel.add(onoff, BorderLayout.EAST);
	  	ptotal.add(plabel, BorderLayout.SOUTH);

  		add(ptotal, BorderLayout.SOUTH);

	  	addWindowListener(new WinListener());
	  	setSize(600,400);
	  	setVisible(true);
	}
	
	public void runClient() {
		try {
			client = new Socket(InetAddress.getLocalHost(), 5000);
			mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			clientdata = new StringBuffer(2048);
			mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
			
			while(true) {
				serverdata = input.readLine();
				display.append(serverdata+"\r\n");
				if(serverdata.trim().equals("�̹� �����ϴ� ID �Դϴ�.")) {
					client.close();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == "ON") {
			onoff.setLabel("OFF");
			ltext.setVisible(false);
			if(client == null) {
				try {
					client = new Socket(InetAddress.getLocalHost(), 5000);
					mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
					input = new BufferedReader(new InputStreamReader(client.getInputStream()));
					output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
					clientdata = new StringBuffer(2048);
					mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
				} catch(IOException io) {
					io.printStackTrace();
				}
			}
		}
		else {
			onoff.setLabel("ON");
			ltext.setVisible(true);
			try {
				clientdata.setLength(0);
	            clientdata.append(REQ_LOGOUT);
	            clientdata.append(SEPARATOR);
	            clientdata.append(ID);
	            output.write(clientdata.toString() + "\r\n");
	            output.flush();
				client.close();
				client = null;
				ID = null;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if(client != null) {
			if(ID == null) {
				ID = ltext.getText();
				logon();
			}
		}
		else {
			mlbl.setText("������ �����ӻ��� �Դϴ�.");
		}
	}
	
	public void logon() {
		mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
		try {
			clientdata.setLength(0);
			clientdata.append(REQ_LOGON);
			clientdata.append(SEPARATOR);
			clientdata.append(ID);
			output.write(clientdata.toString() + "\r\n");
			output.flush();
			ltext.setVisible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		TCPDictionaryClient c = new TCPDictionaryClient();
		c.runClient();
	}
		
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e){
			 try {   
				 if(!client.isClosed()) {
					    clientdata.setLength(0);
			            clientdata.append(REQ_LOGOUT);
			            clientdata.append(SEPARATOR);
			            clientdata.append(ID);
			            output.write(clientdata.toString() + "\r\n");
			            output.flush();
						ID = null;
					    client.close();
				 }
			   } catch(IOException ioe) {
				   ioe.printStackTrace();
			   } catch(NullPointerException npe) {
				   System.exit(0);
			   }
			System.exit(0);
		}
	}
	public void delete(String message) { // /d ������ �ܾ�
		message = message.substring(3); // ��/d���� �����Ѵ�.
		
		clientdata.setLength(0);
		clientdata.append(REQ_DELETE);
		clientdata.append(SEPARATOR);
		clientdata.append(ID);
		clientdata.append(SEPARATOR);
		clientdata.append(message);

		try {
			output.write(clientdata.toString() + "\r\n");
			output.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
		wtext.setText("");
	}
	public void query(String message) {
		message = message.substring(3); // ��/q���� �����Ѵ�.
		
		clientdata.setLength(0);
		clientdata.append(REQ_QUERY);
		clientdata.append(SEPARATOR);
		clientdata.append(ID);
		clientdata.append(SEPARATOR);
		clientdata.append(message);

		try {
			output.write(clientdata.toString()+"\r\n");
			output.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
		wtext.setText("");
	}
	public void update(String message) {
		message = message.substring(3); // ��/u���� �����Ѵ�.
		StringTokenizer st = new StringTokenizer(message, " ");
		String word = st.nextToken();
		String means = st.nextToken();
		
		while(st.hasMoreTokens()) { // ���鹮�� ������ ���� ��ȭ���߰�
			means = means + " " + st.nextToken();
		}
		
		clientdata.setLength(0);
		clientdata.append(REQ_UPDATE);
		clientdata.append(SEPARATOR);
		clientdata.append(ID);
		clientdata.append(SEPARATOR);
		clientdata.append(word);
		clientdata.append(SEPARATOR);
		clientdata.append(means);
		try {
			output.write(clientdata.toString()+"\r\n");
			output.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
		wtext.setText("");
		display.append("\n");
	}
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String message = wtext.getText();
			StringTokenizer st = new StringTokenizer(message, " ");
			if (ID == null) {
				mlbl.setText("�ٽ� �α��� �ϼ���!!!");
				wtext.setText("");
			} else {
				switch (st.nextToken()) {
					case "/d":
						delete(message);
						break;
					case "/q":
						query(message);
						break;
					case "/u":
						update(message);
						break;
					default :
						wtext.setText("�ùٸ��� ���� �����Դϴ�.");
						break;
					}
			}
		}
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}
}