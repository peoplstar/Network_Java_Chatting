package Chapter5_6;

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
				System.out.println("호스트 이름 및 IP 주소를 입력하세요.");
				if ((hostname = br.readLine()) != null)
					printRemoteAddress(hostname);
			}while(hostname != null);
			System.out.println("프로그램을 종료합니다.");
		}catch (IOException ex) {
			System.out.println("입력 에러!");
		}
	}
	static void printLocalAddress() {
		try {
			InetAddress myself = InetAddress.getLocalHost();
			System.out.println("로컬 호스트 이름 : " + myself.getHostName());
			System.out.println("로컬 IP 주소 : " + myself.getHostAddress());
			System.out.println("로컬 호스트 CLASS : " + ipClass(myself.getAddress()));
			System.out.println("로컬 호스트 InetAddress : " + myself.toString());
			System.out.println("로컬 호스트 Loopback 주소 : " + myself.getLoopbackAddress());
			System.out.println();
		} catch(UnknownHostException ex) {
			System.out.println(ex);
		}
	}
	static void printRemoteAddress(String hostname) {
		try {
			System.out.println("호스트를 찾고 있습니다. " + hostname + "...");
			InetAddress machine = InetAddress.getByName(hostname);
			System.out.println("원격 호스트 이름 : " + machine.getHostName());
			System.out.println("원격 호스트 IP : " + machine.getHostAddress());
			System.out.println("원격 호스트 CLASS : " + ipClass(machine.getAddress()));
			System.out.println("원격 호스트 InetAddress : " + machine.toString());
			System.out.println("---------------------------------");
			
			InetAddress[] inet = InetAddress.getAllByName(hostname);
			for (int i = 0; i < inet.length; i++) {
				System.out.println("원격 호스트 주소 : " + inet[i]);
			}
			
			System.out.println("---------------------------------");
			
			InetAddress compareInet = InetAddress.getByAddress(machine.getAddress());
			System.out.println("입력 원격 호스트 & 메소트 호스트 : " + compareInet.equals(machine));
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
