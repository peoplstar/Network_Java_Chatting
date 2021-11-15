package Chapter9;

//Step 2
//�޽����� �̿����� �ʰ� �ټ��� Ŭ���̾�Ʈ���� ü�����α׷�
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
	   super("����");
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
	            		" Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.]");
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
	            		" Ŭ���̾�Ʈ�� ���� �����Ǿ����ϴ�.]");
	    		 break;
	    	 }
	         //display.append(clientdata + "\r\n");
	         int cnt = cs.list.size();
	         for(int i=0; i<cnt; i++) { //��� Ŭ���̾�Ʈ�� �����͸� �����Ѵ�.
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
	   cs.list.remove(this); //����Ʈ���� close�� Ŭ���̾�Ʈ�� �����.
	   try{
	      sock.close();   //���ϴݱ�
	   }catch(IOException ea){
	      ea.printStackTrace();
	   }
	}
}
