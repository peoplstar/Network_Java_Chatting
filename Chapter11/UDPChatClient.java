package Chapter11;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class UDPChatClient extends Frame implements ActionListener{
	private TextArea display;
	private TextField enter;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket socket;
	public UDPChatClient() {
		super("Client");
		enter = new TextField("메시지를 입력하세요.");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		display = new TextArea();
		add(display, BorderLayout.CENTER);
		addWindowListener(new WinListener());
		setSize(400, 300);
		setVisible(true);
		try {
			socket = new DatagramSocket();
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	public void waitForPackets() {
		while (true) {
			try {
				byte data[] = new byte[100];
				receivePacket = new DatagramPacket(data, data.length);
				socket.receive(receivePacket); // Server에게 받은 메세지
				display.append("Server : " + new String(receivePacket.getData()));
				display.append("\n");
			} catch (IOException ie) {
				display.append(ie.toString() + "\n");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) { // 자기자신이 친 메세지 TextField 등록
		try {
			display.append("Client : " + e.getActionCommand() + "\n");
			String s = e.getActionCommand();
			byte data[] = s.getBytes();
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 5000);
			socket.send(sendPacket);
			
		} catch (IOException ie) {
			display.append(ie.toString() + "\n");
			ie.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		UDPChatClient c = new UDPChatClient();
		c.waitForPackets();
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}
