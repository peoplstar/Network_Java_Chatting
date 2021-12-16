package Chapter13;

// step3
// 아이디와 대화말 메시지를 전송함
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class Client extends Frame implements ActionListener, KeyListener {

    TextArea display;
    TextField wtext, ltext;
    Label mlbl, wlbl, loglbl;
    BufferedWriter output;
    BufferedReader input;
    Socket client;
    StringBuffer clientdata;
    String serverdata;
    String ID;
    Panel plabel;
    MulticastSocket msocket;
    DatagramPacket packet;
    boolean join = false;
    boolean rejoin = true;
    InetAddress group;
    SocketAddress sadd;

    private static final String SEPARATOR = "|";
    private static final int REQ_LOGON = 1001;
    private static final int REQ_SENDWORDS = 1021;
    private static final int REQ_LOGOUT = 1002;

    public Client() {
        super("클라이언트");

        mlbl = new Label("채팅 상태를 보여줍니다.");
        add(mlbl, BorderLayout.NORTH);

        display = new TextArea("", 0, 0);
        display.setEditable(false);
        add(display, BorderLayout.CENTER);

        Panel ptotal = new Panel(new BorderLayout());
        Panel pword = new Panel(new BorderLayout());
        
        wlbl = new Label("대화말");
        wtext = new TextField(30); //전송할 데이터를 입력하는 필드
        wtext.addKeyListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
        pword.add(wlbl, BorderLayout.WEST);
        pword.add(wtext, BorderLayout.EAST);
        ptotal.add(pword, BorderLayout.CENTER);

        plabel = new Panel(new BorderLayout());
        loglbl = new Label("로그온");
        ltext = new TextField(30); //전송할 데이터를 입력하는 필드
        ltext.addActionListener(this); //입력된 데이터를 송신하기 위한 이벤트 연결
        plabel.add(loglbl, BorderLayout.WEST);
        plabel.add(ltext, BorderLayout.EAST);
        ptotal.add(plabel, BorderLayout.SOUTH);

        add(ptotal, BorderLayout.SOUTH);

        addWindowListener(new WinListener());
        setSize(400,250);
        setVisible(true);
    }

    public void runClient() {
        try {
            client = new Socket(InetAddress.getLocalHost(), 5000);
            mlbl.setText("연결된 서버이름 : " + client.getInetAddress().getHostName());
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            clientdata = new StringBuffer(2048);
            mlbl.setText("접속 완료 사용할 아이디를 입력하세요.");
            byte data[] = new byte[512];
            while(true) {
                if(!join) {
                    serverdata = input.readLine();
                    display.append(serverdata + "\r\n");
                    group = InetAddress.getByName(serverdata.substring(serverdata.indexOf('/') + 1, serverdata.indexOf(':')));
                    String port_str = serverdata.substring(serverdata.indexOf(':') + 1, serverdata.indexOf("입"));
                    int port = Integer.parseInt(port_str);
                    msocket = new MulticastSocket(port);
                    msocket.setSoTimeout(50000);
                    sadd = new InetSocketAddress(group, port);
                    msocket.joinGroup(sadd,msocket.getNetworkInterface());
                    packet = new DatagramPacket(data, data.length, group, port);
                    join = true;
                }
                else {
                    msocket.receive(packet);
                    String receive = new String(packet.getData(),0, packet.getLength());
                    display.append(receive + "\r\n");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            mlbl.setText("연결에 실패했습니다.");

        }
    }

    public void actionPerformed(ActionEvent ae){
        if(ID == null) {
            if (!client.isConnected()){
                return;
            }
            ID = ltext.getText();
            mlbl.setText(ID + "(으)로 로그인 하였습니다.");
            try {
                clientdata.setLength(0);
                clientdata.append(REQ_LOGON);
                clientdata.append(SEPARATOR);
                clientdata.append(ID);
                output.write(clientdata.toString()+"\r\n");
                output.flush();
                ltext.setVisible(false);
                if(!rejoin) {
                    msocket.joinGroup(sadd,msocket.getNetworkInterface());
                    rejoin = true;
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            plabel.remove(loglbl);
            plabel.remove(ltext);
            Button logout = new Button("로그아웃");
            logout.addActionListener(this);
            plabel.add(logout);
            plabel.validate();
        }
        else if(ae.getActionCommand() == "로그아웃") {
            try {
                clientdata.setLength(0);
                clientdata.append(REQ_LOGOUT);
                clientdata.append(SEPARATOR);
                clientdata.append(ID);
                output.write(clientdata.toString()+"\r\n");
                output.flush();
                client.close();
                ID = null;
                plabel.removeAll();
                loglbl = new Label("로그온");
                ltext = new TextField(30); // 전송할 데이터를 입력하는 필드
                ltext.addActionListener(this);
                plabel.add(loglbl, BorderLayout.WEST);
                plabel.add(ltext, BorderLayout.EAST);
                plabel.validate();
                client = new Socket(InetAddress.getLocalHost(), 5000);
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                msocket.leaveGroup(sadd,msocket.getNetworkInterface());
                rejoin = false;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Client c = new Client();
        c.runClient();
    }

    class WinListener extends WindowAdapter {
        public void windowClosing(WindowEvent e){
            try {
                if(ID == null) System.exit(0);
                if(!client.isClosed()) {
                    clientdata.setLength(0);
                    clientdata.append(REQ_LOGOUT);
                    clientdata.append(SEPARATOR);
                    clientdata.append(ID);
                    output.write(clientdata.toString() + "\r\n");
                    output.flush();
                    client.close();
                    msocket.close();
                }
            }catch (IOException e1) {
                e1.printStackTrace();
            }catch (NullPointerException ne) {
                System.exit(0);
            }
            System.exit(0);
        }
    }

    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
            String message = new String();
            message = wtext.getText();
            if (ID == null) {
                mlbl.setText("다시 로그인 하세요!!!");
                wtext.setText("");
            } else {
                try {
                    clientdata.setLength(0);
                    clientdata.append(REQ_SENDWORDS);
                    clientdata.append(SEPARATOR);
                    clientdata.append(ID);
                    clientdata.append(SEPARATOR);
                    clientdata.append(message);
                    output.write(clientdata.toString()+"\r\n");
                    output.flush();
                    wtext.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }
}