package Chapter11;

public class Contents {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
/*
UDP : 메시지 지향 프로토콜, 연결설정이 없는 비연결형 통신모델, 짧은 정보의 빠른 통신에 사용
헤더 8파이트, 옵션 X, overhead가 적은편
소켓 : 응용 프로그램 인터페이스(API), 네트워크 시스템의 창고 역할, 운영체제의 자원을 빌려 사용하므로 반드시 close()해야한다.
연결설정 과정이 없기때문에 TCP와는 달리 패킷에 목적지 주소가 포함 되어야 한다.
SocketAddress(추상 클래스) = IPAddress + PortNumber
=> InetSocketAddress(InetAddress addr, int port);
송신용 DatagramPacket 생성 시, address 타입이 잘못된 경우 Error 즉, InetAddress를 생성자로 사용시 에러 발생


*/