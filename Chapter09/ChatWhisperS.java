package Chapter09;

//Step 4
/*
- ���� �̽��� �� Ŭ���̾�Ʈ ���� �õ� ���� ó�� O
- �α׿� ���� Ŭ���̾�Ʈ�鿡�� ä�� �޽��� ���۵ǵ��� ���� O
- Ŭ���̾�Ʈ �α׿� ���̵� �Է� �ؽ�Ʈ �ʵ� �ڿ� 'ON' ��ư �߰�. �α׿� �� �α׿� â�� ��invisible����, ��ư�� 'OFF'�� ���� O
- ������ �α׿� ���̵� �ߺ��� Ȯ�� �� ���� �Ǵ� ���� �޽����� Ŭ���̾�Ʈ�� �۽���
- 'OFF' Ŭ�� �� �α׾ƿ� ����. REQ_LOGOUT �޽��� �����Ͽ� ó��, �α׾ƿ� �̺�Ʈ�� ���� ���� ����ڵ鿡�� ����
- �α׾ƿ� �� Ŭ���̾�Ʈ�� ȭ���� �ʱ�ȭ������ �Ǹ�, ������ Ŭ���̾�Ʈ�� ���̵� ������
- �α׿� �� ��α׿� ���� ����ڵ鿡�� �α׿� �̺�Ʈ�� ����
- �α׿� ���� Ŭ���̾�Ʈ ������ ���� �� ���� �����ϰ� ������ ���� ���� Ŭ���Ʈ�鿡�� �˸�
 */
//Ŭ���̾�Ʈ ���� ä�ÿ��� Ư�� Ŭ���̾�Ʈ���� �ӼӸ� ����
//������ �������� �ӼӸ� ����
///w ������̵� ��ȭ��
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class ChatWhisperS extends Frame {
	TextArea display;
	Label info;
	List<ServerThread2> list;
	Hashtable<String, ServerThread2> hash;
	public ServerThread2 SThread;
	
	public ChatWhisperS() {
		super("����");
		info = new Label();
		add(info, BorderLayout.CENTER);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(400,250);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		Socket sock;
		ServerThread2 SThread;
		try {
			server = new ServerSocket(5000, 100);
			hash = new Hashtable<String, ServerThread2>();
			list = new ArrayList<ServerThread2>();
			try {
				while(true) {
					sock = server.accept();
					SThread = new ServerThread2(this, sock, display, info);
					SThread.start();
					info.setText(sock.getInetAddress().getHostName() + " ������ Ŭ���̾�Ʈ�� �����");
				}
			} catch(IOException ioe) {
				server.close();
				ioe.printStackTrace();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
				
	}

	public static void main(String args[]) {
		ChatWhisperS s = new ChatWhisperS();
		s.runServer();
	}
			
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread2 extends Thread {
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatWhisperS cs;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_LOGOUT = 9999;
	
	public ServerThread2(ChatWhisperS c, Socket s, TextArea ta, Label l) {
		sock = s;
		display = ta;
		info = l;
		cs = c;
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void run() {
		try {
			
			while((clientdata = input.readLine()) != null) {
				display.append(clientdata + '\n');
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int Lcnt = cs.list.size();
				switch(command) {
					case REQ_LOGON : {
						cs.list.add(this);
						
						String ID = st.nextToken();
						//ServerThread2 sameID = (ServerThread2)cs.hash.get(ID); sameID �񱳽� ���
						
						if(/*sameID != null*/cs.hash.containsKey(ID)) {
							this.output.write("�̹� �����ϴ� ID �Դϴ�.\r\n");
							this.output.flush();
							this.interrupt();
							cs.list.remove(this);
							continue;
						}
						else {
							//cs.list.add(this);
							cs.hash.put(ID, this); // �ؽ� ���̺� ���̵�� �����带 �����Ѵ�
							
						}
						display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
						
						for(int i = 0; i < Lcnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread2 SThread = (ServerThread2)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
							SThread.output.flush();
						}
						break;
					}
					case REQ_SENDWORDS : {
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n");

						for(int i = 0; i < Lcnt; i++) {
							ServerThread2 SThread = (ServerThread2)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n");
							SThread.output.flush();
						}
						break;
					}
					case REQ_WISPERSEND : {
						String ID = st.nextToken();
						String WID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " -> " + WID + " : " + message + "\r\n");
						ServerThread2 SThread = (ServerThread2)cs.hash.get(ID);
						// �ؽ����̺��� �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� �����带 ����
						SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
						// �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� ������
						SThread.output.flush();
						SThread = (ServerThread2)cs.hash.get(WID);
						// �ؽ����̺��� �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� �����带 ����
						SThread.output.write(ID + " : " + message + "\r\n");
						// �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� ������
						SThread.output.flush();
						break;
					}
					case REQ_LOGOUT : {
						String ID = st.nextToken();
						display.append("Ŭ���̾�Ʈ [" + ID + "] �α׾ƿ� �Ͽ����ϴ�." +  "\r\n");

						for(int i = 0; i < Lcnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread2 SThread = (ServerThread2)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ [" + ID + "] �α׾ƿ� �Ͽ����ϴ�." + "\r\n");
							SThread.output.flush();
						}
						cs.hash.remove(ID, this);
						cs.list.remove(this);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		cs.list.remove(this);
		try {
			sock.close();
		} catch(IOException ea){
			ea.printStackTrace();
		}
	}
}