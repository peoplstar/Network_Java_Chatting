package Chapter13;

// STEP 3
// �α׿� �޽����� ��ȭ�� �޽����� ������
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class Server extends Frame {
    TextArea display;
    Label info;
    MulticastSocket msocket;

    public ServerThread SThread;

    public Server() {
        super("����");
        info = new Label();
        add(info, BorderLayout.CENTER);
        display = new TextArea("", 0, 0);
        display.setEditable(false);
        add(display, BorderLayout.SOUTH);
        addWindowListener(new WinListener());
        setSize(300,250);
        setVisible(true);
    }

    public void runServer() {
        ServerSocket server;
        Socket sock;
        ServerThread SThread;
        try {
            server = new ServerSocket(5000, 100);
            int port = 1473;
            msocket = new MulticastSocket(port);
            msocket.setTimeToLive(1);
            msocket.setSoTimeout(50000);
            try {
                while(true) {
                    sock = server.accept();
                    SThread = new ServerThread(this, sock, display, info,msocket);
                    SThread.start();
                    info.setText(sock.getInetAddress().getHostName() + " ������ Ŭ���̾�Ʈ�� �����");
                }
            } catch(IOException ioe) {
                server.close();
                ioe.printStackTrace();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Server s = new Server();
        s.runServer();
    }

    class WinListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}

class ServerThread extends Thread {
    Socket sock;
    BufferedWriter output;
    BufferedReader input;
    TextArea display;
    Label info;
    TextField text;
    String clientdata;
    String serverdata = "";
    Server cs;
    String user = null;
    MulticastSocket M;
    int port;
    InetAddress group;

    private static final String SEPARATOR = "|";
    private static final int REQ_LOGON = 1001;
    private static final int REQ_SENDWORDS = 1021;
    private static final int REQ_LOGOUT = 1002;

    public ServerThread(Server c, Socket s, TextArea ta, Label l, MulticastSocket msocket) {
        sock = s;
        display = ta;
        info = l;
        cs = c;
        M = msocket;
        port = 1473;
        try {
            input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            group = InetAddress.getByName("225.0.0.0");
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public void run() {
        try {
            while((clientdata = input.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
                int command = Integer.parseInt(st.nextToken());
                switch(command) {
                    case REQ_LOGON : { // ��1001|���̵𡱸� ������ ���
                        String ID = st.nextToken();
                        display.append("Ŭ���̾�Ʈ�� " + ID + "(��)�� �α��� �Ͽ����ϴ�.\r\n");
                        user = ID;
                        this.output.write("��Ƽĳ��Ʈ ä�� �׷� �ּҴ� /225.0.0.0:1473�Դϴ�." + "\r\n");
                        this.output.flush();
                        break;
                    }
                    case REQ_SENDWORDS : { // ��1021|���̵�|��ȭ������ ����
                        String ID = st.nextToken();
                        String message = st.nextToken();
                        display.append(ID + " : " + message + "\r\n");
                        String sending = ID + ":" + message;
                        byte data[] = sending.getBytes();
                        DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
                        M.send(packet);
                        break;
                    }
                    case REQ_LOGOUT : { // ��1002|���̵𡱸� ������ ���
                        String ID = st.nextToken();
                        display.append("Ŭ���̾�Ʈ : " + ID + " �� �α׾ƿ� �Ͽ����ϴ�.\r\n");
                        break;
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        try{
            sock.close();
        }catch(IOException ea){
            ea.printStackTrace();
        }
    }
}
