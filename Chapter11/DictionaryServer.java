package Chapter11;

import java.io.*;
import java.net.*;
import java.util.Arrays;

class HTMLDecode {
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
public class DictionaryServer {
	static String urlPath1 = "https://www.ybmallinall.com/styleV2/dicview.asp?kwdseq=0&kwdseq2=0&DictCategory=DictEng&DictNum=1&ById=0&PageSize=5&StartNum=0&GroupMode=0&cmd=0&kwd=";
	static String urlPath2 = "&x=0&y=0";		
    static String pageContents;
    static String unicode;
    static String contents; // HTML Source storage
    static URL url;
    static String data;
	public static final int MAX_PACKET_SIZE = 65508;
	public static String filename = "dictionary.txt";
	public static void HTMLsource(URL url) {
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
	
	public static void main(String[] args) {
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		String text;
		boolean flag = false;
		try {
			DatagramPacket receivepacket = new DatagramPacket(buffer, buffer.length);
			DatagramPacket sendpacket;
			DatagramSocket socket = new DatagramSocket(5000);
			FileInputStream fin = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			FileWriter fw = new FileWriter(filename, true);
			
			while(true) {
				text = "";
				try {
					socket.receive(receivepacket);					
					String query = new String(receivepacket.getData(), 0, receivepacket.getLength());
					receivepacket.setLength(MAX_PACKET_SIZE);
					
					byte[] data = new byte[100]; // 저장할 데이터
					byte[] packetdata = new byte[100]; // 전송할 데이터
					
					String dict = "";
		            
					while((text = br.readLine()) != null) {
						if(text.contains(query)) {
							int idx = 0;
							idx = text.indexOf(" : ");
							dict = text.substring(idx);
							packetdata = dict.getBytes();
							sendpacket = new DatagramPacket(packetdata, packetdata.length, receivepacket.getAddress(), receivepacket.getPort());
							socket.send(sendpacket);
							flag = true;
							Arrays.fill(packetdata, (byte) 0);
						}
					}
	
					if(!flag) {
						String urlPath = new String(urlPath1 + query + urlPath2);
						url = new URL(urlPath);
			            HTMLsource(url);
			            data = contents.getBytes();
			            sendpacket = new DatagramPacket(data, data.length, receivepacket.getAddress(), receivepacket.getPort());
						socket.send(sendpacket);
			            fw.write(query + " : " + contents);
			            fw.flush();
			            
					}		
					flag = false;
				}catch (IOException e) {
					System.out.println(e);
				}
			}
		} catch (SocketException se) {
			System.out.println(se);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}