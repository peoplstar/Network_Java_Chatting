package Chapter7_8;

import java.io.*;
import java.net.*;

public class DayTimeClient {

	public static void main(String[] args) {
		Socket theSocket;
		String host;
		InputStream is;
		BufferedReader reader;
		BufferedWriter writer;
		if(args.length > 0) {
			host = args[0];
		} else {
			host = "localhost";
		}
		try {
			theSocket = new Socket(host, 13);
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			OutputStream os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
			String theTime = reader.readLine();
			System.out.println("ȣ��Ʈ�� �ð��� " + theTime + "�̴�");
			if (theSocket != null) {
				theSocket.shutdownInput();
				writer.write("Thank You");
				writer.flush();
				theSocket.close();
			}
			
		} catch (UnknownHostException e) {
			System.out.println(args[0] + "ȣ��Ʈ�� ã�� �� �����ϴ�.");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
