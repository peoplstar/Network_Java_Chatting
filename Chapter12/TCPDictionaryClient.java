package Chapter12;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
/*
- 클라이언트는 서버에 로그인 한 후 사전 서비스 이용 가능
- 동시 로그인 사용자 수 제한
- 클라이언트는 단어를 질의/삭제/수정할 수 있음
- 서버는 스레드를 이용한 다중처리
- 사전은 파일로 관리
- 클라이언트-서버간 프로토콜 메시지 정의
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
	  	setSize(600,400);
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
	public void delete(String message) { // /d 삭제할 단어
		message = message.substring(3); // “/d”를 삭제한다.
		
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
		message = message.substring(3); // “/q”를 삭제한다.
		
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
		message = message.substring(3); // “/u”를 삭제한다.
		StringTokenizer st = new StringTokenizer(message, " ");
		String word = st.nextToken();
		String means = st.nextToken();
		
		while(st.hasMoreTokens()) { // 공백문자 다음에 오는 대화말추가
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
				mlbl.setText("다시 로그인 하세요!!!");
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
						wtext.setText("올바르지 않은 형식입니다.");
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