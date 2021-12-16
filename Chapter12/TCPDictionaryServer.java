package Chapter12;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

/*
- Ŭ���̾�Ʈ�� ������ �α��� �� �� ���� ���� �̿� ����
- ���� �α��� ����� �� ����
- Ŭ���̾�Ʈ�� �ܾ ����/����/������ �� ����
- ������ �����带 �̿��� ����ó��
- ������ ���Ϸ� ����
- Ŭ���̾�Ʈ-������ �������� �޽��� ����
 */
class HTMLDecoder {
	public static String htmlEntityDecode(String s) {
		int i = 0, j = 0, pos = 0;
		StringBuffer sb = new StringBuffer();
		while ((i = s.indexOf("&#", pos)) != -1 && (j = s.indexOf(';', i)) != -1) {
			int n = -1;
			for (i += 2; i < j; ++i) {
				char c = s.charAt(i);
				if ('0' <= c && c <= '9') {
					n = ( n == -1 ? 0 : n * 10) + c - '0';
				}
				else {
					break;
				}
			}
			if (i != j) {
				n = -1;
			}
			if (n != -1) {
				sb.append((char)n);
				i = j + 1; // skip ;
				
				if (';' == s.charAt(j))
					if (',' == s.charAt(j + 1)) {
						sb.append((char)',');
						sb.append((char)' ');
					}
					if (')' == s.charAt(j + 1)) 
						sb.append((char)' ');
					if (';' == s.charAt(j + 1)) { 
						sb.append((char)',');
						sb.append((char)' ');
					}
				}
				else {
					for (int k = pos; k < i; ++k) {
						
						sb.append(s.charAt(k));
					}
				}
				pos = i;
			}
			if (sb.length() == 0) {
				return s;
			} 
			else {
				sb.append(s.substring(pos, s.length()));
			}
			return sb.toString();
	}
}
class dictionary {
	static String urlPath1 = "https://www.ybmallinall.com/styleV2/dicview.asp?kwdseq=0&kwdseq2=0&DictCategory=DictEng&DictNum=1&ById=0&PageSize=5&StartNum=0&GroupMode=0&cmd=0&kwd=";
	static String urlPath2 = "&x=0&y=0";		
    static String pageContents;
    static String contents; // HTML Source storage
    static URL url;
    static String data;
	public static void HTMLsource(URL url) { // URLconnection�� �̿��� HTML Source create
	    	try {
	    		URLConnection con = (URLConnection)url.openConnection();
	        	InputStreamReader reader = new InputStreamReader (con.getInputStream());
	        	BufferedReader buff = new BufferedReader(reader);
	        	contents = new String();
	        	
	        	while ((pageContents = buff.readLine())!=null) {
	        		if (pageContents.contains("<span class=\"ExplainNum\">1 </span>")) {        			
	        			String str = new String(pageContents.getBytes("utf-8"), "utf-8");
	        			int i = str.indexOf("<span class=\"ExplainNum\">1 </span>");
	        			String find = str.substring(i+35);
	        			int j = find.indexOf("<");
	        			String load = find.substring(0, j);
	            		contents = (HTMLDecoder.htmlEntityDecode(load) + "\r\n");
	        		}
	        		else {
	        			
	        		}
	        	}
	        	buff.close();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	} 
}
public class TCPDictionaryServer extends Frame {
	TextArea display;
	Label info;
	List<ServerThread> list;
	Hashtable<String, ServerThread> hash;
	FileOutputStream fout;
	FileInputStream fin;

	public ServerThread SThread;
	
