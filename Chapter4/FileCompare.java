package Chapter4;

import java.io.*;
import java.text.SimpleDateFormat;

public class FileCompare {

	public static void main(String[] args) {
		String name;
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
			File f1 = new File(filename[0]);
			File f2 = new File(filename[1]);	
			FileInputStream fin1 = new FileInputStream(filename[0]);
			FileInputStream fin2 = new FileInputStream(filename[1]);
			InputStreamReader isr1 = new InputStreamReader(fin1, "KSC5601");
			InputStreamReader isr2 = new InputStreamReader(fin2, "KSC5601");
			BufferedReader br1 = new BufferedReader(isr1);
			BufferedReader br2 = new BufferedReader(isr2);
		
			StringBuffer Sbuf1 = new StringBuffer();
			StringBuffer Sbuf2 = new StringBuffer();
			
			String text = "";

			while ((text = br1.readLine()) != null) {
				Sbuf1.append(text + "\n");
			}
			text = "";
			while ((text = br2.readLine()) != null) {
				Sbuf2.append(text + "\n");
			}
			
			if ((Sbuf1.toString()).equals((Sbuf2).toString())) { // 최종 수정 시간
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E)요일 kk시 mm분");
				System.out.print("파일의 최종 수정 시간 : ");
				System.out.println(simpleDateFormat.format(f1.lastModified()));
			}
			else { // 각 파일의 길이
				System.out.println(filename[0] + "의 길이 : " + f1.length());
				System.out.println(filename[1] + "의 길이 : " + f2.length());
			}
			
			fin1.close();
			fin2.close();
		} catch (IOException io) { System.out.println(io); }
	}

}
