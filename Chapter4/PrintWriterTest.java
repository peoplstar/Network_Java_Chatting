package Chapter4;

import java.io.*;

public class PrintWriterTest {

	public static void main(String[] args) {
		
		System.out.println("문자열을 입력하세요 : ");
		String text;
		
		try {
			FileOutputStream fos = new FileOutputStream("printwriteText.txt");
			OutputStreamWriter osw = new OutputStreamWriter(fos, "KSC5601");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			while((text = br.readLine()) != null) { 
				osw.write(text + "\r\n");
				osw.flush();
			}
			osw.close();
			
			br = new BufferedReader(new FileReader("printwriteText.txt"));
			PrintWriter writer = new PrintWriter(System.out, true);
			
			while((text = br.readLine()) != null) {
				writer.print(text + "\r\n"); 
				writer.flush();
			}
			
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
