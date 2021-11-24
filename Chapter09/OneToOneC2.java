package Chapter09;

//Step 1
//서버-클라이언트 구조에서 서버와 클라이언트가 1:1로 대화하는 프로그램
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class OneToOneC2 extends Frame implements ActionListener {
	
	TextArea display;
	TextField text;
	Label lword;
	Button reset = new Button("RESET");
	BufferedWriter output;
	BufferedReader input;
	Socket client = null;
	String clientdata = "";
	String serverdata = "";
	Boolean flag;
	public OneToOneC2() {
	   super("클라이언트");
	   display=new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	
	   Panel pword = new Panel(new BorderLayout());
	   lword = new Label("대화말");
	   text = new TextField(30); //전송할 데이터를 입력하는 필드
	   text.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	   reset.addActionListener(this);
	   pword.add(lword, BorderLayout.WEST);
	   pword.add(text, BorderLayout.CENTER);
	   pword.add(reset, BorderLayout.EAST);
	   add(pword, BorderLayout.SOUTH);
	
	   addWindowListener(new WinListener());
	   setSize(400, 200);
	   setVisible(true);
	}
		
	public void runClient() {
	   try {
	      client = new Socket(InetAddress.getLocalHost(), 5000);
	      display.append("[서버 : " + client.getInetAddress().getHostAddress() + ":" +
	    		  client.getPort()+ "와 연결이 되었습니다.]");
	      flag = true;
	      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
	      output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	      while(true) {
	         String serverdata = input.readLine();
	         if(serverdata == null) {
	            display.append("\n서버와의 접속이 중단되었습니다.");
		    	System.exit(0);
	            flag = false;
	            break;
	         } else if(serverdata.equals("exit")) {
	        	 System.exit(0);
	         } else {
	            display.append("\n     서버     : " + serverdata);
	         }
	      }
	      client.close();
	   } catch(IOException e) {
	      e.printStackTrace();
	   } catch(NullPointerException ne) {
		   System.exit(0);
	   }
	}
			
	public void actionPerformed(ActionEvent ae){
	   clientdata = text.getText();
	   if(reset.equals(ae.getSource())) {
			Reconnect re = new Reconnect();
			re.start();
	   }
	   else {
		   try {
			   if(!flag) {
				   text.setText("");
				   display.append("\n");
				   return;
			   } else {
				   display.append("\n클라이언트 : " + clientdata);
				   output.write(clientdata + "\r\n");
				   output.flush();
				   if(clientdata.equals("quit")){
					   flag = false;
					   display.append("\n");
					   client.close();
				   }
				   else if(serverdata.equals("exit")) {
			        	 System.exit(0);
			       }
				   
			   }
			   text.setText("");
			   } catch(IOException e){
			      e.printStackTrace();
			   }
	   }
	}
	
	class Reconnect extends Thread {
		public void run() {
			try {	
				client = new Socket(InetAddress.getLocalHost(), 5000);
			    display.append("[서버 : " + client.getInetAddress().getHostAddress() + ":" +
			    		client.getPort()+ "와 연결이 되었습니다.]");
				flag = true;
				input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				while(true) {
					String serverdata = input.readLine();
					if(serverdata == null) {
				    	display.append("\n서버와의 접속이 중단되었습니다.");
				    	flag = false;
				    	break;
				    } else {
				    	display.append("\n     서버     : " + serverdata);
				    }
				}
				client.close();
			} catch(IOException e ) {
				e.printStackTrace();
	   		}
		}
	}
	
	public static void main(String args[]) {
	   OneToOneC2 c = new OneToOneC2();
	   c.runClient();
	}
			
	class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {
		   try {
			   client.close();
		   } catch(IOException ioe) {
			   ioe.printStackTrace();
		   }
		   System.exit(0);
	   }
	}			
}