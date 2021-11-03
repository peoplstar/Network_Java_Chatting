package Chapter4;

import java.io.*;

public class FileAppend {

	public static void main(String[] args) {
		String name, buf1, buf2;
		String[] filename = new String[2];
		int i=0;
		
		System.out.print("파일명 두개 입력하세요 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while ((name = br.readLine()) != null) {
				filename[i] = name;
				i++;
				if (i >= 2) break;
			}
		} catch (IOException e) { System.out.println(e); } 
		
		try {
			FileInputStream fin1 = new FileInputStream(filename[0]);
			FileInputStream fin2 = new FileInputStream(filename[1]);
			InputStreamReader isr1 = new InputStreamReader(fin1, "KSC5601");
			InputStreamReader isr2 = new InputStreamReader(fin2, "KSC5601");
			BufferedReader br1 = new BufferedReader(isr1);
			BufferedReader br2 = new BufferedReader(isr2);
			
			FileOutputStream fout = new FileOutputStream("fileappend.txt");
			OutputStreamWriter osw = new OutputStreamWriter(fout, "KSC5601");
			BufferedWriter bw = new BufferedWriter(osw);
			
			while((buf1 = br1.readLine()) != null) {
				bw.write(buf1 + "\r\n");
				bw.flush();
			}
			while((buf2 = br2.readLine()) != null) {
				bw.write(buf2 + "\r\n");
				bw.flush();
			}
			fout.close();
			fin1.close();
			fin2.close();
		} catch (IOException e) { System.out.println(e); } 
	
	}

}
