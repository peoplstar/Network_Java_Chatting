package Chapter07_08;

import java.io.*;
import java.net.*;
import java.util.Date;

public class DayTimeServer {
	public final static int DAYTIMEPORT = 13;
	public static void main(String[] args) {
		ServerSocket theServer;
		Socket theSocket =  null;
		InputStream is;
		BufferedWriter writer;
		BufferedReader reader;
		try {
			theServer = new ServerSocket(DAYTIMEPORT);
			while (true) {
				try {
					theSocket = theServer.accept();
					is = theSocket.getInputStream();
					reader = new BufferedReader(new InputStreamReader(is));
					OutputStream os = theSocket.getOutputStream();
					writer = new BufferedWriter(new OutputStreamWriter(os));
					Date now = new Date();
					writer.write(now.toString() + "\r\n");
					writer.flush();
					
					if (theSocket != null) {
						String message = reader.readLine();
						System.out.println(message);
						theSocket.close();
					}
				} catch (IOException e) {
					System.out.println(e);
				} finally {
					/*try {
						if (theSocket != null) theSocket.close();
					} catch (IOException e) {
						System.out.println(e);
					}*/
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
