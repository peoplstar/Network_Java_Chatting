package Chapter9;

//Step 1
//����/Ŭ���̾�Ʈ �������� ������ Ŭ���̾�Ʈ�� 1:1�� ��ȭ�ϴ� ���α׷�
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
	   super("����");
	   display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	   display.setEditable(false);
	   add(display, BorderLayout.CENTER);
	
	   Panel pword = new Panel(new BorderLayout());
	   lword = new Label("��ȭ��");
	   text = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
	   text.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
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
			while(true) { // ���� �� �翬�� ��û�� ���� ACCEPT �ʿ�
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
			display.append("[���� ����]\n");
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
				input = new BufferedReader(isr); // ������ ������ ��ȭ���� ����
				os = connection.getOutputStream();
				osw = new OutputStreamWriter(os);
				output = new BufferedWriter(osw); // Ŭ���̾�Ʈ�� ��ȭ���� ����
				cs.output = this.output;
				cs.input = this.input;
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			display.append("[Ŭ���̾�Ʈ : " + connection.getInetAddress().getHostAddress() + ":" +
					   connection.getPort() + "�� ������ �Ǿ����ϴ�.]");
			try {
			     while(true) {
			    	 String clientdata = input.readLine();
			         if(clientdata.equals("quit")) {
		             display.append("\nŬ���̾�Ʈ���� ������ �ߴܵǾ����ϴ�.\n");
		             flag = false;
		             break;
			         }
			         else {
			        	 display.append("\nŬ���̾�Ʈ : " + clientdata);
			         }
		        }
			} catch(NullPointerException npe) {
				display.append("\nŬ���̾�Ʈ���� ������ �ߴܵǾ����ϴ�.");
			} catch(IOException e) {
			      e.printStackTrace();
			}
			try {
				connection.close();   //���ϴݱ�
			} catch(IOException ea){
				ea.printStackTrace();
			} 
		}
	}
	
	public void actionPerformed(ActionEvent ae){
	   serverdata = text.getText();
	   try {
		   if(flag) {
			   display.append("\n     ����     : " + serverdata);
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