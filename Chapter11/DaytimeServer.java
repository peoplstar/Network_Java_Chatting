package Chapter11;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaytimeServer {
	public static final int PORT = 13;
	public static final int MAX_PACKET_SIZE = 65508;
	public static void main(String[] args) {
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
			DatagramSocket socket = new DatagramSocket(PORT);
			DatagramPacket spacket;
			//DatagramSocket ssocket = new DatagramSocket();	
			while(true) {
				try {
					socket.receive(rpacket);					
					String answer = new String(rpacket.getData(), 0, rpacket.getLength());
					SimpleDateFormat simpleDateFormat;
					rpacket.setLength(MAX_PACKET_SIZE);
							
					byte[] data = new byte[50];
					Date today = new Date();
					
					if (answer.trim().equals("time")) {
						SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:ss a"); 	
						String result = df1.format(today);
						data = result.getBytes();
						spacket = new DatagramPacket(data, data.length, rpacket.getAddress(), rpacket.getPort());
						socket.send(spacket);
						System.out.println("time");
					}
					else if (answer.trim().equals("all")) {
						SimpleDateFormat df2 = new SimpleDateFormat("yyyy�� MM�� dd�� (E)���� kk�� mm��"); 	
						String result = df2.format(today);
						data = result.getBytes();
						spacket = new DatagramPacket(data, data.length, rpacket.getAddress(), rpacket.getPort());	
						socket.send(spacket);
						System.out.println("all");
					}
		
					else {
						System.out.println("�߸��� �Է°��Դϴ�.");
						System.exit(0);
					}
					
					
				}catch (IOException e)
				{
					System.out.println(e);
				}
			}
		} catch (SocketException se)
		{
			System.out.println(se);
		}
	}
}