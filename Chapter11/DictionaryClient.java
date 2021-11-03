package Chapter11;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DictionaryClient {
	static String urlPath1 = "https://www.ybmallinall.com/styleV2/dicview.asp?kwdseq=0&kwdseq2=0&DictCategory=DictEng&DictNum=1&ById=0&PageSize=5&StartNum=0&GroupMode=0&cmd=0&kwd=";
	static String urlPath2 = "&x=0&y=0";		
    static String pageContents;
    static String unicode;
    static StringBuilder contents; // HTML Source storage
    static URL url;
    static String data;
    
	public static final int MAX_PACKET_SIZE = 65508;
	public static String hostname = "localhost";
	public static void main(String[] args) {
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			BufferedReader input;
			DatagramPacket sendpacket;
			DatagramPacket receivepacket = new DatagramPacket(buffer, buffer.length);
			DatagramSocket socket = new DatagramSocket();
			//DatagramSocket ssocket = new DatagramSocket(); || incoming socket, outgoint socket 두개로 하면 안되는 이유, 가능한지
			InetAddress server = InetAddress.getByName(hostname);
			input = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("\n        [온라인 사전]      ");
			System.out.println("[뜻을 알고자 하는 단어를 입력하세요.]");
			
			while(true) {
				
				System.out.println("[        종료를 원할시 '.'    ]");
				System.out.print("입력한 내용 : ");
				String theLine = input.readLine();
				if(theLine.equals(".")) break;
				/*
				String urlPath = new String(urlPath1 + theLine + urlPath2);
				url = new URL(urlPath);
				*/
				byte[] data = theLine.getBytes();
				sendpacket = new DatagramPacket(data, data.length, server, 5000);
				socket.send(sendpacket);
				
				socket.receive(receivepacket);
				String dict = new String(receivepacket.getData());

				System.out.println("뜻 : "  + dict);
	
				receivepacket.setLength(MAX_PACKET_SIZE);
				
			}
		} catch (UnknownHostException e) 
		{
			System.out.println(e);
		}
		 catch (SocketException e)
		{
			System.out.println(e);
		}
		 catch (IOException e)
		{
			System.out.println(e);
		}
	}

}