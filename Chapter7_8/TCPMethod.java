package Chapter7_8;

import java.io.*;
import java.net.*;
import java.util.Date;
/*
 * toString(), getSendBufferSize()/setSendBufferSize(), getReceiveBufferSize()/setReceiveBufferSize(),
 * getKeepAlive()/setKeepAlive(), getTcpNoDelay()/setTcpNoDelay(), getReuseAddress()
 */
public class TCPMethod {

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
			System.out.println("호스트의 시간은 " + theTime + "이다");
			
			System.out.println(theSocket.toString());
			System.out.println("Defalut : " + theSocket.getSendBufferSize());
			theSocket.setSendBufferSize(100);
			System.out.println("Setting : " + theSocket.getSendBufferSize() + "\n");
			
			System.out.println("Defalut : " + theSocket.getReceiveBufferSize());
			theSocket.setReceiveBufferSize(200);
			System.out.println("Setting : " + theSocket.getReceiveBufferSize() + "\n");

			System.out.println("Defalut : " + theSocket.getKeepAlive());
			theSocket.setKeepAlive(true);
			System.out.println("Setting : " + theSocket.getKeepAlive() + "\n");
			
			System.out.println("Defalut : " + theSocket.getTcpNoDelay());
			theSocket.setTcpNoDelay(true);
			System.out.println("Setting : " + theSocket.getTcpNoDelay() + "\n");
			
			System.out.println("Defalut : " + theSocket.getReuseAddress());
			theSocket.setReuseAddress(true);
			System.out.println("Setting : " + theSocket.getReuseAddress() + "\n");
			
			if (theSocket != null) {
				theSocket.shutdownInput();
				writer.write("Thank You");
				writer.flush();
				theSocket.close();
			}
			
		} catch (UnknownHostException e) {
			System.out.println(args[0] + "호스트를 찾을 수 없습니다.");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}


/*
	
*/
