package Chapter09;

//Step 4
/*
- 서버 미실행 시 클라이언트 접속 시도 오류 처리 O 
- 로그온 중인 클라이언트들에만 채팅 메시지 전송되도록 수정 O
- 클라이언트 로그온 아이디 입력 텍스트 필드 뒤에 'ON' 버튼 추가. 로그온 후 로그온 창은 ‘invisible’로, 버튼은 'OFF'로 변경 O
- 서버는 로그온 아이디 중복을 확인 후 승인 또는 거절 메시지를 클라이언트로 송신함 O
- 'OFF' 클릭 시 로그아웃 진행. REQ_LOGOUT 메시지 정의하여 처리, 로그아웃 이벤트를 접속 중인 사용자들에게 전송 O
- 로그아웃 시 클라이언트의 화면은 초기화면으로 되며, 서버는 클라이언트의 아이디를 삭제함 O
- 로그온 시 기로그온 중인 사용자들에게 로그온 이벤트를 전송 O
- 로그온 중인 클라이언트 윈도우 종료 시 소켓 해제하고 서버는 접속 중인 클라언트들에게 알림 O
 */
//클라이언트 간의 채팅에서 특정 클라이언트와의 귓속말 구현
//다음의 형식으로 귓속말 전송
///w 상대방아이디 대화말
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
		super("클라이언트");

		mlbl = new Label("채팅 상태를 보여줍니다.");
		add(mlbl, BorderLayout.NORTH);

		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);

		Panel ptotal = new Panel(new BorderLayout());

		Panel pword = new Panel(new BorderLayout());
		wlbl = new Label("대화말");
    	wtext = new TextField(30); //전송할 데이터를 입력하는 필드
    	wtext.addKeyListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
    	pword.add(wlbl, BorderLayout.WEST);
    	pword.add(wtext, BorderLayout.CENTER);
    	ptotal.add(pword, BorderLayout.CENTER);
    	
    	onoff = new Button("ON");
    	onoff.addActionListener(this);
    	
    	Panel plabel = new Panel(new BorderLayout());
    	loglbl = new Label("로그온");
    	ltext = new TextField(30); //전송할 데이터를 입력하는 필드
    	ltext.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
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
			mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			clientdata = new StringBuffer(2048);
			mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
			
			while(true) {
				serverdata = input.readLine();
				display.append(serverdata+"\r\n");
				if(serverdata.trim().equals("이미 존재하는 ID 입니다.")) {
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
					mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
					input = new BufferedReader(new InputStreamReader(client.getInputStream()));
					output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
					clientdata = new StringBuffer(2048);
					mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
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
			mlbl.setText("서버가 미접속상태 입니다.");
		}
	}
	
	public void logon() {
		mlbl.setText(ID + "(으)로 로그인 하였습니다.");
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
				mlbl.setText("다시 로그인 하세요!!!");
				wtext.setText("");
			} else {
				try {
					if(st.nextToken().equals("/w")) {
						message = message.substring(3); // “/w”를 삭제한다.
						String WID = st.nextToken();
						
						String Wmessage = st.nextToken();
						while(st.hasMoreTokens()) { // 공백문자 다음에 오는 대화말추가
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