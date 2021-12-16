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

		System.out.print("URL을 입력하세요 : "); 
		try {
			text = br.readLine();
			u = new URL(text);
			uc = u.openConnection();
			System.out.println("다운로드 할 Content-Type : " + uc.getContentType()); // 텍스트 파일 경우 .txt 붙여서 새롭게 if 구성
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
				System.out.println("데이터를 정상적으로 읽지 않았습니다.");
			}
			// 바이너리일 경우 
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
