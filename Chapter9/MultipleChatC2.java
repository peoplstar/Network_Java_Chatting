package Chapter9;

//Step 2
//서버-클라이언트 구조에서 다수의 클라이언트가 대화하는 프로그램
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class MultipleChatC2 extends Frame implements ActionListener {
	TextArea display;
	TextField text;
	TextField id;
	Label lword;
	BufferedWriter output;
	BufferedReader input;
	Socket client = null;
	String clientid = "";
	String clientdata = "";
	String serverdata = "";
	
	public MultipleChatC2() {
	   super("클라이언트");
	   display=new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	 
	   Panel pword = new Panel(new GridLayout(1, 4));
	   lword = new Label("대화말");
	   text = new TextField(30); //전송할 데이터를 입력하는 필드
	   id = new TextField(10);
	   text.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
	   pword.add(id);
	   id.setText("ID 입력");
	   pword.add(lword);
	   pword.add(text);
	   add(pword, BorderLayout.SOUTH);
	
	   addWindowListener(new WinListener());
	   setSize(400, 150);
	   setVisible(true);
	}
		
	public void runClient() {
	   try {
	      client = new Socket(InetAddress.getLocalHost(), 5000);
	      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
	      output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	      while(true) {
	         String serverdata = input.readLine();
	         display.append(serverdata + "\r\n");
	      }
	   } catch(IOException e) {
	      e.printStackTrace();
	   }
	   try {
	      client.close();
	   } catch(IOException e){
	      e.printStackTrace();
	   }
	}
			
	public void actionPerformed(ActionEvent ae){
	   clientdata = text.getText();
	   clientid = id.getText();
	   if (!clientdata.equals("quit")) {
		   try {
		       display.append(clientid + " : " + clientdata + "\n");
		       output.write(clientid + " : " + clientdata + "\r\n");
		       output.flush();
		       text.setText("");
		   } catch(IOException e){
		      e.printStackTrace();
		   }
	   }
	   else {
		   try {
			   output.write("quit" + "\r\n");
			   output.flush();
			   client.close(); 
			   System.exit(0);
		   } catch(IOException e) {
			   e.printStackTrace();
		   }  
	   }
	}
			
	public static void main(String args[]) {
	   MultipleChatC2 c = new MultipleChatC2();
	   c.runClient();
	}
			
	class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {	  
		   try {
			   if(client != null) {
				   client.close();
			   }	      
		   } catch(IOException ioe) {
			   ioe.printStackTrace();
		   }
		   System.exit(0);
	   }
	}			
}