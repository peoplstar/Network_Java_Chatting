package Chapter7_8;

import java.io.*;
import java.net.*;

public class EchoSerever /*extends Thread*/{

	public static void main(String[] args) {
		ServerSocket theServer;
		Socket theSocket = null;
		
		try {		
			theServer = new ServerSocket(7, 2);
			theServer.setReuseAddress(true);
			while (true) {			
				//theServer.bind(new InetSocketAddress(7), 2);	
				theSocket = theServer.accept();
				ServerThread thread = new ServerThread(theSocket);
				thread.start();
			}
			/*
			 * theServer = new ServerSocket(7);
			 * theSocket = theServer.accept();
			 */

			
		} catch (UnknownHostException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (theSocket != null) {
				try {
					theSocket.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}
}

class ServerThread extends Thread {
	InputStream is;
	BufferedReader reader;
	OutputStream os;
	BufferedWriter writer;
	String theLine;

	public ServerThread (Socket theSocket) {
		try {
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	public void run() {
		try {
			while ((theLine = reader.readLine()) != null) {
				System.out.println(theLine);
				writer.write(theLine + '\r' + '\n');
				writer.flush();
			}
		} catch (IOException e) {
			System.out.println(e); 
		}
		
	}
	
}
