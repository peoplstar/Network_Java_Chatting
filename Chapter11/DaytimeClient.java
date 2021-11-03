package Chapter11;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaytimeClient {
	public static final int PORT = 13;
	public static final int MAX_PACKET_SIZE = 65508;
	public static String hostname = "localhost";
	public static void main(String[] args) {
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			BufferedReader input;
			DatagramPacket spacket;
			DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
			DatagramSocket socket = new DatagramSocket();
			//DatagramSocket ssocket = new DatagramSocket(); || incoming socket, outgoint socket 두개로 하면 안되는 이유, 가능한지
			InetAddress server = InetAddress.getByName(hostname);
			input = new BufferedReader(new InputStreamReader(System.in));

			while(true) {
				System.out.println("\n[    시간을 알고 싶으면 : time    ]");
				System.out.println("[ 연도/월/일 모두 알고 싶으면 : all ]\n");
				System.out.print("입력한 내용 : ");
				String theLine = input.readLine();
				if(theLine.equals(".")) break;

				byte[] data = theLine.getBytes();
				spacket = new DatagramPacket(data, data.length, server, PORT);
				socket.send(spacket);

				socket.receive(rpacket);
				
				String day = new String(rpacket.getData());
				if(theLine.equals("time"))
					System.out.println("\n시간 : "  + day);
				else System.out.println("\nALL : " + day);
				rpacket.setLength(MAX_PACKET_SIZE);
				
			}
		}catch (UnknownHostException e) 
		{
			System.out.println(e);
		}catch (SocketException e)
		{
			System.out.println(e);
		}catch (IOException e)
		{
			System.out.println(e);
		}
	}

}