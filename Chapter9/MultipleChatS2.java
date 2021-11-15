package Chapter9;

//Step 2
//메시지를 이용하지 않고 다수의 클라이언트간의 체팅프로그램
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class MultipleChatS2 extends Frame {
	TextArea display;
	String clientdata = "";
	String serverdata = "";
	List<ServerThread> list;
	public ServerThread SThread;
	
	public MultipleChatS2() {
	   super("서버");
	   display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display);
	   addWindowListener(new WinListener());
	   setSize(400,250);
	   setVisible(true);
	}
		
	public void runServer() {
	   ServerSocket server;
	   Socket sock;
	   ServerThread SThread;
	   try {
	      list = new ArrayList<ServerThread>();
	      server = new ServerSocket(5000, 100);
	      try {
	         while(true) {
	            sock = server.accept();
	            SThread = new ServerThread(this, sock, display, serverdata);
	            SThread.start();
	            display.append("[" +sock.getInetAddress().getHostAddress() + ":" +sock.getPort() + 
	            		" 클라이언트와 연결되었습니다.]");
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
	   MultipleChatS2 s = new MultipleChatS2();
	   s.runServer();
	}
		
	class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {
	      System.exit(0);
	   }
	}
	}
	
	class ServerThread extends Thread {
		Socket sock;
		InputStream is;
		InputStreamReader isr;
		BufferedReader input;
		OutputStream os;
		OutputStreamWriter osw;
		BufferedWriter output;
		TextArea display;
		Label info;
		TextField text;
		String serverdata = "";
		MultipleChatS2 cs;
		
	public ServerThread(MultipleChatS2 c, Socket s, TextArea ta, String data) {
	   sock = s;
	   display = ta;
	   serverdata = data;
	   cs = c;
	   try {
	      is = sock.getInputStream();
	      isr = new InputStreamReader(is);       
	      input = new BufferedReader(isr);
	      os = sock.getOutputStream();
	      osw = new OutputStreamWriter(os);
	      output = new BufferedWriter(osw);
	   } catch(IOException ioe) {
	      ioe.printStackTrace();
	   }
	}
	public void run() {
	   cs.list.add(this);
	   String clientdata;
	   try {
	      while((clientdata = input.readLine()) != null) {
	    	 if(clientdata.equals("quit")) {
	    		 display.append("[" +sock.getInetAddress().getHostAddress() + ":" +sock.getPort() + 
	            		" 클라이언트와 연결 해제되었습니다.]");
	    		 break;
	    	 }
	         //display.append(clientdata + "\r\n");
	         int cnt = cs.list.size();
	         for(int i=0; i<cnt; i++) { //모든 클라이언트에 데이터를 전송한다.
	            ServerThread SThread = (ServerThread)cs.list.get(i);
	            if(SThread != this) { 
	            	SThread.output.write(clientdata + "\r\n");
	            	SThread.output.flush();
	            }
	         }
	      }
	   } catch(IOException e) {
	      e.printStackTrace();
	   }
	   cs.list.remove(this); //리스트에서 close된 클라이언트를 지운다.
	   try{
	      sock.close();   //소켓닫기
	   }catch(IOException ea){
	      ea.printStackTrace();
	   }
	}
}
