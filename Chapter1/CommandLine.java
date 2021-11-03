package Chapter1;

import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.NoSuchElementException;

// argument�� ���α׷� �����ϱ�
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
				System.err.println("��Ʈ�����κ��� �����͸� ���� �� �����ϴ�.");
				break;
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("�ε����� ������ ������ϴ�.");
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