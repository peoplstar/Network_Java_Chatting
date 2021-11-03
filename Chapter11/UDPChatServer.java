package Chapter11;

import java.io.*;
import java.net.*;

import java.awt.*;
import java.awt.event.*;

public class UDPChatServer extends Frame implements ActionListener {
	private TextArea display;
	private TextField enter;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket socket;
	private InetAddress addr;
	private int port;
	public UDPChatServer() {
		super("Server");
		enter = new TextField("메시지를 입력하세요.");
		enter.addActionListener(this);
		add(enter, BorderLayout.NORTH);
		display = new TextArea();
		add(display, BorderLayout.CENTER);
		addWindowListener(new WinListener());
		setSize(400, 300);
		setVisible(true);
		try {
			socket = new DatagramSocket(5000);
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
				socket.receive(receivePacket);
				addr = receivePacket.getAddress();
				port = receivePacket.getPort();
				
				display.append("Client : " + new String(receivePacket.getData())); 
				display.append("\n");
			} catch (IOException ie) {
				display.append(ie.toString() + "\n");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) { // 자기자신이 친 메세지 TextField 등록
		try {
			display.append("Server : " + e.getActionCommand() + "\n");
			// display.append("ip : " + addr + " port : " + port);
			String s = e.getActionCommand();
			byte data[] = s.getBytes();
			sendPacket = new DatagramPacket(data, data.length, addr, port);
			socket.send(sendPacket);
			
		} catch (IOException ie) {
			display.append(ie.toString() + "\n");
			ie.printStackTrace();
		}
	}
			
	public static void main(String args[]) {
		UDPChatServer s = new UDPChatServer();
		s.waitForPackets();
	}
	
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}
