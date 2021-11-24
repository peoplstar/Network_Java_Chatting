package Chapter04;

import java.io.*;

public class AttachLineNumber {
	public static void main(String args[]) {
		String buf;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		
		try {
			fin = new FileInputStream(args[0]);
			fout = new FileOutputStream(args[1]);
		} catch(Exception e) {}
		
		BufferedReader read = new BufferedReader(new InputStreamReader(fin));
		PrintStream write = new PrintStream(fout);
		int num = 1;
		while(true) {
			try {
				buf = read.readLine();
				if (buf == null) break;
			} catch (IOException e) { break; }
			buf = num + " : " + buf;
			write.print("\r\n");
			num++;
		}
		
	}
}
