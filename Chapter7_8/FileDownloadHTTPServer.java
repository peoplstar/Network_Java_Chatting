package Chapter7_8;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileDownloadHTTPServer {

	public static void main(String[] args) {
		int b, port;
		byte[] data = null;
		String encoding = "ASCII";
		String contenttype = "text/plain";
		
		try {
			/* Argument data 전송	
			if(args[0].endsWith(".html") || args[0].endsWith(".htm")) {
				contenttype = "text/html";
			}
			FileInputStream in = new FileInputStream(args[0]);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((b = in.read()) != -1)
				out.write(b);
			data = out.toByteArray();
			 */	
			port = 1245;
			ServerSocket server = new ServerSocket(port);
			while (true) {
				Socket connection = null;
				FileDownload client = null;
				try {
					connection = server.accept();
					client = new FileDownload(connection, data, encoding, contenttype, port);
					client.start();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class FileDownload extends Thread {
	private byte[] content;
	private byte[] header;
	private int port;
	private String MIMEType;
	Socket connection;
	BufferedOutputStream out;
	BufferedInputStream in;
	FileOutputStream fout;
	FileInputStream fin;
	OutputStreamWriter osw;
	InputStreamReader isr;
	BufferedReader br;
	File file;
	
	int start, end, b;
	String requestFile;
	public FileDownload(Socket connection, byte[] data, String encoding, String MIMEType, int port) throws UnsupportedEncodingException {
		this.connection = connection;
		this.content = data;
		this.port = port;
		this.MIMEType = MIMEType;
	}
	public void run() {
		try {
			out = new BufferedOutputStream(connection.getOutputStream());
			in = new BufferedInputStream(connection.getInputStream());
			StringBuffer request = new StringBuffer(80);
			StringBuffer message = new StringBuffer(100);
			while (true) {
				int c = in.read();
				if (c == '\r' || c == '\n' || c == -1)
					break;
				request.append((char)c);
			}
			
			start = request.toString().indexOf("/");
			end = request.toString().indexOf(" HTTP/");
			requestFile = request.substring(start + 1, end);
			System.out.println(request.toString());
			System.out.println(requestFile);
			file = new File(requestFile);
			String text;
			String error = "파일이 존재하지 않습니다.";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			//if (file.exists()) { // 존재하면 바로 전송
				fin = new FileInputStream(requestFile);
				
				while ((b = fin.read()) != -1)
					baos.write(b);
				this.content = baos.toByteArray();
 
				/*while ((text = br.readLine()) != null) {
					message.append(text);
				}
				*/
				String header = "HTTP 1.0 200 OK\r\n" + "Server: OneFile 1.0\r\n" + "Content-length:" + /*String.valueOf(message).getBytes()*/this.content.length + "\r\n" + "Content-type: " + this.MIMEType + "\r\n\r\n";
				this.header = header.getBytes("ASCII");
				
				//out.write(String.valueOf(message).getBytes());
				//out.write(this.content);
				//out.flush();
			//}
			/*
			else { // 파일 생성 후 전송 (.java 내용 텍스토로 작성 이후 .txt 파일로 변경)
				//out.write(error.getBytes());
				System.out.println(error);
			}
			*/
			
			
			if (request.toString().indexOf("HTTP/") != -1) {
				out.write(this.header);
			}
			out.write(this.content);
			out.flush();
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}	
