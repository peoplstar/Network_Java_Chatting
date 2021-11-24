package Chapter09;


/*
STEP 3
1. 서버 미 실행시 클라이언트 접속오류 수정 O
2. 로그온 후 plabel 패널을 로그아웃 버튼으로 변경 O
3. 로그아웃 구현 O
4. 새 클라이언트 로그온 시 이벤트를 기 접속자에 알림 O
5. 윈도우 종료시 로그아웃 되도록 O
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
		super("서버");
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
	    			info.setText(sock.getInetAddress().getHostName() + " 서버는 클라이언트와 연결됨");
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
			//cs.list.add(this); // 로그온 전에 리스트 등록
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int cnt = cs.list.size();
				switch(command) {
					case REQ_LOGON : { // “1001|아이디”를 수신한 경우
						String ID = st.nextToken();
						display.append("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
						for(int i = 0; i < cnt; i++) { // 모든 클라이언트에 전송
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
							SThread.output.flush();
						}
						cs.list.add(this);// 로그온 이후 리스트 등록
						break;
					}
					case REQ_SENDWORDS : { // “1021|아이디|대화말”를 수신
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n");
						for(int i = 0; i < cnt; i++) { // 모든 클라이언트에 전송
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n");
							SThread.output.flush();
						}
						break;
					}
					case REQ_LOGOUT : {
						String ID = st.nextToken();
						display.append("클라이언트 " + ID + " 이(가) 로그아웃 하였습니다." +  "\r\n");
						for(int i = 0; i < cnt; i++) { // 모든 클라이언트에 전송
							ServerThread1 SThread = (ServerThread1)cs.list.get(i);
							SThread.output.write("클라이언트 " + ID + " 이(가) 로그아웃 하였습니다." + "\r\n");
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