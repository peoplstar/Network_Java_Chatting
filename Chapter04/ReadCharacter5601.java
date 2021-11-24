package Chapter04;

import java.io.*;

public class ReadCharacter5601 {

	public static void main(String[] args) {
		int bytesRead;
		char[] buffer = new char[128];
		try {
			FileInputStream fis = new FileInputStream("exam5601.txt");
			InputStreamReader isr = new InputStreamReader(fis);
			while ((bytesRead = isr.read(buffer)) != -1) 
				System.out.println(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
