package Chapter07_08;

public class content {
/*
 * TCP
 
 * Socket 정의 : 프로그램 간의 양방향 링크의 한쪽 끝단, 특정 포트 번호와 연결되어있음
 * socket에 대한 bind 필요 bind시 서버측 IP,port 채워진다
 * [server] listen() : socket() => 능동형 소켓(연결 시도 시 사용) => 수동형으로 변환
 * [client] connect() : server에 연결 (3-way-handshaking 시도) => serversocket에 client IP, port 채워짐
 * accept() : Block 상태 O, 연결 시 Block End, return 값으로 서버의 소켓이 통신용 소켓으로 변환 (무한 루프속에서 무한정 연결 요청을 받음)
 * ServerSocket : 연결용 소켓, 통신용 소켓 2개 존재, 동시에 연결 가능한 개수 지정 가능 ServerSocket(int port, int backlog_queue); default 50개
 * setSoTimeout() : Block method의 대기 시간 지정, 10초는 10000
 
 * 서버가 리스너에 해당 서버소켓(연결용)으로 하나 생성 후, accept() method로 대기
 * 클라이언트 소켓으로부터 연결 요청을 받을 시 서버소켓은 통신용 소켓으로 변경됨.
 * 데이터 입출력을 위한 소켓.getOut(or In)putStream(); 사용
 
 * Socket : Client의 소켓 (하나만 존재) Socket(String host or InetAddress address, int port);
 * readLine() : null 인 경우 스트림으로 읽을 데이터가 없다. 즉, 클라이언트가 연결이 끊겼다는 것을 의미, 이후 통신용 소켓은 close()
 * TCP UDP echo 차이 : 연결이 이루어 진 것을 서버가 알 수 있냐 없냐, 서버가 클라이언트에 대한 연결 정보를 모른다.
 * 30s ~ 60s 동안 FIN 패킷을 받고 바로 연결 해지를 하지 않고 (무언가) 기다린다.
 * REUSEADDR : 연결 해지 이후 포트 번호 재사용 가능, 멀티캐스팅 으용이 동일한 포트번호 사용 가능 
 
 * 				socketname = the ServerSocket();							*
 * 빈 소켓 생성 후, socketname.setReuseAddress(true); 무조건 bind 이전에 해야할 것		*
 * 				socketname.bind(new InetSocketAddress(7));					*
 
 * bindingExpection 존재 X
 * Probe Packet : 주로 서버에서 setKeepAlive(true)로 클라이언트에게 메세지를 받지 않을 때 사용, 자원 절약을 위함
 * SoLinger : close()시 송신버퍼에 데이터가 남아있는것을 다 전송하고 연결 종료, 하지만 오류 발생 시 재전송 보장하기 때문에 제대로 전송이 되었는지 확인을 못하게 된다. close를 지정 linger 시간동안 BLOCK 상태. default = false
 * true, 0s 이면 패킷 폐기 
 * TCP No delay Option = setTcpNoDelay(on): 송신 데이터가 일정 크기만큼 쌓이지 않아도 바로 전송(Nagle 알고리즘 무시) default = off
 * ShutDownIn(Out)put : 절반 폐쇄
 */
}
 