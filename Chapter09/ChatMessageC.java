package Chapter09;
/*
STEP 3
1. ���� �� ����� Ŭ���̾�Ʈ ���ӿ��� ���� O
2. �α׿� �� plabel �г��� �α׾ƿ� ��ư���� ���� O
3. �α׾ƿ� ���� O
4. �� Ŭ���̾�Ʈ �α׿� �� �̺�Ʈ�� �� �����ڿ� �˸� O 
5. ������ ����� �α׾ƿ� �ǵ��� O
 */
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class ChatMessageC extends Frame implements ActionListener, KeyListener {
	
	TextArea display;
	TextField wtext, ltext;
	Label mlbl, wlbl, loglbl;
	Button logout;
	Panel ptotal, plabel;
	BufferedWriter output;
	BufferedReader input;
	Socket client = null;
	StringBuffer clientdata;
	String serverdata;
	String ID;
	
    private static final String SEPARATOR = "|";
    private static final int REQ_LOGON = 1001;
    private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_LOGOUT = 9999;
	
    public ChatMessageC() {
    	super("Ŭ���̾�Ʈ");

    	mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
    	add(mlbl, BorderLayout.NORTH);

    	display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
    	display.setEditable(false);
    	add(display, BorderLayout.CENTER);

    	ptotal = new Panel(new BorderLayout());
 
    	Panel pword = new Panel(new BorderLayout());
    	wlbl = new Label("��ȭ��");
    	wtext = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
    	wtext.addKeyListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
    	pword.add(wlbl, BorderLayout.WEST);
    	pword.add(wtext, BorderLayout.EAST);
    	ptotal.add(pword, BorderLayout.CENTER);

    	plabel = new Panel(new BorderLayout());
    	loglbl = new Label("�α׿�");
    	ltext = new TextField(30); //������ �����͸� �Է��ϴ� �ʵ�
    	ltext.addActionListener(this); //�Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
    	plabel.add(loglbl, BorderLayout.WEST);
    	plabel.add(ltext, BorderLayout.EAST);
    	ptotal.add(plabel, BorderLayout.SOUTH); // Button���� ����
    	logout = new Button("�α׾ƿ�");
    	logout.addActionListener(this);
    	add(ptotal, BorderLayout.SOUTH);

    	addWindowListener(new WinListener());
    	setSize(400,250);
    	setVisible(true);
    }
	
    public void runClient() {
    	try {
    		client = new Socket(InetAddress.getLocalHost(), 5000);
	        mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
	        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	        clientdata = new StringBuffer(2048);
	        mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
	        while(true) {
	        	serverdata = input.readLine();
	        	display.append(serverdata+"\r\n");
	        }
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
		
    public void actionPerformed(ActionEvent ae) {
    	if(ae.getSource() == logout) {
    		plabel.removeAll();
    		plabel.add(loglbl, BorderLayout.WEST);
			plabel.add(ltext, BorderLayout.EAST);
			ptotal.add(plabel, BorderLayout.SOUTH);
			ltext.setVisible(true);
			ptotal.validate();
			mlbl.setText("�α׾ƿ� �Ͽ����ϴ�.");
			try {
				clientdata.setLength(0);
	            clientdata.append(REQ_LOGOUT);
	            clientdata.append(SEPARATOR);
	            clientdata.append(ID);
	            output.write(clientdata.toString()+"\r\n");
	            output.flush();
				client.close();
				ID = null;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}		
    	}
    	else {
    		if(client == null) mlbl.setText("������ ������ �Ұ��մϴ�.");
	    	else {
		    	if(ID == null) {
		    		try {
						client = new Socket(InetAddress.getLocalHost(), 5000);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
		    		ID = ltext.getText();
		    		mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
		    		plabel.removeAll();
					plabel.add(logout, BorderLayout.SOUTH);
					add(ptotal, BorderLayout.SOUTH);
					ptotal.validate();
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
	    	}
    	}
   }
	
   public static void main(String args[]) {
	   ChatMessageC c = new ChatMessageC();
	   c.runClient();
   }
		
   class WinListener extends WindowAdapter {
	   public void windowClosing(WindowEvent e) {
		   if(client.isClosed()) {

		   }
		   else {
			   try {
				    clientdata.setLength(0);
		            clientdata.append(REQ_LOGOUT);
		            clientdata.append(SEPARATOR);
		            clientdata.append(ID);
		            output.write(clientdata.toString()+"\r\n");
		            output.flush();
					client.close();
					ID = null;
				    client.close();
			   } catch(IOException ioe) {
				   ioe.printStackTrace();
			   }
		   }
		   System.exit(0);
	   }
   }

   public void keyPressed(KeyEvent ke) {
	   if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
		   String message = new String();
		   message = wtext.getText();
		   if (ID == null) {
			   mlbl.setText("�ٽ� �α��� �ϼ���!!!");
			   wtext.setText("");
		   } else {
			   try {
	               clientdata.setLength(0);
	               clientdata.append(REQ_SENDWORDS);
	               clientdata.append(SEPARATOR);
	               clientdata.append(ID);
	               clientdata.append(SEPARATOR);
	               clientdata.append(message);
	               output.write(clientdata.toString()+"\r\n");
	               output.flush();
	               wtext.setText("");
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