package Chapter2;

import java.io.*;

public class WriteAndReadCharString {
	
	static DataOutputStream dos;
	static FileInputStream fin;
	static DataInputStream dis;

	public static void main(String[] args) {
		char ch;
		String sdata1, sdata2;
		String filename = "chardata.txt";
		
		try {
			String data;
			FileOutputStream fout = new FileOutputStream(filename);
			fin = new FileInputStream(filename);
			dos = new DataOutputStream(fout);
			dis = new DataInputStream(fin);
			
			dos.writeChar(65);
			dos.writeUTF("반갑습니다");
			dos.writeUTF("자바 채팅 프로그래밍 교재");
			
			ch = dis.readChar();
			sdata1 = dis.readUTF();
			sdata2 = dis.readUTF();
			System.out.println(ch);
			System.out.println(sdata1);
			System.out.println(sdata2);
			
		}catch (EOFException e) {
			System.err.println(e);
		}catch (IOException e) {
			System.err.println(e);
		}
	}
}
