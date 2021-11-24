package Chapter09;

//Step 4
/*
- 서버 미실행 시 클라이언트 접속 시도 오류 처리 O
- 로그온 중인 클라이언트들에만 채팅 메시지 전송되도록 수정 O
- 클라이언트 로그온 아이디 입력 텍스트 필드 뒤에 'ON' 버튼 추가. 로그온 후 로그온 창은 ‘invisible’로, 버튼은 'OFF'로 변경 O
- 서버는 로그온 아이디 중복을 확인 후 승인 또는 거절 메시지를 클라이언트로 송신함
- 'OFF' 클릭 시 로그아웃 진행. REQ_LOGOUT 메시지 정의하여 처리, 로그아웃 이벤트를 접속 중인 사용자들에게 전송
- 로그아웃 시 클라이언트의 화면은 초기화면으로 되며, 서버는 클라이언트의 아이디를 삭제함
- 로그온 시 기로그온 중인 사용자들에게 로그온 이벤트를 전송
- 로그온 중인 클라이언트 윈도우 종료 시 소켓 해제하고 서버는 접속 중인 클라언트들에게 알림
 */
//클라이언트 간의 채팅에서 특정 클라이언트와의 귓속말 구현
//다음의 형식으로 귓속말 전송
///w 상대방아이디 대화말
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
						//ServerThread2 sameID = (ServerThread2)cs.hash.get(ID); sameID 비교시 사용
						
						if(/*sameID != null*/cs.hash.containsKey(ID)) {
							this.output.write("이미 존재하는 ID 입니다.\r\n");
							this.output.flush();
							this.interrupt();
							cs.list.remove(this);
							continue;
						}
						else {
							//cs.list.add(this);
							cs.hash.put(ID, this); // 해쉬 테이블에 아이디와 스레드를 저장한다
							
						}
						display.append("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
						
						for(int i = 0; i < Lcnt; i++) { // 모든 클라이언트에 전송
							ServerThread2 SThread = (ServerThread2)cs.list.get(i);
							SThread.output.write("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
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
						// 해쉬테이블에서 귓속말 메시지를 전송한 클라이언트의 스레드를 구함
						SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
						// 귓속말 메시지를 전송한 클라이언트에 전송함
						SThread.output.flush();
						SThread = (ServerThread2)cs.hash.get(WID);
						// 해쉬테이블에서 귓속말 메시지를 수신할 클라이언트의 스레드를 구함
						SThread.output.write(ID + " : " + message + "\r\n");
						// 귓속말 메시지를 수신할 클라이언트에 전송함
						SThread.output.flush();
						break;
					}
					case REQ_LOGOUT : {
						String ID = st.nextToken();
						display.append("클라이언트 [" + ID + "] 로그아웃 하였습니다." +  "\r\n");

						for(int i = 0; i < Lcnt; i++) { // 모든 클라이언트에 전송
							ServerThread2 SThread = (ServerThread2)cs.list.get(i);
							SThread.output.write("클라이언트 [" + ID + "] 로그아웃 하였습니다." + "\r\n");
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