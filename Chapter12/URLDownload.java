package Chapter12;

import java.io.*;
import java.net.*;

public class URLDownload {
	public URLDownload() {
		URL u;
		URLConnection uc;
		String text;
		String filename, path = "./download/";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("URL�� �Է��ϼ��� : "); 
		try {
			text = br.readLine();
			u = new URL(text);
			uc = u.openConnection();
			System.out.println("�ٿ�ε� �� Content-Type : " + uc.getContentType()); // �ؽ�Ʈ ���� ��� .txt �ٿ��� ���Ӱ� if ����
			filename = text.substring(text.lastIndexOf("/") + 1);
			
			File download = new File(path);
			if(!download.exists()) {
				download.mkdirs();
			}
			InputStream is = uc.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			int cl = uc.getContentLength();
			byte[] buf = new byte[cl];
			int byteread = 0; int offset = 0;
			while(offset < cl) {
				byteread = bis.read(buf, offset, buf.length-offset);
				if(byteread == -1) break;
				offset = offset + byteread;
			}
			bis.close();
			if(offset != cl) {
				System.out.println("�����͸� ���������� ���� �ʾҽ��ϴ�.");
			}
			// ���̳ʸ��� ��� 
			FileOutputStream fos = new FileOutputStream(path + filename);
			fos.write(buf);
			fos.flush();
			fos.close();
		} catch(MalformedURLException mal) {
			mal.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		
	}
	public static void main(String[] args) {
		URLDownload url = new URLDownload();
	}
}
