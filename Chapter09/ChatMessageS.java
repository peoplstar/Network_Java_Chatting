package Chapter09;


/*
STEP 3
1. ���� �� ����� Ŭ���̾�Ʈ ���ӿ��� ���� O
2. �α׿� �� plabel �г��� �α׾ƿ� ��ư���� ���� O
3. �α׾ƿ� ���� O
4. �� Ŭ���̾�Ʈ �α׿� �� �̺�Ʈ�� �� �����ڿ� �˸� O
5. ������ ����� �α׾ƿ� �ǵ��� O
*/
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class ChatMessageS extends Frame {
	TextArea display;
	Label info;
	List<ServerThread1> list;
	
	public ServerThread1 SThread;
	
	public ChatMessageS() {
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
	    ServerThread1 SThread;
	    try {
	    	list = new ArrayList<ServerThread1>();
	    	server = new ServerSocket(5000, 100);
	    	try {
	    		while(true) {
	    			sock = server.accept();
	    			SThread = new ServerThread1(this, sock, display, info);
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
		ChatMessageS s = new ChatMessageS();
		s.runServer();
	}
			
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class ServerThread1 extends Thread {
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	ChatMessageS cs;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_LOGOUT = 9999;
	
	public ServerThread1(ChatMessageS c, Socket s, TextArea ta, Label l) {
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
			//cs.list.add(this); // �α׿� ���� ����Ʈ ���
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int cnt = cs.list.size();
				switch(command) {
					case REQ_LOGON : { // ��1001|���̵𡱸� ������ ���
						String ID = st.nextToken();
						display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
						for(int i = 0; i < cnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
							SThread.output.flush();
						}
						cs.list.add(this);// �α׿� ���� ����Ʈ ���
						break;
					}
					case REQ_SENDWORDS : { // ��1021|���̵�|��ȭ������ ����
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n");
						for(int i = 0; i < cnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n");
							SThread.output.flush();
						}
						break;
					}
					case REQ_LOGOUT : {
						String ID = st.nextToken();
						display.append("Ŭ���̾�Ʈ " + ID + " ��(��) �α׾ƿ� �Ͽ����ϴ�." +  "\r\n");
						for(int i = 0; i < cnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ " + ID + " ��(��) �α׾ƿ� �Ͽ����ϴ�." + "\r\n");
							SThread.output.flush();
						}
						cs.list.remove(this);
						break;
					}
				}
			}
		} catch(IOException e) {
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