package Chapter05_06;

import java.io.*;
import java.net.*;

public class InetLocalLoopback {
	public static void main(String args[]) {
		String hostname;
		BufferedReader br;
		printLocalAddress();
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			do {
				System.out.println("ȣ��Ʈ �̸� �� IP �ּҸ� �Է��ϼ���.");
				if ((hostname = br.readLine()) != null)
					printRemoteAddress(hostname);
			}while(hostname != null);
			System.out.println("���α׷��� �����մϴ�.");
		}catch (IOException ex) {
			System.out.println("�Է� ����!");
		}
	}
	static void printLocalAddress() {
		try {
			InetAddress myself = InetAddress.getLocalHost();
			System.out.println("���� ȣ��Ʈ �̸� : " + myself.getHostName());
			System.out.println("���� IP �ּ� : " + myself.getHostAddress());
			System.out.println("���� ȣ��Ʈ CLASS : " + ipClass(myself.getAddress()));
			System.out.println("���� ȣ��Ʈ InetAddress : " + myself.toString());
			System.out.println("���� ȣ��Ʈ Loopback �ּ� : " + myself.getLoopbackAddress());
			System.out.println();
		} catch(UnknownHostException ex) {
			System.out.println(ex);
		}
	}
	static void printRemoteAddress(String hostname) {
		try {
			System.out.println("ȣ��Ʈ�� ã�� �ֽ��ϴ�. " + hostname + "...");
			InetAddress machine = InetAddress.getByName("202.30.48.80");
			System.out.println("���� ȣ��Ʈ �̸� : " + machine.getHostName());
			System.out.println("���� ȣ��Ʈ IP : " + machine.getHostAddress());
			System.out.println("���� ȣ��Ʈ CLASS : " + ipClass(machine.getAddress()));
			System.out.println("���� ȣ��Ʈ InetAddress : " + machine.toString());
			System.out.println("---------------------------------");
			
			InetAddress[] inet = InetAddress.getAllByName(hostname);
			for (int i = 0; i < inet.length; i++) {
				System.out.println("���� ȣ��Ʈ �ּ� : " + inet[i]);
			}
			
			System.out.println("---------------------------------");
			
			InetAddress compareInet = InetAddress.getByAddress(machine.getAddress());
			System.out.println("�Է� ���� ȣ��Ʈ & �޼�Ʈ ȣ��Ʈ : " + compareInet.equals(machine));
		} catch(UnknownHostException ex) {
			System.out.println(ex);
		}
	}
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte < 128) ? 'A' : (highByte < 192) ? 'B' : (highByte < 224) ? 'C' : 
			(highByte < 240) ? 'D' : 'E';
	}
}