	public TCPDictionaryServer() {
		super("����");
		info = new Label();
		add(info, BorderLayout.CENTER);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(400,250);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		Socket sock;
		ServerThread SThread;
		int i = 0;
		try {
			server = new ServerSocket(5000, 5);
			hash = new Hashtable<String, ServerThread>();
			list = new ArrayList<ServerThread>();
			try {
				while(true) {
					sock = server.accept();
					SThread = new ServerThread(this, sock, display, info);
					SThread.start();
					info.setText(sock.getInetAddress().getHostName() + " ������ Ŭ���̾�Ʈ�� �����");
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
		TCPDictionaryServer s = new TCPDictionaryServer();
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
	BufferedWriter output;
	BufferedReader input; // ���� �д� �뵵
	BufferedReader br; // ���� �д� �뵵
	FileInputStream fin;
	FileWriter fw;
	
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	String serverdata = "";
	TCPDictionaryServer cs;
	
	private static final String filename = "dictionary.txt";
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_QUERY = 1021;  // /q request
	private static final int REQ_DELETE = 1022; // /d request
	private static final int REQ_UPDATE = 1023; // /u request content
	private static final int REQ_LOGOUT = 9999;
	
	public ServerThread(TCPDictionaryServer c, Socket s, TextArea ta, Label l) {
		sock = s;
		display = ta;
		info = l;
		cs = c;
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			//fin = new FileInputStream(filename);
			//br = new BufferedReader(new InputStreamReader(fin));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void run() {
		String text;
		boolean flag = false;
		int maxium = 5, cnt = 0;
		try {
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int Lcnt = cs.list.size();
				switch(command) {
					case REQ_LOGON : {
						cnt++;
						cs.list.add(this);	
						String ID = st.nextToken();		
						if(/*sameID != null*/cs.hash.containsKey(ID)) {
							this.output.write("�̹� �����ϴ� ID �Դϴ�.\r\n");
							this.output.flush();
							this.interrupt();
							cs.list.remove(this);
							continue;
						}
						else if(cnt > maxium){
							this.output.write("�̿��ڰ� �ʹ� �����ϴ�.\r\n");
							this.output.flush();
							this.interrupt();
							cs.list.remove(this);
							continue;
						}
						else {
							cs.hash.put(ID, this); // �ؽ� ���̺� ���̵�� �����带 �����Ѵ�
						}
						display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
						
						for(int i = 0; i < Lcnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread SThread = (ServerThread)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
							SThread.output.flush();
						}
						break;
					}
					case REQ_QUERY : {
						fw = new FileWriter(filename, true);
						String ID = st.nextToken();
						String word = st.nextToken();
						ServerThread SThread = (ServerThread)cs.hash.get(ID);
			            String means = "";
			            fin = new FileInputStream(filename);
						br = new BufferedReader(new InputStreamReader(fin));
						text = "";
						try {
							while((text = br.readLine()) != null) {
								if(text.contains(word)) {
									int idx = 0;
									idx = text.indexOf(" : ");
									means = text.substring(idx);
									flag = true;
								}
							}
				
							if(!flag) {
								String urlPath = new String(dictionary.urlPath1 + word + dictionary.urlPath2);
								URL url = new URL(urlPath);
								dictionary.HTMLsource(url);
								means = dictionary.contents;
						        fw.write(word + " : " + means);
						        fw.flush();
							}				
					    fw.close();
						flag = false;
						} catch(IOException e) {
							System.out.println(e);
						}
						SThread.output.write(word + " : " + means); // �ܾ� : �� ����
						SThread.output.flush();
						break;
					}
					case REQ_DELETE : {
						StringBuffer sb = new StringBuffer();
						String ID = st.nextToken();
						String word = st.nextToken();
						ServerThread SThread = (ServerThread)cs.hash.get(ID);
						fin = new FileInputStream(filename);
						br = new BufferedReader(new InputStreamReader(fin));
						// �ؽ����̺��� �ӼӸ� �޽����� ������ Ŭ���̾�Ʈ�� �����带 ����
						try {
							while((text = br.readLine()) != null) {
								if(!(text.contains(word))) {
									sb.append(text).append("\n");
								}
								else {
									
								}
								
							}
							FileWriter fw = new FileWriter(filename);
							fw.write(sb.toString());
							fw.flush();
							fw.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
						// �ܾ� ���� 
						SThread.output.write("'" + word + "'" + "�� �ܾ �������� �����߽��ϴ�." + "\r\n");
						SThread.output.flush();
						break;
					}
					
					case REQ_UPDATE : {
						StringBuffer sb = new StringBuffer();
						String ID = st.nextToken();
						String word = st.nextToken();
						String means = st.nextToken();
						fin = new FileInputStream(filename);
						br = new BufferedReader(new InputStreamReader(fin));
						ServerThread SThread = (ServerThread)cs.hash.get(ID);
						text = "";
						try {
							while((text = br.readLine()) != null) {
								if(!(text.contains(word))) {
									sb.append(text).append("\n");
								}
								else {
									sb.append(word).append(" : ").append(means);
								}
							}
							
							FileWriter fw = new FileWriter(filename);
							fw.write(sb.toString());
							fw.flush();
							fw.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
						// �ܾ� ���� 
						SThread.output.write("'" + word + "'" + "�� �ܾ��� ���� " + means + " �� �����Ͽ����ϴ�." + "\r\n");
						SThread.output.flush();
						break;
					}
					case REQ_LOGOUT : {
						String ID = st.nextToken();
						display.append("Ŭ���̾�Ʈ [" + ID + "] �α׾ƿ� �Ͽ����ϴ�." +  "\r\n");

						for(int i = 0; i < Lcnt; i++) { // ��� Ŭ���̾�Ʈ�� ����
							ServerThread SThread = (ServerThread)cs.list.get(i);
							SThread.output.write("Ŭ���̾�Ʈ [" + ID + "] �α׾ƿ� �Ͽ����ϴ�." + "\r\n");
							SThread.output.flush();
						}
						cs.hash.remove(ID, this);
						cs.list.remove(this);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		cs.list.remove(this);
		try {
			sock.close();
		} catch(IOException ea){
			ea.printStackTrace();
		}
	}
}
