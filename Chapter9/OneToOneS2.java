package Chapter9;

//Step 1
//서버/클라이언트 구조에서 서버와 클라이언트가 1:1로 대화하는 프로그램
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
public class OneToOneS2 extends Frame implements ActionListener {
	TextArea display;
	TextField text;
	Label lword;
	Socket connection = null;
	BufferedWriter output;
	BufferedReader input;
	String clientdata = "";
	String serverdata = "";
	Boolean flag = false;
	ServerSocket server;
	
	public OneToOneS2() {
	   super("서버");
	   display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	
	   Panel pword = new Panel(new BorderLayout());
	   lword = new Label("대화말");
	   text = new TextField(30); //전송할 데이터를 입력하는 필드
	   text.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	   pword.add(lword, BorderLayout.WEST);
	   pword.add(text, BorderLayout.EAST);
	   add(pword, BorderLayout.SOUTH);
	
	   addWindowListener(new WinListener());
	   setSize(400,200);
	   setVisible(true);
	}
	
	public void runServer() {  
		try {
			server = new ServerSocket(5000, 100);
			while(true) { // 종료 후 재연결 요청을 위한 ACCEPT 필요
				connection = server.accept();
				if(flag) {
					connection.close();
					continue;
				}
				flag = true;
				ServerThread re = new ServerThread(this, connection);
			  	re.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NullPointerException ne) {
			display.append("[접속 종료]\n");
		}
	}
	
	class ServerThread extends Thread {
		OneToOneS2 cs;
		InputStream is;
		InputStreamReader isr;
		OutputStream os;
		OutputStreamWriter osw;
		BufferedReader input;
		BufferedWriter output;
		public ServerThread(OneToOneS2 c, Socket connection) {
			cs = c;
			try {
				is = connection.getInputStream();
				isr = new InputStreamReader(is);
				input = new BufferedReader(isr); // 서버가 전송한 대화말을 수신
				os = connection.getOutputStream();
				osw = new OutputStreamWriter(os);
				output = new BufferedWriter(osw); // 클라이언트에 대화말을 전송
				cs.output = this.output;
				cs.input = this.input;
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			display.append("[클라이언트 : " + connection.getInetAddress().getHostAddress() + ":" +
					   connection.getPort() + "와 연결이 되었습니다.]");
			try {
			     while(true) {
			    	 String clientdata = input.readLine();
			         if(clientdata.equals("quit")) {
		             display.append("\n클라이언트와의 접속이 중단되었습니다.\n");
		             flag = false;
		             break;
			         }
			         else {
			        	 display.append("\n클라이언트 : " + clientdata);
			         }
		        }
			} catch(NullPointerException npe) {
				display.append("\n클라이언트와의 접속이 중단되었습니다.");
			} catch(IOException e) {
			      e.printStackTrace();
			}
			try {
				connection.close();   //소켓닫기
			} catch(IOException ea){
				ea.printStackTrace();
			} 
		}
	}
	
	public void actionPerformed(ActionEvent ae){
	   serverdata = text.getText();
	   try {
		   if(flag) {
			   display.append("\n     서버     : " + serverdata);
			   output.write(serverdata + "\r\n");
			   output.flush();
			   text.setText(" ");
			   if(serverdata.equals("quit")){
				   connection.close();
			   }
		   }
	   } catch(IOException e) {
	      e.printStackTrace();
	   }
	}
			
	public static void main(String args[]) {
	   OneToOneS2 s = new OneToOneS2();
	   s.runServer();
	}
			
	class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {
		  try {
			  connection.close();
		  } catch(IOException ioe) {
			  ioe.printStackTrace();
		  }
	      System.exit(0);
	   }
	}
}