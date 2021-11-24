package Chapter09;

//Step 1
//����-Ŭ���̾�Ʈ �������� ������ Ŭ���̾�Ʈ�� 1:1�� ��ȭ�ϴ� ���α׷�
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
	   super("Ŭ���̾�Ʈ");
	   display=new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	
	   Panel pword = new Panel(new BorderLayout());
	   lword = new Label("��ȭ��");
	   text = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
	   text.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
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
	      display.append("[���� : " + client.getInetAddress().getHostAddress() + ":" +
	    		  client.getPort()+ "�� ������ �Ǿ����ϴ�.]");
	      flag = true;
	      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
	      output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	      while(true) {
	         String serverdata = input.readLine();
	         if(serverdata == null) {
	            display.append("\n�������� ������ �ߴܵǾ����ϴ�.");
		    	System.exit(0);
	            flag = false;
	            break;
	         } else if(serverdata.equals("exit")) {
	        	 System.exit(0);
	         } else {
	            display.append("\n     ����     : " + serverdata);
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
				   display.append("\nŬ���̾�Ʈ : " + clientdata);
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
			    display.append("[���� : " + client.getInetAddress().getHostAddress() + ":" +
			    		client.getPort()+ "�� ������ �Ǿ����ϴ�.]");
				flag = true;
				input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				while(true) {
					String serverdata = input.readLine();
					if(serverdata == null) {
				    	display.append("\n�������� ������ �ߴܵǾ����ϴ�.");
				    	flag = false;
				    	break;
				    } else {
				    	display.append("\n     ����     : " + serverdata);
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