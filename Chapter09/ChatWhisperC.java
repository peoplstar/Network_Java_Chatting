package Chapter09;

//Step 4
/*
- ���� �̽��� �� Ŭ���̾�Ʈ ���� �õ� ���� ó�� O 
- �α׿� ���� Ŭ���̾�Ʈ�鿡�� ä�� �޽��� ���۵ǵ��� ���� O
- Ŭ���̾�Ʈ �α׿� ���̵� �Է� �ؽ�Ʈ �ʵ� �ڿ� 'ON' ��ư �߰�. �α׿� �� �α׿� â�� ��invisible����, ��ư�� 'OFF'�� ���� O
- ������ �α׿� ���̵� �ߺ��� Ȯ�� �� ���� �Ǵ� ���� �޽����� Ŭ���̾�Ʈ�� �۽��� O
- 'OFF' Ŭ�� �� �α׾ƿ� ����. REQ_LOGOUT �޽��� �����Ͽ� ó��, �α׾ƿ� �̺�Ʈ�� ���� ���� ����ڵ鿡�� ���� O
- �α׾ƿ� �� Ŭ���̾�Ʈ�� ȭ���� �ʱ�ȭ������ �Ǹ�, ������ Ŭ���̾�Ʈ�� ���̵� ������ O
- �α׿� �� ��α׿� ���� ����ڵ鿡�� �α׿� �̺�Ʈ�� ���� O
- �α׿� ���� Ŭ���̾�Ʈ ������ ���� �� ���� �����ϰ� ������ ���� ���� Ŭ���Ʈ�鿡�� �˸� O
 */
//Ŭ���̾�Ʈ ���� ä�ÿ��� Ư�� Ŭ���̾�Ʈ���� �ӼӸ� ����
//������ �������� �ӼӸ� ����
///w ������̵� ��ȭ��
//package Client;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWhisperC extends Frame implements ActionListener, KeyListener {
	
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
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_LOGOUT = 9999;
	
	public ChatWhisperC() {
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
    	setSize(400,250);
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
	            output.write(clientdata.toString()+"\r\n");
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
			output.write(clientdata.toString()+"\r\n");
			output.flush();
			ltext.setVisible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		ChatWhisperC c = new ChatWhisperC();
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
			            output.write(clientdata.toString()+"\r\n");
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

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String message = wtext.getText();
			StringTokenizer st = new StringTokenizer(message, " ");
			if (ID == null) {
				mlbl.setText("�ٽ� �α��� �ϼ���!!!");
				wtext.setText("");
			} else {
				try {
					if(st.nextToken().equals("/w")) {
						message = message.substring(3); // ��/w���� �����Ѵ�.
						String WID = st.nextToken();
						
						String Wmessage = st.nextToken();
						while(st.hasMoreTokens()) { // ���鹮�� ������ ���� ��ȭ���߰�
							Wmessage = Wmessage + " " + st.nextToken();
						}
						
						clientdata.setLength(0);
						clientdata.append(REQ_WISPERSEND);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(WID);
						clientdata.append(SEPARATOR);
						clientdata.append(Wmessage);
						output.write(clientdata.toString()+"\r\n");
						output.flush();
						wtext.setText("");
					} else {
						clientdata.setLength(0);
						clientdata.append(REQ_SENDWORDS);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(message);
						output.write(clientdata.toString()+"\r\n");
						output.flush();
						wtext.setText("");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}
}