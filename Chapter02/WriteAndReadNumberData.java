package Chapter02;

import java.io.*;

public class WriteAndReadNumberData {
	
	static FileOutputStream fout;
	static FileInputStream fin;
	static DataOutputStream dos;
	static DataInputStream dis;
	
	public static void main(String[] args) {
		
		boolean bdata;
		double ddata;
		int number;
		String filename = "numberdata.txt";
		try {
			fout = new FileOutputStream(filename);
			dos = new DataOutputStream(fout);
			dos.writeBoolean(true);
			dos.writeDouble(989.27);
			
			for (int i = 1; i <= 500; i++) {
				dos.writeInt(i);
			}
			fin = new FileInputStream(filename);
			dis = new DataInputStream(fin);
			bdata = dis.readBoolean();
			System.out.println(bdata);
			ddata = dis.readDouble();
			System.out.println(ddata);
			
			while(true) {
				number = dis.readInt();
				System.out.println(number + " ");
			}
			
		}catch (EOFException e) {
			System.err.println("데이터를 모두 읽었습니다.");
		}catch (IOException e) {
			System.err.println(e);
		}
		finally {
			try {
				if (dos != null) dos.close();
			}catch (IOException e) {}
		}
	}
}
