package Chapter9;

//Step 2
//����-Ŭ���̾�Ʈ �������� �ټ��� Ŭ���̾�Ʈ�� ��ȭ�ϴ� ���α׷�
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
	   super("Ŭ���̾�Ʈ");
	   display=new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	 
	   Panel pword = new Panel(new GridLayout(1, 4));
	   lword = new Label("��ȭ��");
	   text = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
	   id = new TextField(10);
	   text.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
	   pword.add(id);
	   id.setText("ID �Է�");
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