package Chapter1;

import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.NoSuchElementException;

// argument로 프로그램 실행하기
public class CommandLine {
	public static void main(String[] args) {
		int bytesRead;
		String name;
		byte[] buffer = new byte[256];
		FileInputStream fin = null;
		int i = 0;
		
		while(true) {
			try {
				name = args[i];
				fin = new FileInputStream(name);
				
				while((bytesRead = fin.read(buffer)) >= 0) {
					System.out.println(name);
					System.out.write(buffer, 0, bytesRead);
					System.out.println("");
					System.out.println("===================");
				}
			}
			catch (IOException e) {
				System.err.println("스트림으로부터 데이터를 읽을 수 없습니다.");
				break;
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("인덱스의 범위를 벗어났습니다.");
			}
			catch (NoSuchElementException e) {
				System.err.println("NoSuchElement");
			} finally {
				try {
					if(fin!=null) fin.close();
				}catch (IOException e) {}
			}
		}
	}
}