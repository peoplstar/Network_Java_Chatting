package Chapter11;

import java.io.*;
import java.net.*;

public class UDPDiscardServer {
	
	public static final int PORT = 9;
	public static final int MAX_PACKET_SIZE = 65508;
	
	public static void main(String[] args) {
		byte [] buffer = new byte[MAX_PACKET_SIZE];
		
		
		try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			DatagramSocket server = new DatagramSocket(PORT);
			while(true) {
				try {
					server.receive(packet);
					String data = new String(packet.getData(), 0 , packet.getLength());
					System.out.println("IP �ּ� : " + packet.getAddress() + " �� ��Ʈ : " + 
					packet.getPort() + "Ŭ���̾�Ʈ���� ������ " + data + "�� ��������.");
					packet.setLength(buffer.length);
				} catch(IOException e) {
					System.out.println(e);
				}
			}
		} catch(SocketException se) {
			System.out.println(se);
		}

	}

}
